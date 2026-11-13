# v0.5.0 — Sprint 5 Population Reports

## Summary

This release integrates Sprint 5 population reports with the previously released country, city, and capital-city reports. It brings the application to **31 of 32 requirements implemented**.

## Included

- Population breakdown reports by continent, region, and country (Requirements 23-25).
- World, continent, region, country, district, and city population totals (Requirements 26-31).
- MySQL-backed repository and CLI integration tests, Docker Compose report smoke tests, and JaCoCo coverage reporting.
- Automated coverage and branch badges, plus a GitHub bug-report issue template.

## Verification

- Release-candidate GitHub Actions run [43366357040](https://github.com/GibbyAung/Devops-Population-System/actions/runs/43366357040) passed the Maven unit/integration tests, Docker build, Compose application run, and report smoke tests.
- CI JaCoCo badge: 64.2% instruction coverage and 49.6% branch coverage. The badge generator truncates to one decimal; the raw branch ratio is 49.6599%.

## Known limitation

Requirement 32 (language population reports) is not implemented and is not included in this release.

## Release status

Sprint 5 code was merged to `master` by PR #60 at commit `9d9f2a4`. Apply the `v0.5.0` tag to the approved master commit containing the finalized release metadata.
