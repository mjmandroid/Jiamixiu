package com.project.jiamixiu.utils;

import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.project.jiamixiu.BaseApplication;

import java.util.ArrayList;

public class SharedPreferencesUtil {
    private static final String LAT_KEY = "lat";
    private static final String LNG_KEY = "lng";
    private static final String ADDRESS_KEY = "address";
    private static final String HISTORY_MAP_SEARCH = "history_map_search";
    private static final String HISTORY_SHOP_SEARCH = "history_shop_search";
    private static final String HISTORY_PUBLISH_SEARCH = "history_publish_search";
    private static final String LOGIN_TOKEN = "login_token";
    private static final String DEVICE_ID = "device_id";
    private static final String ADMIN_ID = "admin_id";
    private static final String WPAGE_ID = "wpage_id";
    private static final String USER_ID = "user_id";
    private static final String IS_SHAKE = "isShake";
    private static final String IS_VIOCE = "isTipVoice";
    private static final String IS_FIRST_COME = "first_come";
    private static final String IS_NOTIFICATION = "is_notification";
    private static final String CUSTOMER_APP_ID = "customer_app_id";
    private static final String APP_GUO_QI = "app_guo_qi";
    private static final String LOGIN_PHONE = "login_phone";
    private static final String PING_LOGO = "ping_tai_logo";
    private static final String HOT_ONLINE = "hot_online";
    private static final String IS_SHOW_COUPON = "is_show_coupon";
    private static final String OS_TOKEN = "os_token";
    private static final String OS_SECRET = "os_secret";
    private static final String OS_KEY = "os_key";
    private static final String KEY_EXPIREON = "expireon";
    /*
    * 插入
    * mContext　上下文
    * field sharePreference 文件名
    * */
    public static String getLatitude() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LAT_KEY, "0");
        return s;
    }
    public static void saveExpireon(String value){
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_EXPIREON, value);
        editor.commit();
    }

    public static String getExpireon(){
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(KEY_EXPIREON, "");
        return s;
    }

    public static void saveLatitude(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LAT_KEY, value);
        editor.commit();
    }


    public static void saveToken(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LOGIN_TOKEN, value);
        editor.commit();
    }

    public static String getToken() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LOGIN_TOKEN, "");
        return s;
    }
    public static String getOsToken() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(OS_TOKEN, "");
        return s;
    }
    public static void saveOsToken(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(OS_TOKEN, value);
        editor.commit();
    }
    public static void saveOsKey(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(OS_KEY, value);
        editor.commit();
    }
    public static String getOsKey() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(OS_KEY, "");
        return s;
    }
    public static void saveOsSecret(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(OS_SECRET, value);
        editor.commit();
    }
    public static String getOsSecret() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(OS_SECRET, "");
        return s;
    }

    public static void savePwd(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ADMIN_ID, value);
        editor.commit();
    }

    public static String getPwd() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(ADMIN_ID, "");
        return s;
    }



    public static void saveUserId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, value);
        editor.commit();
    }

    public static String getUserId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(USER_ID, "");
        return s;
    }


}
