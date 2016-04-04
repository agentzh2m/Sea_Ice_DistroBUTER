package All;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Ice on 2/4/2559.
 */
public class FindIP implements Runnable{
    private Thread t;
    private String threadName;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    StringBuilder sb3 = new StringBuilder();



//    FindIP(String name){
//        threadName = name;
//        System.out.println("Creating: " + threadName);
//    }

    @Override
    public void run() {
//        System.out.println("run");
        ServerUI.all_ip_list = new ArrayList<>();
        String cmd_inst = null;

    /* all_instance = all instance names resulting from dns-sd -B commands */
//            String all_instance = sb.toString();
//        System.out.println(all_instance);
    /* use all instance name to get computer name according to each instance name */
        try {
//            System.out.println("try");
            String br_inst = ServerUI.outputStream.toString();
            System.out.println(br_inst);
            String[] sp = br_inst.split("\n");
            for (String st : sp) {
                System.out.println(st);

                if (st.contains("local")) {
//                    System.out.println("Enter if");

                    String[] inst_parts = st.split(Pattern.quote("."));
//                    System.out.println(inst_parts[4].trim());
//                    System.out.println(st);
                    cmd_inst = "dns-sd -L " + inst_parts[4].trim() + " .";
//                    System.out.println(cmd_inst);
                    String cmd_test = "dns-sd -L Ice .";

                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd_inst);
                    builder.redirectErrorStream(true);
                    Process p = builder.start();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    String line;
                    for (line = r.readLine().trim(); line != null || !line.equals(""); line = r.readLine().trim()) {
                    System.out.println(line);
//                    line = r.readLine();
                        if (line.contains("html")) {
                            break;
                        }

                        if (line.contains("reached")) {
//                        System.out.println("enter if");
                            String[] parts = line.split(Pattern.quote(" "));
//                        System.out.println(parts[7]);
                            String[] host_frac = parts[7].split((Pattern.quote(".")));
//                        System.out.println(host_frac[0] + "." + host_frac[1]);
                            String host = host_frac[0] + "." + host_frac[1];
//                            System.out.println(host);
                            sb2.append(host);
                            sb2.append(System.lineSeparator());
                        }


                    }
                }


            }


            String host_name = sb2.toString();
//            System.out.println(host_name);

            try {
                BufferedReader br_host = new BufferedReader(new StringReader(host_name));

                for (String host_line = br_host.readLine(); host_line != null; host_line = br_host.readLine()) {
//                    System.out.println(host_line);

                    String cmd_host = "dns-sd -G v4 " + host_line;
                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd_host);
                    builder.redirectErrorStream(true);
                    Process p = builder.start();
                    BufferedReader host_br = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    String ip_line;
                    for (ip_line = host_br.readLine().trim(); ip_line.length() != 0; ip_line = host_br.readLine().trim()) {
//                        System.out.println(ip_line);
//                        if (line.contains("local")) {
//                            break;
//                        }

                        if (ip_line.contains("local")) {
//                            System.out.println(ip_line);
                            String[] host_parts = ip_line.split(Pattern.quote("."));
//                            System.out.println(host_parts[3]);
//                            System.out.println(host_parts[6]);
                            String[] last_parts = host_parts[6].split(Pattern.quote(" "));
//                            System.out.println(last_parts[0]);
                            String ip = host_parts[3].trim() + "." + host_parts[4].trim() + "." + host_parts[5].trim() + "." + last_parts[0].trim();
                            System.out.println(ip);
//                            sb3.append(ip);
//                            sb3.append(System.lineSeparator());
                            ServerUI.all_ip_list.add(ip);
//                            System.out.println(TestThread.all_ip_list);

//                            System.out.println(TestThread.all_ip_list);

                            break;
//                        System.out.println(host_parts[1]);
//                        System.out.println(host_parts[3]);
                        }


                    }


                }
            } catch (Exception ex) {

            }

//            String all_ip = sb3.toString();
//            System.out.println(all_ip);
//            System.out.println(list);
            System.out.println(ServerUI.all_ip_list);

        } catch (Exception ex) {

        }

    }

//        public void start () {
//            System.out.println("Starting: " + threadName);
//            if (t == null) {
//                t = new Thread(this, threadName);
//                t.start();
//            }
//        }

    }
