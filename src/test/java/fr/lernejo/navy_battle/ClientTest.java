package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClientTest {

    @Test
    void startGameValid() throws IOException, InterruptedException {
        int serverPort = 9898;
        int clientPort = 9897;
        String serverUrl = "http://localhost:" + serverPort;
        Server server = new Server(serverPort, "localhost");
        server.init();
        Server client = new Server(clientPort, "localhost", serverUrl);
        client.init();
        int responseCode = client.getClt().startGame();
        Assertions.assertEquals(202, responseCode);
    }
}
