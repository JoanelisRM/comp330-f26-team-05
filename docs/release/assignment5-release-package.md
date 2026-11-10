# Assignment 5 Release Evidence README — Cycle 1 Release, Final Evidence Index, and Cycle 2 Handoff

- **Deliverable:** Assignment 5 Release Evidence README (COMP 330 — Assignment 5)
- **Authoritative GitHub Location:** `/docs/release/assignment5-release-package.md`
- **Primary Owner:** Sydel Ugwu (`@Sugwu123`, Architecture & Development Lead; Backup Team Lead)
- **Backup Owner / Reviewer:** Nova Kurul (`@selkianova`, Operations & Evidence Lead)
- **Related Issues:** [Issue #79 — A5: Assignment 5 Release Evidence README](https://github.com/JoanelisRM/comp330-f26-team-05/issues/79), [Issue #91 — A5: Cycle 2 Evidence Handoff](https://github.com/JoanelisRM/comp330-f26-team-05/issues/91)
- **Team Members:** Joanelis Rivera Matos (Team Lead), Khaja Shabbir Ahmed, Sydel Ugwu, Hamza Bashar, Nova Kurul
- **Repository Clone URL:** `https://github.com/JoanelisRM/comp330-f26-team-05.git`
- **Previous Baseline Tag (A4):** `a4-construction-integration`
- **Cycle 1 Release Baseline Tag (A5):** `a5-cycle1-release`
- **Last Updated:** 2026-11-09

## 1. Purpose and Release Readiness Summary

This document serves as the authoritative index for Team 05's (`CampusConnect`) Cycle 1 release package at the Assignment 5 phase gate (`a5-cycle1-release`). It connects the Cycle 1 release presentation and live demonstration to inspectable repository evidence across requirements, architecture, source code, CI/CD checks, automated acceptance and unit tests, defect/review remediation from Assignment 4, known limitations, AI-use governance, and the Cycle 2 engineering handoff.

### What Shipped in Cycle 1 (`a5-cycle1-release`)
1. **Student Support Request Submission (`REQ-001`, `REQ-004`, `API-001`):** Students submit synthetic support requests (`category`, `title`, `description`) via `SupportRequestService.submitRequest` and `POST /api/requests`. Empty or whitespace-only fields are rejected with an explicit `missingFields` list (`AC-REQ-001-02`).
2. **Staff Request Queue View (`REQ-002`, `API-002`):** Staff retrieve all submitted synthetic requests in chronological order via `SupportRequestService.listRequests` and `GET /api/requests`, including empty-queue handling (`AC-REQ-002-02`).
3. **Staff Forward-Only Status Progression (`REQ-003`, `API-003`, `Q-002`):** Staff transition requests strictly along `OPEN -> IN_PROGRESS -> RESOLVED` via `PATCH /api/requests/{id}/status`. Skipped states (`OPEN -> RESOLVED`), backward transitions (`IN_PROGRESS -> OPEN`), and reopening (`RESOLVED -> OPEN`) are rejected with `409 Conflict` (`INVALID_TRANSITION`).
4. **Central Simulated Role Enforcement (`REQ-006`, `API-005`, `ADR-0003`):** `RoleCheckService` enforces `X-Simulated-Role` (`STUDENT` for `API-001`; `STAFF` for `API-002` and `API-003`) before any workflow or storage operation executes, rejecting missing or unauthorized roles with `403 Forbidden` (`ROLE_DENIED`).
5. **Authoritative In-Memory Persistence (`REQ-005`, `API-004`, `Q-003`):** `SupportRequestRepository` and `InMemorySupportRequestRepository` store defensive copies in insertion order, prevent duplicate-ID overwrites (`CRF-006`), and fail explicitly on unknown IDs (`404 Not Found`).
6. **Structured Runtime & Audit Logging (`TASK-108`, `Q-007`):** `AuditLogger` records structured diagnostic events (`SUBMISSION_CREATED`, `SUBMISSION_REJECTED`, `REQUEST_LIST_VIEWED`, `STATUS_UPDATED`, `TRANSITION_REJECTED`, `ROLE_DENIED`) with zero PII or credentials (`REQ-004`).

## 2. Authoritative Assignment 5 Release Evidence Index

| Deliverable | Purpose | Authoritative GitHub Location | Primary / Backup Owner | Related Issue / PR |
|---|---|---|---|---|
| **Assignment 5 Release Evidence README** | Master index of final Cycle 1 release evidence and links to all required artifacts | `/docs/release/assignment5-release-package.md` | Sydel Ugwu / Nova Kurul | [Issue #79](https://github.com/JoanelisRM/comp330-f26-team-05/issues/79) |
| **Final Release Notes** | What shipped, what did not ship, known limitations, mitigations, residual risks, and why the release is acceptable for Cycle 1 | `/docs/release/release-notes.md` | Joanelis Rivera Matos / Sydel Ugwu | [Issue #80](https://github.com/JoanelisRM/comp330-f26-team-05/issues/80), [PR #92](https://github.com/JoanelisRM/comp330-f26-team-05/pull/92) |
| **Cycle 1 Release Baseline Evidence** | Identifies the exact repository state (`a5-cycle1-release`) associated with the presented Cycle 1 release | Git tag `a5-cycle1-release`, `/README.md`, `/docs/release/release-notes.md` | Joanelis Rivera Matos / Sydel Ugwu | [PR #92](https://github.com/JoanelisRM/comp330-f26-team-05/pull/92) |
| **Changed-File Summary Since A4** | Final repository changes after `a4-construction-integration`, including tests, defects, risks, release notes, AI-use updates, and presentation artifacts | `/docs/release/changed-files-since-a4.md` | Sydel Ugwu / Nova Kurul | [Issue #82](https://github.com/JoanelisRM/comp330-f26-team-05/issues/82) |
| **Final Testing Evidence** | Final unit, HTTP integration, and end-to-end `submit -> list -> update -> list-again` acceptance test evidence completed through A5 | `/docs/testing/final-test-evidence.md`, `/docs/testing/test-strategy.md`, `/src/test/java/edu/luc/comp330/campusconnect/` | Hamza Bashar, Khaja Shabbir Ahmed / Sydel Ugwu | [Issue #78](https://github.com/JoanelisRM/comp330-f26-team-05/issues/78) |
| **CI/CD Final Evidence** | Automated Maven build/test workflow (`ci.yml`) status, corrected checks, and links to passing GitHub Actions runs | `/.github/workflows/ci.yml`, `/docs/testing/ci-evidence.md` | Nova Kurul / Hamza Bashar | GitHub Actions tab |
| **Runtime / Operational Evidence** | Structured runtime audit logs (`AuditLogger`), error-handling diagnostics, and troubleshooting guide | `/docs/observability/runtime-logging-evidence.md`, `/docs/testing/runtime-diagnostics-log.txt` | Sydel Ugwu / Nova Kurul | [Issue #49](https://github.com/JoanelisRM/comp330-f26-team-05/issues/49) |
| **Defect Review Summary & A4 Remediation** | Severity, disposition, owner, and verification of Cycle 1 defects and A4 code-review findings (`CRF-001` through `CRF-014`) | `/docs/quality/defect-review.md`, `/docs/release/defect-summary.md` | Hamza Bashar, Khaja Shabbir Ahmed / Sydel Ugwu | A4/A5 PR reviews |
| **Known Limitations & Residual Risks** | Specific Cycle 1 limitations (in-memory storage reset on restart, simulated roles, forward-only status rule, no edit/cancel), severity, mitigations, and Cycle 2 actions | `/docs/release/known-limitations.md`, `/docs/planning/risk-register.md` | Khaja Shabbir Ahmed, Nova Kurul | `/docs/release/known-limitations.md` |
| **AI-Use Log Final Update** | Complete disclosure of AI assistance used across Cycle 1 construction, testing, reviews, and release prep with human verification | `/docs/ai/ai-use-log.md` | Nova Kurul / All Team Members | `/docs/ai/ai-use-log.md` |
| **Traceability Update** | End-to-end links from `REQ-001..006` -> GitHub Issues -> Branches/Commits -> PRs -> CI/CD & JUnit Tests -> Demo Steps -> Release Notes | `/docs/planning/traceability.md`, `/docs/release/traceability-summary.md` | Khaja Shabbir Ahmed, Joanelis Rivera Matos / Sydel Ugwu | `/docs/planning/traceability.md` |
| **Presentation Materials Index & Demo Script** | Identifies final presentation deck, controlled demo script (main path + boundary/failure path), backup materials, and evidence links | `/docs/release/presentation-index.md`, `/docs/release/demo-script.md` | Joanelis Rivera Matos / Sydel Ugwu | [Issue #90](https://github.com/JoanelisRM/comp330-f26-team-05/issues/90), [PR #92](https://github.com/JoanelisRM/comp330-f26-team-05/pull/92) |
| **Cycle 2 Evidence Handoff** | Identifies Cycle 1 defects, residual risks, limitations, weak evidence remediations, and postmortem questions informing Cycle 2 maturity planning | `/docs/release/cycle2-handoff.md` and Section 4 below | Joanelis Rivera Matos / Sydel Ugwu | [Issue #91](https://github.com/JoanelisRM/comp330-f26-team-05/issues/91) |

## 3. Remediation of Assignment 4 Professional Gate Conditions

In the Assignment 4 Engineering Assessment (`a4-construction-integration`), Team 05 received **Proceed to A5 (`PROCEED WITH CONDITIONS`)** with four remediation priorities. Below is how each condition was resolved for `a5-cycle1-release`:

| A4 Professional Condition | Remediation Action Taken in Assignment 5 | Authoritative Evidence in Repository |
|---|---|---|
| **1. Independent remediation review & closure of findings (`CRF-001..014`)** | Addressed Hamza's review findings on `PR #58` and `PR #62`: `InMemorySupportRequestRepository.save` now enforces duplicate-ID protection via `putIfAbsent` (`CRF-006`), `clear()` was removed from the production `SupportRequestRepository` interface (`CRF-007`), and missing repository/service edge cases were added (`CRF-010`, `CRF-014`). | `SupportRequestRepository.java`, `InMemorySupportRequestRepository.java`, `InMemorySupportRequestRepositoryTest.java`, `Cycle1AcceptanceTest.java` |
| **2. Final-baseline `submit -> list -> update -> list-again` acceptance test** | Added `Cycle1AcceptanceTest.java` verifying the complete integrated workflow on the frozen baseline: (a) initial empty staff list (`AC-REQ-002-02`), (b) student submits valid request (`REQ-001`), (c) staff lists queue and sees `OPEN` request (`REQ-002`), (d) staff updates `OPEN -> IN_PROGRESS -> RESOLVED` (`REQ-003`), (e) staff lists again to verify retained `RESOLVED` state (`REQ-005`), and (f) authorization/validation boundary rejections (`REQ-006`). | `src/test/java/edu/luc/comp330/campusconnect/Cycle1AcceptanceTest.java` |
| **3. Reconcile API methods, role transport, requirement scope & ADR status** | Reconciled `CampusConnectApp.java`, `ADR-0003`, and `api-contracts.md`: `API-003` strictly enforces `PATCH /api/requests/{id}/status` (`CRF-001`), separates `400 Bad Request` vs `409 Conflict` (`CRF-002`), passes roles exclusively via `X-Simulated-Role` header without `?role=` URL fallback (`CRF-003`), denies `STAFF` on student-only `API-001` (`CRF-004`), and documents student edit/cancel and request reopening (`Q-002`) as out-of-scope for Cycle 1 (`ADR-002`). | `CampusConnectApp.java`, `/docs/decisions/adr-0003-web-framework-localhost-transport-and-simulated-role.md`, `/docs/release/known-limitations.md` |
| **4. Enforce approval and passing CI before material merges** | All Assignment 5 changes are submitted as focused, buildable Pull Requests that run GitHub Actions CI (`/.github/workflows/ci.yml`) and require reviewer verification before merge into `main`. | GitHub Pull Requests & Actions history |

## 4. Cycle 2 Evidence Handoff Summary ([Issue #91](https://github.com/JoanelisRM/comp330-f26-team-05/issues/91))

To support evidence-based transition into Cycle 2 (Assignment 6 — Operational Maturity & Evolution), the following items are handed off from Cycle 1:

1. **Storage & Restart Persistence (`Q-003`):** Cycle 1 uses `InMemorySupportRequestRepository`, which satisfies `REQ-005` while the JVM process runs but loses state on restart. Cycle 2 must evaluate durable file/SQLite/PostgreSQL persistence behind the existing `API-004` (`SupportRequestRepository`) boundary.
2. **Authentication & Role Security (`ADR-0003`, `REQ-006`):** `X-Simulated-Role` (`STUDENT` / `STAFF`) is an unverified demonstration header. Cycle 2 must replace or harden simulated headers with authenticated session/token verification.
3. **Workflow Lifecycle Extensions (`Q-002` & Student Edit/Cancel):** Cycle 1 enforces forward-only `OPEN -> IN_PROGRESS -> RESOLVED` transitions and does not permit student editing/cancellation or staff reopening of `RESOLVED` requests. Cycle 2 should evaluate controlled reopening with mandatory audit reasons.
4. **Workflow & Review Discipline Postmortem:** In Assignment 4, stacked PR branches caused merge-order friction and `ai-use-log.md` conflicts. In Cycle 2, each feature or defect fix must branch cleanly from `main`, stay small and independently buildable, and wait for green CI and peer review before merge.
