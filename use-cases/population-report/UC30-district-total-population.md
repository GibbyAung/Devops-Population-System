# USE CASE: UC30 View District Total Population

## CHARACTERISTIC INFORMATION

### Goal in Context

A Demographic Analyst wants the aggregated population of cities in one
district to support local demographic and logistical planning.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- Application built and an active world database connection available.
- city table available.
- Shared total-population infrastructure available.
- Analyst supplies a district name, for example England or California.
- PO accepts name-only district matching across all country codes.

### Success End Condition

The terminal displays the district name and SUM(city.Population) for
all matching cities as one report row using Java long.

### Failed End Condition

Missing, null, blank, or unknown district names produce a readable error
and exit status 1. No fabricated zero or unrelated report is displayed.
Database failures produce a readable error without a Java stack trace.

### Primary Actor

Demographic Analyst

### Trigger

Analyst runs:
district-total-population California

## MAIN SUCCESS SCENARIO

1. Analyst requests the report with one district-name argument.
2. System verifies the argument count.
3. System rejects null or blank names and strips surrounding whitespace.
4. System runs a parameterized query using WHERE city.District = ?.
5. System groups by District and sums matching city populations.
6. System reads the aggregate using getLong().
7. System displays District and Total Population with one data row.
8. Analyst views the aggregated city population.

## EXTENSIONS

1. Missing or extra arguments: show usage and exit 1.
2. Null or blank name: "District must be provided." and exit 1.
3. Unknown name: "Unknown district: <input>" and exit 1.
4. Known district whose cities total zero: display a valid report with 0.
5. Database/query failure: show a readable error without a fake report.
6. Matching district names in multiple countries: include all matching cities
   under the agreed name-only lookup contract.

## SUB-VARIATIONS

1. Sum city.Population, not country.Population.
2. Use PreparedStatement and a ? parameter.
3. Use a 64-bit SQL aggregate, Java long, and ResultSet.getLong().
4. GROUP BY distinguishes an unknown name from a recognised zero total.
5. Quote names containing spaces.
6. The result is a recorded-city total, not an estimate of unrecorded residents.

## SCHEDULE

DUE DATE: Sprint 5 — Population Demographics and Totals.