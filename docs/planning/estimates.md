# Estimates

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Joanelis Rivera Matos
**Gate:** A1 — Project Launch (initial baseline) · **Last updated:** 2026-09-24

## Estimation Approach

- Estimates are effort in hours, given as a likely value with a range, not a promise.
- These are the Planning Lead's first estimates. **Each task owner will confirm or correct their own estimate** at the next team meeting, and changes will be recorded under Estimate Changes.
- Estimates include review and rework time, not just writing time.
- Cycle 1 implementation work (the vertical slice itself) will be estimated in A2 once requirements REQ-001..003 exist.

## Estimates

| ID | Work Item | Estimate | Range / Uncertainty | Basis | Assumptions | Owner(s) | Status |
|---|---|---|---|---|---|---|---|
| EST-001 | TASK-001 Working agreements | 3 hrs | 2–4 hrs | Template sections known; mostly writing and review | PA-001 | Khaja Shabbir Ahmed | Done |
| EST-002 | TASK-002 Role acknowledgements | 0.5 hr | 0.5–1 hr | One small commit per member | — | Joanelis Rivera Matos | Initial |
| EST-003 | TASK-003 Charter TBDs | 2 hrs | 1–3 hrs | Depends on kickoff decisions | PA-002 | Hamza Bashar | Initial |
| EST-004 | TASK-004 Kickoff meeting | 1 hr | 1–1.5 hrs | One meeting plus notes | PA-002 | Joanelis Rivera Matos | Initial |
| EST-005 | TASK-005 Confirm slice and REQ IDs | 1 hr | 0.5–2 hrs | Team discussion; proposal already drafted in `scope.md` | PA-001 | Sydel Ugwu | Initial |
| EST-006 | TASK-006 Initial requirements package | 5 hrs | 3–8 hrs | Three requirements plus acceptance criteria, assumptions and questions; wide range because requirements are new to the team | PA-001 | Sydel Ugwu | Initial |
| EST-007 | TASK-007 Planning baseline (six files) | 5 hrs | 4–8 hrs | Six files, depends on requirements being confirmed | PA-001 | Khaja Shabbir Ahmed | Initial |
| EST-008 | TASK-008 Initial decision record | 2 hrs | 1–3 hrs | One ADR with alternatives | — | Sydel Ugwu | Initial |
| EST-009 | TASK-009 README cleanup | 1 hr | 0.5–2 hrs | Remove guidance and update status | — | Joanelis Rivera Matos | Initial |
| EST-010 | TASK-010 Move A1 tag | 0.5 hr | 0.25–1 hr | Two Git commands plus verification | — | Joanelis Rivera Matos | Initial |
| EST-011 | TASK-011 Word index and submission | 2 hrs | 1–3 hrs | Index of links, team table, changed-file list | — | Joanelis Rivera Matos | Initial |
| EST-012 | TASK-012 Issues, milestone, Projects board | 3 hrs | 2–5 hrs | One issue per task; board setup is new to the team | PA-004 | Khaja Shabbir Ahmed | Initial |
| EST-013 | TASK-013 A2 planning package expansion | 8 hrs | 6–12 hrs | Three new files plus updates to six existing ones | PA-001, PA-004 | Khaja Shabbir Ahmed | Initial |

## Estimation Assumptions

| Assumption Reference | Estimate(s) Affected | Effect if Incorrect |
|---|---|---|
| PA-001: The Cycle 1 slice in `scope.md` is accepted without major change | EST-005, EST-006, EST-007, EST-013 | Requirements and planning would need rework (+2–4 hrs each) |
| PA-002: A kickoff meeting can be held this week with most members | EST-003, EST-004 | Charter decisions slip; A1 charter stays incomplete |
| PA-003: Each member can give roughly 3–5 hours per week to the project | All estimates, `schedule.md` | Targets move later; scope may need to be cut (R-008) |
| PA-004: The team can use GitHub Issues and Projects with the current repo permissions | EST-012, EST-013 | Setup delayed until permissions are fixed |

## Estimate Confidence

| Estimate ID | Confidence | Reason |
|---|---|---|
| EST-001 | High | Work is drafted and in review |
| EST-002, EST-004, EST-009, EST-010 | High | Small, well-understood tasks |
| EST-003, EST-005, EST-008, EST-011 | Medium | Depend on team decisions |
| EST-006, EST-007 | Low–Medium | Requirements not yet written; first time for the team |
| EST-012, EST-013 | Low | A2 expectations not fully explored yet |

## Estimate Changes

| Date / Gate | Estimate ID | Previous Estimate | New Estimate | Reason for Change |
|---|---|---|---|---|
| — | — | — | — | No changes yet (initial baseline) |

## Estimate vs. Actual

| Estimate ID | Estimated | Actual | Variance | What We Learned |
|---|---|---|---|---|
| EST-001 | 3 hrs | To be recorded by owner | — | Actuals will be recorded as tasks complete |

## Planning Implications

| Estimate / Evidence | Planning Impact | Related Scope / Schedule / Risk |
|---|---|---|
| Total remaining A1 effort (EST-002..011) ≈ 20 hrs (range ≈ 13–33 hrs) across five members | Feasible for the A1 grace window only if requirements start immediately | MS-005, MS-006, R-005 |
| EST-006 and EST-007 have the lowest confidence and sit on the critical path | Requirements and planning owners should pair rather than work in sequence | R-005 |
| Implementation not yet estimated | Cycle 1 build effort must be estimated in A2 before committing to A4 dates | R-003, R-007 |
