package com.reafor.jiamixiu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.reafor.jiamixiu.BaseApplication;


/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class NetWorkUtil {
    /**
     * 判断网络是否连接.
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }
}
