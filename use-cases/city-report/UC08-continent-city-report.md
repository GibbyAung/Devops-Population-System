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
