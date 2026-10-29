# ADR-0003: Localhost HTTP/JSON Transport, JDK HttpServer Web Layer, and Simulated Role Passing

- **Status:** Accepted (Cycle 1 Construction — Assignment 4)
- **Date:** 2026-10-28
- **Decision Makers / Owners:** Sydel Ugwu (Primary Owner, Architecture & Development Lead) · Hamza Bashar (Backup Owner / Reviewer)
- **Related Issue:** [Issue #29 — A4: Task-111](https://github.com/JoanelisRM/comp330-f26-team-05/issues/29)
- **Related Evidence:** `ADR-001` (open item closure), `API-001..005`, `REQ-001..006`, `Q-003`, `R-007`, `docs/architecture/component-responsibilities.md`

## 1. Context and Problem Statement

In Assignment 3 (`ADR-001` and `docs/architecture/component-responsibilities.md`), Team 5 established a three-layer Java/Maven architecture (`Presentation Layer`, `Logic Layer`, `Data Layer`) plus `Logging` for the CampusConnect Cycle 1 vertical slice (student submits a support request, staff view submitted requests, and staff update request status along `Open -> In Progress -> Resolved`).

Three implementation-level architecture details remained marked as proposed or open items entering Assignment 4 (`TASK-111` / Issue #29):
1. **Web Framework Choice:** Which HTTP server or web framework should host the Presentation/API endpoints without creating unnecessary setup friction (`R-007`) or supply-chain risk?
2. **Localhost Transport Contract (`API-001` to `API-003`):** How are commands, results, and structured validation/authorization errors transported over `localhost`?
3. **Simulated Role Passing (`API-005` / `REQ-006`):** Since Cycle 1 explicitly excludes real Loyola SSO/authentication (`SCP-004`, `ADR-002`, `ASM-001`), how does a caller pass the simulated actor role (`STUDENT` or `STAFF`) across the Presentation boundary so the Logic Layer's central role check (`API-005`) can enforce role permissions deterministically?

## 2. Decision Drivers

- **Zero External Runtime Dependency Overhead (`R-007` & Supply-Chain Governance):** All five team members and the GitHub Actions CI runner (`/.github/workflows/ci.yml`) must be able to build and test the project immediately using standard JDK 17+ and Maven without external container setup or heavy framework configuration.
- **Strict Layer Boundaries (`component-responsibilities.md`):** The Presentation Layer must translate HTTP requests into Logic Layer service calls (`API-001..003`) and never call the Data Layer (`API-004`) directly.
- **Explicit, Auditable Simulated Role Enforcement (`REQ-006`, `API-005`):** Role checks must happen centrally in the Logic Layer before any workflow action executes, and missing or invalid roles must be rejected and logged.
- **Testability (`TASK-106`, `TASK-107`):** Both direct Java unit tests and localhost HTTP handler tests must run deterministically in CI without external network dependencies.

## 3. Alternatives Considered

### Option A: JDK Built-In `com.sun.net.httpserver.HttpServer` + Localhost HTTP/JSON + `X-Simulated-Role` Header (Selected)
- **Description:** Use the standard JDK `jdk.httpserver` module (`com.sun.net.httpserver.HttpServer`) bound exclusively to `127.0.0.1:8080` (`localhost`). Expose RESTful JSON endpoints for `API-001` (`POST /api/requests`), `API-002` (`GET /api/requests`), and `API-003` (`PATCH /api/requests/{id}/status`). Pass the simulated role via the `X-Simulated-Role` HTTP request header (`STUDENT` or `STAFF`, with query parameter `?role=` allowed as a local browser/demo fallback) and forward it as an explicit `Role` parameter into the Logic Layer (`API-005`).
- **Pros:**
  - Zero third-party runtime web framework dependencies to audit, license-check, or patch.
  - Starts in milliseconds during local runs and integration checks.
  - Keeps Presentation-to-Logic separation crystal clear: HTTP handler parses JSON and `X-Simulated-Role`, invokes `SupportRequestService`, and maps `ServiceResult` to HTTP status codes (`201 Created`, `200 OK`, `400 Bad Request`, `403 Forbidden`, `404 Not Found`, `409 Conflict`).
- **Cons:**
  - Requires lightweight helper methods for JSON serialization/parsing and path parameter extraction (implemented cleanly in standard Java for our fixed 3-endpoint schema).

### Option B: Spring Boot (`spring-boot-starter-web`)
- **Description:** Use Spring Boot's embedded Tomcat server, `@RestController`, and Jackson databind.
- **Pros:** Industry-standard enterprise framework with automatic JSON binding.
- **Cons:** Pulls in dozens of transitive dependencies (increasing supply-chain review surface), slows down Maven cold builds in CI, and risks blurring Presentation/Logic boundaries via annotation magic for a small 3-endpoint vertical slice (`R-007`).

### Option C: Javalin (`io.javalin:javalin`)
- **Description:** Lightweight Jetty-based micro-framework.
- **Pros:** Concise routing syntax.
- **Cons:** Introduces external Jetty/Kotlin transitive dependencies that are unnecessary when JDK `HttpServer` satisfies all Cycle 1 localhost requirements natively.

## 4. Decision Outcome

We select **Option A**:

1. **Web Framework:** Standard JDK 17 `com.sun.net.httpserver.HttpServer` bound strictly to `127.0.0.1` (default port `8080`, configurable via system property/argument `0` for ephemeral test ports).
2. **Localhost Transport Contract (`API-001..003`):**
   - **Content-Type:** `application/json; charset=utf-8`
   - **`API-001` Submit Support Request (`REQ-001`, `REQ-004`):**
     - `POST http://localhost:8080/api/requests`
     - Required Header: `X-Simulated-Role: STUDENT`
     - Request JSON: `{"category": "Academic Advising", "title": "Schedule conflict", "description": "Need help resolving COMP 330 lab overlap"}`
     - Success Response (`201 Created`): `{"status": "SUCCESS", "request": {"requestId": "REQ-1001", "category": "...", "title": "...", "description": "...", "requestStatus": "OPEN", "submittedAt": "2026-10-28T...", "updatedAt": "2026-10-28T...", "synthetic": true}}`
     - Validation Failure (`400 Bad Request`): `{"status": "VALIDATION_ERROR", "missingFields": ["title", "description"], "message": "Required fields missing or whitespace-only: title, description"}`
   - **`API-002` Staff Request List (`REQ-002`):**
     - `GET http://localhost:8080/api/requests`
     - Required Header: `X-Simulated-Role: STAFF`
     - Success Response (`200 OK`): `{"status": "SUCCESS", "requests": [...]}`
   - **`API-003` Staff Status Update (`REQ-003`):**
     - `PATCH http://localhost:8080/api/requests/{requestId}/status` (also accepts `POST` for simple HTML form/curl demos)
     - Required Header: `X-Simulated-Role: STAFF`
     - Request JSON: `{"newStatus": "IN_PROGRESS"}`
     - Success Response (`200 OK`): `{"status": "SUCCESS", "request": {...}}`
     - Invalid Transition Response (`409 Conflict` / `400 Bad Request`): `{"status": "INVALID_TRANSITION", "message": "Invalid status transition from RESOLVED to OPEN. Allowed: OPEN -> IN_PROGRESS -> RESOLVED."}`
3. **Simulated Role Passing & Enforcement (`API-005` / `REQ-006`):**
   - The caller supplies `X-Simulated-Role: STUDENT` or `X-Simulated-Role: STAFF` (case-insensitive; falls back to query parameter `?role=` only for local browser inspection).
   - The Presentation Layer extracts the raw role string and passes it to the Logic Layer (`SupportRequestService`), which immediately delegates to `RoleCheckService.requireRole(rawRole, expectedRole)` (`API-005`).
   - If the header is missing, blank, unrecognized, or unauthorized for the operation (e.g., `STUDENT` calling `API-002` or `API-003`, or `STAFF` calling student-only `API-001` if restricted), `API-005` rejects the call before any Data Layer access occurs, records an audit event via `AuditLogger`, and returns HTTP `403 Forbidden` with `{"status": "ROLE_DENIED", "message": "..."}`.

## 5. Consequences and Risks

- **Positive:** Resolves the open transport item from `ADR-001` and `architecture-diagram.png`; keeps runtime dependencies at zero (`JUnit Jupiter 5.10.2` is test-scoped only); makes `API-001..005` directly testable in unit and integration tests.
- **Negative / Limitations:** Simulated roles passed via `X-Simulated-Role` are unverified demonstration inputs and provide **no real authentication or security**. This limitation is explicitly disclosed in `docs/release/known-limitations.md` and `docs/planning/risk-register.md`.
- **Traceability:** Implemented in `TASK-101` (Issue #30) and verified in `TASK-106` (Issue #36) and Issue #49 (`/docs/observability/runtime-logging-evidence.md`).
