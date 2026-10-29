package edu.luc.comp330.campusconnect.logic;

import java.util.Optional;

public enum Role {
    STUDENT,
    STAFF;

    public static Optional<Role> fromHeaderOrParam(String rawRole) {
        if (rawRole == null || rawRole.isBlank()) {
            return Optional.empty();
        }
        return switch (rawRole.trim().toUpperCase()) {
            case "STUDENT" -> Optional.of(STUDENT);
            case "STAFF" -> Optional.of(STAFF);
            default -> Optional.empty();
        };
    }
}
