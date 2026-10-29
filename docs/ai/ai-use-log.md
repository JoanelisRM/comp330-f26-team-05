# AI Use Log

**Owner:** Nova (AI/Governance Lead) | **Backup:** Hamza

## AI Use

| Date | Tool | Purpose | Artifact / Activity | Human Verification | Result / Action |
|---|---|---|---|---|---|
| 2026-09-20 | Gemini | Structure and clean boilerplate templates for AI Governance | `docs/ai/ai-policy.md`, `docs/ai/ai-use-log.md`, `docs/ai/ai-verification-notes.md` | Nova reviewed the generated text to ensure all starter kit instructional comments were removed and team roles were accurately reflected. | Accepted after modification. Files committed to the repository for Assignment 1 phase-gate. |
| 2026-09-20 | ChatGPT | Draft Role Matrix and Working Agreements based on Teams chat | `docs/team/roles.md` | Joanelis and Khaja reviewed the drafted roles against the actual team chat agreements to ensure accuracy. | Accepted and implemented. |
| 2026-09-24 | Claude | Guidance on A1 deliverables; structure and draft wording for Working Agreements; Git/PR workflow steps. At Khaja's request, Claude also committed the roles.md evidence-link fix and this log entry. | `docs/team/working-agreements.md`, `docs/team/roles.md`, `docs/ai/ai-use-log.md` | Khaja reviewed every Working Agreements section against the team charter and team chat decisions, edited bracketed items, and requested PR review from Nova (PR #1). | Accepted after modification. |
| 2026-09-24 | Claude | Drafted the initial A1 planning baseline (scope, task plan, estimates, schedule, risk register, traceability) from repository evidence and the A1/A2 assignment packages, at Khaja's request | `docs/planning/*.md` | Khaja to review every file against team decisions; each task owner to confirm their own estimate; Sydel to confirm the proposed slice and REQ IDs. PR review by Joanelis. | Accepted after modification. |
| 2026-09-25 | Claude | Drafted A2 updates to the task plan (A2 deliverables with Team Lead's ownership split; Cycle 1 build breakdown), risk register (R-009..R-012, materialized risks, review log) and ADR-002 scope tradeoffs, at Khaja's request | `docs/planning/task-plan.md`, `docs/planning/risk-register.md`, `docs/decisions/ADR-002-cycle-1-scope-tradeoffs.md` | Khaja to review all three files; proposed build-task owners and ADR-002 to be confirmed by the team; PR review by Nova (task plan, risks) and Joanelis (ADR-002). | Accepted after modification. |
| 2026-09-29 | ChatGPT / Codex | Helped with draft updates to requirements, scope, traceability, and role acknowledgment. | `docs/requirements/`, `docs/planning/scope.md`, `docs/planning/traceability.md`, `docs/team/roles.md`, `docs/ai/ai-use-log.md` | Sydel worked with AI agent to add some code to all the roles for A2 and made the changes in GitHub. Team review is pending. | Used with revisions. Requirements and estimate alignment still need team confirmation. |
| 2026-09-29 | Gemini | Generate planning phase Team Commitments table and structure initial GitHub Issues | `/docs/planning/team-commitments.md` and GitHub Issues Board | Nova reviewed the generated table against the team's Teams chat agreements and verified alignment with A2 requirements. | Accepted and pushed to repository. |
| 2026-09-29 | Claude | Guidance on Hamza's A2 deliverables and the assignment package; review of Hamza's draft requirements for wording and consistency with ADR-002 and traceability.md; drafted acceptance criteria and assumptions/open questions, then aligned all three files to the terminology and required fields in traceability.md (staff role; category, title, description), at Hamza's request | `docs/requirements/requirements.md`, `docs/requirements/acceptance-criteria.md`, `docs/requirements/assumptions-open-questions.md` | Hamza wrote the initial requirements draft himself. Hamza to review every AI edit | Pending review. Requirements baseline remains Proposed until team acceptance. |
| 2026-10-15 | Claude | Guidance on VS Code and Git branch setup; drafted the A3 API contracts (API-001 to API-005) from the team's requirements, acceptance criteria, ADR-002 and open questions; helped phrase review comments ARF-001 to ARF-003 on PR #21, at Hamza's request | `docs/architecture/api-contracts.md`, PR #21 review | Hamza reviewed each contract against REQ-001 to REQ-006 and the acceptance criteria; Hamza wrote the final PR #21 review comments himself. Joanelis reviewed PR #23. | Accepted after review. Contracts remain Proposed until the open questions are decided. |
| 2026-10-15 | ChatGPT / Codex | Helped draft the architecture diagram, ADR-001, and test/review strategy, and explained the GitHub steps. | `docs/architecture/architecture-diagram.png`, `docs/decisions/ADR-001-local-java-layered-architecture.md`, `docs/testing/test-strategy.md` | Sydel added the drafts in GitHub. Independent team review is pending. | Drafts prepared for A3 review. Architecture decisions remain proposed; no completed tests are claimed. |
| 2026-10-15 | Claude | Drafted `architecture-review.md` from Hamza's PR #21 review comments (ARF-001 to ARF-003) and Joanelis's replies, including dispositions and follow-up actions, at Hamza's request | `docs/reviews/architecture-review.md` | Hamza checked each finding against his own PR #21 comments and corrected the wording to match them. | Accepted after modification. Review remains in progress. |
| 2026-10-21 | Claude | At Khaja's request, drafted the A3 planning update for issue #17 from repository evidence (ADR-001, API contracts, architecture review, Q-003, issues #8–#20, tags): task plan, risk register (R-013..R-018), first build estimates (26/44/76 hrs), schedule with actual dates, scope update, new `re-estimation.md` | `docs/planning/task-plan.md`, `risk-register.md`, `estimates.md`, `schedule.md`, `scope.md`, `re-estimation.md` | Pending: Khaja to read every file and correct anything wrong before requesting review; build estimates to be confirmed by each task owner; PR review by a teammate. Arithmetic of estimate totals re-checked. | Draft — under Khaja's review (PR for #17). |
| 2026-10-21 | Claude | At Khaja's request, drafted the component responsibility map for issue #11 from ADR-001, API-001..005, the architecture diagram and REQ-001..006; flagged naming and content differences in `architecture.md`, the diagram and the PR #22 data-context draft | `docs/architecture/component-responsibilities.md` | Pending: Khaja to check each component and boundary against ADR-001 and the API contracts before requesting review; PR review by a teammate. | Draft — under Khaja's review (PR for #11). |

## What Should Be Logged?

Log AI use when it materially influences requirements, architecture, significant engineering decisions, implementation, tests, debugging, security analysis, or phase-gate evidence.

## Human Verification

The verification should be proportional to the risk and consequence of the AI-assisted work. State exactly what human engineers did to verify the output.

## Result / Action

Useful outcomes include: Accepted, Accepted after modification, Partially used, Rejected, Used as analysis input, Implemented and verified, or No action taken.

## Relationship to Verification Notes

For significant AI-assisted work requiring more detailed verification evidence, reference an entry in:

`/docs/ai/ai-verification-notes.md`

## Expectations

- Record significant AI-assisted engineering activity.
- Identify the affected artifact or engineering activity.
- Describe meaningful human verification.
- Record what was actually done with the AI output.
- Link detailed verification evidence when appropriate.
- Keep entries concise but specific enough to be reviewable.

| 2026-10-29 | Gemini | Issue #44: Drafted reusable Pull Request template (.github/pull_request_template.md) | Sydel reviewed and verified against Cycle 1 requirements, architecture boundaries, and tests before committing. | Accepted after human verification. |
