package com.reafor.jiamixiu.function.person.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.function.login.inter.IRegisterView;
import com.reafor.jiamixiu.function.person.inter.IBindEmailView;
import com.reafor.jiamixiu.function.person.presenter.BindEmailPrenter;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BindEmailActivity extends AppCompatActivity implements IBindEmailView {
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_code)
    EditText edt_code;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    boolean b ;
    BindEmailPrenter prenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        ButterKnife.bind(this);
        toolbar.setTitle("绑定邮箱");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.toString().endsWith(".com")){
                    b = true;
                    tv_get_code.setBackgroundColor(ContextCompat.getColor(BindEmailActivity.this,R.color.theme_color));
                }else {
                    b = false;
                    tv_get_code.setBackgroundColor(ContextCompat.getColor(BindEmailActivity.this,R.color.text_color_99));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b) {
                    return;
                }
             /*   if (TextUtils.isEmpty(edt_email.getText().toString())){
                    UIUtils.showToast(BindEmailActivity.this,"请输入邮箱");
                    return;
                }*/
                prenter.getEmailCode(edt_email.getText().toString());
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_email.getText().toString())){
                    UIUtils.showToast(BindEmailActivity.this,"请输入邮箱");
                    return;
                }
                if (TextUtils.isEmpty(edt_code.getText().toString())){
                    UIUtils.showToast(BindEmailActivity.this,"请输入验证码");
                    return;
                }
                prenter.bind(edt_email.getText().toString(),edt_code.getText().toString());
            }
        });
        prenter = new BindEmailPrenter(this);
    }

    @Override
    public void onBindSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onGetCode() {
        UIUtils.showToast(this,"发送成功");
    }

    @Override
    public void onShowToast(String s) {

    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onCompleted() {

    }
}
