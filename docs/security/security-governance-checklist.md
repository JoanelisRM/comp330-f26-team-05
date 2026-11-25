# Security Governance Checklist

**Review Date:** 2026-11-23  
**Applicable Gate / Milestone:** A6 — Final Cycle 2 Release  
**System / Release Baseline:** `main` (pre-`a6-final-release` tag)  
**Primary Owner:** Nova Kurul (@selkianova) | **Reviewer:** Joanelis Rivera Matos (@JoanelisRM)

## 1. Security Context
CampusConnect is a local Java 17 / Maven HTTP workflow application for submitting and managing synthetic student support requests. It operates strictly on `localhost` with in-memory storage and simulated role headers (`X-Simulated-Role`). It is **not** deployed to the public internet and does **not** process real Loyola student PII, passwords, or production credentials.

## 2. Governance Checklist

| Security Area | Status | Evidence | Owner | Notes / Required Action |
|---|---|---|---|---|
| Authentication approach is defined | Partial | `ADR-0003`, `known-limitations.md` | Nova Kurul | Uses simulated `X-Simulated-Role` header for local scope; cryptographic auth deferred |
| Authorization responsibilities are defined | Complete | `RoleCheckService.java`, `API-005`, `REQ-006` | Hamza Bashar | Central server-side role check enforced in Logic layer |
| Permission boundaries are documented | Complete | `docs/architecture/api-contracts.md`, `RoleCheckServiceTest.java` | Nova Kurul | `STUDENT` can submit only; `STAFF` can list and update status only |
| Sensitive data is identified | Complete | `REQ-004`, `data-context.md` | Nova Kurul | Synthetic data only; real PII explicitly prohibited |
| Data handling expectations are documented | Complete | `docs/security/security-governance-checklist.md`, `known-limitations.md` | Nova Kurul | In-memory only; wiped on restart |
| Secrets and credentials are kept out of source control | Complete | `.gitignore`, repository inspection (`TC-011`) | Khaja Shabbir Ahmed | No API keys, DB passwords, or tokens exist in repo |
| Important security requirements are documented | Complete | `REQ-004`, `REQ-006`, `API-005` | Hamza Bashar | Role boundaries and synthetic-data rules tracked in `traceability.md` |
| Security-relevant assumptions and risks are visible | Complete | `docs/planning/risk-register.md` | Khaja Shabbir Ahmed | Simulated header trust and volatile storage tracked as residual risks |
| Important trust boundaries are understood | Complete | `ADR-001`, `ADR-0003`, `component-responsibilities.md` | Joanelis Rivera Matos | Presentation -> Logic -> Data separation maintained |
| Input validation responsibilities are defined | Complete | `CampusConnectApp.java`, `CampusConnectAppSubmitTest.java` | Sydel Ugwu | Rejects missing/whitespace-only fields and invalid JSON |
| Security-relevant failure behavior is defined | Complete | `CampusConnectAppRoleTransportTest.java`, `final-test-evidence.md` | Hamza Bashar | Unauthorized roles return `403`, invalid methods/inputs return `400`/`405` |
| Important security behavior has verification evidence | Complete | 36 passing automated tests + 12-step HTTP run | Hamza Bashar | Verified via `mvn test` and GitHub Actions CI |
| Security-sensitive logging avoids inappropriate data exposure | Complete | `docs/observability/observability-plan.md` | Nova Kurul | Logs record event type, role, and `requestId` only; no credentials |
| Deployment configuration does not expose known secrets | Not Applicable | Localhost-only execution | Nova Kurul | No cloud deployment or external secret store in Cycle 1/2 |
| Known security limitations are documented | Complete | `docs/release/known-limitations.md` | Khaja Shabbir Ahmed | Explicitly states local synthetic-demo scope only |

## 3. Input Validation, Tool Boundaries, & Audit Expectations
- **Authoritative Validation:** All HTTP inputs (`POST /requests`, `GET /requests`, `PATCH /requests/{id}/status`) are validated server-side in `CampusConnectApp` and `RoleCheckService`. Query-parameter role spoofing (`?role=`) and `POST` status overrides were explicitly removed and regression-tested in PR #97.
- **AI & Tool Boundaries:** No runtime AI calls are made by the application. AI tools (Claude, Gemini, ChatGPT, Copilot) are restricted to offline development/documentation assistance, governed by `/docs/ai/ai-policy.md`, and must never receive real PII or credentials.
- **Audit Expectations:** Every material code or governance change requires a GitHub Issue, a Pull Request with passing CI (`ci.yml`), and an independent peer review prior to merge.

## 4. Known Security Limitations & Residual Risk Ownership

| Limitation | Impact | Disposition | Owner |
|---|---|---|---|
| Unverified `X-Simulated-Role` header | Any local caller can set `X-Simulated-Role: STAFF` without cryptographic proof | Accepted for local synthetic demo; token-based auth required before any network deployment | Nova Kurul / Joanelis |
| Volatile in-memory storage | Data is lost on restart; no encryption at rest | Accepted for local synthetic demo; durable DB deferred | Sydel Ugwu |
| No automated SAST / secret-scanning CI job | Relies on manual PR review and `git grep` checks | Accepted for low-dependency local project | Nova Kurul |

## 5. Review Conclusion
Within its documented local, synthetic-data scope, CampusConnect enforces server-side role boundaries, forward-only status transitions, and input validation backed by 36 automated tests and a 12-step HTTP verification. It is **not** hardened for production or real student data due to simulated authentication and in-memory storage, both of which are transparently documented as residual risks.