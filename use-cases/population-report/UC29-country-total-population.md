# USE CASE: UC29 View Country Total Population

## CHARACTERISTIC INFORMATION

### Goal in Context

A Demographic Analyst wants one country's total population to verify
national population statistics without generating a multi-country table.

### Scope

Population Reporting System

### Level

Primary task

### Preconditions

- Application built and an active world database connection available.
- country table available.
- Req 27–28 shared total-population infrastructure available.
- Analyst supplies a full country name, for example Myanmar or United Kingdom.

### Success End Condition

The terminal displays the matched country's name and country.Population
as one report row using Java long.

### Failed End Condition

Missing, null, blank, or unknown country names produce a readable error
and exit status 1. No fabricated zero or unrelated report is displayed.
Database failures produce a readable error without a Java stack trace.

### Primary Actor

Demographic Analyst

### Trigger

Analyst runs:
country-total-population "United Kingdom"

## MAIN SUCCESS SCENARIO

1. Analyst requests the report with one full country-name argument.
2. System verifies the argument count.
3. System rejects null or blank names and strips surrounding whitespace.
4. System executes a parameterized query using WHERE country.Name = ?.
5. System reads country.Population using getLong().
6. System displays Country and Total Population with one data row.
7. Analyst views the national population.

## EXTENSIONS

1. Missing or extra arguments: show usage and exit 1.
2. Null or blank name: "Country must be provided." and exit 1.
3. Unknown name: "Unknown country: <input>" and exit 1.
4. ISO code supplied instead of name: unknown-name error; use the full name.
5. Known country with population zero: display a valid report with 0.
6. Database/query failure: show a readable error without a fake report.

## SUB-VARIATIONS

1. Read country.Population directly, not a sum of city populations.
2. Use PreparedStatement and a ? parameter.
3. Use Java long and ResultSet.getLong().
4. Quote names containing spaces.
5. Display the name returned by the database.

## SCHEDULE

DUE DATE: Sprint 5 — Population Demographics and Totals.