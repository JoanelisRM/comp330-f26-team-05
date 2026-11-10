package edu.luc.comp330.campusconnect.data;

import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * API-004: Internal Java storage interface for the Data Layer (Issue #31 / TASK-102).
 * Remediated for A5 (CRF-007): removed clear() from the production interface so callers
 * cannot wipe stored requests; each test creates a fresh repository instance in @BeforeEach.
 */
public interface SupportRequestRepository {

    /**
     * Generates the next sequential synthetic request identifier (e.g., REQ-1001).
     */
    String nextRequestId();

    /**
     * Persists a new validated support request in the authoritative store (REQ-005).
     *
     * @throws IllegalArgumentException if a request with the same requestId already exists (CRF-006)
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
}
