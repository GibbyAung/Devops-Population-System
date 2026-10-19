# USE CASE: UC18 View All Capital Cities in a Continent

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst wants to provide a continent and view every capital city in that continent ordered by population from largest to smallest.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and the application is running.
- The application has an active connection to the `world` database.
- A non-blank continent is provided, for example `Asia`.
- Requirement 17 has been completed and checked.

### Success End Condition

The system displays every available capital city in the selected continent with Name, Country, and Population, ordered by population from largest to smallest.

### Failed End Condition

The report is not generated if the database or query fails or the continent is missing or blank. The application displays a clear error and exits with a failure status for invalid input.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests a Continent Capital City Report and provides a continent.

## MAIN SUCCESS SCENARIO

1. The analyst requests the report with a continent.
2. The system checks that the continent is not blank.
3. The system confirms the database connection.
4. The system joins `country.Capital` to `city.ID`.
5. The system filters with `country.Continent = ?`.
6. The system orders the rows by `city.Population DESC`.
7. The system maps the rows to CapitalCity objects.
8. The system displays Name, Country, and Population.
9. The analyst views the report.

## EXTENSIONS

1. **Missing or blank continent**: display `Continent must be provided.` and exit with failure.
2. **Unknown continent**: display `No capital cities found.` without crashing.
3. **Database connection fails**: display a clear error and exit with failure.
4. **Query fails**: display a clear error and do not show a successful report.
5. **Country has no matching capital city**: exclude that country from the report.

## SUB-VARIATIONS

1. The continent comparison uses values stored in `country.Continent`, such as `Asia` or `Europe`.
2. The continent is bound through `PreparedStatement`; it is not concatenated into SQL.
3. Population comes from `city.Population`.

## SCHEDULE

**DUE DATE**: Sprint 4 - Create Capital City Reports
