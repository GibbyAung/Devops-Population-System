# USE CASE 4: View Population Demographics

## CHARACTERISTIC INFORMATION

**Goal in Context**
As an analyst, I want to view population demographics (total population, urban population, and rural population percentages) for various regions, so that I can analyze urbanization and population sizes.

**Scope**
DevOps Population Reporting System.

**Level**
Primary task.

**Preconditions**
1. The MySQL database containing the world demographic data must be running and accessible.
2. The Java application must be successfully connected to the database.

**Success End Condition**
The system successfully queries the database and outputs a formatted console table containing the Area Name, Total Population, Urban Population (raw number and percentage), and Rural Population (raw number and percentage) as requested.

**Failed End Condition**
The system fails to connect to the database or fails to retrieve the data, outputting a handled error message to the terminal without crashing.

**Primary Actor**
Demographic Analyst.

**Trigger**
The analyst executes the system and selects the option to view population demographics for a specific area.

## MAIN SUCCESS SCENARIO
1. The analyst selects the "View Population Demographics" function.
2. The analyst specifies the report type (Continent, Region, Country breakdown, or Total Population queries).
3. The system connects to the MySQL database.
4. The system executes the SQL query to calculate and extract demographic data.
5. The system formats the retrieved SQL ResultSet into a readable string table.
6. The system prints the completed report to the terminal.

## EXTENSIONS
None.

## SUB-VARIATIONS
**2a. Total Population Only (Reqs 26-31):** The analyst requests only the total population of a specific area (World, Continent, Region, Country, District, City).
* System modifies the query to return only the Area Name and Total Population without urban/rural calculations.

## SCHEDULE
**DUE DATE:** Release 1