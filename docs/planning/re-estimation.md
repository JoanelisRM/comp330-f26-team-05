# Re-Estimation Notes

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead; A3 issue #17) · **Backup:** Hamza Bashar (A2 re-estimation owner)
**Gate:** A3 — Cycle 1 Architecture and Review · **Last updated:** 2026-10-21

This file records how the plan changed because of evidence, and why. It was not created in A2 (TASK-024 was not completed), so this first entry covers everything since the A2 baseline.

## Re-Estimation Summary

| Item | Value |
|---|---|
| Re-estimation date | 2026-10-21 |
| Compared against | A2 baseline (tag `a2-planning-estimate`, ffe1842) |
| Evidence reviewed | Requirements REQ-001..006; ADR-001; API contracts API-001..005 (PR #23); architecture review ARF-001..003 (PR #25); Q-003 resolution; A3 issues #8–#20; tags |
| Owner confirmation | **Pending** — each task owner confirms their build estimate at the next check-in |

## What Changed?

| Area | A2 Plan | New Understanding | Change Made | Evidence |
|---|---|---|---|---|
| Requirements | REQ-001..003 proposed | Six requirements with acceptance criteria (REQ-004 synthetic data, REQ-005 no loss while running, REQ-006 isolated role logic) | Build tasks linked to REQ-001..006; R-005 → Mitigated | `requirements.md`, ffe1842 |
| Architecture | Not decided | Local Java app with Presentation / Logic / Data layers | Build tasks reorganized by layer | ADR-001 |
| Storage | "Synthetic data store" (undecided) | In-memory only; nothing kept after restart | TASK-102 narrowed to an in-memory implementation behind API-004; restart persistence moved out of scope | Q-003, R-013 |
| Role handling | Not planned as a task | One simulated role check for all operations (API-005) | Added TASK-110 | API-005, REQ-006 |
| Framework / transport | Not considered | Still undecided; "HTTP / JSON on localhost" only proposed | Added TASK-111 as the first A4 task | ADR-001 open items, R-014 |
| Component names | — | Two naming schemes in the repo (template names in `architecture.md` vs. layer names in ADR-001 / contracts) | Recorded as R-015; component map (#11) waits on ARA-003 | ARF-003 |
| Gate process | Tag after all work is merged | A3 tag was created on 2026-10-16 with six issues still open | Recorded as R-009 materialized; internal target changed to "all PRs merged one day before the deadline" | Tag 6c0c830, `schedule.md` |

## Estimate Updates

| Task / Work ID | A2 Estimate | Updated Estimate (low / likely / high) | Reason for Change | Owner |
|---|---:|---:|---|---|
| TASK-101..109 (build) | Not estimated (IDs reserved) | 23 / 39 / 67 hrs | First estimate, based on the API contracts and ADR-001 | Proposed owners in `task-plan.md` |
| TASK-110 (new) | — | 2 / 3 / 5 hrs | Role check is now a separate component (API-005) | Hamza Bashar |
| TASK-111 (new) | — | 1 / 2 / 4 hrs | Framework decision is missing and blocks the build | Sydel Ugwu |
| **Cycle 1 build total** | — | **26 / 44 / 76 hrs** | | |
| TASK-102 | — | 2 / 4 / 6 hrs | In-memory storage is less work than a database (would add ≈ 4–8 hrs) | Sydel Ugwu |

## Scope Decisions

| Decision | Reason | Impact | Evidence / Link |
|---|---|---|---|
| No persistence after restart in Cycle 1 | Q-003 resolved to in-memory only | Less storage work; data lost on restart (accepted R-013) | Q-003, `scope.md` |
| No runtime AI in the product | ADR-002 TD-05; A3 boundary questions apply only to non-AI parts | No AI boundary components to build | ADR-002, `scope.md` |
| Status sequence stays forward-only until Q-002 is answered | Avoid building reopen logic that may not be needed | Keeps EST-105 at its current range | Q-002, R-017 |

## Next Actions

| Action | Owner | Target | Evidence Needed |
|---|---|---|---|
| Confirm or correct each build estimate | Each proposed owner | Next team check-in | Comment on issue #17 or a PR to `estimates.md` |
| Framework / transport ADR | Sydel Ugwu | First A4 week | New ADR in `docs/decisions/` |
| Close ARA-003 (one set of component names) | Joanelis Rivera Matos | Before A4 coding | Updated `architecture.md` |
| Answer Q-002 and Q-007 | Joanelis Rivera Matos / Hamza Bashar | Before TASK-105 | `assumptions-open-questions.md` |
| Record ADR-001 / ADR-002 acceptance | Sydel Ugwu / Khaja Shabbir Ahmed | Start of A4 | ADR status lines with date and reviewers |
| Re-estimate after the first A4 week | Khaja Shabbir Ahmed | End of first A4 week | Next entry in this file |

## Reviewability Check

Another team should be able to see from this file why the build is now estimated at about 44 hours, what architecture evidence caused each change, and which decisions are still open.
