# Requirement Evidence

This folder records application-output evidence for the requirements reported as implemented in the root [`README.md`](../README.md).

## Current Verification State

```text
Verification date: 12 October 2026
Application commit: f0efacc
Application branch: docs/documentation-recovery
Maven result: PASS - 17 tests, 0 failures, 0 errors, 0 skipped
Docker result: PASS - Requirement 12 smoke check against MySQL
Product Owner: Saw Zwel Myat Tow
Scrum Master: Win Myat Aung
Screenshot capture: Complete for Requirements 1-12
Independent evidence review: Pending
```

This verification proves the current branch builds, its automated tests pass, and Requirement 12 runs against MySQL. Output screenshots are now present for Requirements 1-12. Requirements 7-11 use separate `-total` images because their complete reports are too long to show the heading, leading rows, and total in one readable screenshot.

Requirements 4-6 currently show the requested Top-N report followed by an unintended fallback report. Their required rows are visible, but these three screenshots should be recaptured after that control-flow defect is fixed.

## Evidence Rules

Every requirement marked `Yes` in the root README must have an application-output image showing:

1. The report command or selected input.
2. The report title.
3. Every required output column.
4. Enough leading rows to demonstrate descending population order.
5. The selected N for a Top-N report.
6. Output produced by the application rather than a manually constructed table.

Images must not expose passwords, tokens, matriculation numbers, contribution percentages, personal contact details, or unrelated private information.

## File Naming

Create:

```text
evidence/requirements/requirement-01.png
evidence/requirements/requirement-02.png
...
evidence/requirements/requirement-32.png
```

Use two digits for Requirements 1-9. Do not reuse one requirement's image for another requirement.

## Prepare the Application

From the repository root:

```shell
mvn clean test
mvn clean package
docker compose up -d mysql
docker compose build app
```

Maven packaging must happen before the Docker build because the Dockerfile copies the dependency-inclusive JAR from `target/`.

## Capture Country Reports

Requirement 1 uses no application argument:

```shell
DB_HOST=localhost java -jar target/*-jar-with-dependencies.jar
```

Requirements 2-6:

```shell
docker compose run --rm app Asia
docker compose run --rm app region "Eastern Asia"
docker compose run --rm app top-n 10
docker compose run --rm app continent-top-n Asia 10
docker compose run --rm app region-top-n "Eastern Asia" 10
```

## Capture City Reports

Requirements 7-12:

```shell
docker compose run --rm app city-world
docker compose run --rm app city-continent Asia
docker compose run --rm app city-region "Eastern Asia"
docker compose run --rm app city-country CHN
docker compose run --rm app city-district California
docker compose run --rm app city-top-world 10
```

Stop the environment after capturing the output:

```shell
docker compose down
```

## Verification Register

Change a row to `Verified` only after a teammate has opened the image and checked the command/input, columns, ordering, and result.

| ID | Evidence file | Input shown | Required output | Check | Result | Reviewer |
|---:|---|---|---|---|---|---|
| 1 | `requirements/requirement-01.png` | World | Six country columns | Descending population | Linked - review pending | |
| 2 | `requirements/requirement-02.png` | Continent | Six country columns | Filter + descending population | Linked - review pending | |
| 3 | `requirements/requirement-03.png` | Region | Six country columns | Filter + descending population | Linked - review pending | |
| 4 | `requirements/requirement-04.png` | N | Six country columns | N rows + descending population | Linked - recapture recommended | |
| 5 | `requirements/requirement-05.png` | Continent + N | Six country columns | Filter + N + descending population | Linked - recapture recommended | |
| 6 | `requirements/requirement-06.png` | Region + N | Six country columns | Filter + N + descending population | Linked - recapture recommended | |
| 7 | `requirements/requirement-07.png` and `requirements/requirement-07-total.png` | World | Four city columns | Descending population + total | Linked - review pending | |
| 8 | `requirements/requirement-08.png` and `requirements/requirement-08-total.png` | Continent | Four city columns | Filter + descending population + total | Linked - review pending | |
| 9 | `requirements/requirement-09.png` and `requirements/requirement-09-total.png` | Region | Four city columns | Filter + descending population + total | Linked - review pending | |
| 10 | `requirements/requirement-10.png` and `requirements/requirement-10-total.png` | Country code | Four city columns | Filter + descending population + total | Linked - review pending | |
| 11 | `requirements/requirement-11.png` and `requirements/requirement-11-total.png` | District | Four city columns | Filter + descending population + total | Linked - review pending | |
| 12 | `requirements/requirement-12.png` | N | Four city columns | N rows + descending population | Linked - review pending | |
| 13 | `requirements/requirement-13.png` | Continent + N | Four city columns | Filter + N + descending population | Not implemented | |
| 14 | `requirements/requirement-14.png` | Region + N | Four city columns | Filter + N + descending population | Not implemented | |
| 15 | `requirements/requirement-15.png` | Country + N | Four city columns | Filter + N + descending population | Not implemented | |
| 16 | `requirements/requirement-16.png` | District + N | Four city columns | Filter + N + descending population | Not implemented | |
| 17 | `requirements/requirement-17.png` | World | Three capital columns | Descending population | Not implemented | |
| 18 | `requirements/requirement-18.png` | Continent | Three capital columns | Filter + descending population | Not implemented | |
| 19 | `requirements/requirement-19.png` | Region | Three capital columns | Filter + descending population | Not implemented | |
| 20 | `requirements/requirement-20.png` | N | Three capital columns | N rows + descending population | Not implemented | |
| 21 | `requirements/requirement-21.png` | Continent + N | Three capital columns | Filter + N + descending population | Not implemented | |
| 22 | `requirements/requirement-22.png` | Region + N | Three capital columns | Filter + N + descending population | Not implemented | |
| 23 | `requirements/requirement-23.png` | Continent | Totals and percentages | Calculations checked | Not implemented | |
| 24 | `requirements/requirement-24.png` | Region | Totals and percentages | Calculations checked | Not implemented | |
| 25 | `requirements/requirement-25.png` | Country | Totals and percentages | Calculations checked | Not implemented | |
| 26 | `requirements/requirement-26.png` | World | Population | Total checked | Not implemented | |
| 27 | `requirements/requirement-27.png` | Continent | Population | Total checked | Not implemented | |
| 28 | `requirements/requirement-28.png` | Region | Population | Total checked | Not implemented | |
| 29 | `requirements/requirement-29.png` | Country | Population | Total checked | Not implemented | |
| 30 | `requirements/requirement-30.png` | District | Population | Total checked | Not implemented | |
| 31 | `requirements/requirement-31.png` | City | Population | Total checked | Not implemented | |
| 32 | `requirements/requirement-32.png` | Five languages | Language, speakers, percentage | Descending count + calculations | Not implemented | |

## Complete the Evidence Review

1. Ask a teammate to open every linked image and verify its report title, input, required columns, ordering, and total or N where applicable.
2. Change its register result to `Verified` and add the reviewer's name.
3. Fix the Top-N country control flow, then replace the Requirement 4-6 screenshots with clean output and verify them again.
4. Link the evidence commit on the relevant Zube cards.

Use this image format in the root README:

```markdown
![Requirement 12](evidence/requirements/requirement-12.png)
```

Evidence captured from a development branch must not be described as evidence of the current `master` release until the same commit has completed the release flow.
