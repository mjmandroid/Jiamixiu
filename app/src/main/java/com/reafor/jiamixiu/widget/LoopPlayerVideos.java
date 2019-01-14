package com.reafor.jiamixiu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.reafor.jiamixiu.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class LoopPlayerVideos extends JzvdStd {
    public LoopPlayerVideos(Context context) {
        super(context);
    }

    public LoopPlayerVideos(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }



}
