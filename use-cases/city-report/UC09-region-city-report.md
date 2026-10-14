# USE CASE: UC09 View Region City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants cities in one region (e.g. Eastern Asia) largest to smallest.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project built, app running, active `world` DB connection.
- Region value provided (e.g. `Eastern Asia`).
- City model + world/continent reports already work (Req 7-8).

### Success End Condition
Cities where `country.Region = ?` displayed with Name, Country, District, Population ordered DESC.

### Failed End Condition
Build/DB/query fail -> error, no fake report. Blank region -> error + non-zero exit.

### Primary Actor
Analyst

### Trigger
Analyst requests Region City Report with region name.

## MAIN SUCCESS SCENARIO
1. Analyst requests Region City Report + region.
2. System validates region non-blank.
3. System confirms DB connection.
4. System runs parameterized query with `WHERE country.Region = ?`.
5. System maps + orders DESC.
6. System displays `Cities in region: Eastern Asia` report.
7. Analyst views report.

## EXTENSIONS
1. **Blank region**: `Region must be provided.` + exit 1.
2. **Unknown region**: header + `No cities found.`
3. **DB/query fail**: failure message, no partial data.

## SUB-VARIATIONS
1. Region filter uses `country.Region` via JOIN, not `city` table.
2. Must use `PreparedStatement`.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports