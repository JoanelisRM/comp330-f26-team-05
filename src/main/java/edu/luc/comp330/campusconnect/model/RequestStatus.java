package edu.luc.comp330.campusconnect.model;

/**
 * Allowed workflow statuses for a CampusConnect support request in Cycle 1.
 * Enforces forward-only progression (OPEN -> IN_PROGRESS -> RESOLVED) per REQ-003 and Q-002.
 */
public enum RequestStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED;

    /**
     * Checks whether transitioning from the current status to targetStatus is permitted in Cycle 1.
     * Reopening a RESOLVED request or skipping IN_PROGRESS is rejected (Q-002).
     */
    public boolean canTransitionTo(RequestStatus targetStatus) {
        if (targetStatus == null) {
            return false;
        }
        return switch (this) {
            case OPEN -> targetStatus == IN_PROGRESS;
            case IN_PROGRESS -> targetStatus == RESOLVED;
            case RESOLVED -> false;
        };
    }
}
