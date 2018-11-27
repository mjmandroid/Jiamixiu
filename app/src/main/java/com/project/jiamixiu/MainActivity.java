package com.project.jiamixiu;

import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.project.jiamixiu.base.BaseFragment;
import com.project.jiamixiu.function.home.HomeFragment;
import com.project.jiamixiu.manger.FragmentChangeManager;
import com.project.jiamixiu.utils.ToastUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    FragmentChangeManager manager;
=======
//11
public class MainActivity extends AppCompatActivity {
>>>>>>> 2c00c59fd7b4d5aaab3d3646996b521f433f62bc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.rg);
        ArrayList fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HomeFragment());
        manager = new FragmentChangeManager(getSupportFragmentManager(),R.id.fl_content,fragmentList);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.iv_add).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        manager.setFragments(0);
                        break;
                    case R.id.rb_sub:

                        break;
                    case R.id.rb_msg:

                        break;
                    case R.id.rb_mine:

                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

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
