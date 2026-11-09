# Release Notes

<!--
STARTER KIT GUIDANCE — DELETE BEFORE PHASE-GATE SUBMISSION

This file describes the release being presented or delivered.

Release notes should tell a reviewer:

- what release this is;
- what capabilities are included;
- what meaningful changes were made;
- what was verified;
- what defects were resolved;
- what remains limited or incomplete; and
- where supporting engineering evidence can be found.
-->

## Release Identification

**Release Date:**  11/09/2026 

**Release Gate / Milestone:** Cycle 1 

**Git Tag / Commit:**  A5-cycle1-release 

**Deployment / Environment:** Localhost 

## Release Summary

The purpose of this first release is to present a simple demo of the CampusConnect workflow system. This demo primarily serves to showcase the current architecture: the presentation layer, logic layer, and the data layer. Users won't be able to enter their down data, instead they'll view the outputs listing out results of some cases based on pre-written synthetic data. 

## Included Capabilities

| Capability | Related Requirements | Release Status |
|---|---|---|
| Simulated student can submit a synthetic support request | REQ-001, REQ-004, REQ-005, REQ-006 | Included |
| Simulated staff can view the list of submitted support requests | REQ-002, REQ-006 | Included |
| Simulated staff can update a request's status along OPEN -> IN_PROGRESS -> RESOLVED | REQ-003, REQ-005, REQ-006 | Included |

## Significant Changes

<!--
TEAM CONTENT REQUIRED

Summarize meaningful changes since the previous release or baseline.

Possible categories include:

- new capability;
- changed behavior;
- architectural change;
- interface change;
- security improvement;
- reliability improvement;
- operational improvement;
- documentation or evidence improvement.

Do not reproduce the Git commit log.

EXAMPLE ONLY:

| Area | Change | Why It Matters | Related Evidence |
|---|---|---|---|
| Workflow validation | Added server-side required-field validation | Prevents invalid workflow records from being created | REQ-004, AC-REQ-004-02, PR #21 |

Populate actual changes below.
-->

| Area | Change | Why It Matters | Related Evidence |
|---|---|---|---|
| AI Evidence | Added new AI-Use logs | Records AI-assisted work with human verification | PR #77 |

## Verification Summary

| Verification Area | Result | Evidence |
|---|---|---|
| Student submission acceptance criteria | Passed | AC-REQ-001-01, AC-REQ-001-02, automated tests |
| Request list access and contents acceptance criteria | Passed | AC-REQ-002-01, AC-REQ-002-02, AC-REQ-002-03, AC-REQ-002-04, automated tests |
| Request status verification | Passed | AC-REQ-003-01, AC-REQ-003-02, AC-REQ-003-03, AC-REQ-003-04, AC-REQ-003-05, automated tests | 
| Synthetic data verification | Passed | AC-REQ-004-01, automated tests, code review |
| Request list/request status update verification | Passed | AC-REQ-005-01, automated tests |
| Role logic verification | Passed | AC-REQ-006-01, code review |

## Known Limitations

<!--
TEAM CONTENT REQUIRED

Summarize only the most important limitations affecting this release.

The authoritative detailed record is:

/docs/release/known-limitations.md

Do not duplicate the entire limitations file.

Reference limitation IDs where possible.
-->

| Limitation ID | Summary | Impact |
|---|---|---|
|  |  |  |

## Deployment / Operational Notes

<!--
TEAM CONTENT REQUIRED WHEN THE RELEASE IS DEPLOYED OR OPERATED

Document release-specific operational information that a reviewer or operator
needs to know.

Examples might include:

- deployment completed successfully;
- configuration change required;
- database migration performed;
- service restart required;
- first-run behavior;
- environment-specific limitation.

Do not duplicate the complete runbook.

Reference:

/docs/operations/runbook.md

Remove this section if there is no release-specific operational information.
-->

## Evidence References

| Evidence | Location / Reference | Purpose |
|---|---|---|
| Capabilities of the system | Requirements, Acceptance Criteria | Reviewers are aware of how the program functions |
| System behavior | ADR | Reviewers can view how the three main layers of the system are meant to work together |
| Requirement verification | Test Evidence, Traceability | Reviewers can check records of the tests and checks conducted for requirement verification |
