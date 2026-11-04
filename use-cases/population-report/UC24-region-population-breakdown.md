# USE CASE: UC24 View Population Breakdown by Region

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst wants to view the total population, the population living in cities, and the population not living in cities for every region in the database, including the exact percentage each group contributes to the region total, so that regional urbanization rates can be compared and development resources directed effectively.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and the application is running.
- The application has an active connection to the `world` database.
- Requirements 1-16 are available from the completed releases.
- The `country.Region`, `country.Population`, `city.Population`, and `country.Code` fields are available.
- No command arguments are required; the report aggregates every region in the database.

### Success End Condition

The system displays one row per region in the database with exactly four data points: the region name, its total population, its city population with the exact percentage of the region total, and its non-city population with the exact percentage of the region total. Rows are ordered from largest to smallest total population, and a Total summary row is appended.

### Failed End Condition

The report is not generated when the database connection or SQL query fails. The application displays a clear error and exits with a failure status rather than displaying a misleading report.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests the Region Population Breakdown Report.

## MAIN SUCCESS SCENARIO

1. The analyst runs `region-population`.
2. The system verifies that no extra arguments were supplied.
3. The system confirms the database connection.
4. The repository sums `country.Population` grouped by `country.Region`.
5. The repository sums `city.Population` for each region through a derived table joined on `country.Code`, aggregated separately so country rows are never multiplied.
6. The repository left-joins the city totals onto the region totals and coalesces a missing city sum to zero.
7. The repository orders the rows with `ORDER BY TotalPopulation DESC`.
8. The repository maps each row to a `PopulationBreakdown`, computing the non-city population as Total Region Population - Sum(City Populations).
9. The formatter displays the Region, Total Population, City Population, and Non-City Population columns, each city and non-city cell formatted as `value (percent%)`.
10. The formatter appends a Total row with the summed populations and their percentages of the grand total.
11. The analyst compares urbanization rates across regions.

## EXTENSIONS

1. **Extra arguments supplied**: display `Usage: region-population` and exit with failure.
2. **Database connection fails**: display a clear error and exit with failure.
3. **Query fails**: display a clear error and do not show a successful report.
4. **Region has zero recorded cities**: report city population as `0 (0.00%)` and the full total as non-city without crashing.
5. **Region has zero total population**: report both percentages as `0.00%` without a division-by-zero exception or arithmetic error.
6. **No rows are returned**: display `No population data found.` without crashing.

## SUB-VARIATIONS

1. Regions come from `country.Region` values present in the database, e.g. `Eastern Asia`.
2. City populations are summed only from `city.Population` joined through `city.CountryCode = country.Code`.
3. The non-city population is always Total - Sum(City Populations), never computed by re-joining `city` before aggregation.
4. Percentages are calculated as `value * 100.0 / total` and formatted cleanly to two decimal places with a `%` suffix using `Locale.US`.
5. A zero or negative total short-circuits the percentage to `0.00%`.
6. The report contains exactly four columns: Region, Total Population, City Population, Non-City Population.
7. The report is ordered with `ORDER BY TotalPopulation DESC`.

## SCHEDULE

**DUE DATE**: Sprint 5 - Create Population Breakdown Reports
