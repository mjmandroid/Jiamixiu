package com.reafor.jiamixiu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.function.home.HomeFragment;
import com.reafor.jiamixiu.function.home.HomeRightFragment;
import com.reafor.jiamixiu.function.message.MessageFragment;
import com.reafor.jiamixiu.function.person.PersonFragment;
import com.reafor.jiamixiu.function.subscrite.SubscriteFragment;
import com.reafor.jiamixiu.function.upload.CropVideoActivity;
import com.reafor.jiamixiu.manger.FragmentChangeManager;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.FileUtils;
import com.reafor.jiamixiu.utils.OssUtils;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import cn.jzvd.JzvdStd;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity  {
    public DrawerLayout drawerLayout;
    public boolean drawlayout_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fl_right = findViewById(R.id.fl_right);
        drawerLayout = findViewById(R.id.drawerLayout);
        DisplayMetrics metrics  = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) fl_right.getLayoutParams();
        params.width = metrics.widthPixels;
        fl_right.setLayoutParams(params );
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_right,new HomeRightFragment()).commit();
        getOsToken();
        drawerLayout.openDrawer(Gravity.RIGHT);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                JzvdStd.goOnPlayOnResume();

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (JzvdMgr.getCurrentJzvd() != null) {
                    Jzvd jzvd = JzvdMgr.getCurrentJzvd();
                    if (jzvd.currentState == JzvdStd.CURRENT_STATE_PLAYING){
                        JzvdStd.goOnPlayOnPause();
                    }
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }



    @Override
    protected void onResume() {
        super.onResume();
        FileUtils.delVideoFile();
    }

    private void getOsToken(){
        HttpManager.sendRequest(UrlConst.os_token, new HashMap<>(), new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.optJSONObject("data");
                    String securitytoken = data.optString("securitytoken");
                    SharedPreferencesUtil.saveOsToken(securitytoken);
                    String accesskeysecret = data.optString("accesskeysecret");
                    SharedPreferencesUtil.saveOsSecret(accesskeysecret);
                    String accesskeyid = data.optString("accesskeyid");
                    SharedPreferencesUtil.saveOsKey(accesskeyid);
                    data.optString("expiration");
                    OssUtils.getInstance().initOss(accesskeyid,accesskeysecret,securitytoken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFail(String result, String code) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    private long mExitTime;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showTosat(this,"再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
