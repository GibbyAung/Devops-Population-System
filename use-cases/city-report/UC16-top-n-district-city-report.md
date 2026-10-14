# USE CASE: UC16 View Top N Populated Cities in a District

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants to enter a district name and a positive whole number N to see the N most populated cities in that district.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project is built, application is running, and the `world` MySQL database is accessible.
- Requirements 7-15 city report features are available on `feature/city-reports`.
- Analyst supplies a non-blank district name, for example `California`.
- Analyst supplies a positive whole number N, such as `10`.

### Success End Condition
The terminal displays **at most N** cities matching the provided district, with Name, Country, District and Population columns, ordered by population descending. Exactly N city rows are displayed when N or more cities match.

### Failed End Condition
Missing/blank district, missing/non-numeric/zero/negative N, database errors or SQL errors cause a clear error rather than a misleading successful report. Invalid arguments exit with a non-zero status.

### Primary Actor
Analyst

### Trigger
The analyst requests the Top-N District City Report with a district name and N.

## MAIN SUCCESS SCENARIO
1. Analyst runs `city-top-district California 10`.
2. System checks that a district and N were provided.
3. System parses N and checks that N > 0.
4. City report service checks that the district is not blank and N is positive.
5. Repository joins `city.CountryCode` to `country.Code` so the output includes country names.
6. SQL uses `WHERE city.District = ?`.
7. SQL orders by `city.Population DESC` and applies `LIMIT ?`.
8. Repository maps results into existing `City` objects.
9. Formatter prints the four columns and at most 10 city rows.
10. Analyst views the report.

## EXTENSIONS
1. **Missing district or N**: show usage error; exit with failure.
2. **Blank district**: show `District must be provided.`; exit with failure.
3. **N is zero, negative, nonnumeric or too large for Java `int`**: show `N must be a positive whole number.`; exit with failure.
4. **Unknown district**: report header plus `No cities found.`
5. **N exceeds available cities**: display every matching city, not duplicate rows.
6. **Database/query failure**: show an error; do not produce a false successful report.

## SUB-VARIATIONS
1. Filter **`city.District`**, not `country.Region` or `country.Continent`.
2. District matching is based on the district name alone, as in Requirement 11. If districts with identical names occur in different countries, they all match. A country-plus-district filter requires a separate agreed requirement.
3. Put multi-word district names in quotes: `city-top-district "New York" 10`.
4. Bind both district and N with a `PreparedStatement`.
5. Apply `ORDER BY city.Population DESC` **before** `LIMIT ?`.
6. The formatter's summary/total row does not count toward the N city rows.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports