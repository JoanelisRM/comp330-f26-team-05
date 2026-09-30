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
