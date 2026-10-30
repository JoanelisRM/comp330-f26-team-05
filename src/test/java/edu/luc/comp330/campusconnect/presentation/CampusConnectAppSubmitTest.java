package edu.luc.comp330.campusconnect.presentation;

import com.sun.net.httpserver.HttpServer;
import edu.luc.comp330.campusconnect.data.InMemorySupportRequestRepository;
import edu.luc.comp330.campusconnect.data.SupportRequestRepository;
import edu.luc.comp330.campusconnect.logic.RoleCheckService;
import edu.luc.comp330.campusconnect.logic.SupportRequestService;
import edu.luc.comp330.campusconnect.model.RequestStatus;
import edu.luc.comp330.campusconnect.model.SupportRequest;
import edu.luc.comp330.campusconnect.observability.AuditLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HTTP-level tests for API-001 submit support request (Issue #33 / TASK-103, REQ-001, AC-REQ-001-01/02).
 */
class CampusConnectAppSubmitTest {

    private InMemorySupportRequestRepository repository;
    private HttpServer server;
    private URI requestsUri;
    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void setUp() throws IOException {
        repository = new InMemorySupportRequestRepository();
        startServer(repository);
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    private void startServer(SupportRequestRepository repo) throws IOException {
        AuditLogger logger = new AuditLogger(false);
        SupportRequestService service = new SupportRequestService(repo, new RoleCheckService(), logger);
        server = new CampusConnectApp(service, logger).startServer(0);
        requestsUri = URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/api/requests");
    }

    private HttpResponse<String> submit(String role, String json) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(requestsUri)
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (role != null) {
            request.header("X-Simulated-Role", role);
        }
        return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void validSubmissionReturns201WithSystemAssignedIdStatusAndTime() throws Exception {
        HttpResponse<String> response = submit("STUDENT",
                "{\"category\":\"IT Support\",\"title\":\"Wifi drops\",\"description\":\"Synthetic: library wifi\"}");

        assertEquals(201, response.statusCode());
        SupportRequest stored = repository.findById("REQ-1001").orElseThrow();
        assertEquals(RequestStatus.OPEN, stored.getStatus());
        assertTrue(response.body().contains("\"requestId\":\"REQ-1001\""));
        assertTrue(response.body().contains("\"requestStatus\":\"OPEN\""));
        assertTrue(response.body().contains("\"description\":\"Synthetic: library wifi\""));
        assertTrue(response.body().contains("\"submittedAt\":\"" + stored.getSubmittedAt() + "\""));
    }

    @Test
    void clientSuppliedIdStatusAndTimeAreIgnored() throws Exception {
        HttpResponse<String> response = submit("STUDENT",
                "{\"requestId\":\"FAKE-1\",\"requestStatus\":\"RESOLVED\",\"submittedAt\":\"2000-01-01T00:00:00Z\","
                        + "\"category\":\"Advising\",\"title\":\"Override\",\"description\":\"Synthetic\"}");

        assertEquals(201, response.statusCode());
        SupportRequest stored = repository.findById("REQ-1001").orElseThrow();
        assertEquals(RequestStatus.OPEN, stored.getStatus());
        assertNotEquals(Instant.parse("2000-01-01T00:00:00Z"), stored.getSubmittedAt());
        assertTrue(repository.findById("FAKE-1").isEmpty());
    }

    @Test
    void missingFieldsReturn400ListingEveryMissingField() throws Exception {
        HttpResponse<String> response = submit("STUDENT", "{\"category\":\"IT Support\"}");

        assertEquals(400, response.statusCode());
        assertTrue(response.body().contains("\"missingFields\":[\"title\",\"description\"]"));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void whitespaceOnlyFieldsCountAsMissing() throws Exception {
        HttpResponse<String> response = submit("STUDENT",
                "{\"category\":\"   \",\"title\":\"\\t\",\"description\":\"Synthetic\"}");

        assertEquals(400, response.statusCode());
        assertTrue(response.body().contains("\"missingFields\":[\"category\",\"title\"]"));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void malformedBodyReportsAllFieldsMissing() throws Exception {
        HttpResponse<String> response = submit("STUDENT", "category=IT&title=x&description=y");

        assertEquals(400, response.statusCode());
        assertTrue(response.body().contains("\"missingFields\":[\"category\",\"title\",\"description\"]"));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void staffOrMissingRoleCannotSubmit() throws Exception {
        String body = "{\"category\":\"IT\",\"title\":\"Wifi\",\"description\":\"Synthetic\"}";

        assertEquals(403, submit("STAFF", body).statusCode());
        assertEquals(403, submit(null, body).statusCode());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void quotesAndBackslashesInFieldsArePreserved() throws Exception {
        HttpResponse<String> response = submit("STUDENT",
                "{\"category\":\"IT\",\"title\":\"Say \\\"hi\\\" \\\\ ok\",\"description\":\"Synthetic\"}");

        assertEquals(201, response.statusCode());
        assertEquals("Say \"hi\" \\ ok", repository.findById("REQ-1001").orElseThrow().getTitle());
        assertTrue(response.body().contains("\"title\":\"Say \\\"hi\\\" \\\\ ok\""));
    }

    @Test
    void storageFailureReturns500AndNeverSuccess() throws Exception {
        server.stop(0);
        startServer(new InMemorySupportRequestRepository() {
            @Override
            public SupportRequest save(SupportRequest request) {
                throw new IllegalStateException("synthetic storage failure");
            }
        });

        HttpResponse<String> response = submit("STUDENT",
                "{\"category\":\"IT\",\"title\":\"Wifi\",\"description\":\"Synthetic\"}");

        assertEquals(500, response.statusCode());
        assertFalse(response.body().contains("SUCCESS"));
    }
}
