package com.docker.common.util;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParamUtils {


    public static Map objectToMap(Object obj, boolean keepNullVal) {
        if (obj == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal) {
                    map.put(field.getName(), (String) field.get(obj));
                } else {
//                    if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
//                    if (!TextUtils.isEmpty(field.getName()) && !TextUtils.isEmpty(field.get(obj).toString())) {
                    map.put(field.getName(), (String) field.get(obj));
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Map obj2map(Object o) {
        Gson gson = new Gson();
        String ss = gson.toJson(o);
        LinkedTreeMap<String, String> hashMap = new LinkedTreeMap<>();
        hashMap = gson.fromJson(ss, new TypeToken<Map<String, String>>() {
        }.getType());
        return removeMapEmptyValue(hashMap);
    }


    public static Map<String, String> removeMapEmptyValue(Map<String, String> paramMap) {
        Set<String> set = paramMap.keySet();
        Iterator<String> it = set.iterator();
        List<String> listKey = new ArrayList<String>();
        while (it.hasNext()) {
            String str = it.next();
            if (paramMap.get(str) == null || "".equals(paramMap.get(str))) {
                listKey.add(str);
            }
        }
        for (String key : listKey) {
            paramMap.remove(key);
        }
        return paramMap;
    }


    public static Map objectToMapRest(Object obj, boolean keepNullVal) {
        if (obj == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    map.put((String) field.get(obj), field.getName());
                } else {
                    if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
                        map.put((String) field.get(obj), field.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Object mapToObject(Map<String, String> map, Object obj, boolean keepNullVal) {
        if (map == null || map.size() == 0) {
            return null;
        }
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    field.set(obj, map.get(field.getName()));
                } else {
                    if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                        field.set(obj, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


}
