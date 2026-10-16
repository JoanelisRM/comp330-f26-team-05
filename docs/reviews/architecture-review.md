# Architecture Review

**Tracking issues:** #15 (Architecture Review Evidence), #16 (Review Workflow Evidence)

**Document status:** In progress. This file records the review activity completed so far; remaining sections will be added as the review continues.

## Review Identification

**Review Date:** 2026-10-15
**Applicable Gate / Milestone:** A3 — Cycle 1 Architecture and Review Package
**Architecture Baseline / Commit:** `docs/architecture/architecture.md` as proposed in PR #21 (branch `JoanelisRMBranch`, commit `e1e4370`)
**Reviewer(s):** Hamza Bashar (reviewer); Joanelis Rivera Matos (author of PR #21)

The review was carried out as a GitHub pull-request review on PR #21. Each finding and author response below can be traced to that PR's conversation.

## Review Scope

**Included:** `docs/architecture/architecture.md` (PR #21), checked for consistency with `requirements.md`, `assumptions-open-questions.md`, and ADR-002.

**Not yet reviewed:** `component-responsibilities.md`, `data-context.md` (PR #22), and the architecture diagram. `api-contracts.md` is authored by the reviewer and is reviewed by another team member in its own pull request.

## Findings

| ID | Type | Finding | Importance | Evidence | Required Action |
|---|---|---|---|---|---|
| ARF-001 | Concern | "Handle large amounts of data" (Scalability) does not fit the size of the project: Cycle 1 is a small slice with synthetic data running locally, so scalability is not driving any design choice. Suggested replacing the qualities with ones based on established requirements: correctness of the status rules Open → In Progress → Resolved (REQ-003); reliability, meaning submitted requests and status changes are not lost while the app is running (REQ-005); and maintainability, meaning role checks are in one place so simulated roles can later be swapped for real login (REQ-006). | Medium | PR #21 review comment on `architecture.md` line 170 | ARA-001 |
| ARF-002 | Gap | "Database" is listed as the persistence for request and user data, but how data will be stored is still undecided (Q-003 in `assumptions-open-questions.md`). Suggested something like "undecided" so the document does not imply a decision that has not been made. Also raised for consideration: since roles are simulated (ASM-001), is user data actually stored in Cycle 1? | Medium | PR #21 review comment on lines 113–114 | ARA-002 |
| ARF-003 | Concern | The Architectural Structure section names the parts Presentation, Logic, and Data Layer, but the "Major Components and Responsibilities" and "Interfaces and Dependencies" tables, and the owner column in "Data and State Ownership", still use the template names Web Client, Application Service, and Persistence Layer. Asked for those tables to use Presentation, Logic, and Data Layer, because the names also have to match in `component-responsibilities.md` and `api-contracts.md`. | High | PR #21 review comment on line 26 | ARA-003 |

### Author Responses and Dispositions

| ID | Author Response (summary) | Disposition | Date |
|---|---|---|---|
| ARF-001 | Agreed and will update the qualities. | **Accepted** | 2026-10-15 |
| ARF-002 | Agreed that no permanent storage is needed; proposed that the system store data only temporarily, in memory, during a run, and offered to update Q-003. | **Accepted**; in-memory storage proposed, pending team confirmation | 2026-10-15 |
| ARF-003 | The Data and State Ownership owner column was an oversight. The other tables were left as template until `component-responsibilities.md` and `api-contracts.md` are settled. | **Accepted** (data ownership); **Accepted / Deferred** (component and interface tables) | 2026-10-15 |

No findings were rejected.

## Required Actions

| Action ID | Required Action | Driver | Owner | Target | Status |
|---|---|---|---|---|---|
| ARA-001 | Replace the quality attributes with ones based on REQ-003, REQ-005, and REQ-006 | ARF-001 | Joanelis Rivera Matos |  | Open |
| ARA-002 | Replace "Database" on lines 113–114 with "undecided" or, once the team confirms it, the in-memory approach; update Q-003 in `assumptions-open-questions.md` to match | ARF-002 | Joanelis Rivera Matos |  | Open |
| ARA-003 | Use Presentation / Logic / Data Layer names in the Data and State Ownership, Major Components, and Interfaces and Dependencies tables | ARF-003 | Joanelis Rivera Matos |  | Open (tables deferred by author) |

## Review Conclusion

**Review Outcome:** Not yet concluded



## Follow-Up

- Re-check `architecture.md` after the author pushes updates to PR #21; resolve each review thread only after the fix is confirmed.
- Review the remaining artifacts listed under Review Scope and add their findings to this file.