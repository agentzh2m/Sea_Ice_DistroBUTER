package sample;

import java.util.ArrayList;

/**
 * Created by Ice on 1/4/2559.
 */
public class TestThread {
    static ArrayList<String> all_ip_list;
    public static void main(String[] args) throws InterruptedException {

        // Browse for bonjour service
        ScanService discover = new ScanService("Thread 1");
        Thread D1 = new Thread(discover);
        D1.start();
        Thread.sleep(500);
        D1.interrupt();

        // Find all ip
        FindIP name = new FindIP("Thread 2");
        Thread D2 = new Thread(name);
        D2.start();
        D2.sleep(500);
        D2.interrupt();

        System.out.println(all_ip_list);

    }
}
