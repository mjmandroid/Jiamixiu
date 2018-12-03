package com.project.jiamixiu.function.person.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.jiamixiu.R;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmCardActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_number)
    EditText edt_number;
    @BindView(R.id.iv_pic2)
    ImageView iv_pic2;
    @BindView(R.id.iv_pic1)
    ImageView iv_pic1;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_card);
        ButterKnife.bind(this);
        toolbar.setTitle("实名认证");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
    @OnClick({R.id.btn_ok,R.id.rl_pic2,R.id.rl_pic1})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                break;
            case R.id.rl_pic1:
                break;
            case R.id.rl_pic2:
                break;
        }
    }
}

