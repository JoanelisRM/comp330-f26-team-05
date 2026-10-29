package edu.luc.comp330.campusconnect.presentation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import edu.luc.comp330.campusconnect.data.InMemorySupportRequestRepository;
import edu.luc.comp330.campusconnect.data.SupportRequestRepository;
import edu.luc.comp330.campusconnect.logic.RoleCheckService;
import edu.luc.comp330.campusconnect.logic.SupportRequestService;
import edu.luc.comp330.campusconnect.logic.SupportRequestService.ServiceResult;
import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;
import edu.luc.comp330.campusconnect.observability.AuditLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Presentation Layer entry point for CampusConnect Cycle 1 (Issue #30 / TASK-101, ADR-0003).
 * Provides both:
 * 1. A deterministic vertical-slice verification runner (`--demo`, default when run via `mvn compile exec:java`), and
 * 2. A localhost HTTP/JSON server (`--serve [port]`) using JDK's built-in HttpServer bound to 127.0.0.1.
 */
public class CampusConnectApp {

    private final SupportRequestService service;
    private final AuditLogger auditLogger;

    public CampusConnectApp(SupportRequestService service, AuditLogger auditLogger) {
        this.service = service;
        this.auditLogger = auditLogger;
    }

    public static void main(String[] args) throws IOException {
        SupportRequestRepository repository = new InMemorySupportRequestRepository();
        RoleCheckService roleCheckService = new RoleCheckService();
        AuditLogger logger = new AuditLogger(true);
        SupportRequestService service = new SupportRequestService(repository, roleCheckService, logger);
        CampusConnectApp app = new CampusConnectApp(service, logger);

        if (args.length > 0 && "--serve".equalsIgnoreCase(args[0])) {
            int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
            HttpServer server = app.startServer(port);
            System.out.println("CampusConnect localhost HTTP/JSON server listening on http://127.0.0.1:"
                    + server.getAddress().getPort());
        } else {
            app.runDiagnosticVerificationScenario();
        }
    }

    /**
     * Runs the 7-step Cycle 1 verification & diagnostic logging scenario (Issue #49).
     */
    public void runDiagnosticVerificationScenario() {
        System.out.println("=== CampusConnect Cycle 1 Runtime & Diagnostics Verification ===");

        // 1. Valid student request submission (REQ-001, API-001)
        ServiceResult<SupportRequest> submitOk = service.submitRequest(
                "STUDENT",
                "Academic Advising",
                "COMP 330 section conflict",
                "Synthetic request: need override for Thursday lab slot");

        String reqId = submitOk.data() != null ? submitOk.data().getRequestId() : "REQ-1001";

        // 2. Rejected student submission with empty/whitespace fields (AC-REQ-001-02)
        service.submitRequest("STUDENT", "Academic Advising", "   ", "");

        // 3. Unauthorized role attempt: STUDENT attempting staff status update (REQ-006, API-005)
        service.updateStatus("STUDENT", reqId, RequestStatus.IN_PROGRESS);

        // 4. Staff listing requests (REQ-002, API-002)
        service.listRequests("STAFF");

        // 5. Staff valid transition OPEN -> IN_PROGRESS (REQ-003, API-003)
        service.updateStatus("STAFF", reqId, RequestStatus.IN_PROGRESS);

        // 6. Staff valid transition IN_PROGRESS -> RESOLVED (REQ-003, API-003)
        service.updateStatus("STAFF", reqId, RequestStatus.RESOLVED);

        // 7. Staff disallowed transition RESOLVED -> OPEN (Q-002, REQ-003)
        service.updateStatus("STAFF", reqId, RequestStatus.OPEN);

        long successes = auditLogger.getEntries().stream()
                .filter(e -> "SUCCESS".equals(e.outcome()))
                .count();
        System.out.printf(
                "=== Operational Summary: totalEvents=%d, successes=%d, rejectionsOrDenials=%d ===%n",
                auditLogger.getEntries().size(),
                successes,
                auditLogger.getEntries().size() - successes);
    }

    /**
     * Starts the localhost HTTP/JSON server implementing API-001..003 per ADR-0003.
     */
    public HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/api/requests", this::handleRequestsEndpoint);
        server.start();
        return server;
    }

    private void handleRequestsEndpoint(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();
        String path = exchange.getRequestURI().getPath();
        String role = exchange.getRequestHeaders().getFirst("X-Simulated-Role");
        if (role == null || role.isBlank()) {
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.startsWith("role=")) {
                role = query.substring("role=".length());
            }
        }

        if ("/api/requests".equals(path) && "GET".equals(method)) {
            ServiceResult<List<SupportRequest>> res = service.listRequests(role);
            sendResult(exchange, mapHttpCode(res.statusCode(), 200), toJsonListResponse(res));
            return;
        }

        if ("/api/requests".equals(path) && "POST".equals(method)) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String category = extractJsonField(body, "category");
            String title = extractJsonField(body, "title");
            String description = extractJsonField(body, "description");
            ServiceResult<SupportRequest> res = service.submitRequest(role, category, title, description);
            sendResult(exchange, mapHttpCode(res.statusCode(), 201), toJsonSingleResponse(res));
            return;
        }

        if (path.startsWith("/api/requests/") && path.endsWith("/status")
                && ("PATCH".equals(method) || "POST".equals(method))) {
            String idPart = path.substring("/api/requests/".length(), path.length() - "/status".length());
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String rawStatus = extractJsonField(body, "newStatus");
            RequestStatus target = null;
            if (rawStatus != null && !rawStatus.isBlank()) {
                try {
                    target = RequestStatus.valueOf(rawStatus.trim().toUpperCase());
                } catch (IllegalArgumentException ignored) {
                    target = null;
                }
            }
            ServiceResult<SupportRequest> res = service.updateStatus(role, idPart, target);
            sendResult(exchange, mapHttpCode(res.statusCode(), 200), toJsonSingleResponse(res));
            return;
        }

        sendResult(exchange, 404, "{\"status\":\"NOT_FOUND\",\"message\":\"Endpoint not found\"}");
    }

    private static int mapHttpCode(SupportRequestService.StatusCode code, int successCode) {
        return switch (code) {
            case SUCCESS -> successCode;
            case VALIDATION_ERROR -> 400;
            case ROLE_DENIED -> 403;
            case NOT_FOUND -> 404;
            case INVALID_TRANSITION -> 409;
        };
    }

    private static String extractJsonField(String json, String key) {
        if (json == null) {
            return null;
        }
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) {
            return null;
        }
        int colon = json.indexOf(':', idx + pattern.length());
        if (colon < 0) {
            return null;
        }
        int firstQuote = json.indexOf('"', colon + 1);
        if (firstQuote < 0) {
            return null;
        }
        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote < 0) {
            return null;
        }
        return json.substring(firstQuote + 1, secondQuote);
    }

    private static String toJsonSingleResponse(ServiceResult<SupportRequest> res) {
        if (res.data() == null) {
            return String.format(
                    "{\"status\":\"%s\",\"missingFields\":%s,\"message\":\"%s\"}",
                    res.statusCode(),
                    res.missingFields(),
                    escape(res.message()));
        }
        SupportRequest r = res.data();
        return String.format(
                "{\"status\":\"%s\",\"request\":{\"requestId\":\"%s\",\"category\":\"%s\",\"title\":\"%s\",\"requestStatus\":\"%s\",\"synthetic\":%b}}",
                res.statusCode(),
                escape(r.getRequestId()),
                escape(r.getCategory()),
                escape(r.getTitle()),
                r.getStatus(),
                r.isSynthetic());
    }

    private static String toJsonListResponse(ServiceResult<List<SupportRequest>> res) {
        if (res.data() == null) {
            return String.format(
                    "{\"status\":\"%s\",\"message\":\"%s\"}",
                    res.statusCode(),
                    escape(res.message()));
        }
        return String.format(
                "{\"status\":\"%s\",\"count\":%d}",
                res.statusCode(),
                res.data().size());
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }

    private static void sendResult(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
