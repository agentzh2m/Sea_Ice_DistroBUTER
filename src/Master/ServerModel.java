package Master;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedPeer;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel {
    public static File myTorrentFile = new File("distro.torrent");
    Map<String, TrackedPeer> trackedClient;
    public ServerModel(List<String> clientLst, File distrofile){
        InetSocketAddress torrentCon = null;
        try {
            torrentCon = new InetSocketAddress(InetAddress.getLocalHost(), 4444);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("This server inet adr is " + torrentCon.getAddress());
       //start tracker and make torrent file
            try {
                Tracker tracker = new Tracker(torrentCon);
                Torrent torrent = Torrent.create(distrofile, tracker.getAnnounceUrl().toURI(), "IceSea_DISTRO");
                FileOutputStream fos = new FileOutputStream(myTorrentFile);
                torrent.save(fos);
                fos.close();
                tracker.announce(TrackedTorrent.load(myTorrentFile));
                tracker.start();
                Client client = new Client(new InetSocketAddress(1111).getAddress(),
                        SharedTorrent.fromFile(myTorrentFile, new File(".")));
                client.share();
                System.out.println("Tracker running with URL: " + tracker.getAnnounceUrl());
                List<TrackedTorrent> lsttor = new ArrayList<>();
                trackedClient = new HashMap<>();
                //new Thread(new serverThread()).start();
                //start HTTP server to send torrent file
                HttpServer listenServer = HttpServer.create();
                listenServer.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888), 0);
                System.out.println("Server listen on: " + listenServer.getAddress());
                listenServer.createContext("/downloadTorrent", new getTorrentHandler());
                listenServer.start();
                lsttor.addAll(tracker.getTrackedTorrents());
                //iterating over track torrent
                for(TrackedTorrent tor: lsttor){
                    trackedClient.putAll(tor.getPeers());
                }
                System.out.println("Current client state: " + client.getState());

            } catch (Exception e) {
                e.printStackTrace();
        }

        //establish connection
        ExecutorService pool = Executors.newCachedThreadPool();
        for(String st: clientLst){
            pool.submit(new clientThread(st));
        }
    }


    public class clientThread implements Runnable{
        private String conCli;
        public clientThread(String client){
            conCli = client;
        }
        @Override
        public void run(){
            while (true){
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL("http://"+conCli+":8000/connect").openConnection();
                    if(con.getResponseCode() == 200){
                        System.out.println("Starting Connecion from client: " + conCli);
                        InputStreamReader isr = new InputStreamReader(con.getInputStream());
                        BufferedReader br = new BufferedReader(isr);
                        System.out.println(br.readLine());
                        con.disconnect();
                    }else {
                        System.out.println("Cannot establish HTTP connection with client " + conCli);
                        break;
                    }
                    //trigger client to start downloading torrent
                    HttpURLConnection conTor = (HttpURLConnection) new URL("http://"+conCli+":8000/getTorrent").openConnection();
                    System.out.println("establishing connection to allow user to send torrent file");
                    if (conTor.getResponseCode() == 200){
                        System.out.println("Client get Torrent file");
                        InputStreamReader isr = new InputStreamReader(conTor.getInputStream());
                        BufferedReader br = new BufferedReader(isr);
                        String msg = null;
                        while ((msg = br.readLine()) != null){
                            System.out.println(msg);
                        }
                    }
                    System.out.println("Sending command to start torrent");
                    HttpURLConnection conSTor = (HttpURLConnection) new URL("http://"+conCli+":8000/startTorrent").openConnection();
                    if(conSTor.getResponseCode() == 200){
                        InputStreamReader isr = new InputStreamReader(conSTor.getInputStream());
                        BufferedReader br = new BufferedReader(isr);
                        System.out.println(br.readLine());
                    }else {
                        System.out.println("Fail to start torrent");
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    static class getTorrentHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange){
            byte[] buf = new byte[(int)myTorrentFile.length()];
            try {
                FileInputStream fis = new FileInputStream(myTorrentFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                System.out.println("byte sent: " + bis.read(buf, 0, buf.length));

                exchange.sendResponseHeaders(200, myTorrentFile.length());
                OutputStream os = exchange.getResponseBody();
                os.write(buf, 0, buf.length);
                os.close();
                System.out.println("Finish sending the file");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
