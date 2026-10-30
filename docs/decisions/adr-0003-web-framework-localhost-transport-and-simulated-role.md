# ADR-0003: Localhost HTTP/JSON Transport, JDK HttpServer Web Layer, and Simulated Role Passing

- **Status:** Accepted (Cycle 1 Construction — Assignment 4)
- **Date:** 2026-10-29
- **Decision Makers / Owners:** Sydel Ugwu (Primary Owner, Architecture & Development Lead) · Hamza Bashar (Backup Owner / Reviewer)
- **Related Issue:** [Issue #29 — A4: Task-111](https://github.com/JoanelisRM/comp330-f26-team-05/issues/29)
- **Related Evidence:** `ADR-001` (open item closure), `API-001..005`, `REQ-001..006`, `Q-003`, `R-007`, `docs/architecture/component-responsibilities.md`, `docs/architecture/api-contracts.md`

## 1. Context and Problem Statement

In Assignment 3 (`ADR-001` and `docs/architecture/component-responsibilities.md`), Team 5 established a three-layer Java/Maven architecture (`Presentation Layer`, `Logic Layer`, `Data Layer`) plus `Logging` for the CampusConnect Cycle 1 vertical slice (student submits a support request, staff view submitted requests, and staff update request status along `Open -> In Progress -> Resolved`).

Three implementation-level architecture details remained marked as proposed or open items entering Assignment 4 (`TASK-111` / Issue #29):
1. **Web Framework Choice:** Which HTTP server or web framework should host the Presentation/API endpoints without creating unnecessary setup friction (`R-007`) or supply-chain risk?
2. **Localhost Transport Contract (`API-001` to `API-003`):** How are commands, results, and structured validation/authorization errors transported over `localhost`?
3. **Simulated Role Passing (`API-005` / `REQ-006`):** Since Cycle 1 explicitly excludes real Loyola SSO/authentication (`SCP-004`, `ADR-002`, `ASM-001`), how does a caller pass the simulated actor role (`STUDENT` or `STAFF`) across the Presentation boundary so the Logic Layer's central role check (`API-005`) can enforce role permissions deterministically?

## 2. Decision Drivers

- **Zero External Runtime Dependency Overhead (`R-007` & Supply-Chain Governance):** All five team members must be able to build and test the project immediately using standard JDK 17+ and Maven without external container setup or heavy framework configuration (and so the planned CI workflow in `TASK-107` can run `mvn -B test` cleanly once configured).
- **Strict Layer Boundaries (`component-responsibilities.md`):** The Presentation Layer must translate HTTP requests into Logic Layer service calls (`API-001..003`) and never call the Data Layer (`API-004`) directly.
- **Explicit, Auditable Simulated Role Enforcement (`REQ-006`, `API-005`):** Role checks must happen centrally in the Logic Layer before any workflow action executes, and missing or invalid roles must be rejected and logged.
- **Testability (`TASK-106`, `TASK-107`):** Both direct Java unit tests and localhost HTTP handler tests must run deterministically without external network dependencies.

## 3. Alternatives Considered

### Option A: JDK Built-In `com.sun.net.httpserver.HttpServer` + Localhost HTTP/JSON + `X-Simulated-Role` Header (Selected)
- **Description:** Use the standard JDK `jdk.httpserver` module (`com.sun.net.httpserver.HttpServer`) bound exclusively to `127.0.0.1:8080` (`localhost`). Expose RESTful JSON endpoints for `API-001` (`POST /api/requests`), `API-002` (`GET /api/requests`), and `API-003` (`PATCH /api/requests/{id}/status`). Pass the simulated role exclusively via the `X-Simulated-Role` HTTP request header (`STUDENT` or `STAFF`) and forward it as an explicit `Role` parameter into the Logic Layer (`API-005`).
- **Pros:**
  - Zero third-party runtime web framework dependencies to audit, license-check, or patch.
  - Starts in milliseconds during local runs and integration checks.
  - Keeps Presentation-to-Logic separation clear: HTTP handler parses JSON and `X-Simulated-Role`, invokes `SupportRequestService`, and maps `ServiceResult` to HTTP status codes (`201 Created`, `200 OK`, `400 Bad Request`, `403 Forbidden`, `404 Not Found`, `409 Conflict`).
- **Cons:**
  - Requires lightweight helper methods for JSON serialization/parsing and path parameter extraction (implemented in standard Java for our fixed 3-endpoint schema).

### Option B: Spring Boot (`spring-boot-starter-web`)
- **Description:** Use Spring Boot's embedded Tomcat server, `@RestController`, and Jackson databind.
- **Pros:** Industry-standard enterprise framework with automatic JSON binding.
- **Cons:** Pulls in dozens of transitive dependencies (increasing supply-chain review surface), slows down Maven cold builds, and risks blurring Presentation/Logic boundaries via annotation magic for a small 3-endpoint vertical slice (`R-007`).

### Option C: Javalin (`io.javalin:javalin`)
- **Description:** Lightweight Jetty-based micro-framework.
- **Pros:** Concise routing syntax.
- **Cons:** Introduces external Jetty/Kotlin transitive dependencies that are unnecessary when JDK `HttpServer` satisfies all Cycle 1 localhost requirements natively.

## 4. Decision Outcome

We select **Option A** (incorporating review feedback `CRF-001` through `CRF-005` from PR #56):

1. **Web Framework:** Standard JDK 17 `com.sun.net.httpserver.HttpServer` bound strictly to `127.0.0.1` (default port `8080`, configurable via argument `0` for ephemeral test ports).
2. **Localhost Transport Contract (`API-001..003`, aligned with `docs/architecture/api-contracts.md`):**
   - **Content-Type:** `application/json; charset=utf-8`
   - **`API-001` Submit Support Request (`REQ-001`, `REQ-004`):**
     - `POST http://localhost:8080/api/requests`
     - Required Header: `X-Simulated-Role: STUDENT` (`STAFF` is denied per `API-005` and `C-Q3` resolution)
     - Request JSON: `{"category": "Academic Advising", "title": "Schedule conflict", "description": "Need help resolving COMP 330 lab overlap"}`
     - Success Response (`201 Created`): `{"status": "SUCCESS", "request": {"requestId": "REQ-1001", "category": "...", "title": "...", "description": "...", "requestStatus": "OPEN", "submittedAt": "2026-10-28T...", "updatedAt": "2026-10-28T...", "synthetic": true}}`
     - Validation Failure (`400 Bad Request`): `{"status": "VALIDATION_ERROR", "missingFields": ["title", "description"], "message": "Required fields missing or whitespace-only: title, description"}`
   - **`API-002` Staff Request List (`REQ-002`):**
     - `GET http://localhost:8080/api/requests`
     - Required Header: `X-Simulated-Role: STAFF` (`STUDENT` is denied per `API-005`)
     - Success Response (`200 OK`): `{"status": "SUCCESS", "count": 1, "requests": [...]}`
   - **`API-003` Staff Status Update (`REQ-003`):**
     - `PATCH http://localhost:8080/api/requests/{requestId}/status` (`CRF-001`: strictly `PATCH` per `api-contracts.md`; `POST` is not accepted on this endpoint)
     - Required Header: `X-Simulated-Role: STAFF` (`STUDENT` is denied per `API-005`)
     - Request JSON: `{"newStatus": "IN_PROGRESS"}`
     - Success Response (`200 OK`): `{"status": "SUCCESS", "request": {...}}`
     - Malformed / Unknown Status Value Response (`400 Bad Request`, `CRF-002`): `{"status": "VALIDATION_ERROR", "missingFields": ["newStatus"], "message": "..."}` when `newStatus` is missing, blank, or not one of `OPEN`, `IN_PROGRESS`, `RESOLVED`.
     - Invalid Transition Response (`409 Conflict`, `CRF-002`): `{"status": "INVALID_TRANSITION", "message": "Invalid status transition from RESOLVED to OPEN (allowed: OPEN -> IN_PROGRESS -> RESOLVED)"}` when the requested state transition violates `REQ-003`.
3. **Simulated Role Passing & Enforcement (`API-005` / `REQ-006`, `CRF-003` & `CRF-004`):**
   - The caller supplies `X-Simulated-Role: STUDENT` or `X-Simulated-Role: STAFF` (case-insensitive) via the HTTP request header only. No `?role=` URL query parameter fallback is supported (`CRF-003`), preventing simulated roles from leaking into URLs, browser history, or access logs.
   - The Presentation Layer extracts the `X-Simulated-Role` header and passes it to the Logic Layer (`SupportRequestService`), which immediately delegates to `RoleCheckService.requireRole(rawRole, expectedRole, actionName)` (`API-005`).
   - Role permissions are strictly partitioned (`CRF-004`, resolving `C-Q3` in `api-contracts.md`):
     - `API-001` (`POST /api/requests`): **Only `STUDENT` is allowed** (`STAFF` is denied with `403 Forbidden`).
     - `API-002` (`GET /api/requests`) and `API-003` (`PATCH /api/requests/{id}/status`): **Only `STAFF` is allowed** (`STUDENT` is denied with `403 Forbidden`).
   - If the `X-Simulated-Role` header is missing, blank, unrecognized, or unauthorized for the operation, `API-005` rejects the call before any Data Layer access occurs, records an audit event via `AuditLogger`, and returns HTTP `403 Forbidden` with `{"status": "ROLE_DENIED", "message": "..."}`.

## 5. Consequences and Risks

- **Positive:** Resolves the open transport item from `ADR-001` and `architecture-diagram.png`; keeps runtime dependencies at zero (`JUnit Jupiter 5.10.2` is test-scoped only); aligns HTTP methods and status codes (`400` vs `409`) with `api-contracts.md`.
- **Negative / Limitations:** Simulated roles passed via `X-Simulated-Role` are unverified demonstration inputs and provide **no real authentication or security**. This limitation is explicitly disclosed in `docs/release/known-limitations.md` and `docs/planning/risk-register.md`.
- **Traceability (`CRF-005`):** Implemented in `TASK-101` (Issue #30) / `TASK-103` (Issue #33) and to be verified in `TASK-106` (Issue #36), `TASK-107` (Issue #47 CI workflow setup), and Issue #49 (`/docs/observability/runtime-logging-evidence.md`).
