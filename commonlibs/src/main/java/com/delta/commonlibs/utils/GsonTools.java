package com.delta.commonlibs.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GsonTools {

    public GsonTools() {
    }

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    /**
     * map集合添加到json数组
     *
     * @param datas
     * @param <T>
     * @return
     */
    public static <T extends Object> String createGsonListString(Map<String, T> datas) {
        List<Map<String, T>> list = new ArrayList<>();
        list.add(datas);
        return createGsonString(list);
    }

    public static < T extends Object>String createGsonWithListString(String key, List<T> value) {
        Map<String, List<T>> datas = new HashMap<>();
        datas.put(key, value);
        return createGsonString(datas);
    }

    /**
     * 对象添加到json数组
     *
     * @param datas
     * @return
     */
    public static String createGsonListString(Object datas) {
        List<Object> list = new ArrayList<>();
        list.add(datas);
        return createGsonString(list);
    }

    public static <T extends Object> String createGsonString(String[] keys, Object[] values) {
        Map<String, Object> maps = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            maps.put(keys[i], values[i]);
        }
        return createGsonString(maps);
    }

    /**
     * 键值对的形式添加json数组
     *
     * @param keys
     * @param values
     * @return
     */
    public static String createGsonListString(String[] keys, Object[] values) {
        Map<String, Object> maps = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            maps.put(keys[i], values[i]);
        }
        return createGsonListString(maps);
    }


    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, cls);
        return t;
    }

    public static <T> ArrayList<T> changeGsonToList(String json, Class<T> cls) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjs = new Gson().fromJson(json, type);
        ArrayList<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(new Gson().fromJson(jsonObj, cls));
        }

        return listOfT;
    }

    public static <T> List<Map<String, T>> changeGsonToListMaps(
            String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * jsonArray contains json,if contains return true,otherwise return false;
     *
     * @param jsonArray
     * @param json
     * @return
     */
    public static boolean containsJson(JSONArray jsonArray, String json) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (json.equals(jsonArray.get(i).toString())) {
                    return true;
                }
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}
