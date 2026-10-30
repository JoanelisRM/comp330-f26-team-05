# Assumptions and Open Questions
 
Known uncertainty affecting the Cycle 1 requirements baseline in [requirements.md](requirements.md) and [acceptance-criteria.md](acceptance-criteria.md). Several assumptions come from tradeoffs accepted in [ADR-002](../decisions/ADR-002-cycle-1-scope-tradeoffs.md).
 
## Key Distinctions
 
- **Assumption:** something the team is treating as true without full confirmation.
- **Open question:** something the team knows it does not yet know.
- **Requirement:** an obligation the system must satisfy (see requirements.md).
- **Acceptance criterion:** an observable condition showing a requirement is met (see acceptance-criteria.md).
Assumptions are not turned into requirements just because the team needs an answer.
 
## Assumptions
 
| ID | Assumption | Basis | Related Evidence | Impact if Incorrect | Owner | Status |
|---|---|---|---|---|---|---|
| ASM-001 | In Cycle 1, the student and staff roles are simulated, and this is an acceptable substitute for real authentication. | ADR-002, TD-02 | REQ-001, REQ-002, REQ-003, REQ-006; AC-REQ-002-03, AC-REQ-003-04 | Real authentication would move into Cycle 1, adding significant security and integration work and likely delaying the schedule. | Hamza | Unvalidated |
| ASM-002 | Synthetic data is sufficient to demonstrate and test the Cycle 1 slice. | ADR-002, TD-01 | REQ-004, AC-REQ-004-01 | Real data would need institutional approval and privacy controls the project does not have. | Hamza | Unvalidated |
| ASM-003 | Running the system locally is sufficient for the Cycle 1 demonstration and assessment. | ADR-002, TD-06 | ADR-002 | Hosted deployment would need infrastructure and security work not in the current plan. | Joanelis | Unvalidated |
| ASM-004 | A support request has exactly one current status at any time. | Team interpretation of the Cycle 1 workflow | REQ-003; AC-REQ-003-01 to AC-REQ-003-05 | The request model and request list would need redesign. | Joanelis | Unvalidated |
| ASM-005 | A newly submitted request starts with the status Open. | REQ-003 sequence begins at Open | REQ-001, REQ-003; AC-REQ-002-04, AC-REQ-003-01 | The status sequence in REQ-003 and its criteria would need a new starting status. | Hamza | Unvalidated |
| ASM-006 | Status changes only move forward one step at a time; a Resolved request cannot be reopened in Cycle 1. | ADR-002, Decision item 3 | REQ-003, AC-REQ-003-03, Q-002 | REQ-003 and AC-REQ-003-03 would need to allow additional transitions. | Joanelis | Unvalidated |
| ASM-007 | The Cycle 1 slice fits within the team's available weekly hours. | ADR-002; risk R-008 | REQ-001 to REQ-006; `docs/planning/estimates.md` | Scope would need to shrink further, or work would reach later phases untested (R-003). | Hamza | Unvalidated |
 
### Assumption Status
 
Status values: **Unvalidated** (treated as true, not confirmed), **Validated** (confirmed by evidence), **Invalidated** (shown to be wrong), **Superseded** (replaced by a new decision). All assumptions are currently Unvalidated.
 
## Open Questions
 
| ID | Question | Related Evidence | Owner | Needed By | Status / Resolution |
|---|---|---|---|---|---|
| Q-001 | Which information must a student provide when submitting a support request? | REQ-001, REQ-002, AC-REQ-001-02 | Hamza | Before implementation | Resolved: category, title, and description, as planned in `docs/planning/traceability.md` (REQ-001, TASK-103). Recorded in REQ-001 and AC-REQ-001-01/02. |
| Q-002 | Can a Resolved request be reopened, or can a request move back to an earlier status? | REQ-003, AC-REQ-003-03, ASM-006 | Joanelis | Before implementation | Resolved: A resolved quest cannot be reopened or move back to an earlier status |
| Q-003 | How will support request data be stored, and must it persist after the application restarts? | REQ-005, AC-REQ-005-01; ADR-002 known technical debt | Joanelis | A3 | Resolved: The system will only store data temporarily in memory during a run |
| Q-004 | Should the system prevent or flag duplicate submissions of the same request? | REQ-001; acceptance-criteria.md (Positive and Negative Conditions) | Hamza | A3 | Open |
| Q-005 | Should students be able to view the status of requests they submitted? | REQ-001, REQ-002 | Hamza | A3 | Open |
| Q-006 | Can a student edit or cancel a request after submitting it? | REQ-001, REQ-003 | Joanelis | A3 | Resolved:  A student can edit a request as long as its status is 'Open' and not 'In Progress' or 'Resolved.' A student can also cancel a request as long as the request is 'Open' or 'In Progress' but not when it is 'Resolved.' |
| Q-007 | Should the system record who changed a request's status and when? | REQ-003 | Hamza | Before implementation | Resolved: Partly. Each status change is logged with the simulated role and time (AuditLogger, TASK-108, PR #60), and the request stores its last-updated time. Individual users are not recorded, because roles are simulated and unverified (ASM-001). Revisit if real authentication is added. |
| Q-008 | What technology stack will the system use? | ADR-001 (pending) | Hamza | Before implementation | Open (to be answered by ADR-001) |
 
### Open-Question Guidance
 
**Needed By** is the earliest phase gate or decision that would be affected if the question stayed open. When a question is resolved, its row is kept, the resolution and its evidence are recorded in the Status / Resolution column, and the related requirements and criteria are updated.
 
## Resolving Assumptions
 
When an assumption is validated or invalidated, the owner records the result and evidence, then reviews and updates every item listed in its Related Evidence column. For example, if ASM-006 is invalidated because requests can be reopened, REQ-003 and AC-REQ-003-03 must both change, and Q-002 is marked Resolved.
 
## Resolving Open Questions
 
A question is resolved only when the answer is recorded in the repository, not only in chat or a meeting. The resolution references where the answer now lives (a requirement, criterion, or decision record), and affected artifacts are updated in the same change where practical.
 
## Managing Unknowns
 
Some questions will legitimately remain open at A2. Questions marked "Before implementation" and still open (Q-002, Q-007, Q-008) have the highest priority, because implementation of the Cycle 1 slice depends on their answers. Questions marked A3 can remain open without blocking Cycle 1.
 
## Relationship to Risk
 
- ASM-007 is tied to R-008 (limited weekly hours) and R-003 (work reaching later phases untested).
- Q-008 is tied to R-002 and R-007 (team experience with Git workflow and Java/Maven).
- ASM-001 is tied to the technical debt accepted in ADR-002 for simulated roles.
## Expectations
 
- Record important assumptions explicitly.
- Record unresolved matters as clear questions.
- Assign owners.
- Identify affected evidence.
- State the impact of important assumptions being wrong.
- Identify when open questions need resolution.
- Update downstream artifacts when uncertainty is resolved.
- Preserve meaningful history rather than silently overwriting prior understanding.
- Link assumptions and questions to risks when the potential consequence warrants it.
- Never invent certainty simply to make an artifact appear complete.
