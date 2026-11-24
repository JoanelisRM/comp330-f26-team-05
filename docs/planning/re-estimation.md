# Re-Estimation Notes

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead; A3 issue #17) · **Backup:** Hamza Bashar (A2 re-estimation owner)
**Gate:** A6 — Final Cycle 2 Release · **Last updated:** 2026-11-24 · **Tracking issues:** #17 (A3), #110 (A6)

This file records how the plan changed because of evidence, and why. The newest entry is first: **Cycle 2 (A6)**, then the **A3 entry** (2026-10-21), which covered everything since the A2 baseline.

## Cycle 2 Re-Estimation (A6, 2026-11-24)

| Item | Value |
|---|---|
| Re-estimation date | 2026-11-24 |
| Compared against | A5 release (`main` `1bb17f4`) and the A3 entry below |
| Evidence reviewed | `known-limitations.md` (LIM-001..LIM-010), `defect-review.md` (QA-001..QA-006), `risk-register.md`, A4 instructor assessment, A5 presentation (slide 6), `cycle2-handoff.md` |
| Owner confirmation | **Not yet.** The Cycle 2 estimates below were prepared by the planning lead on 2026-11-24; owners have not confirmed them |

### How Cycle 1 compared with its estimate

| Item | Estimate (A3) | What Happened |
|---|---|---|
| Cycle 1 build (TASK-101..111) | 26 / 44 / 76 hrs | All build tasks done between 2026-10-29 and 2026-11-10. **Actual hours were not recorded** on the issues as planned, so the estimate cannot be checked in hours |
| Rework | Not estimated | RF-01..RF-06 (compile errors and HTTP defects) needed a fix PR (#67), a list fix (#76) and an RF-06 fix (#97); CRF-006/007/010 needed a second PR (#95). Rework was not in any estimate |
| Framework decision (TASK-111) | Must happen first | Code was built before ADR-0003 was merged; the ADR (PR #56) is still not on `main` |

### Cycle 2 maturity backlog with estimates

Targets come from Cycle 1 evidence; reasons and outcomes are in `cycle2-maturity-summary.md` (PR #125). Estimates are low / likely / high hours.

| Target | Estimate | Owner | Outcome (2026-11-24) |
|---|---:|---|---|
| MT-01 Observability plan | 2 / 3 / 4 | Nova Kurul | In review (#124) |
| MT-02 Security and governance checklist | 2 / 3 / 4 | Nova Kurul | In review (#124) |
| MT-03 Merge PR #95 (conflict, CI, merge) | 0.5 / 1 / 2 | Sydel Ugwu | Not done |
| MT-04 Branch protection on `main` | 0.25 / 0.5 / 1 | Joanelis Rivera Matos | Not done |
| MT-05 Final tag and GitHub Release | 0.25 / 0.5 / 1 | Joanelis Rivera Matos | After the last merge |
| MT-06 Runbook | 2 / 3 / 5 | Sydel Ugwu | Not done |
| MT-07 Durable storage (SQLite), with ADR and restart test | 6 / 10 / 16 | Sydel Ugwu | Not started |
| MT-08 Real authentication (hashed passwords, sessions), with ADR and tests | 10 / 16 / 24 | Sydel Ugwu | Not started |
| MT-09 Controlled reopening with an audit comment | 3 / 5 / 8 | Hamza Bashar | Not started |
| MT-10 Browser page (plain HTML/JS) | 8 / 12 / 20 | Not assigned | Not started |
| **Code targets MT-07..MT-10** | **27 / 43 / 68** | | |

The four code targets together are about the size of the whole Cycle 1 build (26 / 44 / 76 hours), and none of them has an issue, owner or agreed estimate yet (R-024). With the time left before the A6 deadline, the realistic order is MT-03, MT-04 and MT-06 first; MT-07..MT-10 only if an owner commits to one now.

### Scope decisions

| Decision | Reason | Impact | Evidence |
|---|---|---|---|
| **Proposed:** MT-07..MT-10 deferred unless an owner commits to one this week | No issues yet; their combined size equals the Cycle 1 build | LIM-001, LIM-002, LIM-004, LIM-010 would stay open in the final release | `known-limitations.md`, `cycle2-maturity-summary.md`; to be confirmed by the team |
| **Proposed:** finish the small targets first (MT-03, MT-04, MT-06) | Highest value for the least effort | Real risk reduction before the tag | `cycle2-maturity-summary.md` |

### What happened to the A3 next actions

| Action (A3 entry below) | Outcome |
|---|---|
| Confirm or correct each build estimate | Not done |
| Framework / transport ADR | Written (ADR-0003, PR #56, approved) but not merged |
| Close ARA-003 (one set of component names) | Done (PR #28) |
| Answer Q-002 and Q-007 | Done (PR #28, PR #70) |
| Record ADR-001 / ADR-002 acceptance | Not done (R-018) |
| Re-estimate after the first A4 week | Not done; this Cycle 2 entry is the next one |

### Lesson

Re-estimate at the **start** of a cycle, with an issue, owner and estimate for each target, and record actual hours when an issue closes. In Cycle 2 this entry was written on 2026-11-24, eleven days after the A6 issues were created and before any code target had started.

---

## A3 Re-Estimation (2026-10-21)

### Re-Estimation Summary

| Item | Value |
|---|---|
| Re-estimation date | 2026-10-21 |
| Compared against | A2 baseline (tag `a2-planning-estimate`, ffe1842) |
| Evidence reviewed | Requirements REQ-001..006; ADR-001; API contracts API-001..005 (PR #23); architecture review ARF-001..003 (PR #25); Q-003 resolution; A3 issues #8–#20; tags |
| Owner confirmation | **Pending** — each task owner confirms their build estimate at the next check-in |

### What Changed?

| Area | A2 Plan | New Understanding | Change Made | Evidence |
|---|---|---|---|---|
| Requirements | REQ-001..003 proposed | Six requirements with acceptance criteria (REQ-004 synthetic data, REQ-005 no loss while running, REQ-006 isolated role logic) | Build tasks linked to REQ-001..006; R-005 → Mitigated | `requirements.md`, ffe1842 |
| Architecture | Not decided | Local Java app with Presentation / Logic / Data layers | Build tasks reorganized by layer | ADR-001 |
| Storage | "Synthetic data store" (undecided) | In-memory only; nothing kept after restart | TASK-102 narrowed to an in-memory implementation behind API-004; restart persistence moved out of scope | Q-003, R-013 |
| Role handling | Not planned as a task | One simulated role check for all operations (API-005) | Added TASK-110 | API-005, REQ-006 |
| Framework / transport | Not considered | Still undecided; "HTTP / JSON on localhost" only proposed | Added TASK-111 as the first A4 task | ADR-001 open items, R-014 |
| Component names | — | Two naming schemes in the repo (template names in `architecture.md` vs. layer names in ADR-001 / contracts) | Recorded as R-015; component map (#11) waits on ARA-003 | ARF-003 |
| Gate process | Tag after all work is merged | A3 tag was created on 2026-10-16 with six issues still open | Recorded as R-009 materialized; internal target changed to "all PRs merged one day before the deadline" | Tag 6c0c830, `schedule.md` |

### Estimate Updates

| Task / Work ID | A2 Estimate | Updated Estimate (low / likely / high) | Reason for Change | Owner |
|---|---:|---:|---|---|
| TASK-101..109 (build) | Not estimated (IDs reserved) | 23 / 39 / 67 hrs | First estimate, based on the API contracts and ADR-001 | Proposed owners in `task-plan.md` |
| TASK-110 (new) | — | 2 / 3 / 5 hrs | Role check is now a separate component (API-005) | Hamza Bashar |
| TASK-111 (new) | — | 1 / 2 / 4 hrs | Framework decision is missing and blocks the build | Sydel Ugwu |
| **Cycle 1 build total** | — | **26 / 44 / 76 hrs** | | |
| TASK-102 | — | 2 / 4 / 6 hrs | In-memory storage is less work than a database (would add ≈ 4–8 hrs) | Sydel Ugwu |

### Scope Decisions

| Decision | Reason | Impact | Evidence / Link |
|---|---|---|---|
| No persistence after restart in Cycle 1 | Q-003 resolved to in-memory only | Less storage work; data lost on restart (accepted R-013) | Q-003, `scope.md` |
| No runtime AI in the product | ADR-002 TD-05; A3 boundary questions apply only to non-AI parts | No AI boundary components to build | ADR-002, `scope.md` |
| Status sequence stays forward-only until Q-002 is answered | Avoid building reopen logic that may not be needed | Keeps EST-105 at its current range | Q-002, R-017 |

### Next Actions

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
