package All;

import All.FindIP;
import All.ScanService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
//import sun.tools.jar.CommandLine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Ice on 1/4/2559.
 */
public class TestThread {
    static ArrayList<String> all_ip_list;
    static ByteArrayOutputStream outputStream = null;
    public static void main(String[] args) throws InterruptedException {

        // Browse for bonjour service
        ScanService discover = new ScanService("Thread 1");
        discover.run();
//        System.out.println(outputStream);
//        Thread D1 = new Thread(discover);
//        D1.setDaemon(true);
//        D1.start();
//        Thread.sleep(500);
//        D1.interrupt();



        // Find all ip
        FindIP name = new FindIP();
        name.run();
//        Thread D2 = new Thread(name);
//        D2.start();
//        D2.join();
//        D2.interrupt();

//        System.out.println(all_ip_list);

    }
}
