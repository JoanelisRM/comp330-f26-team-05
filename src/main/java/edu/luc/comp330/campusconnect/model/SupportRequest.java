package edu.luc.comp330.campusconnect.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable domain record representing a synthetic student support request (REQ-001, REQ-004, REQ-005).
 * Contains no real student PII, university IDs, emails, or credentials.
 */
public final class SupportRequest {
    private final String requestId;
    private final String category;
    private final String title;
    private final String description;
    private final RequestStatus status;
    private final Instant submittedAt;
    private final Instant updatedAt;
    private final boolean synthetic;

    public SupportRequest(
            String requestId,
            String category,
            String title,
            String description,
            RequestStatus status,
            Instant submittedAt,
            Instant updatedAt) {
        this.requestId = Objects.requireNonNull(requestId, "requestId must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.submittedAt = Objects.requireNonNull(submittedAt, "submittedAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        this.synthetic = true; // Enforces REQ-004 synthetic-data boundary
    }

    public String getRequestId() {
        return requestId;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean isSynthetic() {
        return synthetic;
    }

    public SupportRequest withStatus(RequestStatus newStatus, Instant updateTime) {
        return new SupportRequest(
                this.requestId,
                this.category,
                this.title,
                this.description,
                newStatus,
                this.submittedAt,
                updateTime);
    }

    public SupportRequest copy() {
        return new SupportRequest(
                this.requestId,
                this.category,
                this.title,
                this.description,
                this.status,
                this.submittedAt,
                this.updatedAt);
    }
}
