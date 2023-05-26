package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private final HttpClient client;
    final int port;
    final String servUrl;

    public Client(int port, String url)
    {
        this.client = HttpClient.newHttpClient();
        this.servUrl = url;
        this.port = port;

    }

    public int startGame() throws IOException, InterruptedException {

        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(this.servUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"Bonjour_toi\"}"))
            .build();
        HttpResponse<String> resp = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        return resp.statusCode();
    }
}
