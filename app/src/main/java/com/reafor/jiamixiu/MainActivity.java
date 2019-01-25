package com.reafor.jiamixiu;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.reafor.jiamixiu.function.home.HomeFragment;
import com.reafor.jiamixiu.function.home.HomeRightFragment;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.FileUtils;
import com.reafor.jiamixiu.utils.OssUtils;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.utils.UrlConst;
import com.reafor.jiamixiu.utils.VideoTrimmerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import cn.jzvd.JzvdStd;

public class MainActivity extends AppCompatActivity  {
    public DrawerLayout drawerLayout;

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

    private void testAddWaterVideos() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String srcPath = rootPath + File.separator+"Download/mask_start.mp4";
        String waterPath = rootPath + File.separator + "Download/Water.jpg";
        String targetPath = rootPath + File.separator+"Download/target.mp4";
        VideoTrimmerUtil.addWaterMark(this,srcPath,waterPath,targetPath,null);
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
