# Cycle 2 Maturity Target Disposition

**Owner:** Khaja Shabbir Ahmed (Planning & Process Lead) · **Backup / Reviewer:** Hamza Bashar
**Gate:** A6 — Final Cycle 2 Release · **Last updated:** 2026-11-24 · **Tracking issue:** #123
**Status as of:** `main` at `1bb17f4` (the A5 release; nothing has merged to `main` since 2026-11-10) and the open A6 pull requests listed below. Re-check every disposition on the final commit before the `a6-final-release` tag.

This file answers one question for each Cycle 2 target: **why was it chosen, what proof was expected, and what actually happened?** A target counts as **Done** only when its proof is on `main`. Work in an open pull request is **In review**, and work that was not started is **Not done**.

## How the Targets Were Chosen

Every target comes from Cycle 1 evidence, not from a feature wish list:

| Source | What it showed |
|---|---|
| [`known-limitations.md`](known-limitations.md) | LIM-001 (data lost on restart), LIM-002 (simulated role), LIM-004 (no web page), LIM-010 (no reopening) |
| [`defect-review.md`](../quality/defect-review.md) | 9 open findings waiting on unmerged PRs; improvement actions QA-001..QA-006 |
| [`risk-register.md`](../planning/risk-register.md) | R-006 (tags on the wrong commit), R-021 (merges before approval), R-022 (hand-written JSON) |
| A4 instructor assessment | Conditions: review and CI before merge; end-to-end acceptance test; reconcile ADRs and code |
| A5 presentation (slide 6) and [`cycle2-handoff.md`](cycle2-handoff.md) | Cycle 2 priorities: durable storage, real authentication, controlled reopening, logging/health |

## Target Disposition

| ID | Target | Cycle 1 Evidence | Expected Proof | Owner | Disposition | Risk Reduced? |
|---|---|---|---|---|---|---|
| MT-01 | Document what the running system logs and how failures are seen | TASK-108; LIM-001; rubric (observability) | `docs/observability/observability-plan.md` | Nova Kurul | **In review** (PR #124). Documents the existing `AuditLogger` events and CI signals. No `/health` endpoint or log file was built | Partly: what can be observed is now written down; nothing new can be observed |
| MT-02 | Security and governance review of the final release | LIM-002; REQ-004; R-016; rubric (governance) | `docs/security/security-governance-checklist.md` with risk owners | Nova Kurul | **In review** (PR #124) | Partly: risks and owners are recorded; no control was changed |
| MT-03 | Merge the Data Layer fixes and the end-to-end acceptance test | CRF-006, CRF-007, CRF-010; QA-001; A4 condition 2 | PR #95 merged; 41 tests passing in CI on `main` | Sydel Ugwu / Hamza Bashar | **Not done.** PR #95 is approved but has a merge conflict and is not merged | No: the two High findings (CRF-006, CRF-007) are still open on `main` |
| MT-04 | Require an approval and a passing CI check before any merge to `main` | R-020, R-021; QA-002; A4 condition 4 | Branch protection rule on `main` | Joanelis Rivera Matos | **Not done.** No branch protection is configured | No: merges still depend on each person following the practice |
| MT-05 | Release baseline on the correct commit | R-006 (A1, A2 and A5 tags) | `a6-final-release` on the final `main` commit, checked with `git ls-remote --tags origin` | Joanelis Rivera Matos (#107) | **Pending** until the tag is created tonight | To be confirmed at tagging |
| MT-06 | Runbook: how to set up, run, diagnose and recover the system | Rubric (operational readiness); manual start (known limitations) | `docs/operations/runbook.md` | Sydel Ugwu (#116) | **Not done.** No pull request | No |
| MT-07 | Durable storage (SQLite) behind the Data Layer interface | LIM-001, R-013 | Restart test passes; LIM-001 resolved | Sydel Ugwu / Hamza Bashar | **Deferred** (not started) | No |
| MT-08 | Real authentication in place of the simulated role | LIM-002 | Login tests; `X-Simulated-Role` removed | Sydel Ugwu / Nova Kurul | **Deferred** (not started) | No |
| MT-09 | Controlled reopening of resolved requests with an audit comment | LIM-010, Q-002 | Transition and audit tests | Hamza Bashar / Khaja Shabbir Ahmed | **Deferred** (not started) | No |
| MT-10 | Browser page for students and staff | LIM-004 | Pages served by the existing server | No owner was assigned | **Deferred** (not started) | No |

**Summary:** of 10 targets, 0 are done on `main`, 2 are in review (MT-01, MT-02, both documentation), 1 is pending (MT-05), 3 were planned but not done (MT-03, MT-04, MT-06), and 4 were deferred without being started (MT-07..MT-10).

## Was the Intended Risk Reduction Achieved?

Not for the code. The Cycle 1 limitations and the two open High findings (CRF-006, CRF-007) are still present in the release, because no Cycle 2 code change has reached `main`. Cycle 2 so far improved how the release is **described** (observability, security and governance documents in PR #124; final release notes and presentation materials in progress), not how it **behaves**.

The two targets with the highest value for the least effort were MT-03 (merge the approved PR #95) and MT-04 (turn on branch protection). Both can still be done before the tag; if they are, update their rows.

## Why the Code Targets Slipped

- **Scope versus time.** Storage, authentication, reopening and a web page were each planned as Cycle 2 work, but no issue or estimate was created for them; the A6 issues (#105–#123) cover evidence documents only.
- **Late start.** The first A6 pull request was opened on 2026-11-23, one day before the deadline.
- **The same pattern as A4 and A5.** Work was finished close to the gate, which left little time for review (R-021).

The deferred targets and their owners are carried into [`known-limitations.md`](known-limitations.md) and [`risk-register.md`](../planning/risk-register.md) as residual risks.

## Change History

| Date | Change |
|---|---|
| 2026-11-24 | First version for A6 (#123), as of `main` `1bb17f4` and open PR #124 |
