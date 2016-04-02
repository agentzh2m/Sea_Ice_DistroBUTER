import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientMain {
    private ClientMain(){
        //open listen server for the client stable HTTPConnection
        try {
            HttpServer listenServer = HttpServer.create();
            listenServer.bind(InetSocketAddress.createUnresolved("localhost", 80), 0);
            System.out.println("Server listen on: " + listenServer.getAddress());
            listenServer.start();
            listenServer.createContext("/html_content");
            listenServer.createContext("/");
            System.out.println("Client HTTP server is running");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
