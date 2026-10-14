# USE CASE: UC12 View Top N Populated Cities (World)

## CHARACTERISTIC INFORMATION

### Goal in Context
Analyst gives a number N (e.g. 10) to see the N most populated cities in the world.

### Scope
Population Reporting System

### Level
Primary task

### Preconditions
- Project built, app running, active `world` DB connection.
- N is a positive integer (e.g. `10`).
- Existing city model, repository, service, formatter work.

### Success End Condition
At most N cities worldwide displayed with Name, Country, District, Population,
ordered by population DESC; exactly N when at least N cities exist.

### Failed End Condition
Build/DB/query fail -> error, no fake report. Missing/invalid N -> error + non-zero exit.

### Primary Actor
Analyst

### Trigger
Analyst requests Top N Populated Cities (World) with number N.

## MAIN SUCCESS SCENARIO
1. Analyst requests Top N World City Report + N.
2. System parses N as an integer.
3. System validates N > 0.
4. System confirms DB connection.
5. System queries all world cities with `ORDER BY city.Population DESC LIMIT ?`.
6. System displays `Top 10 populated cities in the world` report.
7. Analyst views report.

## EXTENSIONS
1. **Missing / nonnumeric N**: `N must be a positive integer.` + exit 1.
2. **N = 0 or negative**: validation error + exit 1.
3. **N larger than available cities**: display all available cities.
4. **DB/query fail**: failure message, no partial data.

## SUB-VARIATIONS
1. Top N is for the **whole world** (no country/region/district WHERE filter).
2. Use `PreparedStatement` for `LIMIT ?`.
3. `ORDER BY city.Population DESC` must come BEFORE `LIMIT ?`.

## SCHEDULE
**DUE DATE**: Sprint 3 - Create City Reports
