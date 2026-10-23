# Task Plan

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Nova Kurul
**Gate:** A3 — Cycle 1 Architecture and Review · **Last updated:** 2026-10-21 · **Tracking issue:** #17

Every A3 deliverable has a GitHub Issue (#8–#20). This file is the planning view of that work; the issue and its linked PR are the completion evidence. Cycle 1 build tasks (Part 2) are now based on ADR-001 (local Java layered architecture) and the API contracts (API-001..API-005).

## Active Task Plan

### Part 1 — A3 architecture deliverables

Owners are the GitHub Issue assignees. **Backup owners for A3 were not assigned in the issues** — to be confirmed by the Team Lead (see Blocked Work).

| ID | Task | Owner | Backup / Reviewer | Related Evidence | Issue | Status (2026-10-21) |
|---|---|---|---|---|---|---|
| TASK-030 | Architecture Package README | Joanelis Rivera Matos | TBD | `docs/architecture/README.md` | #8 | Open — file not created |
| TASK-031 | Architecture Overview | Joanelis Rivera Matos | Hamza Bashar (reviewer, PR #21) | `docs/architecture/architecture.md` | #9 | Closed — components/interfaces tables still template names (ARA-003 deferred) |
| TASK-032 | Architecture Diagram | Sydel Ugwu | TBD | `docs/architecture/architecture-diagram.png` | #10 | Done (PR #24) |
| TASK-033 | Component Responsibility Map | Khaja Shabbir Ahmed | TBD | `docs/architecture/component-responsibilities.md` | #11 | Open — still starter template |
| TASK-034 | API and Tool Boundary Contracts | Hamza Bashar | Joanelis Rivera Matos (reviewer, PR #23) | `docs/architecture/api-contracts.md` | #12 | Done (PR #23) |
| TASK-035 | Data and Context Ownership | Nova Kurul | Joanelis Rivera Matos (reviewer, PR #22) | `docs/architecture/data-context.md` | #13 | In Review — PR #22 open |
| TASK-036 | Architecture Decision Records | Sydel Ugwu | Nova Kurul (ADR-001 backup) | `docs/decisions/ADR-001-local-java-layered-architecture.md` | #14 | Done (PR #24) — ADR-001 status still "Proposed" |
| TASK-037 | Architecture Review Evidence | Hamza Bashar | Joanelis Rivera Matos (reviewer, PR #25) | `docs/reviews/architecture-review.md` | #15 | Done (PR #25) — review outcome "Not yet concluded" |
| TASK-038 | Review Workflow Evidence | Hamza Bashar | TBD | PR #21, #23, #25 review threads | #16 | Open |
| TASK-039 | Updated Planning Evidence (this update) | Khaja Shabbir Ahmed | TBD | `docs/planning/` | #17 | In Review |
| TASK-040 | Test / Review Evidence Plan | Sydel Ugwu | TBD | `docs/testing/test-strategy.md` | #18 | Done (PR #24) |
| TASK-041 | AI-Use Log Updates | Nova Kurul | TBD | `docs/ai/ai-use-log.md` | #19 | In Review — PR #22 open |
| TASK-042 | Architecture Review Action Tracking | Joanelis Rivera Matos | TBD | ARA-001..003 in `architecture-review.md` | #20 | Closed — ARA-001..003 still listed as Open in the review file |
| TASK-043 | A3 tag `a3-architecture-review` and Sakai submission | Joanelis Rivera Matos | Sydel Ugwu | Tag at `6c0c830` (2026-10-16) | — | Tag created while TASK-030, -033, -035, -038, -039, -041 were still open (see R-006, R-009) |

### Part 2 — Cycle 1 build work (planned for A4)

Updated from the A2 plan to match ADR-001 and the API contracts. Owners are **proposed** from the specialized roles and still need team confirmation. Estimates are in `estimates.md` (EST-101..EST-111).

| ID | Task | Proposed Owner | Backup / Reviewer | Related Evidence | Estimate | Dependencies | Status |
|---|---|---|---|---|---|---|---|
| TASK-111 | Decide web framework, localhost transport and how the simulated role is passed; record as an ADR | Sydel Ugwu | Hamza Bashar | ADR-001 (open item), API-001..003 "HTTP / JSON (proposed)" | EST-111 | — | Planned — **must happen first** |
| TASK-101 | Maven project skeleton with Presentation / Logic / Data packages; every member builds and runs it | Sydel Ugwu | Nova Kurul | ADR-001, R-007 | EST-101 | TASK-111 | Planned |
| TASK-102 | Data Layer: internal Java storage interface plus in-memory implementation | Sydel Ugwu | Hamza Bashar | API-004, Q-003 (in-memory), REQ-005 | EST-102 | TASK-101 | Planned |
| TASK-110 | Logic Layer simulated role check in one place | Hamza Bashar | Khaja Shabbir Ahmed | API-005, REQ-006 | EST-110 | TASK-101 | Planned |
| TASK-103 | Submit support request: form, validation, identifier and submission time | Khaja Shabbir Ahmed | Sydel Ugwu | API-001, REQ-001, REQ-004 | EST-103 | TASK-102, TASK-110 | Planned |
| TASK-104 | Staff list of submitted requests | Joanelis Rivera Matos | Khaja Shabbir Ahmed | API-002, REQ-002 | EST-104 | TASK-102, TASK-110 | Planned |
| TASK-105 | Staff status change: Open → In Progress → Resolved only; reject other changes | Hamza Bashar | Joanelis Rivera Matos | API-003, REQ-003 | EST-105 | TASK-102, TASK-110, Q-002, Q-007 | Planned |
| TASK-106 | Unit tests for validation, status transitions and role checks | Hamza Bashar | Sydel Ugwu | `docs/testing/test-strategy.md`, AC-REQ-001..006 | EST-106 | TASK-103, TASK-105, TASK-110 | Planned |
| TASK-107 | CI runs the build and tests on every PR | Nova Kurul | Hamza Bashar | `.github/workflows/ci.yml` | EST-107 | TASK-101 | Planned |
| TASK-108 | Logging of failed submissions and status changes | Nova Kurul | Joanelis Rivera Matos | `data-context.md` (PR #22), Q-007 | EST-108 | TASK-103, TASK-105 | Planned |
| TASK-109 | Integration checkpoint: end-to-end run of the slice on `main` (no restart during the run) | Joanelis Rivera Matos | Khaja Shabbir Ahmed | REQ-005, R-013 | EST-109 | TASK-103..110 | Planned |

## Definition of Task Completion

A task is complete when it meets the team **Definition of Done** in `docs/team/working-agreements.md`: acceptance criteria met, merged through a PR with a **submitted** review from someone other than the author, related documents updated, AI use logged, and the GitHub Issue closed with the board updated. Each task's completion evidence is its issue plus the merged, reviewed PR.

## Blocked Work

| Task ID | Blocker | Resolution Owner | Needed Evidence / Action | Impact |
|---|---|---|---|---|
| TASK-101..110 | Web framework, transport and role-passing mechanism not decided (ADR-001 open item) | Sydel Ugwu | TASK-111 ADR | No build task can start; build estimates stay low confidence (R-014) |
| TASK-105 | Q-002 (can a request be reopened?) and Q-007 (record who changed status?) still open | Joanelis Rivera Matos / Hamza Bashar | Answer in `assumptions-open-questions.md`; update API-003 and tests | Status rules and logging may change (R-017) |
| TASK-033, TASK-031 | Component names still differ between `architecture.md` (template names) and ADR-001 / API contracts (Presentation / Logic / Data Layer) | Joanelis Rivera Matos | Complete ARA-003 | Component map cannot be final (R-015) |
| Part 1 | Backup owners not assigned for A3 issues | Joanelis Rivera Matos | Add backups to issues or this file | A3 checklist requires a backup per deliverable |

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
| TASK-032, -034, -036, -037, -040 | A3 diagram, API contracts, ADR-001, review evidence, test strategy | See Part 1 | PRs #23, #24, #25 | 2026-10-15 |

**A2 items not completed:** TASK-014 planning README, TASK-019 estimates update, TASK-024 re-estimation notes. TASK-022 Projects board could not be verified. Estimates and re-estimation are picked up in this A3 update (#17); the planning README remains open.

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
