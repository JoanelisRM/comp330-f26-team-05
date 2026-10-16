# Test Strategy
**Primary Owner:** Sydel Ugwu  
**Backup Owner:** Nova Kurul  
**Date:** 2026-10-15  
**Tracking Issue:** #18  
**Status:** Proposed, pending team review

This strategy describes planned verification for the CampusConnect Cycle 1 workflow. It does not report completed tests or passing CI checks.



## Testing Objectives
Verify that students can submit requests, staff can view them, and staff can update their status.

Check that required fields, simulated role permissions, status rules, and stored request data stay consistent across the Presentation, Logic, and Data layers.

For A3, review whether the proposed architecture and contracts support these checks before implementation begins.



## Testing Principles
- Base expected results on the agreed requirements and acceptance criteria.
- Check successful actions and important failure conditions.
- Confirm rejected actions leave request data unchanged.
- Use automated checks for repeatable behavior and human review for architecture and data-handling decisions.
- Record actual results before marking anything verified.


## Test Levels


| Test Level | Purpose | Typical Scope | Primary Defects Detected |
|---|---|---|---|
| Unit | Check individual workflow rules | Required fields, role decisions, and status transitions in the Logic Layer | Missing validation and incorrect permission or transition rules |
| Integration | Check communication between layers | Logic Layer and Data Layer, followed by Presentation Layer integration | Lost updates, incorrect responses, and false success after storage failures |
| Acceptance | Check the complete Cycle 1 workflow | Submit, list, update, and view the updated request | Behavior that does not meet the acceptance criteria |

These levels are planned. Specific test files will be linked when implementation exists.


## Test Types



| Test Type | Why It Matters | Where Applied |
|---|---|---|
| Functional | Confirms the main workflow works as expected | Submission, staff list, and status updates |
| Negative-path | Protects request data when an action fails | Missing fields, denied actions, invalid transitions, and storage errors |
| Permission | Checks the limits of simulated roles | Staff list and status changes |
| Regression | Detects the return of previously fixed defects | Automated checks added or updated with relevant fixes |

## Requirements and Acceptance-Criteria Coverage
The following checks are planned against the current proposed criteria in [acceptance-criteria.md](../requirements/acceptance-criteria.md).

API references describe the proposed contracts and must be confirmed during review.

| Requirement / Criteria | Planned Verification | Related Boundary |
|---|---|---|
| REQ-001; AC-REQ-001-01 | Submit category, title, and description; confirm a unique identifier, submission time, and confirmation are returned | API-001, API-004 |
| REQ-001; AC-REQ-001-02 | Omit each required field; confirm missing fields are identified and no request is created | API-001 |
| REQ-002; AC-REQ-002-01 and AC-REQ-002-02 | Check that staff see every request with the required fields; check the empty-list message when no requests exist | API-002, API-004 |
| REQ-002; AC-REQ-002-03 | Attempt to open the staff list as a student; confirm denial without request data | API-002, API-005 |
| REQ-003; AC-REQ-003-01 and AC-REQ-003-02 | As staff, change Open to In Progress, then In Progress to Resolved | API-003 |
| REQ-003; AC-REQ-003-03 and AC-REQ-003-04 | Attempt skipped or backward transitions and student status changes; confirm rejection and unchanged status | API-003, API-005 |
| REQ-002, REQ-003, REQ-005; AC-REQ-002-04, AC-REQ-003-05, AC-REQ-005-01 | Submit and update a request, then check that the staff list shows its latest state while the application remains running | API-001 through API-004 |
| REQ-004; AC-REQ-004-01 | Inspect fixtures, stored data, logs, and repository content for synthetic data only | All layers |
| REQ-006; AC-REQ-006-01 | Review the code to confirm all role-dependent decisions are located in one identified part of the Logic Layer | API-005 |

Maintain the links from requirements to criteria, tests, results, and defects in [traceability.md](../planning/traceability.md).

## Risk-Based Testing

| Risk / Concern | Additional Testing Approach | Related Evidence |
|---|---|---|
| Unclear or inconsistent requirements | Review field names, role names, unique criterion IDs, and expected results before implementing tests | R-005 |
| Limited experience with Java and Maven | Confirm another team member can build the project and run the configured tests using documented steps | R-007 |
| Scope growth and limited capacity | Prioritize submission, staff listing, status changes, and their important failure paths | R-003, R-008 |
| Unreviewed AI-generated material | Have a human check suggested scenarios against the actual requirements and contracts | R-004 |
| Incorrect permissions or lost updates | Check denied actions, unchanged state after rejection, and the latest stored status | REQ-002, REQ-003, REQ-005, REQ-006 |

Risk references come from [risk-register.md](../planning/risk-register.md).


## Positive and Negative Testing
The main positive flow is student submission, staff listing, and staff updates through Open → In Progress → Resolved.

Negative checks include missing required fields, student access to staff operations, and invalid status transitions.

Additional proposed contract checks include unknown request IDs, missing or unknown roles, whitespace-only required fields, and storage failures. Confirm these expectations during contract review before treating them as accepted behavior.

A storage failure must not appear as a successful submission or update. A failed list read must not appear as a valid empty list.

Duplicate-submission behavior remains open under Q-004.


## Automation Strategy



| Verification Area | Automated / Manual / Mixed | Reason |
|---|---|---|
| Validation, role rules, and status transitions | Planned automated | Repeatable rules need consistent checks |
| Storage interaction and failure handling | Planned automated | Checks state changes and unsuccessful operations |
| Complete user workflow | Planned mixed | Automated integration checks plus a visible demonstration |
| Architecture and contract alignment | Manual | Requires judgment about responsibilities and unresolved decisions |
| Synthetic data and centralized role checks | Manual review, supported by automated checks where useful | Requires inspection of code and data handling |

Use the Maven test command once the project and tests are configured. Record the actual command and result rather than assuming the build or tests pass.

## Regression Strategy
When a meaningful defect is fixed, add or update a check that would detect it again.

Run the relevant tests after changes to validation, permissions, status transitions, storage behavior, or layer interfaces. Record the defect and its verification evidence in [defect-log.md](../quality/defect-log.md).

## Test Data Strategy
Use synthetic requests and simulated student and staff roles.

Include an empty store, multiple requests, missing fields, and requests in each allowed status. Reset test data between independent scenarios.

Do not use real student information, credentials, or institutional records. Simulated-role tests do not demonstrate real authentication.


## Environment Strategy



| Environment | Testing Purpose | Important Differences / Limitations |
|---|---|---|
| Local Java/Maven environment | Build checks, automated tests, and workflow demonstration once implemented | Storage and framework decisions remain open; restart persistence is not promised |
| CI, when configured | Run repeatable build and test checks on proposed changes | A run must reference the tested commit; CI does not replace architecture review |

Cycle 1 does not require a production deployment or external Loyola services.

## AI-Assisted Testing
ChatGPT helped draft this strategy and suggest verification scenarios.

A team member must review the suggestions against the requirements, contracts, and implementation. AI output does not count as a test result or independent human review.

Record this assistance in [ai-use-log.md](../ai/ai-use-log.md), following [ai-policy.md](../ai/ai-policy.md).


## Test Evidence
For A3, preserve architecture review evidence in [architecture-review.md](../reviews/architecture-review.md). Record:

- the reviewed commit or document version;
- reviewer and review date;
- findings and their disposition, with rationale;
- follow-up actions, owners, due dates, and issue or PR links.

Review the diagram, ADR-001, component responsibilities, and contracts together. Check matching layer names, permission boundaries, authoritative state, failure behavior, and unresolved decisions.

Nova is the proposed backup reviewer for this work. A completed human review must be recorded before claiming it occurred.

During implementation, add detailed execution steps to [test-plan.md](test-plan.md) and scenarios to [test-cases.md](test-cases.md). Preserve actual results, tested commit IDs, test reports, and relevant CI run links. Record CI evidence in [ci-evidence.md](ci-evidence.md).


## Known Testing Limitations




| Limitation | Impact | Planned Improvement | Related Evidence |
|---|---|---|---|
| This document contains planned checks | It does not prove implemented behavior or passing CI | Add execution evidence when implementation exists | Issue #18 |
| Requirements and criteria remain proposed, with formatting and duplicate-content cleanup needed | Expected behavior could be interpreted inconsistently | Reconcile the requirements and criteria before accepting the baseline | R-005 |
| Framework, transport details, and storage implementation remain open | Concrete integration tests cannot yet be finalized | Update the strategy after the decisions are reviewed | ADR-001, Q-003 |
| Reopening and duplicate submissions remain unresolved | Final expectations for these cases are not established | Resolve the questions and update criteria and tests together | Q-002, Q-004 |
| Simulated roles and local execution only | Tests cannot establish production authentication or deployment readiness | Keep verification claims within the Cycle 1 scope | ADR-002 |


