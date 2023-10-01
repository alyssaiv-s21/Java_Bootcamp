package ex03;

import java.io.IOException;
import java.util.LinkedList;

public class Program {
    public static LinkedList<String> urlList;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("You should specify count number '--threadsCount='");
            System.exit(1);
        }
        int threadsCount = Integer.parseInt(args[0].substring(15));

        try {
            urlList = LoadUrlList.ReadFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        for (int i = 0; i < threadsCount; ++i) {
            LoadingThread newThread = new LoadingThread(urlList);
            newThread.start();
        }

    }

}