package edu.luc.comp330.campusconnect.data;

import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory implementation of API-004 (SupportRequestRepository) for Cycle 1 (Issue #31 / TASK-102, Q-003, REQ-005).
 * Preserves insertion order, returns defensive copies, and fails explicitly on unknown IDs.
 */
public class InMemorySupportRequestRepository implements SupportRequestRepository {

    private final Map<String, SupportRequest> store = Collections.synchronizedMap(new LinkedHashMap<>());
    private final AtomicInteger sequence = new AtomicInteger(1000);

    @Override
    public String nextRequestId() {
        return "REQ-" + sequence.incrementAndGet();
    }

    @Override
    public SupportRequest save(SupportRequest request) {
        Objects.requireNonNull(request, "SupportRequest to save must not be null");
        SupportRequest copy = request.copy();
        store.put(copy.getRequestId(), copy);
        return copy.copy();
    }

    @Override
    public Optional<SupportRequest> findById(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return Optional.empty();
        }
        SupportRequest found = store.get(requestId.trim());
        return Optional.ofNullable(found == null ? null : found.copy());
    }

    @Override
    public List<SupportRequest> findAll() {
        synchronized (store) {
            List<SupportRequest> results = new ArrayList<>(store.size());
            for (SupportRequest req : store.values()) {
                results.add(req.copy());
            }
            return Collections.unmodifiableList(results);
        }
    }

    @Override
    public SupportRequest updateStatus(String requestId, RequestStatus newStatus, Instant updatedAt) {
        Objects.requireNonNull(requestId, "requestId must not be null");
        Objects.requireNonNull(newStatus, "newStatus must not be null");
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");

        synchronized (store) {
            SupportRequest existing = store.get(requestId.trim());
            if (existing == null) {
                throw new IllegalArgumentException("Support request not found: " + requestId);
            }
            SupportRequest updated = existing.withStatus(newStatus, updatedAt);
            store.put(updated.getRequestId(), updated);
            return updated.copy();
        }
    }

    @Override
    public void clear() {
        store.clear();
        sequence.set(1000);
    }
}
