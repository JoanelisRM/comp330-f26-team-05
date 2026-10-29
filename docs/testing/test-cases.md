# Test Cases

**Owner:** Khaja Shabbir Ahmed · **Backup:** Hamza Bashar
**Gate:** A4 — Cycle 1 Construction and Integration · **Last updated:** 2026-10-29 · **Tracking issue:** #53
**Status:** Proposed — pending review by the backup owner

**How this file relates to the others.** The mapping of the existing unit tests to acceptance criteria is in `test-strategy.md` (being rewritten in PR #62) and is not repeated here. This file adds the HTTP-level, integration, regression, review and end-to-end cases, and records what actually ran. Raw results: [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md). Automated tests live in `src/test/java` (Maven) and run with `mvn test`.

**Important:** no application code is on `main` yet. Every result below is for an open PR or the #33 fix PR (#67), and must be re-run on `main` after the code merges (TC-013).

## Test Case Catalog

| ID | Test Case | Requirement / Finding | Type | How Verified | Status (Run) |
|---|---|---|---|---|---|
| TC-001 | Valid submission over HTTP returns `201` with a system-assigned ID, status Open, `description` and `submittedAt`; ID, status or time sent by the client are ignored | REQ-001, AC-REQ-001-01, API-001, RF-05 | Integration (HTTP) | `CampusConnectAppSubmitTest`: `validSubmission…`, `clientSuppliedIdStatusAndTimeAreIgnored` | Pass (RUN-006) |
| TC-002 | Missing, whitespace-only or malformed fields return `400`, list every missing field as a valid JSON array, and store nothing | AC-REQ-001-02, RF-04 | Negative | `CampusConnectAppSubmitTest`: `missingFields…`, `whitespaceOnlyFields…`, `malformedBody…` | Pass (RUN-006); Fail on PR #61 (RUN-005 #7) |
| TC-003 | Staff role or no role cannot submit (`403`); nothing stored | REQ-001, API-005 | Security | `CampusConnectAppSubmitTest`: `staffOrMissingRoleCannotSubmit` | Pass (RUN-006) |
| TC-004 | Quotes and backslashes in a field are stored and returned exactly | RF-03, REQ-005 | Boundary | `CampusConnectAppSubmitTest`: `quotesAndBackslashesInFieldsArePreserved` | Pass (RUN-006); Fail on PR #61 (RUN-005 #2) |
| TC-005 | A storage failure returns `500` and never reports success | API-001 failure table, REQ-005 | Failure path | `CampusConnectAppSubmitTest`: `storageFailureReturns500AndNeverSuccess` | Pass (RUN-006); no response on PR #61 |
| TC-006 | Staff list over HTTP shows each request's ID, title, submission time and status | AC-REQ-002-01, RF-02 | Integration (HTTP) | Manual (`curl`); automated test to come with TASK-104 (#34) | **Fail** (RUN-005 #3) |
| TC-007 | Empty list returns no requests and a "no requests" message, with no error | AC-REQ-002-02 | Boundary | Not yet automated | Not run |
| TC-008 | Submit → staff list → change status → list again shows the request with its latest status, without a restart | AC-REQ-002-04, AC-REQ-003-05, AC-REQ-005-01 | Integration | Not yet automated; the demo runner covers part of it (RUN-007) | Not run |
| TC-009 | Student cannot open the staff list or change a status; the status stays unchanged | AC-REQ-002-03, AC-REQ-003-04 | Security | `SupportRequestServiceTest#roleCheckDeniesStudentFromListingOrUpdatingRequests` (PR #62) | Pass only with the RF-01 fix (RUN-004b); build fails as-is |
| TC-010 | Every role decision is made in one place in the Logic Layer | AC-REQ-006-01, R-016 | Code review | Read the code in PRs #57, #58 and #61: only `RoleCheckService` decides; the Presentation Layer passes the header on; the Data Layer has no role logic | Pass (review, 2026-10-29) |
| TC-011 | The repository and stored data contain synthetic data only | AC-REQ-004-01, REQ-004 | Inspection | Manual inspection before the A4 tag | Not run |
| TC-012 | `mvn test` builds and the full suite passes | RF-01 | Build | Local runs; CI not configured (R-019) | Pass: #57, #58, PR #67. **Fail:** #61, #62, #60 (RUN-001..004) |
| TC-013 | End-to-end run on `main` without a restart: submit, list, Open → In Progress → Resolved, list | TASK-109, AC-REQ-005-01, R-013 | System (manual) | Steps below | Not run (no code on `main`) |

## Detailed Test Cases

Automated cases are described by their test methods. Only the manual cases need steps.

### TC-006 — Staff list over HTTP

| Step | Action | Expected Result |
|---|---|---|
| 1 | Start the server (`--serve`) and submit two requests as `STUDENT` | Two `201` responses |
| 2 | `GET /api/requests` with `X-Simulated-Role: STAFF` | `200`; both requests with ID, title, submission time and status |

**Actual (RUN-005):** `{"status":"SUCCESS","count":2}`, a count with no request data. **Decision:** Fix, under TASK-104 (#34).

### TC-013 — End-to-end on `main`

| Step | Action | Expected Result |
|---|---|---|
| 1 | `mvn test` on the latest `main` | `BUILD SUCCESS`, all tests pass |
| 2 | Start the server; submit one request as `STUDENT` | `201`, ID `REQ-1001`, status Open |
| 3 | `GET /api/requests` as `STAFF` | The request is listed with status Open |
| 4 | Change the status to In Progress, then to Resolved, as `STAFF` | Two `200` responses |
| 5 | Try Resolved → Open | `409`, status unchanged |
| 6 | `GET /api/requests` as `STAFF` (no restart) | The request is listed with status Resolved |

**Owner:** Joanelis Rivera Matos (#39) · **Backup:** Khaja Shabbir Ahmed. Record the result here and in `test-evidence/`.

## Regression Cases

Findings from `docs/release/known-limitations.md` and the tests that protect against them.

| Finding | Behavior Protected | Test Case | Evidence |
|---|---|---|---|
| RF-01 | The project compiles and every test runs | TC-012 | RUN-003, RUN-004 |
| RF-02 | The staff list returns the requests, not only a count | TC-006 | RUN-005 #3 |
| RF-03 | Quotes and backslashes are not cut off | TC-004 | RUN-006 |
| RF-04 | Error responses are valid JSON | TC-002 | RUN-006 |
| RF-05 | The submit response includes `submittedAt` and `description` | TC-001 | RUN-006 |
| RF-06 | `?role=` works in any position | None. Low severity; disposition open | RUN-005 #5 |

## Test Execution Results

Commits, commands and output for RUN-001..RUN-007 are in [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md). RUN-006 and RUN-007 are on the #33 fix branch (PR #67).

## Blocked or Not-Run Tests

| Test Case | Reason | Owner | Next Action |
|---|---|---|---|
| TC-006 | Fails: staff list returns only a count | Joanelis Rivera Matos (TASK-104, #34) | Return the request fields, add an automated test |
| TC-007, TC-008 | Not automated yet | Hamza Bashar (TASK-106, #36) | Add tests once the code PRs merge |
| TC-011 | Inspection planned for the tag | Khaja Shabbir Ahmed | Inspect the repository before the A4 tag |
| TC-013 | No application code on `main` | Joanelis Rivera Matos (#39) | Run after the code PRs merge |
| All | No CI (R-019) | Nova Kurul (#47) | Re-run every case in CI once `ci.yml` exists |
