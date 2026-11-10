# Traceability

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup / Reviewer:** Hamza Bashar
**Gate:** A5 — Cycle 1 Release · **Last updated:** 2026-11-10 · **Tracking issues:** #88 (A5), #43 (A4)
**Status:** Proposed — pending review by the backup owner

**Basis.** Checked on 2026-11-10 against `main` at `e9f93e6`: requirements, acceptance criteria, GitHub issues #29–#96, PRs #55–#99, `git log`, and CI run #23 on `e9f93e6` (36 tests, 0 failures). Every test named below as "on `main`" runs in CI on every PR and every push to `main`. Open PRs are named but not counted as done: #92 (release notes and demo script) and #95 (Data Layer fixes and end-to-end acceptance test). Known limitations (#98) merged after this check, with no code change. Re-check this file on the release commit before the `a5-cycle1-release` tag.

**Status key**

- **Verified on `main`:** the code is on `main`, and the check named in each acceptance criterion passed on it.
- **Verified, partly manual:** verified on `main`, but some acceptance criteria were checked by hand; their automated test is in an open PR.
- **Implemented, check pending:** the code is on `main`, but at least one acceptance criterion has not been checked on it yet.

## 1. Requirement Traceability

Test case (TC) and run (RUN) IDs are in [`test-cases.md`](../testing/test-cases.md) and [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md); LIM and RF IDs in [`known-limitations.md`](../release/known-limitations.md).

| Requirement | Acceptance Criteria | Issue / Task | PR → Commit on `main` | Tests and Checks | Status | Limitations / Findings |
|---|---|---|---|---|---|---|
| REQ-001 Student submits a request | AC-REQ-001-01, AC-REQ-001-02 | #33 (TASK-103), #31 (TASK-102) | #61 → `49adb8e`; #67 → `a06910c`, `ae190fe`, `553f205`, `8eb2296` | `CampusConnectAppSubmitTest` (8 HTTP tests) | Verified on `main` | LIM-005, LIM-009; RF-01, RF-03..RF-05 resolved |
| REQ-002 Staff view the list of requests | AC-REQ-002-01..AC-REQ-002-04 | #34 (TASK-104), #31 (TASK-102) | #76 → `8ba1ca0` | AC-REQ-002-01..04: reviewer HTTP check on PR #76 (2026-10-30). AC-REQ-002-03: `RoleCheckServiceTest`. A listed request appears over HTTP: `CampusConnectAppRoleTransportTest`. AC-REQ-002-02 and -04 automated in `Cycle1AcceptanceTest` (PR #95, open) | Verified, partly manual | LIM-004; RF-02 resolved |
| REQ-003 Staff change status: Open → In Progress → Resolved only | AC-REQ-003-01..AC-REQ-003-05 | #35 (TASK-105), #96 | #58 → `a897b31`; #61 → `49adb8e`; #70 (Q-007); #73 → `0dfec52`; #97 → `a8a2872` | AC-REQ-003-01..03: `RequestStatusTest` (9). AC-REQ-003-04: `RoleCheckServiceTest`. `PATCH` only: `CampusConnectAppRoleTransportTest`. AC-REQ-003-05: reviewer HTTP check on PR #76; automated in PR #95 (open) | Verified, partly manual | LIM-007, LIM-008, LIM-010 |
| REQ-004 Synthetic data only | AC-REQ-004-01 | #31 (TASK-102), #33 (TASK-103) | #58 → `a897b31` (`synthetic = true`) | Repository inspection TC-011 on `e9f93e6`: no real data, credentials or data files (RUN-010). `InMemorySupportRequestRepositoryTest` checks the synthetic flag | Verified on `main` | — |
| REQ-005 No request or status lost while running | AC-REQ-005-01 | #31 (TASK-102), #39 (TASK-109) | #58 → `a897b31`; #67 → `553f205` (`500` on storage failure) | `InMemorySupportRequestRepositoryTest` (5); `storageFailureReturns500AndNeverSuccess`. AC-REQ-005-01 (list again after a change, no restart): `Cycle1AcceptanceTest` (PR #95, open); HTTP run TC-013 (#39) not yet on the release commit | Implemented, check pending | LIM-001 |
| REQ-006 Role logic in one identified place | AC-REQ-006-01 | #32 (TASK-110), #29 (TASK-111), #96 | #57 → `663fbd0`; #71 → `0627263`; #97 → `a8a2872` | TC-010 code review: Pass. `RoleCheckServiceTest` (9). `CampusConnectAppRoleTransportTest` (5): role read from the header only | Verified on `main` | LIM-002; RF-06 resolved; ADR-0003 not merged (#56) |

## 2. Requirement to Demo Step, Release Note and Release Baseline

Demo steps are the scenarios in `docs/release/demo-script.md` ("Demo Evidence / Results"); release-note entries are in `docs/release/release-notes.md`. Both are in PR #92 (open). The scenarios are not numbered yet, so they are named here.

| Requirement | Demo Step | Release Note Entry | Release Baseline |
|---|---|---|---|
| REQ-001 | "Student creates new request"; "Student fails to submit request due to missing fields" | Included Capabilities: "Simulated student can submit a synthetic support request"; Verification Summary: "Student submission acceptance criteria" | `a5-cycle1-release` (see section 4) |
| REQ-002 | "Staff can view the request list" | Included Capabilities: "Simulated staff can view the list of submitted support requests"; Verification Summary: "Request list access and contents" | `a5-cycle1-release` |
| REQ-003 | "Staff changes a request's status from OPEN to IN_PROGRESS"; "… from IN_PROGRESS to RESOLVED"; "Staff fails to change a request's status from RESOLVED to OPEN" | Included Capabilities: "Simulated staff can update a request's status along OPEN -> IN_PROGRESS -> RESOLVED"; Verification Summary: "Request status verification" | `a5-cycle1-release` |
| REQ-004 | All scenarios use synthetic data ("Test data prepared" pre-demo check) | Verification Summary: "Synthetic data verification" | `a5-cycle1-release` |
| REQ-005 | All scenarios run in one process without a restart (demo step 1) | Verification Summary: "Request list/request status update verification" | `a5-cycle1-release` |
| REQ-006 | "Student fails to update a request's status" (role denied) | Verification Summary: "Role logic verification" | `a5-cycle1-release` |

## 3. Defect Traceability

Following one defect from discovery to the release, as asked in the A4 instructor assessment.

| Defect | Found | Issue | Branch → Commit | PR and Review | CI | Regression Test | Recorded In | Status |
|---|---|---|---|---|---|---|---|---|
| RF-06 / CRF-003 / CRF-023: role read from `?role=`; `POST` accepted for status changes | RUN-005 (A4); #56 review | #96 | `fix/role-header-only-rf06` → `a8a2872` | #97: approved by Hamza before merge (2026-11-10) | PR check passed; `main` run #23 | `CampusConnectAppRoleTransportTest` (3 of 5 tests fail on the old code) | `known-limitations.md` (#98), this file | Resolved |
| RF-02: staff list returned only a count | RUN-005 (A4) | #34 | `JoanelisRM/task104` → `0fae726` | #76: approved by Khaja (2026-10-30) | No CI yet at the time | Reviewer HTTP check; automated in PR #95 (open) | `known-limitations.md` | Resolved |
| RF-01, RF-03..RF-05: compile error and JSON handling | RUN-003, RUN-005 (A4) | #33 | `fix/submit-request-33` → `a06910c`..`8eb2296` | #67, merged into #61 without a separate review | No CI yet at the time | `CampusConnectAppSubmitTest` (6 of 8 tests fail on the old code) | `known-limitations.md` | Resolved |
| CRF-006, CRF-007, CRF-010: Data Layer findings | #58 review (A4) | — | `sydel/a5-remediation-and-e2e-acceptance-test` | #95 (open) | — | `InMemorySupportRequestRepositoryTest`, `Cycle1AcceptanceTest` (in #95) | `code-review-a4.md` | Fix in review |

## 4. Release Baseline

- **Tag:** `a5-cycle1-release` (lowercase). It must point to the final `main` commit after the last A5 merge (#81).
- **Current state (2026-11-10):** the tag exists on `b3613ae` (the #99 merge), which is **before** PR #97 merged and while #92, #95, #98 and this PR were still open. It has to be recreated on the final commit before the deadline (R-006).
- **Check:** `git ls-remote --tags origin` shows the tag on the same commit as `main`, and that commit has a passing CI run.
- Release notes and demo script (PR #92) write the tag as `A5-cycle1-release`; the tag name is lowercase.

## 5. Task and Issue Traceability

Owners and backups are from `task-plan.md` and the issue descriptions.

| Task | Issue | Owner / Backup | PR | Commits on `main` | Status |
|---|---|---|---|---|---|
| TASK-111 Framework, transport and role ADR | #29 (open) | Sydel Ugwu / Hamza Bashar | #56 (approved, not merged) | — | ADR not on `main`; code matches it since #97 |
| TASK-101 Maven skeleton | #30 (closed) | Sydel Ugwu / Nova Kurul | #57 (merged through #61) | `663fbd0` | Done |
| TASK-102 Data Layer | #31 (closed) | Sydel Ugwu / Hamza Bashar | #58 (merged through #61); #95 (open, review fixes) | `a897b31` | Done; review fixes in #95 |
| TASK-110 Role check in one place | #32 (closed) | Hamza Bashar / Khaja Shabbir Ahmed | #71 | `663fbd0`, `0627263` | Done |
| TASK-103 Submit request | #33 (closed) | Khaja Shabbir Ahmed / Sydel Ugwu | #61, #67 | `49adb8e`; `a06910c`..`8eb2296` | Done |
| TASK-104 Staff list | #34 (closed) | Joanelis Rivera Matos / Khaja Shabbir Ahmed | #76 | `0fae726` (merge `8ba1ca0`) | Done |
| TASK-105 Status change | #35 (open) | Hamza Bashar / Joanelis Rivera Matos | #70, #73, #97 | `a897b31`, `49adb8e`, `0dfec52`, `a8a2872` | Code and tests on `main` |
| TASK-106 Unit tests | #36 (open) | Hamza Bashar / Sydel Ugwu | #71, #73 (merged); #62 (open, stale) | `0627263`, `0dfec52` | Role and status tests on `main` |
| TASK-107 CI | #37 (closed) | Nova Kurul / Hamza Bashar | #72 | merge `fca18d9` | Done; CI runs on every PR |
| TASK-108 Logging | #38 (open) | Nova Kurul / Joanelis Rivera Matos | #60 (open, stale) | `49adb8e` (`AuditLogger`) | Logger on `main` |
| TASK-109 End-to-end run on `main` | #39 (open) | Joanelis Rivera Matos / Khaja Shabbir Ahmed | — | — | HTTP run on the release commit still to do |

### A5 Deliverables

| Issue | Deliverable | Owner / Backup | PR | Status |
|---|---|---|---|---|
| #79 | A5 release evidence README | Sydel Ugwu / Nova Kurul | #93 | Merged |
| #80, #90 | Release notes; presentation index and demo script | Joanelis Rivera Matos / Sydel Ugwu | #92 | Open |
| #81 | Release baseline | Khaja Shabbir Ahmed / Hamza Bashar | Tag and GitHub Release | Tag to be recreated (section 4) |
| #82 | Changed-file summary since A4 | Sydel Ugwu / Nova Kurul | #94 | Merged |
| #83 | Final testing evidence | Hamza Bashar / Khaja Shabbir Ahmed | — | No PR yet |
| #84, #87 | CI/CD final evidence; AI-use log final update | Nova Kurul / Joanelis Rivera Matos | #99 | Merged (without a review) |
| #85 | Defect review summary | Hamza Bashar / Khaja Shabbir Ahmed | — | No PR yet |
| #86 | Known limitations and residual risks | Khaja Shabbir Ahmed / Hamza Bashar | #98 | Merged |
| #88 | Traceability update (this file) | Khaja Shabbir Ahmed / Hamza Bashar | This PR | Open |
| #89 | Release workflow evidence | Hamza Bashar / Khaja Shabbir Ahmed | — | No PR yet |
| #91 | Cycle 2 evidence handoff | Joanelis Rivera Matos / Sydel Ugwu | #93 | Merged (issue still open) |
| #96 | RF-06 fix | Khaja Shabbir Ahmed / Hamza Bashar | #97 | Merged |

## 6. Decision Traceability

| Decision | Affects | Status |
|---|---|---|
| ADR-001 Local Java layered architecture | All code | Proposed (R-018) |
| ADR-002 Cycle 1 scope tradeoffs | Deferred capabilities in `scope.md` | Proposed (R-018) |
| ADR-0003 Localhost transport and simulated role | REQ-006, `CampusConnectApp`, `RoleCheckService` | PR #56 approved, not merged; code matches it since #97 (R-014) |
| Q-002 No reopening | REQ-003 | Answered (PR #28); enforced on `main` (`RequestStatusTest`); LIM-010 |
| Q-003 In-memory storage | REQ-005 | Answered; LIM-001 |
| Q-006 Students may edit or cancel | REQ-001, REQ-003 | Answered (PR #28) but not in the Cycle 1 release (LIM-007) |
| Q-007 Who changed status | REQ-003 | Answered (PR #70); LIM-008 |

## 7. Open Traceability Gaps

| Gap | Owner | Next Action |
|---|---|---|
| The `a5-cycle1-release` tag is on `b3613ae`, before the last A5 merges | Khaja Shabbir Ahmed (#81), Joanelis Rivera Matos | Recreate the tag on the final `main` commit before the deadline |
| Release notes (PR #92) mark AC-REQ-002-02/04, AC-REQ-003-05 and AC-REQ-005-01 as passed by automated tests, but those tests are only in PR #95; its Known Limitations section is still the template | Joanelis Rivera Matos | Merge #95 first, or mark those criteria as manually checked; reference LIM IDs |
| `cycle2-handoff.md` numbers limitations differently from `known-limitations.md` and lists CRF-006/007/010 as fixed before #95 has merged | Joanelis Rivera Matos, Sydel Ugwu | Use the LIM IDs from `known-limitations.md`; update after #95 merges |
| Demo scenarios are not numbered | Joanelis Rivera Matos | Number them (D-1..D-7) so section 2 can link to them exactly |
| HTTP end-to-end run TC-013 has not been done on the release commit | Joanelis Rivera Matos, Khaja Shabbir Ahmed (#39) | Run it on the final commit before presenting |
| ADR-0003 (#56) is approved but not merged; ADR-001 and ADR-002 are still "Proposed" | Sydel Ugwu | Merge #56; record acceptance on ADR-001 and ADR-002 |
| No PR yet for final testing evidence, defect review or release workflow evidence | Hamza Bashar (#83, #85, #89) | Open the PRs before the tag |
| PR #99 merged without a review (R-021) | Nova Kurul | Ask for a review after the fact and record it |
| `test-cases.md` still describes the A4 state | Hamza Bashar, Khaja Shabbir Ahmed | Final testing evidence (#83) to supersede it |
| Stale A4 PRs #22, #56, #60, #62, #63, #65 are still open | Their authors | Merge or close them |

## Change History

| Date | Gate | Change |
|---|---|---|
| 2026-09-24 | A1 | Initial planning traceability baseline created |
| 2026-09-29 | A2 | Corrected REQ-002 alignment; connected requirements to build and verification tasks, proposed owners, reserved estimate IDs, and planned evidence; updated ownership and recorded unresolved dependencies |
| 2026-10-29 | A4 | Rewritten against `main` `0c5f605`: requirements to acceptance criteria, tasks, code, tests and limitations; tasks to issues, branches, PRs and commits; decisions and open gaps. A2 planning-only sections removed (kept in the `a2-planning-estimate` tag). Issue #43 |
| 2026-11-10 | A5 | Updated against `main` `e9f93e6`: added demo step, release-note entry and release baseline for each requirement; defect traceability for RF-06, RF-02 and RF-01..05; A5 deliverables; REQ-002, REQ-003 and REQ-004 statuses updated. Issue #88 |

## Maintenance

- Update this file in the same PR as changes to requirements, tasks, decisions or tests.
- Change a status only after the check has run on `main`, and name the evidence.
- Keep work in open PRs separate from work on `main`.
- Disclose AI-assisted changes in `docs/ai/ai-use-log.md` and record the human review.
- The Planning & Process Lead reviews traceability at each phase gate.
