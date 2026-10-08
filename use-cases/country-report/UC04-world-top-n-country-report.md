# USE CASE: UC04 View Top-N World Country Report

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to view the N most populated countries in the world, ordered from the largest population to the smallest, so that the largest national populations can be compared quickly.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project has been built with the required Java environment.
- The application can connect to the `world` MySQL database.
- Country and city data are available.
- The analyst provides a positive whole-number value for N.

### Success End Condition

The system displays no more than N countries from the world database, ordered by population descending. The report contains the columns Code, Name, Continent, Region, Population, and Capital.

### Failed End Condition

The report is not generated when N is missing, non-numeric, zero, negative, the database connection fails, or the query fails. The application displays a useful error message and does not display an incomplete report.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests a Top-N world country report and supplies N.

## MAIN SUCCESS SCENARIO

1. The analyst requests the Top-N world country report.
2. The analyst supplies a positive whole-number value for N.
3. The system confirms that the database connection is available.
4. The system validates that N is greater than zero.
5. The system executes a safe query against the `world` database.
6. The system joins countries with their capital city information.
7. The system orders countries by population descending.
8. The system limits the result to no more than N rows.
9. The system maps the result to Country objects.
10. The system displays Code, Name, Continent, Region, Population, and Capital.
11. The analyst views the completed report.

## EXTENSIONS

1. **N is missing**: The system displays an error explaining that a positive N is required.
2. **N is not numeric**: The system displays an error explaining that N must be a whole number.
3. **N is zero or negative**: The system rejects the value before querying the database.
4. **Database connection is unavailable**: The system reports the connection failure and ends the use case.
5. **The query fails**: The system reports the failure and does not display incomplete data.
6. **N is greater than the number of countries**: The system displays all available countries and no more than N rows.
7. **Capital information is unavailable**: The system displays `Unknown` and keeps the country in the report.

## SUB-VARIATIONS

1. N may be 1, 5, 10, or another positive whole number.
2. Results remain ordered from largest population to smallest.
3. N is passed safely to the database query and is not concatenated into SQL.

## SCHEDULE

**DUE DATE:** Sprint 2 - Create Country Reports

Requirement 4 must be implemented, tested, reviewed, demonstrated, and accepted before the Requirement 5 handoff begins.