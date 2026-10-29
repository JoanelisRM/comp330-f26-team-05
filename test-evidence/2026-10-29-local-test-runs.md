# Local Test Runs — 2026-10-29

**Recorded by:** Khaja Shabbir Ahmed · **Tracking issue:** #53 · **Test cases:** [`docs/testing/test-cases.md`](../docs/testing/test-cases.md)

**Environment:** macOS, OpenJDK 17.0.18, Apache Maven 3.10.0, run locally (no CI yet, R-019). Command: `mvn -B test` from the repository root on the commit shown.

**Why locally:** no application code is on `main` and `.github/workflows/ci.yml` is still the starter workflow, so these runs are the only build and test evidence for the open code PRs.

## RUN-001 to RUN-004 — Build and unit tests per PR

| Run | Commit (PR) | Result | Key output |
|---|---|---|---|
| RUN-001 | `663fbd0` (#57) | Build passes; no tests in this PR | `BUILD SUCCESS` |
| RUN-002 | `a897b31` (#58) | 5/5 pass | `Tests run: 5, Failures: 0, Errors: 0` (`InMemorySupportRequestRepositoryTest`) |
| RUN-003 | `49adb8e` (#61) | Build fails | `74 errors` … `CampusConnectApp.java:[151,38] ')' expected` … `BUILD FAILURE`. Reproduced separately by Khaja in a local terminal. |
| RUN-004a | `bdff8e0` (#62) as-is | Build fails | `74 errors`, `BUILD FAILURE` (same file as #61) |
| RUN-004b | `bdff8e0` (#62) + RF-01 quote fix only | 10/10 pass | `SupportRequestServiceTest` 5/5, `InMemorySupportRequestRepositoryTest` 5/5, `BUILD SUCCESS` |
| RUN-004c | `d2737d1` (#60) as-is / + RF-01 fix | Fails / 10/10 pass | Same results as #62 |

## RUN-005 — Manual HTTP check of PR #61

PR #61 (`49adb8e`) with only the RF-01 quote fix applied, started with `java -cp target/classes edu.luc.comp330.campusconnect.presentation.CampusConnectApp --serve 18761`. `U=http://127.0.0.1:18761/api/requests`.

| # | Request | Response | Finding |
|---|---|---|---|
| 1 | `POST $U` role `student`, `{"category":"IT","title":"Wifi","description":"Synthetic test"}` | `{"status":"SUCCESS","request":{"requestId":"REQ-1001","category":"IT","title":"Wifi","requestStatus":"OPEN","synthetic":true}}` | Works; no `submittedAt` or `description` (RF-05) |
| 2 | `POST $U` role `STUDENT`, title `Say \"hi\" please` | `…"title":"Say \","requestStatus":"OPEN"…` | Title cut off; invalid JSON (RF-03) |
| 3 | `GET $U` role `STAFF` | `{"status":"SUCCESS","count":2}` | No request data (RF-02) |
| 4 | `GET $U?role=STAFF` | `{"status":"SUCCESS","count":2}` | Query role works as first parameter |
| 5 | `GET $U?x=1&role=STAFF` | `{"status":"ROLE_DENIED",…'MISSING'…}` | Role ignored when not first (RF-06) |
| 6 | `POST $U` with no role | HTTP `403` | Works |
| 7 | `POST $U` role `STUDENT`, `{"category":"IT"}` | `{"status":"VALIDATION_ERROR","missingFields":[title, description],…}` | All missing fields listed, but not valid JSON (RF-04) |
| 8 | `PATCH $U/REQ-1001/status` role `STAFF`, `{"newStatus":"RESOLVED"}` | `{"status":"INVALID_TRANSITION",…"from OPEN to RESOLVED"…}` | Works (skip rejected) |
| 9 | `POST $U` role `STUDENT`, form-encoded body | `VALIDATION_ERROR`, all three fields missing | Works |

## RUN-006 — Fix for #33 (PR #67)

Branch `fix/submit-request-33` at `e2c5958` (PR #67: five commits on top of PR #61). `mvn -B test`: **13/13 pass** (`InMemorySupportRequestRepositoryTest` 5, `CampusConnectAppSubmitTest` 8), `BUILD SUCCESS`.

Check that the new tests detect the bugs: `CampusConnectAppSubmitTest` run against `a06910c` (only the RF-01 fix) gives **6 of 8 failing**: valid submission (missing fields in response), missing fields, whitespace-only fields, malformed body (invalid JSON array), quotes and backslashes, storage failure (`HTTP/1.1 header parser received no bytes`: no response). The two that pass (role denied, client-supplied values ignored) were already correct.

## RUN-007 — Demo runner

`mvn -q exec:java` on `e2c5958`: seven scripted steps, `totalEvents=7, successes=4, rejectionsOrDenials=3`: submit, reject blank fields, deny student status change, staff list (1 request), Open → In Progress, In Progress → Resolved, reject Resolved → Open.

## RUN-008 — Integration rehearsal for TASK-109 (#39)

A throwaway local copy of `main` (`45f6ad2`) with the code PRs merged in stack order: #57 `663fbd0` → #58 `a897b31` → #61 `49adb8e` → #67 `e2c5958` → #62 `bdff8e0` → #60 `d2737d1`. **Every merge was clean.** Then the TC-013 steps were run on one server process, with no restart (`--serve 18762`).

| Step | Request | Response | Result |
|---|---|---|---|
| 1 | `mvn -B test` | `Tests run: 18, Failures: 0, Errors: 0` (service 5, repository 5, HTTP 8), `BUILD SUCCESS` | Pass |
| 2 | `POST` as `STUDENT` | `201`, `REQ-1001`, `OPEN`, `submittedAt` present | Pass |
| 3 | `GET` as `STAFF` | `200 {"status":"SUCCESS","count":1}` | **Fail**: no request data (RF-02) |
| 4 | `PATCH` → `IN_PROGRESS`, then → `RESOLVED` | `200`, `200`; responses show the new status | Pass |
| 5 | `PATCH` → `OPEN` | `409 INVALID_TRANSITION` | Pass |
| 6 | `GET` as `STAFF` | `200 {"status":"SUCCESS","count":1}` | **Fail**: status not visible (RF-02) |
| — | `GET` and `PATCH` as `STUDENT` | `403`, `403` | Pass |

The server log had one line per action (8 actions: 5 `INFO`, 3 `WARN`), each with the simulated role and, where there is one, the request ID.
