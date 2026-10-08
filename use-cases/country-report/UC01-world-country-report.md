# USE CASE: UC01 View World Country Report

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to view a report of all countries in the world organised from the largest population to the smallest so that global population distributions can be analysed.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project has been successfully built using the required Java environment.
- The application is running successfully.
- The application has an active connection to the `world` database.
- Country and city data are available in the database.

### Success End Condition

The system successfully displays all countries in the world in descending population order. The report contains the columns Code, Name, Continent, Region, Population, and Capital.

### Failed End Condition

The World Country Report is not generated if the application cannot be built or started, the database connection fails, or the country query cannot be completed. The system or development environment provides an appropriate error message so that the failure can be identified and corrected.

### Primary Actor

Analyst

### Trigger

The analyst requests the World Country Report.

## MAIN SUCCESS SCENARIO

1. The analyst requests the World Country Report.
2. The system confirms that the database connection is available.
3. The system retrieves all country records from the `world` database.
4. The system joins each country with its corresponding capital city information.
5. The system maps the retrieved database values to the country report data.
6. The system orders the countries by population in descending order.
7. The system prepares the report with the columns Code, Name, Continent, Region, Population, and Capital.
8. The system displays the completed World Country Report in the terminal.
9. The analyst views the report.

## EXTENSIONS

1. **Application cannot be built or started**: The development environment reports the build or runtime error. The issue must be corrected before the World Country Report can be executed.

2. **Database connection is unavailable**: The system displays an error message indicating that the report cannot be generated because the database connection failed, and the use case ends.

3. **Database query fails**: The system displays an appropriate country report failure message and does not display incomplete report data.

3. **No country records are returned**: The system displays a message indicating that no countries were found.

4. **Capital information is unavailable for a country**: The system displays `Unknown` for the capital while keeping the country in the report.

## SUB-VARIATIONS

1. A country may not have a matching capital city record; the country must still remain in the report.
2. Country population values vary, but all returned countries are ordered from the largest population to the smallest.

## SCHEDULE

**DUE DATE**: Sprint 2 - Create Country Reports

Requirement 1 must be implemented, built, tested, reviewed, demonstrated, and accepted before the Requirement 2 handoff begins.