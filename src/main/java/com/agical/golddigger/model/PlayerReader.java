package com.agical.golddigger.model;

import com.agical.golddigger.model.fieldcreator.Reader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerReader {
    public static Map<String, String> read(String fileName) throws IOException {
        URL resource = PlayerReader.class.getResource(fileName);
        String fileContent = Reader.read(resource);
        return parseString(fileContent);
    }

    private static Map<String, String> parseString(String fileContent) {
        Map<String, String> players = new HashMap<String, String>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (!line.startsWith("#")) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String userName = parts[0].trim();
                    String password = parts[1].trim();
                    players.put(userName, password);
                }
            }
        }

        return players;
    }
}
