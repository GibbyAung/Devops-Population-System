
# USE CASE: UC22 View Top N Populated Capital Cities in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst wants to provide a region and a positive whole number N to view the N most populated capital cities within that region, ordered from largest to smallest population.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and running.
- The application can connect to the `world` database.
- Requirements 17-21 are implemented and available on the shared Sprint 4 branch.
- The analyst supplies a non-blank region, such as `Southeast Asia`.
- The analyst supplies a positive whole number N, such as `10`.
- The `country.Capital`, `city.ID`, and `country.Region` fields are available.

### Success End Condition

The system displays up to N capital cities in the selected region with Name, Country, and Population, ordered from highest capital-city population to lowest. It shows exactly N city rows when at least N eligible capitals exist, otherwise all available rows.

### Failed End Condition

The report is not generated when the database or SQL query fails or the region or N arguments are missing or invalid. The application displays a clear error and exits with failure for invalid input.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests the Top-N Region Capital City Report, providing the region and N.

## MAIN SUCCESS SCENARIO

1. The analyst runs `capital-top-region "Southeast Asia" 10`.
2. The system verifies that a region and N were provided.
3. The system parses N as a positive whole number.
4. The service verifies the region is not null or blank and N is positive.
5. The system confirms the database connection.
6. The repository joins `country.Capital = city.ID` to identify capital cities only.
7. The repository filters using `WHERE country.Region = ?`.
8. The repository orders cities using `ORDER BY city.Population DESC`.
9. The repository applies `LIMIT ?` to return up to N rows.
10. The repository maps rows to `CapitalCity` objects.
11. The formatter displays the Name, Country, and Population columns.
12. The analyst reviews the report.

## EXTENSIONS

1. **Missing region or N**: display usage `capital-top-region <region> <N>` and exit with failure.
2. **Null, empty, or blank region**: display `Region must be provided.` and exit with failure.
3. **N is nonnumeric or not a whole number**: display `N must be a positive whole number.` and exit with failure.
4. **N is zero or negative**: reject the limit and exit with failure.
5. **Unknown region**: display `No capital cities found.` without crashing.
6. **N is greater than available capitals**: display all available capitals without adding fake rows.
7. **Database connection or SQL query fails**: display a clear error, do not show a misleading report, and exit with failure.
8. **Country has no matching capital city**: exclude that country from the results.

## SUB-VARIATIONS

1. Use `country.Region` values present in the database, e.g. `Southeast Asia` and `Western Europe`.
2. Capital cities are determined only by `country.Capital = city.ID`.
3. The displayed country is `country.Name` and the population is `city.Population`.
4. Bind region with `PreparedStatement.setString(1, region)`.
5. Bind N with `PreparedStatement.setInt(2, n)`.
6. `ORDER BY city.Population DESC` must appear before `LIMIT ?`.
7. Display exactly the three specified data columns: Name, Country, Population.

## SCHEDULE

**DUE DATE**: Sprint 4 - Create Capital City Reports
