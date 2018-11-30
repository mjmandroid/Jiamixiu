package com.project.jiamixiu.utils;

import android.content.Context;


import com.project.jiamixiu.BaseApplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static void showToast(Context context,String s) {
      Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static View inflate(Context context,int resId) {
        return LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context,int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    public static int px2dip(Context context,int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    public static void setTextViewFakeBold(TextView v,boolean b){
        v.getPaint().setFakeBoldText(b);
    }
    public static int getWindowWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }


    public static int getColor(String color){
        int c = Color.parseColor("#ffffff");
        if (!TextUtils.isEmpty(color) && color.startsWith("#") && (color.length() == 7 || color.length() == 9)){
            c = Color.parseColor(color);
        }
        return c;
    }

    public static String getHidePhoneNumber(String s){
        return s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    public static String getHideEmail(String s){
        return s.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }
}
