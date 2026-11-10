# Cycle 2 Evidence Handoff — Defects, Residual Risks, Limitations, and Postmortem Learning

- **Deliverable:** Cycle 2 Evidence Handoff (COMP 330 — Assignment 5)
- **Authoritative GitHub Location:** `/docs/release/cycle2-handoff.md`
- **Primary Owner:** Joanelis Rivera Matos (`@JoanelisRM`, Team Lead)
- **Backup Owner / Reviewer:** Sydel Ugwu (`@Sugwu123`, Architecture & Development Lead)
- **Related Issue:** [Issue #91 — A5: Cycle 2 Evidence Handoff](https://github.com/JoanelisRM/comp330-f26-team-05/issues/91)
- **Release Baseline:** `a5-cycle1-release`
- **Last Updated:** 2026-11-09

## 1. Purpose

This document captures the technical limitations, remediated and deferred defects, residual operational risks, and workflow postmortem lessons from Cycle 1 (`a5-cycle1-release`) so Team 05 can plan Cycle 2 (Assignment 6 — Operational Maturity & Evolution) from actual repository evidence rather than an unfiltered wishlist.

## 2. Cycle 1 Limitations & Technical Debt Handed Off to Cycle 2

| ID | Limitation / Residual Risk | Cycle 1 Evidence & Impact | Cycle 1 Mitigation / Workaround | Recommended Cycle 2 Action | Proposed Cycle 2 Owner |
|---|---|---|---|---|---|
| **LIM-001 (`Q-003`)** | **Volatile In-Memory Storage (`InMemorySupportRequestRepository`)** | All synthetic support requests (`REQ-005`) are stored in a JVM `LinkedHashMap` and reset whenever the application process restarts. | Disclosed in `/docs/release/known-limitations.md` and demo script; acceptable for single-session local evaluation (`ADR-001`). | Implement a durable repository adapter (e.g., SQLite or JSON/file-backed store) behind `SupportRequestRepository` (`API-004`) with restart-survival integration tests. | Sydel Ugwu / Hamza Bashar |
| **LIM-002 (`ADR-0003`)** | **Unverified Simulated Role Header (`X-Simulated-Role`)** | Caller passes `X-Simulated-Role: STUDENT` or `STAFF` in HTTP headers (`REQ-006`). Provides role-boundary verification (`API-005`) but zero cryptographic authentication. | Restricted server binding to `127.0.0.1` (`localhost`), removed `?role=` URL fallback (`CRF-003`), and enforced synthetic-only data (`REQ-004`). | Introduce signed session/token validation or local role-credential verification while preserving `RoleCheckService` (`API-005`). | Sydel Ugwu / Nova Kurul |
| **LIM-003 (`Q-002`)** | **Forward-Only Status Rule (`OPEN -> IN_PROGRESS -> RESOLVED`) & No Request Edit/Cancel** | Staff cannot reopen a `RESOLVED` request or move `IN_PROGRESS -> OPEN`, and students cannot edit or cancel a request after submission (`ADR-002`). | Rejected invalid transitions deterministically with `409 Conflict` (`INVALID_TRANSITION`) and logged via `AuditLogger`. | Evaluate controlled request reopening (`RESOLVED -> IN_PROGRESS`) with a required staff audit comment in Cycle 2. | Hamza Bashar / Khaja Shabbir Ahmed |
| **LIM-004 (`TASK-108`)** | **In-Memory / Console Diagnostic Logging Only** | `AuditLogger` writes structured events to `stdout` and an in-memory list (`Issue #49`). Logs are not rotated to persistent disk files or metrics endpoints. | Captured verification logs in `/docs/testing/runtime-diagnostics-log.txt` and verified log entries in unit/acceptance tests. | Add rolling file output and a `/api/health` or `/api/metrics` operational diagnostics endpoint for Assignment 6. | Nova Kurul / Sydel Ugwu |

## 3. Defects and Review Findings Summary Entering Cycle 2

| Finding / Defect ID | Description | Disposition in `a5-cycle1-release` | Verification Evidence |
|---|---|---|---|
| **CRF-001..005** | `ADR-0003` and `CampusConnectApp` method (`PATCH` only), status code (`400` vs `409`), and header-only `X-Simulated-Role` alignment | **Fixed in A5** | `ADR-0003`, `CampusConnectApp.java`, `Cycle1AcceptanceTest.java` |
| **CRF-006** | `InMemorySupportRequestRepository.save(...)` could overwrite an existing request with a duplicate `requestId` | **Fixed in A5** | `InMemorySupportRequestRepository.java` (`putIfAbsent` check), `InMemorySupportRequestRepositoryTest` |
| **CRF-007** | `clear()` exposed on production `SupportRequestRepository` (`API-004`) interface | **Fixed in A5** | Removed from `SupportRequestRepository.java`; tests instantiate a fresh repository in `@BeforeEach` |
| **CRF-008** | `nextRequestId()` lives on `SupportRequestRepository` (`Data Layer`) while `component-responsibilities.md` attributed ID generation to `Logic Layer` | **Mitigated / Documented** | `SupportRequestService` orchestrates ID assignment via `repository.nextRequestId()`; Cycle 2 will evaluate UUID/sequence generation strategy alongside durable storage |
| **CRF-010 & CRF-014** | Missing edge-case tests (`findById` null/blank/unknown, empty staff queue `AC-REQ-002-02`, and full end-to-end `submit -> list -> update -> list-again` flow) | **Fixed in A5** | `InMemorySupportRequestRepositoryTest.java`, `Cycle1AcceptanceTest.java` |

## 4. Engineering Process & Postmortem Questions for Cycle 2

1. **How will we prevent stacked-PR merge bottlenecks in Cycle 2?**
   - *Lesson from A4:* Opening 11 stacked branches simultaneously created `docs/ai/ai-use-log.md` merge conflicts and caused some changes to merge before independent review completed.
   - *Cycle 2 Rule:* Merge foundational interface changes first, branch each feature directly from `main`, and require both green GitHub Actions CI and peer review approval before merging.
2. **How will we keep acceptance evidence synchronized with code changes?**
   - *Lesson from A4:* Individual unit tests passed, but end-to-end workflow evidence (`submit -> list -> update -> list-again`) was initially split across separate test files.
   - *Cycle 2 Rule:* Maintain `Cycle1AcceptanceTest.java` as a mandatory regression gate in CI for every Cycle 2 pull request.
