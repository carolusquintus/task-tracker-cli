package dev.carv.task.cli.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
            System.err.printf("Error while trying to read json file: %s", path);
        }
        return null;
   }

   public Object parseJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseJsonObject(json);
        }
        if (json.startsWith("[")) {
            return parseJsonArray(json);
        }
        if (json.startsWith("\"") && json.endsWith("\"")) {
            return json.substring(1, json.length() - 1);
        }
        if (json.equals("true") || json.equals("false")) {
            return Boolean.parseBoolean(json);
        }
        if (json.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
            if (json.contains(".") || json.contains("e") || json.contains("E")) {
                return Double.parseDouble(json);
            } else {
                return Long.parseLong(json);
            }
        }
        if (json.equals("null")) {
            return null;
        }
        throw new IllegalArgumentException("Invalid JSON value: %s".formatted(json));
   }

   public Map<String, Object> parseJsonObject(String json) {
        var map = new LinkedHashMap<String, Object>();
        json = json.substring(1, json.length() - 1).trim();
        var entries = splitJsonEntries(json);
        for (String entry : entries) {
            var keyValue = entry.split(":", 2);
            var key = (String) parseJson(keyValue[0]);
            var value = parseJson(keyValue[1]);
            map.put(key, value);
        }
        return map;
   }

   public List<Object> parseJsonArray(String json) {
        var list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim();
        var entries = splitJsonEntries(json);
        for (String entry : entries) {
            list.add(parseJson(entry));
        }
        return list;
   }

   private String[] splitJsonEntries(String json) {
        var entries = new ArrayList<String>();
        int bracketCount = 0;
        int braceCount = 0;
        boolean inQuotes = false;
        var entry = new StringBuilder();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                }
            }

            if (c == ',' && !inQuotes && bracketCount == 0 && braceCount == 0) {
                entries.add(entry.toString().trim());
                entry.setLength(0);
            } else {
                entry.append(c);
            }
        }

        if (!entry.isEmpty()) {
           entries.add(entry.toString().trim());
        }

        return entries.toArray(new String[0]);
   }

    public String printJson(Object json) {
        var builder = new StringBuilder();
        if (json instanceof Map<?, ?> map) {
            builder.append("{");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                builder.append("\"").append(entry.getKey()).append("\":").append(printJson(entry.getValue()));
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("}");
        } else if (json instanceof List<?> list) {
            if (list.isEmpty()) {
                builder.append("[]");
            } else {
                builder.append("[");
                for (Object item : list) {
                    builder.append(printJson(item)).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append("]");
            }
        } else if (json == null) {
            builder.append("null").append(",");
        } else if (json instanceof String str) {
            builder.append("\"").append(str).append("\"").append(",");
        } else if (json instanceof Boolean || json instanceof Number) {
            builder.append(json).append(",");
        } else {
            builder.append("\"").append(json).append("\"").append(",");
        }
        return builder.toString();
    }
}
