# Acceptance Criteria
 
Acceptance criteria for the Cycle 1 requirements in [requirements.md](requirements.md). In Cycle 1, the student and staff roles are simulated; there is no real login (ADR-002, TD-02; ASM-001).
 
## Acceptance Criteria
 
| ID | Requirement Reference | Acceptance Criterion | Verification Method | Status |
|---|---|---|---|---|
| AC-REQ-001-01 | REQ-001 | Given a user in the student role who has provided a category, a title, and a description, when the user submits a support request, then the system creates the request, records it with a unique identifier and its submission time, and confirms the submission to the user. | Automated test / demonstration | Proposed |
| AC-REQ-001-02 | REQ-001 | Given a user in the student role whose submission is missing the category, title, or description, when the user attempts to submit, then the system does not create the request and identifies which fields are missing. | Automated test | Proposed |
| AC-REQ-002-01 | REQ-002 | Given one or more submitted support requests, when a user in the staff role opens the request list, then every submitted request is shown with its identifier, title, submission time, and current status. | Automated test / demonstration | Proposed |
| AC-REQ-002-02 | REQ-002 | Given no support requests have been submitted, when a user in the staff role opens the request list, then the system shows an empty list with a message that there are no requests, and no error occurs. | Automated test | Proposed |
| AC-REQ-002-03 | REQ-002 | Given a user in the student role, when the user attempts to open the staff request list, then the system denies access and shows no request data. | Automated test | Proposed |
| AC-REQ-002-04 | REQ-002 | Given a user in the student role has just submitted a support request, when a user in the staff role opens the request list, then the new request appears in the list with its current status. | Automated integration test / demonstration | Proposed |
| AC-REQ-003-01 | REQ-003 | Given a request with status Open, when a user in the staff role changes its status to In Progress, then the request's status becomes In Progress. | Automated test | Proposed |
| AC-REQ-003-02 | REQ-003 | Given a request with status In Progress, when a user in the staff role changes its status to Resolved, then the request's status becomes Resolved. | Automated test | Proposed |
| AC-REQ-003-03 | REQ-003 | Given a request in any status, when a user in the staff role attempts a status change outside the sequence Open → In Progress → Resolved (for example Open → Resolved, or Resolved → Open), then the system rejects the change, the status is unchanged, and the user is told why. | Automated test | Proposed |
| AC-REQ-003-04 | REQ-003 | Given a user in the student role, when the user attempts to change the status of any request, then the system rejects the change and the status is unchanged. | Automated test | Proposed |
| AC-REQ-003-05 | REQ-003 | Given a user in the staff role has changed a request's status, when the request list is next viewed, then the list shows the updated status. | Automated integration test / demonstration | Proposed |
| AC-REQ-004-01 | REQ-004 | Given the repository and any data the system stores, when they are inspected, then all request data is synthetic and no real student personal information, credentials, or institutional records are present. | Inspection / code review | Proposed |
| AC-REQ-005-01 | REQ-005 | Given a support request has been submitted and its status has been changed, when the request list is viewed again while the application is still running, then the request is present with its most recent status. | Automated integration test | Proposed |
| AC-REQ-006-01 | REQ-006 | Given the Cycle 1 codebase, when it is reviewed, then all logic that depends on the user's role is located in a clearly identified part of the code, and no other part of the code checks the role directly. | Code review | Proposed |
 
## Writing Acceptance Criteria
 
The team writes criteria in Given / When / Then form. Each criterion describes one observable outcome and should be checkable by a single automated test or one demonstration step.
 
## Positive and Negative Conditions
 
| Condition Type | Covered By | Notes |
|---|---|---|
| Successful path | AC-REQ-001-01, AC-REQ-002-01, AC-REQ-003-01, AC-REQ-003-02 | Core slice behavior |
| Missing input | AC-REQ-001-02 | Required fields: category, title, description |
| Unauthorized access | AC-REQ-002-03, AC-REQ-003-04 | Enforced through simulated roles in Cycle 1 |
| Invalid state change | AC-REQ-003-03 | Covers skipped and backward transitions |
| Boundary condition | AC-REQ-002-02 | Empty request list |
| End-to-end flow | AC-REQ-002-04, AC-REQ-003-05, AC-REQ-005-01 | Submissions and status changes visible to staff |
| Duplicate operations | Not covered | Open question Q-004 |
| Unavailable dependencies | Not applicable | Cycle 1 runs locally with no external services (ADR-002, TD-06) |
 
## Verification Method
 
Verification methods are preliminary. As tests are written, each "Automated test" entry will be replaced with the specific test file in `/tests/`, and demonstration evidence will be recorded in `/docs/testing/`. AC-REQ-004-01 and AC-REQ-006-01 are verified by inspection and code review and will be added to the pull request review checklist.
 
## Status Guidance
 
All criteria are **Proposed** until the team accepts the requirements baseline. Status values used: Proposed, Accepted, Implemented, Verified, Failed, Deferred, Removed. A criterion is marked Verified only when test or demonstration evidence exists, not when the code is merely written.
 
## Acceptance Criteria and Implementation Tasks
 
These criteria define acceptable system behavior; they are not engineering tasks. Implementation work is planned in `docs/planning/task-plan.md`, and links between requirements, tasks, and criteria are maintained in `docs/planning/traceability.md`.
 
## Traceability
 
Traceability path: requirement → acceptance criterion → automated test or demonstration → implementation → phase-gate evidence.
 
| Requirement | Acceptance Criteria |
|---|---|
| REQ-001 | AC-REQ-001-01, AC-REQ-001-02 |
| REQ-002 | AC-REQ-002-01, AC-REQ-002-02, AC-REQ-002-03, AC-REQ-002-04 |
| REQ-003 | AC-REQ-003-01, AC-REQ-003-02, AC-REQ-003-03, AC-REQ-003-04, AC-REQ-003-05 |
| REQ-004 | AC-REQ-004-01 |
| REQ-005 | AC-REQ-005-01 |
| REQ-006 | AC-REQ-006-01 |
 
## Unresolved Criteria
 
These criteria depend on items in [assumptions-open-questions.md](assumptions-open-questions.md):
 
- **AC-REQ-003-03** assumes requests cannot be reopened or moved backward (ASM-006, Q-002). If that changes, REQ-003 and this criterion must change together.
- **AC-REQ-005-01** covers only a running application. Behavior after a restart depends on the storage decision (Q-003).
- Duplicate submissions have no criterion until Q-004 is answered.
## Expectations
 
- Maintain unique acceptance-criteria identifiers.
- Reference valid requirement IDs.
- Keep requirement-to-criterion traceability current in both files.
- Make criteria observable and verifiable.
- Include important success and failure conditions.
- Identify an appropriate verification method.
- Strengthen verification references as implementation matures.
- Do not claim verification without evidence.
- Record unresolved questions rather than inventing missing behavior.
- Preserve meaningful traceability when criteria change.| AC-REQ-002-03 | REQ-002 | Given a user in the student role, when the user attempts to open the staff request list, then the system denies access and shows no request data. | Automated test | Proposed |
| AC-REQ-002-04 | REQ-002 | Given a user in the student role has just submitted a support request, when a user in the staff role opens the request list, then the new request appears in the list with its current status. | Automated integration test / demonstration | Proposed |
| AC-REQ-003-01 | REQ-003 | Given a request with status Open, when a user in the staff role changes its status to In Progress, then the request's status becomes In Progress. | Automated test | Proposed |
| AC-REQ-003-02 | REQ-003 | Given a request with status In Progress, when a user in the staff role changes its status to Resolved, then the request's status becomes Resolved. | Automated test | Proposed |
| AC-REQ-003-03 | REQ-003 | Given a request in any status, when a user in the staff role attempts a status change outside the sequence Open → In Progress → Resolved (for example Open → Resolved, or Resolved → Open), then the system rejects the change, the status is unchanged, and the user is told why. | Automated test | Proposed |
| AC-REQ-003-04 | REQ-003 | Given a user in the student role, when the user attempts to change the status of any request, then the system rejects the change and the status is unchanged. | Automated test | Proposed |
| AC-REQ-003-05 | REQ-003 | Given a user in the staff role has changed a request's status, when the request list is next viewed, then the list shows the updated status. | Automated integration test / demonstration | Proposed |
| AC-REQ-004-01 | REQ-004 | Given the repository and any data the system stores, when they are inspected, then all request data is synthetic and no real student personal information, credentials, or institutional records are present. | Inspection / code review | Proposed |
| AC-REQ-005-01 | REQ-005 | Given a support request has been submitted and its status has been changed, when the request list is viewed again while the application is still running, then the request is present with its most recent status. | Automated integration test | Proposed |
| AC-REQ-006-01 | REQ-006 | Given the Cycle 1 codebase, when it is reviewed, then all logic that depends on the user's role is located in a clearly identified part of the code, and no other part of the code checks the role directly. | Code review | Proposed |
<!--
DELETE THE SAMPLE ROWS ABOVE after your team has replaced them with actual
project acceptance criteria.
-->

## Writing Acceptance Criteria

The team writes criteria in Given/ When/ Then form. Each criterion will describes one observable outcome and will be checkable by a single test or step.
<!--
Acceptance criteria should describe observable behavior or measurable outcomes.

When useful, use:

Given <starting condition>
When <action or event>
Then <observable result>

Given / When / Then is encouraged when it improves clarity, but it is not
mandatory. Another precise formulation is acceptable.

The important requirement is that the criterion be specific and verifiable.

Avoid criteria that merely repeat the requirement without defining how
satisfaction could be observed.
-->

## Positive and Negative Conditions

<!--
Do not consider only the successful path.

For relevant requirements, consider behavior involving:

- missing input;
- invalid input;
- unauthorized access;
- duplicate operations;
- unavailable dependencies;
- boundary conditions;
- failure conditions;
- recovery behavior; and
- other meaningful exceptions.

Not every requirement needs every type of condition. Use engineering judgment.
-->

## Verification Method

<!--
Identify how the team currently expects to demonstrate satisfaction of each
criterion.

Possible methods include:

- automated unit test;
- automated integration test;
- automated end-to-end test;
- manual demonstration;
- inspection;
- analysis;
- code review; or
- operational observation.

At an early phase gate, the method may be preliminary.

As implementation matures, replace general descriptions with concrete,
repository-visible evidence where practical.

Example early reference:
Automated test / demonstration

Example later reference:
tests/integration/test_request_submission.py
-->

## Status Guidance

<!--
Recommended status values:

Proposed
- Criterion has been identified but is not yet part of the accepted baseline.

Accepted
- Criterion is part of the current requirements baseline.

Implemented
- Intended system behavior exists.

Verified
- Evidence demonstrates that the criterion is satisfied.

Failed
- Verification demonstrates that the criterion is not currently satisfied.

Deferred
- Criterion has intentionally been postponed.

Removed
- Criterion no longer applies, but is retained when needed for traceability.

Do not mark a criterion Verified simply because implementation exists.
Verification requires evidence.
-->

## Acceptance Criteria and Implementation Tasks

<!--
Acceptance criteria describe acceptable SYSTEM BEHAVIOR.

They are not implementation tasks.

Acceptance criterion example:

"Given an invalid workflow request, when submission is attempted, then the
request is rejected and the missing required fields are identified."

Implementation-task example:

"Add validation logic to the request controller."

The first defines an observable outcome.
The second describes engineering work.
-->

## Traceability

<!--
Acceptance criteria should eventually connect requirements to verification
evidence.

A mature traceability path may look like:

REQ-001
  ->
AC-REQ-001-01
  ->
Automated integration test
  ->
Implementation
  ->
Phase-gate evidence

Maintain the relationships throughout the project rather than attempting to
reconstruct them at the end.
-->

## Unresolved Criteria

<!--
If the team cannot define a meaningful acceptance criterion because important
information is unknown, do not invent precision.

Record the unresolved matter in:

/docs/requirements/assumptions-open-questions.md

Refine the criterion when the required information becomes available.
-->

## Expectations

- Maintain unique acceptance-criteria identifiers.
- Reference valid requirement IDs.
- Keep requirement-to-criterion traceability current in both files.
- Make criteria observable and verifiable.
- Include important success and failure conditions.
- Identify an appropriate verification method.
- Strengthen verification references as implementation matures.
- Do not claim verification without evidence.
- Record unresolved questions rather than inventing missing behavior.
- Preserve meaningful traceability when criteria change.

<!--
Acceptance criteria are living engineering evidence and should mature alongside
requirements, implementation, and verification.

Before the applicable phase-gate submission:
1. Replace all sample data.
2. Confirm all requirement references are valid.
3. Review criteria for verifiability.
4. Remove instructional HTML comments.
-->
