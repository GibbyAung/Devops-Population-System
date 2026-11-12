#!/usr/bin/env bash

set -euo pipefail

run_and_expect() {
    local expected="$1"
    shift

    local output
    if ! output="$(docker compose run --rm app "$@" 2>&1)"; then
        printf 'Integration command failed: %s\n%s\n' "$*" "$output" >&2
        return 1
    fi

    if [[ "$output" != *"$expected"* ]]; then
        printf 'Expected output containing %q from command: %s\n%s\n' \
            "$expected" "$*" "$output" >&2
        return 1
    fi

    printf 'PASS: %s\n' "$*"
}

expect_failure_with() {
    local expected="$1"
    shift

    local output
    if output="$(docker compose run --rm app "$@" 2>&1)"; then
        printf 'Expected command to fail: %s\n%s\n' "$*" "$output" >&2
        return 1
    fi

    if [[ "$output" != *"$expected"* ]]; then
        printf 'Expected failure containing %q from command: %s\n%s\n' \
            "$expected" "$*" "$output" >&2
        return 1
    fi

    printf 'PASS (expected failure): %s\n' "$*"
}

run_and_expect "Tokyo" capital-world
run_and_expect "Capital cities in continent: Asia" capital-continent Asia
run_and_expect "Population breakdown by country" country-population
run_and_expect "World total population" world-population
run_and_expect "Population breakdown by continent" continent-population
run_and_expect "Continent" continent-total-population Asia
run_and_expect "Region" region-total-population "Southeast Asia"
run_and_expect "United Kingdom" country-total-population "United Kingdom"
run_and_expect "District" district-total-population California
run_and_expect "Yangon" city-total-population Yangon
expect_failure_with "Unknown city" city-total-population "No Such City"

printf 'All MySQL report integration smoke tests passed.\n'
