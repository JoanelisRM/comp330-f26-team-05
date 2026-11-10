package edu.luc.comp330.campusconnect;

import edu.luc.comp330.campusconnect.data.InMemorySupportRequestRepository;
import edu.luc.comp330.campusconnect.logic.RoleCheckService;
import edu.luc.comp330.campusconnect.logic.SupportRequestService;
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
 * End-to-End Cycle 1 Acceptance Test Suite (Assignment 5 Release Readiness & A4 Remediation).
 * Directly satisfies Instructor Assessment Condition #2 and CRF-014:
 * "Complete a final-baseline submit/list/update/list-again acceptance test, including
 *  empty-list, authorization and retained-state cases."
 */
class Cycle1AcceptanceTest {

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
    void fullCycle1LifecycleEmptyListSubmitListUpdateAndListAgainRetainsState() {
        // Step 1: Staff lists queue when empty (AC-REQ-002-02)
        ServiceResult<List<SupportRequest>> initialList = service.listRequests("STAFF");
        assertTrue(initialList.isSuccess());
        assertEquals(StatusCode.SUCCESS, initialList.statusCode());
        assertNotNull(initialList.data());
        assertTrue(initialList.data().isEmpty(), "Initial staff queue must be empty before submissions");

        // Step 2: Student submits two synthetic support requests (REQ-001, AC-REQ-001-01, REQ-004)
        ServiceResult<SupportRequest> firstSubmit = service.submitRequest(
                "STUDENT",
                "Academic Advising",
                "Course registration override",
                "Synthetic request: prerequisite check for COMP 330");
        assertTrue(firstSubmit.isSuccess());
        assertEquals("REQ-1001", firstSubmit.data().getRequestId());
        assertEquals(RequestStatus.OPEN, firstSubmit.data().getStatus());

        ServiceResult<SupportRequest> secondSubmit = service.submitRequest(
                "STUDENT",
                "IT Support",
                "Campus Wi-Fi disconnect",
                "Synthetic request: eduroam drops in Cudahy Science Hall");
        assertTrue(secondSubmit.isSuccess());
        assertEquals("REQ-1002", secondSubmit.data().getRequestId());
        assertEquals(RequestStatus.OPEN, secondSubmit.data().getStatus());

        // Step 3: Staff lists queue and sees both requests in submission order (REQ-002, AC-REQ-002-01, AC-REQ-002-04)
        ServiceResult<List<SupportRequest>> afterSubmitList = service.listRequests("STAFF");
        assertTrue(afterSubmitList.isSuccess());
        assertEquals(2, afterSubmitList.data().size());
        assertEquals("REQ-1001", afterSubmitList.data().get(0).getRequestId());
        assertEquals(RequestStatus.OPEN, afterSubmitList.data().get(0).getStatus());
        assertEquals("REQ-1002", afterSubmitList.data().get(1).getRequestId());
        assertEquals(RequestStatus.OPEN, afterSubmitList.data().get(1).getStatus());

        // Step 4: Staff transitions REQ-1001 from OPEN -> IN_PROGRESS (REQ-003, AC-REQ-003-01)
        ServiceResult<SupportRequest> toInProgress =
                service.updateStatus("STAFF", "REQ-1001", RequestStatus.IN_PROGRESS);
        assertTrue(toInProgress.isSuccess());
        assertEquals(RequestStatus.IN_PROGRESS, toInProgress.data().getStatus());

        // Step 5: Staff transitions REQ-1001 from IN_PROGRESS -> RESOLVED (REQ-003, AC-REQ-003-02)
        ServiceResult<SupportRequest> toResolved =
                service.updateStatus("STAFF", "REQ-1001", RequestStatus.RESOLVED);
        assertTrue(toResolved.isSuccess());
        assertEquals(RequestStatus.RESOLVED, toResolved.data().getStatus());

        // Step 6: Staff lists again and verifies retained state across both requests (REQ-005, AC-REQ-003-05)
        ServiceResult<List<SupportRequest>> finalList = service.listRequests("STAFF");
        assertTrue(finalList.isSuccess());
        assertEquals(2, finalList.data().size());
        SupportRequest retainedFirst = finalList.data().get(0);
        SupportRequest retainedSecond = finalList.data().get(1);
        assertEquals("REQ-1001", retainedFirst.getRequestId());
        assertEquals(RequestStatus.RESOLVED, retainedFirst.getStatus(), "REQ-1001 must retain RESOLVED state");
        assertEquals("Course registration override", retainedFirst.getTitle());
        assertEquals("REQ-1002", retainedSecond.getRequestId());
        assertEquals(RequestStatus.OPEN, retainedSecond.getStatus(), "REQ-1002 must remain untouched in OPEN state");
    }

    @Test
    void authorizationAndTransitionBoundariesPreserveRetainedStateOnFailure() {
        // Seed one valid request
        String reqId = service.submitRequest(
                "STUDENT", "Financial Aid", "Scholarship inquiry", "Synthetic inquiry").data().getRequestId();

        // STAFF attempting student-only submit (API-001, CRF-004) must be denied
        ServiceResult<SupportRequest> staffSubmitAttempt =
                service.submitRequest("STAFF", "IT", "Staff test", "Should be denied");
        assertEquals(StatusCode.ROLE_DENIED, staffSubmitAttempt.statusCode());

        // Missing or unrecognized role must be denied on all endpoints (REQ-006, API-005)
        assertEquals(StatusCode.ROLE_DENIED, service.listRequests(null).statusCode());
        assertEquals(StatusCode.ROLE_DENIED, service.listRequests("ADMIN").statusCode());
        assertEquals(StatusCode.ROLE_DENIED, service.updateStatus("STUDENT", reqId, RequestStatus.IN_PROGRESS).statusCode());

        // Move REQ-1001 to IN_PROGRESS and verify backward/same-status moves are rejected (CRF-014)
        assertEquals(StatusCode.SUCCESS, service.updateStatus("STAFF", reqId, RequestStatus.IN_PROGRESS).statusCode());
        assertEquals(StatusCode.INVALID_TRANSITION, service.updateStatus("STAFF", reqId, RequestStatus.IN_PROGRESS).statusCode());
        assertEquals(StatusCode.INVALID_TRANSITION, service.updateStatus("STAFF", reqId, RequestStatus.OPEN).statusCode());

        // Unknown requestId -> NOT_FOUND; null targetStatus -> VALIDATION_ERROR (CRF-014)
        assertEquals(StatusCode.NOT_FOUND, service.updateStatus("STAFF", "REQ-9999", RequestStatus.RESOLVED).statusCode());
        assertEquals(StatusCode.VALIDATION_ERROR, service.updateStatus("STAFF", reqId, null).statusCode());

        // Confirm retained state in list-again is still IN_PROGRESS after all rejected attempts
        List<SupportRequest> listAgain = service.listRequests("STAFF").data();
        assertEquals(1, listAgain.size());
        assertEquals(RequestStatus.IN_PROGRESS, listAgain.get(0).getStatus());
    }
}
