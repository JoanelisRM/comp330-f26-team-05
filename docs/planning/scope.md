# Project Scope

**Owner:** Sydel Ugwu (Architecture & Development Lead) · **Backup:** Khaja Shabbir Ahmed

**Gate:** A2 — Cycle 1 Planning and Estimation · **Last updated:** 2026-09-29

## Scope Statement

In Cycle 1, CampusConnect will deliver one narrow, end-to-end vertical slice: a student submits a support request, staff view the submitted requests, and staff update a request's status. Cycle 1 uses synthetic data only and runs locally; it does not connect to any real Loyola system.

> **Status:** Proposed for A2 team review. Cycle 1 covers student request submission, staff request listing, and staff status updates. Sydel Ugwu maintains scope and traceability. Confirmation of REQ-001 through REQ-003 and their acceptance criteria is pending coordination with Hamza, who is handling requirements for A2.

## In Scope

| Scope ID | Included Capability / Work | Related Requirements | Notes |
|---|---|---|---|
| SCP-001 | Student submits a support request (category, title, description) | REQ-001 (proposed) | Includes required-field validation |
| SCP-002 | Staff view a list of submitted requests | REQ-002 (proposed) | Simple list; no filtering or search in Cycle 1 |
| SCP-003 | Staff manually update request status (Open → In Progress → Resolved) | REQ-003 (proposed) | Status changes should be traceable |
| SCP-004 | Launch and planning evidence (team, AI governance, requirements, planning) | — | A1/A2 phase-gate deliverables |

## Out of Scope

| Item | Reason Excluded | Future Consideration |
|---|---|---|
| Integration with real Loyola systems (login, student records) | Requires private data and institutional approval | Not planned; synthetic data only |
| Real authentication / single sign-on | Adds security and integration work beyond Cycle 1 capacity | Revisit after Cycle 1 if required |
| Native mobile application | Course delivery focuses on one application | Not planned |
| Production deployment | No institutional production authorization for a course project | Demonstrate release-readiness evidence only |

## Scope Constraints

| Constraint | Impact on Scope | Related Evidence |
|---|---|---|
| Semester timeline with six phase gates (A1–A6) | Cycle 1 must stay small enough to review and test properly | `schedule.md` |
| Five student members with other courses and jobs | Limited weekly capacity; features must be deferred rather than rushed | `estimates.md` (PA-003), R-008 |
| Technology: Java and Maven (per README) | Scope depends on the team becoming productive in this stack | R-007 |
| Synthetic data only | No real student data may enter the repository | `/docs/security/data-handling-notes.md` |

## Dependencies Affecting Scope

| Dependency | Why It Matters | Owner / Source | Related Risk |
|---|---|---|---|
| Requirements baseline and acceptance criteria (REQ-001 through REQ-003) | Confirm that each scope item matches the requirements and can be verified | Hamza (requirements); Sydel Ugwu (scope and traceability alignment) | R-005 |
| Tech stack / architecture decision (ADR-001) | Determines how the slice is built | Sydel Ugwu | R-007 |
| Team capacity per member | Determines how much scope is realistic | All members | R-008 |

## Deferred Scope

| Item | Reason Deferred | Decision / Evidence | Reconsider By |
|---|---|---|---|
| Admin / analytics dashboard | Needs a stable request model and agreed metrics first | This file | A3 |
| Automatic routing of requests to staff | Needs workflow policy and access rules | This file | After Cycle 1 |
| Email or in-app notifications | Needs failure-handling assumptions and test evidence | This file | After Cycle 1 |
| AI-assisted request categorization | Needs validation plan, human approval boundary and AI-use disclosure | `docs/ai/ai-policy.md` | After Cycle 1 |
| Search, filtering and sorting of requests | Not needed to prove the basic workflow | This file | A3 |

## Scope Change History

| Effective Gate | Change | Added / Removed | Reason | Related Evidence |
|---|---|---|---|---|
| A1 | Initial Cycle 1 scope baseline created | Added SCP-001..004 | Project launch | This file |
| A2 | Updated scope ownership and requirements coordination for A2 review | No capabilities added or removed | Align responsibilities with the team's A2 work allocation; scope confirmation remains pending | Requirements baseline in /docs/requirements/; traceability.md |

## Scope Acceptance Check

| Question | Current Answer |
|---|---|
| Is the Cycle 1 slice small enough to engineer and review well? | Yes — one workflow, three capabilities, synthetic data |
| What is intentionally not being built? | Real integrations, real authentication, mobile app, production deployment |
| What would justify expanding scope? | Slice implemented, reviewed and tested with spare capacity, and risks R-005/R-007/R-008 under control |
| Which item carries the most scope risk? | SCP-003 (status workflow), because status rules are not yet defined |
| Has the team confirmed this scope? | Not yet — to be confirmed with the requirements baseline |
