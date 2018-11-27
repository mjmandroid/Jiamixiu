package com.project.jiamixiu.function.home;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseFragment;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment  extends BaseFragment{
    private TabLayout tabLayout;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home_layout,null);
        tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));


        return view;
    }

    @Override
    public void initData() {

    }

}
