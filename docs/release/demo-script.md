# Release Demonstration Script

## Demonstration Identification

**Release / Version:** Cycle 1  
**Git Tag / Commit:** A5-cycle1-release   
**Demo Environment:** Localhost  
**Demo Date:** 11/10/2026   
**Presenter(s):** Joanelis Rivera Matos, Khaja Shabbir Ahmed, Hamza Bashar, Nova Kurul, Sydel Ugwu

## Demonstration Objective

This demonstration is intended to show the test cases based on simulated user data. It's meant to show how the team's main engineering decisions have been implemented so far. The simulated data serves as evidence for successful implementation and opens up potential for future expansion in theoretical later cycles.

## Pre-Demo Checks

| Check | Expected Result | Action if Not Ready |
|---|---|---|
| Relevant evidence accessible | Evidence for engineering decisions is visible and easy to find | Add related evidence before deployment |
| Test data prepared | Demo runs with the correct simulated data | 
| Correct release with appropriate tag deployed | Deployment matches the A5 tag | Correct the tag before deployment |

## Demo Scenario 1 — Student and Staff Simulation

### Purpose

To demonstrate the implementation of the system's requirements, architecture decisions, and testing. 

### Related Requirements
- REQ-001
- REQ-002
- REQ-003
- REQ-004
- REQ-005
- REQ-006

### Related Acceptance Criteria
- AC-REQ-001-01
- AC-REQ-001-02
- AC-REQ-002-01
- AC-REQ-002-02
- AC-REQ-002-03
- AC-REQ-002-04
- AC-REQ-003-01
- AC-REQ-003-02
- AC-REQ-003-03
- AC-REQ-003-04
- AC-REQ-003-05
- AC-REQ-004-01
- AC-REQ-005-01
- AC-REQ-006-01

### Starting State
- The demo can only run locally with the correct Java version and correct Maven version

### Steps

1. Run the program on the local environment or '.\apache-maven-3.10.0\bin\mvn.cmd compile exec:java' in the terminal 

### Expected Result

A list of seven different outputs with varying results based on the following categories:
- Level: INFO for an input that was completed successfully. WARN for an input that was rejected or denied. 
- Event: SUBMISSION_CREATED for a request that was successfully added to the request list. SUBMISSION_REJECTED for a request that was rejected. ROLE_DENIED for an action from an unauthorized role. REQUEST_LIST_VIEWED for an action performed by a staff role in regards to the request list. STATUS_UPDATED for a request's status that transitioned from OPEN -> IN_PROGRESS -> RESOLVED. TRANSITION_REJECTED for a request's status to transition in an order that isn't OPEN -> IN_PROGRESS -> RESOLVED.
- Role: STUDENT or STAFF
- Request ID: REQ-1001
- Outcome: SUCCESS for creating requests, viewing the request list, and updating the request's status. REJECTED for requests that couldn't be added to the list and incorrectly updating a request's status. DENIED for an action from a user with an unauthorized role. 
- Details: Gives reasoning for the outcomes of each test.

### Evidence
- All seven automated tests that utilize synthetic data

### Known Limitations
- Limited amount of simulated tests
- All user data is synthetic 
- Reviewers cannot input their own data for testing

## Negative / Failure Scenario

- Student submits request with missing fields
- Student attempts to update the status of a request
- Staff tries to update a request's status from RESOLVED to OPEN

## Architecture / Engineering Evidence Callout

| Evidence | What It Demonstrates | When to Show |
|---|---|---|
| Automated tests | How the system functions with different user inputs | During the demo |
| ADR/API Contract | How the system functions logically with each layer | During the demo |

## Known Limitations to Acknowledge

| Limitation ID | What to State During Demo |
|---|---|
| LIM-01 | Requests are only kept in memory, which means everything is lost when the application stops or restarts. |
| LIM-02 | There is no real login. The role is whatever the client sends in the X-Simulated-Role header, so anyone can act as staff. |
| LIM-03 | The server only listens on 127.0.0.1. There is no hosted deployment. |
| LIM-04 | There is no browser page. The Presentation Layer is a JSON HTTP API plus a console demo runner; there is no submission form or staff list page. |
| LIM-05 | Duplicate submissions are not detected; the same request sent twice is stored twice. |
| LIM-06 | Students cannot view the status of requests they submitted. |
| LIM-07 | Students cannot edit or cancel a request, although the Q-006 answer says they may (edit while Open, cancel while Open or In Progress). No requirement, contract, task or estimate exists for it. |
| LIM-08 | Only the current status and the time of the last change are kept. Who changed a status is not recorded, and there is no status history. |
| LIM-09 | Category is free text; any non-blank value is accepted. |
| LIM-10 | A resolved request cannot be reopened, and a status cannot move backwards (Open → In Progress → Resolved only). |

## Demo Evidence / Results

| Scenario | Result | Evidence / Observation | Follow-Up |
|---|---|---|---|
| Student creates new request | Passed | Expected result displayed when run | None |
| Student fails to submit request due to missing fields | Passed | Expected result displayed when run | None |
| Student fails to update a request's status | Passed | Expected result displayed when run | None |
| Staff can view the request list | Passed | Expected result displayed when run | None |
| Staff changes a request's status from OPEN to IN_PROGRESS | Passed | Expected result displayed when run | None |
| Staff changes a request's status from IN_PROGRESS to RESOLVED | Passed | Expected result displayed when run | None |
| Staff fails to change a request's status from RESOLVED to OPEN | Passed | Expected result displayed when run | None |

## Recovery / Backup Demo Plan

- Verified local environment
- Test results

## Presentation Index

Related Evidence:
- docs/release/known-limitations.md
- docs/requirements/requirements.md
- docs/requirements/acceptance-criteria.md
- docs/architecture/api-contracts.md
- docs/architecture/architecture.md
- docs/architecture/component-responsibilities.md
- docs/planning/scope.md
- src/main/java/edu/luc/comp330/campusconnect
- src/test/java/edu/luc/comp330/campusconnect
