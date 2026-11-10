# Release Workflow Evidence — Cycle 1 Release

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup / Reviewer:** Khaja Shabbir Ahmed
**Gate:** A5 — Cycle 1 Release · **Tracking issue:** #89 (Release Workflow Evidence)
**Last updated:** 2026-11-10 · **Status as of:** `main` at `aaf77c8`, GitHub pages read at about 01:12 CST

The evidence for this item is the repository history itself: Issues, branches, commits, Pull Requests, Actions runs, reviews and tags. This file is an index to that history for the A5 release. It shows how each piece of A5 work moved from an issue to `main`, and where the workflow was not followed.

## How Work Reaches `main`

| Step | What Happens | Where It Is Visible |
|---|---|---|
| 1. Issue | Each A5 deliverable has an issue with an owner and a backup | Issues tab, label `Cycle 1 Release Evidence Package` |
| 2. Branch | The owner works on a branch named after the issue (for example `docs/defect-review-85`) | Branch list; branch name on each PR |
| 3. Commits | Commit messages name the issue number | Commit history |
| 4. Pull Request | The PR says `Closes #NN` and names the backup as reviewer | Pull requests tab |
| 5. CI | `Java CI with Maven` runs on the PR, and again on `main` after the merge | Actions tab; check mark on each PR |
| 6. Review | The reviewer comments, requests changes or approves | Review thread on the PR |
| 7. Merge | Merge commit on `main`; the issue closes automatically | `git log --first-parent main` |
| 8. Baseline | The final `main` commit is tagged `a5-cycle1-release` | Tags page |

Steps 5 and 6 are team practice. GitHub does not enforce them, because `main` has no branch protection.

## A5 Work: Issue to `main`

All merges below are on `main` after the A4 tag (`a4-construction-integration`, `157d3a2`). Times are CST.

| Issue | Deliverable | Branch | PR (Author) | Commits | CI on PR / on `main` | Review | Merged | Issue State |
|---|---|---|---|---|---|---|---|---|
| #79 | A5 release README, Cycle 2 handoff | `sydel/issue-79-a5-release-readme` | #93 (Sydel) | `dd9e3d3` | #14 / #17 passed | Approved | `5fa8dfd`, 11-09 22:28 | Closed |
| #82 | Changed-file summary | `sydel/issue-82-a5-changed-files` | #94 (Sydel) | `67248d5` | #15 / #18 passed | Approved | `706f9bf`, 11-09 22:34 | Closed |
| #84, #87 | CI/CD final evidence, AI-use log | `a5-nova-final-evidence` | #99 (Nova) | `72c868a` | #21 / #22 passed | **None** | `b3613ae`, 11-09 23:26 | Closed |
| #96 | RF-06 fix (code) | `fix/role-header-only-rf06` | #97 (Khaja) | `a8a2872` | #19 / #23 passed | Approved by Hamza (finding CRF-024) | `e9f93e6`, 11-10 00:28 | Closed |
| #86 | Known limitations, residual risks | `release/known-limitations-86` | #98 (Khaja) | `766e4e9`, `b199663` | #20, #24 / #26 passed | Approved by Hamza; last update merged before a re-review | `321aef6`, 11-10 00:36 | Closed |
| #88 | Traceability update | `planning/traceability-88` | #100 (Khaja) | `f10464e`, `c3555ff` | #25, #27 passed | Approved by Hamza (CRF-026, CRF-027) | `3c579b4`, 11-10 00:55 | Closed |
| #83 | Final testing evidence | `docs/final-test-evidence-83` | #102 (Hamza) | `db6e86c`, `14d3fab` | Passed | Approved by Khaja (1 fix, applied) | `961d10f`, 11-10 01:07 | Closed |
| #85 | Defect review summary | `docs/defect-review-85` | #101 (Hamza) | `ad78bfe`, `41b38ed`, `7a6efe2` | #28 passed | Approved by Khaja (4 fixes, applied) | `aaf77c8`, 11-10 01:10 | Closed |

Eight A5 pull requests merged. Seven were approved before merge; one (#99) had no review. Every one had a passing CI check.

### A5 Work Not Yet on `main`

| Issue | Deliverable | PR | State |
|---|---|---|---|
| — | Data Layer fixes (CRF-006, CRF-007, CRF-010) and `Cycle1AcceptanceTest` | #95 (Sydel) | Open. Approved, CI passed. Not merged |
| #80, #90 | Final release notes, presentation index, demo script | #92 (Joanelis) | Open. CI passed, no review yet |
| #81 | Release baseline | Tag | Open. Tag is on an older commit (see Release Baseline) |
| #91 | Cycle 2 handoff | #93 (merged) | Issue still open |
| #89 | Release workflow evidence | This file | Open |

## One Change Followed End to End

The RF-06 fix is the only A5 code change on `main`, so it is the clearest example of the full workflow.

| Step | Evidence |
|---|---|
| Found | Local HTTP check in A4 (RUN-005); review of PR #56 (CRF-003, CRF-023) |
| Issue | #96 "A5: Fix RF-06 / CRF-023 — read role from header only, PATCH only for status" |
| Branch | `fix/role-header-only-rf06` |
| Commit | `a8a2872` "Read simulated role from header only and accept PATCH only for status (RF-06, CRF-003, CRF-023)" |
| Tests | `CampusConnectAppRoleTransportTest`, 5 tests added in the same commit |
| Pull Request | #97, opened 2026-11-09 23:13 |
| CI on the PR | Run #19, passed |
| Review | Approved by Hamza Bashar before merge; one finding recorded (CRF-024, deferred) |
| Merge | `e9f93e6`, 2026-11-10 00:28, by Khaja |
| CI on `main` | Run #23, passed |
| Verified after merge | `mvn test` 36/36 and the 12-step HTTP run on `321aef6` (`final-test-evidence.md`, FT-001, FT-002 step 11) |
| Recorded | `known-limitations.md` (RF-06 Resolved), `defect-review.md`, `traceability.md` section 3 |

## CI/CD Checks

- Workflow: `Java CI with Maven`, `.github/workflows/ci.yml`. Triggers: pull request to `main`, push to `main`.
- At 00:49 CST on 2026-11-10 the Actions tab showed runs up to #28. Every run from #14 to #28 passed.
- Each merged A5 pull request shows a passing check (`1/1`) in the pull request list.
- Run details and test counts: [`final-test-evidence.md`](../testing/final-test-evidence.md).

## Review Decisions

| PR | Reviewer | Decision | Findings |
|---|---|---|---|
| #97 | Hamza Bashar | Approved | CRF-024 (deferred to Cycle 2) |
| #98 | Hamza Bashar | Approved | — |
| #95 | Hamza Bashar | Approved (ran `mvn test` on `00879c7`) | CRF-025 |
| #100 | Hamza Bashar | Approved | CRF-026, CRF-027 (non-blocking) |
| #101 | Khaja Shabbir Ahmed | Approved | 4 fixes requested and applied (`41b38ed`, `7a6efe2`) |
| #102 | Khaja Shabbir Ahmed | Approved | 1 fix requested and applied (`4149af2`) |
| #93, #94 | See the PR | Approved | — |
| #99 | — | No review | Merged by its author |

A4 review decisions are in [`code-review-a4.md`](../reviews/code-review-a4.md).

## Release Baseline

| Tag | Commit | Date |
|---|---|---|
| `a1-project-launch` | `808a359` | 2026-09-08 |
| `a2-planning-estimate` | `ffe1842` | 2026-09-29 |
| `a3-architecture-review` | `6c0c830` | 2026-10-16 |
| `a4-construction-integration` | `157d3a2` | 2026-10-30 |
| `a5-cycle1-release` | `b3613ae` | 2026-11-09 23:26 |

**The A5 tag is not on the final commit.** It was created on `b3613ae`, right after PR #99 merged. Five pull requests merged after it: #97, #98, #100, #102 and #101. The tagged commit therefore:

- still has the `?role=` fallback that PR #97 removed, and has 31 tests, not 36;
- does not contain the final known limitations, traceability, test evidence or defect review.

`main` is now at `aaf77c8`. The tag has to be recreated on the final `main` commit once the last A5 pull requests are merged. Owner: Khaja Shabbir Ahmed and Joanelis Rivera Matos (#81); also recorded in `traceability.md` section 4 and as risk R-006.

**Release notes:** `docs/release/release-notes.md` is on `main`; the final version is in PR #92 (open).

**Projects board:** the Projects tab is empty. Work is tracked through issues, labels and assignees.

## Where the Workflow Was Not Followed

| Deviation | Evidence | Effect | Follow-Up |
|---|---|---|---|
| PR #99 merged without a review | Pull request list: no review decision on #99 | Its `ci-evidence.md` claims were not checked by a second person | Review after the fact (gap listed in `traceability.md`) |
| Tag created before the last merges | Tags page: `b3613ae`; five merges after it | The baseline does not match the released code | Recreate the tag (#81) |
| Approved work not merged | #95 (A5), #56 (A4) | 9 open defects wait on them (`defect-review.md`) | QA-001 |
| Old pull requests still open | #22, #60, #62 (changes requested), #63, #65 | Unclear which work is abandoned | Authors to merge or close |
| Old issues still open | #6, #7, #13, #16, #19 (A1 to A3); #29, #36, #38, #39 (A4) | The issue list does not show the true state | Owners to close or update |
| No branch protection | A PR can merge without approval or a passing check | Review and CI depend on each person following the practice | QA-002 (Cycle 2) |
| Most A5 work merged in the last hours | First A5 merge 11-09 22:28; last 11-10 01:10 | Little time between review and merge | Cycle 2 planning |

## Counts at the Time of Writing

| Item | Count |
|---|---:|
| Pull requests | 38 closed, 8 open |
| Issues | 42 closed, 14 open |
| A5 issues (#79..#91, #96) | 9 closed, 5 open |
| Phase-gate tags | 5 |
| CI runs | 28 or more; none failed from #14 onward |

## Evidence Integrity

- Merge commits, branches, commit hashes and tags were read from the repository (`git log`, `git tag`).
- Review decisions, issue states and counts were read from the GitHub Pull requests, Issues, Actions and Tags pages on 2026-11-10 between 00:49 and 01:12 CST. They change as work continues.
- The reviewer names for #93 and #94 were not checked; see those pull requests.
- AI assistance: Claude collected the history and drafted this file. Disclosed in `docs/ai/ai-use-log.md`.