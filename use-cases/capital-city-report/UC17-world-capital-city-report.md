# USE CASE: UC17 View All Capital Cities in the World

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst wants to view every capital city in the world ordered by population from largest to smallest.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and the application is running.
- The application has an active connection to the `world` database.
- Requirements 1-16 are available from the completed releases.
- The `country.Capital` and `city.ID` relationship is available.

### Success End Condition

The system displays every available capital city with Name, Country, and Population, ordered by population from largest to smallest.

### Failed End Condition

The report is not generated if the database connection or query fails. The application displays a clear error and exits with a failure status rather than displaying a misleading report.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests the World Capital City Report.

## MAIN SUCCESS SCENARIO

1. The analyst requests all capital cities in the world.
2. The system confirms the database connection.
3. The system joins `country.Capital` to `city.ID`.
4. The system selects the capital name, country name, and city population.
5. The system orders the rows by `city.Population DESC`.
6. The system maps the rows to CapitalCity objects.
7. The system displays Name, Country, and Population.
8. The analyst views the report.

## EXTENSIONS

1. **Database connection fails**: display a clear error and exit with failure.
2. **Query fails**: display a clear error and do not show a successful report.
3. **No rows are returned**: display `No capital cities found.` without crashing.
4. **Country has no matching capital city**: exclude that country because there is no valid capital-city row to display.

## SUB-VARIATIONS

1. Capital cities are identified by joining `country.Capital = city.ID`.
2. Population comes from `city.Population`, not `country.Population`.
3. The report is ordered with `ORDER BY city.Population DESC`.

## SCHEDULE

**DUE DATE**: Sprint 4 - Create Capital City Reports
