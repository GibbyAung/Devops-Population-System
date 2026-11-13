# USE CASE: UC25 View Country Population Breakdown

## CHARACTERISTIC INFORMATION

### Goal in Context
The demographic analyst wants to view each country's total, urban, and rural population with exact percentages so national demographic distributions can support policy and market analysis.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- The application is built and running.
- The application has an active connection to the `world` database.
- The `country` and `city` tables are available.
- No command arguments are required.

### Success End Condition
The system displays one row for every country with its name, total population, city population and percentage, and non-city population and percentage.

### Failed End Condition
The report is not generated when the database connection or query fails. The system displays a clear failure message without showing incomplete data.

### Primary Actor
Demographic Analyst

### Trigger
The analyst runs `country-population`.

## MAIN SUCCESS SCENARIO
1. The analyst runs `country-population`.
2. The system validates that no extra arguments were supplied.
3. The system confirms the database connection.
4. The repository left-joins `country` to `city` by country code and groups by country.
5. The system uses zero for missing city totals and calculates rural population as total minus urban population.
6. The formatter calculates and displays both percentages safely.
7. The system displays the completed country report in the terminal.

## EXTENSIONS
1. **Extra arguments**: display `Usage: country-population` and exit with failure.
2. **Database connection failure**: display a clear error and stop.
3. **Query failure**: display a clear error without a successful report.
4. **Country has no cities**: display urban population as `0 (0.00%)` and retain the country.
5. **Zero total population**: display both percentages as `0.00%` without division by zero.
6. **Empty result**: display `No population data found.` without crashing.

## SUB-VARIATIONS
1. Countries are ordered by descending total population.
2. Urban population is the sum of matching `city.Population` values.
3. Rural population is `country.Population - urban population`.

## SCHEDULE
**DUE DATE**: Sprint 5 - Create Population Breakdown Reports
