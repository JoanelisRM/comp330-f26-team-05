package edu.luc.comp330.campusconnect.data;

import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * API-004: Internal Java storage interface for the Data Layer (Issue #31 / TASK-102).
 * Owned exclusively by the Data Layer and called only by the Logic Layer (never directly by Presentation).
 */
public interface SupportRequestRepository {

    /**
     * Generates the next sequential synthetic request identifier (e.g., REQ-1001).
     */
    String nextRequestId();

    /**
     * Persists a validated support request in the authoritative store (REQ-005).
     */
    SupportRequest save(SupportRequest request);

    /**
     * Finds a support request by its unique identifier.
     */
    Optional<SupportRequest> findById(String requestId);

    /**
     * Returns all stored support requests in submission order (REQ-002).
     */
    List<SupportRequest> findAll();

    /**
     * Updates the status of an existing request and records the update timestamp (REQ-003, REQ-005).
     *
     * @throws IllegalArgumentException if the requestId does not exist
     */
    SupportRequest updateStatus(String requestId, RequestStatus newStatus, Instant updatedAt);

    /**
     * Clears all stored requests (used for deterministic test isolation).
     */
    void clear();
}
