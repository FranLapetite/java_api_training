package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class Custom implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            handleGetRequest(exchange);
        } else {
            handleBadRequest(exchange);
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        JSONObject response = new JSONObject("{\"consequence\": \"sunk\",  \"shipLeft\":true}");
        sendResponse(exchange, response.toString(), 200);
    }

    private void handleBadRequest(HttpExchange exchange) throws IOException {
        sendResponse(exchange, "BAD REQUEST", 400);
    }

    private void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
        exchange.getResponseHeaders().add("Accept", "application/json");
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
