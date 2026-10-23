# Schedule

**Owner:** Joanelis Rivera Matos (Team Lead) · **Backup:** Hamza Bashar
**Gate:** A3 — Cycle 1 Architecture and Review · **Last updated:** 2026-10-21 (updated under issue #17)

## Schedule Basis

- Official deadlines come from Sakai. Dates below are **actual dates from repository evidence** (tags, merges) where available; future dates say "per Sakai" until confirmed.
- The A1 version of this file used week-number estimates that turned out to be wrong (A2 was actually due 2026-09-29), so those estimates have been removed.
- Internal readiness target: all gate PRs merged and reviewed **at least one day** before the Sakai deadline, so the tag can be created and checked.
- Capacity assumption: PA-003 in `estimates.md`.

## Milestones

| ID | Milestone | Target / Actual Date | Required By | Dependencies | Owner | Status |
|---|---|---|---|---|---|---|
| MS-001 | Repository created from starter kit | 2026-09-08 | A1 | — | Joanelis Rivera Matos | Done |
| MS-004 | Working agreements and planning baseline merged | 2026-09-25 | A1 | — | Khaja Shabbir Ahmed | Done (PR #1, #2) |
| MS-003 | Kickoff meeting recorded | 2026-09-29 | A1 | — | Joanelis Rivera Matos | Done (per A2 schedule update) |
| MS-005 | Requirements baseline REQ-001..006 | 2026-09-29 | A2 | — | Hamza Bashar / Sydel Ugwu | Done |
| MS-008 | A2 package and `a2-planning-estimate` tag | 2026-09-29 (Sakai 23:55) | A2 | MS-005 | Joanelis Rivera Matos | Done — tag on ffe1842 |
| MS-009 | A3 architecture PRs merged (overview, diagram, ADR-001, contracts, review, test strategy) | 2026-10-15 | A3 | MS-005 | See `task-plan.md` Part 1 | Done (PRs #21, #23, #24, #25) |
| MS-010 | `a3-architecture-review` tag | 2026-10-16 | A3 | MS-009 | Joanelis Rivera Matos | Done — tag on 6c0c830, with six A3 issues still open |
| MS-011 | Architecture baseline accepted (ADR-001 / ADR-002 acceptance recorded; ARA-001..003 closed) | Start of A4 | A4 | MS-010 | Sydel Ugwu / Joanelis Rivera Matos | Not started (R-018, R-015) |
| MS-012 | Framework / transport decision ADR (TASK-111) | First week of A4 | A4 | MS-011 | Sydel Ugwu | Not started (R-014) |
| MS-013 | Every member builds and runs the skeleton (TASK-101) | After MS-012 | A4 | MS-012 | Sydel Ugwu | Not started |
| MS-014 | Integration checkpoint: slice runs end to end on `main` (TASK-109) | Per Sakai A4 date, minus review buffer | A4 | MS-013 | Joanelis Rivera Matos | Not started |

## Phase-Gate Readiness

| Gate | Sakai Deadline | Key Evidence / Deliverables | Status |
|---|---|---|---|
| A1 — Project Launch | Passed | Launch package | Graded (tag on starter commit; one-time instructor exception) |
| A2 — Planning & Estimation | 2026-09-29 | Planning package, requirements, issues, tag | Submitted — tag `a2-planning-estimate` → ffe1842 |
| A3 — Architecture & Review | Per Sakai (tag created 2026-10-16) | Architecture package, ADRs, contracts, review evidence, test strategy, updated planning | Tagged with open items (see `task-plan.md`) |
| A4 — Implementation & Review | Per Sakai | Cycle 1 slice code, reviewed PRs, tests | Not started |
| A5 — Verification & Release | Per Sakai | Test evidence, release notes, known limitations | Not started |
| A6 — Operational Maturity | Per Sakai | Observability, runbook, postmortem | Not started |

## Major Dependencies

| Predecessor / Dependency | Dependent Work | Schedule Impact if Delayed | Related Risk |
|---|---|---|---|
| Framework / transport ADR (TASK-111) | All build tasks TASK-101..110 | Blocks A4 entirely | R-014 |
| ARA-003 component names | Component map (#11), implementation package names | Rework of code and docs | R-015 |
| Q-002, Q-007 answered | Status change (TASK-105), logging (TASK-108) | Rework of API-003 and tests | R-017 |
| All gate PRs merged and reviewed | Gate tag | Work missing from the graded snapshot | R-006, R-009 |

## Near-Term Planning Window

| Time Window | Planned Outcome | Related Milestone(s) | Key Dependency / Risk |
|---|---|---|---|
| 2026-10-21 → A4 start | Close open A3 items (#8, #11, #13, #16, #17, #19); record ADR acceptance; close ARA-001..003 | MS-011 | R-015, R-018 |
| First A4 week | Framework ADR; skeleton builds on every machine; owners confirm build estimates | MS-012, MS-013 | R-014, R-007 |
| Remaining A4 weeks | Build TASK-102..108, then integration checkpoint | MS-014 | R-008, R-013 |

## Schedule Changes

| Date | Milestone / Gate | Previous Target | New Target | Reason | Related Evidence |
|---|---|---|---|---|---|
| 2026-09-24 | A1 | End of Week 4 | End of Week 5 (grace) | Launch evidence late | A1 schedule |
| 2026-09-29 | A2 | ≈ 2026-10-04 (week-number estimate) | 2026-09-29 | Actual Sakai deadline confirmed by Team Lead | Commit 331fbfb |
| 2026-10-21 | A4 build | Not scheduled | 2–3 weeks likely, up to 5 (≈ 44 hrs, 26–76) | First build estimates | `estimates.md`, `re-estimation.md` |

## Schedule Risks

| Risk ID | Affected Milestone / Gate | Schedule Exposure |
|---|---|---|
| R-014 | MS-012 and all of A4 | High — nothing can be built before the framework decision |
| R-008 | A4 | Medium–High — the high estimate (76 hrs) may not fit the A4 window |
| R-009 / R-006 | Every gate | Medium — tagging before work is merged leaves it out of the graded snapshot |
