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

    public static String getLongitude() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LNG_KEY, "0");
        return s;
    }

    public static void saveLatitude(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LAT_KEY, value);
        editor.commit();
    }

    public static void saveCustomerAppId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CUSTOMER_APP_ID, value);
        editor.commit();
    }

    public static String getCustomerAppId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(CUSTOMER_APP_ID, "");
        return s;
    }
    public static void saveHotOnline(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(HOT_ONLINE, value);
        editor.commit();
    }
    public static String getHotOnline(){
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(HOT_ONLINE, "");
        return s;
    }
    public static void saveCustomerLogo(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PING_LOGO, value);
        editor.commit();
    }

    public static String getCustomerLogo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(PING_LOGO, "");
        return s;
    }

    public static void saveLongitude(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LNG_KEY, value);
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

    public static void saveAdminId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ADMIN_ID, value);
        editor.commit();
    }

    public static String getAdminId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(ADMIN_ID, "");
        return s;
    }

    public static void saveWPageid(String wPageId) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WPAGE_ID, wPageId);
        editor.commit();
    }

    public static void saveIsGuoQi(boolean isGuoQi) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(APP_GUO_QI, isGuoQi);
        editor.commit();
    }

    public static boolean getIsGuoQi() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(APP_GUO_QI, false);
        return b;
    }

    public static String getWPageid() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(WPAGE_ID, "");
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

    public static void saveLoginPhone(String phone) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LOGIN_PHONE, phone);
        editor.commit();
    }

    public static String getLoginPhone() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LOGIN_PHONE, "");
        return s;
    }




    public static void saveShowCouponDate(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(IS_SHOW_COUPON, value);
        editor.commit();
    }

    public static String getShowCouponDate() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(IS_SHOW_COUPON, "");
        return s;
    }

    public static void saveDeviceId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DEVICE_ID, value);
        editor.commit();
    }

    public static String getDeviceId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(DEVICE_ID, "");
        return s;
    }

    public static void saveIsEffectVip(boolean isEffectVip) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("is_effectVip", isEffectVip);
        editor.commit();
    }

    public static boolean getIsEffectVip() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("is_effectVip", false);
        return b;
    }

    public static void saveIsShopMember(boolean isShopMember) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isShopMember", isShopMember);
        editor.commit();
    }

    public static boolean getIsShopMember() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("isShopMember", false);
        return b;
    }

    public static void saveIsFreezenMember(boolean isFreezenMember) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFreezenMember", isFreezenMember);
        editor.commit();
    }

    public static boolean getIsFreezenMember() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("isFreezenMember", false);
        return b;
    }

    public static void isFirstCome(boolean value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_FIRST_COME, value);
        editor.commit();
    }

    public static boolean getFirstCome() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_FIRST_COME, false);
        return b;
    }

    /*
     * 保存定位搜索信息
     * context 上下文
     * jsonString　登录信息,json数据
     */


    /*获取所有历史搜索信息
    * context 上下文
    * */

    public static void clearLocationSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_MAP_SEARCH, json);
        editor.commit();
    }

    /*
  * 保存店铺搜索信息
  * context 上下文
   * jsonString　登录信息,json数据
  * */



    public static void clearShopSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_SHOP_SEARCH, json);
        editor.commit();
    }



    public static void clearPublishSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_PUBLISH_SEARCH, json);
        editor.commit();
    }


}
