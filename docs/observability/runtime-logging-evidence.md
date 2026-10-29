# Runtime Visibility, Logging, and Error-Handling Evidence

- **Deliverable:** Runtime / Logging Evidence (COMP 330 — Assignment 4)
- **Authoritative GitHub Locations:** `/docs/observability/runtime-logging-evidence.md` and `/docs/testing/runtime-diagnostics-log.txt`
- **Primary Owner:** Sydel Ugwu (`@Sugwu123`, Architecture & Development Lead)
- **Backup Owner / Reviewer:** Nova Kurul (`@selkianova`, Operations & Evidence Lead)
- **Related Issue:** [Issue #49 — A4: Runtime/Logging Evidence](https://github.com/JoanelisRM/comp330-f26-team-05/issues/49)
- **Related Requirements & Decisions:** `REQ-001`, `REQ-003`, `REQ-004`, `REQ-005`, `REQ-006`, `API-001..005`, `Q-007`, `ADR-0003`, `TASK-108`
- **Last Updated:** 2026-10-28

## 1. Purpose and Scope

This document provides Cycle 1 runtime visibility, structured logging, error-handling diagnostics, and operational metrics evidence for CampusConnect (`Issue #49`). It closes open question `Q-007` from the Assignment 3 architecture review by defining the structured log format, event taxonomy, and privacy boundaries (`REQ-004`), and links the captured runtime output from executing both happy-path workflows and negative/security failure paths.

## 2. Structured Logging Design (`AuditLogger` & `Q-007` Resolution)

The `AuditLogger` component (`src/main/java/edu/luc/comp330/campusconnect/observability/AuditLogger.java`) emits structured key-value diagnostic lines to standard output (`stdout`) for immediate console/CI visibility and retains an inspectable in-memory `List<LogEntry>` buffer for automated test assertions (`SupportRequestServiceTest`).

### Log Entry Schema
```text
<ISO-8601-UTC-Timestamp> | level=<INFO|WARN|ERROR> | event=<EVENT_TYPE> | role=<STUDENT|STAFF|NONE> | requestId=<REQ-####|N/A> | outcome=<SUCCESS|REJECTED|DENIED|ERROR> | details=<Diagnostic Message>
```

### Logged Event Taxonomy

| Event Type | Log Level | Triggering Boundary / Contract | Outcome | Purpose for Troubleshooting & Review |
|---|---|---|---|---|
| `SUBMISSION_CREATED` | `INFO` | `API-001` (`SupportRequestService.submitRequest`) | `SUCCESS` | Confirms a valid student request was persisted in `API-004` (`InMemorySupportRequestRepository`) with assigned ID and timestamp (`REQ-001`, `REQ-005`). |
| `SUBMISSION_REJECTED` | `WARN` | `API-001` (`SupportRequestService.submitRequest`) | `REJECTED` | Lists exact missing or whitespace-only required fields (`category`, `title`, `description`) without mutating storage (`AC-REQ-001-02`). |
| `REQUEST_LIST_VIEWED` | `INFO` | `API-002` (`SupportRequestService.listRequests`) | `SUCCESS` | Records staff retrieval of the request queue along with total record count (`REQ-002`). |
| `STATUS_UPDATED` | `INFO` | `API-003` (`SupportRequestService.updateStatus`) | `SUCCESS` | Audits staff status transition (`previousStatus -> newStatus`) after `API-004` storage succeeds (`REQ-003`, `REQ-005`). |
| `TRANSITION_REJECTED` | `WARN` | `API-003` (`SupportRequestService.updateStatus`) | `REJECTED` | Captures disallowed state transitions (e.g., `OPEN -> RESOLVED` or `RESOLVED -> OPEN` per `Q-002`) or unknown `requestId` lookups (`404`/`409`). |
| `ROLE_DENIED` | `WARN` | `API-005` (`RoleCheckService.requireRole`) | `DENIED` | Audits unauthorized or missing `X-Simulated-Role` attempts before any workflow or storage operation runs (`REQ-006`). |

### Data Privacy & Security Boundary (`REQ-004`)
- **Synthetic Data Only:** Logs record only synthetic `requestId` values (`REQ-1001`, etc.), category names, status transitions, and field-name validation errors.
- **Zero PII / Credentials:** No student names, university IDs, email addresses, passwords, tokens, or raw request descriptions are written to diagnostic logs.

## 3. Captured Runtime & Diagnostic Log Output

Running the vertical-slice verification scenario (`mvn compile exec:java` or `CampusConnectApp --demo`) executes 7 end-to-end operations covering positive workflows, validation failures, role denials, and disallowed transitions:

```text
=== CampusConnect Cycle 1 Runtime & Diagnostics Verification ===
2026-10-28T23:45:01.104Z | level=INFO  | event=SUBMISSION_CREATED   | role=STUDENT | requestId=REQ-1001 | outcome=SUCCESS  | details=Created synthetic support request (category='Academic Advising') with status OPEN
2026-10-28T23:45:01.112Z | level=WARN  | event=SUBMISSION_REJECTED  | role=STUDENT | requestId=N/A      | outcome=REJECTED | details=Validation failed; missing or whitespace-only fields: [title, description]
2026-10-28T23:45:01.118Z | level=WARN  | event=ROLE_DENIED          | role=STUDENT | requestId=REQ-1001 | outcome=DENIED   | details=Role 'STUDENT' is not authorized for action 'UPDATE_STATUS' (requires STAFF)
2026-10-28T23:45:01.123Z | level=INFO  | event=REQUEST_LIST_VIEWED  | role=STAFF   | requestId=N/A      | outcome=SUCCESS  | details=Returned 1 synthetic support request(s) to STAFF view
2026-10-28T23:45:01.129Z | level=INFO  | event=STATUS_UPDATED       | role=STAFF   | requestId=REQ-1001 | outcome=SUCCESS  | details=Transitioned request REQ-1001 from OPEN to IN_PROGRESS
2026-10-28T23:45:01.134Z | level=INFO  | event=STATUS_UPDATED       | role=STAFF   | requestId=REQ-1001 | outcome=SUCCESS  | details=Transitioned request REQ-1001 from IN_PROGRESS to RESOLVED
2026-10-28T23:45:01.140Z | level=WARN  | event=TRANSITION_REJECTED  | role=STAFF   | requestId=REQ-1001 | outcome=REJECTED | details=Invalid status transition from RESOLVED to OPEN (allowed: OPEN -> IN_PROGRESS -> RESOLVED)
=== Operational Summary: totalEvents=7, successes=4, validationRejections=1, roleDenials=1, transitionRejections=1, repositoryCount=1 ===
```

## 4. Error Handling & Troubleshooting Guide

| Symptom / HTTP Status | Diagnostic Event in Log | Root Cause | Resolution / Troubleshooting Step |
|---|---|---|---|
| `400 Bad Request` (`VALIDATION_ERROR`) | `event=SUBMISSION_REJECTED` | Caller omitted `category`, `title`, or `description`, or passed whitespace-only strings (`"   "`). | Inspect `details=Validation failed; missing or whitespace-only fields: [...]` and supply non-blank text for all three fields (`REQ-001`). |
| `403 Forbidden` (`ROLE_DENIED`) | `event=ROLE_DENIED` | Missing `X-Simulated-Role` header, unrecognized role value, or `STUDENT` attempting a `STAFF`-only action (`API-002`, `API-003`). | Pass `X-Simulated-Role: STUDENT` for `POST /api/requests` and `X-Simulated-Role: STAFF` for `GET /api/requests` and `PATCH /api/requests/{id}/status` (`ADR-0003`, `REQ-006`). |
| `409 Conflict` (`INVALID_TRANSITION`) | `event=TRANSITION_REJECTED` | Staff attempted to skip `IN_PROGRESS` (`OPEN -> RESOLVED`), repeat the same status, or reopen a `RESOLVED` request (`Q-002`). | Follow the forward-only sequence `OPEN -> IN_PROGRESS -> RESOLVED` (`REQ-003`). |
| `404 Not Found` (`NOT_FOUND`) | `event=TRANSITION_REJECTED` | `requestId` does not exist in `InMemorySupportRequestRepository` (or server restarted, clearing in-memory state per `Q-003`). | Query `GET /api/requests` (`API-002`) to verify active synthetic `requestId` values in the current process run. |
