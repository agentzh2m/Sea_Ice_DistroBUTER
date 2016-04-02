import FileServer.FileClient;
import Master.ServerModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WhiteBOX {
    public static void main(String[] args) {
        List<String> client = new ArrayList<>();
        client.add("http://192.168.0.13:8000/connect");
        ServerModel sm = new ServerModel(client, new File("README.md"));
    }
}
