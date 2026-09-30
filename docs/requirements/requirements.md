# Requirements

<!--
STARTER KIT GUIDANCE — DELETE BEFORE PHASE-GATE SUBMISSION

This file contains sample requirements showing the expected structure and
level of detail.

Replace the sample requirements with requirements for your team's actual
system. Do not simply change a few words in the examples.

Requirements describe what the system is obligated to do or satisfy.
They should be clear enough that another engineer can understand the
obligation and determine whether it has been met.

Keep the document structure that is useful to your team, but remove
instructional comments like this one as you complete the artifact.
-->

## Requirements

<!--
Replace the sample rows below with your team's actual requirements.

Use unique IDs in the form REQ-###.

Requirement:
State the system obligation clearly and precisely. When practical, use
"The system shall..." Avoid vague statements such as "The system should
be easy to use" unless the expectation is made observable or measurable.

Rationale:
Explain why the requirement exists. Do not simply restate the requirement.

Priority:
Use Must, Should, or Could.

Acceptance Criteria Reference:
Reference the criteria in acceptance-criteria.md that demonstrate whether
the requirement has been satisfied.

Status:
Recommended values are Proposed, Accepted, Changed, Deferred, or Removed.
-->

| ID | Requirement | Rationale | Priority | Acceptance Criteria Reference | Status |
|---|---|---|---|---|---|
| REQ-001 | The system shall allow an authenticated student to submit a workflow request containing all information required for processing. | Students need a controlled and traceable way to initiate a workflow. | Must | AC-REQ-001-01, AC-REQ-001-02 | Proposed |
| REQ-002 | The system shall allow users in the support role to view a list of all submitted workflow requests, It should show each request's identifier, summary, submission time, and current status of the requests. Users who are authenticated student shall not have access to this list| Support staff cannot act on requests they cannot locate. The list closes the between submission and action, which is needed to demonstrate the workflow end to end. | Must | AC-REQ-002-01, AC-REQ-002-02, AC-REQ-002-03, AC-REQ-002-04 | Proposed |
| REQ-003 | The system shall allow a user who has designation of support role to change a requests status only along the sequence of Open → In Progress → Resolved and it shall reject any other status change and it should not allow any status change attempted by a user in the student role. | A controlled cycle shows that requests are being taken care of and prevents a request from appearing resolved without being looked at or worked on. | Must | AC-REQ-003-01, AC-REQ-003-02, AC-REQ-003-03, AC-REQ-003-04, AC-REQ-003-05 | Proposed |
| REQ-004 | The system should operate on synthetic data only and should not require or should store real student personal information, credentials, or it's institutional records | Access to students real data requires institutional approval and privacy controls which this project is not equipped with | Must | AC-REQ-004-01 | Proposed |
| REQ-005 | The system should not lose a submitted request or change its status while the application is running. | User must be able to trust or tell that a submitted request exists when support staff works on it. | Must | AC-REQ-005-01 | Proposed |
| REQ-006 | Logic that is dependent on the user's role shall be marked or kept in a way that it can be clearly identified as part of the codebase, so that simulated roles can be replaced when real authentication occurs without changes throughout the system |  |  | AC-REQ-006-01 | Proposed |
| REQ-007 |  | |  |  |  |
| REQ-008 |  |  |  |  |  |

<!--
DELETE THE SAMPLE ROWS ABOVE after your team has replaced them with actual
project requirements.

Do not reuse a requirement ID for a different requirement after that ID has
been referenced elsewhere in the repository.
-->

## Requirement Quality

<!--
Use this section as a final review checklist while developing your requirements.
Delete this comment before submission.

Good requirements should be:

- clear;
- concise;
- unambiguous;
- necessary;
- feasible;
- traceable; and
- verifiable.

Before accepting a requirement, consider:

- What stakeholder, engineering, operational, or project need does it address?
- Is the obligation clear?
- Could two reasonable people interpret it differently?
- Can the team eventually demonstrate whether it has been satisfied?
- Does it unnecessarily prescribe a technical solution?
- Is it consistent with other requirements?
- Does it depend on an unresolved assumption or open question?
-->

## Requirements and Design

These requirements only establishes on what the system must do, it does not in any way establishes on how it will be built. The technology stack will be recorded in a different spot and the storage approach is to be decided. REQ-006 can be said that it concerns code structure.
<!--
Requirements normally describe WHAT must be true, not unnecessarily dictate
HOW the system must be implemented.

Example of an appropriate requirement:

"The system shall preserve an audit record of workflow status changes."

This establishes an obligation.

By contrast:

"The system shall use PostgreSQL table workflow_history with three indexes..."

is usually an architecture or implementation decision unless that technology
is itself an externally imposed requirement.

Record significant implementation choices in the appropriate architecture or
decision artifact.
-->

## Requirements and Uncertainty

Unresolved issues are recorded in [assumption-open-questions.md](assumption-open-questions.md) because it can't be decided in requirement. The open questions that are most directly affected with this baseline are:

- Which fields a student must complete when submitting a request. It affects REQ-001 and REQ-002.
- Whether a Resolved request can be undone meaning it can reopened. It affects REQ-003.
- How data provided in requests will be stored. It affects REQ-005

<!--
Do not invent an answer simply because a requirement is incomplete.

If an important fact is unknown, record it in:

/docs/requirements/assumptions-open-questions.md

A known uncertainty is stronger engineering evidence than an unsupported
assumption disguised as a requirement.
-->

## Requirements and Acceptance Criteria


Each requirement REQ is linked to the criteria in [acceptance-criteria.md](acceptance-criteria.md) that shows that it has been met and each criterion references its requirement.

| Requirement | Acceptance Criteria |
|---|---|
| REQ-001 | AC-REQ-001-01, AC-REQ-001-02 |
| REQ-002 | AC-REQ-002-01, AC-REQ-002-02, AC-REQ-002-03, AC-REQ-002-04 |
| REQ-003 | AC-REQ-003-01, AC-REQ-003-02, AC-REQ-003-03, AC-REQ-003-04, AC-REQ-003-05 |
| REQ-004 | AC-REQ-004-01 |
| REQ-005 | AC-REQ-005-01 |
| REQ-006 | AC-REQ-006-01 |
<!--
A requirement establishes an obligation.

An acceptance criterion defines an observable condition demonstrating that
the obligation has been satisfied.

Example:

Requirement:
"The system shall allow a requester to view the current status of each
workflow request they submitted."

Acceptance criterion:
"Given an authenticated requester with an existing workflow request, when the
requester views their request list, then the current status of that request is
displayed."

Maintain traceability in both directions between requirements and acceptance
criteria.
-->

## Expectations

- Maintain unique requirement identifiers.
- Keep requirements current as understanding changes.
- Use professional engineering language.
- Record rationale rather than simply listing features.
- Assign meaningful priorities.
- Link requirements to acceptance criteria.
- Link related decisions, risks, tests, and other evidence when appropriate.
- Record unresolved uncertainty explicitly rather than inventing details.
- Preserve traceability when accepted requirements change, are deferred, or are removed.

<!--
This is a living requirements baseline, not a one-time document.

Before the applicable phase-gate submission:
1. Replace all sample data.
2. Review the document for accuracy and internal consistency.
3. Remove instructional HTML comments.
-->
