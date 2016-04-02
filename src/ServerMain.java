import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerMain {
    public ServerMain(String[] clientLst){
        //establish connection
        for(String st: clientLst){
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(st).openConnection();
                System.out.println(st +" "+ con.getResponseMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class clientThread implements Runnable{
        private String conCli;
        private HttpURLConnection HTTPCon;
        public clientThread(String client, HttpURLConnection hcon){
            conCli = client;
            HTTPCon = hcon;
        }
        @Override
        public void run(){

        }

    }
}
