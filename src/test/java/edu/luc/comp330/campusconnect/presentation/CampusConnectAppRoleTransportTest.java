package edu.luc.comp330.campusconnect.presentation;

import com.sun.net.httpserver.HttpServer;
import edu.luc.comp330.campusconnect.data.InMemorySupportRequestRepository;
import edu.luc.comp330.campusconnect.logic.RoleCheckService;
import edu.luc.comp330.campusconnect.logic.SupportRequestService;
import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.observability.AuditLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for RF-06 / CRF-003 / CRF-023: the simulated role is read from the
 * X-Simulated-Role header only (never from the URL), and status changes use PATCH only
 * (ADR-0003, API-003, API-005, REQ-006).
 */
class CampusConnectAppRoleTransportTest {

    private static final String VALID_REQUEST =
            "{\"category\":\"IT\",\"title\":\"Wifi\",\"description\":\"Synthetic\"}";

    private InMemorySupportRequestRepository repository;
    private HttpServer server;
    private String baseUrl;
    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void setUp() throws IOException {
        repository = new InMemorySupportRequestRepository();
        AuditLogger logger = new AuditLogger(false);
        SupportRequestService service = new SupportRequestService(repository, new RoleCheckService(), logger);
        server = new CampusConnectApp(service, logger).startServer(0);
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort() + "/api/requests";
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    private HttpResponse<String> send(String method, String pathAndQuery, String role, String body) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(baseUrl + pathAndQuery))
                .method(method, body == null
                        ? HttpRequest.BodyPublishers.noBody()
                        : HttpRequest.BodyPublishers.ofString(body));
        if (role != null) {
            request.header("X-Simulated-Role", role);
        }
        return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private void submitOneRequest() throws Exception {
        assertEquals(201, send("POST", "", "STUDENT", VALID_REQUEST).statusCode());
    }

    // --- RF-06 / CRF-003: role from the header only ---

    @Test
    void roleInQueryStringIsIgnored() throws Exception {
        submitOneRequest();

        HttpResponse<String> first = send("GET", "?role=STAFF", null, null);
        HttpResponse<String> notFirst = send("GET", "?x=1&role=STAFF", null, null);

        assertEquals(403, first.statusCode());
        assertEquals(403, notFirst.statusCode());
        assertTrue(first.body().contains("\"status\":\"ROLE_DENIED\""));
        assertFalse(first.body().contains("REQ-1001"));
    }

    @Test
    void roleInQueryStringCannotSubmit() throws Exception {
        HttpResponse<String> response = send("POST", "?role=STUDENT", null, VALID_REQUEST);

        assertEquals(403, response.statusCode());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void headerRoleWorksWithAnyQueryParameters() throws Exception {
        submitOneRequest();

        HttpResponse<String> extra = send("GET", "?x=1", "STAFF", null);
        HttpResponse<String> conflicting = send("GET", "?x=1&role=STUDENT", "STAFF", null);

        assertEquals(200, extra.statusCode());
        assertEquals(200, conflicting.statusCode());
        assertTrue(conflicting.body().contains("\"id\":\"REQ-1001\""));
    }

    // --- CRF-001 / CRF-023: status changes use PATCH only ---

    @Test
    void statusChangeWithPatchSucceeds() throws Exception {
        submitOneRequest();

        HttpResponse<String> response = send("PATCH", "/REQ-1001/status", "STAFF", "{\"newStatus\":\"IN_PROGRESS\"}");

        assertEquals(200, response.statusCode());
        assertEquals(RequestStatus.IN_PROGRESS, repository.findById("REQ-1001").orElseThrow().getStatus());
    }

    @Test
    void statusChangeWithPostIsRejectedAndStatusUnchanged() throws Exception {
        submitOneRequest();

        HttpResponse<String> response = send("POST", "/REQ-1001/status", "STAFF", "{\"newStatus\":\"IN_PROGRESS\"}");

        assertEquals(404, response.statusCode());
        assertEquals(RequestStatus.OPEN, repository.findById("REQ-1001").orElseThrow().getStatus());
    }
}
