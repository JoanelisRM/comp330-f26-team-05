# Test Strategy
**Primary Owner:** Sydel Ugwu  
**Backup Owner:** Nova Kurul  
**Date:** 2026-10-15  
**Tracking Issue:** #18  
**Status:** Proposed, pending team review

This strategy describes planned verification for the CampusConnect Cycle 1 workflow. It does not report completed tests or passing CI checks.

<!--
STARTER KIT GUIDANCE — DELETE BEFORE PHASE-GATE SUBMISSION

This file defines the team's overall approach to testing and verification.

A test strategy should answer questions such as:

- What kinds of defects are we trying to detect?
- What testing levels matter for this system?
- Which risks require deeper verification?
- What should be automated?
- What still requires human review or manual verification?
- How do requirements and acceptance criteria connect to tests?
- How do we handle negative paths, failures, and regression risk?
- What evidence will demonstrate that testing actually occurred?

This file defines the overall testing APPROACH.

Detailed execution planning belongs in:

/docs/testing/test-plan.md

Specific test scenarios belong in:

/docs/testing/test-cases.md

Automated pipeline evidence belongs in:

/docs/testing/ci-evidence.md

IMPORTANT:

- Everything inside HTML comments is Starter Kit guidance.
- Remove ALL instructional comments before the applicable phase-gate submission.
- Examples are guidance only.
- Do not claim a testing practice exists unless the team actually intends to use it.
-->

## Testing Objectives
Verify that students can submit requests, staff can view them, and staff can update their status.

Check that required fields, simulated role permissions, status rules, and stored request data stay consistent across the Presentation, Logic, and Data layers.

For A3, review whether the proposed architecture and contracts support these checks before implementation begins.

<!--
TEAM CONTENT REQUIRED

Describe what the team is trying to accomplish through testing.

Possible objectives include:

- verify requirements;
- verify acceptance criteria;
- detect regressions;
- validate important architecture boundaries;
- verify failure handling;
- verify security behavior;
- verify operational behavior;
- reduce release risk.

Do not simply write:

"Make sure the system works."

Replace this comment with the team's actual testing objectives.
-->

## Testing Principles
- Base expected results on the agreed requirements and acceptance criteria.
- Check successful actions and important failure conditions.
- Confirm rejected actions leave request data unchanged.
- Use automated checks for repeatable behavior and human review for architecture and data-handling decisions.
- Record actual results before marking anything verified.
<!--
TEAM CONTENT REQUIRED

Define the principles that guide testing decisions.

Examples ONLY:

- Test behavior against requirements, not implementation assumptions.
- Verify both successful and unsuccessful paths.
- Automate repeatable verification where practical.
- Use human review where judgment is required.
- Preserve evidence of meaningful test execution.
- Add regression coverage when a significant defect is fixed.

Do not retain generic principles merely because they sound good.

Replace this comment with principles the team actually intends to follow.
-->

## Test Levels

<!--
TEAM CONTENT REQUIRED

Define which test levels are appropriate for this system.

Possible levels include:

- Unit
- Component
- Integration
- Contract
- System
- End-to-End
- Acceptance
- Runtime / Operational

Do NOT include every possible test level automatically.

For each level, explain:

- what it verifies;
- what boundary it exercises;
- what kinds of defects it is intended to detect.

EXAMPLE ONLY:

| Test Level | Purpose | Typical Scope | Primary Defects Detected |
|---|---|---|---|
| Unit | Verify isolated business logic | Individual function or module | Logic and boundary-condition defects |
| Integration | Verify interaction among components | Application Service and Persistence | Interface and state-management defects |

DELETE the example and populate the actual table below.
-->

| Test Level | Purpose | Typical Scope | Primary Defects Detected |
|---|---|---|---|
| Unit | Check individual workflow rules | Required fields, role decisions, and status transitions in the Logic Layer | Missing validation and incorrect permission or transition rules |
| Integration | Check communication between layers | Logic Layer and Data Layer, followed by Presentation Layer integration | Lost updates, incorrect responses, and false success after storage failures |
| Acceptance | Check the complete Cycle 1 workflow | Submit, list, update, and view the updated request | Behavior that does not meet the acceptance criteria |

These levels are planned. Specific test files will be linked when implementation exists.


## Test Types

<!--
TEAM CONTENT REQUIRED

Identify test types relevant to the actual system.

Possible types include:

- Functional
- Negative-path
- Regression
- Security
- Performance
- Reliability
- Usability
- Accessibility
- Compatibility
- Recovery
- Deployment verification

Do not add a category merely because it appears in this list.

EXAMPLE ONLY:

| Test Type | Why It Matters | Where Applied |
|---|---|---|
| Negative-path testing | Ensures invalid input does not create invalid state | Workflow submission and authorization |
| Regression testing | Prevents previously corrected defects from returning | Automated CI suite |

Populate actual testing types below.
-->

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

<!--
TEAM CONTENT REQUIRED

Testing should connect to engineering expectations.

Reference:

/docs/requirements/requirements.md
/docs/requirements/acceptance-criteria.md
/docs/planning/traceability.md

A useful chain is:

Requirement
  ->
Acceptance Criterion
  ->
Test
  ->
Test Result
  ->
Release Evidence

Do not require every low-level unit test to map directly to a requirement.

Traceability matters most for meaningful behavior and release claims.

Replace this comment with the team's actual traceability approach.
-->

## Risk-Based Testing

<!--
TEAM CONTENT REQUIRED

Not every part of the system deserves equal testing depth.

Use risk to guide verification effort.

Consider areas with:

- high impact if incorrect;
- complex logic;
- security significance;
- data integrity consequences;
- external dependencies;
- repeated defect history;
- difficult recovery;
- architectural uncertainty.

Reference:

/docs/planning/risk-register.md

EXAMPLE ONLY:

| Risk / Concern | Additional Testing Approach | Related Evidence |
|---|---|---|
| Unauthorized data access | Multi-user authorization tests and negative-path API verification | R-004, permission-boundaries.md |

DELETE the example and populate actual risk-based testing below.
-->

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
<!--
TEAM CONTENT REQUIRED

Describe how the team will verify both:

POSITIVE PATHS
Expected valid behavior.

NEGATIVE PATHS
Invalid, unauthorized, missing, conflicting, or failed conditions.

Examples of negative conditions may include:

- missing required input;
- malformed data;
- unauthorized operation;
- duplicate request;
- unavailable dependency;
- unknown resource;
- invalid state transition.

Do not test negative conditions merely for volume.

Prioritize those that matter to requirements, security, integrity, and risk.

Replace this comment with the team's actual approach.
-->

## Automation Strategy

<!--
TEAM CONTENT REQUIRED

Explain what the team intends to automate and why.

Possible automated checks include:

- unit tests;
- integration tests;
- contract tests;
- regression tests;
- static checks;
- build verification;
- deployment checks.

Also identify what remains manual and why.

Do not claim "everything is automated" unless that is actually true.
-->

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
<!--
TEAM CONTENT REQUIRED

Describe how the team prevents previously fixed behavior from breaking again.

A practical rule may be:

"When a meaningful defect is fixed, add or update an automated test where
practical so the defect would be detected if it returned."

Reference:

/docs/quality/defect-log.md

Replace this comment with the team's actual regression approach.
-->

## Test Data Strategy
Use synthetic requests and simulated student and staff roles.

Include an empty store, multiple requests, missing fields, and requests in each allowed status. Reset test data between independent scenarios.

Do not use real student information, credentials, or institutional records. Simulated-role tests do not demonstrate real authentication.

<!--
TEAM CONTENT REQUIRED

Describe how test data will be created and controlled.

Consider:

- synthetic data;
- test accounts;
- boundary values;
- invalid data;
- duplicate data;
- security-sensitive cases.

Reference:

/docs/security/data-handling-notes.md

Do not place real sensitive personal information into tests merely because it
is convenient.

Replace this comment with the team's actual test-data approach.
-->

## Environment Strategy

<!--
TEAM CONTENT REQUIRED

Identify where different kinds of tests run.

Examples might include:

- local developer environment;
- CI;
- test deployment;
- staging-like environment;
- deployed course environment.

Explain meaningful differences that could affect results.

Do not create fictional environments.
-->

| Environment | Testing Purpose | Important Differences / Limitations |
|---|---|---|
| Local Java/Maven environment | Build checks, automated tests, and workflow demonstration once implemented | Storage and framework decisions remain open; restart persistence is not promised |
| CI, when configured | Run repeatable build and test checks on proposed changes | A run must reference the tested commit; CI does not replace architecture review |

Cycle 1 does not require a production deployment or external Loyola services.

## AI-Assisted Testing
ChatGPT helped draft this strategy and suggest verification scenarios.

A team member must review the suggestions against the requirements, contracts, and implementation. AI output does not count as a test result or independent human review.

Record this assistance in [ai-use-log.md](../ai/ai-use-log.md), following [ai-policy.md](../ai/ai-policy.md).

<!--
TEAM CONTENT REQUIRED WHEN AI IS USED TO ASSIST TESTING

AI may assist with:

- identifying edge cases;
- proposing test scenarios;
- reviewing test completeness;
- suggesting failure conditions;
- generating initial test code.

AI output is not verification by itself.

The team remains responsible for:

- deciding whether proposed tests are valid;
- understanding generated test code;
- verifying test expectations;
- ensuring tests actually exercise meaningful behavior.

Reference:

/docs/ai/ai-policy.md
/docs/ai/ai-use-log.md

Remove this section if AI-assisted testing is not used.
-->

## Test Evidence
For A3, preserve architecture review evidence in [architecture-review.md](../reviews/architecture-review.md). Record:

- the reviewed commit or document version;
- reviewer and review date;
- findings and their disposition, with rationale;
- follow-up actions, owners, due dates, and issue or PR links.

Review the diagram, ADR-001, component responsibilities, and contracts together. Check matching layer names, permission boundaries, authoritative state, failure behavior, and unresolved decisions.

Nova is the proposed backup reviewer for this work. A completed human review must be recorded before claiming it occurred.

During implementation, add detailed execution steps to [test-plan.md](test-plan.md) and scenarios to [test-cases.md](test-cases.md). Preserve actual results, tested commit IDs, test reports, and relevant CI run links. Record CI evidence in [ci-evidence.md](ci-evidence.md).
<!--
TEAM CONTENT REQUIRED

Define what evidence the team preserves.

Possible evidence includes:

- CI run;
- test report;
- test-case result;
- runtime evidence;
- pull request;
- screenshot when appropriate;
- defect verification.

Do not store large amounts of redundant evidence without purpose.

The evidence should be reviewable enough to support important engineering claims.

Replace this comment with the team's actual evidence approach.
-->

## Known Testing Limitations

For A3, preserve architecture review evidence in [architecture-review.md](../reviews/architecture-review.md). Record:

- the reviewed commit or document version;
- reviewer and review date;
- findings and their disposition, with rationale;
- follow-up actions, owners, due dates, and issue or PR links.

Review the diagram, ADR-001, component responsibilities, and contracts together. Check matching layer names, permission boundaries, authoritative state, failure behavior, and unresolved decisions.

Nova is the proposed backup reviewer for this work. A completed human review must be recorded before claiming it occurred.

During implementation, add detailed execution steps to [test-plan.md](test-plan.md) and scenarios to [test-cases.md](test-cases.md). Preserve actual results, tested commit IDs, test reports, and relevant CI run links. Record CI evidence in [ci-evidence.md](ci-evidence.md).

TEAM CONTENT REQUIRED

Record limitations honestly.

Examples ONLY:

- no sustained-load testing;
- one external integration verified manually;
- browser compatibility limited;
- recovery testing incomplete;
- performance baseline not yet established.

Reference:

/docs/release/known-limitations.md

when the limitation affects a release.
-->

| Limitation | Impact | Planned Improvement | Related Evidence |
|---|---|---|---|
| This document contains planned checks | It does not prove implemented behavior or passing CI | Add execution evidence when implementation exists | Issue #18 |
| Requirements and criteria remain proposed, with formatting and duplicate-content cleanup needed | Expected behavior could be interpreted inconsistently | Reconcile the requirements and criteria before accepting the baseline | R-005 |
| Framework, transport details, and storage implementation remain open | Concrete integration tests cannot yet be finalized | Update the strategy after the decisions are reviewed | ADR-001, Q-003 |
| Reopening and duplicate submissions remain unresolved | Final expectations for these cases are not established | Resolve the questions and update criteria and tests together | Q-002, Q-004 |
| Simulated roles and local execution only | Tests cannot establish production authentication or deployment readiness | Keep verification claims within the Cycle 1 scope | ADR-002 |

<!--
FINAL STARTER KIT CHECK — DELETE BEFORE PHASE-GATE SUBMISSION

Before submission:

1. Define testing objectives that actually matter to the project.
2. Include only test levels and types the team genuinely uses.
3. Connect important verification to requirements and acceptance criteria.
4. Use risk to guide test depth.
5. Include meaningful negative-path testing.
6. Be explicit about what is automated versus manual.
7. Describe regression and test-data practices.
8. Identify real test environments.
9. State testing limitations honestly.
10. Remove ALL instructional HTML comments.

The completed strategy should explain WHY the team tests the way it does, not
serve as a generic catalog of testing terminology.
-->
