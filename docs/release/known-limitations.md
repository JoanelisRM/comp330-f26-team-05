# Known Limitations

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Hamza Bashar
**Gate:** A4 — Cycle 1 Construction and Integration · **Last updated:** 2026-10-29 · **Tracking issue:** #54
**Status:** Proposed — pending review by the backup owner

**Review basis.** Checked against `scope.md`, ADR-001, ADR-002, `assumptions-open-questions.md`, the API contracts, the risk register, and a local build and run of `main` at `7ee567c` on 2026-10-29 (JDK 17, Maven 3.10). That commit is the merge of PR #61, which also brought in #57, #58 and the PR #67 fixes. PR #60 (logging diagnostics) and PR #62 (Logic Layer tests) are still open. Re-check this file before the `a4-construction-integration` tag.

## Current Limitations

| ID | Limitation | Impact | Affected Area | Workaround | Disposition | Related Evidence |
|---|---|---|---|---|---|---|
| LIM-001 | Requests are kept in memory only. Everything is lost when the application stops or restarts. | A restart during a demo or test run looks like data loss. | Data Layer | Do not restart mid-run; re-submit synthetic requests after a restart. | Accepted (Cycle 1) | Q-003, R-013, API-004, `InMemorySupportRequestRepository` |
| LIM-002 | There is no real login. The role is whatever the client sends in the `X-Simulated-Role` header (or `?role=`), so anyone can act as staff. | Role checks prove the workflow rules, not security. | Security | None needed in Cycle 1: synthetic data, localhost only. | Accepted (Cycle 1) | TD-02, ASM-001, REQ-006, API-005, `RoleCheckService`, ADR-0003 (PR #56, proposed) |
| LIM-003 | The server only listens on `127.0.0.1`. There is no hosted deployment. | Only the person running it can use it. | Deployment | None needed for a local demo | Accepted (Cycle 1) | TD-06, ASM-003 |
| LIM-004 | There is no browser page yet. The Presentation Layer on `main` is a JSON HTTP API plus a console demo runner; there is no submission form or staff list page. | ADR-001 and `architecture.md` say the Presentation Layer collects request fields and shows the staff list. Today that needs `curl` or the demo runner. | Presentation Layer; REQ-001, REQ-002 | Use `mvn exec:java` (demo) or HTTP requests. | Under Investigation — team to decide whether a page is part of Cycle 1 | ADR-001, `architecture.md`, `CampusConnectApp.java`, TASK-103 |
| LIM-005 | Duplicate submissions are not detected; both are created. | Staff may see the same request twice. | REQ-001 | None | Deferred | Q-004 (open), API-001 failure table |
| LIM-006 | Students cannot view the status of requests they submitted. | Students must ask staff for status. | REQ-001, REQ-002 | None | Deferred | Q-005 (open) |
| LIM-007 | Students cannot edit or cancel a request, although the Q-006 answer says they may (edit while Open, cancel while Open or In Progress). No requirement, contract, task or estimate exists for it. | The answer and the system disagree. | Scope; REQ-001, REQ-003 | None | Under Investigation — defer to Cycle 2 or add a requirement and re-estimate | Q-006 (PR #28), R-003 |
| LIM-008 | Only the current status and the time of the last change are kept. The system does not record who changed a status, and there is no status history. | A status change cannot be traced to a person afterwards. | REQ-003; audit | Console log shows the simulated role and time of each change. | Accepted (Cycle 1) | Q-007 (answered in PR #70: role and time are logged; individual users are not, because roles are simulated), R-017, `SupportRequest.updatedAt`, `AuditLogger` |
| LIM-009 | Category is free text; any non-blank value is accepted. | Categories can be inconsistent. | REQ-001 | None | Deferred | C-Q1 in `api-contracts.md` |

## Deferred Capabilities

Full list and reasons: [`scope.md`](../planning/scope.md) (Deferred Scope and Out of Scope) and ADR-002. Real login and persistence after restart are covered by LIM-002 and LIM-001.

| Capability | Reason Deferred | Impact on Current Release | Future Reference |
|---|---|---|---|
| Search, filtering and sorting | Not needed to prove the workflow | Simple list only | ADR-002 TD-04; `scope.md` |
| Automatic routing, notifications, dashboard | Need workflow policy and failure handling first | Staff work the list manually | `scope.md` Deferred Scope |
| AI categorization or any runtime AI | Needs a validation plan and human approval step | No AI in the product | ADR-002 TD-05 |
| Real Loyola data or systems | Needs institutional approval | Synthetic data only | ADR-002 TD-01; REQ-004 |

## Unresolved Defects and Open Review Findings

These findings come from building and running the code PRs locally on 2026-10-29. RF-01, RF-03, RF-04 and RF-05 were fixed in PR #67 (merged into #61) and are now on `main`. RF-02 and RF-06 were re-checked over HTTP on `main` at `7ee567c` and are still present. `defect-log.md` has no entries yet.

| ID | Finding | Impact | Severity | Current Disposition |
|---|---|---|---|---|
| RF-01 | Build fails: 74 compile errors in `CampusConnectApp.java` (unescaped quotes in JSON strings, lines 151–221). | The PRs cannot be built or tested; merging one breaks `main`. | High | Resolved: PR #67; `mvn test` 13/13 on `main` `7ee567c` |
| RF-02 | `GET /api/requests` returns only `{"status":"SUCCESS","count":N}`, not the requests. | REQ-002 / AC-REQ-002-01 not met over HTTP; the demo runner calls the Logic Layer directly, so it does not show this. | High | Open on `main`: fix in TASK-104 (#34), or log as a `DEF-###` defect before the tag |
| RF-03 | A value containing an escaped quote is cut off: the title `Say \"hi\" please` is stored as `Say \`, and the response is then invalid JSON. | The stored request differs from what the student sent (REQ-001, REQ-005). | Medium | Resolved: PR #67; test `quotesAndBackslashesInFieldsArePreserved` |
| RF-04 | The validation-error response is not valid JSON: `"missingFields":[title, description]`. | A client cannot read which fields are missing (AC-REQ-001-02). | Medium | Resolved: PR #67; test `missingFieldsReturn400ListingEveryMissingField` |
| RF-05 | The submit response leaves out `submittedAt` and `description`, which API-001 lists. | The student does not see the recorded submission time (REQ-001). | Medium | Resolved: PR #67; test `validSubmissionReturns201WithSystemAssignedIdStatusAndTime` |
| RF-06 | `?role=` is only read when it is the first query parameter (`?x=1&role=STAFF` is denied). | Confusing; the header works. | Low | Open on `main` (`?x=1&role=STAFF` still denied): accept or fix |

## Operational Limitations

| Limitation | Operational Impact | Related Evidence |
|---|---|---|
| No working CI: `.github/workflows/ci.yml` on `main` is the starter workflow that fails on purpose. | Nothing checks build or tests on PRs; RF-01 was found only by a local build. | `ci.yml`, #47, #48, R-019 |
| Logs are printed to the console and kept in memory only. | Log history is lost when the application stops. | `AuditLogger.java`, TASK-108, #49 |
| Manual start; no health check, restart or recovery. | A stopped application must be restarted by hand, and its data is gone (LIM-001). | R-013 |
| Each machine needs JDK 17 and Maven. | A member without them cannot run or test the system. | `pom.xml`, R-007 |

## Verification Limitations

| Limitation / Gap | What Has Been Verified | What Has Not Been Verified | Related Evidence |
|---|---|---|---|
| End-to-end run | — | The TASK-109 end-to-end run (TC-013) has not been done on `main`; it fails at the staff list until RF-02 is fixed. | #39 |
| Automated tests | `main` `7ee567c`: 13/13 pass (5 Data Layer, 8 HTTP). PR #62 `496fe55`: 18/18 pass (Hamza's review run, same result locally). All on 2026-10-29. | Logic Layer tests are not on `main` until PR #62 merges; no CI runs. | PR #62, R-019 |
| Manual HTTP check | First run on PR #61 with only the RF-01 fix (2026-10-29): a valid request was stored with an ID and status Open; missing fields, a missing role and an invalid status change were rejected. Re-run on `main` `7ee567c`: submit returns `description` and `submittedAt`. | Staff list still returns only a count (RF-02); `?x=1&role=STAFF` still denied (RF-06); status change not re-run on `main`. | RUN-005 in `test-evidence/2026-10-29-local-test-runs.md`; re-run on `main` in the PR #66 review reply |
| Failure paths | Storage failure returns `500` (test `storageFailureReturns500AndNeverSuccess`, on `main`). | Simultaneous requests, very large inputs. | API-001, REQ-005 |

## Limitation Changes

| Date / Release | Limitation ID | Change | Reason | Evidence |
|---|---|---|---|---|
| 2026-10-29 / A4 | LIM-001..LIM-009, RF-01..RF-06 | First list created | A4 known-limitations review | Issue #54 |
| 2026-10-29 / A4 | RF-01, RF-03, RF-04, RF-05; LIM-008; review basis | RF-01/03/04/05 → Resolved; LIM-008 to move to Accepted when PR #70 merges; file re-checked against `main` | PR #61 and #67 merged; Hamza's review CRF-015..CRF-018 | PR #66 review; `main` `7ee567c` |
| 2026-10-29 / A4 | LIM-008 | Under Investigation → Accepted (Cycle 1) | Q-007 answered and merged | PR #70 |
