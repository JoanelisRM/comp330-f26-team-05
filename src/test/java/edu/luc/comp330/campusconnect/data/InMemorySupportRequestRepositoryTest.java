package edu.luc.comp330.campusconnect.data;

import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InMemorySupportRequestRepository (Issue #31 / TASK-102, API-004, REQ-005).
 * Updated in A5 to close CRF-006, CRF-007, and CRF-010.
 */
class InMemorySupportRequestRepositoryTest {

    private InMemorySupportRequestRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemorySupportRequestRepository();
    }

    @Test
    void nextRequestIdGeneratesSequentialSyntheticIds() {
        assertEquals("REQ-1001", repository.nextRequestId());
        assertEquals("REQ-1002", repository.nextRequestId());
    }

    @Test
    void findAllOnEmptyRepositoryReturnsEmptyUnmodifiableList() {
        List<SupportRequest> empty = repository.findAll();
        assertNotNull(empty);
        assertTrue(empty.isEmpty(), "Empty store should return empty list for AC-REQ-002-02");
    }

    @Test
    void findByIdReturnsEmptyForNullBlankOrUnknownIds() {
        assertTrue(repository.findById(null).isEmpty());
        assertTrue(repository.findById("").isEmpty());
        assertTrue(repository.findById("   ").isEmpty());
        assertTrue(repository.findById("REQ-9999").isEmpty());
    }

    @Test
    void saveAndFindByIdPreservesSyntheticRequestFields() {
        Instant now = Instant.parse("2026-10-28T20:00:00Z");
        SupportRequest request = new SupportRequest(
                "REQ-1001",
                "IT Support",
                "Sakai upload error",
                "Synthetic description for testing",
                RequestStatus.OPEN,
                now,
                now);

        SupportRequest saved = repository.save(request);
        assertTrue(saved.isSynthetic());
        assertEquals("REQ-1001", saved.getRequestId());

        Optional<SupportRequest> loaded = repository.findById("REQ-1001");
        assertTrue(loaded.isPresent());
        assertEquals("IT Support", loaded.get().getCategory());
        assertEquals("Sakai upload error", loaded.get().getTitle());
        assertEquals(RequestStatus.OPEN, loaded.get().getStatus());
    }

    @Test
    void saveRejectsDuplicateRequestIdWithoutOverwritingExistingRecord() {
        Instant now = Instant.parse("2026-10-28T20:00:00Z");
        SupportRequest original = new SupportRequest(
                "REQ-1001", "Advising", "Original Title", "Original Desc", RequestStatus.OPEN, now, now);
        SupportRequest duplicate = new SupportRequest(
                "REQ-1001", "IT Support", "Replacement Title", "Replacement Desc", RequestStatus.OPEN, now, now);

        repository.save(original);
        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(duplicate),
                "CRF-006: duplicate requestId must fail explicitly rather than overwriting");

        SupportRequest preserved = repository.findById("REQ-1001").orElseThrow();
        assertEquals("Original Title", preserved.getTitle());
    }

    @Test
    void findAllPreservesInsertionOrderAndReturnsUnmodifiableList() {
        Instant t1 = Instant.parse("2026-10-28T20:00:00Z");
        Instant t2 = Instant.parse("2026-10-28T20:05:00Z");
        repository.save(new SupportRequest("REQ-1001", "Advising", "First", "Desc 1", RequestStatus.OPEN, t1, t1));
        repository.save(new SupportRequest("REQ-1002", "Financial Aid", "Second", "Desc 2", RequestStatus.OPEN, t2, t2));

        List<SupportRequest> all = repository.findAll();
        assertEquals(2, all.size());
        assertEquals("REQ-1001", all.get(0).getRequestId());
        assertEquals("REQ-1002", all.get(1).getRequestId());
        assertThrows(UnsupportedOperationException.class, () -> all.remove(0));
    }

    @Test
    void updateStatusUpdatesStoredRecordAndTimestamp() {
        Instant created = Instant.parse("2026-10-28T20:00:00Z");
        Instant updatedTime = Instant.parse("2026-10-28T20:10:00Z");
        repository.save(new SupportRequest("REQ-1001", "Advising", "Title", "Desc", RequestStatus.OPEN, created, created));

        SupportRequest updated = repository.updateStatus("REQ-1001", RequestStatus.IN_PROGRESS, updatedTime);
        assertEquals(RequestStatus.IN_PROGRESS, updated.getStatus());
        assertEquals(updatedTime, updated.getUpdatedAt());
        assertEquals(RequestStatus.IN_PROGRESS, repository.findById("REQ-1001").orElseThrow().getStatus());
    }

    @Test
    void updateStatusThrowsExplicitExceptionWhenIdDoesNotExist() {
        Instant now = Instant.now();
        assertThrows(
                IllegalArgumentException.class,
                () -> repository.updateStatus("REQ-9999", RequestStatus.IN_PROGRESS, now));
    }
}
