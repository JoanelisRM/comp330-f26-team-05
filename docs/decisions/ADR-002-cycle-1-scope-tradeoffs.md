# ADR-002: Cycle 1 Scope Tradeoffs and Deferred Work

**Status:** Proposed (to be accepted by the team before the A2 submission)
**Date:** 2026-09-25
**Decision Owners:** Khaja Shabbir Ahmed (owner) · Joanelis Rivera Matos (backup)

> ADR-001 is reserved for the project choice and tech stack decision (Sydel Ugwu).

## Context

CampusConnect could grow into a large system: student requests, staff workflow, notifications, dashboards, routing and AI-assisted categorization. The team has five members with limited weekly hours (R-008), is still new to team Git workflow and possibly to Java/Maven (R-002, R-007), and has not yet written its requirements baseline (R-005). A2 asks the team to record scope cuts, deferred work, technical debt and tradeoffs so that Cycle 1 stays small enough to build, review and test properly.

## Decision

Cycle 1 will deliver **one vertical slice only**:

1. A student submits a support request (REQ-001, proposed).
2. Staff view the list of submitted requests (REQ-002, proposed).
3. Staff manually update a request's status: Open → In Progress → Resolved (REQ-003, proposed).

The following tradeoffs apply to Cycle 1:

| ID | Tradeoff | What we chose | What we gave up | Why |
|---|---|---|---|---|
| TD-01 | Data | Synthetic data only | Realism of real student data | Real data needs institutional approval and privacy controls |
| TD-02 | Authentication | No real login; roles (student/staff) simulated | Real access control | Real auth is a large security and integration task; revisit after Cycle 1 |
| TD-03 | Status workflow | Manual status changes by staff | Automatic routing and assignment | Manual flow proves the lifecycle with far less logic to test |
| TD-04 | Staff view | Simple list | Search, filtering, sorting, dashboards | Not needed to prove the workflow end to end |
| TD-05 | AI features | No AI categorization in the product | Possible time savings for staff | Needs a validation plan and a human-approval boundary (R-004) |
| TD-06 | Deployment | Runs locally | Hosted deployment | No production authorization for a course project |

### Deferred scope

| Item | Evidence needed before accepting | Reconsider by |
|---|---|---|
| Admin / analytics dashboard | Stable request model; agreed metrics | A3 |
| Search, filtering and sorting | Slice working end to end | A3 |
| Notifications (email or in-app) | Failure-handling assumptions and test evidence | After Cycle 1 |
| Automatic routing to staff | Workflow policy and access rules | After Cycle 1 |
| Real authentication | Security design and permission boundaries in `docs/security/` | After Cycle 1 |
| AI-assisted categorization | Validation plan, human approval step, AI-use disclosure | After Cycle 1 |

### Known technical debt accepted in Cycle 1

- Simulated roles instead of real authentication; any code that assumes a role must be easy to replace (TD-02).
- Synthetic data store; the storage approach will be decided in A3 and may need migration later (TD-01).

## Alternatives Considered

### Alternative 1: Build the full workflow in Cycle 1 (submission, routing, notifications, dashboard)
Rejected. Estimated effort far exceeds team capacity, and most of it would reach A5 untested (R-003, R-008).

### Alternative 2: Build submission only
Rejected. It does not close the loop: nobody can act on a request, so the workflow cannot be demonstrated end to end.

## Consequences

### Positive
- The slice is small enough to estimate, build, review and test in Cycle 1.
- Every Cycle 1 feature traces to one of three requirements.
- Deferred items are visible, with clear conditions for bringing them back.

### Negative / Trade-Offs
- The Cycle 1 demo will look basic (no login, no dashboard).
- Simulated roles and synthetic data create technical debt to repay later.
- If the team moves fast, some capacity may go unused; this is accepted in exchange for lower schedule risk.

## Evidence

- `docs/planning/scope.md` (In Scope, Out of Scope, Deferred Scope)
- `docs/planning/task-plan.md` (TASK-101..109)
- `docs/planning/risk-register.md` (R-003, R-004, R-005, R-007, R-008)

## Revisit Conditions

- The requirements baseline (TASK-025) changes the slice.
- The slice is implemented, reviewed and tested with capacity left over → consider the A3 deferred items.
- ADR-001 (tech stack) makes any tradeoff above unnecessary or more expensive.
