# Observability Plan

**Owner:** Nova Kurul (@selkianova) | **Backup / Reviewer:** Joanelis Rivera Matos (@JoanelisRM)  
**Gate:** A6 — Final Cycle 2 Release  
**Tracking Issue:** #115  

## 1. Observability Objectives
Because CampusConnect is a local, bounded Java/HTTP workflow application using synthetic data, our observability goal is practical operational visibility rather than enterprise-scale infrastructure. Our signals answer five core engineering questions:
1. Is the local HTTP server running and accepting requests on the configured port?
2. Are student submissions, staff listings, and status updates succeeding or failing?
3. Are unauthorized role attempts (`STUDENT` attempting `STAFF` actions) and invalid status transitions being caught and rejected?
4. Can an engineer correlate a runtime event or rejection back to a specific `requestId`, `role`, and timestamp?
5. Are there any unexpected storage or serialization failures during execution?

## 2. Signals and Coverage

| Concern | Signal Type | Source | What It Tells Us | Owner |
|---|---|---|---|---|
| Request lifecycle & outcomes | Structured Runtime Logs | Presentation & Logic Layers (`CampusConnectApp`, `AuditLogger`) | Confirms whether submissions, list views, and status updates succeeded or were rejected | Nova Kurul |
| Authorization & validation rejections | Warning Logs (`WARN`) | `RoleCheckService` / `CampusConnectApp` | Makes denied role attempts, missing fields, and invalid status transitions visible | Nova Kurul |
| Build & regression health | CI Pipeline Logs | GitHub Actions (`ci.yml`) | Confirms all 36 automated tests compile and pass on every PR and push to `main` | Nova Kurul |
| End-to-end HTTP behavior | Manual Runtime Verification | Local `curl` / HTTP test runs | Verifies live HTTP status codes, JSON responses, and error handling | Hamza Bashar |

## 3. Logging Plan

| Event / Concern | Component | Level | Key Context | Sensitive-Data Consideration |
|---|---|---|---|---|
| Request submitted (`SUBMISSION_CREATED`) | Presentation / Logic | `INFO` | Timestamp (UTC), `role`, `requestId`, category, initial status (`OPEN`) | Synthetic data only; no real student PII |
| Validation failure (`SUBMISSION_REJECTED`) | Presentation / Logic | `WARN` | Timestamp, `role`, missing/whitespace field names | Do not log raw unvalidated payloads if sensitive |
| Unauthorized role (`ROLE_DENIED`) | `RoleCheckService` | `WARN` | Timestamp, attempted `role`, attempted action, `requestId` | No credentials or tokens exist in Cycle 1/2 |
| Staff list viewed (`REQUEST_LIST_VIEWED`) | Presentation | `INFO` | Timestamp, `role=STAFF`, count of returned requests | Summary count only; avoids dumping full descriptions |
| Status updated (`STATUS_UPDATED`) | Logic | `INFO` | Timestamp, `role=STAFF`, `requestId`, old status -> new status | Operational state metadata only |
| Invalid transition (`TRANSITION_REJECTED`) | Logic | `WARN` | Timestamp, `role=STAFF`, `requestId`, attempted transition | Confirms forward-only rule enforcement |

## 4. Correlation, Failure Visibility, and Operating Assumptions
- **Correlation:** Runtime events are correlated using UTC ISO-8601 timestamps, `requestId` (e.g., `REQ-1001`), and `role` (`STUDENT` / `STAFF`).
- **Failure Visibility:** Invalid inputs, unauthorized roles, and illegal transitions return explicit HTTP `4xx` responses and emit `WARN` logs rather than failing silently. Storage failures return HTTP `500`.
- **Operating & Cost Assumptions:** The application runs locally on JDK 17 with zero cloud hosting costs and in-memory storage (`InMemorySupportRequestRepository`). Memory footprint is bounded by local demo/test volume and resets on process restart.
- **Alerting:** Automated alerting and external dashboards (e.g., Prometheus/Grafana) are **not implemented** for this local course system. Detection relies on CI checks, local log inspection, and manual test runs.

## 5. Verification & Known Gaps
- **Verification:** Logging and runtime rejection visibility were verified during the Cycle 1/2 runtime diagnostics run (7 events: 4 successes, 3 rejections/denials) and the 12-step HTTP verification recorded in `/docs/testing/final-test-evidence.md`.
- **Known Gaps:**
  - Logs are written to local console/output and are not shipped to a centralized persistent log store.
  - No dedicated `/health` HTTP endpoint or automated latency metrics exist yet; these are deferred to future production hardening.