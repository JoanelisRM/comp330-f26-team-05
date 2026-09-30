# Traceability

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Joanelis Rivera Matos
**Gate:** A1 — Project Launch (initial baseline) · **Last updated:** 2026-09-24

At A1, traceability is intentionally light: requirements are proposed, and there is no architecture, implementation or test evidence yet. Gaps are listed openly below and will close as the project reaches later gates.

## Requirements Traceability Matrix

| Requirement | Acceptance Criteria | Architecture / Design | Implementation | Verification | Risk / Assumption | Status |
|---|---|---|---|---|---|---|
| REQ-001 Student submits a support request (proposed) | Pending (AC-REQ-001-xx) | Not yet available | Not yet implemented | Not yet available | R-005, PA-001 | Proposed — SCP-001 |
| REQ-002 Staff view submitted requests (proposed) | Pending confirmation with requirements owner | Not yet available | Not yet implemented | Not yet available | R-005, PA-001 | Proposed — SCP-002 |
| REQ-003 Staff update request status (proposed) | Pending (AC-REQ-003-xx) | Not yet available | Not yet implemented | Not yet available | R-005, R-003 | Proposed — SCP-003 |

## Baseline Reconciliation

The starter template used REQ-002 for requesters viewing their own request status. The team's proposed scope and traceability documents instead use REQ-002 for staff viewing submitted requests. This draft aligns REQ-002 with SCP-002 and the planning matrix. The former starter wording is not included in the proposed Cycle 1 baseline. This reconciliation remains pending team review.


## Decision Traceability

| Decision | Drivers / Inputs | Affected Architecture / Implementation | Verification / Follow-Up |
|---|---|---|---|
| ADR-001 (planned): project choice and tech stack (Java/Maven) | README prerequisites, R-007 | Whole Cycle 1 implementation | TASK-008; review at A3 |
| Scope decision: synthetic data only, no real Loyola integration | Data-handling constraints | All requirements | `scope.md` Out of Scope |

## Risk and Assumption Traceability

| Risk / Assumption | Affected Evidence | Current Effect / Action |
|---|---|---|
| R-005 / PA-001 | REQ-001..003, `scope.md`, `estimates.md` | Requirements provisional until TASK-006 is done |
| R-006 | Tag `a1-project-launch` | Tag must be moved before grading (TASK-010) |
| R-007 | Future implementation (A4) | Setup and ADR-001 needed before estimating the build |
| PA-003 / R-008 | `schedule.md`, all estimates | Capacity to be confirmed by each member |

## Change Impact Traceability

| Change | Upstream Evidence | Downstream Evidence Reviewed / Updated | Result |
|---|---|---|---|
| Initial planning baseline created | Proposed slice (`scope.md`) | Task plan, estimates, schedule, risks, this file | Consistent as of 2026-09-24 |

## Traceability Gaps

| Gap | Why It Matters | Owner | Planned Resolution | Target Gate |
|---|---|---|---|---|
| REQ-001..003 not yet written in `docs/requirements/` | Scope and planning cannot be confirmed | Sydel Ugwu | TASK-006 | A1 |
| No acceptance criteria | Nothing to verify against | Sydel Ugwu | TASK-006 | A1/A2 |
| Tasks not linked to GitHub Issues | Work is not visible on GitHub | Khaja Shabbir Ahmed | TASK-012 | A2 |
| No architecture or implementation evidence | Expected at this stage | Sydel Ugwu | A3/A4 | A3 |

## Traceability Maintenance

- Anyone who changes a requirement, scope item, decision or risk updates this file in the same PR.
- The Planning & Process Lead reviews this file at every phase gate.
- From A2, requirement IDs will appear in GitHub Issues and PR descriptions so links can be followed in both directions.
