# Assignment 4 Evidence README — Cycle 1 Construction, Integration, and AI Code Review Package

- **Deliverable:** Assignment 4 Evidence README (COMP 330 — Assignment 4)
- **Authoritative GitHub Location:** `/docs/reviews/assignment4-review-package.md`
- **Primary Owner:** Joanelis Rivera Matos (`@JoanelisRM`) · **Backup Owner / Reviewer:** Sydel Ugwu (`@Sugwu123`)
- **Related Issue:** [Issue #40 — A4: Assignment 4 Evidence README](https://github.com/JoanelisRM/comp330-f26-team-05/issues/40)
- **Repository Clone URL:** `https://github.com/JoanelisRM/comp330-f26-team-05.git`
- **Required Phase-Gate Tag:** `a4-construction-integration`

## 1. Cycle 1 Vertical Slice Summary

CampusConnect Cycle 1 implements the end-to-end student support request vertical slice in Java 17 / Maven:
1. **Student Submission (`REQ-001`, `API-001`, `TASK-103`):** Validates `category`, `title`, and `description` (rejecting empty or whitespace-only inputs per `AC-REQ-001-02`), assigns a sequential `REQ-####` ID and UTC timestamp, and stores a synthetic record (`REQ-004`).
2. **Staff Request Queue (`REQ-002`, `API-002`, `TASK-104`):** Enforces `STAFF` role authorization (`API-005`, `REQ-006`) and returns all submitted requests in chronological order.
3. **Staff Status Transitions (`REQ-003`, `API-003`, `TASK-105`):** Enforces forward-only progression (`OPEN -> IN_PROGRESS -> RESOLVED`) and rejects skipped states or reopening (`Q-002`).
4. **In-Memory Data Layer (`REQ-005`, `API-004`, `TASK-102`):** Provides `SupportRequestRepository` and `InMemorySupportRequestRepository` (`Q-003`) with defensive copying.
5. **Structured Runtime Logging (`TASK-108`, `Issue #49`, `Q-007`):** Records submission, validation failure, role denial, and status-transition events via `AuditLogger`.

## 2. Authoritative Assignment 4 Evidence Index

| Deliverable | Purpose | Authoritative GitHub Location | Primary / Backup Owner | Related Issue(s) |
|---|---|---|---|---|
| **Assignment 4 Evidence README** | Index of construction, integration, review, AI-use, CI/CD, and test evidence | `/docs/reviews/assignment4-review-package.md` | Joanelis Rivera Matos / Sydel Ugwu | [#40](https://github.com/JoanelisRM/comp330-f26-team-05/issues/40) |
| **Changed-File Index** | Shows what changed, was added, or remained unchanged since Assignment 3 | `/docs/reviews/assignment4-changed-files.md` & Sakai Word Summary | Joanelis Rivera Matos / Sydel Ugwu | [#41](https://github.com/JoanelisRM/comp330-f26-team-05/issues/41) |
| **Architecture Review Action Items** | Shows what was raised in A3 design review, what changed, what was deferred, and closure evidence | `/docs/reviews/architecture-review.md` | Sydel Ugwu / Joanelis Rivera Matos | [#42](https://github.com/JoanelisRM/comp330-f26-team-05/issues/42) |
| **Transport & Simulated Role ADR** | Records JDK `HttpServer` localhost HTTP/JSON transport and `X-Simulated-Role` decision (`TASK-111`) | `/docs/decisions/adr-0003-web-framework-localhost-transport-and-simulated-role.md` | Sydel Ugwu / Hamza Bashar | [#29](https://github.com/JoanelisRM/comp330-f26-team-05/issues/29) |
| **Maven Project Skeleton & Layered Code** | Java 17 Maven build (`Presentation`, `Logic`, `Data`, `Observability` packages) (`TASK-101..105`) | `/pom.xml`, `/src/main/java/edu/luc/comp330/campusconnect/` | Sydel Ugwu, Khaja Shabbir Ahmed, Joanelis Rivera Matos, Hamza Bashar | [#30](https://github.com/JoanelisRM/comp330-f26-team-05/issues/30), [#31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31), [#33](https://github.com/JoanelisRM/comp330-f26-team-05/issues/33) |
| **Issue & Workflow Traceability Updates** | Connects `REQ-001..006`, GitHub Issues, branches, commits, PRs, CI runs, and tests | `/docs/planning/traceability.md` | Khaja Shabbir Ahmed, Joanelis Rivera Matos / Sydel Ugwu | [#46](https://github.com/JoanelisRM/comp330-f26-team-05/issues/46) |
| **Pull Request Template** | Reusable PR template requiring issue link, summary, evidence, risks, tests run, and AI disclosure | `/.github/pull_request_template.md` | Sydel Ugwu / Joanelis Rivera Matos | [#44](https://github.com/JoanelisRM/comp330-f26-team-05/issues/44) |
| **CI/CD Workflow & Results Evidence** | Automated Maven build and JUnit 5 test workflow on pull requests and `main` | `/.github/workflows/ci.yml`, `/docs/testing/ci-evidence.md` | Nova Kurul / Hamza Bashar | #47, #48 |
| **Runtime / Logging Evidence** | Structured runtime audit logging (`AuditLogger`), error diagnostics, and captured output | `/docs/observability/runtime-logging-evidence.md`, `/docs/testing/runtime-diagnostics-log.txt` | Sydel Ugwu / Nova Kurul | [#49](https://github.com/JoanelisRM/comp330-f26-team-05/issues/49) |
| **Automated Unit & Integration Tests** | JUnit 5 tests covering validation, forward-only transitions, role checks, and in-memory storage | `/src/test/java/edu/luc/comp330/campusconnect/`, `/docs/testing/test-strategy.md` | Hamza Bashar / Sydel Ugwu | [#36](https://github.com/JoanelisRM/comp330-f26-team-05/issues/36) |
| **AI-Use Log Updates** | Discloses AI assistance, accepted/rejected output, human edits, and verification | `/docs/ai/ai-use-log.md` | Nova Kurul / All Team Members | #50 |
