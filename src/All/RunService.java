package All;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Ice on 4/4/2559.
 */
public class RunService implements Runnable {
            static String name;
    public void run(){
        try {
            StringBuilder sb = new StringBuilder();

            String cmd = "dns-sd -R " + name + " _http._tcp . 80 path=path-to-page.html";
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", cmd);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            for (line = r.readLine(); line != null; line = r.readLine()) {
                System.out.println(line);
            }


        }catch (Exception ex){

        }
    }

}
