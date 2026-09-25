# Task Plan

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Nova Kurul
**Gate:** A2 — Cycle 1 Planning & Estimation · **Last updated:** 2026-09-25

This plan has two parts: (1) the A2 planning deliverables, owned as assigned by the Team Lead, and (2) the Cycle 1 build work that turns the vertical slice into small, reviewable tasks. From A2 onward, every task also has a GitHub Issue on the Projects board (owned by Nova / Joanelis); the **Issue** column will be filled as issues are created.

**A2 deadline:** Tuesday 2026-09-29, 11:55 PM (Sakai). **Internal deadline:** 11:00 PM the same day, so the Team Lead can review before submitting.

## Active Task Plan

### Part 1 — A2 planning deliverables

| ID | Task | Owner | Backup / Reviewer | Related Evidence | Issue | Target | Dependencies | Status |
|---|---|---|---|---|---|---|---|---|
| TASK-014 | Planning Summary / README | Joanelis Rivera Matos | Sydel Ugwu | `docs/planning/README.md` | TBD | 2026-09-29 23:00 | Other planning files | Planned |
| TASK-015 | Cycle 1 Scope Statement | Sydel Ugwu | Khaja Shabbir Ahmed | `docs/planning/scope.md` | TBD | 2026-09-29 23:00 | Slice confirmed | Planned |
| TASK-016 | Requirement-to-Task Traceability | Sydel Ugwu | Hamza Bashar | `docs/planning/traceability.md` | TBD | 2026-09-29 23:00 | TASK-015, TASK-025 | Planned |
| TASK-017 | Task Plan (this file) | Khaja Shabbir Ahmed | Nova Kurul | `docs/planning/task-plan.md` | TBD | 2026-09-29 23:00 | — | In Progress |
| TASK-018 | GitHub Issues / Task Board | Nova Kurul | Joanelis Rivera Matos | GitHub Issues | — | 2026-09-29 23:00 | TASK-017 | Planned |
| TASK-019 | Effort Estimates | Hamza Bashar | Sydel Ugwu | `docs/planning/estimates.md` | TBD | 2026-09-29 23:00 | TASK-017 (task IDs) | Planned |
| TASK-020 | Risk Register | Khaja Shabbir Ahmed | Nova Kurul | `docs/planning/risk-register.md` | TBD | 2026-09-29 23:00 | — | In Progress |
| TASK-021 | Project Schedule and Major Milestones | Joanelis Rivera Matos | Hamza Bashar | `docs/planning/schedule.md` | TBD | 2026-09-29 23:00 | Sakai dates | Planned |
| TASK-022 | GitHub Projects board and Cycle 1 milestone | Joanelis Rivera Matos | Hamza Bashar | GitHub Projects | — | 2026-09-29 23:00 | TASK-018 | Planned |
| TASK-023 | Team Commitments | Nova Kurul | Khaja Shabbir Ahmed | `docs/planning/team-commitments.md` | TBD | 2026-09-29 23:00 | TASK-017 | Planned |
| TASK-024 | Re-estimation Notes | Hamza Bashar | Nova Kurul | `docs/planning/re-estimation.md` | TBD | 2026-09-29 23:00 | TASK-019 | Planned |
| TASK-025 | Updated Requirements Package | Hamza Bashar | Joanelis Rivera Matos | `docs/requirements/` | TBD | 2026-09-29 23:00 | Slice confirmed | Planned |
| TASK-026 | Tradeoff / Deferred Scope Decisions | Khaja Shabbir Ahmed | Joanelis Rivera Matos | `docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` | TBD | 2026-09-29 23:00 | — | In Progress |
| TASK-027 | AI-Use Log Updates | Nova Kurul | Khaja Shabbir Ahmed | `docs/ai/ai-use-log.md` | TBD | 2026-09-29 23:00 | All A2 work | Planned |
| TASK-028 | Final review, `a2-planning-estimate` tag and Sakai submission | Joanelis Rivera Matos | Sydel Ugwu | Git tag, Word index | — | 2026-09-29 23:55 | TASK-014..027 merged | Planned |

### Part 2 — Cycle 1 build work (vertical slice)

These tasks are **planned, not committed**. They depend on the confirmed requirements (TASK-025) and the architecture decision in A3, and will be scheduled for A4. Owners are **proposed** from the specialized roles in `docs/team/roles.md` and will be confirmed at the next team check-in. Estimate IDs EST-101..109 are reserved for Hamza's estimates file.

| ID | Task | Proposed Owner | Backup / Reviewer | Related Evidence | Estimate | Target | Dependencies | Status |
|---|---|---|---|---|---|---|---|---|
| TASK-101 | Set up Maven project skeleton; confirm every member can build and run it | Sydel Ugwu | Nova Kurul | README Build/Run, R-007 | EST-101 | Before A4 | ADR-001 (tech stack) | Planned |
| TASK-102 | Request data model and synthetic data store | Sydel Ugwu | Hamza Bashar | REQ-001..003 | EST-102 | A4 | TASK-101 | Planned |
| TASK-103 | Submit a support request with required-field validation | Khaja Shabbir Ahmed | Sydel Ugwu | REQ-001, SCP-001 | EST-103 | A4 | TASK-102 | Planned |
| TASK-104 | Staff list of submitted requests | Joanelis Rivera Matos | Khaja Shabbir Ahmed | REQ-002, SCP-002 | EST-104 | A4 | TASK-102 | Planned |
| TASK-105 | Staff status update with allowed transitions (Open → In Progress → Resolved) | Hamza Bashar | Joanelis Rivera Matos | REQ-003, SCP-003 | EST-105 | A4 | TASK-102 | Planned |
| TASK-106 | Unit tests for validation and status transitions | Hamza Bashar | Sydel Ugwu | Acceptance criteria, `docs/testing/` | EST-106 | A4 | TASK-103, TASK-105 | Planned |
| TASK-107 | CI runs the build and tests on every PR | Nova Kurul | Hamza Bashar | `.github/workflows/ci.yml` | EST-107 | A4 | TASK-101 | Planned |
| TASK-108 | Basic logging for failed submissions and status changes | Nova Kurul | Joanelis Rivera Matos | `docs/observability/` | EST-108 | A4 | TASK-103, TASK-105 | Planned |
| TASK-109 | Integration checkpoint: end-to-end run of the slice on `main` | Joanelis Rivera Matos | Khaja Shabbir Ahmed | Demo evidence | EST-109 | A4 | TASK-103..108 | Planned |

## Definition of Task Completion

A task is complete when it meets the team **Definition of Done** in `docs/team/working-agreements.md`: acceptance criteria met, merged through a reviewed PR, related documents updated, AI use logged, and the GitHub Issue closed with the board updated.

Each task's **completion evidence** is the file or GitHub item in the Related Evidence column, plus the merged PR that changed it.

## Blocked Work

| Task ID | Blocker | Resolution Owner | Needed Evidence / Action | Impact |
|---|---|---|---|---|
| TASK-015, TASK-016, TASK-025 | Cycle 1 slice and REQ-001..003 not yet confirmed; requirements files are still templates | Sydel Ugwu / Hamza Bashar | Confirm the slice in team chat, then write REQ-001..003 | Traceability, estimates and Part 2 tasks stay provisional |
| TASK-101 | Tech stack decision record (ADR-001) not yet written | Sydel Ugwu | Write ADR-001 | Build tasks cannot be estimated with confidence |

## Completed Work

| ID | Task | Owner | Completion Evidence | Completed |
|---|---|---|---|---|
| — | Repository created from starter kit | Joanelis Rivera Matos | Commit 808a359 | 2026-09-08 |
| — | Role matrix and ownership assigned | Joanelis Rivera Matos / Hamza Bashar | `docs/team/roles.md` history | 2026-09-20 |
| — | AI policy and AI-use log established | Nova Kurul | Commit 583d4d1 | 2026-09-20 |
| TASK-001 | Working agreements | Khaja Shabbir Ahmed | PR #1 | 2026-09-24 |
| TASK-007 | A1 planning baseline (six planning files) | Khaja Shabbir Ahmed | PR #2 | 2026-09-25 |
| — | Khaja role acknowledgement | Khaja Shabbir Ahmed | Commit bcca00a | 2026-09-24 |

**A1 carry-over (status to be confirmed by the Team Lead):** TASK-002 (Sydel acknowledgement still Pending), TASK-003 (charter TBDs), TASK-004 (kickoff date not recorded), TASK-006 (requirements — now part of TASK-025), TASK-008 (ADR-001), TASK-009 (README cleanup), TASK-010 (`a1-project-launch` still points at 808a359), TASK-011 (A1 submission).

## Task Changes

| Date | Task ID | Change | Reason | Related Evidence |
|---|---|---|---|---|
| 2026-09-24 | All | Initial task plan created | A1 planning baseline | PR #2 |
| 2026-09-25 | TASK-001 | Status In Review → Done | PR #1 merged | PR #1 |
| 2026-09-25 | TASK-014..028 | A2 deliverables added with owners from the Team Lead's split | A2 kickoff | Team Lead's message in Teams |
| 2026-09-25 | TASK-006 | Requirements work moved from Sydel to Hamza (TASK-025) | A2 ownership split | Team Lead's message in Teams |
| 2026-09-25 | TASK-101..109 | Cycle 1 build work broken down (proposed owners) | A2 requires requirements → tasks | This file |
| 2026-09-25 | Backup | Task Plan backup changed from Joanelis to Nova | A2 ownership split | Team Lead's message in Teams |
