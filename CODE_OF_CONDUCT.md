# Code of Conduct

## 1. Purpose

This Code of Conduct defines how the Scrum team collaborates while developing the Population Reporting System. Every team member is expected to follow it.

## 2. Respect and Inclusion

- Communicate respectfully and professionally.
- Listen to different technical opinions before making a decision.
- Give feedback about work, not about a person's character.
- Do not tolerate harassment, discrimination, intimidation, or abusive behaviour.
- Give every team member a fair opportunity to contribute and learn.

## 3. Communication

- Use the team's agreed communication channel for important project updates.
- Attend agreed Scrum events or notify the team as early as possible when attendance is not possible.
- During a stand-up, state completed work, planned work, and blockers.
- Ask questions when requirements or implementation decisions are unclear.
- Do not hide blockers, build failures, unfinished work, or mistakes.

## 4. Scrum Roles

- The Product Owner orders the Product Backlog and accepts or rejects work against the agreed acceptance criteria.
- The Scrum Master facilitates Scrum events, helps make blockers visible, and supports improvement of the team's process.
- Developers own the implementation, testing, documentation, review, and delivery of the Sprint increment.
- The Scrum Master is not the team's manager or disciplinary authority.

## 5. Development Workflow

- Direct commits to `master` and `develop` are prohibited.
- Changes are made on short-lived feature, test, or documentation branches created from the appropriate current shared branch.
- Changes are reviewed through pull requests and must pass the required GitHub Actions checks.
- Feature work is merged into `develop` after review.
- Accepted releases move from `develop` through `release` to `master`.
- Force pushes to shared branches are prohibited unless the whole team and teaching team have explicitly approved an exceptional recovery action.
- Secrets, tokens, passwords, matriculation numbers, private contact details, and private contribution percentages must not be committed.

## 6. Definition of Done

A story is Done when:

- Its acceptance criteria and Conditions of Satisfaction are met.
- The associated use case is complete and traceable.
- Code and useful tests are committed on the agreed short-lived branch.
- Maven tests and the required Docker checks pass.
- A teammate reviews the work through a pull request or recorded branch handoff.
- GitHub Actions passes.
- Documentation and requirement evidence are updated.
- The Product Owner accepts the demonstrated result.
- The Zube card accurately reflects the completed state.

Work is not Done merely because a file exists or code has been pushed.

## 7. Blockers and Assistance

- A team member who is blocked should record the blocker on the Zube card and notify the team promptly.
- If a task remains blocked after a reasonable attempt, the Scrum Master facilitates a discussion to find help.
- Team members should help remove blockers without taking ownership away from the story owner unless the team agrees to reassign it.

## 8. Code Review and Quality

- Review comments must be specific, respectful, and actionable.
- Authors respond to comments and explain any decision not to apply a suggestion.
- Broken builds are fixed before unrelated work is merged.
- The team does not claim that tests, deployment, coverage, or requirements pass without evidence.
- Generated files, IDE settings, and secrets are not committed unless explicitly required and reviewed.

## 9. Attendance and Contribution

- Team members attend agreed Sprint Planning, Reviews, Retrospectives, and assessment meetings whenever possible.
- Contributions should be visible through meaningful issues, commits, tests, reviews, documentation, or accepted Scrum artifacts.
- The private contribution spreadsheet must be agreed by the team and total 100% at each assessment point.
- Contribution disagreements are raised honestly rather than hidden or altered to improve appearance.

## 10. Concerns and Escalation

The team first discusses conduct, attendance, or contribution concerns collectively and respectfully. The Scrum Master may facilitate this discussion but does not decide disciplinary action.

If a concern cannot be resolved, or if a team member is uncomfortable raising it within the team, the issue is escalated to the module teaching team with appropriate evidence. Formal action follows the University's and module's procedures.

## 11. Agreement

The team reviews this Code of Conduct when the project workflow changes and records team agreement through the relevant Zube card and pull request.
