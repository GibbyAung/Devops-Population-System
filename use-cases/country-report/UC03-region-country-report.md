# USE CASE: UC03 View Countries in a Region Report

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to view all countries within a selected region,
organised from the largest population to the smallest, so that
population distribution within that region can be analysed.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project has been successfully built using the required Java environment.
- The application is running successfully.
- The application has an active connection to the `world` database.
- Country and city data are available in the database.
- The shared Country Report functionality from Requirements 1 and 2 is available.

### Success End Condition

The system displays all countries belonging to the selected region
in descending population order. The report contains Code, Name,
Continent, Region, Population, and Capital.

### Failed End Condition

The Region Country Report is not generated if the application
cannot start, the database connection fails, or the region query
cannot be completed. An appropriate error message is displayed.

### Primary Actor

Analyst

### Trigger

The analyst requests a country report for a selected region.

## MAIN SUCCESS SCENARIO

1. The analyst requests the Region Country Report.
2. The analyst provides a region.
3. The system confirms that the database connection is available.
4. The system receives the selected region as input.
5. The system executes a parameterized query for the selected region.
6. The system joins each country with its associated capital city.
7. The system maps the database values to Country objects.
8. The system orders the countries by population in descending order.
9. The system prepares the six required report columns.
10. The system displays the completed Region Country Report.
11. The analyst views the report.

## EXTENSIONS

2. **No region value is provided**: The system prevents the region
   report from being executed until an appropriate region is supplied.

3. **Database connection unavailable**: The system displays a
   database connection failure message and ends the use case.

5. **Database query fails**: The system reports the failure and
   does not display incomplete data.

5. **No countries match the region**: The system displays
   `No countries found.`

6. **Capital information is unavailable**: The system displays
   `Unknown` for the capital while retaining the country in the report.

## SUB-VARIATIONS

1. Different valid regions may be supplied, such as `Eastern Asia`
   or `Western Europe`.
2. A country without a matching capital record remains in the report.
3. Population values differ, but results remain ordered from largest
   to smallest.
4. The region value is supplied using a parameterized
   `PreparedStatement`.

## SCHEDULE

**DUE DATE**: Sprint 2 - Create Country Reports

Requirement 3 must be implemented, tested, reviewed, demonstrated,
and accepted before the Requirement 4 handoff begins.