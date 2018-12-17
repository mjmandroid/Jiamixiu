package com.project.jiamixiu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.project.jiamixiu.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;

import iknow.android.utils.BaseUtils;

import static android.os.Build.MANUFACTURER;


/**
 * Application类，基类
 */
public class BaseApplication extends Application {
    private static SharedPreferences config; // app配置文件，包括用户设置;
    private static BaseApplication mInstance; // 全局上下文；
    private static Thread mMainThread;
    private static int mMainThreadId = -1;
    private static Handler mMainThreadHandler;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        config = getSharedPreferences("config", Context.MODE_PRIVATE);
        mInstance = this;
        BaseUtils.init(this);
        initFFmpegBinary(this);
        FileUtils.delVideoFile();
    }

    private void initFFmpegBinary(Context context) {

        try {
            FFmpeg.getInstance(context).loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception exception) {
//                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取全局配置文件
     */
    public static SharedPreferences getConfigFile() {
        return config;
    }
    public static SharedPreferences getPreferences() {
        return config;
    }

    public static BaseApplication getApplication() {
        return mInstance;
    }
    public static BaseApplication getContext(){
        return mInstance;
    }
    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程id
     */
    public static long getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获ui线程handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }




}
