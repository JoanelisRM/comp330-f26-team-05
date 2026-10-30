package edu.luc.comp330.campusconnect.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Cycle 1 status rule (Issue #35 / TASK-105, Issue #36 / TASK-106).
 * Checks all nine from/to combinations of OPEN, IN_PROGRESS and RESOLVED, plus a null target.
 * Only OPEN -> IN_PROGRESS and IN_PROGRESS -> RESOLVED are allowed (REQ-003, API-003, ASM-006, Q-002).
 * Related criteria: AC-REQ-003-01, AC-REQ-003-02 (allowed) and AC-REQ-003-03 (rejected).
 */
class RequestStatusTest {

    // --- Allowed: one step forward (AC-REQ-003-01, AC-REQ-003-02) ---

    @Test
    void openCanMoveToInProgress() {
        assertTrue(RequestStatus.OPEN.canTransitionTo(RequestStatus.IN_PROGRESS));
    }

    @Test
    void inProgressCanMoveToResolved() {
        assertTrue(RequestStatus.IN_PROGRESS.canTransitionTo(RequestStatus.RESOLVED));
    }

    // --- Rejected: skipping a step (AC-REQ-003-03) ---

    @Test
    void openCannotSkipToResolved() {
        assertFalse(RequestStatus.OPEN.canTransitionTo(RequestStatus.RESOLVED));
    }

    // --- Rejected: moving backward or reopening (AC-REQ-003-03, Q-002) ---

    @Test
    void inProgressCannotMoveBackToOpen() {
        assertFalse(RequestStatus.IN_PROGRESS.canTransitionTo(RequestStatus.OPEN));
    }

    @Test
    void resolvedCannotBeReopened() {
        assertFalse(RequestStatus.RESOLVED.canTransitionTo(RequestStatus.OPEN));
        assertFalse(RequestStatus.RESOLVED.canTransitionTo(RequestStatus.IN_PROGRESS));
    }

    // --- Rejected: "changing" to the same status ---

    @Test
    void statusCannotChangeToItself() {
        for (RequestStatus status : RequestStatus.values()) {
            assertFalse(status.canTransitionTo(status), status + " -> " + status + " should be rejected");
        }
    }

    // --- Rejected: missing target ---

    @Test
    void nullTargetIsRejected() {
        for (RequestStatus status : RequestStatus.values()) {
            assertFalse(status.canTransitionTo(null), status + " -> null should be rejected");
        }
    }

    // --- Whole rule at once: exactly two of the nine moves are allowed ---

    @Test
    void onlyTwoOfNineTransitionsAreAllowed() {
        int allowed = 0;
        for (RequestStatus from : RequestStatus.values()) {
            for (RequestStatus to : RequestStatus.values()) {
                if (from.canTransitionTo(to)) {
                    allowed++;
                }
            }
        }
        assertEquals(2, allowed, "Only OPEN -> IN_PROGRESS and IN_PROGRESS -> RESOLVED should be allowed");
    }

    @Test
    void cycleOneHasExactlyThreeStatuses() {
        // If a status is added (e.g. CANCELLED for Q-006), this test fails so the rule and its tests are revisited.
        assertEquals(3, RequestStatus.values().length);
    }
}