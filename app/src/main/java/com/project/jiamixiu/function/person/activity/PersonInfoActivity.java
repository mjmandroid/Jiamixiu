package com.project.jiamixiu.function.person.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.ll_headimage)
    LinearLayout llHeadimage;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_address_2)
    TextView tvAddress2;
    @BindView(R.id.ll_address_2)
    LinearLayout llAddress2;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.tv_confirm_name)
    TextView tvConfirmName;
    @BindView(R.id.ll_confirm_name)
    LinearLayout llConfirmName;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_tier)
    TextView tvTier;
    @BindView(R.id.ll_tier)
    LinearLayout llTier;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.ll_bill)
    LinearLayout llBill;
    @BindView(R.id.btn_exit)
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_headimage,R.id.ll_nickname,R.id.ll_gender,R.id.ll_birthday,R.id.ll_address,R.id.ll_address_2,R.id.ll_sign,
            R.id.ll_confirm_name,R.id.ll_pwd,R.id.ll_email,R.id.ll_phone,R.id.ll_tier,R.id.ll_bill})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_headimage:
                break;
            case R.id.ll_nickname:
                break;
            case R.id.ll_gender:
                break;
            case R.id.ll_birthday:
                break;
            case R.id.ll_address:
                break;
            case R.id.ll_address_2:
                break;
            case R.id.ll_sign:
                break;
            case R.id.ll_confirm_name:
                break;
            case R.id.ll_pwd:
                break;
            case R.id.ll_email:
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_tier:
                break;
            case R.id.ll_bill:
                break;
            case R.id.btn_exit:
                break;

        }
    }
}
