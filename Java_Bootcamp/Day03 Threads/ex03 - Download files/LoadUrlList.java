package ex03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class LoadUrlList {
    private static final String FILE_NAME = "ex03/files_urls.txt";

    public static LinkedList<String> ReadFile() throws IOException {
        LinkedList<String> result = new LinkedList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line = reader.readLine();
        while (line != null) {
            result.add(line);
            line = reader.readLine();
        }
        reader.close();
        return result;
    }
}