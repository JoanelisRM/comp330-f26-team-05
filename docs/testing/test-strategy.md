# Test and Verification Strategy — Cycle 1 Construction (Assignment 4)

- **Owner:** Hamza Bashar (`@hbashar786`, Quality & Review Lead) · **Backup / Reviewer:** Sydel Ugwu (`@Sugwu123`)
- **Related Issue:** [Issue #36 — A4: Task-106](https://github.com/JoanelisRM/comp330-f26-team-05/issues/36), [Issue #18](https://github.com/JoanelisRM/comp330-f26-team-05/issues/18)
- **Last Updated:** 2026-10-29

## 1. Purpose

This document maps Cycle 1 requirements (`REQ-001` through `REQ-006`), acceptance criteria (`AC-REQ-001..006`), API contracts (`API-001..005`), and architecture risks (`Q-002`, `Q-003`, `Q-007`) to executable JUnit 5 tests (`mvn clean test`) and runtime diagnostic checks.

## 2. Requirement-to-Test Verification Matrix (`AC-REQ-001..006`)

| Requirement / Contract | Acceptance Criterion / Rule | Automated Test Class & Method | Test Type (Happy / Negative / Boundary / Security) | Status |
|---|---|---|---|---|
| **REQ-001 / API-001** | `AC-REQ-001-01`: Valid student submission creates `OPEN` request with sequential `REQ-####` ID and timestamp | `SupportRequestServiceTest#submitRequestCreatesOpenSyntheticRequestWhenFieldsAreValid` | Happy path | **Implemented & Passing** |
| **REQ-001 / API-001** | `AC-REQ-001-02`: Empty or whitespace-only `category`, `title`, or `description` is rejected and lists all missing fields | `SupportRequestServiceTest#submitRequestRejectsEmptyOrWhitespaceFieldsAndIdentifiesAllMissingFields` | Negative / Boundary | **Implemented & Passing** |
| **REQ-002 / API-002** | `AC-REQ-002-01`: Staff view all submitted requests in chronological order | `InMemorySupportRequestRepositoryTest#findAllPreservesInsertionOrderAndReturnsUnmodifiableList` | Happy path & immutability boundary | **Implemented & Passing** |
| **REQ-003 / API-003** | `AC-REQ-003-01`: Staff transition request status along `OPEN -> IN_PROGRESS -> RESOLVED` | `SupportRequestServiceTest#staffCanTransitionOpenToInProgressToResolved` | Happy path state transition | **Implemented & Passing** |
| **REQ-003 / Q-002** | `AC-REQ-003-02`: Disallowed transitions (`OPEN -> RESOLVED` skip or `RESOLVED -> OPEN` reopen) are rejected with `INVALID_TRANSITION` | `SupportRequestServiceTest#staffCannotSkipInProgressOrReopenResolvedRequest` | Negative / State boundary | **Implemented & Passing** |
| **REQ-004** | `AC-REQ-004-01`: Only synthetic data is stored (`synthetic=true`); no real student PII or credentials | `InMemorySupportRequestRepositoryTest#saveAndFindByIdPreservesSyntheticRequestFields` | Governance / Data boundary | **Implemented & Passing** |
| **REQ-005 / API-004** | `AC-REQ-005-01`: Authoritative in-memory store (`Q-003`) persists requests during runtime and throws on unknown ID | `InMemorySupportRequestRepositoryTest#updateStatusUpdatesStoredRecordAndTimestamp`, `updateStatusThrowsExplicitExceptionWhenIdDoesNotExist` | Persistence & error path | **Implemented & Passing** |
| **REQ-006 / API-005** | `AC-REQ-006-01`: Central role check denies `STUDENT` from calling staff list (`API-002`) or status update (`API-003`) | `SupportRequestServiceTest#roleCheckDeniesStudentFromListingOrUpdatingRequests` | Security / Authorization boundary | **Implemented & Passing** |

## 3. Running the Automated Test Suite Locally and in CI

```bash
mvn -B clean test
```
