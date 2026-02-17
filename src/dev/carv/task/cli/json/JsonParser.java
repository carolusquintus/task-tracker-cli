package dev.carv.task.cli.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

// Much of the code implemented in this class is based on the following post
// https://vajithc.medium.com/parsing-json-without-libraries-build-your-own-json-reader-in-java-1db8e6165039

public final class JsonParser {

    private static final JsonParser INSTANCE = new JsonParser();

    private JsonParser() {}

    public static JsonParser getInstance() {
        return INSTANCE;
    }

    public String readJson(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            System.err.println("Error while trying to read json file: " + path);
        }
        return null;
   }

   public Object parseJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) {

        }
        if (json.startsWith("[")) {

        }
   }

   public Map<String, Object> parseJsonObject(String json) {
        var map = new LinkedHashMap<String, Object>();
        json = json.substring(1, json.length() - 1).trim();

   }

   private String[] splitJsonEntries(String json) {
        var entries = new ArrayList<String>();


   }

}
