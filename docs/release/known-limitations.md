# Known Limitations

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Hamza Bashar
**Gate:** A5 — Cycle 1 Release · **Last updated:** 2026-11-10 · **Tracking issues:** #86 (A5), #54 (A4)
**Status:** Proposed — pending review by the backup owner

**Review basis.** Checked against `main` at `e9f93e6` on 2026-11-10 (CI run #23 passed, 36 tests), `scope.md`, ADR-001, ADR-002, `assumptions-open-questions.md`, the API contracts and the risk register. Open PRs that change a limitation are named but not counted as done: #95 (Data Layer fixes and end-to-end acceptance test). Re-check this file on the release commit before the `a5-cycle1-release` tag.

**Severity** is the effect on a Cycle 1 user or demo: **High** blocks a required workflow, **Medium** limits or confuses a workflow, **Low** is cosmetic or only matters beyond Cycle 1.

## Current Limitations

| ID | Limitation | User Impact | Severity | Workaround / Mitigation | Disposition | Owner | Cycle 2 Action | Evidence |
|---|---|---|---|---|---|---|---|---|
| LIM-001 | Requests are kept in memory only. Everything is lost when the application stops or restarts. | A restart during a demo or test run looks like data loss. | Medium | Do not restart mid-run; re-submit synthetic requests after a restart. | Accepted (Cycle 1) | Sydel Ugwu | Add durable storage behind the existing Data Layer interface (API-004). | Q-003, R-013, `InMemorySupportRequestRepository` |
| LIM-002 | There is no real login. The role is whatever the client sends in the `X-Simulated-Role` header (the `?role=` fallback is removed in PR #97), so anyone can act as staff. | Role checks prove the workflow rules, not security. | Medium | Synthetic data only, and the server listens on localhost only (LIM-003). | Accepted (Cycle 1) | Sydel Ugwu | Replace the simulated header with real authentication; keep the single role check (API-005). | ASM-001, REQ-006, API-005, `RoleCheckService`, ADR-0003 (PR #56, not merged) |
| LIM-003 | The server only listens on `127.0.0.1`. There is no hosted deployment. | Only the person running it can use it. | Low | Run the demo on the presenter's machine. | Accepted (Cycle 1) | Nova Kurul | Decide whether a hosted deployment is needed; if so, add it with real authentication (LIM-002). | TD-06, ASM-003 |
| LIM-004 | There is no browser page. The Presentation Layer is a JSON HTTP API plus a console demo runner; there is no submission form or staff list page. | Users need `curl` or the demo runner; ADR-001 and `architecture.md` describe a page that collects fields and shows the list. | Medium | Use the console demo runner (`mvn exec:java`) or HTTP requests. | Deferred to Cycle 2 (not in the Cycle 1 release) | Sydel Ugwu | Decide whether to build a page; if not, update ADR-001 and `architecture.md` to match. | ADR-001, `architecture.md`, `CampusConnectApp.java` |
| LIM-005 | Duplicate submissions are not detected; the same request sent twice is stored twice. (PR #95 rejects a duplicate request *ID*, which is a different check.) | Staff may see the same request twice. | Low | Staff can ignore the duplicate. | Deferred | Hamza Bashar | Answer Q-004, then add the rule and a test. | Q-004 (open), API-001 failure table |
| LIM-006 | Students cannot view the status of requests they submitted. | Students must ask staff for the status. | Medium | Staff report the status. | Deferred | Hamza Bashar | Answer Q-005; if yes, add a student view with its own role check. | Q-005 (open) |
| LIM-007 | Students cannot edit or cancel a request, although the Q-006 answer says they may (edit while Open, cancel while Open or In Progress). No requirement, contract, task or estimate exists for it. | The answer and the system disagree. | Medium | None in Cycle 1. | Deferred to Cycle 2 (not in the Cycle 1 release) | Joanelis Rivera Matos | Either add a requirement, contract and estimate for edit/cancel, or change the Q-006 answer and record it in `scope.md`. | Q-006 (PR #28), R-003 |
| LIM-008 | Only the current status and the time of the last change are kept. Who changed a status is not recorded, and there is no status history. | A status change cannot be traced to a person afterwards. | Low | The console log shows the simulated role and time of each change. | Accepted (Cycle 1) | Hamza Bashar | Record the user once real authentication exists (LIM-002); add status history if needed. | Q-007 (PR #70), `AuditLogger` |
| LIM-009 | Category is free text; any non-blank value is accepted. | Categories can be inconsistent. | Low | Staff read the category as entered. | Deferred | Hamza Bashar | Agree a fixed category list (C-Q1) and validate it. | C-Q1 in `api-contracts.md` |
| LIM-010 | A resolved request cannot be reopened, and a status cannot move backwards (Open → In Progress → Resolved only). | A request resolved by mistake has to be submitted again. | Low | Submit a new request. | Accepted (Cycle 1, by design) | Joanelis Rivera Matos | Revisit Q-002 if staff need to reopen requests. | Q-002, REQ-003, `RequestStatusTest` |

## Deferred Capabilities

Full list and reasons: [`scope.md`](../planning/scope.md) (Deferred Scope and Out of Scope) and ADR-002.

| Capability | Reason Deferred | Impact on Current Release | Future Reference |
|---|---|---|---|
| Search, filtering and sorting | Not needed to prove the workflow | Simple list only | ADR-002 TD-04; `scope.md` |
| Automatic routing, notifications, dashboard | Need workflow policy and failure handling first | Staff work the list manually | `scope.md` Deferred Scope |
| AI categorization or any runtime AI | Needs a validation plan and human approval step | No AI in the product | ADR-002 TD-05 |
| Real Loyola data or systems | Needs institutional approval | Synthetic data only | ADR-002 TD-01; REQ-004 |

## Defects and Review Findings

Runtime findings (RF) come from local builds and HTTP checks in A4 (`test-evidence/2026-10-29-local-test-runs.md`). Code review findings (CRF-001..CRF-023) are indexed in [`code-review-a4.md`](../reviews/code-review-a4.md); their final status is tracked in the defect review (#85).

| ID | Finding | Severity | Disposition | Evidence |
|---|---|---|---|---|
| RF-01 | Build failed: 74 compile errors in `CampusConnectApp.java` (unescaped quotes in JSON strings). | High | Resolved | PR #67; CI passes on `main` |
| RF-02 | `GET /api/requests` returned only a count, not the requests (AC-REQ-002-01). | High | Resolved | PR #76 (TASK-104); reviewer HTTP check of AC-REQ-002-01..04 on 2026-10-30; HTTP list check in `CampusConnectAppRoleTransportTest` (PR #97); service-level check in PR #95 (open) |
| RF-03 | A value with an escaped quote was cut off, and the response was invalid JSON. | Medium | Resolved | PR #67; test `quotesAndBackslashesInFieldsArePreserved` |
| RF-04 | The validation-error response was not valid JSON. | Medium | Resolved | PR #67; test `missingFieldsReturn400ListingEveryMissingField` |
| RF-05 | The submit response left out `submittedAt` and `description`. | Medium | Resolved | PR #67; test `validSubmissionReturns201WithSystemAssignedIdStatusAndTime` |
| RF-06 | `?role=` was read only as the first query parameter (`?x=1&role=STAFF` denied), and the role could be put in the URL at all (CRF-003). `POST` was also accepted for status changes (CRF-023). | Low | Resolved | Issue #96; PR #97 (approved by Hamza before merge, CI passed); `CampusConnectAppRoleTransportTest` (3 of 5 tests fail on the old code) |

## Operational Limitations

| Limitation | Operational Impact | Related Evidence |
|---|---|---|
| CI runs the build and tests on every PR and every push to `main` (since PR #72), but nothing blocks a merge: there is no branch protection. | A PR can still merge without a passing check or an approval (it happened with #75). | `docs/testing/ci-evidence.md`, R-019, R-021 |
| Logs are printed to the console and kept in memory only. | Log history is lost when the application stops. | `AuditLogger.java`, #49 |
| Manual start; no health check, restart or recovery. | A stopped application must be restarted by hand, and its data is gone (LIM-001). | R-013 |
| Each machine needs JDK 17 and Maven. | A member without them cannot run or test the system locally; CI still runs the tests. | `pom.xml`, R-007 |

## Verification Limitations

| Area | What Has Been Verified | What Has Not Been Verified | Related Evidence |
|---|---|---|---|
| Automated tests | `main` `e9f93e6`: 36/36 in CI (run #23). With open PR #95 merged locally: 41/41. | Tests in #95 are not on `main` until it merges. | `docs/testing/ci-evidence.md` |
| End-to-end flow | `Cycle1AcceptanceTest` (PR #95) runs submit → list → update → list again through the Logic Layer. | The same flow over HTTP on the release commit (TC-013, #39) before the tag. | PR #95, #39 |
| Staff list over HTTP | Reviewer HTTP check on PR #76 (2026-10-30): fields, empty list, new requests, status changes, student denied. An HTTP test on `main` checks that a listed request appears (PR #97). | No HTTP test of each field in the list yet. | PR #76 review, PR #97 |
| Failure paths | Storage failure returns `500` (test `storageFailureReturns500AndNeverSuccess`). | Simultaneous requests, very large inputs. | API-001, REQ-005 |

## Limitation Changes

| Date / Release | Limitation ID | Change | Reason | Evidence |
|---|---|---|---|---|
| 2026-10-29 / A4 | LIM-001..LIM-009, RF-01..RF-06 | First list created | A4 known-limitations review | Issue #54 |
| 2026-10-29 / A4 | RF-01, RF-03, RF-04, RF-05; LIM-008; review basis | RF-01/03/04/05 → Resolved; LIM-008 to move to Accepted when PR #70 merges; file re-checked against `main` | PR #61 and #67 merged; Hamza's review CRF-015..CRF-018 | PR #66 review; `main` `7ee567c` |
| 2026-10-29 / A4 | LIM-008 | Under Investigation → Accepted (Cycle 1) | Q-007 answered and merged | PR #70 |
| 2026-11-09 / A5 | All | Added severity, owner and Cycle 2 action to every limitation | A5 release requirements | Issue #86 |
| 2026-11-09 / A5 | RF-02, RF-06, LIM-004, LIM-007, LIM-010 | RF-02 → Resolved; LIM-004 and LIM-007 → Deferred to Cycle 2 (not in the release); LIM-010 added (forward-only status rule) | TASK-104 merged (#76); Cycle 1 release scope fixed | PR #76, Q-002 |
| 2026-11-10 / A5 | RF-06 | → Resolved | Fix reviewed and merged | Issue #96, PR #97 |
