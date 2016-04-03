package Discovery;
import java.io.*;
/**
 * Created by Ice on 1/4/2559.
 */
public class ScanService implements Runnable{
    private Thread t;
    private String threadName;

    ScanService(String name){
        threadName = name;
        System.out.println("Creating: " + threadName);
    }

    public void run(){

//        File here = new File(".");
//        System.out.println(here.getAbsolutePath());
        File file = new File("./src/Discovery/raw_ip.txt");
//        System.out.println(file.exists());
        if (file.exists() == true) {
            file.delete();
//            System.out.println(file.exists());
            System.out.println("File deleted");
        } else{
            System.out.println("Cannot delete");
        }

        while(true){
            System.out.println("Running: " + threadName);


            try {


//                ProcessBuilder builder = new ProcessBuilder(
//                        "cmd.exe", "/c", "dns-sd -B _http._tcp >> raw_ip.txt");
//            ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "cd \"C:\\Program Files\\Microsoft SQL Server\" && dir");
                ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd ./src/Discovery/ && dns-sd -B _http._tcp >> raw_ip.txt");
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while (true) {
                    line = r.readLine();
                    if (!line.contains("local")) {
                        break;
                    }
//                    System.out.println(line);
                }
//            Runtime.getRuntime().exec("dns-sd -B");
            }catch (IOException e){
                System.out.println("Thread " + threadName + "interrupted");
            }

            try {
                Thread.sleep(1000);
            }catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
            System.out.println(threadName + " exiting");
        }


    }

    public void start(){
        System.out.println("Starting: " + threadName);
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

}