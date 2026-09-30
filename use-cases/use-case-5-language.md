# USE CASE 5: View Language Statistics

## CHARACTERISTIC INFORMATION

**Goal in Context**
As an analyst, I want a report providing the number of people who speak Chinese, English, Hindi, Spanish, and Arabic, including the percentage of the world population, so that I can analyze global language distributions.

**Scope**
DevOps Population Reporting System.

**Level**
Primary task.

**Preconditions**
1. The MySQL database containing the world demographic data must be running and accessible.
2. The Java application must be successfully connected to the database.

**Success End Condition**
The system successfully queries the database and outputs a formatted console table containing the Language, Total Speakers, and Percentage of World Population for the specified languages, ordered from greatest number of speakers to smallest.

**Failed End Condition**
The system fails to connect to the database or fails to retrieve the data, outputting a handled error message to the terminal without crashing.

**Primary Actor**
Demographic Analyst.

**Trigger**
The analyst executes the system and selects the option to view language statistics.

## MAIN SUCCESS SCENARIO
1. The analyst selects the "View Language Statistics" function.
2. The system connects to the MySQL database.
3. The system executes the SQL query to calculate speakers and global percentages for Chinese, English, Hindi, Spanish, and Arabic, ordering the results descending.
4. The system formats the retrieved SQL ResultSet into a readable string table.
5. The system prints the completed report to the terminal.

## EXTENSIONS
None.

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE:** Release 1