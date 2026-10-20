# Code Review 1 Demonstration Guide

This guide maps the team's demonstration to the Code Review 1 checklist. Demonstrate the released `master` branch rather than unfinished Sprint work.

## Review Scope

```text
Release: v0.3.2
Requirements implemented: 16 of 32
Completion: 50%
Product Owner: Saw Zwel Myat Tow
Scrum Master: Win Myat Aung
```

Requirements 17-18 remain on `feature/capital-city-reports` and are not represented as released work.

## Before the Meeting

- Confirm every attendee can access GitHub and Zube.
- Select `master` on GitHub before presenting released code.
- Confirm the latest `master` and `develop` workflows are green.
- Confirm release `v0.3.2` and its tag are visible.
- Confirm the Product Backlog, Sprint boards, issues, use cases, and evidence are accessible.
- Pull the latest `master` locally and build it before the meeting.
- Keep Docker Desktop running and the required tools already signed in.

## Demonstration Order

1. Show the root README, badges, project purpose, and `16/32 = 50%` status.
2. Show the GitHub repository and team contribution history.
3. Show `master`, `develop`, feature, and release branches to explain GitFlow.
4. Show GitHub release `v0.3.2` and its corresponding tag.
5. Show GitHub Issues written as user stories with acceptance criteria.
6. Show the Product Backlog and its priority order in Zube.
7. Show completed and active Sprint boards, story points, owners, and card states.
8. Show the agreed [`CODE_OF_CONDUCT.md`](../CODE_OF_CONDUCT.md) and Definition of Done.
9. Show the country and city use cases and their traceability to requirements.
10. Open the [`use-case-diagram.drawio.svg`](../use-cases/use-case-diagram.drawio.svg).
11. Show useful class and function comments in the Java source.
12. Open [`.github/workflows/main.yaml`](../.github/workflows/main.yaml) and a successful workflow run.
13. Build the self-contained Maven JAR.
14. Build and run the application through Docker Compose.
15. Demonstrate one country report and one city report.
16. Show the Requirements 1-16 evidence in the root README and [`evidence/README.md`](../evidence/README.md).

## Code Review 1 Checklist

- [ ] GitHub coursework repository is accessible.
- [ ] Product Backlog exists and is prioritised.
- [ ] Maven produces a self-contained JAR.
- [ ] Dockerfile builds and runs the application.
- [ ] GitHub Actions builds the JAR and Docker application.
- [ ] GitFlow uses `master`, `develop`, feature, and release branches correctly.
- [ ] A tagged GitHub release is published.
- [ ] Code of Conduct and Definition of Done are available.
- [ ] GitHub Issues are in use.
- [ ] Tasks are written as user stories.
- [ ] GitHub is integrated with Zube.
- [ ] Kanban/Product Backlog board is in use.
- [ ] Sprint boards show planned and completed work.
- [ ] Full use cases are available for the demonstrated requirements.
- [ ] The complete use-case diagram is available.
- [ ] Code contains useful comments.
- [ ] Team contributions are visible in GitHub metrics.

## Maven Demonstration

```shell
git switch master
git pull --ff-only origin master
mvn clean test
mvn clean package
ls target/*-jar-with-dependencies.jar
```

Explain that `mvn clean package` runs the automated tests and produces the dependency-inclusive executable JAR used by Docker.

## Docker Demonstration

```shell
docker compose up -d mysql
docker compose build app
docker compose run --rm app region "Eastern Asia"
docker compose run --rm app city-top-world 10
docker compose down
```

The complete list of released commands is in [`REPORT_COMMANDS.md`](REPORT_COMMANDS.md).

## Scrum Evidence to Show

- Product Backlog ordering by the Product Owner.
- User stories and Conditions of Satisfaction.
- Planning Poker estimates and story points.
- Sprint Goal and selected Sprint Backlog.
- Named owners and visible card movement.
- Daily Scrum blockers or card comments.
- Product Owner review and acceptance.
- Sprint Review records.
- Sprint Retrospective improvement.
- Completed-point velocity.

## Presenter Responsibilities

| Area | Suggested presenter |
|---|---|
| Product vision, priorities, and acceptance | Saw Zwel Myat Tow |
| Scrum process, Zube, Sprint evidence, and GitFlow | Win Myat Aung |
| Maven, Docker, and GitHub Actions | Agreed developer |
| Country and city report demonstration | Story owners |
| Use cases and use-case diagram | Agreed developer |

All team members should attend and be ready to explain their visible contributions.

## Final Review Questions

Before ending the preparation session, confirm:

1. Can a fresh clone build with Maven?
2. Does the dependency-inclusive JAR exist under `target/`?
3. Does Docker run against the supplied MySQL world database?
4. Are the latest `master` and `develop` Actions green?
5. Do the release tag and release page agree?
6. Do Issues, use cases, Zube cards, commits, and evidence remain traceable?
7. Does `master` claim only work that is actually released and demonstrated?
