package edu.luc.comp330.campusconnect.observability;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Structured runtime & audit logger for CampusConnect Cycle 1 (Issue #49 / TASK-108 / Q-007).
 * Captures submission events, validation rejections, role-check denials, and status transitions
 * without logging credentials or real student PII (REQ-004).
 */
public class AuditLogger {

    public record LogEntry(
            Instant timestamp,
            String level,
            String eventType,
            String simulatedRole,
            String requestId,
            String outcome,
            String details) {

        public String formatLine() {
            return String.format(
                    "%s | level=%-5s | event=%-20s | role=%-7s | requestId=%-8s | outcome=%-8s | details=%s",
                    timestamp,
                    level,
                    eventType,
                    simulatedRole == null || simulatedRole.isBlank() ? "NONE" : simulatedRole,
                    requestId == null || requestId.isBlank() ? "N/A" : requestId,
                    outcome,
                    details);
        }
    }

    private final List<LogEntry> entries = Collections.synchronizedList(new ArrayList<>());
    private final boolean consoleOutputEnabled;

    public AuditLogger() {
        this(true);
    }

    public AuditLogger(boolean consoleOutputEnabled) {
        this.consoleOutputEnabled = consoleOutputEnabled;
    }

    public LogEntry log(
            String level,
            String eventType,
            String simulatedRole,
            String requestId,
            String outcome,
            String details) {
        LogEntry entry = new LogEntry(
                Instant.now(),
                Objects.requireNonNullElse(level, "INFO"),
                Objects.requireNonNullElse(eventType, "UNSPECIFIED"),
                simulatedRole,
                requestId,
                Objects.requireNonNullElse(outcome, "UNKNOWN"),
                Objects.requireNonNullElse(details, ""));
        entries.add(entry);
        if (consoleOutputEnabled) {
            System.out.println(entry.formatLine());
        }
        return entry;
    }

    public List<LogEntry> getEntries() {
        synchronized (entries) {
            return List.copyOf(entries);
        }
    }

    public void clear() {
        entries.clear();
    }
}
