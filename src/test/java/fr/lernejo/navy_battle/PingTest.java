package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class PingTest {
    private static final int SERVER_PORT = 9855;
    private static final String SERVER_URL = "http://localhost:" + SERVER_PORT;
    private HttpClient client;

    @BeforeEach
    void setUp() {
        client = HttpClient.newHttpClient();
    }

    @Test
    void handlePingRequest() throws IOException, InterruptedException {
        Server server = new Server(SERVER_PORT, "localhost");
        server.init();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(SERVER_URL + "/ping"))
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:6666\", \"message\":\"Bonjour_toi\"}"))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }
}
