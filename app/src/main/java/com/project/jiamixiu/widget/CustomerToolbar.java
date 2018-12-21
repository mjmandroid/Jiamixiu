package com.project.jiamixiu.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.jiamixiu.R;

public class CustomerToolbar extends LinearLayout {
    TextView tv_title;
    ImageView iv_back,iv_menu;

    public ImageView getIv_menu() {
        return iv_menu;
    }

    public CustomerToolbar(Context context) {
        super(context);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CustomerToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void setTitle(String s){
        tv_title.setText(s);
    }
    public void setTitleColor( int color){
        tv_title.setTextColor(color);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar,null);
        tv_title = (TextView)view.findViewById(R.id.tv_title);
        iv_back = (ImageView)view.findViewById(R.id.iv_back);
        iv_menu = view.findViewById(R.id.iv_menu);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbarListener != null){
                    toolbarListener.onBack();
                }
            }
        });
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    private ToolbarListener toolbarListener;

    public void setToolbarLisenter(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public interface ToolbarListener{
        void onBack();
    }
}
