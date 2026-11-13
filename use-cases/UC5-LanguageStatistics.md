# USE CASE: View Language Statistics (Requirement 32)

## CHARACTERISTIC INFORMATION

### Goal in Context
As a Demographic Analyst, I want to generate a report providing the number of people who speak Chinese, English, Hindi, Spanish, and Arabic from greatest number to smallest, including the percentage of the world population, so that global language distribution can be reported to the organisation.

### Scope
DevOps Population Reporting System (Black-Box).

### Level
Primary task.

### Preconditions
* The MySQL `world` database is initialised and contains current country population and language percentage data.
* The Demographic Analyst has access to the reporting application CLI.

### Success End Condition
A formatted Language Report is displayed showing `Chinese`, `English`, `Hindi`, `Spanish`, and `Arabic` ordered from greatest number of speakers to smallest, including each language's total speaker count and percentage of the world population.

### Failed End Condition
No report is generated; the system outputs a readable validation or error message and exits safely.

### Primary Actor
Demographic Analyst.

### Trigger
The Demographic Analyst executes the language statistics report command on the system.

## MAIN SUCCESS SCENARIO

1. The Demographic Analyst requests the global language statistics report (**Req 32**).
2. The system validates that no invalid or extra command arguments were supplied.
3. The system calculates the total number of speakers and the percentage of the total world population for `Chinese`, `English`, `Hindi`, `Spanish`, and `Arabic`.
4. The system orders the five languages from greatest number of speakers to smallest.
5. The system formats and displays the Language Report table to the Demographic Analyst.

## EXTENSIONS

1. **Invalid Extra Arguments (Step 2):** If unexpected command arguments are supplied, the system displays command usage instructions and exits with status code `1`.
2. **Zero World Population or Empty Dataset (Step 3):** If the world population total is zero or empty, the system guards against division-by-zero and terminates safely with an informative message.
3. **Database Connection Failure (Step 3):** If the database cannot be reached after the configured retry attempts, the system logs the connection failure and terminates safely.

## SUB-VARIATIONS

1. **Req 32 (Five Languages Report):** The number of people who speak Chinese, English, Hindi, Spanish, and Arabic from greatest number to smallest, including the percentage of the world population.

## SCHEDULE

* **DUE DATE:** Release v0.6.0 / v1.0.0 (Coursework 2 Final Submission — 2 December 2026).