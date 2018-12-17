package com.project.jiamixiu;

import android.content.Intent;
import android.os.Handler;

import com.project.jiamixiu.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_splash_layout);
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        },1000);
    }
}
