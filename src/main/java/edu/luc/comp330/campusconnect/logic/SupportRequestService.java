package edu.luc.comp330.campusconnect.logic;

import edu.luc.comp330.campusconnect.data.SupportRequestRepository;
import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;
import edu.luc.comp330.campusconnect.observability.AuditLogger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Logic Layer workflow service implementing API-001 (submit), API-002 (list), and API-003 (status update).
 * Coordinates central role check (API-005), required-field & transition validation (REQ-001, REQ-003),
 * Data Layer persistence (API-004), and structured runtime logging (Issue #49 / TASK-108).
 */
public class SupportRequestService {

    public enum StatusCode {
        SUCCESS,
        VALIDATION_ERROR,
        ROLE_DENIED,
        NOT_FOUND,
        INVALID_TRANSITION
    }

    public record ServiceResult<T>(
            StatusCode statusCode,
            T data,
            List<String> missingFields,
            String message) {

        public boolean isSuccess() {
            return statusCode == StatusCode.SUCCESS;
        }
    }

    private final SupportRequestRepository repository;
    private final RoleCheckService roleCheckService;
    private final AuditLogger auditLogger;

    public SupportRequestService(
            SupportRequestRepository repository,
            RoleCheckService roleCheckService,
            AuditLogger auditLogger) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.roleCheckService = Objects.requireNonNull(roleCheckService, "roleCheckService must not be null");
        this.auditLogger = Objects.requireNonNull(auditLogger, "auditLogger must not be null");
    }

    /**
     * API-001: Student submits a synthetic support request (REQ-001, REQ-004, REQ-005, REQ-006).
     */
    public ServiceResult<SupportRequest> submitRequest(
            String simulatedRole,
            String category,
            String title,
            String description) {

        RoleCheckService.RoleCheckOutcome roleOutcome =
                roleCheckService.requireRole(simulatedRole, Role.STUDENT, "SUBMIT_REQUEST");
        if (!roleOutcome.authorized()) {
            auditLogger.log("WARN", "ROLE_DENIED", simulatedRole, "N/A", "DENIED", roleOutcome.denialReason());
            return new ServiceResult<>(StatusCode.ROLE_DENIED, null, List.of(), roleOutcome.denialReason());
        }

        List<String> missing = new ArrayList<>();
        if (category == null || category.isBlank()) {
            missing.add("category");
        }
        if (title == null || title.isBlank()) {
            missing.add("title");
        }
        if (description == null || description.isBlank()) {
            missing.add("description");
        }

        if (!missing.isEmpty()) {
            String msg = "Validation failed; missing or whitespace-only fields: " + missing;
            auditLogger.log("WARN", "SUBMISSION_REJECTED", "STUDENT", "N/A", "REJECTED", msg);
            return new ServiceResult<>(
                    StatusCode.VALIDATION_ERROR,
                    null,
                    Collections.unmodifiableList(missing),
                    msg);
        }

        String id = repository.nextRequestId();
        Instant now = Instant.now();
        SupportRequest created = new SupportRequest(
                id,
                category.trim(),
                title.trim(),
                description.trim(),
                RequestStatus.OPEN,
                now,
                now);
        SupportRequest saved = repository.save(created);

        auditLogger.log(
                "INFO",
                "SUBMISSION_CREATED",
                "STUDENT",
                saved.getRequestId(),
                "SUCCESS",
                "Created synthetic support request (category='" + saved.getCategory() + "') with status OPEN");

        return new ServiceResult<>(StatusCode.SUCCESS, saved, List.of(), "Support request created");
    }

    /**
     * API-002: Staff view the list of submitted support requests (REQ-002, REQ-006).
     */
    public ServiceResult<List<SupportRequest>> listRequests(String simulatedRole) {
        RoleCheckService.RoleCheckOutcome roleOutcome =
                roleCheckService.requireRole(simulatedRole, Role.STAFF, "LIST_REQUESTS");
        if (!roleOutcome.authorized()) {
            auditLogger.log("WARN", "ROLE_DENIED", simulatedRole, "N/A", "DENIED", roleOutcome.denialReason());
            return new ServiceResult<>(StatusCode.ROLE_DENIED, null, List.of(), roleOutcome.denialReason());
        }

        List<SupportRequest> all = repository.findAll();
        auditLogger.log(
                "INFO",
                "REQUEST_LIST_VIEWED",
                "STAFF",
                "N/A",
                "SUCCESS",
                "Returned " + all.size() + " synthetic support request(s) to STAFF view");
        return new ServiceResult<>(StatusCode.SUCCESS, all, List.of(), "Listed " + all.size() + " request(s)");
    }

    /**
     * API-003: Staff manually update a request's status along OPEN -> IN_PROGRESS -> RESOLVED (REQ-003, REQ-005, REQ-006).
     */
    public ServiceResult<SupportRequest> updateStatus(
            String simulatedRole,
            String requestId,
            RequestStatus targetStatus) {

        String safeId = (requestId == null || requestId.isBlank()) ? "N/A" : requestId.trim();
        RoleCheckService.RoleCheckOutcome roleOutcome =
                roleCheckService.requireRole(simulatedRole, Role.STAFF, "UPDATE_STATUS");
        if (!roleOutcome.authorized()) {
            auditLogger.log("WARN", "ROLE_DENIED", simulatedRole, safeId, "DENIED", roleOutcome.denialReason());
            return new ServiceResult<>(StatusCode.ROLE_DENIED, null, List.of(), roleOutcome.denialReason());
        }

        if (requestId == null || requestId.isBlank()) {
            String msg = "Request ID must not be empty";
            auditLogger.log("WARN", "TRANSITION_REJECTED", "STAFF", "N/A", "REJECTED", msg);
            return new ServiceResult<>(StatusCode.VALIDATION_ERROR, null, List.of("requestId"), msg);
        }

        if (targetStatus == null) {
            String msg = "Target status must not be null";
            auditLogger.log("WARN", "TRANSITION_REJECTED", "STAFF", safeId, "REJECTED", msg);
            return new ServiceResult<>(StatusCode.VALIDATION_ERROR, null, List.of("newStatus"), msg);
        }

        Optional<SupportRequest> existingOpt = repository.findById(safeId);
        if (existingOpt.isEmpty()) {
            String msg = "Support request not found: " + safeId;
            auditLogger.log("WARN", "TRANSITION_REJECTED", "STAFF", safeId, "REJECTED", msg);
            return new ServiceResult<>(StatusCode.NOT_FOUND, null, List.of(), msg);
        }

        SupportRequest existing = existingOpt.get();
        if (!existing.getStatus().canTransitionTo(targetStatus)) {
            String msg = "Invalid status transition from " + existing.getStatus() + " to " + targetStatus
                    + " (allowed: OPEN -> IN_PROGRESS -> RESOLVED)";
            auditLogger.log("WARN", "TRANSITION_REJECTED", "STAFF", safeId, "REJECTED", msg);
            return new ServiceResult<>(StatusCode.INVALID_TRANSITION, null, List.of(), msg);
        }

        SupportRequest updated = repository.updateStatus(safeId, targetStatus, Instant.now());
        auditLogger.log(
                "INFO",
                "STATUS_UPDATED",
                "STAFF",
                safeId,
                "SUCCESS",
                "Transitioned request " + safeId + " from " + existing.getStatus() + " to " + targetStatus);

        return new ServiceResult<>(StatusCode.SUCCESS, updated, List.of(), "Status updated to " + targetStatus);
    }
}
