package Master;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel {
    public ServerModel(String[] clientLst){
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
                    while (true){
                        //need to implement
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
