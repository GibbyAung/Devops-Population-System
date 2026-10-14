\# USE CASE: UC13 View Top N Populated Cities in a Continent



\## CHARACTERISTIC INFORMATION



\### Goal in Context

The analyst wants to provide a continent and a positive integer N to view the N most populated cities in that continent.



\### Scope

Population Reporting System



\### Level

Primary task



\### Preconditions

\- The project is built and the application is running.

\- The application has an active connection to the `world` database.

\- A non-blank continent is provided, for example `Asia`.

\- N is a positive whole number, for example `10`.

\- Requirements 7-12 are available on the City Reports branch.



\### Success End Condition

The system displays at most N cities from the selected continent with Name, Country, District, and Population, ordered by population from largest to smallest. It displays exactly N rows when at least N matching cities exist.



\### Failed End Condition

The report is not generated if the database or query fails, the continent is missing, or N is missing, nonnumeric, zero, or negative. The application displays a clear error and exits with a failure status.



\### Primary Actor

Analyst



\### Trigger

The analyst requests a Top-N Continent City Report with a continent and N.



\## MAIN SUCCESS SCENARIO

1\. The analyst requests the report with a continent and N.

2\. The system checks that the continent is not blank.

3\. The system parses N as an integer and validates N > 0.

4\. The system confirms the database connection.

5\. The system joins `city.CountryCode` to `country.Code`.

6\. The system filters with `country.Continent = ?`.

7\. The system orders by `city.Population DESC` and applies `LIMIT ?`.

8\. The system maps the rows to City objects.

9\. The system displays the four required columns and at most N rows.

10\. The analyst views the report.



\## EXTENSIONS

1\. \*\*Missing or blank continent\*\*: display `Continent must be provided.` and exit with failure.

2\. \*\*Missing or nonnumeric N\*\*: display a positive-whole-number error and exit with failure.

3\. \*\*N is zero or negative\*\*: reject N and exit with failure.

4\. \*\*Unknown continent\*\*: display `No cities found.` without crashing.

5\. \*\*N exceeds available rows\*\*: display all matching cities.

6\. \*\*Database/query failure\*\*: display an error and do not show a misleading successful report.



\## SUB-VARIATIONS

1\. The continent comparison uses the values stored in `country.Continent`, such as `Asia` or `Europe`.

2\. Both the continent and N are bound through `PreparedStatement`; input is not concatenated into SQL.

3\. `ORDER BY city.Population DESC` appears before `LIMIT ?`.



\## SCHEDULE

\*\*DUE DATE\*\*: Sprint 3 - Create City Reports

