package com.project.jiamixiu.utils;

import android.content.Context;

import com.project.jiamixiu.BaseApplication;

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.getApplication();
    }
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
