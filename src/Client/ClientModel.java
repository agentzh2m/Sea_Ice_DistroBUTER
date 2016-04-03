package Client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;

public class ClientModel {
    public static float currentProgress;
    public static File clientTorrentFile;
    public static InetSocketAddress serverUrl;
    public ClientModel(){
        //open listen server for the client stable HTTPConnection
        try {
            HttpServer listenServer = HttpServer.create();
            listenServer.bind(new InetSocketAddress(InetAddress.getLocalHost(),8000), 0);
            System.out.println("Server listen on: " + listenServer.getAddress());
            listenServer.createContext("/connect", new listenHandler());
            listenServer.createContext("/getTorrent", new receiveTorrentHandler());
            listenServer.createContext("/startTorrent" ,new startTorrentHandler());
            listenServer.start();
            System.out.println("Client HTTP server is running");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class listenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            OutputStream os = exchange.getResponseBody();
            serverUrl = exchange.getLocalAddress();
            System.out.println("Connected with server: " +serverUrl.getHostString());
            String myreply = "REQ_TORRENT_DL\n";
            exchange.sendResponseHeaders(200, myreply.length());
            os.write(myreply.getBytes());
            os.close();
        }
    }

    static class receiveTorrentHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange){
            System.out.println("Start to Download torrent from server");
            try {
                //Downloading file from server
                HttpURLConnection con = (HttpURLConnection) new URL("http://"+serverUrl.getHostString()+":8888/downloadTorrent").openConnection();
                if (con.getResponseCode() == 200){
                    System.out.println("In the process of receiving the file of size: " + con.getContentLength());
                    clientTorrentFile = new File("cl_distro.torrent");
                    FileOutputStream fos = new FileOutputStream(clientTorrentFile);
                    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                    byte[] buffer = new byte[con.getContentLength()];
                    System.out.println("byte rcv "+bis.read(buffer, 0, con.getContentLength()));
                    fos.write(buffer, 0, buffer.length);
                    fos.close();
                }
                String myreply = "FINISH DOWNLOADING\n";
                OutputStream os = exchange.getResponseBody();
                exchange.sendResponseHeaders(200, myreply.length());
                os.write(myreply.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    static class startTorrentHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange){
            System.out.println("Receive command to start torrent");
            try {
                Client client = new Client(new InetSocketAddress(5555).getAddress(),
                        SharedTorrent.fromFile(clientTorrentFile, new File(".")));
                client.share();
                System.out.println("Current torrent state: " + client.getState());
                client.addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        Client client1 = (Client) o;
                        float progress = client.getTorrent().getCompletion();
                        //client can tracker their download progress
                        System.out.println("Current progress: " + progress);
                        currentProgress = progress;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String msg = "START_TORRENT_OK\n";
                exchange.sendResponseHeaders(200, msg.length());
                OutputStream os = exchange.getResponseBody();
                os.write(msg.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
