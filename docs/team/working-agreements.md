# Working Agreements

These are the day-to-day engineering practices our team agrees to follow for CampusConnect.
Owner: Khaja Shabbir Ahmed (Planning & Process Lead) · Backup: Nova Kurul
Last updated: [2026-09-24]

## Communication Practices

- Microsoft Teams is our main channel for day-to-day discussion and coordination.
- Anything about a specific task, requirement, or decision is discussed in the related GitHub Issue or Pull Request so the decision stays on record.
- Messages should be acknowledged within 24 hours.
- Urgent issues are also sent through our backup channel ([text / email]) and should get a reply within a few hours.
- Blockers are reported as soon as they are known, not on the due date, by commenting on the issue and posting in Teams.
- If a member will be unavailable for more than 24 hours, they tell the team in advance.

## Work Planning and Ownership

- Every piece of work is tracked as a GitHub Issue with one owner (assignee) and one reviewer.
- Task status is kept current on the GitHub Projects board: To Do → In Progress → In Review → Done.
- Artifact ownership (primary and backup) is recorded in `docs/team/roles.md`.
- If a member is blocked for more than [48 hours], they notify their backup and the Planning & Process Lead so the work can be reassigned or the plan adjusted.

## Git and Branching

- No direct commits to `main`. All changes go through a Pull Request.
- Branch naming: `type/short-description`, for example `docs/working-agreements`, `feature/request-form`, `fix/status-update`.
- Branches should be short-lived and merged within [3 days] where possible.
- Pull the latest `main` before starting work and before opening a PR.

## Commits

- Each commit should be small and focused on one change.
- Commit messages are written in the imperative and describe what changed, for example "Add risk register for Cycle 1". Messages like "update" or "fix stuff" are not acceptable.
- Every member commits from their own GitHub account so individual contributions are visible.

## Pull Requests and Review

- Every PR uses the repository PR template and links its issue (for example `Closes #12`).
- Every PR needs at least one approving review from a team member other than the author.
- Reviewers respond within [48 hours].
- Review comments must be resolved or answered before merging.
- The author merges the PR after approval.
- The Quality & Review Lead (Hamza Bashar, backup Khaja Shabbir Ahmed) monitors review quality.

## Testing and Verification

- Once implementation begins, code PRs must include or update automated tests, and CI must pass before merging.
- For documentation PRs, the reviewer checks accuracy and consistency with related artifacts (requirements, planning, traceability).
- The author is responsible for verifying their change works; the reviewer confirms it.

## Definition of Done

A task is Done only when all of the following are true:

1. The work meets the acceptance criteria in its issue.
2. It is merged into `main` through a reviewed and approved Pull Request.
3. Tests pass (for code) or the content has been reviewed (for documentation).
4. Related documents are updated, such as traceability, task-plan status, or known limitations.
5. Any significant AI assistance is recorded in `docs/ai/ai-use-log.md`.
6. The issue is closed and the Projects board is updated.

## Documentation and Traceability

- Requirement IDs (REQ-###) are referenced in related issues and PRs.
- When a requirement, decision, or scope item changes, the person making the change updates the affected files (such as `docs/planning/traceability.md`) in the same PR.
- The Planning & Process Lead reviews planning and traceability files at every phase gate.

## AI-Assisted Work

- AI use follows `docs/ai/ai-policy.md`.
- AI output is treated as a draft and must be reviewed by a team member before it is committed.
- Significant AI use is recorded in `docs/ai/ai-use-log.md`.
- No one submits AI-assisted work they cannot explain and defend.

## Handling Defects and Broken Builds

- Defects are reported as GitHub Issues using the defect report template.
- If `main` or CI breaks, the author of the breaking change fixes or reverts it before starting new work.
- Anyone who notices a broken build announces it in Teams right away.

## Working Agreement Changes

- Changes to these agreements are proposed through a Pull Request and need approval from the team.
- Every change is recorded below so the history stays visible.

| Date | Change | Reason | Approved By |
|---|---|---|---|
| [2026-09-24] | Initial working agreements created | A1 Project Launch | [team members who approved] |