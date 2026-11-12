# USE CASE: UC19 View All Capital Cities in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst wants to provide a region and view every capital city in that region, ordered from largest to smallest capital-city population.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The application is built and can connect to the `world` database.
- The analyst provides a non-blank region, such as `Southeast Asia`.
- Requirements 17 and 18 are available on the Capital City Reports branch.
- `country.Capital`, `city.ID`, and `country.Region` are available.

### Success End Condition

The system displays all matching capital cities with the columns Name, Country, and Population, ordered by `city.Population` descending. If the region has no matching capital cities, the system displays a clear no-results message.

### Failed End Condition

The report is not generated if the region is missing or blank, the database connection fails, or the query fails. The application displays a clear error and exits with a failure status for invalid input or operational failure.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests a Region Capital City Report and provides a region.

## MAIN SUCCESS SCENARIO

1. The analyst requests all capital cities in a region.
2. The system checks that the region is present and not blank.
3. The system confirms the database connection.
4. The repository joins `country.Capital = city.ID` to select capital cities only.
5. The repository filters the results with `country.Region = ?`.
6. The repository orders the results by `city.Population DESC`.
7. The system maps the rows to CapitalCity objects.
8. The formatter displays Name, Country, and Population.
9. The analyst views the report.

## EXTENSIONS

1. **Missing or blank region**: display `Region must be provided.` and exit with failure.
2. **Unknown region or no matching capital cities**: display `No capital cities found.` without crashing.
3. **Database connection fails**: display a clear error and exit with failure.
4. **Query fails**: display a clear error and do not present a misleading successful report.
5. **Country has no matching capital-city row**: exclude that country from the report.

## SUB-VARIATIONS

1. Region values are matched against `country.Region`, for example `Southeast Asia` or `Western Europe`.
2. Capital cities are identified only through `country.Capital = city.ID`.
3. The displayed population is `city.Population`, not `country.Population`.
4. Bind the region using `PreparedStatement`; do not concatenate user input into SQL.
5. Display only the required data columns: Name, Country, and Population.

## SCHEDULE

**DUE DATE**: Sprint 4 - Create Capital City Reports
