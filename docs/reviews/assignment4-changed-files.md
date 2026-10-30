# Assignment 4 Changed-File Index (Since Assignment 3 Tag `a3-architecture-review`)

- **Deliverable:** Changed-File Index (COMP 330 — Assignment 4)
- **Authoritative GitHub Location:** `/docs/reviews/assignment4-changed-files.md` (and Sakai Word Summary)
- **Primary Owner:** Joanelis Rivera Matos (`@JoanelisRM`) · **Backup Owner / Reviewer:** Sydel Ugwu (`@Sugwu123`)
- **Related Issue:** [Issue #41 — A4: Changed-File Index](https://github.com/JoanelisRM/comp330-f26-team-05/issues/41)
- **Last Updated:** 2026-10-29

| Repository Path / Artifact | Status Since A3 | What Changed or Why No Change | Owner(s) |
|---|---|---|---|
| `/.github/pull_request_template.md` | **Added** | Added reusable PR template enforcing issue links, architecture boundary checks, test/CI evidence, dependency review, risks, and AI disclosure (#44). | Sydel Ugwu / Joanelis Rivera Matos |
| `/.github/workflows/ci.yml` | **Added** | Added GitHub Actions CI workflow running `mvn -B clean test` on pull requests and pushes to `main` (#47). | Nova Kurul / Hamza Bashar |
| `/pom.xml` | **Added** | Added Java 17 Maven build configuration with JUnit Jupiter 5.10.2 (test scope) and zero external runtime dependencies (#30 / `TASK-101`). | Sydel Ugwu / Nova Kurul |
| `/src/main/java/edu/luc/comp330/campusconnect/model/` | **Added** | Added `SupportRequest.java` and `RequestStatus.java` implementing synthetic request records and forward-only status rules (`REQ-003`, `REQ-004`, `REQ-005`, #31). | Sydel Ugwu / Hamza Bashar |
| `/src/main/java/edu/luc/comp330/campusconnect/data/` | **Added** | Added `SupportRequestRepository.java` (`API-004`) and `InMemorySupportRequestRepository.java` (`Q-003`, #31 / `TASK-102`). | Sydel Ugwu / Hamza Bashar |
| `/src/main/java/edu/luc/comp330/campusconnect/logic/` | **Added** | Added `Role.java`, `RoleCheckService.java` (`API-005`), and `SupportRequestService.java` (`API-001..003`, #30, #33). | Sydel Ugwu, Khaja Shabbir Ahmed, Hamza Bashar |
| `/src/main/java/edu/luc/comp330/campusconnect/observability/` | **Added** | Added `AuditLogger.java` for structured runtime and security/validation event logging (#49 / `TASK-108`). | Sydel Ugwu / Nova Kurul |
| `/src/main/java/edu/luc/comp330/campusconnect/presentation/` | **Added** | Added `CampusConnectApp.java` providing the localhost JDK `HttpServer` JSON endpoints and `--demo` diagnostic verification runner (#30, #33, #49). | Sydel Ugwu / Khaja Shabbir Ahmed |
| `/src/test/java/edu/luc/comp330/campusconnect/` | **Added** | Added `InMemorySupportRequestRepositoryTest.java` and `SupportRequestServiceTest.java` verifying happy paths, whitespace rejections, role denials, and disallowed transitions (#31, #36). | Sydel Ugwu, Hamza Bashar |
| `/docs/decisions/adr-0003-web-framework-localhost-transport-and-simulated-role.md` | **Added** | Recorded decision selecting JDK `HttpServer`, localhost JSON transport (`API-001..003`), and `X-Simulated-Role` passing (#29 / `TASK-111`). | Sydel Ugwu / Hamza Bashar |
| `/docs/reviews/architecture-review.md` | **Updated** | Recorded closure evidence and deferral decisions for A3 design review items `ARA-001..006`, `Q-002`, `Q-003`, and `Q-007` (#42). | Sydel Ugwu / Joanelis Rivera Matos |
| `/docs/observability/runtime-logging-evidence.md` | **Added** | Documented runtime log schema, captured 7-event diagnostic output, and error troubleshooting table (#49). | Sydel Ugwu / Nova Kurul |
| `/docs/testing/runtime-diagnostics-log.txt` | **Added** | Captured raw runtime verification log output for Cycle 1 workflows and failure paths (#49). | Sydel Ugwu / Nova Kurul |
| `/docs/testing/test-strategy.md` | **Updated** | Mapped `REQ-001..006` and `AC-REQ-001..006` to implemented JUnit 5 test methods (#36). | Hamza Bashar / Sydel Ugwu |
| `/docs/reviews/assignment4-review-package.md` | **Added** | Created authoritative index for the Assignment 4 construction and review package (#40). | Joanelis Rivera Matos / Sydel Ugwu |
| `/docs/planning/traceability.md` | **Updated** | Connected `REQ-001..006` and `TASK-101..111` to GitHub Issues `#29`–`#49`, branches, PRs, CI runs, and JUnit tests (#46). | Joanelis Rivera Matos / Sydel Ugwu |
| `/docs/ai/ai-use-log.md` | **Updated** | Logged Assignment 4 AI assistance, rejected framework alternatives, human edits, and verification steps. | Nova Kurul / All Members |
| `/docs/team/team-charter.md`, `/docs/team/working-agreements.md`, `/docs/ai/ai-policy.md` | **Unchanged** | Governance baseline remained stable during Cycle 1 construction; no team charter or AI policy changes were required. | Team 05 |
