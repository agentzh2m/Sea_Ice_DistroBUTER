package Client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ClientModel {
    private ClientModel(){
        //open listen server for the client stable HTTPConnection
        try {
            HttpServer listenServer = HttpServer.create();
            listenServer.bind(new InetSocketAddress(8000), 0);
            System.out.println("Server listen on: " + listenServer.getAddress());
            listenServer.createContext("/connect", new listenHandler());
            listenServer.start();
            System.out.println("Client HTTP server is running");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class listenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            String myres = "Connected to client";
            exchange.sendResponseHeaders(200, myres.length());
            OutputStream os = exchange.getResponseBody();
            os.write(myres.getBytes());
            String myreply = "REQ_TORRENT_DL";
            os.close();
            InputStream is = exchange.getRequestBody();
        }
    }
}
