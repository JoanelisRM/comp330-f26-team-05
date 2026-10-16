# Data and Context Ownership

This document identifies the authoritative data sources, sensitive data boundaries, allowed uses, and logging expectations for the CampusConnect Workflow System.

| Data / Context | Authoritative Source | Sensitive? | Allowed Use | Retention / Logging |
| :--- | :--- | :--- | :--- | :--- |
| **Student Request Record** | Application Database | Yes (May contain Student ID, emails, personal issues) | Workflow triage, assignment, and resolution by support staff. | Retained per university policy; log all state/status changes. |
| **Support Staff Profiles** | Identity/Access Management (IAM) | No | Role-based access control and request routing. | Retained while actively employed. |
| **AI Prompt Context (If used)** | Sanitized Request Text | No (PII must be stripped before processing) | To suggest request categories or routing to staff. | Record prompt purpose and verification in AI-use log. |
| **System Audit Logs** | Centralized Logging Service | No | Observability, debugging, and security monitoring. | 30 days retention; access restricted to system admins. |

## AI / Context Boundary Note
If AI is introduced to assist in categorizing student requests, it will **only** have "Read" access to sanitized request descriptions. It will not have access to authoritative student records or the ability to "Change" request states without human (Support Reviewer) approval.