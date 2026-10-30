package edu.luc.comp330.campusconnect.logic;

import edu.luc.comp330.campusconnect.logic.RoleCheckService.RoleCheckOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the central simulated role check (Issue #32 / TASK-110, API-005, REQ-006).
 * Covers allowed and denied roles, missing and unknown roles, and how role text is read.
 * Related criteria: AC-REQ-002-03 and AC-REQ-003-04 (students denied staff actions).
 */
class RoleCheckServiceTest {

    private RoleCheckService roleCheck;

    @BeforeEach
    void setUp() {
        roleCheck = new RoleCheckService();
    }

    // --- Allowed: the role matches what the action requires ---

    @Test
    void studentIsAllowedWhenStudentRoleIsRequired() {
        RoleCheckOutcome outcome = roleCheck.requireRole("STUDENT", Role.STUDENT, "SUBMIT_REQUEST");

        assertTrue(outcome.authorized());
        assertEquals(Role.STUDENT, outcome.resolvedRole());
        assertNull(outcome.denialReason());
    }

    @Test
    void staffIsAllowedWhenStaffRoleIsRequired() {
        RoleCheckOutcome outcome = roleCheck.requireRole("STAFF", Role.STAFF, "LIST_REQUESTS");

        assertTrue(outcome.authorized());
        assertEquals(Role.STAFF, outcome.resolvedRole());
        assertNull(outcome.denialReason());
    }

    // --- Denied: a known role that does not match ---

    @Test
    void studentIsDeniedStaffActions() {
        // AC-REQ-002-03 (staff list) and AC-REQ-003-04 (status change)
        assertDenied(roleCheck.requireRole("STUDENT", Role.STAFF, "LIST_REQUESTS"));
        assertDenied(roleCheck.requireRole("STUDENT", Role.STAFF, "UPDATE_STATUS"));
    }

    @Test
    void staffIsDeniedStudentOnlySubmission() {
        // API-005: staff may not submit requests in Cycle 1 (C-Q3)
        assertDenied(roleCheck.requireRole("STAFF", Role.STUDENT, "SUBMIT_REQUEST"));
    }

    // --- Denied: missing or unknown roles get no permissions (API-005) ---

    @Test
    void missingRoleIsDenied() {
        assertDenied(roleCheck.requireRole(null, Role.STAFF, "LIST_REQUESTS"));
        assertDenied(roleCheck.requireRole("", Role.STAFF, "LIST_REQUESTS"));
        assertDenied(roleCheck.requireRole("   ", Role.STAFF, "LIST_REQUESTS"));
    }

    @Test
    void unknownRoleIsDenied() {
        assertDenied(roleCheck.requireRole("ADMIN", Role.STAFF, "LIST_REQUESTS"));
        assertDenied(roleCheck.requireRole("STAFFF", Role.STAFF, "LIST_REQUESTS"));
        assertDenied(roleCheck.requireRole("STUDENT,STAFF", Role.STAFF, "LIST_REQUESTS"));
    }

    @Test
    void missingRoleIsDeniedEvenForStudentActions() {
        assertDenied(roleCheck.requireRole(null, Role.STUDENT, "SUBMIT_REQUEST"));
    }

    // --- How role text is read (current behavior, see ADR-0003) ---

    @Test
    void roleTextIgnoresCaseAndSurroundingSpaces() {
        RoleCheckOutcome outcome = roleCheck.requireRole("  staff ", Role.STAFF, "LIST_REQUESTS");

        assertTrue(outcome.authorized());
        assertEquals(Role.STAFF, outcome.resolvedRole());
    }

    @Test
    void parsingReturnsEmptyForMissingOrUnknownText() {
        assertTrue(Role.fromHeaderOrParam(null).isEmpty());
        assertTrue(Role.fromHeaderOrParam(" ").isEmpty());
        assertTrue(Role.fromHeaderOrParam("ADMIN").isEmpty());
        assertEquals(Role.STUDENT, Role.fromHeaderOrParam("Student").orElseThrow());
    }

    private static void assertDenied(RoleCheckOutcome outcome) {
        assertFalse(outcome.authorized());
        assertNull(outcome.resolvedRole());
        assertNotNull(outcome.denialReason());
        assertFalse(outcome.denialReason().isBlank());
    }
}