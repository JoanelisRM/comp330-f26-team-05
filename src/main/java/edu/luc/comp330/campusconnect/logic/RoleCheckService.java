package edu.luc.comp330.campusconnect.logic;

import java.util.Optional;

public class RoleCheckService {

    public record RoleCheckOutcome(boolean authorized, Role resolvedRole, String denialReason) {
        public static RoleCheckOutcome allow(Role role) {
            return new RoleCheckOutcome(true, role, null);
        }

        public static RoleCheckOutcome deny(String reason) {
            return new RoleCheckOutcome(false, null, reason);
        }
    }

    public RoleCheckOutcome requireRole(String rawRole, Role requiredRole, String actionName) {
        Optional<Role> parsed = Role.fromHeaderOrParam(rawRole);
        if (parsed.isEmpty()) {
            String shown = (rawRole == null || rawRole.isBlank()) ? "MISSING" : rawRole.trim();
            return RoleCheckOutcome.deny(
                    "Missing or unrecognized simulated role '" + shown + "' for action '" + actionName
                            + "' (requires " + requiredRole + ")");
        }
        if (parsed.get() != requiredRole) {
            return RoleCheckOutcome.deny(
                    "Role '" + parsed.get() + "' is not authorized for action '" + actionName
                            + "' (requires " + requiredRole + ")");
        }
        return RoleCheckOutcome.allow(parsed.get());
    }
}
