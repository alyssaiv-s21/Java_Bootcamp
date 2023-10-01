package ex03;

import java.util.LinkedList;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LoadingThread extends Thread {
    private LinkedList<String> urlList;
    private static final String DOWNLOAD = "ex03/Download/";

    public LoadingThread(LinkedList<String> list) {
        urlList = list;
    }

    public void run() {
        while (urlList.size() > 0) {
            String link = GetURL();
            LoadURL(link);
        }
    }

    private String GetURL() {
        synchronized (urlList) {
            if (urlList.size() > 0) {
                return urlList.removeFirst();
            }
            return "";
        }
    }

    private void LoadURL(String link) {
        if (!link.equals("")) {
            String[] splitLink = link.split(" ");
            System.out.println(Thread.currentThread().getName()
                    + " start download file number " + splitLink[0]);
            URL myURL;
            try {
                myURL = new URL(splitLink[1]);
                try (InputStream in = myURL.openStream()) {
                    Path fileName = Paths.get(splitLink[1]).getFileName();
                    String saveTo = DOWNLOAD + fileName.toString();
                    Files.copy(in, Paths.get(saveTo), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()
                    + " finish download file number " + splitLink[0]);
        }
    }
}