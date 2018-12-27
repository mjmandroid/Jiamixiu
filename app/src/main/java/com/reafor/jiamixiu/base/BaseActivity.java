package com.reafor.jiamixiu.base;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.reafor.jiamixiu.BaseApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {

    protected static final List<BaseActivity> activityList = new LinkedList<BaseActivity>(); // 将list加入到集合中统一管理;

    private static BaseActivity currentActivity; // 记录当前活动的是哪个页面；
    //  NFC适配器
    private NfcAdapter nfcAdapter = null;
    // 传达意图
    private PendingIntent pi = null;
    // 滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    // 是否支持NFC功能的标签
    private boolean should_cancel = true;
    protected int activityCloseEnterAnimation;

    protected int activityCloseExitAnimation;
    public void SetCancel(boolean istrue){
        should_cancel = istrue;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
        super.onCreate(savedInstanceState);
        activityList.add(0, this); // 在activity创建时加入到管理集合中去；
        initNavigation();
        initData();
        initView();
    }




    /**
     * 初始化页面布局
     */
    protected void initView() {

    }


    /**
     * 初始化一些页面上最基本的数据
     */
    protected void initData() {

    }

    /**
     * 初始化导航
     */
    protected void initNavigation() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this; // 当页面在交互时，记录起来！


    }

    @Override
    public void finish() {
        super.finish();
        //退出动画会失效
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    /**
     * 获取当前页面
     */
    public static BaseActivity getCurrentActivity() {

        return currentActivity;

    }

    /**
     * 屏蔽掉菜单键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_MENU == keyCode) {

            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;

        synchronized (BaseActivity.class) {

            copy = new ArrayList<BaseActivity>(activityList);

        }
        for (BaseActivity activity : copy) {

            activity.finish();

        }
    }


    /**
     * 退出应用
     */
    public void exitApp() {

        finishAll();

        android.os.Process.killProcess(android.os.Process.myPid());

        System.exit(0);
    }

    /**
     * 进入指定页面,isfinish为true，finish掉当前页面
     */
    protected void entryActivity(Class<? extends BaseActivity> clazz, boolean isfinish) {
        Intent homeIntent = new Intent(this, clazz);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        if (isfinish) {
            this.finish();
        }
    }

    public <T extends View> T findview(View view, @IdRes int id) {
        if (view == null) {
            return (T) findViewById(id);
        } else {
            return (T) view.findViewById(id);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     */
    public void closeSoftKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * @param key
     * @param defaultValue 默认值
     * @param <T>          接收类型 int boolean float String long
     * @return 返回sharedPreferences的结果
     */
    public <T> T getSharedPreferencesData(String key, T defaultValue) {
        SharedPreferences sp = BaseApplication.getConfigFile();
        if (defaultValue instanceof Integer) {
            Integer value = (Integer) defaultValue;
            Integer result = sp.getInt(key, value);
            return (T) result;
        } else if (defaultValue instanceof String) {
            String dvalue = (String) defaultValue;
            return (T) sp.getString(key, dvalue);
        } else if (defaultValue instanceof Boolean) {
            Boolean b = (Boolean) defaultValue;
            Boolean result = sp.getBoolean(key, b);
            return (T) result;
        } else if (defaultValue instanceof Float) {
            Float dvalue = (Float) defaultValue;
            Float result = sp.getFloat(key, dvalue);
            return (T) result;
        } else if (defaultValue instanceof Long) {
            Long dvalue = (Long) defaultValue;
            Long result = sp.getLong(key, dvalue);
            return (T) result;
        }
        return defaultValue;
    }

}
