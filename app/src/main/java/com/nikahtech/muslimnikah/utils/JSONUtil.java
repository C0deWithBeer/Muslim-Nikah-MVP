package com.nikahtech.muslimnikah.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class JSONUtil {
    private static final Gson gson;
    private static final Gson gsonPretty;

    static {
        GsonBuilder builder = new GsonBuilder();

        // Register LocalDate adapter
        builder.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-MM-dd"
            }
        });

        builder.registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        });

        // LocalDateTime adapter
        builder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null) return null;
                return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-MM-dd'T'HH:mm:ss"
            }
        });

        builder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json == null || json.isJsonNull()) return null;

                try {
                    if (json.isJsonPrimitive()) {
                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } else {
                        // If cached JSON somehow became JsonObject (corrupted/old version), fallback to null
                        return null;
                    }
                } catch (Exception e) {
                    throw new JsonParseException("Failed to parse LocalDateTime: " + json.toString(), e);
                }
            }
        });

        gson = builder.create();
        gsonPretty = builder.setPrettyPrinting().create();
    }

    // Convert POJO or Map to JSON string
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // Convert JSON string to a POJO class
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    // Convert JSON string to generic Map<String, Object>
    public static Map<String, Object> fromJsonToMap(String jsonString) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }

    public static String toPrettyJson(Object obj) {
        return gsonPretty.toJson(obj);
    }

    // 🔹 Read a raw resource JSON file as String
    public static String readRawResource(Context context, int rawResId) {
        try {
            InputStream is = context.getResources().openRawResource(rawResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔹 Get List<String> from a specific attribute in JSON file (like "caste", "languages")
    public static List<String> getStringListFromRaw(Context context, int rawResId, String attributeName) {
        String jsonString = readRawResource(context, rawResId);
        if (jsonString == null) return null;

        Map<String, Object> jsonMap = fromJsonToMap(jsonString);

        if (!jsonMap.containsKey(attributeName)) return null;

        String attributeJson = gson.toJson(jsonMap.get(attributeName));
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(attributeJson, listType);
    }

    public static List<String> getStringDataArrayFromRaw(Context context, int rawResId, String attributeName) {
        String jsonString = readRawResource(context, rawResId);
        if (jsonString == null) return null;

        Map<String, Object> jsonMap = fromJsonToMap(jsonString);

        if (!jsonMap.containsKey(attributeName)) return null;

        String dataJson = gson.toJson(jsonMap.get(attributeName));
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(dataJson, listType);
    }

}
