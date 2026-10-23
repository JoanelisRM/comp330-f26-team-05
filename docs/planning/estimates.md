# Estimates

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead; A3 issue #17) · **Backup:** Hamza Bashar (A2 estimates owner)
**Gate:** A3 — Cycle 1 Architecture and Review · **Last updated:** 2026-10-21

## Estimation Approach

- Effort in hours, given as **low / likely / high**. An estimate is a working judgment, not a promise.
- Estimates include review, rework and tests — not just writing code.
- Build estimates below are the **first estimates of Cycle 1 implementation**. They are based on ADR-001 (local Java layered application), the API contracts (API-001..005) and the in-memory storage decision (Q-003).
- They were drafted by the Planning Lead and **must be confirmed by each task owner** at the next team check-in; changes are recorded in `re-estimation.md`.

## Cycle 1 Build Estimates (A4 work)

| ID | Task | Low | Likely | High | Basis | Assumptions | Owner (proposed) | Confidence |
|---|---|---:|---:|---:|---|---|---|---|
| EST-111 | TASK-111 Framework / transport / role-passing decision (ADR) | 1 | 2 | 4 | One ADR with two or three options | PA-006 | Sydel Ugwu | Medium |
| EST-101 | TASK-101 Maven skeleton with three layer packages; builds on every machine | 2 | 4 | 8 | Setup across five laptops; first Java project for some members | PA-006, R-007 | Sydel Ugwu | Low |
| EST-102 | TASK-102 Data Layer interface + in-memory implementation | 2 | 4 | 6 | Small interface (API-004): save, list, get, update status | PA-005 | Sydel Ugwu | Medium |
| EST-110 | TASK-110 Simulated role check in one place (API-005) | 2 | 3 | 5 | Two roles, one check used by all operations | PA-006 | Hamza Bashar | Medium |
| EST-103 | TASK-103 Submit request: form, validation, ID and timestamp (API-001) | 4 | 6 | 10 | UI form + Logic validation + error display | PA-006 | Khaja Shabbir Ahmed | Low |
| EST-104 | TASK-104 Staff request list (API-002) | 3 | 5 | 8 | Read-only list view | PA-006 | Joanelis Rivera Matos | Medium |
| EST-105 | TASK-105 Status change with allowed transitions only (API-003) | 4 | 6 | 10 | Transition rules + rejection path + UI control | PA-006, Q-002, Q-007 | Hamza Bashar | Low |
| EST-106 | TASK-106 Unit tests: validation, transitions, role checks | 4 | 6 | 10 | Positive and negative cases from acceptance criteria | — | Hamza Bashar | Medium |
| EST-107 | TASK-107 CI build and tests on every PR | 1 | 3 | 6 | Existing `ci.yml` to adapt | — | Nova Kurul | Medium |
| EST-108 | TASK-108 Logging of failed submissions and status changes | 1 | 2 | 4 | Standard Java logging | Q-007 | Nova Kurul | Medium |
| EST-109 | TASK-109 End-to-end integration checkpoint | 2 | 3 | 5 | One full run of the slice on `main` | PA-005 | Joanelis Rivera Matos | Medium |
| **Total** | | **26** | **44** | **76** | | | | |

## Planning-Artifact Estimates (A1 history)

EST-001..EST-013 (A1/A2 planning work) are kept in the A1 version of this file (PR #2). Actual hours were **not recorded** for those tasks, so no estimate-vs-actual comparison is possible for A1/A2 (see Estimate vs. Actual).

## Estimation Assumptions

| Assumption Reference | Estimate(s) Affected | Effect if Incorrect |
|---|---|---|
| PA-003: Each member can give roughly 3–5 hours per week (not yet confirmed by every member) | All | Build takes longer; scope cuts needed (R-008) |
| PA-005: In-memory storage is enough for Cycle 1 (Q-003); no persistence after restart | EST-102, EST-109 | Adding file or database storage adds roughly 4–8 hours (R-013) |
| PA-006: The framework chosen in TASK-111 is simple enough for the whole team to learn quickly | EST-101, EST-103..105, EST-110 | Learning curve could push most tasks toward their high estimate (R-007, R-014) |
| PA-007: Q-002 is answered "no reopening" and Q-007 needs only basic logging | EST-105, EST-108 | Reopening or full audit history adds roughly 3–6 hours |

## Estimate Confidence

| Estimate ID | Confidence | Reason |
|---|---|---|
| EST-102, EST-104, EST-106..EST-111 | Medium | Contract is defined and the work is small |
| EST-101, EST-103, EST-105 | Low | Depend on the undecided framework (R-014) and open questions Q-002/Q-007 (R-017) |

## Estimate Changes

| Date / Gate | Estimate ID | Previous Estimate | New Estimate | Reason for Change |
|---|---|---|---|---|
| 2026-10-21 / A3 | EST-101..EST-111 | Not estimated (reserved in A2) | 26 / 44 / 76 hrs total | First build estimates after ADR-001, API contracts and Q-003 |

## Estimate vs. Actual

| Estimate ID | Estimated | Actual | Variance | What We Learned |
|---|---|---|---|---|
| EST-001..EST-013 | See PR #2 | Not recorded | — | Actuals were not tracked in A1/A2. From A4, each owner records actual hours on the issue when closing it. |

## Planning Implications

| Estimate / Evidence | Planning Impact | Related Scope / Schedule / Risk |
|---|---|---|
| Likely build effort ≈ 44 hrs (26–76) | At PA-003 capacity (≈15–25 team hrs/week) the build needs about **2–3 weeks likely, up to 5 weeks** at the high end | `schedule.md` A4; R-008 |
| TASK-111 blocks everything | The framework decision must be the first task of A4 | R-014 |
| Three Low-confidence tasks sit on the critical path (EST-101, EST-103, EST-105) | Pair members on these, and re-estimate after the first A4 week | `re-estimation.md`, R-007 |
