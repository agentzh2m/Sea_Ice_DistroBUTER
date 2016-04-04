//import FileServer.FileClient;
//import Master.ServerModel;

import All.ServerModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerTest {
    public static void main(String[] args) {
        List<String> client = new ArrayList<>();
        client.add("192.168.0.13");
        ServerModel sm = new ServerModel(client, new File("README.md"));
    }
}
