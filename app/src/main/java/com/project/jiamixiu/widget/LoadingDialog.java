package com.project.jiamixiu.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.jiamixiu.R;


/**
 * Created by Administrator on 2017/6/8 0008.
 * 加载
 */

public class LoadingDialog extends AlertDialog {
    private TextView tips_loading_msg;
    private ImageView iv_route;
    private String message = null;
    private AnimationDrawable mAnimation;
    private boolean isAnimationStop;
    public LoadingDialog(Context context) {
        this(context,"加载中...");
    }

    public LoadingDialog(Context context, String message) {
        this(context,R.style.loadingDialog,message);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);

        iv_route = (ImageView) findViewById(R.id.iv_route);
        mAnimation = (AnimationDrawable) iv_route.getBackground();
        initAnim();

    }

    private void initAnim() {
        iv_route.post(new Runnable() {
            @Override
            public void run() {
                isAnimationStop = false;
                mAnimation.start();
            }
        });
    }

    @Override
    public void dismiss() {
        try {
            isAnimationStop = true;
            mAnimation.stop();
            super.dismiss();
        }catch (Exception e){

        }
    }

    @Override
    public void show() {
        if (isAnimationStop){
            initAnim();
        }
        super.show();
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }
}
