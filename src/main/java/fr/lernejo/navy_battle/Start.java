package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;


public class Start implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            CheckJsonAndRespond(exchange);
        } else {
            exchange.sendResponseHeaders(404, "NOT FOUND".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("NOT FOUND".getBytes());
            }
        }
    }

    private boolean CheckJson(JSONObject jsonData) {
        try {
            File schemaFile = new File("src/main/resources/startJsonSchema.json");
            JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));
            JSONObject jsonSchema = new JSONObject(schemaData);
            Schema schemaValidator = SchemaLoader.load(jsonSchema);
            schemaValidator.validate(jsonData);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private void CheckJsonAndRespond(HttpExchange exchange) throws IOException{
        JSONObject jsonData = new JSONObject(new JSONTokener(new InputStreamReader(exchange.getRequestBody())));
        if (!CheckJson(jsonData))
        {
            exchange.sendResponseHeaders(400, "BAD REQUEST".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("BAD REQUEST".getBytes());
            }
        }
        String json = "{\"id\":\"1\", \"url\":\"http://localhost:" + exchange.getLocalAddress().getPort() + "\", \"message\":\"Good luck!\"}";
        exchange.sendResponseHeaders(202, json.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes());
        }
        System.out.println("Client connected");
    }
}
