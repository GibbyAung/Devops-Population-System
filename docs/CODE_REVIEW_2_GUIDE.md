# Code Review 2 Demonstration Guide

This guide maps the Moodle Code Review 2 rubric to repository evidence and a short demonstration. The Moodle screenshot says Travis CI; this repository uses GitHub Actions, as specified by the coursework setup. Demonstrate the equivalent CI checks rather than adding a duplicate CI provider.

## Before the Review

- Merge the reviewed feature PR into `develop`; do not describe the local merge as released.
- Confirm the PR's GitHub Actions run is green, including unit tests, MySQL integration tests, Docker build, and report smoke tests.
- Record coverage from the CI JaCoCo report; do not use a local result where database tests were skipped.
- Confirm all five README badges render. After successful verification, CI generates and commits the branch-specific coverage SVG on pushes to `develop`, `release/**`, and `master`.
- Start Docker Desktop and rehearse the commands below before the meeting.

## Suggested 10-Minute Walkthrough

### 1. Unit-test quality and coverage (2 minutes)

Run `mvn clean verify` and open `target/site/jacoco/index.html`. Show tests for models, formatting, service validation, unknown input, database errors, and population edge cases. Discuss uncovered code and planned tests; coverage percentage alone is not proof of test quality.

### 2. Database integration tests (2 minutes)

Show `src/test/java/org/napier/com/repository/WorldDatabaseRepositoryIT.java` and `src/test/java/org/napier/com/MainCommandIT.java`, plus the Failsafe profile in `pom.xml`. They connect to the real MySQL `world` schema, test repository mapping/order and duplicate-city aggregation, exercise the CLI handlers, and roll back inserted test data. CI starts MySQL and runs `mvn clean verify -Pintegration-tests`. Without `DB_HOST`, the integration tests skip intentionally; use CI results to demonstrate them running against MySQL.

### 3. Continuous integration (1 minute)

Show `.github/workflows/main.yaml` and a successful PR run. It runs Maven verification, starts MySQL, builds the Docker image, runs the application and `scripts/integration-smoke.sh`, then uploads the JaCoCo report. GitHub Actions is the team's CI system; the rubric's Travis reference is not a reason to run two pipelines.

### 4. Deployment (1 minute)

Show the dependency-inclusive JAR, `Dockerfile`, and `docker-compose.yaml`. Rehearse `mvn clean package`, `docker compose up -d --wait mysql`, `docker compose build app`, and `docker compose run --rm app city-total-population "London"`.

### 5. Bug reporting (1 minute)

Open GitHub Issues → **New issue** → **Bug report**. Show reproduction steps, expected/actual behavior, environment, safe-log guidance, and automatic `bug` label. Preview the form; do not create a fake production bug.

### 6. Badges and requirements (2 minutes)

Show master build, develop build, coverage, latest release, and license badges in the root README. Explain that the coverage SVG is generated from the JaCoCo CSV report and committed on active branches after CI passes; the release badge reflects GitHub Releases. Show the requirement table: **31/32 (96.875%) implemented**, with evidence for Requirements 1-31. Requirement 32 (language populations) remains explicitly unmet. Sprint 5 is not released until its approved release PR and tag are complete.

### 7. GitHub/Kanban quality (1 minute)

Show a requirement issue linked to its use case, implementation PR, test/evidence, and Product Owner acceptance. Explain the feature → `develop` → release → `master` flow.

## Rubric Evidence Map

| Rubric item | Evidence |
|---|---|
| Unit tests and coverage | `src/test/java/`, `pom.xml`, `target/site/jacoco/`, CI artifact, master coverage badge |
| Integration tests | `WorldDatabaseRepositoryIT.java`, `MainCommandIT.java`, Failsafe profile, `scripts/integration-smoke.sh`, CI run |
| CI | `.github/workflows/main.yaml`, successful pull-request Actions run |
| Deployment | `Dockerfile`, `docker-compose.yaml`, successful MySQL-backed smoke run |
| Bug reporting | `.github/ISSUE_TEMPLATE/bug_report.yml`, GitHub `bug` label |
| Five badges | Root `README.md`; coverage badge generated on master |
| Requirements met | Root README table and `evidence/README.md` review register |
| Kanban and commits | GitHub Issues/PR links and Zube sprint board |

## Current Verification Notes

- The capital/population integration baseline is 94 passing unit tests.
- The preparation machine had no running Docker daemon, so MySQL integration tests and Compose smoke checks have not been run locally. Require a green GitHub Actions run before presenting them as passed.
- Sprint 5 screenshot review remains pending in `evidence/README.md` until integrated screenshots have been checked.
- Latest published release is `v0.4.1`; do not tag or announce Sprint 5 before review and green CI.
