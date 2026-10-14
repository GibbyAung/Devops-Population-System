# USE CASE: UC14 View Top N Populated Cities in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to provide a region and a positive integer N to view the N most populated cities in that region.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project is built and the application is running.
- The application has an active connection to the `world` database.
- A non-blank region is provided, for example `Eastern Asia`.
- N is a positive whole number, for example `10`.
- Requirement 13 has been completed and checked.

### Success End Condition

The system displays at most N cities from the selected region with Name, Country, District, and Population, ordered by population from largest to smallest. It displays exactly N rows when at least N matching cities exist.

### Failed End Condition

The report is not generated if the database or query fails, the region is missing, or N is missing, nonnumeric, zero, or negative. The application displays a clear error and exits with a failure status.

### Primary Actor

Analyst

### Trigger

The analyst requests a Top-N Region City Report with a region and N.

## MAIN SUCCESS SCENARIO

1. The analyst requests the report with a region and N.
2. The system checks that the region is not blank.
3. The system parses N as an integer and validates N > 0.
4. The system confirms the database connection.
5. The system joins `city.CountryCode` to `country.Code`.
6. The system filters with `country.Region = ?`.
7. The system orders by `city.Population DESC` and applies `LIMIT ?`.
8. The system maps the rows to City objects.
9. The system displays the four required columns and at most N rows.
10. The analyst views the report.

## EXTENSIONS

1. **Missing or blank region**: display `Region must be provided.` and exit with failure.
2. **Missing or nonnumeric N**: display a positive-whole-number error and exit with failure.
3. **N is zero or negative**: reject N and exit with failure.
4. **Unknown region**: display `No cities found.` without crashing.
5. **N exceeds available rows**: display all matching cities.
6. **Database/query failure**: display an error and do not show a misleading successful report.

## SUB-VARIATIONS

1. The region comparison uses values stored in `country.Region`, such as `Eastern Asia`.
2. Region names containing spaces must be quoted on the command line.
3. Both the region and N are bound through `PreparedStatement`; input is not concatenated into SQL.
4. `ORDER BY city.Population DESC` appears before `LIMIT ?`.

## SCHEDULE

**DUE DATE**: Sprint 3 - Create City Reports
