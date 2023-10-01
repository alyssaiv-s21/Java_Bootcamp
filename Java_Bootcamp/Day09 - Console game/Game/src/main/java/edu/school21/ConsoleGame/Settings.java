package edu.school21.ConsoleGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    private Map<String, String> settings = new HashMap<String, String>();
    private String filePath;

    public Settings(GameMode mode) {
        if(mode == GameMode.DEVELOPMENT) {
            filePath = "target/classes/application-dev.properties";
        } else if (mode == GameMode.PRODUCTION) {
            filePath = "target/classes/application-production.properties";
        } else if (mode == GameMode.HIDDEN) {
            filePath = "target/classes/application-hidden.properties";
        }
    }

    private void MapLoading() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while (line != null) {
            String[] words = line.split("=");
            if(words.length == 2) {
                settings.put(words[0], words[1]);
            } else if (words.length == 1) {
                settings.put(words[0], " ");
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public Map<String, String> getSettings() throws IOException{
        MapLoading();
        return settings;
    }

}
