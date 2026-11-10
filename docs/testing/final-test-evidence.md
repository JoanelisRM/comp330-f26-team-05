# Final Test Evidence — Cycle 1 Release

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup / Reviewer:** Khaja Shabbir Ahmed
**Gate:** A5 — Cycle 1 Release · **Tracking issue:** #83 (Final Testing Evidence)
**Last updated:** 2026-11-10 · **Code tested:** `main` at `321aef6`

This file records the testing done after Assignment 4 on the final Cycle 1 code. Every result below was produced on `main` at `321aef6` unless a row says otherwise. Earlier A4 runs (RUN-001..RUN-009) are in [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md).

## Summary

| Evidence ID | Type | What Ran | Result |
|---|---|---|---|
| FT-001 | Unit and HTTP tests (automated) | `mvn test` on `main` at `321aef6`, 2026-11-10 00:48 CST | 36 run, 0 failures, 0 errors, 0 skipped, BUILD SUCCESS |
| FT-002 | System test (manual, end to end over HTTP) | 12 `curl` steps against a running server, 2026-11-10 00:45 CST | 12 of 12 steps as expected |
| FT-003 | Reviewer run of an open PR | `mvn test` on PR #95 at `00879c7`, 2026-11-10 00:34 CST | 36 run, 0 failures, BUILD SUCCESS (PR not merged) |
| FT-004 | CI | `Java CI with Maven` on `main` | See CI Evidence |

All runs were done by Hamza Bashar on macOS with a local JDK and Maven. Only synthetic data was used.

## FT-001 — Automated Test Suite

Command: `mvn test` · Finished: 2026-11-10T00:48:29-06:00 · Total time: 2.091 s

| Test Class | Layer | Tests | Failures | Errors | Skipped |
|---|---|---:|---:|---:|---:|
| `RoleCheckServiceTest` | Logic | 9 | 0 | 0 | 0 |
| `RequestStatusTest` | Model | 9 | 0 | 0 | 0 |
| `InMemorySupportRequestRepositoryTest` | Data | 5 | 0 | 0 | 0 |
| `CampusConnectAppRoleTransportTest` | Presentation (HTTP) | 5 | 0 | 0 | 0 |
| `CampusConnectAppSubmitTest` | Presentation (HTTP) | 8 | 0 | 0 | 0 |
| **Total** | | **36** | **0** | **0** | **0** |

```text
[INFO] Tests run: 36, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Finished at: 2026-11-10T00:48:29-06:00
```

**Change since A4:** the A4 suite had 31 tests in four classes. `CampusConnectAppRoleTransportTest` (5 tests) was added in PR #97.

## FT-002 — End-to-End Run on `main` (TC-013)

This is the first run of TC-013 on `main`. The A4 rehearsal (RUN-008) failed at the list steps because of RF-02.

**Setup:** server started with `mvn -q exec:java -Dexec.args="--serve 8080"`; requests sent with `curl` to `http://127.0.0.1:8080/api/requests`. The server was not restarted during the run.

| Step | Request | Role | Expected | Actual | Result |
|---|---|---|---|---|---|
| 1 | `GET /api/requests` (nothing submitted) | `STAFF` | `200`, empty list, "no requests" message | `200`, `"count":0,"requests":[]`, "No requests have been submitted" | Pass |
| 2 | `POST /api/requests` with category, title, description | `STUDENT` | `201`, system ID, status Open | `201`, `REQ-1001`, `OPEN`, `submittedAt` set | Pass |
| 3 | `POST /api/requests` with only a category | `STUDENT` | `400`, lists the missing fields | `400`, `"missingFields":["title","description"]` | Pass |
| 4 | `GET /api/requests` | `STAFF` | `200`, the request with ID, title, time, status | `200`, 1 request, `REQ-1001`, `OPEN` | Pass |
| 5 | `GET /api/requests` | `STUDENT` | `403` | `403`, `ROLE_DENIED` | Pass |
| 6 | `PATCH /api/requests/REQ-1001/status` to `IN_PROGRESS` | `STUDENT` | `403`, status unchanged | `403`, `ROLE_DENIED`; step 7 shows it was still Open | Pass |
| 7 | `PATCH` to `IN_PROGRESS` | `STAFF` | `200` | `200`, `IN_PROGRESS` | Pass |
| 8 | `PATCH` to `RESOLVED` | `STAFF` | `200` | `200`, `RESOLVED` | Pass |
| 9 | `PATCH` to `OPEN` (backward) | `STAFF` | `409`, status unchanged | `409`, `INVALID_TRANSITION` | Pass |
| 10 | `PATCH /api/requests/REQ-9999/status` | `STAFF` | `404` | `404`, `NOT_FOUND` | Pass |
| 11 | `GET /api/requests?role=STAFF` with no header | none | `403` (role in the URL is ignored) | `403`, `ROLE_DENIED` | Pass |
| 12 | `GET /api/requests` (no restart) | `STAFF` | `200`, request shown as Resolved | `200`, `REQ-1001`, `RESOLVED` | Pass |

Selected responses:

```text
== 1 empty list (STAFF)
{"status":"SUCCESS","count":0,"requests":[],"message":"No requests have been submitted"}
HTTP 200
== 4 list (STAFF)
{"status":"SUCCESS","count":1,"requests":[{"id":"REQ-1001","title":"Synthetic wifi issue","summary":"Synthetic test request","submittedAt":"2026-11-10T06:45:46.650845Z","status":"OPEN"}]}
HTTP 200
== 9 RESOLVED to OPEN
{"status":"INVALID_TRANSITION","missingFields":[],"message":"Invalid status transition from RESOLVED to OPEN (allowed: OPEN -> IN_PROGRESS -> RESOLVED)"}
HTTP 409
== 12 final list (STAFF)
{"status":"SUCCESS","count":1,"requests":[{"id":"REQ-1001","title":"Synthetic wifi issue","summary":"Synthetic test request","submittedAt":"2026-11-10T06:45:46.650845Z","status":"RESOLVED"}]}
HTTP 200
```

### Runtime Log During the Run

The server's audit log recorded one line per step, with the role, request ID and outcome. This is the observability evidence for the same run:

```text
level=INFO  | event=REQUEST_LIST_VIEWED  | role=STAFF   | requestId=N/A      | outcome=SUCCESS  | details=Returned 0 synthetic support request(s) to STAFF view
level=INFO  | event=SUBMISSION_CREATED   | role=STUDENT | requestId=REQ-1001 | outcome=SUCCESS  | details=Created synthetic support request (category='IT Help') with status OPEN
level=WARN  | event=SUBMISSION_REJECTED  | role=STUDENT | requestId=N/A      | outcome=REJECTED | details=Validation failed; missing or whitespace-only fields: [title, description]
level=WARN  | event=ROLE_DENIED          | role=STUDENT | requestId=REQ-1001 | outcome=DENIED   | details=Role 'STUDENT' is not authorized for action 'UPDATE_STATUS' (requires STAFF)
level=INFO  | event=STATUS_UPDATED       | role=STAFF   | requestId=REQ-1001 | outcome=SUCCESS  | details=Transitioned request REQ-1001 from IN_PROGRESS to RESOLVED
level=WARN  | event=TRANSITION_REJECTED  | role=STAFF   | requestId=REQ-1001 | outcome=REJECTED | details=Invalid status transition from RESOLVED to OPEN (allowed: OPEN -> IN_PROGRESS -> RESOLVED)
level=WARN  | event=ROLE_DENIED          | role=NONE    | requestId=N/A      | outcome=DENIED   | details=Missing or unrecognized simulated role 'MISSING' for action 'LIST_REQUESTS' (requires STAFF)
```

(7 of the 12 lines shown; timestamps 2026-11-10T06:45:46Z removed for width.)

### Observations From the Run

Neither affects a pass or fail result. Both are recorded for Cycle 2.

| ID | Observation | Evidence | Effect |
|---|---|---|---|
| FT-OBS-01 | Error responses on the status route include an empty `"missingFields":[]`, which only makes sense for a validation error | Steps 6, 9, 10 | Cosmetic; status code and message are correct |
| FT-OBS-02 | The list uses `id`, `summary`, `status`; the submit and status responses use `requestId`, `description`, `requestStatus` for the same data | Steps 2 and 4 | A client must handle two sets of names |

## FT-003 — Reviewer Run of PR #95

PR #95 (Sydel Ugwu) adds a duplicate-ID check, removes `clear()`, and adds 3 repository tests and `Cycle1AcceptanceTest` (2 tests). As the requested reviewer, Hamza ran `mvn test` on the PR at `00879c7` on 2026-11-10 00:34 CST: 36 run, 0 failures, BUILD SUCCESS.

This run was on the PR branch before `main` (with PR #97's 5 tests) was merged into it. The PR is approved but **not merged**, so its 5 tests are not part of FT-001 and not part of the release. If it merges, the suite on `main` becomes 41 tests and FT-001 should be re-run.

## CI Evidence

Workflow: `Java CI with Maven` (`.github/workflows/ci.yml`), which runs `mvn -B package` and `mvn test` on every pull request to `main` and every push to `main`. Runs are listed on the repository's **Actions** tab. The rows below were read from that tab on 2026-11-10 at about 00:49 CST, when it showed 27 workflow runs, the latest being #28. Every run shown on the page (#14 to #28) had passed.

| Run | Date (CST) | Trigger | Commit / PR | Result | Why It Matters |
|---|---|---|---|---|---|
| #16 | 2026-11-09 19:53 | Pull request | PR #95 (`sydel/a5-remediation-and-e2e-acceptance-test`) | Passed (29s) | CI check on the PR with the CRF-006/007/010 fixes (not merged) |
| #19 | 2026-11-09 23:13 | Pull request | PR #97 (`fix/role-header-only-rf06`) | Passed (22s) | CI check on the RF-06 fix before review and merge |
| #22 | 2026-11-09 23:26 | Push to `main` | `b3613ae` (PR #99 merged) | Passed (22s) | The commit carrying the `a5-cycle1-release` tag |
| #23 | 2026-11-10 ~00:28 | Push to `main` | `e9f93e6` (PR #97 merged) | Passed (19s) | `main` after the RF-06 fix |
| #24 | 2026-11-10 ~00:36 | Pull request | PR #98, updated | Passed (21s) | CI check after `main` was merged into the PR |
| **#26** | 2026-11-10 ~00:37 | Push to `main` | **`321aef6`** (PR #98 merged) | Passed (24s) | **Same commit as FT-001 and FT-002** |
| #28 | 2026-11-10 ~00:43 | Pull request | PR #101 (`docs/defect-review-85`) | Passed (24s) | Latest run at the time of writing |

Run #26 and FT-001 are the same code (`321aef6`), so the local result of 36 tests is the suite CI ran on that commit. The test count was read from the local run, not from the CI log.

**Note on the tag:** `a5-cycle1-release` is on `b3613ae` (run #22). PR #97 (the RF-06 fix and 5 tests) and PR #98 were merged after it, so the tagged commit has 31 tests and still contains the `?role=` fallback. The evidence in this file is for `321aef6`, which is newer than the tag.

**CI is not enforced:** there is no branch protection, so a passing check is team practice and not a merge requirement.

## Acceptance Criteria Coverage on `main`

| Criterion | Automated Test (FT-001) | Manual Step (FT-002) | Status |
|---|---|---|---|
| AC-REQ-001-01 valid submission | `CampusConnectAppSubmitTest`: `validSubmissionReturns201WithSystemAssignedIdStatusAndTime`, `clientSuppliedIdStatusAndTimeAreIgnored` | Step 2 | Verified |
| AC-REQ-001-02 missing fields rejected | `missingFieldsReturn400ListingEveryMissingField`, `whitespaceOnlyFieldsCountAsMissing`, `malformedBodyReportsAllFieldsMissing` | Step 3 | Verified |
| AC-REQ-002-01 staff list content | `CampusConnectAppRoleTransportTest`: `headerRoleWorksWithAnyQueryParameters` (checks the request ID is listed) | Step 4 (ID, title, time and status all shown) | Verified; title, time and status by manual step only |
| AC-REQ-002-02 empty list | None on `main` (in PR #95) | Step 1 | Verified manually only |
| AC-REQ-002-03 student cannot list | `RoleCheckServiceTest`; `roleInQueryStringIsIgnored` | Steps 5, 11 | Verified |
| AC-REQ-002-04 new submission visible | None on `main` (in PR #95) | Steps 2, 4 | Verified manually only |
| AC-REQ-003-01, -02 Open → In Progress → Resolved | `RequestStatusTest`; `statusChangeWithPatchSucceeds` | Steps 7, 8 | Verified |
| AC-REQ-003-03 invalid transitions rejected | `RequestStatusTest` | Step 9 | Verified |
| AC-REQ-003-04 student cannot change status | `RoleCheckServiceTest` | Step 6 | Verified |
| AC-REQ-003-05 status change visible in list | None on `main` (in PR #95) | Step 12 | Verified manually only |
| AC-REQ-004-01 synthetic data only | Not testable by a unit test (CRF-009) | Only synthetic data used in this run | Inspection TC-011 still not run |
| AC-REQ-005-01 state kept during the run | `InMemorySupportRequestRepositoryTest` | Steps 4, 12 (no restart) | Verified |
| AC-REQ-006-01 role checks in one place | Code review (TC-010, A4) | — | Verified by review |

## Test Cases Updated by This Evidence

Status of the cases in [`test-cases.md`](test-cases.md) that were failing or not run at A4:

| Test Case | A4 Status | Status Now | Evidence |
|---|---|---|---|
| TC-006 staff list over HTTP | Fail (RF-02) | Pass | FT-002 step 4; FT-001 |
| TC-007 empty list | Not run | Pass (manual) | FT-002 step 1 |
| TC-008 submit → list → change → list | Not run | Pass (manual) | FT-002 steps 2, 4, 7, 8, 12 |
| TC-011 synthetic-data inspection | Not run | Not run | Owner: Khaja Shabbir Ahmed |
| TC-012 build and full suite | Fail on some PRs | Pass | FT-001 |
| TC-013 end to end on `main` | Not run on `main` | Pass | FT-002 |

## What Was Not Tested

| Gap | Why | Risk | Next Action |
|---|---|---|---|
| No automated test on `main` for AC-REQ-002-02, AC-REQ-002-04, AC-REQ-003-05 | The tests are in PR #95, not merged | A later change could break them unnoticed; they pass manually today | Merge PR #95 |
| No automated end-to-end test over HTTP | `Cycle1AcceptanceTest` (PR #95) calls the services directly (CRF-025) | The HTTP path is checked end to end only by hand | Cycle 2 |
| TC-011 inspection | Not yet done | Low; all test and demo data seen is synthetic | Khaja Shabbir Ahmed, before the final baseline |
| Wrong method on the status route returns `404`, not `405` (CRF-024) | Deferred | Low | Cycle 2 |
| Two requests at the same time, restart behaviour, load | Out of Cycle 1 scope (LIM-001) | Accepted | Cycle 2 |
| AI validation tests | The system has no AI feature | None | Not applicable |

## Evidence Integrity

- FT-001, FT-002 and FT-003 were run by Hamza Bashar on his own machine; the output above is copied from the terminal.
- The commit was confirmed with `git log --oneline -1` (`321aef6`) before the runs.
- The manual run is one pass. It was not repeated.
- AI assistance: Claude prepared the `curl` steps and drafted this file from the terminal output. Disclosed in `docs/ai/ai-use-log.md`.