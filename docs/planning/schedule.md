# Schedule

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup:** Joanelis Rivera Matos
**Gate:** A1 — Project Launch (initial baseline) · **Last updated:** 2026-09-24

## Schedule Basis

- Official deadlines come from Sakai. The A1 and A2 dates below are derived from the week numbers in the assignment packages, assuming Week 1 began Monday 2026-08-24 and "end of week" means Sunday. **Verify against Sakai before relying on them.**
- Internal readiness targets are set 2–3 days before the Sakai date to leave time for PR review and tagging.
- Dates for A3–A6 will be filled in when those assignments are released.
- Capacity assumption: see PA-003 in `estimates.md`.

## Milestones

| ID | Milestone | Target Date | Required By | Dependencies | Owner | Status |
|---|---|---|---|---|---|---|
| MS-001 | Repository created from starter kit | 2026-09-08 | A1 | — | Joanelis Rivera Matos | Done |
| MS-002 | Roles, charter and AI policy drafted | 2026-09-20 | A1 | MS-001 | Joanelis Rivera Matos | Done (charter has open TBDs) |
| MS-003 | Kickoff meeting held and decisions recorded | TBD | A1 | Team availability | Joanelis Rivera Matos | Not recorded |
| MS-004 | Working agreements and planning baseline merged | 2026-09-26 | A1 | PR reviews | Khaja Shabbir Ahmed | In Review |
| MS-005 | Initial requirements baseline (REQ-001..003) | 2026-09-26 | A1 | Slice agreed | Sydel Ugwu | Not started |
| MS-006 | A1 submitted and `a1-project-launch` tag on final commit | 2026-09-27 | A1 | MS-003..005 | Joanelis Rivera Matos | Planned |
| MS-007 | GitHub Issues, Cycle 1 milestone and Projects board set up | 2026-09-30 | A2 | MS-005 | Khaja Shabbir Ahmed | Planned |
| MS-008 | A2 planning package complete and `a2-planning-estimate` tag pushed | 2026-10-02 | A2 | MS-007 | Khaja Shabbir Ahmed | Planned |

## Phase-Gate Readiness

| Gate | Internal Readiness Target | Key Evidence / Deliverables | Status |
|---|---|---|---|
| A1 — Project Launch | 2026-09-26 (Sakai: end of Week 4, grace to end of Week 5 ≈ 2026-09-27) | README, charter, roles, working agreements, AI policy/log, initial requirements, planning baseline, decision record, tag | In progress |
| A2 — Planning & Estimation | 2026-10-02 (Sakai: end of Week 6 ≈ 2026-10-04, grace ≈ 2026-10-11) | Full planning package, Issues, Milestones, Projects board, team commitments, re-estimation, tag | Not started |
| A3 — Architecture & Design | TBD (per Sakai) | Architecture, ADRs, component responsibilities, API contracts | Not started |
| A4 — Implementation & Review | TBD (per Sakai) | Code for the Cycle 1 slice, PR reviews, tests | Not started |
| A5 — Verification & Release | TBD (per Sakai) | Test evidence, release notes, known limitations | Not started |
| A6 — Operational Maturity | TBD (per Sakai) | Observability, runbook, postmortem | Not started |

## Major Dependencies

| Predecessor / Dependency | Dependent Work | Schedule Impact if Delayed | Related Risk |
|---|---|---|---|
| Cycle 1 slice agreed | Requirements, scope confirmation, estimates | Blocks A1 requirements and all A2 planning | R-005 |
| Requirements baseline (REQ-001..003) | Traceability, GitHub Issues, A2 estimates | Delays A2 | R-005 |
| All A1 PRs merged | Moving the `a1-project-launch` tag | Wrong version graded | R-006 |
| Tech stack decision (ADR-001) and local setup | A3 architecture, A4 implementation | Delays implementation | R-007 |

## Near-Term Planning Window

| Time Window | Planned Outcome | Related Milestone(s) | Key Dependency / Risk |
|---|---|---|---|
| 2026-09-24 → 2026-09-27 | Finish A1: merge working agreements, planning baseline, requirements; fix charter TBDs; move tag | MS-004, MS-005, MS-006 | R-005, R-006 |
| 2026-09-28 → 2026-10-04 | A2: refine requirements, create Issues/Milestone/Board, full estimates, team commitments | MS-007, MS-008 | R-008 |

## Schedule Changes

| Date | Milestone / Gate | Previous Target | New Target | Reason | Related Evidence |
|---|---|---|---|---|---|
| 2026-09-24 | A1 | End of Week 4 (recommended target) | End of Week 5 (grace window) | Launch evidence not complete by the recommended target | This file, R-005 |

## Schedule Risks

| Risk ID | Affected Milestone / Gate | Schedule Exposure |
|---|---|---|
| R-005 | MS-005, A2 | High — requirements are the first dependency for all planning |
| R-006 | MS-006 | Medium — quick to fix but must be done before grading |
| R-008 | All gates | Medium — limited capacity across five members |
