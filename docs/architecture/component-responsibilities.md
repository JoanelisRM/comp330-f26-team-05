# Component Responsibilities

**Owner:** Khaja Shabbir Ahmed · **Backup:** not yet assigned · **Tracking issue:** #11
**Status:** Proposed — pending team review · **Last updated:** 2026-10-21
**Based on:** ADR-001 (local Java layered architecture), API-001..API-005 (`api-contracts.md`), the architecture diagram, and REQ-001..REQ-006, as of `main` 6c0c830.

This file answers one question: **"Which component is responsible for this behavior, state, or decision?"** Component names match ADR-001, the API contracts and the diagram. There is **no Identity Provider and no external database** in Cycle 1 (ASM-001, ADR-002 TD-02, Q-003).

## Component Responsibility Matrix

| Component | Primary Responsibilities | Does Not Own | Depends On | Used By | Related Requirements |
|---|---|---|---|---|---|
| **Presentation Layer** (student view, staff view) | Collect request fields; show the staff request list and status controls; show results, validation errors and denials; send the user's simulated role with each action | Validation rules, role decisions, the status transition rule, storage, IDs or timestamps | Logic Layer (API-001..003) | Student, Staff | REQ-001, REQ-002, REQ-003 |
| **Logic Layer — Request Workflow** | Validate submissions; assign `id`, `Open` status and `submittedAt`; enforce Open → In Progress → Resolved; reject everything else; report success only after storage succeeds; emit log events | Screen layout, how data is physically stored, role comparisons (asks Role Check) | Role Check (API-005), Data Layer (API-004), Logging | Presentation Layer | REQ-001, REQ-002, REQ-003, REQ-005 |
| **Logic Layer — Role Check** | The **only** place that decides whether a simulated role may submit, list or change status; treats unknown/missing roles as no permission | Proving identity (roles are unverified in Cycle 1), workflow rules, storage | — | Request Workflow | REQ-002, REQ-003, REQ-006 |
| **Data Layer** (`RequestRepository`, in-memory) | Store and return synthetic requests and their current status while the app runs; report save/update failures explicitly | Validation, role checks, transition rules, IDs, anything the user sees | — | Request Workflow only | REQ-004, REQ-005 |
| **Logging** (planned, TASK-108) | Record failed submissions, denied actions and status changes so problems can be diagnosed | Deciding anything; storing request data; real student data or credentials | — | Request Workflow | REQ-003, REQ-004 |

## Detailed Component Responsibilities

### Presentation Layer

**Purpose** — The only part of the system people interact with: a student view to submit a request, and a staff view to list requests and change status.

**Owns**
- the submission form (category, title, description) and the staff list and status controls;
- choosing and sending the simulated role (mechanism still open, C-Q2);
- turning Logic Layer responses into messages (e.g. listing every missing field, "no requests yet", "Open can only move to In Progress").

**Does Not Own**
- deciding whether input is valid or a role is allowed — it may pre-check or hide buttons for convenience, but **the Logic Layer always re-checks** (API-003 contract rule);
- creating IDs, statuses or timestamps;
- calling the Data Layer.

**Inputs** — user actions; Logic Layer responses and the common error shape (`error`, `message`, `missingFields`).
**Outputs** — API-001 submit, API-002 list, API-003 status change, each with the simulated role.

**Failure Responsibilities** — Show every error the Logic Layer returns. Never show a request as submitted or a status as changed unless the Logic Layer confirmed it. Show a storage error as an error, not as an empty list.

**Related Evidence** — REQ-001..003 · AC-REQ-001-02, AC-REQ-002-02 · API-001..003 · ADR-001 · TASK-103, TASK-104, TASK-105

### Logic Layer — Request Workflow

**Purpose** — Holds the rules of the Cycle 1 workflow so they can be tested without the user interface.

**Owns**
- required-field validation (absent, empty or whitespace = missing) and listing every missing field;
- assigning `id`, initial status `Open` and `submittedAt`, ignoring any values the client sends;
- the transition rule Open → In Progress → Resolved, and rejecting all other changes with the status unchanged;
- "no false success": confirming only after the Data Layer has stored the change;
- sending log events for failed submissions, denials and status changes.

**Does Not Own** — role comparisons (always asks Role Check), physical storage, presentation.

**Inputs** — API-001..003 calls with fields and simulated role.
**Outputs** — created/updated request or the list; errors `400`, `403`, `404`, `409`, `500` as defined in the contracts; API-004 calls; log events.

**Failure Responsibilities**

| Situation | Responsibility |
|---|---|
| Missing fields | Create nothing; return every missing field (AC-REQ-001-02) |
| Role not allowed | Ask Role Check; return `403` with no request data (AC-REQ-002-03, AC-REQ-003-04) |
| Transition not allowed | Leave status unchanged; return `409` with the reason (AC-REQ-003-03) |
| Unknown request `id` | Return `404` |
| Data Layer fails | Return `500`; never report success; never return an empty list in place of an error (REQ-005) |

**Related Evidence** — REQ-001, REQ-002, REQ-003, REQ-005 · AC-REQ-001-01..AC-REQ-003-05 · API-001..004 · ADR-001 · TASK-103..106

### Logic Layer — Role Check

**Purpose** — Keep **every** role decision in one place so simulated roles can later be replaced by real login without changes across the codebase (REQ-006).

**Owns** — the permission table: student may submit; only staff may list and change status; unknown or missing role = no permission.

**Does Not Own** — verifying who the user really is. In Cycle 1 the role is chosen by the user, not proven; this is acceptable only with synthetic data on a local machine (ASM-001, ADR-002 TD-02).

**Inputs** — role + action. **Outputs** — allowed / denied.

**Failure Responsibilities** — Deny by default when the role is missing or unknown.

**Related Evidence** — REQ-006 · AC-REQ-006-01 (code review), AC-REQ-002-03, AC-REQ-003-04 · API-005 · TASK-110

### Data Layer

**Purpose** — Hold the authoritative copy of each synthetic request and its current status while the application runs.

**Owns** — save, list all, find one, update status (API-004), using an **in-memory** implementation (Q-003, resolved 2026-10-15). The interface stays the same if storage changes later.

**Does Not Own** — any decision: validation, roles and transitions live in the Logic Layer. The Presentation Layer never calls it directly.

**Inputs** — already-validated requests and status updates from Request Workflow. **Outputs** — stored request(s), "not found", or an explicit failure.

**Failure Responsibilities** — Report failures to the Logic Layer; never fail silently. Data is lost when the application stops — an accepted Cycle 1 limitation (R-013).

**Related Evidence** — REQ-004, REQ-005 · AC-REQ-005-01 · API-004 · ADR-001 · TASK-102

### Logging

**Purpose** — Make important behavior observable so failures can be diagnosed (A3 "Observe" and "Log" boundary questions).

**Owns** — recording failed submissions, denied actions and status changes (TASK-108).

**Does Not Own** — request data (the Data Layer is authoritative), decisions, or real personal data — logs contain synthetic data only (REQ-004).

**Open** — log destination is TBD (diagram); whether a status-change entry records *who* and *when* depends on Q-007.

**Related Evidence** — REQ-003, REQ-004 · API-003 contract rules · TASK-108 · Q-007

## Responsibility Boundaries

| Concern | Owning Component | Boundary / Rule |
|---|---|---|
| Input validation | Logic Layer — Request Workflow | The Presentation Layer may pre-check, but only the Logic Layer decides |
| Role / permission decisions | Logic Layer — Role Check | No other code compares role values (REQ-006) |
| Status transition rule | Logic Layer — Request Workflow | Enforced only here; hidden buttons are not enforcement |
| Authoritative request state | Data Layer | Only the Logic Layer reads or writes it (API-004) |
| IDs, initial status, timestamps | Logic Layer — Request Workflow | Client-sent values are ignored |
| User-facing error messages | Presentation Layer | Displays what the Logic Layer returns; does not invent success |
| Logging | Logic Layer emits, Logging records | No real data or credentials in logs |
| Human approval point | Staff (via Presentation Layer) | Every status change is chosen by a staff member; nothing changes automatically |

## Shared Responsibilities

| Concern | Components | Responsibility Split |
|---|---|---|
| Security (simulated) | Presentation, Role Check, Data Layer | Presentation sends the role; Role Check decides; Data Layer is reachable only through the Logic Layer. Roles are unverified (known limitation). |
| Error handling | Request Workflow, Data Layer, Presentation | Data Layer reports failures; Request Workflow turns them into contract errors; Presentation shows them |
| Synthetic data only (REQ-004) | Presentation, Data Layer, Logging | Forms and test data use synthetic content; nothing real is stored or logged |

## Consistency Notes for Review

These differences between artifacts were found while writing this map and need an owner decision:

| Artifact | Difference | Suggested Action | Related |
|---|---|---|---|
| `architecture.md` | Components and interfaces tables still use template names (Web Client, Application Service, Persistence Layer, **Identity Provider**) | Use the names in this file | ARA-003, R-015 |
| `architecture-diagram.png` | Says "Q-003 open"; `assumptions-open-questions.md` marks Q-003 resolved (in-memory) on 2026-10-15 | Update the diagram note | Q-003 |
| `data-context.md` (PR #22) | Lists an Application Database, IAM, Student IDs / emails and AI prompt context | Align with REQ-004, ASM-001, Q-003 and ADR-002 TD-05 | PR #22 |

## Component Changes

| Effective Gate | Component | Change | Reason | Related Decision |
|---|---|---|---|---|
| A3 | All | Starter-kit sample components replaced with Presentation / Logic (Request Workflow, Role Check) / Data layers and Logging; Identity Provider removed | Match ADR-001 and the API contracts; no real authentication in Cycle 1 | ADR-001, ADR-002 TD-02, API-001..005 |
