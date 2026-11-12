# USE CASE: UC26 View World Total Population

## CHARACTERISTIC INFORMATION

### Goal in Context
The demographic analyst wants the total population of all countries so international demographic proportions and growth metrics have an accurate global baseline.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- The application is built and running.
- The application has an active connection to the `world` database.
- The `country` table is available.

### Success End Condition
The system displays `World` and the sum of all country populations as a 64-bit integer.

### Failed End Condition
The system displays a clear database error rather than crashing or displaying a misleading total.

### Primary Actor
Demographic Analyst

### Trigger
The analyst runs `world-population`.

## MAIN SUCCESS SCENARIO
1. The analyst runs `world-population`.
2. The system validates that no extra arguments were supplied.
3. The system confirms the database connection.
4. The repository sums `country.Population` using a database aggregate.
5. The system reads the result as a Java `long`/SQL `BIGINT`.
6. The formatter displays the labelled world total in the terminal.

## EXTENSIONS
1. **Extra arguments**: display `Usage: world-population` and exit with failure.
2. **Database connection failure**: display a clear error and stop without crashing.
3. **Query failure or connection drop**: display a clear report failure message.
4. **Empty country table**: display a total of `0` without crashing.

## SUB-VARIATIONS
1. The output scope is always labelled `World`.
2. The total uses `long` to prevent 32-bit integer overflow.

## SCHEDULE
**DUE DATE**: Sprint 5 - Create Population Breakdown Reports
