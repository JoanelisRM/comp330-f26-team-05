# CI Evidence

**Owner:** Hamza Bashar (Quality & Review Lead) · **Backup / Reviewer:** Khaja Shabbir Ahmed
**Gate:** A4 — Cycle 1 Construction and Integration · **Tracking issue:** #48 (CI/CD Results Evidence)
**Last updated:** 2026-10-30 · **Status as of:** `main` at `2540879`

## CI System

**CI Platform:** GitHub Actions
**Workflow / Pipeline:** `Java CI with Maven` (job: `build`)
**Configuration Location:** [`.github/workflows/ci.yml`](../../.github/workflows/ci.yml), added in PR #72 (#47, TASK-107), merged `fca18d9` on 2026-10-29

Before PR #72, `ci.yml` was the starter workflow, which could only be run manually and failed on purpose (R-019). No pull request had a CI check before 2026-10-29 23:04.

## CI Checks

| Check | Purpose | Command / Mechanism | Required to Pass? |
|---|---|---|---|
| Set up JDK 17 | Build with the Java version in `pom.xml` | `actions/setup-java@v3` (Temurin 17, Maven cache) | Yes (job fails if setup fails) |
| Build | Compile the code and package the application | `mvn -B package --file pom.xml` (also runs the unit tests) | Yes |
| Run Tests | Run the automated test suite | `mvn test` | Yes |

A failing build or test fails the job, and GitHub shows the check as failed on the pull request.

## Trigger Conditions

- **Pull request** to `main`: runs on the PR's code each time the PR is opened or updated.
- **Push** to `main`: runs after each merge.

Manual runs are no longer configured.

## CI Run Evidence

Runs are listed on the repository's **Actions** tab under "Java CI with Maven". Run #1 is not listed under this workflow (the starter workflow was replaced by PR #72).

| Evidence ID | Date (CDT) | Commit / PR | Workflow Run | Result | Purpose |
|---|---|---|---|---|---|
| CI-001 | 2026-10-29 23:04 | PR #72 (`evidence-updates-kurul`) | Java CI with Maven #2 (27s) | Passed | First CI run on a pull request |
| CI-002 | 2026-10-29 ~23:43 | PR #72, updated | Java CI with Maven #3 (25s) | Passed | Re-run after PR #72 was updated |
| CI-003 | 2026-10-29 ~23:52 | Push to `main`, `fca18d9` (PR #72 merged) | Java CI with Maven #4 (28s) | Passed | First CI run on `main` |
| CI-004 | 2026-10-30 ~00:10 | PR #75 (`docs/code-review-evidence-50`) | Java CI with Maven #5 (25s) | Passed | Status check shown on a reviewed pull request ("All checks have passed") |
| CI-005 | 2026-10-30 ~00:11 | Push to `main`, `2540879` (PR #75 merged) | Java CI with Maven #6 (24s) | Passed | `main` after the merge |

## Test Results

The test suite on `main` at `2540879` has 31 automated tests, the same four test files that ran 31/31 locally on PR #73 (see Local Verification).

| CI Evidence ID | Test Type | Executed | Passed | Failed | Skipped | Notes |
|---|---|---:|---:|---:|---:|---|
| CI-005 | Unit and HTTP tests | 31 | 31 | 0 | 0 | `RoleCheckServiceTest` 9, `RequestStatusTest` 9, `InMemorySupportRequestRepositoryTest` 5, `CampusConnectAppSubmitTest` 8 |

### Local Verification Before CI Existed

Before CI was set up, the reviewer ran the suite locally with `mvn test` (JDK and Maven on macOS):

| Date (CDT) | Code | Result | Purpose |
|---|---|---|---|
| 2026-10-29 20:42 | PR #62 at `496fe55` | 18 run, 0 failures, BUILD SUCCESS | Reviewer check for the #62 review |
| 2026-10-29 21:15 | PR #71 (`RoleCheckServiceTest` added) | 22 run, 0 failures, BUILD SUCCESS | Author check before opening #71 |
| 2026-10-29 21:38 | PR #73 (`RequestStatusTest` added) | 31 run, 0 failures, BUILD SUCCESS | Author check before opening #73 |

Further local runs by Khaja are recorded as RUN-001 to RUN-009 in [`test-evidence/2026-10-29-local-test-runs.md`](../../test-evidence/2026-10-29-local-test-runs.md).

## Failed CI Runs

No CI run has failed since CI was set up. The build failures below were found by **local** runs before CI existed, which is the gap CI now covers.

| Evidence ID | Failure | Cause | Action | Resolution Evidence |
|---|---|---|---|---|
| RUN-003 (local) | PR #61 at `49adb8e`: 74 compile errors, BUILD FAILURE | Unescaped quotes in JSON strings in `CampusConnectApp.java` (RF-01) | Fixed in PR #67, merged into #61 | `mvn test` passes on `main` (CI-003, CI-005) |
| RUN-004a (local) | PR #62 at `bdff8e0`: same 74 errors | Same file, inherited from #61 | PR #62 updated with the #67 fix (`496fe55`) | 18 run, 0 failures (local, 2026-10-29 20:42) |

With CI in place, a build like this now fails as a visible check on the pull request instead of being found by hand.

## CI-to-Requirement Traceability

| CI Evidence ID | Test / Test Class | Related Requirement / Criterion | Result |
|---|---|---|---|
| CI-005 | `CampusConnectAppSubmitTest` | REQ-001, AC-REQ-001-01, AC-REQ-001-02; storage failure `500` (API-001, REQ-005) | Passed |
| CI-005 | `RoleCheckServiceTest` | REQ-006, API-005, AC-REQ-002-03, AC-REQ-003-04 | Passed |
| CI-005 | `RequestStatusTest` | REQ-003, API-003, AC-REQ-003-01, AC-REQ-003-02, AC-REQ-003-03 | Passed |
| CI-005 | `InMemorySupportRequestRepositoryTest` | REQ-005, API-004 | Passed |

Not covered by any test in CI yet: AC-REQ-002-01 (staff list; fixed in #76, no test yet), AC-REQ-002-02, AC-REQ-002-04, AC-REQ-003-05 and AC-REQ-005-01 (see `traceability.md`). Logic Layer tests in PR #62 are not on `main`.

## Branch / Merge Protection

No branch protection was observed: PR #75 could be merged while a review still requested changes, so neither an approving review nor a passing check is enforced by GitHub. Review and CI are team practice, not enforced rules.

## CI Limitations

| Limitation | Impact | Additional Verification |
|---|---|---|
| Tests run twice per job (`mvn package` runs them, then `mvn test` again) | Slower runs; no loss of checking | None needed |
| No `permissions:` block in `ci.yml` | The job gets GitHub's default token permissions rather than least privilege | Code review of `ci.yml` |
| `actions/checkout@v3` and `actions/setup-java@v3` are older major versions (current is v4) | Possible deprecation warnings | Dependency review |
| No branch protection | A PR can merge without a passing check or approval | Reviewer checks the PR's status before approving |
| CI runs unit and HTTP tests only; no end-to-end run | TC-013 (TASK-109) still needs a manual run on `main` | Manual end-to-end check before the tag |

## Release CI Evidence

**Release / Gate:** A4 (`a4-construction-integration`)
**Commit / Tag:** To be confirmed when the A4 tag is placed on the final `main` commit
**CI Evidence ID:** The CI run on that commit (CI-005 or later)
**Result:** To be recorded

## CI Evidence Integrity

Run numbers, dates, durations and results above were taken from the repository's Actions tab on 2026-10-30. Test counts were confirmed in the CI log of run #6 (`main` at `2540879`): `Tests run: 31, Failures: 0, Errors: 0, Skipped: 0`, `BUILD SUCCESS`, finished 2026-10-30T05:11:59Z. AI assistance in preparing this file is recorded in `docs/ai/ai-use-log.md`.