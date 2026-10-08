# USE CASE: UC02 View Countries in a Continent Report

## CHARACTERISTIC INFORMATION

### Goal in Context

The analyst wants to view a report of all countries within a selected continent organised from the largest population to the smallest so that population distribution within that continent can be analysed.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The project has been successfully built using the required Java environment.
- The application is running successfully.
- The application has an active connection to the `world` database.
- Country and city data are available in the database.
- The shared Country Report functionality from Requirement 1 is available.

### Success End Condition

The system successfully displays all countries belonging to the selected continent in descending population order. The report contains the columns Code, Name, Continent, Region, Population, and Capital.

### Failed End Condition

The Continent Country Report is not generated if the application cannot be built or started, the database connection fails, the continent query cannot be completed, or the provided continent does not match any available records. The system displays an appropriate message describing the result or failure.

### Primary Actor

Analyst

### Trigger

The analyst requests a country report for a selected continent.

## MAIN SUCCESS SCENARIO

1. The analyst requests the Continent Country Report.
2. The analyst provides the name of a continent.
3. The system confirms that the database connection is available.
4. The system receives the selected continent as input.
5. The system executes a parameterized query to retrieve countries belonging to the selected continent.
6. The system joins each country with its corresponding capital city information.
7. The system maps the retrieved database values to the country report data.
8. The system orders the countries by population in descending order.
9. The system prepares the report with the columns Code, Name, Continent, Region, Population, and Capital.
10. The system displays the completed Continent Country Report in the terminal.
11. The analyst views the report.

## EXTENSIONS

1. **Application cannot be built or started**: The development environment reports the build or runtime error. The issue must be corrected before the Continent Country Report can be executed.

2. **No continent value is provided**: The system uses the configured default continent value, if one is available, or prevents the report from being generated until a valid continent input is supplied.

3. **Database connection is unavailable**: The system displays an error message indicating that the report cannot be generated because the database connection failed, and the use case ends.

5. **Database query fails**: The system displays an appropriate continent report failure message and does not display incomplete report data.

5. **No countries match the selected continent**: The system displays a message indicating that no countries were found.

6. **Capital information is unavailable for a country**: The system displays `Unknown` for the capital while keeping the country in the report.

## SUB-VARIATIONS

1. The analyst may request different continent values, such as Asia, Europe, Africa, North America, South America, Oceania, or Antarctica.
2. A country may not have a matching capital city record; the country must still remain in the report.
3. Country population values vary, but all returned countries are ordered from the largest population to the smallest.
4. The selected continent is passed to the database query using a parameterized `PreparedStatement` rather than being concatenated directly into SQL.

## SCHEDULE

**DUE DATE**: Sprint 2 - Create Country Reports

Requirement 2 must be implemented, built, tested, reviewed, demonstrated, and accepted before the Requirement 3 handoff begins.