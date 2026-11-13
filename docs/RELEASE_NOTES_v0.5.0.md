# v0.5.0 — Sprint 5 Population Reports

## Summary

This release candidate integrates Sprint 5 population reports with the previously released country, city, and capital-city reports. It brings the application to **31 of 32 requirements implemented**.

## Included

- Population breakdown reports by continent, region, and country (Requirements 23-25).
- World, continent, region, country, district, and city population totals (Requirements 26-31).
- MySQL-backed repository and CLI integration tests, Docker Compose report smoke tests, and JaCoCo coverage reporting.
- Automated coverage and branch badges, plus a GitHub bug-report issue template.

## Verification

- GitHub Actions run [43365853944](https://github.com/GibbyAung/Devops-Population-System/actions/runs/43365853944) passed the Maven unit/integration tests, Docker build, Compose application run, and report smoke tests.
- CI JaCoCo report: 64.2% instruction coverage and 49.7% branch coverage.

## Known limitation

Requirement 32 (language population reports) is not implemented and is not included in this release.

## Release status

This document describes the `v0.5.0` release candidate. Publish the tag and GitHub release only after the release pull request is approved, merged to `master`, and its CI is green.
