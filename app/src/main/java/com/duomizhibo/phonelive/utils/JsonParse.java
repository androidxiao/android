package com.duomizhibo.phonelive.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Json解析工具类
 * Created by wei on 2018/5/29.
 */
public class JsonParse {

    private static final Gson gson;

    private JsonParse() {}

    static {
         gson = new Gson();
    }

    /**
     * Json解析
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        T t = gson.fromJson(json, clazz);
        return t;
    }

    /**
     * Json按类型解析
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String json, Type type) {
        T t = gson.fromJson(json, type);
        return t;
    }

    /**
     * 转化为json字符串便于传递和储存
     * @param src
     * @return
     */
    public static String obj2Json(Object src) {
       return  gson.toJson(src);
    }

}
