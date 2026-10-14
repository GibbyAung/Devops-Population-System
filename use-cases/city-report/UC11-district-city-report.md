# USE CASE: UC11 View District City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants all cities in one district (e.g. California), largest to smallest.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project built, app running, active `world` DB connection.
- District value provided (e.g. `California`).
- City reports from Req 7-10 already work.

### Success End Condition
All cities where `city.District = ?` are displayed with Name, Country,
District, Population, ordered DESC. No row limit.

### Failed End Condition
Build/DB/query fail -> error, no fake report. Blank district -> error + non-zero exit.

### Primary Actor
Analyst

### Trigger
Analyst requests District City Report with district name.

## MAIN SUCCESS SCENARIO
1. Analyst requests District City Report + district.
2. System validates district non-blank.
3. System confirms DB connection.
4. System runs parameterized query with `WHERE city.District = ?`.
5. System retrieves **all** matching cities, ordered by population DESC.
6. System displays `Cities in district: California` report.
7. Analyst views report.

## EXTENSIONS
1. **Blank district**: `District must be provided.` + exit 1.
2. **Unknown district**: header + `No cities found.`
3. **DB/query fail**: failure message, no partial data.

## SUB-VARIATIONS
1. Filter uses `city.District`, NOT `country.Region`.
2. Must use `PreparedStatement`.
3. No country restriction unless a separate requirement specifies one.
4. No `LIMIT`: display every matching city.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports
