package edu.luc.comp330.campusconnect.logic;

import edu.luc.comp330.campusconnect.data.InMemorySupportRequestRepository;
import edu.luc.comp330.campusconnect.logic.SupportRequestService.ServiceResult;
import edu.luc.comp330.campusconnect.logic.SupportRequestService.StatusCode;
import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;
import edu.luc.comp330.campusconnect.observability.AuditLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for validation, status transitions, role checks, and audit logging
 * (Issue #36 / TASK-106, Issue #33 / TASK-103, Issue #49, AC-REQ-001..006).
 */
class SupportRequestServiceTest {

    private InMemorySupportRequestRepository repository;
    private AuditLogger auditLogger;
    private SupportRequestService service;

    @BeforeEach
    void setUp() {
        repository = new InMemorySupportRequestRepository();
        auditLogger = new AuditLogger(false);
        service = new SupportRequestService(repository, new RoleCheckService(), auditLogger);
    }

    @Test
    void submitRequestCreatesOpenSyntheticRequestWhenFieldsAreValid() {
        ServiceResult<SupportRequest> result = service.submitRequest(
                "STUDENT",
                "Academic Advising",
                "Registration hold question",
                "Synthetic request testing AC-REQ-001-01");

        assertTrue(result.isSuccess());
        assertEquals(StatusCode.SUCCESS, result.statusCode());
        assertNotNull(result.data());
        assertEquals("REQ-1001", result.data().getRequestId());
        assertEquals(RequestStatus.OPEN, result.data().getStatus());
        assertTrue(result.data().isSynthetic());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void submitRequestRejectsEmptyOrWhitespaceFieldsAndIdentifiesAllMissingFields() {
        ServiceResult<SupportRequest> result = service.submitRequest(
                "STUDENT",
                "   ",
                "",
                null);

        assertFalse(result.isSuccess());
        assertEquals(StatusCode.VALIDATION_ERROR, result.statusCode());
        assertEquals(List.of("category", "title", "description"), result.missingFields());
        assertTrue(repository.findAll().isEmpty());
        assertEquals("SUBMISSION_REJECTED", auditLogger.getEntries().get(0).eventType());
    }

    @Test
    void roleCheckDeniesStudentFromListingOrUpdatingRequests() {
        ServiceResult<SupportRequest> created = service.submitRequest(
                "STUDENT", "IT", "Wi-Fi issue", "Synthetic wifi issue");
        String requestId = created.data().getRequestId();

        ServiceResult<List<SupportRequest>> listAttempt = service.listRequests("STUDENT");
        assertEquals(StatusCode.ROLE_DENIED, listAttempt.statusCode());

        ServiceResult<SupportRequest> updateAttempt =
                service.updateStatus("STUDENT", requestId, RequestStatus.IN_PROGRESS);
        assertEquals(StatusCode.ROLE_DENIED, updateAttempt.statusCode());
        assertEquals(RequestStatus.OPEN, repository.findById(requestId).orElseThrow().getStatus());
    }

    @Test
    void staffCanTransitionOpenToInProgressToResolved() {
        String requestId = service.submitRequest(
                "STUDENT", "Facilities", "Projector bulb", "Room 204 projector").data().getRequestId();

        ServiceResult<SupportRequest> inProgress =
                service.updateStatus("STAFF", requestId, RequestStatus.IN_PROGRESS);
        assertEquals(StatusCode.SUCCESS, inProgress.statusCode());
        assertEquals(RequestStatus.IN_PROGRESS, inProgress.data().getStatus());

        ServiceResult<SupportRequest> resolved =
                service.updateStatus("STAFF", requestId, RequestStatus.RESOLVED);
        assertEquals(StatusCode.SUCCESS, resolved.statusCode());
        assertEquals(RequestStatus.RESOLVED, resolved.data().getStatus());
    }

    @Test
    void staffCannotSkipInProgressOrReopenResolvedRequest() {
        String requestId = service.submitRequest(
                "STUDENT", "Advising", "Grad check", "Synthetic grad check").data().getRequestId();

        // Attempting to skip IN_PROGRESS (OPEN -> RESOLVED) must fail
        ServiceResult<SupportRequest> skipAttempt =
                service.updateStatus("STAFF", requestId, RequestStatus.RESOLVED);
        assertEquals(StatusCode.INVALID_TRANSITION, skipAttempt.statusCode());
        assertEquals(RequestStatus.OPEN, repository.findById(requestId).orElseThrow().getStatus());

        // Move through valid states to RESOLVED
        service.updateStatus("STAFF", requestId, RequestStatus.IN_PROGRESS);
        service.updateStatus("STAFF", requestId, RequestStatus.RESOLVED);

        // Attempting to reopen RESOLVED -> OPEN (Q-002) must fail
        ServiceResult<SupportRequest> reopenAttempt =
                service.updateStatus("STAFF", requestId, RequestStatus.OPEN);
        assertEquals(StatusCode.INVALID_TRANSITION, reopenAttempt.statusCode());
        assertEquals(RequestStatus.RESOLVED, repository.findById(requestId).orElseThrow().getStatus());
    }
}
