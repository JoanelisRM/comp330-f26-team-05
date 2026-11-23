# Cycle 2: Final CI/CD and Testing Evidence

**Owner:** Nova Kurul (@selkianova) | **Backup / Reviewer:** Joanelis Rivera Matos (@JoanelisRM)  
**Gate:** A6 — Final Cycle 2 Release  
**Tracking Issue:** #112  
**Target Baseline:** `a6-final-release` (to be tagged on `main` after final PR review and passing CI)

## 1. CI/CD Pipeline Configuration & Controls
- **CI Platform:** GitHub Actions
- **Workflow File:** `.github/workflows/ci.yml` (`Java CI with Maven`, job: `build`)
- **Triggers:** Automatic execution on every `pull_request` targeting `main` and every `push` to `main`.
- **Automated Checks Executed:**
  1. **Environment Setup:** `actions/setup-java@v3` (Temurin JDK 17 with Maven dependency cache) — Required.
  2. **Build & Package:** `mvn -B package --file pom.xml` — Required.
  3. **Automated Test Suite:** `mvn test` — Required.

## 2. Baseline Reconciliation & A5 Remediation
To directly address Assignment 5 feedback regarding baseline discrepancies and unreviewed merges:
- **Executable Code vs. Documentation Merges:** The 36 automated tests verified on `main` cover `RoleCheckServiceTest` (9), `RequestStatusTest` (9), `InMemorySupportRequestRepositoryTest` (5), `CampusConnectAppSubmitTest` (8), and `CampusConnectAppRoleTransportTest` (5). Documentation-only PRs in Cycle 2 still trigger the full `Java CI with Maven` workflow to confirm the build remains green without modifying executable code.
- **Pre-Merge Review Control:** No A6 deliverable PR is merged without (a) a passing GitHub Actions CI run on the latest commit and (b) an explicit approving peer review from the assigned backup owner. Any substantive edit after approval requires a renewed review decision.
- **Unmerged Historical PRs:** Legacy open PRs from A4/A5 (e.g., #56, #60, #62, #63, #65, #95) are explicitly excluded from the `main` test count and are not claimed as shipped functionality.

## 3. End-to-End & Regression Verification Summary
- **Automated Suite on `main`:** 36/36 tests passing (0 failures, 0 errors, 0 skipped).
- **Manual 12-Step HTTP Verification:** Verified `submit -> list -> update -> list-again` lifecycle along with negative paths (missing/whitespace fields, `STUDENT` role denial on staff actions, invalid status transitions, and unknown request IDs). Full step-by-step output is recorded in `/docs/testing/final-test-evidence.md`.

## 4. Automated Controls Not Enabled (Explained Gaps)
To remain transparent about CI scope and limitations:
- **Static Analysis / Linting (e.g., Checkstyle, SpotBugs):** Not configured in `ci.yml` for Cycle 2. Code style and correctness are enforced through manual peer review using the PR checklist.
- **Automated Dependency / Secret Scanning:** Not enabled as a CI job; the project uses minimal Maven dependencies (JUnit for testing, JDK 17 standard library for runtime) and zero committed secrets.
- **GitHub Branch Protection Rules:** Repository-level branch protection is not enforced by GitHub settings; review-before-merge is governed by team workflow policy and audited in PR history.
- **Duplicate Test Execution:** `mvn package` and `mvn test` both run unit tests during a single CI job. This slightly increases run duration (~25–30s) but does not reduce verification coverage.

## 5. Final Release CI Sign-Off
- **A6 Evidence PR Check:** Verified via the GitHub Actions check attached to this PR prior to merge.
- **Final Tag Verification:** Prior to Sakai submission, `a6-final-release` will be applied strictly to the final reviewed `main` commit with a green `Java CI with Maven` run.