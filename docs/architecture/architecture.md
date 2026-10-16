# Architecture

<!--
- what the system is and what surrounds it;
- how the system is structurally organized;
- the major components and their responsibilities;
- important interfaces and dependencies;
- significant constraints and assumptions;
- the current architecture diagram; and
- the significant decisions that shaped the architecture.

At early gates, some architectural elements may still be proposed or
incomplete. State uncertainty explicitly rather than inventing detail.

Do not use this file as a source-code inventory. Focus on meaningful
architectural structure and engineering reasoning.
-->

## System Context

The CampusConnect Workflow System allows students to submit requests. Authorized staff are then able to view these requests, process them, and sends the completed information back to the student. Users are able to access the system through a locally run web application.

## Architectural Structure

### Layered Application
Presentation Layer -> 
- What the users and staff are able to see, such as the user interface. Users can access the site and send out their requests but that's how much they're able to access. Staff have access to a similar interface specifically to review sent in requests and solve them.

Logic Layer -> 
- Responsible for coordinating the application, processing commands, and making logical decisions and evaluations. It also moves and processes data between the presentation layer to logic, between the logic to data, and vice versa.

Data Layer -> 
- Where the data is stored and retrieved from the logic layer. The necessary data here includes request data, records, and user data. 

The architecture is provisional. It may be prone to change, but chances are low due to the relatively small scope.

## Major Components and Responsibilities

<!--
TEAM CONTENT REQUIRED

Summarize the major architectural components.

Keep this table at a high level. More detailed responsibility boundaries
belong in:

/docs/architecture/component-responsibilities.md

Use stable component names consistently across:

- architecture.md;
- component-responsibilities.md;
- api-contracts.md;
- architecture diagrams;
- decision records;
- implementation; and
- verification evidence.

THE ROWS BELOW ARE SAMPLE DATA.
DELETE AND REPLACE THEM WITH YOUR ACTUAL COMPONENTS.
-->

| Component | Primary Responsibility | Key Dependencies |
|---|---|---|
| Web Client | Provides the user-facing workflow interface and communicates user actions to the application service. | Application Service |
| Application Service | Enforces application workflows and coordinates domain operations. | Identity Provider, Persistence Layer |
| Persistence Layer | Stores and retrieves durable workflow data. | Database |
| Identity Provider | Authenticates users and provides trusted identity information. | External service |

<!--
Do not list every class, file, package, or library.

A component should represent a meaningful architectural responsibility or
boundary.
-->

## Interfaces and Dependencies

| Source | Target | Interaction | Interface / Protocol | Notes |
|---|---|---|---|---|
| Presentation Layer | Logic Layer | Students submits a request | HTTP / JSON on localhost (proposed) | Requests will be simulated |
| Presentation Layer | Logic Layer | Staff views a list of submitted requests | HTTP / JSON on localhost (proposed) | Submitted requests will be simulated and synthetic |
| Presentation Layer | Logic Layer | Staff changes a request's status | HTTP / JSON on localhost (proposed) | This status change will be temporary during a run |
| Logic Layer | Data Layer | Store and retrieve requests | HTTP / JSON on localhost (proposed) | Storing and retrieving will be temporary during a run |
| Logic Layer | Logic Layer | Decide what the simulated role will do | HTTP / JSON on localhost (proposed) | Team decision |


## Data and State Ownership

| Data / State | Authoritative Owner | Persistence | Notes |
|---|---|---|---|
| Workflow Request | Data Layer | Database | Request simulated data will be stored in the data layer |
| User Data | Data Layer | Database | Synthetic user data will be stored in the data layer |

## Constraints and Assumptions

| ID / Reference | Type | Description | Architectural Impact |
|---|---|---|---|
| ASM-001 | Assumption | The student and staff roles are simulated as a substitute for real authentication | The architecture doesn't rely on any external services |
| ASM-002 | Assumption | Running the system locally is sufficient for Cycle 1 | The architecture doesn't rely on infrastructure |
| ASM-003 | Assumption | Each piece of data will be synthetic | The architecture doesn't rely on any external database, similar to ASM-001 |
| CNST-001 | Constraint | The program must be programmed and run through Java | The architecture relies on a single programming language |

## Architecture Diagram

The current Architecture Diagram can be found in 'docs/architecture/architecture-diagram.png'

## Architectural Qualities and Tradeoffs

| Quality / Concern | Architectural Response | Tradeoff / Consequence |
|---|---|---|
| Usability | Ensure the system is easy for users to carry out their tasks | Could lead to long testing sessions |
| Reliability | Submitted requests and the status changes won't be lost while the application is still running | Increases complexity |
| Maintainability | Role checks will be in one place so simulated roles can be changed and used for real login later | Could lead to overly complex code |


## Key Architectural Decisions

| Decision | Summary | Evidence |
|---|---|---|
| ADR-001 | There is no real student data for a database. It will all be synthetic. | `/docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` 
| ADR-002 | There is no real login or authentication. Both student and staff roles will be simulated | `/docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` |
| ADR-003 | Status changes will be done manually by staff instead of using automatic routing and assigment | `/docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` |
| ADR-004 | The data will be stored via simple list instead of dashboards that need sorting, searching, and filtering | `/docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` |
| ADR-005 | The program will run locally instead of being deployed | `/docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` |


## Architecture Evolution

<!--
Architecture may evolve as:

- requirements become clearer;
- implementation produces evidence;
- assumptions are validated or invalidated;
- risks are discovered; and
- engineering decisions change.

When the architecture changes materially:

1. update this file;
2. update affected diagrams;
3. update component-responsibilities.md;
4. update api-contracts.md;
5. record significant decisions;
6. review affected requirements and acceptance criteria;
7. review affected risks; and
8. update relevant tests and operational evidence.

Use the table below only for meaningful architectural changes.

A blank table is intentional at project start.
-->

| Effective Gate | Change | Reason | Related Evidence |
|---|---|---|---|
|  |  |  |  |

## Expectations

- Describe the actual architecture rather than an aspirational architecture.
- Use consistent component and interface names across engineering artifacts.
- Keep system context, structure, responsibilities, and dependencies current.
- Maintain a useful architecture diagram.
- Separate requirements from architecture and architecture from low-level implementation detail.
- Reference assumptions, risks, decisions, and contracts rather than unnecessarily duplicating them.
- Make significant architectural tradeoffs explicit.
- Record uncertainty rather than manufacturing architectural certainty.
- Update related engineering evidence when the architecture materially changes.
