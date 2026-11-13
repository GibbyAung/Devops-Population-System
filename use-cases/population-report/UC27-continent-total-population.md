# USE CASE: UC27 Continent Total Population

## CHARACTERISTIC INFORMATION

### Goal in Context
A Demographic Analyst wants the total population of one continent
to reference its overall market size during executive briefings.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Application built.
- Active connection to the world database.
- country table available.
- Analyst supplies a continent name, for example Asia.

### Success End Condition
The terminal displays the continent name and the sum of the
population of every country within that continent.

The application uses Java long for the total.

### Failed End Condition
Null, blank, or unknown continent names produce a readable error
and a non-zero exit status.

Database or query failures produce a readable error without
displaying a fabricated total.

### Primary Actor
Demographic Analyst

### Trigger
Analyst runs:
continent-total-population Asia

## MAIN SUCCESS SCENARIO
1. Analyst requests the report with a continent name.
2. System checks that exactly one continent argument is supplied.
3. System rejects null or blank input.
4. System removes leading and trailing whitespace.
5. System executes a parameterized country-table aggregation.
6. System reads the total using getLong().
7. System displays Continent and Total Population.
8. Analyst views the report.

## EXTENSIONS
1. Missing argument:
   Display command usage and exit with status 1.
2. Null or blank continent:
   Display "Continent must be provided." and exit with status 1.
3. Unknown continent:
   Display "Unknown continent: <input>" and exit with status 1.
4. Recognised continent with population zero:
   Display its name and total population 0.
5. Database or query failure:
   Display a failure message without a stack trace or fake report.

## SUB-VARIATIONS
1. Filter using country.Continent = ?.
2. Use PreparedStatement.
3. Sum country.Population directly.
4. Use Java long and a 64-bit SQL result.
5. Display the continent name returned by the database.

## SCHEDULE
DUE DATE: Team-agreed population reporting sprint.