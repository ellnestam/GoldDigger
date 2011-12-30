package com.agical.golddigger.model.fieldcreator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Reader {

    public static String read(URL url) {
        try {
            InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String result = "";
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) result += tmp + "\n";
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
