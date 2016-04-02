package FileServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sea on 4/1/16.
 */

public class FileServer {

    public void SendFile(String filename, String dest) throws IOException{
        ServerSocket serv_sock;
        Socket sock;
        FileInputStream fis;
        BufferedInputStream bis;
        OutputStream os;
        int client_port = 5000;

        try{

            // create connection
//            serv_sock = new ServerSocket(serv_port);
//            sock = serv_sock.accept();
            sock = new Socket(dest, client_port);
            System.out.println("Connecting..");

            // send file
            File file = new File(filename);
            byte[] file_byte = new byte[filename.length()];
            fis = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            bis.read(file_byte, 0, file_byte.length);
            os = sock.getOutputStream();
            System.out.println("Sending");
            os.write(file_byte, 0, file_byte.length);
            os.flush();

            bis.close();
            os.close();
            sock.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
