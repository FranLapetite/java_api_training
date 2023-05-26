package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    public final int port;
    public final String url;
    public final Client clt;

    public Server(int port, String url)
    {
        this.clt = null;
        this.port = port;
        this.url = url;
    }

    public Server(int port, String url,String servUrl)
    {
        this.port = port;
        this.clt = new Client(port,servUrl);
        this.url = url;
    }

    public Client getClt() {
        return clt;
    }

    public boolean init() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.url, this.port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new Ping());
        server.createContext("/api/game/start", new Start());
        server.createContext("/api/game/fire", new Custom(this));
        server.start();
        System.out.println("Server started at port : " + this.port);
        return true;
    }

}
