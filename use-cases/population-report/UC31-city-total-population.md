# USE CASE: UC31 View City Total Population

## CHARACTERISTIC INFORMATION

### Goal in Context

The demographic analyst provides a city name and views its recorded population.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- The application is built and can connect to the `world` database.
- The analyst provides a non-blank city name, for example `Yangon`.
- The Product Owner has confirmed how duplicate city names are handled.

### Success End Condition

The application displays City and Total Population for the matching city name. Under the agreed name-only behavior, every exact-name row in the `city` table is summed into one result.

### Failed End Condition

Missing, blank, or unknown city names produce a readable error and non-zero exit status. Database failures produce a readable error without a misleading total or Java stack trace.

### Primary Actor

Demographic Analyst

### Trigger

The analyst requests the total population of a city and provides its name.

## MAIN SUCCESS SCENARIO

1. The analyst runs `city-total-population "Yangon"`.
2. The system checks that exactly one city-name argument was provided.
3. The service rejects null or blank input and strips surrounding whitespace.
4. The repository binds the name through a PreparedStatement.
5. The repository calculates the total from matching `city.Population` values.
6. The service rejects an unknown city rather than fabricating a zero result.
7. The formatter displays the City and Total Population.
8. The analyst views the result.

## EXTENSIONS

1. Missing or extra argument: show usage and exit with failure.
2. Null or blank name: display `City must be provided.` and exit with failure.
3. Unknown city: display `Unknown city: <name>` and exit with failure.
4. Duplicate city names: apply the Product Owner's recorded behavior and do not crash.
5. Database/query failure: display a readable error and do not show a fake total.

## SUB-VARIATIONS

1. Population comes from `city.Population`, not a country aggregate.
2. Bind the city name with `PreparedStatement`; do not concatenate input into SQL.
3. Store and display the total using `long` / SQL `BIGINT`-compatible values.
4. Display the two required fields: City and Total Population.

## SCHEDULE

**DUE DATE**: Sprint 5 - Population Demographics and Totals