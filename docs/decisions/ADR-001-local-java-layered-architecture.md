# ADR-001: Local Java Application with Separate Layers

**Status:** Proposed - pending team review  
**Date:** 2026-10-15  
**Decision Owners:** Sydel Ugwu (primary) · Nova Kurul (backup)  
**Tracking Issue:** #14

## Context

CampusConnect needs to support one Cycle 1 workflow: students submit requests, staff view them, and staff update their status.

The README and planning evidence identify Java and Maven. Joanelis's architecture proposal in PR #21 separates the application into Presentation, Logic, and Data layers. Hamza's proposed contracts use the same names.

This record proposes how those layers should work together. It does not claim the team has approved a web framework, storage implementation, or final transport details.

## Decision

Propose a locally running Java application built with Maven, with these responsibilities:

| Layer | Responsibility | Boundary |
|---|---|---|
| Presentation Layer | Collect request fields, display the staff list, and show results or errors | Does not enforce the authoritative role or status rules or access storage directly |
| Logic Layer | Validate requests, assign identifiers and submission times, check simulated roles, and enforce status transitions | Calls the Data Layer for storage and reports success only after the operation succeeds |
| Data Layer | Store and retrieve synthetic requests and their current status | Does not decide role permissions or workflow rules |

Keep role checks together in the Logic Layer so simulated roles can later be replaced without spreading changes throughout the application.

Use the forward-only status sequence Open → In Progress → Resolved from the current requirements proposal. Q-002 remains open; any decision to allow reopening must update the requirements, contracts, diagram, and tests together.

Keep storage behind an internal Java interface. Q-003 remains open: this record does not select a database, files, or an in-memory implementation, or promise persistence after restarting.

HTTP/JSON on localhost is proposed in the API contracts. The team must confirm the framework, transport, and role-passing mechanism before implementation.

Cycle 1 uses synthetic data and simulated roles. Real authentication, Loyola integrations, runtime AI, and production deployment remain outside the proposed scope.

## Alternatives Considered

These are options evaluated for this proposal. Team acceptance or rejection is not yet recorded.

### Alternative 1: Keep presentation, workflow rules, and storage together

This would reduce initial setup. However, it would make role checks and status rules harder to locate, test, and change. Separate layers are proposed to avoid that coupling.

### Alternative 2: Split the workflow into independently deployed services

This would allow components to be deployed separately. It would also add deployment, networking, and failure-handling work for a small local workflow. The proposed design keeps the application together while separating responsibilities internally.

## Consequences

### Positive

- Each major responsibility has a clear location.
- Workflow and role rules can be tested separately from the interface.
- Storage can change behind its interface.
- The design fits the local Cycle 1 scope.

### Negative / Trade-Offs

- Layer interfaces add setup and coordination work.
- Simulated roles do not provide real authentication.
- Storage, restart behavior, and web framework decisions remain unresolved.
- Conflicting component names and ADR references in other drafts need reconciliation during review.

## Evidence

- [Issue #14](https://github.com/JoanelisRM/comp330-f26-team-05/issues/14)
- [Architecture proposal, PR #21](https://github.com/JoanelisRM/comp330-f26-team-05/pull/21)
- [README](../../README.md)
- [Architecture diagram](../architecture/architecture-diagram.png)
- [Requirements: REQ-001 through REQ-006](../requirements/requirements.md)
- [Assumptions and questions: Q-002, Q-003, Q-008](../requirements/assumptions-open-questions.md)
- [Existing scope tradeoffs, ADR-002](ADR-002-cycle-1-scope-tradeoffs.md)
- [Proposed API contracts on Hamza's branch](https://github.com/JoanelisRM/comp330-f26-team-05/blob/docs/api-contracts-issue-12/docs/architecture/api-contracts.md)

## Revisit Conditions

Review this proposal when:

- the team selects the framework, transport, or storage implementation;
- requirements change the status or role rules;
- restart persistence becomes required;
- real authentication, external services, or real data enter scope;
- implementation or testing reveals that the boundaries do not work.

Record acceptance and reviewer evidence before treating this as the approved architecture baseline. Update affected planning, contracts, and tests when a decision changes.
