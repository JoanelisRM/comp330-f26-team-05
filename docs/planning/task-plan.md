# Task Plan

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Nova Kurul
**Gate:** A6 — Final Cycle 2 Release · **Last updated:** 2026-11-24 · **Tracking issues:** #17 (A3), #110 (A6 re-estimation and maturity backlog)

Every deliverable has a GitHub Issue; the issue and its merged, reviewed PR are the completion evidence. This file is the planning view of that work. Statuses were checked against `main` at `1bb17f4` and GitHub on 2026-11-24. Re-check them on the final commit before the `a6-final-release` tag.

## Active Task Plan

### Part 1 — A6 deliverables (issues #105–#123)

| Issue | Deliverable | Owner | Backup / Reviewer | PR | Status (2026-11-24) |
|---|---|---|---|---|---|
| #105 | A6 final evidence README | Sydel Ugwu | Nova Kurul | — | Not started |
| #106 | Final release notes | Joanelis Rivera Matos | Sydel Ugwu | — | Draft on a branch; no PR yet |
| #107 | Final release baseline (`a6-final-release` tag, GitHub Release) | Joanelis Rivera Matos | Sydel Ugwu | — | After the last merge |
| #108 | Changed-file summary since A5 | Joanelis Rivera Matos | Sydel Ugwu | — | Draft on a branch; no PR yet |
| #109 | Postmortem / Cycle 2 learning summary | Hamza Bashar | Khaja Shabbir Ahmed | — | Not started |
| #110 | Cycle 2 re-estimation / maturity backlog | Sydel Ugwu | Nova Kurul | — | Not started; this file and `re-estimation.md` updated by their owner |
| #111 | Final testing and validation evidence | Hamza Bashar | Khaja Shabbir Ahmed | — | Not started |
| #112 | CI/CD final evidence | Nova Kurul | Joanelis Rivera Matos | #124 | In review (no reviewer yet) |
| #113 | Final defect review summary | Hamza Bashar | Khaja Shabbir Ahmed | — | Not started |
| #114 | Final risk register / residual risks | Khaja Shabbir Ahmed | Hamza Bashar | #125 | In review |
| #115 | Observability evidence | Nova Kurul | Joanelis Rivera Matos | #124 | In review (no reviewer yet) |
| #116 | Runbook / operational readiness | Sydel Ugwu | Nova Kurul | — | Not started |
| #117 | Operational failure / recovery notes | Hamza Bashar | Khaja Shabbir Ahmed | — | Not started |
| #118 | Security / governance evidence | Nova Kurul | Joanelis Rivera Matos | #124 | In review (no reviewer yet) |
| #119 | AI-use log final update | Nova Kurul | Joanelis Rivera Matos | #124 | In review (no reviewer yet) |
| #120 | Traceability update | Khaja Shabbir Ahmed | Hamza Bashar | — | After the other A6 PRs merge |
| #121 | Final workflow traceability evidence | Khaja Shabbir Ahmed | Hamza Bashar | — | After the other A6 PRs merge |
| #122 | Final presentation materials | Joanelis Rivera Matos | Sydel Ugwu | — | Draft on a branch; no PR yet |
| #123 | Cycle 2 maturity target disposition | Khaja Shabbir Ahmed | Hamza Bashar | #125 | In review |

### Part 2 — Cycle 2 maturity targets

Chosen from Cycle 1 evidence; the reason, expected proof and outcome of each are in `docs/release/cycle2-maturity-summary.md` (PR #125). None has its own issue or estimate yet (R-024).

| ID | Target | Owner | Status (2026-11-24) |
|---|---|---|---|
| MT-01 | Observability plan for the running system | Nova Kurul | In review (#124) |
| MT-02 | Security and governance checklist | Nova Kurul | In review (#124) |
| MT-03 | Merge the Data Layer fixes and the end-to-end acceptance test | Sydel Ugwu / Hamza Bashar | Not done: PR #95 approved, merge conflict |
| MT-04 | Branch protection: approval and passing CI before merge | Joanelis Rivera Matos | Not done |
| MT-05 | `a6-final-release` on the final commit | Joanelis Rivera Matos | After the last merge (#107) |
| MT-06 | Runbook | Sydel Ugwu | Not done (#116) |
| MT-07 | Durable storage (SQLite) | Sydel Ugwu / Hamza Bashar | Not started |
| MT-08 | Real authentication | Sydel Ugwu / Nova Kurul | Not started |
| MT-09 | Controlled reopening with an audit comment | Hamza Bashar / Khaja Shabbir Ahmed | Not started |
| MT-10 | Browser page | Not assigned | Not started |

### Part 3 — Cycle 1 build tasks (A4, issues #29–#39)

| ID | Task | Owner | Backup / Reviewer | Issue | Completion Evidence | Status |
|---|---|---|---|---|---|---|
| TASK-111 | Framework, localhost transport and simulated-role ADR | Sydel Ugwu | Hamza Bashar | #29 (open) | ADR-0003 in PR #56 (approved, not merged) | Decided and implemented in code; ADR not on `main` |
| TASK-101 | Maven project skeleton | Sydel Ugwu | Nova Kurul | #30 | PR #57 (merged through #61) | Done |
| TASK-102 | Data Layer interface and in-memory store | Sydel Ugwu | Hamza Bashar | #31 | PR #58 (merged through #61); review fixes in PR #95 (open) | Done; CRF-006/007 still open |
| TASK-110 | Role check in one place | Hamza Bashar | Khaja Shabbir Ahmed | #32 | PR #71 (9 tests) | Done |
| TASK-103 | Submit support request | Khaja Shabbir Ahmed | Sydel Ugwu | #33 | PRs #61, #67 (8 HTTP tests) | Done |
| TASK-104 | Staff list of requests | Joanelis Rivera Matos | Khaja Shabbir Ahmed | #34 | PR #76 | Done |
| TASK-105 | Status change, forward only | Hamza Bashar | Joanelis Rivera Matos | #35 | PRs #70, #73 (9 tests), #97 | Done |
| TASK-106 | Unit tests | Hamza Bashar | Sydel Ugwu | #36 (open) | PRs #71, #73 merged; PR #62 never merged | Done on `main` through #71 and #73; issue still open |
| TASK-107 | CI on every PR | Nova Kurul | Hamza Bashar | #37 | PR #72 | Done |
| TASK-108 | Logging of submissions and status changes | Nova Kurul | Joanelis Rivera Matos | #38 (open) | `AuditLogger` merged through #61; PR #60 never merged | Done in code; issue still open |
| TASK-109 | End-to-end run on `main` | Joanelis Rivera Matos | Khaja Shabbir Ahmed | #39 (open) | FT-002 in `final-test-evidence.md`: 12 of 12 steps on `321aef6` (A5) | Done; issue still open |

### Part 4 — A3 deliverables (issues #8–#20)

| ID | Deliverable | Owner | Issue | Final Status |
|---|---|---|---|---|
| TASK-030 | Architecture package README | Joanelis Rivera Matos | #8 | Done (PR #28) |
| TASK-031 | Architecture overview | Joanelis Rivera Matos | #9 | Done; components aligned in PR #28 (R-015 mitigated) |
| TASK-032 | Architecture diagram | Sydel Ugwu | #10 | Done (PR #24) |
| TASK-033 | Component responsibility map | Khaja Shabbir Ahmed | #11 | Done (PR #27) |
| TASK-034 | API and tool boundary contracts | Hamza Bashar | #12 | Done (PR #23) |
| TASK-035 | Data and context ownership | Nova Kurul | #13 (open) | Not merged: PR #22 still open |
| TASK-036 | Architecture decision records | Sydel Ugwu | #14 | Done (PR #24); ADR-001 still "Proposed" (R-018) |
| TASK-037 | Architecture review evidence | Hamza Bashar | #15 | Done (PR #25) |
| TASK-038 | Review workflow evidence | Hamza Bashar | #16 (open) | Not completed in A3; review evidence was produced in A4 and A5 instead (`code-review-a4.md`, `release-workflow-evidence.md`) |
| TASK-039 | Updated planning evidence | Khaja Shabbir Ahmed | #17 | Done (PR #26) |
| TASK-040 | Test / review evidence plan | Sydel Ugwu | #18 | Done (PR #24) |
| TASK-041 | AI-use log updates | Nova Kurul | #19 (open) | Log kept current in later PRs; PR #22 still open |
| TASK-042 | Architecture review action tracking | Joanelis Rivera Matos | #20 | Issue closed in A3 with ARA-001..003 still open; action items closed in A4 (PR #59, issue #42) |
| TASK-043 | A3 tag | Joanelis Rivera Matos | — | Tag on `6c0c830` while six A3 issues were open (R-009) |

### Evidence deliverables, A4 and A5

- **A4 (#40–#54):** all 15 issues are closed. PRs #63 (#40) and #65 (#46) were never merged; the work they described is covered by `assignment4-changed-files.md`, `traceability.md` and `code-review-a4.md`.
- **A5 (#79–#91, #96):** all issues are closed except #81 (release baseline): the tag `a5-cycle1-release` stayed on `b3613ae`, and a second tag `a5-cycle1-release_` was created on `1bb17f4` (R-006).

## Definition of Task Completion

A task is complete when it meets the team **Definition of Done** in `docs/team/working-agreements.md`: acceptance criteria met, merged through a PR with an approval from someone other than the author **and a passing CI check**, related documents updated, AI use logged, and the GitHub Issue closed. Each task's completion evidence is its issue plus the merged, reviewed PR.

## Blocked Work

| Item | Blocker | Resolution Owner | Needed Action | Impact |
|---|---|---|---|---|
| MT-03 (CRF-006, CRF-007) | PR #95 approved but has a merge conflict in `ai-use-log.md` | Sydel Ugwu | Resolve the conflict, re-run CI, merge | Two High findings stay open on `main` |
| #120, #121 | Need the other A6 PRs merged first | Khaja Shabbir Ahmed | Update after the last merge, before the tag | Traceability would describe an unfinished release |
| #107 (tag) | Must wait for the last merge | Joanelis Rivera Matos | Tag once; check with `git ls-remote --tags origin` | Wrong tag repeats R-006 |
| ADR-0003 | PR #56 approved but not merged | Sydel Ugwu | Merge or record why not | Decision record missing from `main` (R-014) |

## Completed Work

| ID | Task | Owner | Completion Evidence | Completed |
|---|---|---|---|---|
| TASK-001 | Working agreements | Khaja Shabbir Ahmed | PR #1 | 2026-09-24 |
| TASK-007 | A1 planning baseline | Khaja Shabbir Ahmed | PR #2 | 2026-09-25 |
| TASK-015, TASK-016 | Cycle 1 scope; requirement-to-task traceability | Sydel Ugwu | PR #4 | 2026-09-29 |
| TASK-017, TASK-020, TASK-026 | A2 task plan; risk register; ADR-002 tradeoffs | Khaja Shabbir Ahmed | PR #3 (approved by Joanelis 2026-09-25) | 2026-09-28 |
| TASK-018 | GitHub Issues for A2 | Nova Kurul | Issues #5–#7 | 2026-09-29 |
| TASK-021 | Schedule dates | Joanelis Rivera Matos | Commit 331fbfb | 2026-09-29 |
| TASK-023 | Team commitments | Nova Kurul | Commit 87c9757 | 2026-09-29 |
| TASK-025 | Requirements package (REQ-001..006, acceptance criteria, assumptions) | Hamza Bashar / Sydel Ugwu | Commits ffe1842 and earlier | 2026-09-29 |
| TASK-028 | A2 tag | Joanelis Rivera Matos | `a2-planning-estimate` → ffe1842 | 2026-09-29 |
| TASK-030..042 | A3 deliverables | See Part 4 | PRs #23–#28, #59 | 2026-10-15 to 2026-10-29 |
| TASK-101..110 | Cycle 1 build | See Part 3 | PRs #57–#97 | 2026-10-29 to 2026-11-10 |
| A5 | Cycle 1 release evidence | See issues #79–#96 | PRs #92–#104 | 2026-11-10 |

**A2 items not completed:** TASK-014 planning README; TASK-022 Projects board could not be verified (the Projects tab is empty).

## Task Changes

| Date | Task ID | Change | Reason | Related Evidence |
|---|---|---|---|---|
| 2026-09-24 | All | Initial task plan created | A1 planning baseline | PR #2 |
| 2026-09-25 | TASK-014..028 | A2 deliverables added with the Team Lead's owners | A2 kickoff | PR #3 |
| 2026-10-21 | TASK-030..043 | A3 deliverables added from issues #8–#20 with current status | A3 planning update | Issue #17 |
| 2026-10-21 | TASK-102 | "Synthetic data store" → in-memory Data Layer behind an internal interface | Q-003 resolved (in-memory only) | API-004, ADR-001 |
| 2026-10-21 | TASK-103..105 | Linked to API-001..003 | API contracts merged | PR #23 |
| 2026-10-21 | TASK-110, TASK-111 | Added role-check task and framework/transport decision task | ADR-001 and API-005 | ADR-001 |
| 2026-10-21 | A2 items | Statuses recorded as Done / not completed from repository evidence | Keep tagged statuses accurate (A1 feedback) | This file |
| 2026-11-24 | All | Statuses for A3, A4 and A5 brought up to date (the file had not been updated since A3); A6 deliverables (#105–#123) and Cycle 2 targets MT-01..MT-10 added | A6 gate; statuses must be accurate when tagged | Issues #105–#123, PR #125 |
