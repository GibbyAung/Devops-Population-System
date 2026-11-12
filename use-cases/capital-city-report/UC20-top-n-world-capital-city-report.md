# USE CASE: UC20 View Top N Populated Capital Cities in the World

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst provides a positive integer N and views the N most populated capital cities in the world.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and running.
- The application has an active connection to the `world` database.
- N is a positive whole number, for example `10`.
- Requirements 17-19 are available on the Capital Reports branch.

### Success End Condition

The system displays at most N capital cities with Name, Country, and Population, ordered from largest population to smallest. It displays exactly N rows when at least N capital cities exist.

### Failed End Condition

The report is not generated if the database or query fails, or if N is missing, nonnumeric, zero, or negative. The application displays a clear error and exits with a failure status.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests the Top-N World Capital City Report and provides N.

## MAIN SUCCESS SCENARIO

1. The analyst requests the report with N.
2. The system parses N as an integer and validates N > 0.
3. The system confirms the database connection.
4. The system joins `country.Capital` to `city.ID`.
5. The system orders by `city.Population DESC` and applies `LIMIT ?`.
6. The system maps rows to CapitalCity objects.
7. The system displays Name, Country, and Population.
8. The system displays a total row for the rows shown.
9. The analyst views the report.

## EXTENSIONS

1. Missing or nonnumeric N: display a positive-whole-number error and exit with failure.
2. N is zero or negative: reject N and exit with failure.
3. N exceeds the number of capital cities: display all available capital cities.
4. Database connection or query fails: display a clear error and do not show a misleading report.

## SUB-VARIATIONS

1. Capital cities are identified by joining `country.Capital = city.ID`.
2. Population comes from `city.Population`, not `country.Population`.
3. N is bound through `PreparedStatement`; it is not concatenated into SQL.
4. `ORDER BY city.Population DESC` appears before `LIMIT ?`.

## SCHEDULE

**DUE DATE**: Sprint 4 - Create Capital City Reports