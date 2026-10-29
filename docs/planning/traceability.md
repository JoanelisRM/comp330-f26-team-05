# Requirement, Task, and Workflow Review Traceability (Assignment 4)

- **Owner:** Sydel Ugwu (`@Sugwu123`, Architecture & Development Lead) · **Workflow Traceability Lead:** Joanelis Rivera Matos (`@JoanelisRM`)
- **Backup / Reviewer:** Hamza Bashar (`@hbashar786`) / Sydel Ugwu (`@Sugwu123`)
- **Gate:** A4 — Cycle 1 Construction, Integration, and AI Code Review
- **Related Issues:** [Issue #46 — A4: Workflow Review Traceability](https://github.com/JoanelisRM/comp330-f26-team-05/issues/46)
- **Last Updated:** 2026-10-29

## 1. End-to-End Requirement -> Issue -> Branch -> Code -> Test Traceability

| Requirement | Scope Item | Acceptance Criteria | Build Task & GitHub Issue | Branch(es) | Implementation Artifact(s) | Automated Test & Runtime Evidence | Status |
|---|---|---|---|---|---|---|---|
| **REQ-001** Student submits support request (`category`, `title`, `description`) with required-field validation | `SCP-001` | `AC-REQ-001-01`, `AC-REQ-001-02` | `TASK-103` ([Issue #33](https://github.com/JoanelisRM/comp330-f26-team-05/issues/33)), `TASK-102` ([Issue #31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31)) | `sydel/issue-33-task-103-submit-request`, `sydel/issue-31-task-102-data-layer` | `SupportRequestService.java` (`submitRequest`), `CampusConnectApp.java` (`POST /api/requests`) | `SupportRequestServiceTest#submitRequestCreatesOpenSyntheticRequestWhenFieldsAreValid`, `submitRequestRejectsEmptyOrWhitespaceFieldsAndIdentifiesAllMissingFields`; `/docs/observability/runtime-logging-evidence.md` | **Implemented & Verified** |
| **REQ-002** Staff view list of submitted requests | `SCP-002` | `AC-REQ-002-01` | `TASK-104`, `TASK-102` ([Issue #31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31)) | `sydel/issue-31-task-102-data-layer`, `sydel/issue-33-task-103-submit-request` | `SupportRequestService.java` (`listRequests`), `InMemorySupportRequestRepository.java` (`findAll`) | `InMemorySupportRequestRepositoryTest#findAllPreservesInsertionOrderAndReturnsUnmodifiableList`; `/docs/observability/runtime-logging-evidence.md` | **Implemented & Verified** |
| **REQ-003** Staff manually update request status (`OPEN -> IN_PROGRESS -> RESOLVED`) | `SCP-003` | `AC-REQ-003-01`, `AC-REQ-003-02` (`Q-002`) | `TASK-105`, `TASK-106` ([Issue #36](https://github.com/JoanelisRM/comp330-f26-team-05/issues/36)) | `sydel/issue-36-task-106-unit-tests`, `sydel/issue-31-task-102-data-layer` | `RequestStatus.java` (`canTransitionTo`), `SupportRequestService.java` (`updateStatus`) | `SupportRequestServiceTest#staffCanTransitionOpenToInProgressToResolved`, `staffCannotSkipInProgressOrReopenResolvedRequest` | **Implemented & Verified** |
| **REQ-004** Synthetic data only; no real Loyola student data or credentials | `SCP-001..003` | `AC-REQ-004-01` | `TASK-102` ([Issue #31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31)), `TASK-103` ([Issue #33](https://github.com/JoanelisRM/comp330-f26-team-05/issues/33)) | `sydel/issue-31-task-102-data-layer` | `SupportRequest.java` (`synthetic = true`), `AuditLogger.java` | `InMemorySupportRequestRepositoryTest#saveAndFindByIdPreservesSyntheticRequestFields` | **Implemented & Verified** |
| **REQ-005** Authoritative in-memory persistence during runtime (`API-004`, `Q-003`) | `SCP-001..003` | `AC-REQ-005-01` | `TASK-102` ([Issue #31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31)) | `sydel/issue-31-task-102-data-layer` | `SupportRequestRepository.java`, `InMemorySupportRequestRepository.java` | `InMemorySupportRequestRepositoryTest` (5 unit tests) | **Implemented & Verified** |
| **REQ-006** Central role check (`API-005`) before workflow execution | `SCP-001..003` | `AC-REQ-006-01` | `TASK-111` ([Issue #29](https://github.com/JoanelisRM/comp330-f26-team-05/issues/29)), `TASK-101` ([Issue #30](https://github.com/JoanelisRM/comp330-f26-team-05/issues/30)), `TASK-106` ([Issue #36](https://github.com/JoanelisRM/comp330-f26-team-05/issues/36)) | `sydel/issue-29-task-111-adr`, `sydel/issue-30-task-101-maven-skeleton` | `Role.java`, `RoleCheckService.java`, `ADR-0003` | `SupportRequestServiceTest#roleCheckDeniesStudentFromListingOrUpdatingRequests` | **Implemented & Verified** |

## 2. Workflow Review Traceability (Issues -> Branches -> PRs -> Reviews -> Evidence)

| GitHub Issue | Task / Deliverable | Branch Name | Primary Owner | Backup / Reviewer | Authoritative Repository Path |
|---|---|---|---|---|---|
| **[Issue #29](https://github.com/JoanelisRM/comp330-f26-team-05/issues/29)** | `TASK-111`: Web framework, localhost transport & simulated role ADR | `sydel/issue-29-task-111-adr` | Sydel Ugwu | Hamza Bashar | `/docs/decisions/adr-0003-web-framework-localhost-transport-and-simulated-role.md` |
| **[Issue #30](https://github.com/JoanelisRM/comp330-f26-team-05/issues/30)** | `TASK-101`: Maven project skeleton (`Presentation` / `Logic` / `Data`) | `sydel/issue-30-task-101-maven-skeleton` | Sydel Ugwu | Nova Kurul | `/pom.xml`, `/src/main/java/edu/luc/comp330/campusconnect/` |
| **[Issue #31](https://github.com/JoanelisRM/comp330-f26-team-05/issues/31)** | `TASK-102`: Data Layer interface (`API-004`) + in-memory implementation | `sydel/issue-31-task-102-data-layer` | Sydel Ugwu | Hamza Bashar | `/src/main/java/edu/luc/comp330/campusconnect/data/`, `/src/test/java/.../data/` |
| **[Issue #33](https://github.com/JoanelisRM/comp330-f26-team-05/issues/33)** | `TASK-103`: Submit support request: form, validation, ID & submission time | `sydel/issue-33-task-103-submit-request` | Khaja Shabbir Ahmed | Sydel Ugwu | `/src/main/java/edu/luc/comp330/campusconnect/logic/SupportRequestService.java` |
| **[Issue #36](https://github.com/JoanelisRM/comp330-f26-team-05/issues/36)** | `TASK-106`: Validation, status transitions & role checks unit tests | `sydel/issue-36-task-106-unit-tests` | Hamza Bashar | Sydel Ugwu | `/src/test/java/edu/luc/comp330/campusconnect/logic/SupportRequestServiceTest.java`, `/docs/testing/test-strategy.md` |
| **[Issue #40](https://github.com/JoanelisRM/comp330-f26-team-05/issues/40)** | Assignment 4 Evidence README | `sydel/issue-40-evidence-readme` | Joanelis Rivera Matos | Sydel Ugwu | `/docs/reviews/assignment4-review-package.md` |
| **[Issue #41](https://github.com/JoanelisRM/comp330-f26-team-05/issues/41)** | Changed-File Index | `sydel/issue-41-changed-files` | Joanelis Rivera Matos | Sydel Ugwu | `/docs/reviews/assignment4-changed-files.md` |
| **[Issue #42](https://github.com/JoanelisRM/comp330-f26-team-05/issues/42)** | Architecture Review Action Items | `sydel/issue-42-architecture-review` | Sydel Ugwu | Joanelis Rivera Matos | `/docs/reviews/architecture-review.md` |
| **[Issue #44](https://github.com/JoanelisRM/comp330-f26-team-05/issues/44)** | Pull Request Template | `sydel/issue-44-pr-template` | Sydel Ugwu | Joanelis Rivera Matos | `/.github/pull_request_template.md` |
| **[Issue #46](https://github.com/JoanelisRM/comp330-f26-team-05/issues/46)** | Workflow Review Traceability | `sydel/issue-46-workflow-traceability` | Joanelis Rivera Matos | Sydel Ugwu | `/docs/planning/traceability.md` |
| **[Issue #49](https://github.com/JoanelisRM/comp330-f26-team-05/issues/49)** | Runtime / Logging Evidence | `sydel/issue-49-runtime-logging` | Sydel Ugwu | Nova Kurul | `/docs/observability/runtime-logging-evidence.md`, `/docs/testing/runtime-diagnostics-log.txt` |

## 3. Known Limitations & Deferred Work Entering Assignment 5

1. **In-Memory Reset on Restart (`Q-003`):** `InMemorySupportRequestRepository` persists requests only while the JVM process runs.
2. **Forward-Only Transitions (`Q-002`):** `RESOLVED` requests cannot be reopened in Cycle 1 (`OPEN -> IN_PROGRESS -> RESOLVED`).
3. **Simulated Roles (`ADR-0003`, `ASM-001`):** `X-Simulated-Role` (`STUDENT` / `STAFF`) is an unverified demonstration input for Cycle 1 role-check verification (`REQ-006`), not real authentication.
