# USE CASE: UC06 View Top-N Country Report by Region

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to view the N most populated countries in a selected region, ordered from the largest population to the smallest, so that population distribution within that region can be compared quickly.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project has been built with the required Java environment.
- The application can connect to the `world` MySQL database.
- Country and city data are available.
- The analyst supplies a valid region.
- The analyst supplies a positive whole-number value for N.

### Success End Condition

The system displays no more than N countries belonging to the selected region, ordered by population descending. The report contains Code, Name, Continent, Region, Population, and Capital.

### Failed End Condition

The report is not generated when the region is missing or blank, N is missing or invalid, the database connection fails, or the query fails. The system displays a useful error message and does not display incomplete data.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests a Top-N country report for a region.

## MAIN SUCCESS SCENARIO

1. The analyst requests the region Top-N report.
2. The analyst supplies a region, such as `Eastern Asia`.
3. The analyst supplies a positive whole-number N.
4. The system confirms that the database connection is available.
5. The system validates the region and N.
6. The system executes a parameterized query for the selected region.
7. The system joins countries with their capital city information.
8. The system orders results by population descending.
9. The system limits the result to no more than N rows.
10. The system displays Code, Name, Continent, Region, Population, and Capital.
11. The analyst views the completed report.

## EXTENSIONS

1. **Region is missing or blank**: The system reports that a region is required.
2. **N is missing or non-numeric**: The system reports that N must be a positive whole number.
3. **N is zero or negative**: The system rejects N before querying the database.
4. **No countries match**: The system displays `No countries found.`
5. **Database connection or query fails**: The system displays a handled error message and no incomplete report.
6. **Capital information is unavailable**: The system displays `Unknown` and keeps the country in the report.

## SUB-VARIATIONS

1. The analyst may select regions such as `Eastern Asia` or `Western Europe`.
2. N may be 1, 5, 10, or another positive whole number.
3. The region and N are supplied safely to the database query.

## SCHEDULE

**DUE DATE:** Sprint 2 - Create Country Reports

Requirement 6 must be implemented, tested, reviewed, demonstrated, and accepted before the next handoff.