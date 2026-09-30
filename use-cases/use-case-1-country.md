# USE CASE 1: Generate Country Reports

## CHARACTERISTIC INFORMATION

**Goal in Context**
As an analyst, I want a report of all countries organized by largest population to smallest (globally, by continent, or by region), so that I can see population distributions.

**Scope**
DevOps Population Reporting System.

**Level**
Primary task.

**Preconditions**
1. The MySQL database containing the world demographic data must be running and accessible.
2. The Java application must be successfully connected to the database.

**Success End Condition**
The system successfully queries the database and outputs a formatted console table containing the Code, Name, Continent, Region, Population, and Capital of the requested countries.

**Failed End Condition**
The system fails to connect to the database or fails to retrieve the data, outputting a handled error message to the terminal without crashing.

**Primary Actor**
Demographic Analyst.

**Trigger**
The analyst executes the system and selects the option to generate a global, continental, or regional country report.

## MAIN SUCCESS SCENARIO
1. The analyst selects the "Generate Country Report" function.
2. The analyst specifies the scope (World, Continent, or Region).
3. The system connects to the MySQL database.
4. The system executes the SQL query to extract country data, ordered by population descending.
5. The system formats the retrieved SQL ResultSet into a readable string table.
6. The system prints the completed report to the terminal.

## EXTENSIONS
**2a. Filter by Top 'N' Entries (Reqs 4-6):** The analyst provides a specific integer (N).
* System modifies the SQL query to include a `LIMIT N` clause.
* System outputs only the top N populated countries.

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE:** Release 1