package Master;

import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel {
    public File myTorrentFile = new File("distro.torrent");
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
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(conCli).openConnection();
                if(con.getResponseCode() >= 200 && con.getResponseCode() < 300){
                    System.out.println("Starting Connecion from client: " + conCli);
                    while (true){
                        if(con.getResponseMessage().equals("REQ_TORRENT_DL")){
                            //check login validation
                            String validation = "SEND_VALIDATION";
                            OutputStream cos = con.getOutputStream();
                            cos.write(validation.getBytes());
                            if(con.getResponseMessage().equals("THIS_IS_LIFE")){
                                System.out.println("Stupid Validation OK");
                                //send torrent file
                                cos.write("SEND_TORRENT".getBytes());
                                int sent = 0;
                                int total_sent = 0;
                                FileInputStream tfos = new FileInputStream(myTorrentFile);
                                byte[] torbuffer = new byte[8192];
                                cos.write(Long.toString(myTorrentFile.length()).getBytes());
                                while (sent <= myTorrentFile.length()){
                                    sent=tfos.read(torbuffer);
                                    cos.write(torbuffer, total_sent, sent);
                                    total_sent+=sent;
                                }
                                System.out.println("Send file completed");
                                //keep waiting untill everyone finish then close the tracker

                            }else {
                                System.out.println("Validation Fail");
                                break;
                            }
                        }
                    }
                }else {
                    System.out.println("Cannot establish HTTP connection with client " + conCli);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
