## Summary

<!-- Briefly describe what this pull request changes and what engineering artifact or vertical-slice component it introduces or updates. -->

## Related Issue(s) and Traceability

<!-- Link the authoritative GitHub Issue(s) and requirement/task/ADR IDs from /docs/planning/traceability.md. -->
- Closes #
- **Task / Deliverable ID:** <!-- e.g., TASK-111, TASK-101, TASK-102, A4 Deliverable -->
- **Requirement / Contract / ADR IDs:** <!-- e.g., REQ-001..006, AC-REQ-001-01, API-001..005, ADR-001, ADR-003 -->

## What Changed

- 
- 

## Why This Change Is Needed

<!-- Explain the engineering reason for this change (e.g., Cycle 1 vertical slice implementation, closing an Assignment 3 architecture review action item, adding verification evidence). -->

## Architecture & Boundary Check

- [ ] Respects Cycle 1 layered architecture (`Presentation` -> `Logic` -> `Data`, plus `Observability/Logging`) per `docs/architecture/component-responsibilities.md` and `ADR-001` / `ADR-003`
- [ ] Presentation layer does not access the Data layer directly (`API-004` is called only by the Logic layer)
- [ ] Central role check (`API-005` / `REQ-006`) and forward-only status transitions (`Open -> In Progress -> Resolved`, `REQ-003`) are preserved
- [ ] Uses synthetic data only (`REQ-004`); no real Loyola student data, PII, or credentials are included
- [ ] Any architecture change or deviation from Assignment 3 is documented in `docs/architecture/` or `docs/decisions/` before merge

## Tests Run & CI/CD Evidence

<!-- State how this change was verified locally and in GitHub Actions CI (.github/workflows/ci.yml). -->
- [ ] Local build and unit tests ran (`mvn clean test`) — attach or summarize output below
- [ ] GitHub Actions CI workflow (`ci.yml`) triggered and passed on this pull request
- [ ] Positive, negative, boundary, and role/transition failure paths covered (if code/test change)
- **Verification Evidence Location:** <!-- e.g., PR checks tab, /docs/testing/, /docs/observability/runtime-logging-evidence.md -->

## Dependency & Supply-Chain Review

- [ ] No new external dependencies introduced
- [ ] *If a new dependency, library, API, or version change was introduced:* documented below and in `/docs/decisions/` (package name, version, license, maintenance status, security considerations, and owner)

## Risks, Known Limitations & Follow-Up

- **Risk Category:** <!-- e.g., None / Correctness / Security / Architecture / Maintainability / Dependencies / Testing -->
- **Known Limitations or Deferred Work:** <!-- Reference /docs/planning/risk-register.md or /docs/release/known-limitations.md if applicable -->

## AI Assistance Disclosure & Human Verification

<!-- Required by COMP 330 Assignment 4 Section 8 and /docs/ai/ai-policy.md. -->
- [ ] **No AI was used** for this pull request
- [ ] **AI assistance was used** and is recorded in `/docs/ai/ai-use-log.md`:
  - **Tool / Model:** 
  - **Task / Prompt Purpose:** 
  - **Accepted Output:** 
  - **Rejected Output / Human Edits:** 
  - **Human Verification Performed:** 

## Reviewer Checklist (COMP 330 Quality Gate Lenses)

<!-- To be completed by the assigned Backup Owner / Reviewer before approving or blocking merge. Reviewers must identify risk category, risk explanation, fix direction, and required evidence in PR comments. -->
- [ ] **Correctness:** Satisfies accepted requirement, business rule, state transition, and edge cases
- [ ] **Security:** Inputs validated, simulated role check enforced before action, synthetic-data rule respected
- [ ] **Architecture:** Respects service/API boundaries (`API-001..005`), data ownership, persistence rules, and audit logging
- [ ] **Maintainability:** Clear package structure, naming, and documentation so teammates can test and modify safely
- [ ] **Dependencies:** No unreviewed packages, licenses, or external system calls introduced
- [ ] **Tests:** Challenges workflow, failure paths, boundaries, and authorization cases (not only happy paths)
- [ ] **Evidence & Stewardship:** PR notes, review comments, `ai-use-log.md`, tests, and CI checks explain why this merge is trustworthy
