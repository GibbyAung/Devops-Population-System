# USE CASE: UC15 View Top N Populated Cities in a Country

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants to enter a country code and a positive whole number N to see the N most populated cities in that country.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project is built, application is running, and the `world` MySQL database is accessible.
- Requirements 7-14 city report features are already available on `feature/city-reports`.
- Analyst supplies a country code such as `CHN`, `USA` or `GBR` (not a full country name).
- Analyst supplies a positive whole number N, such as `10`.

### Success End Condition
The terminal displays **at most N** cities in the selected country, with Name, Country, District and Population columns, ordered by population descending. Exactly N city rows are displayed when N or more cities match.

### Failed End Condition
Missing/blank country, missing/non-numeric/zero/negative N, database errors or SQL errors cause a clear error rather than a misleading successful report. Invalid arguments exit with a non-zero status.

### Primary Actor
Analyst

### Trigger
The analyst requests the Top-N Country City Report with a country code and N.

## MAIN SUCCESS SCENARIO
1. Analyst runs `city-top-country CHN 10`.
2. System checks that a country code and N have been provided.
3. System parses N and checks that N > 0.
4. City report service checks that the country code is not blank and N is positive.
5. Repository joins `city.CountryCode` to `country.Code`.
6. The SQL statement uses `WHERE city.CountryCode = ?`.
7. SQL orders by `city.Population DESC` and applies `LIMIT ?`.
8. Repository maps results into existing `City` objects.
9. Formatter prints the four columns and at most 10 city rows.
10. Analyst views the report.

## EXTENSIONS
1. **Missing country or N**: show usage error; exit with failure.
2. **Blank country**: show `Country must be provided.`; exit with failure.
3. **N is zero, negative, nonnumeric or too large for Java `int`**: show `N must be a positive whole number.`; exit with failure.
4. **Unknown country code** (for example `XXX`): report header plus `No cities found.`
5. **N exceeds available cities**: display every matching city, not duplicate rows.
6. **Database/query failure**: show an error; do not produce a false successful report.

## SUB-VARIATIONS
1. Use **ISO 3-letter country codes** from `country.Code`, following Requirement 10; do not filter by full country name.
2. Both country code and N must be passed using `PreparedStatement` placeholders; never concatenate user input into SQL.
3. Apply `ORDER BY city.Population DESC` **before** `LIMIT ?`.
4. The formatter may add a summary/total row; this is not counted as one of the N city rows.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports