# USE CASE: UC10 View Country City Report

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst wants cities in one country (e.g. CHN) largest to smallest.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Same as UC09 + country code provided.
- Agreed input is ISO CountryCode (e.g. `CHN`, `GBR`, `USA`), NOT full name. Document on Zube card.

### Success End Condition
Cities where `city.CountryCode = ?` displayed with 4 columns ordered DESC.

### Failed End Condition
Same as UC09. Blank code -> error + exit 1.

### Primary Actor
Analyst

### Trigger
Analyst requests Country City Report with country code.

## MAIN SUCCESS SCENARIO
1. Analyst requests Country City Report + code (e.g. `CHN`).
2. System validates code non-blank.
3. System confirms DB connection.
4. System runs `WHERE city.CountryCode = ?` ordered DESC.
5. System maps + displays `Cities in country: CHN`.
6. Analyst views report.

## EXTENSIONS
1. **Blank code**: `Country must be provided.` + exit 1.
2. **Unknown code** (e.g. `XXX`): header + `No cities found.`
3. **Full name entered instead of code** (e.g. `China`): returns empty - user must use code. Document this.

## SUB-VARIATIONS
1. Uses `city.CountryCode` directly, no need for country name filter.
2. If team later wants name search, add separate method - do not mix in this Sprint.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports