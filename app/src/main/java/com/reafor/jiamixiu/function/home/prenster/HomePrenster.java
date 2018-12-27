package com.reafor.jiamixiu.function.home.prenster;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.TabBeanResponse;
import com.reafor.jiamixiu.function.home.view.IhomeView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.lang.reflect.Field;
import java.util.HashMap;

public class HomePrenster {
    private IhomeView homeView;
    public HomePrenster(IhomeView homeView) {
        this.homeView = homeView;
    }

    public void acquireTabs(){
        HttpManager.sendRequest(UrlConst.get_tabs, new HashMap<String, String>(), new HttpRequestListener(){

            @Override
            public void onRequestSuccess(String response) {
                Gson gson = new Gson();
                TabBeanResponse res = gson.fromJson(response, TabBeanResponse.class);
                homeView.getTabsSuccess(res.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                homeView.getTabsFail(result);
            }

            @Override
            public void onCompleted() {
                homeView.requestFinish();
            }
        });
    }

    /**
     * @param tabLayout
     * @param padding
     * 设置tablayout的indcdactor 宽度为wrap_content
     */
    public static void setTabWidth(final TabLayout tabLayout, final int padding){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);



                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
