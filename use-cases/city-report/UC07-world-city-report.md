# USE CASE: UC07 View World City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
The analyst wants to view all cities in the world organised largest to smallest population so global urban distribution can be analysed.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project built with required Java environment.
- Application running.
- Active connection to `world` database.
- `city` and `country` data available.

### Success End Condition
System displays all cities with Name, Country, District, Population ordered DESC.

### Failed End Condition
Report not generated if build fails, DB connection fails, or query fails. System shows error, no fake report.

### Primary Actor
Analyst

### Trigger
Analyst requests World City Report.

## MAIN SUCCESS SCENARIO
1. Analyst requests World City Report.
2. System confirms DB connection.
3. System retrieves city records joined to country for Country name.
4. System maps rows to City data.
5. System orders by Population DESC.
6. System prepares columns Name, Country, District, Population.
7. System displays report in terminal.
8. Analyst views report.

## EXTENSIONS
1. **Build/start fails**: env reports error, fix before retry.
2. **DB unavailable**: error + non-zero exit, use case ends.
3. **Query fails**: city report failure message, no partial data.
4. **No rows**: header + `No cities found.`

## SUB-VARIATIONS
1. Country name comes from `country.Name` via `city.CountryCode = country.Code`.
2. All populations ordered largest to smallest.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports
File: use-cases/city-report/UC08-continent-city-report.md

# USE CASE: UC08 View Continent City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants cities in one continent (e.g. Asia) largest to smallest.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Same as UC07 + continent value provided (e.g. `Asia`).

### Success End Condition
Cities where `country.Continent = ?` displayed with 4 columns ordered DESC.

### Failed End Condition
Same as UC07. Blank continent -> error + non-zero exit.

### Primary Actor
Analyst

### Trigger
Analyst requests Continent City Report with continent name.

## MAIN SUCCESS SCENARIO
1. Analyst requests Continent City Report + continent (e.g. `Asia`).
2. System validates continent non-blank.
3. System confirms DB connection.
4. System runs parameterized query with `WHERE country.Continent = ?`.
5. System maps + orders DESC.
6. System displays `Cities in continent: Asia` report.
7. Analyst views report.

## EXTENSIONS
1. **Blank continent**: `Continent must be provided.` + exit 1.
2. **Unknown continent**: header + `No cities found.` (not crash).
3. **DB/query fail**: same as UC07.

## SUB-VARIATIONS
1. Continent filter uses `country.Continent`, not `city` table.
2. Must use `PreparedStatement`, no string concatenation.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports