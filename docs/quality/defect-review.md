# Defect Review

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup / Reviewer:** Khaja Shabbir Ahmed
**Gate:** A5 — Cycle 1 Release · **Tracking issue:** #85 (Defect Review Summary)
**Last updated:** 2026-11-10 · **Status as of:** `main` at `3c579b4`

## Review Scope

**Review Date:** 2026-11-10
**Review Period / Gate:** A4 (construction) through A5 (Cycle 1 release), 2026-10-29 to 2026-11-10
**Defects Reviewed:** 31 — runtime findings RF-01..RF-06 and code review findings CRF-001..CRF-025

The team did not keep a separate `DEF-###` log in Cycle 1. Defects were recorded where they were found, and this review uses those IDs instead of renumbering them:

| ID range | What it is | Where it is recorded |
|---|---|---|
| RF-01..RF-06 | Runtime findings from local builds and HTTP checks | [`known-limitations.md`](../release/known-limitations.md), `test-evidence/2026-10-29-local-test-runs.md` |
| CRF-001..CRF-023 | Code review findings from A4 pull request reviews | [`code-review-a4.md`](../reviews/code-review-a4.md) and the review threads on PRs #56, #58, #62, #66, #74 |
| CRF-024, CRF-025 | Code review findings from A5 reviews | Review threads on PR #97 (CRF-024) and PR #95 (CRF-025) |

Not counted as defects: known limitations LIM-001..LIM-010 (accepted or deferred scope, not faults) and architecture review findings ARF-001..ARF-003 (A3, see `architecture-review.md`).

A finding is **Closed** only when the fix is on `main`. A fix that sits in an approved but unmerged pull request is still **Open**.

## Defect Summary

| Measure | Count |
|---|---:|
| Total defects reviewed | 31 |
| Open | 10 |
| Closed | 19 |
| Deferred | 2 |
| Critical / High | 0 Critical, 4 High (RF-01, RF-02, CRF-006, CRF-007) |

Of the 4 High defects, RF-01 and RF-02 are closed on `main`. CRF-006 and CRF-007 are fixed in PR #95, which is approved but not merged.

Of the 10 open defects, 9 have a fix waiting in an unmerged pull request (#56 or #95). Only CRF-025 has no fix yet.

### Disposition of Each Defect

| ID | Summary | Severity | Status | Owner | Disposition / Mitigation |
|---|---|---|---|---|---|
| RF-01 | Build failed: 74 compile errors in `CampusConnectApp.java` | High | Closed | Khaja Shabbir Ahmed | Fixed in PR #67; CI passes on `main` |
| RF-02 | `GET /api/requests` returned a count, not the requests | High | Closed | Joanelis Rivera Matos | Fixed in PR #76; HTTP list check in `CampusConnectAppRoleTransportTest` |
| RF-03 | Value with an escaped quote was cut off; invalid JSON | Medium | Closed | Khaja Shabbir Ahmed | PR #67; test `quotesAndBackslashesInFieldsArePreserved` |
| RF-04 | Validation-error response was not valid JSON | Medium | Closed | Khaja Shabbir Ahmed | PR #67; test `missingFieldsReturn400ListingEveryMissingField` |
| RF-05 | Submit response left out `submittedAt` and `description` | Medium | Closed | Khaja Shabbir Ahmed | PR #67; test `validSubmissionReturns201WithSystemAssignedIdStatusAndTime` |
| RF-06 | `?role=` read only as the first query parameter; role allowed in the URL | Low | Closed | Khaja Shabbir Ahmed | Issue #96, PR #97 (merged `e9f93e6`) |
| CRF-001 | Status endpoint accepted `POST` as well as `PATCH` | Medium | Open | Sydel Ugwu | Code fixed on `main` (#97). ADR-0003 text fixed in `47b4379`, but PR #56 is not merged |
| CRF-002 | Invalid transition listed as "409 / 400" in ADR-0003 | Low | Open | Sydel Ugwu | ADR fixed in `47b4379` (PR #56, not merged). Code on `main` already returns `409` |
| CRF-003 | `?role=` fallback puts the role in URLs and logs | Medium | Open | Sydel Ugwu | Code fixed on `main` (#97). ADR text waits on PR #56 |
| CRF-004 | Staff-submit rule left open in ADR-0003 | Low | Open | Sydel Ugwu | ADR fixed in `47b4379` (PR #56, not merged). Code denies staff; test `staffOrMissingRoleCannotSubmit` |
| CRF-005 | ADR-0003 said "Verified" before tests ran | Low | Open | Sydel Ugwu | ADR fixed in `47b4379` (PR #56, not merged) |
| CRF-006 | `save` silently replaced a request with the same ID | High | Open | Sydel Ugwu | Fixed in PR #95 (`00879c7`, approved, not merged). On `main`, IDs come only from `nextRequestId()`, so no caller can trigger it today |
| CRF-007 | `clear()` on the production interface could wipe all requests | High | Open | Sydel Ugwu | Removed in PR #95 (not merged). On `main`, no production code calls `clear()` |
| CRF-008 | Data Layer generates IDs; API-001 says the Logic Layer does | Low | Deferred | Sydel Ugwu | Behaviour is correct; code and document disagree. Cycle 2: move the code or update API-001 |
| CRF-009 | `synthetic` is always `true`, so the REQ-004 test cannot fail | Low | Closed | Khaja Shabbir Ahmed | REQ-004 is now verified by inspection: TC-011 run on `e9f93e6`, Pass (RUN-010, PR #100) |
| CRF-010 | Missing Data Layer tests: unknown / `null` / blank ID, empty list, duplicate save | Medium | Open | Sydel Ugwu | 3 tests added in PR #95 (not merged) |
| CRF-011 | Rejected-transition test labelled with a success criterion | Low | Closed | Sydel Ugwu | The mislabelled `test-strategy.md` was in PR #62, which never merged. `main` has the correct labels |
| CRF-012 | Student-denied test mapped to a code-review criterion | Low | Closed | Sydel Ugwu | Same as CRF-011 |
| CRF-013 | "Implemented & Passing" claims with no evidence linked | Low | Closed | Sydel Ugwu | Same as CRF-011; `test-strategy.md` on `main` does not report completed tests |
| CRF-014 | Missing Logic Layer tests (list content, empty list, state after changes, unknown ID) | Medium | Open | Sydel Ugwu, Hamza Bashar | Backward and same-status moves covered by `RequestStatusTest` (#73). The rest are in `Cycle1AcceptanceTest` in PR #95 (not merged) |
| CRF-015..CRF-019 | Out-of-date statements in the risk register and known limitations (5 findings) | Low | Closed | Khaja Shabbir Ahmed | Fixed in `38ba88a`; merged in PR #66 |
| CRF-020..CRF-022 | Missing PRs and wrong owners in traceability (3 findings) | Low | Closed | Khaja Shabbir Ahmed | Fixed in `fef4b2a`, `a99c99a`; merged in PR #74 |
| CRF-023 | Code on `main` did not match ADR-0003 (`POST`, `?role=`) | Medium | Closed | Khaja Shabbir Ahmed | PR #97; `CampusConnectAppRoleTransportTest` (5 tests) |
| CRF-024 | Wrong method on the status route returns `404`, not `405` | Low | Deferred | Khaja Shabbir Ahmed | Request is still refused, only the code differs. Deferred to Cycle 2 |
| CRF-025 | `Cycle1AcceptanceTest` calls the services directly, not over HTTP | Low | Open | Sydel Ugwu | Raised on PR #95. HTTP behaviour is covered separately by `CampusConnectAppSubmitTest` and `CampusConnectAppRoleTransportTest` |

## Defect Distribution

| Category | Defect IDs | Count | Observation |
|---|---|---:|---|
| Implementation (Presentation Layer) | RF-01..RF-06, CRF-023, CRF-024 | 8 | All in `CampusConnectApp.java`. Most came from JSON built and parsed by hand |
| Implementation (Data Layer) | CRF-006, CRF-007 | 2 | Both are data-loss paths in the repository; both High |
| Interface contract / architecture | CRF-001..CRF-004, CRF-008 | 5 | ADR-0003 and the code each disagreed with `api-contracts.md` |
| Test gaps | CRF-009, CRF-010, CRF-014, CRF-025 | 4 | Success paths were tested first; empty, unknown and duplicate cases came later |
| Evidence and documentation accuracy | CRF-005, CRF-011..CRF-013, CRF-015..CRF-022 | 12 | Largest group. Documents claimed more than the evidence showed, or were out of date after a merge |

## Recurring Patterns

| Pattern | Supporting Defects / Evidence | Engineering Significance |
|---|---|---|
| Documents state something that is not true on `main` | CRF-005, CRF-013, CRF-015..CRF-017; risk R-023 | Readers cannot trust a status without checking the code. This is 12 of 31 findings |
| Hand-written JSON | RF-01, RF-03, RF-04, RF-05; risk R-022 | Four defects from one design choice in one file |
| Code and contract drift apart | CRF-001, CRF-003, CRF-008, CRF-023 | The contract was written first and not re-checked when code changed |
| Fixes approved but not merged | 9 of the 10 open defects wait on PR #56 or PR #95 | The defect is solved on a branch, but the release does not contain the fix |
| Code reached `main` before its review finished | PRs #57 and #58 through #61 (R-020); PR #68 (R-021) | CRF-006..CRF-010 applied to merged code and needed a second PR |

## Contributing Factors

| Contributing Factor | Supporting Evidence | Effect |
|---|---|---|
| No CI until 2026-10-29 (R-019) | RF-01: a branch with 74 compile errors was opened as a PR | Build failures were found by hand |
| No branch protection | `ci-evidence.md` (A4); PR #75 merged while changes were requested | Review and CI are team practice, not enforced |
| Stacked pull requests (R-020) | #61 contained #57 and #58 | Unreviewed code merged with reviewed code |
| Work finished close to the deadline | Most A4 PRs opened and merged on 2026-10-29; A5 PRs on 2026-11-09/10 | Little time between review and merge; fixes left on branches |
| Every PR edits `ai-use-log.md` | Merge conflicts on PRs #62, #66, #95 | Approved PRs stall on a conflict that has nothing to do with the change |

## Defect Escape Analysis

| Defect ID | Detected At | Earlier Detection Opportunity | Improvement |
|---|---|---|---|
| RF-01 | Reviewer's local `mvn test` on PR #61 | Author's own build before opening the PR; CI | CI on every PR (in place since PR #72) |
| RF-02 | Reviewer's HTTP check after merge | A test for AC-REQ-002-01 before merge | Each acceptance criterion gets a test in the same PR as the code |
| CRF-006, CRF-007 | Review of PR #58, after the code was already on `main` | Review before merge | Do not stack PRs; merge only after approval (QA-002) |
| CRF-023 | Re-review of PR #56 | When the ADR changed, check the code in the same PR | A contract or ADR change lists the code it affects |
| RF-06 | HTTP check in A4, fixed in A5 | A test with more than one query parameter | Closed by removing the feature (#97) |

## Quality Strengths

| Strength | Supporting Evidence | Why It Matters |
|---|---|---|
| No defect was found by a user or in the demo | All 31 were found by review, local runs or tests | Defects were caught before release |
| Reviewers ran the code | Local `mvn test` on PRs #62 and #95; RUN-001..RUN-009 | Findings were based on results, not on reading alone |
| Fixes came with tests | PR #67 (3 tests), PR #97 (5 tests), PR #95 (5 tests) | A fixed defect stays fixed |
| Findings have IDs and an outcome | `code-review-a4.md`, `known-limitations.md` | Each defect can be followed from finding to fix |
| CI now runs on every PR and on `main` | `.github/workflows/ci.yml`; 36 tests on `main` | A broken build is now a visible failed check |

## Improvement Actions

| Action ID | Improvement | Evidence / Driver | Owner | Target | Status |
|---|---|---|---|---|---|
| QA-001 | Merge PR #95 (after its `ai-use-log.md` conflict is fixed) and PR #56, or record them as deferred | 9 open defects wait on them | Sydel Ugwu | Before the final A5 baseline | Open |
| QA-002 | Turn on branch protection for `main`: one approval and a passing CI check | R-020, R-021; no branch protection | Joanelis Rivera Matos | Start of Cycle 2 | Open |
| QA-003 | Replace hand-written JSON with a library, or keep it in one tested helper | RF-01, RF-03..RF-05; R-022 | Sydel Ugwu | Cycle 2 | Open |
| QA-004 | Run TC-011 (synthetic-data inspection) and record the result | CRF-009 | Khaja Shabbir Ahmed | Before the final A5 baseline | Done (RUN-010, PR #100) |
| QA-005 | Status claims in documents name a `main` commit or a PR | CRF-005, CRF-013, CRF-015..CRF-017; R-023 | Hamza Bashar | Cycle 2, checked in review | Open |
| QA-006 | Decide CRF-008 (ID generation) and CRF-024 (`405` vs `404`) and update code or contract | Deferred defects | Sydel Ugwu, Khaja Shabbir Ahmed | Cycle 2 | Open |

## Engineering Evidence to Update

| Finding / Defect | Artifact / Evidence | Required Update | Owner | Status |
|---|---|---|---|---|
| CRF-006, CRF-007, CRF-010, CRF-014 | `docs/release/assignment5-release-package.md`, `cycle2-handoff.md` | These files describe the fixes and `Cycle1AcceptanceTest` as part of the release; true only after PR #95 merges | Sydel Ugwu | Open |
| CRF-009 | `test-evidence/2026-10-29-local-test-runs.md` (RUN-010) | Inspection result recorded | Khaja Shabbir Ahmed | Done (PR #100) |
| CRF-001..CRF-005 | ADR-0003 | Not on `main` until PR #56 merges | Sydel Ugwu | Open |
| CRF-024, CRF-025 | [`code-review-a4.md`](../reviews/code-review-a4.md) | Index covers CRF-001..CRF-023 only; this file is the record for the A5 findings | Hamza Bashar | Done (recorded here) |
| All | `docs/quality/defect-log.md` | Still the starter template; Cycle 1 defects are recorded under RF and CRF IDs (see Review Scope) | Hamza Bashar | Open (Cycle 2) |

## Previous Review Actions

This is the first defect review. The closest earlier actions are from the A3 architecture review:

| Action | Status at This Review |
|---|---|
| ARA-001..ARA-003 (fixes to `architecture.md`) | Not re-checked in this review; see `architecture-review.md` |

## Review Conclusion

**Trend:** The findings changed in kind between A4 and A5. A4 found build and behaviour defects (RF-01..RF-05, CRF-006, CRF-007). A5 found only two new defects, both Low (CRF-024, CRF-025). No High defect has been found since 2026-10-29.

**State of the release:** 19 of 31 defects are closed on `main`, including every runtime finding. No Critical defect was found. The two open High defects (CRF-006, CRF-007) are fixed in PR #95 and cannot be triggered by the code on `main` today, but the fix is not in the release until that PR merges.

**Main weakness:** not the code, but finishing: fixes left on unmerged branches, and documents that describe those fixes as done. QA-001 and QA-005 address this.

**Deferred to Cycle 2:** CRF-008, CRF-024, and the limitations LIM-004..LIM-007 and LIM-009 in `known-limitations.md`.

AI assistance in preparing this file is recorded in `docs/ai/ai-use-log.md`.