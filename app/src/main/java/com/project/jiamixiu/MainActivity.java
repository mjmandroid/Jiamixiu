package com.project.jiamixiu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.project.jiamixiu.base.BaseFragment;
import com.project.jiamixiu.function.home.HomeFragment;
import com.project.jiamixiu.function.message.MessageFragment;
import com.project.jiamixiu.function.person.PersonFragment;
import com.project.jiamixiu.manger.FragmentChangeManager;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.OssUtils;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    FragmentChangeManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.rg);
        ArrayList fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonFragment());

        manager = new FragmentChangeManager(getSupportFragmentManager(),R.id.fl_content,fragmentList);
        initEvent();
        getOsToken();
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
                        manager.setFragments(1);
                        break;
                    case R.id.rb_mine:
                        manager.setFragments(2);
                        break;
                }
            }
        });
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
