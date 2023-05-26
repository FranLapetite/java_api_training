package fr.lernejo.navy_battle;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 1) {
            startServer(Integer.parseInt(args[0]), "localhost");
        } else if (args.length == 2) {
            startServerWithClient(Integer.parseInt(args[0]), "localhost", args[1]);
        } else {
            printUsage();
        }
    }

    private static void startServer(int port, String host) throws IOException, InterruptedException {
        Server server = new Server(port, host);
        server.init();
    }

    private static void startServerWithClient(int port, String host, String serverPort) throws IOException, InterruptedException {
        Server server = new Server(port, host, serverPort);
        server.init();
        server.getClt().startGame();
    }

    private static void printUsage() {
        System.out.println("Usage: java Launcher <Server> port | <Client> port serverPort");
    }
}
