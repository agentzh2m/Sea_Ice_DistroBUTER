package All;
import java.io.*;
import java.nio.file.Files;

import junit.framework.Test;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.omg.CORBA.Environment;

import java.nio.file.Path;

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
//        File file = new File("./src/All/raw_ip.txt");
//        System.out.println(file.exists());
//        try {
//            if (file.exists() == true) {
//                Files.delete(file.toPath());
//                file.delete();
////                System.out.println(file.delete());
////                System.out.println(file.exists());
////                System.out.println("File deleted");
//            } else {
//                System.out.println("Cannot delete");
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }


//        while(true){
            System.out.println("Running: " + threadName);


//            try {


//                ProcessBuilder builder = new ProcessBuilder(
//                        "cmd.exe", "/c", "dns-sd -B _http._tcp >> raw_ip.txt");
//            ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "cd \"C:\\Program Files\\Microsoft SQL Server\" && dir");
//                ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "cd ./src/All/ && dns-sd -B _http._tcp > raw_ip.txt");
//                builder.redirectErrorStream(true);
//                Process p = builder.start();
//                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String line;
//
//                while (true) {
//                    line = r.readLine();
////                    System.out.println(line);
//                    if (!line.contains("local")) {
//                        break;
//                    }
//                    System.out.println(line);
//                }

        try {
            if (ServerUI.outputStream == null) {
                ServerUI.outputStream = new ByteArrayOutputStream();

            }
//                String line = "dns-sd -B _http._tcp";
//                CommandLine cmdLine = CommandLine.parse(line);
            CommandLine cmdLine = new CommandLine("dns-sd");
            cmdLine.addArgument("-B");
            cmdLine.addArgument("_http._tcp");
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);

            ExecuteWatchdog watchdog = new ExecuteWatchdog(2000);
            executor.setWatchdog(watchdog);
            PumpStreamHandler streamHandler = new PumpStreamHandler(ServerUI.outputStream);
            executor.setStreamHandler(streamHandler);

            int exitValue = executor.execute(cmdLine, EnvironmentUtils.getProcEnvironment());

//            System.out.println(TestThread.outputStream.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
//            Runtime.getRuntime().exec("dns-sd -B");
//            }catch (IOException e){
//                System.out.println("Thread " + threadName + "interrupted");
//            }


//        System.out.println(threadName + " exiting");
//        }


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