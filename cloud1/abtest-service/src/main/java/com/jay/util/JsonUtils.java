package com.jay.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;


/**
 * Created by Jay He on 2014/5/21.
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 默认的日期格式: yyyy-MM-dd HH:mm:ss
     */
    public static String getJson(Object object) {
        String result = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(object);
        return result;
    }

    /**
     * 默认的日期格式: yyyy-MM-dd'T'HH:mm:ss.SSS+08:00
     */
    public static <T> T fromJson(String json, Class<T> classOfT, String... dateFormat) throws JsonSyntaxException {
        String format = dateFormat.length > 0 ? dateFormat[0] : "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00";
        return new GsonBuilder().setPrettyPrinting().setDateFormat(format).create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT, String dateFormat) throws JsonSyntaxException {
        return new GsonBuilder().setPrettyPrinting().setDateFormat(dateFormat).create().fromJson(json, typeOfT);
    }

    public static <J> J toObject(String json, Class<J> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <J> J toObject(String json, TypeReference<J> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
