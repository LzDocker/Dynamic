package com.docker.common.util;


import android.text.TextUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.docker.common.vo.SearchVo;
import com.docker.common.vo.UserInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
sp 缓存
 * */
public class CacheUtils {


    public static void savePrivateFlag() {
        SPUtils.getInstance("PrivateFlag").put("flag", true);
    }

    public static boolean getPrivateFlag() {
        return SPUtils.getInstance("PrivateFlag").getBoolean("flag", false);
    }

    public static void saveUser(UserInfoVo userInfoVo) {
        String json = GsonUtils.toJson(userInfoVo);
        SPUtils.getInstance("safe_user").put("uservo", json);
    }


    public static UserInfoVo getUser() {
        UserInfoVo userInfoVo = null;
        if (!TextUtils.isEmpty(SPUtils.getInstance("safe_user").getString("uservo"))) {
            userInfoVo = GsonUtils.fromJson(SPUtils.getInstance("safe_user").getString("uservo"), UserInfoVo.class);
        }
        return userInfoVo;
    }

    public static void clearUser() {
        SPUtils.getInstance("safe_user").clear();
    }


    public static void saveSerachChache(String t, String keyword) {
        SearchVo searchVo;
        if (!TextUtils.isEmpty(SPUtils.getInstance("searchVo").getString("searchVo"))) {
            searchVo = GsonUtils.fromJson(SPUtils.getInstance("searchVo").getString("searchVo"), SearchVo.class);
            if (searchVo != null) {
                if (searchVo.getResource().get(t) != null) {
                    if (!searchVo.getResource().get(t).contains(keyword)) {
                        searchVo.getResource().get(t).add(keyword);
                    }
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(keyword);
                    searchVo.getResource().put(t, arrayList);
                }
            } else {
                searchVo = new SearchVo();
                ArrayList arrayList = new ArrayList();
                arrayList.add(keyword);
                searchVo.getResource().put(t, arrayList);
            }
        } else {
            searchVo = new SearchVo();
            ArrayList arrayList = new ArrayList();
            arrayList.add(keyword);
            searchVo.getResource().put(t, arrayList);
        }
        String json = GsonUtils.toJson(searchVo);
        SPUtils.getInstance("searchVo").put("searchVo", json);
    }

    public static List<String> getSerachChache(String t) {
        ArrayList<String> list = new ArrayList<>();
        SearchVo searchVo = null;
        if (!TextUtils.isEmpty(SPUtils.getInstance("searchVo").getString("searchVo"))) {
            String string = SPUtils.getInstance("searchVo").getString("searchVo");
            searchVo = GsonUtils.fromJson(string, SearchVo.class);
            if (searchVo != null && searchVo.getResource() != null && searchVo.getResource().get(t) != null) {
                list = (ArrayList<String>) searchVo.getResource().get(t);
            }
        }
        return list;
    }

    public static void delSerachCache(String t, String keyworld) {
        SearchVo searchVo = null;
        if (!TextUtils.isEmpty(SPUtils.getInstance("searchVo").getString("searchVo"))) {
            searchVo = GsonUtils.fromJson(SPUtils.getInstance("searchVo").getString("searchVo"), SearchVo.class);
            if (searchVo != null && searchVo.getResource() != null && searchVo.getResource().get(t) != null) {
                searchVo.getResource().get(t).remove(keyworld);
                String json = GsonUtils.toJson(searchVo);
                SPUtils.getInstance("searchVo").put("searchVo", json);
            }
        }
    }

    public static void ClearSerachCache() {
        SPUtils.getInstance("searchVo").clear();
    }

    public static void saveJpAlias(String alias) {
        SPUtils.getInstance("jp_alias").put("jp_alias", alias);
    }

    public static String getJpAlias() {
        if (!TextUtils.isEmpty(SPUtils.getInstance("jp_alias").getString("jp_alias"))) {
            return "";
        } else {
            return SPUtils.getInstance("jp_alias").getString("jp_alias");
        }
    }


    public static void saveLaut(String lat, String lng) {
        SPUtils.getInstance("Laut").put("lat", lat);
        SPUtils.getInstance("Laut").put("lng", lng);
    }

    public static String[] getLaut() {

        String lat = SPUtils.getInstance("Laut").getString("lat");
        String lng = SPUtils.getInstance("Laut").getString("lng");
        if (TextUtils.isEmpty(lat)) {
            return null;
        }
        return new String[]{lat, lng};
    }

    public static void saveCommentLike(String commentid, int status) {
        SPUtils.getInstance("comment").put(commentid, status);
    }

    public static int getCommentLike(String commtid) {
        return SPUtils.getInstance("comment").getInt(commtid);
    }

    public static void saveWelcomeFlag() {
        SPUtils.getInstance("WelcomeFlag").put("flag", true);
    }

    public static boolean getWelcomeFlag() {
        return SPUtils.getInstance("WelcomeFlag").getBoolean("flag", false);
    }


    public static String getudid() {
        if (TextUtils.isEmpty(SPUtils.getInstance("udid").getString("udid"))) {
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            String uuid2 = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            SPUtils.getInstance("udid").put("udid", uuid + uuid2);
            return uuid + uuid2;
        } else {
            return SPUtils.getInstance("udid").getString("udid");
        }
    }

    public static void clearudid(String udid) {
        SPUtils.getInstance("udid").clear();
    }


    public static void SaveTestPageConfig(String pagestrs) {
        SPUtils.getInstance("pageoptions").put("test", pagestrs);
    }


    public static String GetTestPageConfig() {
      return  SPUtils.getInstance("pageoptions").getString("test");
    }


}
