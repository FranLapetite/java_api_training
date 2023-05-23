import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java Launcher <port>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        HttpHandler pingHandler = createPingHandler();
        server.createContext("/ping", pingHandler);
        server.start();
        System.out.println("Server started on port " + port);
    }

    private static HttpHandler createPingHandler() {
        return new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "OK";
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(response.getBytes());
                }
            }
        };
    }
}
