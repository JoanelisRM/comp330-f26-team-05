# Risk Register

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Nova Kurul
**Gate:** A2 — Cycle 1 Planning & Estimation · **Last updated:** 2026-09-25

## Risk Register

| ID | Risk | Likelihood | Impact | Mitigation | Contingency / Response | Owner | Status | Related Evidence |
|---|---|---|---|---|---|---|---|---|
| R-001 | If Microsoft Teams stays unreliable for some members, then messages and decisions will be missed and deadlines will slip. | Medium | Medium | Backup urgent channel; task decisions recorded in GitHub Issues/PRs, not only chat | Escalate through the backup channel; follow the 72-hour unresponsiveness rule in the charter | Khaja Shabbir Ahmed | Open | `docs/team/team-charter.md`, `docs/team/working-agreements.md` |
| R-002 | If members new to team Git workflow push directly to `main` or keep long-lived branches, then work will be lost or conflict. | Medium | Medium | Branch + PR rules and required review in working agreements | Revert the breaking change; pair with the member on the next PR | Hamza Bashar | Open | `docs/team/working-agreements.md` |
| R-003 | If new features are added to Cycle 1 without re-estimating, then the vertical slice will not be finished and tested on time. | Medium | High | Deferred-scope list; any scope change needs a PR to `scope.md` and a re-estimate | Cut or defer the newest item first | Khaja Shabbir Ahmed | Open | `scope.md` |
| R-004 | If AI-generated content is committed without review, then the team will submit work it cannot explain or defend. | Medium | High | AI policy, AI-use log, required PR review | Remove or rewrite the content; record the finding in AI verification notes | Nova Kurul | Open | `docs/ai/ai-policy.md`, `docs/ai/ai-use-log.md` |
| R-005 | If the requirements baseline stays unwritten, then scope, tasks, estimates and traceability cannot be confirmed. | High | High | Treat requirements as the first A1/A2 dependency; Planning Lead backs up the requirements owner | Planning Lead and requirements owner pair to draft REQ-001..003 | Hamza Bashar | Materialized | `docs/requirements/requirements.md` |
| R-006 | If a phase-gate tag points to the wrong commit, then the graded version will not match the team's final work. | Medium | High | Tag checklist: tag only after all gate PRs are merged, then verify on GitHub | Move the tag to the correct commit before grading | Joanelis Rivera Matos | Materialized | Tag `a1-project-launch` |
| R-007 | If members are not yet productive in Java/Maven, then implementation (A4) will take much longer than estimated. | Medium | High | Early setup task; share a working build on every machine before A4 | Simplify the slice or pair experienced with less-experienced members | Sydel Ugwu | Open | `README.md` (Prerequisites) |
| R-008 | If weekly availability is lower than planned (other courses, jobs), then milestones will slip. | High | Medium | Plan from stated capacity; internal targets 2–3 days before Sakai deadlines | Use backup owners; defer scope rather than skip review/testing | Khaja Shabbir Ahmed | Open | `schedule.md`, `estimates.md` |
| R-009 | If 14 A2 deliverables across five people depend on requirements that are not yet written, then several deliverables will be rushed or inconsistent by the Tuesday deadline. | High | High | Internal deadline 11 PM Tuesday; requirements and slice confirmed first; planning files build on the A1 baseline | Submit with gaps clearly marked as open rather than invented; re-estimate in A3 | Khaja Shabbir Ahmed | Open | `task-plan.md` (TASK-014..028) |
| R-010 | If several members edit the same planning files at the same time, then PRs will conflict and changes may be lost. | Medium | Medium | One owner per file; small PRs merged quickly; pull `main` before starting | Resolve conflicts keeping both sides; owner checks the merged file | Hamza Bashar | Materialized | PR #2 conflict in `ai-use-log.md` |
| R-011 | If the Team Lead is unavailable after the A2 deadline (surgery the following Wednesday), then early A3 coordination and any post-deadline fixes will slow down. | Medium | Medium | Backup Team Lead (Sydel) covers coordination that week; A2 tag and submission finished before Wednesday | Backup owners act for their artifacts; defer non-urgent decisions to the weekend | Sydel Ugwu | Open | `docs/team/roles.md` |
| R-012 | If the Cycle 1 build tasks (TASK-101..109) are estimated before the architecture decision, then A4 estimates will be unreliable. | Medium | Medium | Mark build estimates as low confidence; revisit after ADR-001 and A3 | Record changes in `re-estimation.md` | Hamza Bashar | Open | `task-plan.md` Part 2, `estimates.md` |

## Risk Evaluation

- **Likelihood:** Low = unlikely this cycle; Medium = could reasonably happen; High = already showing signs or very likely.
- **Impact:** Low = minor rework; Medium = delays one task or milestone; High = threatens a phase gate or release claim.
- Risks rated High on either scale are reviewed at every team meeting.

## Risk Triggers / Indicators

| Risk ID | Trigger / Indicator | Monitoring Evidence |
|---|---|---|
| R-001 | A message goes unanswered for more than 24 hours | Teams chat, issue comments |
| R-002 | A commit appears on `main` without a PR, or a branch is open more than a week | Commit history, PR list |
| R-003 | A new feature appears in an issue or PR that is not in `scope.md` | Issues, PRs, `scope.md` history |
| R-004 | A PR contains content the author cannot explain, or AI use is not logged | PR review comments, AI-use log |
| R-005 | `requirements.md` still contains starter-kit content near a gate deadline | `docs/requirements/` history |
| R-006 | `git ls-remote --tags` shows a gate tag not on the latest gate commit | GitHub tags page |
| R-007 | A member cannot build and run the project locally | Setup issue, team meeting notes |
| R-008 | Tasks pass their target date, or members report reduced availability | Task plan, Projects board |
| R-009 | Any A2 deliverable not started by Sunday 2026-09-27, or requirements still templates on Monday | Projects board, `docs/requirements/` history |
| R-010 | GitHub shows "This branch has conflicts" on a PR | PR page |
| R-011 | Team Lead messages go unanswered during recovery week | Teams chat |
| R-012 | Build estimates change by more than 50% after A3 | `re-estimation.md` |

## Materialized Risks

| Risk ID | Date | What Occurred | Resulting Action / Issue | Impact |
|---|---|---|---|---|
| R-006 | 2026-09-24 | `a1-project-launch` tag found pointing at the Initial commit (808a359, 2026-09-08) instead of the final A1 work | Team Lead to move the tag after all A1 PRs merge (TASK-010) | Graded A1 version would miss all launch evidence if not fixed |
| R-006 | 2026-09-25 | Tag still points at 808a359 after the A1 window | Raised with the Team Lead; still open | Same as above; also needed as a lesson for the `a2-planning-estimate` tag |
| R-010 | 2026-09-25 | PR #2 conflicted with PR #1 in `ai-use-log.md` | Resolved by keeping both rows (commit c88d2e8) | Minor; about 10 minutes of rework |
| R-005 | 2026-09-24 | Requirements files are still starter-kit templates at the end of the A1 window | Requirements baseline tracked as TASK-005/TASK-006; planning uses proposed REQ IDs | Scope, estimates and traceability remain provisional |

## Closed or Accepted Risks

| Risk ID | Final Status | Reason | Evidence |
|---|---|---|---|
| — | — | No risks closed yet at A1 | — |

## Risk Review

- The Planning & Process Lead reviews this register at each weekly team meeting and before every phase-gate submission.
- Risk owners update the status of their own risks when triggers occur.
- New risks are added through a PR so the change is reviewable.

### Review Log

| Date | What Changed | Action | Owner |
|---|---|---|---|
| 2026-09-24 | Initial register (R-001..R-008) | A1 baseline | Khaja Shabbir Ahmed |
| 2026-09-25 | Added R-009..R-012 for A2; R-005 owner moved to Hamza (requirements owner in A2); R-006 and R-010 recorded as materialized | A2 update | Khaja Shabbir Ahmed |
