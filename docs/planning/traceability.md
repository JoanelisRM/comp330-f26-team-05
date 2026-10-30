# Traceability

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup / Reviewer:** Hamza Bashar
**Gate:** A4 — Cycle 1 Construction and Integration · **Last updated:** 2026-10-29 · **Tracking issue:** #43
**Status:** Proposed — pending review by the backup owner

**Basis.** Checked on 2026-10-29 against `main` at `63438d8`: `requirements.md`, `acceptance-criteria.md`, `task-plan.md`, GitHub issues #29–#54, PRs #55–#74, `git log`, `mvn -B test` (22 run, 0 failures) and a manual HTTP check (RUN-009 in [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md)). Statuses describe what is on `main`; work in open PRs is named but not counted as done. Reviews, CI checks and merge decisions are traced under #46 (Workflow Review Traceability).

**Status key**

- **Verified on `main`:** the code is on `main` and the check named in the acceptance criteria passed on it.
- **Implemented, check pending:** the code is on `main`, but its automated test is in an open PR or the required check has not been run.
- **Partial:** the code is on `main`, but at least one acceptance criterion fails.

## Requirement Traceability

Test case (TC) IDs are defined in [`test-cases.md`](../testing/test-cases.md); LIM and RF IDs in [`known-limitations.md`](../release/known-limitations.md).

| Requirement | Acceptance Criteria | Task / Issue | Code on `main` | Tests and Checks | Status | Limitations / Findings |
|---|---|---|---|---|---|---|
| REQ-001 Student submits a request (category, title, description) | AC-REQ-001-01, AC-REQ-001-02 | TASK-103 (#33), TASK-102 (#31) | `SupportRequestService.submitRequest`; `POST /api/requests` in `CampusConnectApp` | `CampusConnectAppSubmitTest`, 8 tests on `main` (TC-001..TC-005) | Verified on `main` | LIM-005, LIM-009; RF-01, RF-03..RF-05 resolved |
| REQ-002 Staff view the list of requests | AC-REQ-002-01..AC-REQ-002-04 | TASK-104 (#34), TASK-102 (#31) | `SupportRequestService.listRequests`; `GET /api/requests` | AC-REQ-002-01: TC-006 **Fail** (only a count is returned, RUN-009). AC-REQ-002-03: student denied (RUN-009; `RoleCheckServiceTest` on `main`). AC-REQ-002-02 and AC-REQ-002-04: TC-007, TC-008 not run | Partial | RF-02 (open), LIM-004 |
| REQ-003 Staff change status: Open → In Progress → Resolved only | AC-REQ-003-01..AC-REQ-003-05 | TASK-105 (#35) | `RequestStatus.canTransitionTo`; `SupportRequestService.updateStatus`; `PATCH /api/requests/{id}/status` | Manual on `main` (RUN-009): both allowed steps `200`, Open → Resolved and Resolved → Open `409`, student `403`. Automated: `SupportRequestServiceTest` in PR #62, `RequestStatusTest` in PR #73. AC-REQ-003-05: TC-008 not run | Implemented, check pending | LIM-007, LIM-008 (accepted, Q-007) |
| REQ-004 Synthetic data only | AC-REQ-004-01 | TASK-102 (#31), TASK-103 (#33) | `SupportRequest` (`synthetic = true`) | `InMemorySupportRequestRepositoryTest#saveAndFindByIdPreservesSyntheticRequestFields` on `main`; repository inspection TC-011 not run | Implemented, check pending | — |
| REQ-005 No request or status lost while running | AC-REQ-005-01 | TASK-102 (#31), TASK-109 (#39) | `SupportRequestRepository`, `InMemorySupportRequestRepository` | `InMemorySupportRequestRepositoryTest`, 5 tests on `main`; storage failure returns `500` (TC-005); end-to-end TC-008 and TC-013 not run on `main` | Implemented, check pending | LIM-001 (accepted, Q-003) |
| REQ-006 Role logic kept in one identified place | AC-REQ-006-01 | TASK-110 (#32), TASK-111 (#29) | `Role`, `RoleCheckService`, called only from `SupportRequestService` | TC-010 code review: Pass (2026-10-29). `RoleCheckServiceTest`, 9 tests on `main` (PR #71) | Verified on `main` | LIM-002, RF-06 (open); ADR-0003 not merged (PR #56) |

## Task and Issue Traceability

Owners and backups are from [`task-plan.md`](task-plan.md) and match the issue descriptions. "Commits on `main`" lists the commits that brought the task's code to `main`.

| Task | Issue | Owner / Backup | Branch | PR | Commits on `main` | Status on `main` |
|---|---|---|---|---|---|---|
| TASK-111 Framework, transport and role ADR | #29 (open) | Sydel Ugwu / Hamza Bashar | `sydel/issue-29-task-111-adr` | #56 (open, changes requested) | — | ADR-0003 not merged, but the code already follows it (R-014) |
| TASK-101 Maven skeleton | #30 (closed) | Sydel Ugwu / Nova Kurul | `sydel/issue-30-task-101-maven-skeleton` | #57 (merged through #61) | `663fbd0` | Done; also added `Role` and `RoleCheckService` |
| TASK-102 Data Layer | #31 (closed) | Sydel Ugwu / Hamza Bashar | `sydel/issue-31-task-102-data-layer` | #58 (merged through #61) | `a897b31` | Done; #58 review findings still to fix in a new PR |
| TASK-110 Role check in one place | #32 (closed) | Hamza Bashar / Khaja Shabbir Ahmed | `test/role-check-tests-32` | #71 (merge `3f83413`) | `663fbd0` (code), `0627263` (tests) | Done |
| TASK-103 Submit request | #33 (closed) | Khaja Shabbir Ahmed / Sydel Ugwu | `sydel/issue-33-task-103-submit-request`; `fix/submit-request-33` | #61 (merge `7ee567c`); #67 into #61 | `49adb8e`; `a06910c`, `ae190fe`, `553f205`, `8eb2296` | Done |
| TASK-104 Staff list | #34 (open) | Joanelis Rivera Matos / Khaja Shabbir Ahmed | `JoanelisRM/task104` (`0fae726`, no PR yet) | — | `49adb8e` (partial code) | Partial: RF-02; fix in progress |
| TASK-105 Status change | #35 (open) | Hamza Bashar / Joanelis Rivera Matos | `docs/q007-status-change-record-35`; `test/request-status-tests-35` | #70 (merge `63438d8`, Q-007 answer); #73 (open, tests) | `a897b31`, `49adb8e` | Code on `main`; transition tests in PR #73 |
| TASK-106 Unit tests | #36 (open) | Hamza Bashar / Sydel Ugwu | `sydel/issue-36-task-106-unit-tests` | #62 (open, changes requested) | — | 18/18 pass on PR `496fe55`; not merged |
| TASK-107 CI | #37 (open) | Nova Kurul / Hamza Bashar | `evidence-updates-kurul` | #72 (open, changes requested) | — | No CI on `main` (R-019) |
| TASK-108 Logging | #38 (open) | Nova Kurul / Joanelis Rivera Matos | `sydel/issue-49-runtime-logging` | #60 (open, for #49) | `49adb8e` (`AuditLogger`) | Logger on `main`; diagnostics PR open |
| TASK-109 End-to-end run on `main` | #39 (open) | Joanelis Rivera Matos / Khaja Shabbir Ahmed | — | — | — | Rehearsal RUN-008; blocked by RF-02 |

### Evidence Deliverables

| Issue | Deliverable | PR | Status |
|---|---|---|---|
| #40 | A4 evidence README | #63 | Open |
| #41 | Changed-file index | #64 | Merged |
| #42 | Architecture review action items | #59 | Merged |
| #43 | Issue and task traceability (this file) | #74 | Open |
| #44 | Pull request template | #55 | Merged |
| #45, #48, #50 | Reviewed PR, CI results and code review evidence | — | No PR yet |
| #46 | Workflow review traceability | #65 | Open |
| #47, #49, #51, #52 | CI workflow, runtime logging, AI code review, AI-use log | #72 (also #60 for #49, #69 for #52) | #69 merged; #60 open; #72 changes requested |
| #53 | Test evidence | #68 | Merged |
| #54 | Known risks and deferred work | #66 | Merged |

## Decision Traceability

| Decision | Affects | Status |
|---|---|---|
| ADR-001 Local Java layered architecture | All code | Proposed (R-018) |
| ADR-002 Cycle 1 scope tradeoffs | Deferred capabilities in `scope.md` | Proposed (R-018) |
| ADR-0003 Localhost transport and simulated role | REQ-006, `CampusConnectApp`, `RoleCheckService` | PR #56 open (R-014) |
| Q-002 No reopening | REQ-003 | Answered (PR #28); enforced on `main` (RUN-009) |
| Q-003 In-memory storage | REQ-005 | Answered; LIM-001 |
| Q-006 Students may edit or cancel | REQ-001, REQ-003 | Answered (PR #28) but not built (LIM-007, R-003) |
| Q-007 Who changed status | REQ-003, TASK-105, TASK-108 | Answered (PR #70): role and time are logged, individual users are not (LIM-008) |

## Open Traceability Gaps

| Gap | Owner | Next Action |
|---|---|---|
| The staff list returns only a count (RF-02), so AC-REQ-002-01 fails and TC-013 cannot pass | Joanelis Rivera Matos (TASK-104, #34) | Return the request fields, add an HTTP test, re-run TC-006 |
| The code for TASK-104, TASK-105 and TASK-108 reached `main` inside the TASK-103 commit `49adb8e`, not through PRs of their own | Task owners | Link each task's follow-up PR to its own issue |
| #57 and #67 merged without a review; the #58 review came after the merge (R-020) | Sydel Ugwu, Hamza Bashar | Fix the #58 findings in a new PR; record the merge decisions under #46 |
| Automated tests for REQ-003 are not on `main` | Hamza Bashar (PRs #62, #73) | Merge after review |
| AC-REQ-002-02, AC-REQ-002-04, AC-REQ-003-05 and AC-REQ-005-01 have no test | Hamza Bashar (TASK-106) | Add TC-007 and TC-008 as automated tests |
| Repository inspection TC-011 (REQ-004) and end-to-end TC-013 have not run on `main` | Khaja Shabbir Ahmed (TC-011), Joanelis Rivera Matos (TC-013, #39) | Run both before the A4 tag |
| PRs #60 and #72 both close #49 | Nova Kurul, Sydel Ugwu | Agree which PR closes it |
| No PR has a CI check | Nova Kurul (#47, PR #72) | Merge a working `ci.yml`, then re-run the suite in CI |
| PR #65 (#46) also rewrites this file | Joanelis Rivera Matos, Sydel Ugwu, Khaja Shabbir Ahmed | Combine the two versions into one before the tag |

## Change History

| Date | Gate | Change |
|---|---|---|
| 2026-09-24 | A1 | Initial planning traceability baseline created |
| 2026-09-29 | A2 | Corrected REQ-002 alignment; connected requirements to build and verification tasks, proposed owners, reserved estimate IDs, and planned evidence; updated ownership and recorded unresolved dependencies |
| 2026-10-29 | A4 | Rewritten against `main` `63438d8`: requirements to acceptance criteria, tasks, code, tests and limitations; tasks to issues, branches, PRs and commits; decisions and open gaps. A2 planning-only sections removed (kept in the `a2-planning-estimate` tag). Issue #43 |

## Maintenance

- Update this file in the same PR as changes to requirements, tasks, decisions or tests.
- Change a status only after the check has run on `main`, and name the evidence.
- Keep work in open PRs separate from work on `main`.
- Disclose AI-assisted changes in `docs/ai/ai-use-log.md` and record the human review.
- The Planning & Process Lead reviews traceability at each phase gate.
