package Client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;

public class ClientModel {
    public ClientModel(){
        //open listen server for the client stable HTTPConnection
        try {
            HttpServer listenServer = HttpServer.create();
            listenServer.bind(new InetSocketAddress(InetAddress.getLocalHost(),8000), 0);
            System.out.println("Server listen on: " + listenServer.getAddress());
            listenServer.createContext("/connect", new listenHandler());
            listenServer.start();
            System.out.println("Client HTTP server is running");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class listenHandler implements HttpHandler {
        public static float currentProgress;
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            OutputStream os = exchange.getResponseBody();
            String myreply = "REQ_TORRENT_DL";
            exchange.sendResponseHeaders(200, myreply.length());
            os.write(myreply.getBytes());
            boolean waitForSize = false;
            int rcvfile = 0;
            //wait for server response with while loop
            while (true){
                InputStream is = exchange.getRequestBody();
                String st = "";
                byte[] mybuf = new byte[4096];
                is.read(mybuf);
                st = new String(mybuf);
                if(st.equals("SEND_VALIDATION")){
                    String sendValidation = "THIS_IS_LIFE";
                    exchange.sendResponseHeaders(200, sendValidation.length());
                    os.write(sendValidation.getBytes());
                }
                if(st.equals("SEND_TORRENT")){
                    waitForSize = true;
                }
                if(isInteger(st) && waitForSize){
                    rcvfile = Integer.parseInt(st);
                    break;
                }

            }
            //start a new loop to rcv file
                InputStream is = exchange.getRequestBody();
                byte[] mybuf = new byte[8192];
                FileOutputStream fos = new FileOutputStream(new File("cl_distro.torrent"));
                int total_rcv = 0;
                int rcv = 0;
                while (total_rcv <= rcvfile) {
                    rcv = is.read();
                    fos.write(mybuf, total_rcv, rcv);
                    total_rcv += rcv;
                }
            //finish receiving file

            try {
                Client client = new Client(new InetSocketAddress(5555).getAddress(),
                        SharedTorrent.fromFile(new File("cl_distro.torrent"), new File("some_huge_file.xyz")));
                client.download();
                client.addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        Client client1 = (Client) o;
                        float progress = client.getTorrent().getCompletion();
                        //need to send progress back to master
                        System.out.println("Current progress: " + progress);
                        currentProgress = progress;
                    }
                });
                //keep sending progress info back to server
                while (currentProgress < 100){
                    os.write(Float.toString(currentProgress).getBytes());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        public static boolean isInteger(String s) {
            try {
                Integer.parseInt(s);
            } catch(NumberFormatException e) {
                return false;
            } catch(NullPointerException e) {
                return false;
            }
            // only got here if we didn't return false
            return true;
        }

    }
}
