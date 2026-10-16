# API and Interface Contracts

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup:** TBD
**Gate:** A3 — Architecture and Design · **Status:** Proposed, pending team review
**Tracking issue:** #12

This file defines the contracts between the layers of the CampusConnect Cycle 1 vertical slice: a student submits a support request, staff view the list of requests, and staff move a request through Open → In Progress → Resolved.

**Component names.** This file uses the layer names from the Architectural Structure section of [`architecture.md`](architecture.md): **Presentation Layer**, **Logic Layer**, and **Data Layer**. If the team settles on different names (see review finding ARF-003 on PR #21), this file will be updated to match.

**What is settled vs. proposed.** The behavior in each contract (inputs, outputs, failures, role rules) comes from the requirements and acceptance criteria and is the part the team must agree on. HTTP paths, status codes, and JSON field names are **proposed** so the team has something concrete to review; they may change when ADR-001 (tech stack) is written.

## Contract Summary

| ID | Consumer | Provider | Purpose | Interface / Protocol | Related Requirements | Status |
|---|---|---|---|---|---|---|
| API-001 | Presentation Layer | Logic Layer | Student submits a support request | HTTP / JSON on localhost (proposed) | REQ-001, REQ-004, REQ-005 | Proposed |
| API-002 | Presentation Layer | Logic Layer | Staff view the list of submitted requests | HTTP / JSON on localhost (proposed) | REQ-002, REQ-005 | Proposed |
| API-003 | Presentation Layer | Logic Layer | Staff change a request's status | HTTP / JSON on localhost (proposed) | REQ-003, REQ-005 | Proposed |
| API-004 | Logic Layer | Data Layer | Store and retrieve support requests | Internal Java interface | REQ-001, REQ-002, REQ-003, REQ-005 | Proposed |
| API-005 | Logic Layer (all operations) | Logic Layer role check | Decide what the simulated role may do | Internal Java interface | REQ-002, REQ-003, REQ-006 | Proposed |

There are no external-system contracts in Cycle 1. The system runs locally, uses synthetic data, and has no real login or third-party service (ADR-002: TD-01, TD-02, TD-06).

## Detailed Contracts

### API-001 — Submit Support Request

**Consumer:** Presentation Layer (student view)
**Provider:** Logic Layer
**Purpose:** Let a student create a new support request.
**Related Requirements:** REQ-001, REQ-004, REQ-005
**Status:** Proposed

#### Interaction

`POST /api/requests` (proposed)

The student fills in the submission form; the Presentation Layer sends the fields and the current simulated role to the Logic Layer.

#### Request / Input

| Field | Required | Description |
|---|---|---|
| `category` | Yes | Type of help needed. The list of allowed categories is not yet defined (see C-Q1). |
| `title` | Yes | Short title of the request. |
| `description` | Yes | What the student needs help with. Synthetic content only (REQ-004). |
| Simulated role | Yes | Must be `student`. How the role is sent is not yet decided (see API-005 and C-Q2). |

A field counts as missing if it is absent, empty, or only whitespace.

#### Response / Output

On success the Logic Layer returns the created request:

| Field | Description |
|---|---|
| `id` | Unique identifier assigned by the system (REQ-001). |
| `status` | Always `Open` for a new request (ASM-005). |
| `submittedAt` | Submission time recorded by the system, not by the client. |
| `category`, `title`, `description` | As stored. |

Proposed HTTP status: `201 Created`.

#### Failure Behavior

| Condition | Result | Proposed status | Evidence |
|---|---|---|---|
| One or more required fields missing | No request is created; the response lists **every** missing field by name. | `400` | AC-REQ-001-02 |
| Role is not `student` | No request is created. | `403` | REQ-001 (see C-Q3) |
| Request could not be stored | No request is created and the client is told it failed. The system must never report success for a request it did not save. | `500` | REQ-005 |
| Same request submitted twice | Not handled in Cycle 1; both are created. | — | Q-004 (open) |

#### Authentication / Authorization

No real authentication in Cycle 1 (ASM-001). The simulated role is checked by the Logic Layer through API-005, never by the Presentation Layer alone.

#### Contract Rules

- The Logic Layer assigns `id`, `status`, and `submittedAt`. Values for these sent by the client are ignored.
- A success response means the request is stored and will appear in API-002 (AC-REQ-002-04).
- Invalid input must not create a partial or empty request.

#### Verification

AC-REQ-001-01, AC-REQ-001-02, AC-REQ-002-04 — planned unit tests (TASK-106) and the end-to-end demonstration (TASK-109).

#### Notes / Open Questions

C-Q1, C-Q2, C-Q3, Q-004.

---

### API-002 — List Submitted Requests

**Consumer:** Presentation Layer (staff view)
**Provider:** Logic Layer
**Purpose:** Let staff see every submitted request so they can act on it.
**Related Requirements:** REQ-002, REQ-005
**Status:** Proposed

#### Interaction

`GET /api/requests` (proposed)

#### Request / Input

The simulated role only. No filtering, search, or sorting in Cycle 1 (ADR-002, TD-04).

#### Response / Output

A list in which each item contains:

| Field | Description |
|---|---|
| `id` | Request identifier. |
| `title` | Request title. |
| `submittedAt` | Submission time. |
| `status` | Current status: `Open`, `In Progress`, or `Resolved`. |

REQ-002 also mentions a "summary" field, but AC-REQ-002-01 does not. This needs a decision (see C-Q4).

Proposed HTTP status: `200 OK`.

#### Failure Behavior

| Condition | Result | Proposed status | Evidence |
|---|---|---|---|
| No requests exist | An empty list, not an error. The Presentation Layer shows a "no requests" message. | `200` | AC-REQ-002-02 |
| Role is `student` | Access denied and **no request data** in the response. | `403` | AC-REQ-002-03 |
| Requests could not be read | An error, not an empty list. An empty list would wrongly tell staff there is nothing to do. | `500` | REQ-005 |

#### Authentication / Authorization

Staff role only, checked in the Logic Layer through API-005. Hiding the staff page in the user interface is not enough on its own.

#### Contract Rules

- Every stored request is returned, including ones submitted moments earlier (AC-REQ-002-04).
- Each status shown is the current stored status, including changes made through API-003 (AC-REQ-003-05).
- The order of the list is not part of the contract in Cycle 1.

#### Verification

AC-REQ-002-01, AC-REQ-002-02, AC-REQ-002-03, AC-REQ-002-04, AC-REQ-003-05 — planned tests (TASK-106) and demonstration (TASK-109).

#### Notes / Open Questions

C-Q4.

---

### API-003 — Change Request Status

**Consumer:** Presentation Layer (staff view)
**Provider:** Logic Layer
**Purpose:** Let staff move a request forward through its lifecycle.
**Related Requirements:** REQ-003, REQ-005
**Status:** Proposed

#### Interaction

`PATCH /api/requests/{id}/status` (proposed)

#### Request / Input

| Field | Required | Description |
|---|---|---|
| `id` (in path) | Yes | The request to change. |
| `status` | Yes | The requested new status. |
| Simulated role | Yes | Must be `staff`. |

#### Response / Output

The updated request with its new `status`. Proposed HTTP status: `200 OK`.

#### Allowed Transitions

| From | To | Allowed? |
|---|---|---|
| Open | In Progress | Yes |
| In Progress | Resolved | Yes |
| Any other combination (for example Open → Resolved, Resolved → Open, In Progress → Open) | — | **No** |

Based on ASM-006. If Q-002 decides that resolved requests can be reopened, this table, REQ-003, and AC-REQ-003-03 must change together.

#### Failure Behavior

| Condition | Result | Proposed status | Evidence |
|---|---|---|---|
| Transition not in the table above | Rejected; status unchanged; the response says why (for example "Open can only move to In Progress"). | `409` | AC-REQ-003-03 |
| Role is `student` | Rejected; status unchanged. | `403` | AC-REQ-003-04 |
| `status` value is not one of the three known statuses | Rejected; status unchanged. | `400` | REQ-003 |
| No request with that `id` | Rejected. | `404` | — |
| Change could not be saved | Error returned; the system must not report the new status. | `500` | REQ-005 |

#### Authentication / Authorization

Staff role only, checked in the Logic Layer through API-005.

#### Contract Rules

- **The transition rule is enforced only in the Logic Layer.** The Presentation Layer may hide buttons for invalid moves, but that is a convenience, not the enforcement.
- A rejected change leaves the request exactly as it was.
- After a successful change, API-002 shows the new status (AC-REQ-003-05).
- Each status change is logged (TASK-108). Whether the log records *who* made the change is still open (Q-007).

#### Verification

AC-REQ-003-01 through AC-REQ-003-05 — planned unit tests for every allowed and rejected transition (TASK-106) and demonstration (TASK-109).

#### Notes / Open Questions

Q-002, Q-007, C-Q5.

---

### API-004 — Request Storage (Logic Layer → Data Layer)

**Consumer:** Logic Layer
**Provider:** Data Layer
**Purpose:** Keep support requests and their current status.
**Related Requirements:** REQ-001, REQ-002, REQ-003, REQ-005
**Status:** Proposed

#### Interaction

An internal Java interface (proposed name `RequestRepository`) that the Logic Layer calls. The Presentation Layer never calls the Data Layer directly.

#### Operations

| Operation | Input | Output |
|---|---|---|
| Save a new request | A complete, already-validated request | The stored request with its `id` |
| List all requests | — | Every stored request (possibly empty) |
| Find one request | `id` | The request, or "not found" |
| Update status | `id`, new status | The updated request, or "not found" |

#### Failure Behavior

If a save or update fails, the Data Layer reports the failure to the Logic Layer instead of failing silently, so the Logic Layer can return the `500` cases in API-001 and API-003.

#### Contract Rules

- **The Data Layer stores; it does not decide.** Validation, role checks, and the transition rule all live in the Logic Layer.
- Data saved stays available for as long as the application is running (REQ-005, AC-REQ-005-01).
- Whether data survives a restart depends on the storage decision (Q-003). The interface stays the same either way, so the storage can change later without changing the Logic Layer.
- Only synthetic data is stored (REQ-004).

#### Verification

AC-REQ-005-01 — planned integration test (TASK-106 / TASK-109).

#### Notes / Open Questions

Q-003.

---

### API-005 — Simulated Role Check (inside the Logic Layer)

**Consumer:** Every Logic Layer operation (API-001, API-002, API-003)
**Provider:** One role-check component in the Logic Layer
**Purpose:** Keep every role decision in one place.
**Related Requirements:** REQ-002, REQ-003, REQ-006
**Status:** Proposed

#### Interaction

Each operation asks the role-check component one question: "may this role perform this action?" No other code compares role values directly.

#### Rules

| Action | Student | Staff |
|---|---|---|
| Submit a request (API-001) | Allowed | Not allowed (see C-Q3) |
| List all requests (API-002) | **Denied** | Allowed |
| Change a status (API-003) | **Denied** | Allowed |

#### Contract Rules

- All role logic lives in this one component (REQ-006, AC-REQ-006-01), so simulated roles can later be replaced by real login without changes across the codebase.
- An unknown or missing role is treated as having no permissions.
- **Known limitation:** in Cycle 1 the role is chosen by the user, not proven. This is acceptable only because the data is synthetic and the system runs locally (ASM-001, ADR-002 TD-02). It must not be used with real data.

#### Verification

AC-REQ-002-03, AC-REQ-003-04 (tests); AC-REQ-006-01 (code review).

#### Notes / Open Questions

C-Q2.

## Data Contract Guidance

Field tables in this file are the authoritative contract for Cycle 1. No OpenAPI or other schema file exists yet. If one is added, this file will link to it rather than repeat every field.

**Proposed common error shape** for all API-001 to API-003 failures:

| Field | Description |
|---|---|
| `error` | Short machine-readable code, for example `MISSING_FIELDS`, `FORBIDDEN`, `INVALID_TRANSITION`, `NOT_FOUND`. |
| `message` | Human-readable explanation shown to the user. |
| `missingFields` | Only for `MISSING_FIELDS`: the list of missing field names. |

## Failure Contracts

Shared rules for every contract in this file:

- **No false success.** A success response is sent only after the change is actually stored.
- **No partial changes.** A rejected or failed operation leaves stored data exactly as it was.
- **Errors are not empty results.** A storage failure is reported as an error, never as an empty list.
- **Denied means no data.** A denied request returns no request content.

Not covered in Cycle 1: duplicate submissions (Q-004) and two staff members changing the same request at the same moment (C-Q5).

## Security Boundaries

| Boundary | Rule | Evidence |
|---|---|---|
| Presentation → Logic | The Logic Layer re-checks the role and all input. Nothing the Presentation Layer hides or disables counts as enforcement. | API-005 |
| Logic → Data | Only the Logic Layer talks to the Data Layer. | API-004 |
| Data content | Synthetic data only; no real names, student IDs, emails, or credentials. | REQ-004, AC-REQ-004-01 |
| Identity | Roles are simulated and unverified. Accepted Cycle 1 technical debt. | ASM-001, ADR-002 TD-02 |

## AI and Tool Boundary

**Cycle 1 has no AI in the product.** AI-assisted categorization is deferred until there is a validation plan and a human-approval step (ADR-002, TD-05; R-004). No contract in this file calls an AI model or external tool.

As the assignment allows, the boundary questions are applied below to the system's one privileged action, **changing a request's status** (API-003):

| Boundary | Cycle 1 answer |
|---|---|
| Read | Staff can read all requests. Students cannot read the staff list. |
| Infer | The system draws no conclusions on its own; staff choose every status change. |
| Call | No external APIs or services are called. |
| Change | Only `status` can change, only forward one step, only by staff. |
| Approve | The staff member making the change is the human decision-maker; no automatic changes. |
| Observe | Rejected transitions and denied requests return clear errors and are visible in the log (TASK-108). |
| Log | Status changes and failed submissions are logged (TASK-108). Recording *who* changed a status is open (Q-007). |
| Recover | There is no "undo" in Cycle 1; whether a resolved request can be reopened is open (Q-002). Synthetic data can be reset by restarting with fresh data. |

## Contract Compatibility and Change

| Effective Gate | Contract | Change | Compatibility Impact | Related Evidence |
|---|---|---|---|---|
| A3 | API-001 to API-005 | Initial contracts replace the starter-kit samples | None; no implementation exists yet | Issue #12 |

Before changing an accepted contract, the person making the change updates both the consumer and provider sides, the affected acceptance criteria, and the related tests in the same pull request.

## Verification and Traceability

| Contract | Requirement | Acceptance Criteria | Planned Verification |
|---|---|---|---|
| API-001 | REQ-001 | AC-REQ-001-01, AC-REQ-001-02 | TASK-106 unit tests; TASK-109 demo |
| API-002 | REQ-002 | AC-REQ-002-01 to AC-REQ-002-04 | TASK-106; TASK-109 |
| API-003 | REQ-003 | AC-REQ-003-01 to AC-REQ-003-05 | TASK-106; TASK-109 |
| API-004 | REQ-005 | AC-REQ-005-01 | Integration test; TASK-109 |
| API-005 | REQ-006 | AC-REQ-006-01, AC-REQ-002-03, AC-REQ-003-04 | Code review; TASK-106 |

No contract is marked Implemented or Verified; there is no code yet.

## Open Contract Questions

Questions marked "New" should be added to [`assumptions-open-questions.md`](../requirements/assumptions-open-questions.md) so they are tracked in one place.

| ID | Question | Affects | Status |
|---|---|---|---|
| C-Q1 | What are the allowed request categories? | API-001 | New |
| C-Q2 | How does the Presentation Layer send the simulated role (for example a role picker that sets a header)? | API-001 to API-005 | New; depends on ADR-001 |
| C-Q3 | Can staff also submit requests, or only students? | API-001, API-005 | New |
| C-Q4 | Does the staff list include a "summary"? REQ-002 says yes; AC-REQ-002-01 does not. | API-002 | New |
| C-Q5 | What happens if two staff change the same request at the same time? | API-003 | New; likely deferred |
| Q-002 | Can a resolved request be reopened? | API-003 | Existing, open |
| Q-003 | How is data stored, and must it survive a restart? | API-004 | Existing, open |
| Q-004 | Should duplicate submissions be prevented? | API-001 | Existing, open |
| Q-007 | Should the system record who changed a status? | API-003 | Existing, open |

## Expectations

- Use stable identifiers for significant interface contracts.
- Use component names consistent with the architecture.
- Identify both consumer and provider.
- Describe meaningful input and output behavior.
- Document important failure behavior.
- Make security expectations explicit where relevant.
- Link contracts to requirements and acceptance criteria.
- Reference authoritative schemas rather than creating unnecessary duplicate definitions.
- Update both sides of a contract when it changes.
- Add verification evidence as implementation matures.
- Record unresolved behavior explicitly rather than inventing contract details.