package FileServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sea on 4/2/16.
 *
 */

public class FileClient {

    public void ReceiveFile(String filename){
        ServerSocket serv_sock;
        Socket sock;
        FileOutputStream fos;
        BufferedOutputStream bos;
        InputStream is;
        int port = 5000;

        int bytesRead;
        int current = 0;
        int file_size = Integer.MAX_VALUE;

        try{
            serv_sock = new ServerSocket(port);
            sock = serv_sock.accept();

            byte[] file_byte = new byte[file_size];
            is = sock.getInputStream();
            fos = new FileOutputStream(filename);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(file_byte,0,file_byte.length);
            current = bytesRead;

            do{
                bytesRead = is.read(file_byte, current, file_byte.length - current);
                if (bytesRead >= 0 ){
                    current += bytesRead;
                }
            } while(bytesRead > -1);

            bos.write(file_byte,0,current);
            bos.flush();

            fos.close();
            bos.close();
            sock.close();


        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
}
