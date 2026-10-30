# Code Review Evidence — Assignment 4

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup / Reviewer:** Khaja Shabbir Ahmed
**Gate:** A4 — Cycle 1 Construction and Integration · **Tracking issue:** #50
**Last updated:** 2026-10-29 · **Status as of:** `main` at `c80f777`

This file indexes the pull request reviews Hamza Bashar carried out during A4. The authoritative evidence is the review threads on each PR. This file summarizes each finding's risk category, fix direction, required evidence, and outcome, so a reviewer can see in one place what was raised and what happened to it.

## How Reviews Were Done

- Each finding is numbered `CRF-###` (code review finding) across all A4 reviews, so it can be referenced from other files.
- Every finding was checked against the code or document on the PR before posting, using the A4 review lenses: correctness, security, architecture, maintainability, dependencies, tests, and evidence.
- Where a finding depended on the build, the reviewer ran `mvn test` locally rather than relying on the PR's own claims.
- Merge decisions were recorded on GitHub (Request changes / Approve). Approvals were given only after the fixes were checked.

## Review Summary

| PR | Author | Subject | Findings | Decision | Outcome |
|---|---|---|---|---|---|
| #56 | Sydel Ugwu | ADR-0003: transport and simulated role | CRF-001..CRF-005, CRF-023 | Request changes → **Approved** | ADR fixed in `47b4379`; code follow-up requested (CRF-023) |
| #58 | Sydel Ugwu | Data Layer storage | CRF-006..CRF-010 | Request changes | Code reached `main` through #61 before the review (R-020); fixes still owed in a new PR |
| #62 | Sydel Ugwu | Unit tests | CRF-011..CRF-014 | Request changes | Open; no update since the review |
| #66 | Khaja Shabbir Ahmed | Risk register and known limitations | CRF-015..CRF-019 | Request changes → **Approved** | Fixed in `38ba88a`; merged `136cc55` |
| #74 | Khaja Shabbir Ahmed | Requirement and task traceability | CRF-020..CRF-022 | Request changes → **Approved** | Fixed in `fef4b2a`, `a99c99a`; merged `c80f777` |

## Findings

### PR #56 — ADR-0003

| ID | Category | Finding | Fix Direction | Required Evidence | Outcome |
|---|---|---|---|---|---|
| CRF-001 | Contract | Status endpoint accepted `POST` as well as `PATCH`; API-003 says `PATCH` | Remove `POST`, or update `api-contracts.md` | ADR and contract agree | ADR fixed (`47b4379`): `PATCH` only. Code on `main` still accepts `POST` (CRF-023) |
| CRF-002 | Contract | Invalid transition listed as "409 / 400", leaving the code undecided | Use `409` for invalid transitions and `400` for unknown status values (API-003) | One status code per case | ADR fixed (`47b4379`): `409` |
| CRF-003 | Security | `?role=` query fallback puts the role in URLs, browser history and logs | Remove the fallback; header only | ADR and code read the header only | ADR fixed (`47b4379`). Code on `main` still reads `?role=` (CRF-023, RF-06) |
| CRF-004 | Correctness | "STAFF calling API-001 if restricted" left the staff-submit rule open (C-Q3) | State the rule: staff denied (API-005) | ADR states the rule | ADR fixed (`47b4379`): only `STUDENT` may submit |
| CRF-005 | Evidence | "Verified in TASK-106" while #62 did not compile and CI was not set up | Say "to be verified" until tests pass in CI | Claim matches evidence | ADR fixed (`47b4379`): "to be verified" |

### PR #58 — Data Layer

| ID | Category | Finding | Fix Direction | Required Evidence | Outcome |
|---|---|---|---|---|---|
| CRF-006 | Data integrity | `save` uses `store.put`, so a duplicate ID silently replaces an existing request (REQ-005) | Use `putIfAbsent` and fail on duplicates | Test for duplicate save | Open on `main` |
| CRF-007 | Architecture / Safety | `clear()` on the production interface lets any caller wipe all requests; not in API-004 | Remove from the interface | Interface matches API-004 | Open on `main` |
| CRF-008 | Architecture | Data Layer generates IDs (`nextRequestId()`); API-001 says the Logic Layer assigns them | Move ID generation, or update the docs | Code and docs agree | Open on `main` |
| CRF-009 | Evidence | `synthetic` is always `true`, so the REQ-004 test can never fail | Verify AC-REQ-004-01 by inspection, not this test | Traceability shows inspection | Partly addressed: `traceability.md` (#74) lists repository inspection TC-011 as still to run |
| CRF-010 | Tests | Missing: unknown/`null`/blank ID lookup, empty list (AC-REQ-002-02), duplicate save | Add the cases | Tests on `main` | Open |

### PR #62 — Unit tests

Reviewer ran `mvn test` on `496fe55` (2026-10-29 20:42): 18 run, 0 failures, BUILD SUCCESS.

| ID | Category | Finding | Fix Direction | Required Evidence | Outcome |
|---|---|---|---|---|---|
| CRF-011 | Traceability | Rejected-transition test labelled AC-REQ-003-02; that criterion is a success case. Rejected transitions are AC-REQ-003-03 | Relabel | `test-strategy.md` matches `acceptance-criteria.md` | Open |
| CRF-012 | Traceability | Student-denied test mapped to AC-REQ-006-01, which is a code-review criterion | Map to AC-REQ-002-03 and AC-REQ-003-04 | Same | Open |
| CRF-013 | Evidence | Every row says "Implemented & Passing" with no evidence linked; no CI | Link a run (RUN-### or CI) or state "passing locally on [date]" | Evidence link per row | Open |
| CRF-014 | Tests | Missing: staff list fields, empty list, new submission and status change visible in the list, backward and same-status moves, unknown ID, `null` status | Add the cases | Tests on `main` | Open. Backward and same-status moves now covered by `RequestStatusTest` (#73). Rest shared with Hamza in `traceability.md` |

### PR #66 — Risk register and known limitations

| ID | Category | Finding | Fix Direction | Required Evidence | Outcome |
|---|---|---|---|---|---|
| CRF-015 | Currency | "No application code is merged to `main`" was no longer true after #61 | Re-check against `main` | Review basis names a `main` commit | Fixed (`38ba88a`) |
| CRF-016 | Currency | RF-01, RF-03..RF-05 fixed on `main`; RF-02 and RF-06 still present | Update dispositions with evidence | Each row cites a test or PR | Fixed (`38ba88a`) |
| CRF-017 | Currency | "Tests in #62 cannot run" was out of date | Record the 18/18 run | Updated verification row | Fixed (`38ba88a`) |
| CRF-018 | Traceability | LIM-008 / R-017 cited Q-007 as open; answered in #70 | Link #70; move LIM-008 to Accepted once merged | LIM-008 status | Fixed (`38ba88a`); moved to Accepted in #74 after #70 merged |
| CRF-019 | Risk | R-020 (stacked PRs) had happened: #61 brought #57 and #58 onto `main` before review | Record as materialized; fixes for #58 in a new PR | Materialized-risk row | Fixed (`38ba88a`) |

### PR #74 — Traceability

| ID | Category | Finding | Fix Direction | Required Evidence | Outcome |
|---|---|---|---|---|---|
| CRF-020 | Traceability | TASK-106 listed only #62, so Hamza's merged tests (#71) did not appear | Add #71 and #73 with branches | TASK-106 row | Fixed (`fef4b2a`, `a99c99a`) |
| CRF-021 | Ownership | Missing-test owner was Hamza only, though the same cases were requested from Sydel in #62 (CRF-014) | Share ownership with #62 | Gap row owner | Fixed (`fef4b2a`) |
| CRF-022 | Ownership | REQ-003 test gap owned by Hamza for both #62 (Sydel's) and #73 | Name each PR's owner | Gap row owner | Fixed; gap row removed after #73 merged (`a99c99a`) |

## Follow-Up Findings

| ID | Category | Finding | Fix Direction | Outcome |
|---|---|---|---|---|
| CRF-023 | Architecture consistency | After #56's update, ADR-0003 says the status endpoint is `PATCH` only and the role is read from the header only. `CampusConnectApp` on `main` still accepts `POST` (line 147) and reads `?role=` (lines 125–127) | Change the code to match the ADR in a follow-up PR, or note the difference in the ADR before it merges | Open — raised in the #56 re-review (2026-10-29), ADR approved |

## Findings Considered and Not Raised

Recorded so the review shows judgment, not only the comments that were posted.

| PR | Considered | Reason Not Raised |
|---|---|---|
| #56 | ADR status says "Accepted" before review | The PR cannot merge without reviewer approval, so the label does not change the outcome |
| #62 | PR also carried a 194-line `CampusConnectApp` change and conflicted with `main` | Re-checked against `main`: the change was already on `main` through #61, so it would drop out of the diff; the only conflict was `ai-use-log.md` (noted in the summary instead) |
| #56 | `requireRole(rawRole, expectedRole)` puts the role rules in each caller instead of in `RoleCheckService` | Left out of the posted review; the limitation is recorded in the PR #71 description and can be raised again if role rules change |

## Process Observations

- PRs #57 and #58 reached `main` through #61 before their own reviews finished (R-020). The #58 findings now apply to merged code.
- PRs #61 and #68 were merged before the requested review by Hamza took place.
- No PR had a CI check during these reviews (R-019); build and test evidence came from local `mvn test` runs by the reviewer and author.

## AI Assistance

Claude helped draft review findings and this file, at Hamza's request. Hamza checked each finding against the code before posting and reworded, dropped or re-numbered findings. Disclosed in `docs/ai/ai-use-log.md`.