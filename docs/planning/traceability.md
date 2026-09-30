# Traceability

**Owner:** Sydel Ugwu (Architecture & Development Lead)

**Backup / Reviewer:** Hamza Bashar

**Gate:** A2 - Cycle 1 Planning and Estimation

**Last updated:** 2026-09-29

## Planning Status

This draft connects the proposed Cycle 1 requirements to scope items, tasks, owners, estimate IDs, risks, and planned evidence locations.

Requirements and acceptance criteria remain pending confirmation with Hamza Bashar through TASK-025. Team scope confirmation is also pending.

Build tasks are planned for A4, with proposed owners. References below describe planned work, not completed implementation or successful tests.

## Requirement-to-Task Traceability

| Requirement | Scope Item | Planned Build Tasks | Planned Verification Tasks | Acceptance Criteria Status |
|---|---|---|---|---|
| REQ-001 Student submits a support request with category, title, and description | SCP-001 | TASK-102 request model and synthetic store; TASK-103 submission and required-field validation; TASK-108 failed-submission logging | TASK-106 validation unit tests; TASK-109 end-to-end demonstration | AC-REQ-001-01 and AC-REQ-001-02 in [acceptance-criteria.md](../requirements/acceptance-criteria.md); both Proposed, pending team review |
| REQ-002 Staff view submitted requests | SCP-002 | TASK-102 request model and synthetic store; TASK-104 staff request list | TASK-109 end-to-end demonstration, including staff list behavior | Confirm final criteria and IDs through TASK-025 |
| REQ-003 Staff update request status | SCP-003 | TASK-102 request model and synthetic store; TASK-105 allowed status transitions; TASK-108 status-change logging | TASK-106 status-transition unit tests; TASK-109 end-to-end demonstration | Confirm final criteria and IDs through TASK-025 |

Task definitions, dependencies, proposed owners, and reserved estimate IDs are recorded in [task-plan.md](task-plan.md).

TASK-101 provides the Maven setup shared by all three requirements. TASK-107 provides the planned CI build and test checks.

No implementation or test-result evidence is claimed in this matrix.

## Task Owners, Estimates, and Planned Evidence

The assignments and estimate IDs below come from the A2 task plan. Build owners are proposed, and EST-101 through EST-109 are reserved IDs.

Numerical estimates must be confirmed in [estimates.md](estimates.md). These IDs do not establish that estimates are complete. Each estimate must include the required range, assumptions, confidence, dependencies, and risk triggers.

Evidence locations describe planned outputs, not existing implementation or passing tests.

| Task | Requirement Coverage | Proposed Owner | Estimate ID | Planned Completion Evidence |
|---|---|---|---|---|
| TASK-101 | Shared setup for REQ-001 through REQ-003 | Sydel Ugwu | EST-101 | README build/run instructions and reviewed Maven setup PR |
| TASK-102 | REQ-001 through REQ-003 | Sydel Ugwu | EST-102 | Request model and synthetic store in /src/, with reviewed PR |
| TASK-103 | REQ-001 | Khaja Shabbir Ahmed | EST-103 | Submission and validation implementation in /src/, with reviewed PR |
| TASK-104 | REQ-002 | Joanelis Rivera Matos | EST-104 | Staff-list implementation in /src/, with reviewed PR |
| TASK-105 | REQ-003 | Hamza Bashar | EST-105 | Status-transition implementation in /src/, with reviewed PR |
| TASK-106 | REQ-001 and REQ-003 | Hamza Bashar | EST-106 | Automated tests in /tests/ and results referenced from /docs/testing/ |
| TASK-107 | Shared verification infrastructure | Nova Kurul | EST-107 | .github/workflows/ci.yml and linked CI run results |
| TASK-108 | REQ-001 and REQ-003 | Nova Kurul | EST-108 | Logging implementation and evidence in /docs/observability/ |
| TASK-109 | REQ-001 through REQ-003 | Joanelis Rivera Matos | EST-109 | End-to-end demonstration evidence referenced from /docs/testing/ |

Before final A2 review, confirm the estimate entries and add available GitHub Issue links. During implementation, replace planned evidence descriptions with exact file, PR, and test-result links.

## A2 Planning Dependencies

| Task | Deliverable | Owner | Dependency / Follow-Up |
|---|---|---|---|
| TASK-015 | Scope statement | Sydel Ugwu | Confirm the proposed slice with the team; backup/reviewer is Khaja Shabbir Ahmed |
| TASK-016 | Requirement-to-task traceability | Sydel Ugwu | Align TASK-015 with TASK-025; backup/reviewer is Hamza Bashar |
| TASK-025 | Requirements package | Hamza Bashar | Confirm requirements, acceptance criteria, assumptions, and open questions |
| TASK-018 | GitHub Issues | Nova Kurul | Add issue links for the tasks in the task plan |
| TASK-022 | Projects board and milestone | Joanelis Rivera Matos | Connect issues to the board and Cycle 1 milestone |
| TASK-019 / TASK-024 | Estimates and re-estimation | Hamza Bashar | Review impacts when requirements or scope change |
| TASK-027 | AI-use log updates | Nova Kurul | Record and review AI assistance used for this planning work |
| TASK-028 | Final review, tag, and submission | Joanelis Rivera Matos | Review and merge the A2 deliverables before tagging the submitted version |

See [task-plan.md](task-plan.md) for the complete A2 assignments, backups, deadlines, and dependencies.

## Baseline Reconciliation

The starter requirements template used REQ-002 for requesters viewing their own request status. The proposed scope and A2 task plan use REQ-002 for staff viewing submitted requests.

This draft follows SCP-002 and TASK-104. Hamza Bashar and the team must confirm the REQ-002 meaning and acceptance criteria before the baseline is accepted.

Earlier requirement edits on this branch must be reconciled with TASK-025 during PR review. Do not treat the conflicting definitions as approved requirements.

## Decision Traceability

| Decision | Affected Work | Follow-Up |
|---|---|---|
| ADR-001: technology and architecture decision, planned | TASK-101 and dependent build tasks | Sydel Ugwu; A1 carry-over TASK-008, reviewed at A3 |
| Synthetic data only; no real Loyola integration | REQ-001 through REQ-003; TASK-102 | Maintain the boundary in [scope.md](scope.md) |
| Cycle 1 scope tradeoffs, TASK-026 | Included and deferred capabilities | Align scope with [ADR-002](../decisions/ADR-002-cycle-1-scope-tradeoffs.md) before final review |

## Risk and Assumption Traceability

| Risk / Assumption | Affected Work | Required Follow-Up |
|---|---|---|
| R-005 / PA-001 | REQ-001 through REQ-003; TASK-015, TASK-016, TASK-025 | Confirm requirements and update scope, traceability, and estimates together |
| R-007 | ADR-001; TASK-101 and dependent tasks | Confirm setup and architecture assumptions |
| PA-003 / R-008 | Schedule, estimates, proposed task assignments | Confirm each member's capacity through team commitments |
| R-006, A1 tag carry-over | TASK-010 | Team Lead verifies the A1 tag status and determines any required correction |

Risk IDs are carried forward from the planning baseline. Current descriptions and status must agree with [risk-register.md](risk-register.md).

Changes to requirements, status-transition rules, technology assumptions, or available capacity must be reviewed for their effects on task effort, dependencies, scope, and schedule. Record resulting estimate changes in [re-estimation.md](re-estimation.md).

## Open Traceability Gaps

| Gap | Responsible Person | Resolution |
|---|---|---|
| Requirements and acceptance criteria are not yet reconciled with this draft | Hamza Bashar and Sydel Ugwu | Complete TASK-025 alignment and add exact acceptance-criteria references before accepting the baseline |
| Estimate IDs are reserved, but numerical entries have not been verified for this draft | Hamza Bashar and Sydel Ugwu | Check EST-101 through EST-109 against estimates.md |
| GitHub issue links are not supplied in the reviewed task plan | Nova Kurul | Populate issue links through TASK-018 and connect them to the task plan |
| Staff-list verification has no separate unit-test task | Hamza Bashar and Khaja Shabbir Ahmed | Confirm TASK-109 covers the agreed REQ-002 criteria; add a test task if needed |
| Architecture, implementation, and test-result evidence are not linked | Sydel Ugwu with task owners | Add actual evidence during A3/A4 |
| Team approval and merge evidence are not recorded here | Sydel Ugwu and Hamza Bashar | Review this draft against the final requirements and task plan in the PR |
| AI assistance for these edits needs a matching log entry | Sydel Ugwu and Nova Kurul | Record the assistance, affected files, and human review in docs/ai/ai-use-log.md |

## Change History

| Date | Gate | Change |
|---|---|---|
| 2026-09-24 | A1 | Initial planning traceability baseline created |
| 2026-09-29 | A2 | Corrected REQ-002 alignment; connected requirements to build and verification tasks, proposed owners, reserved estimate IDs, and planned evidence; updated ownership and recorded unresolved dependencies |

## Maintenance

- Update this file in the same PR as changes to requirements, scope, tasks, decisions, or risks.
- Add actual issue, PR, implementation, and test-evidence links as they become available.
- Keep proposed work separate from completed and verified work.
- Review changes against the task plan, estimates, schedule, and risk register.
- Disclose AI-assisted changes and record human review.
- The Planning & Process Lead reviews traceability at each phase gate.
