# Data and Context Boundaries (Cycle 1)

## Data Ownership
*   **Role:** Simulated local roles only (STUDENT and STAFF). No admin roles and no external IAM integration.
*   **Data Type:** 100% Synthetic data. No real student IDs, names, or emails are processed or stored.
*   **Storage:** Local in-memory lifetime during runtime only.

## AI Boundary Constraint
*   **Constraint:** The application does **NOT** send any runtime data, student information, or synthetic profiles to any external AI service or LLM during execution.
*   **Verification:** Runtime logs (AuditLogger) only reflect internal state transitions and synthetic IDs.