package com.reafor.jiamixiu.function.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.LoginBean;
import com.reafor.jiamixiu.function.login.inter.IRegisterView;
import com.reafor.jiamixiu.function.login.presenter.RegisterPresenter;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IRegisterView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_ok)
    Button btnOk;
    RegisterPresenter presenter;
    int second = 60;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11) {
                if (second <= 0) {
                    second = 60;
                    tvCode.setText("重新获取");
                } else {
                    second--;
                    handler.sendEmptyMessageDelayed(11, 1000);
                    tvCode.setText(second + "s");
                }
            }
        }
    };
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.edt_pwd_2)
    EditText edtPwd2;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String type;
    @BindView(R.id.ll_go_login)
    LinearLayout llGoLogin;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        presenter = new RegisterPresenter(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("0")) {
            tvTitle.setText("注册");
            llGoLogin.setVisibility(View.VISIBLE);
        } else if (type.equals("1")) {
            tvTitle.setText("修改密码");
            llGoLogin.setVisibility(View.INVISIBLE);
            edtPwd.setHint("当前密码");
            edtPwd2.setHint("新密码");
        } else if (type.equals("2")) {
            tvTitle.setText("找回密码");
            edtPwd.setHint("设置密码");
            edtPwd2.setVisibility(View.GONE);
            llGoLogin.setVisibility(View.INVISIBLE);
        }else if (type.equals("3")) {
            tvTitle.setText("更换手机");
            edtPwd.setHint("当前密码");
            edtPwd2.setVisibility(View.GONE);
            llGoLogin.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_code, R.id.btn_ok,R.id.ll_go_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (llPhone.getVisibility() == View.VISIBLE) {
                    finish();
                } else if (llCode.getVisibility() == View.VISIBLE) {
                    llPhone.setVisibility(View.VISIBLE);
                    llCode.setVisibility(View.GONE);
                    llPwd.setVisibility(View.GONE);
                    btnOk.setText("下一步");
                    if (type.equals("0")) {
                        tvTitle.setText("注册");
                        llGoLogin.setVisibility(View.VISIBLE);
                    } else if (type.equals("1")) {
                        tvTitle.setText("修改密码");
                        llGoLogin.setVisibility(View.INVISIBLE);
                    } else if (type.equals("2")) {
                        tvTitle.setText("找回密码");
                        llGoLogin.setVisibility(View.INVISIBLE);
                    }
                } else if (llPwd.getVisibility() == View.VISIBLE) {
                    llPhone.setVisibility(View.GONE);
                    llCode.setVisibility(View.VISIBLE);
                    llPwd.setVisibility(View.GONE);
                    btnOk.setText("完成");
                    tvTitle.setText("填写信息验证码");
                }
                break;
            case R.id.tv_code:
                if (second == 60) {
                    String s = "";
                    if (type.equals("0")) {
                        s = "SMS_Register_Normal";
                    } else if (type.equals("1")) {
                        s = "SMS_Register_Normal";
                    } else if (type.equals("2")) {
                        s = "SMS_Forget_Password";
                    }else if (type.equals("3")) {
                        s = "SMS_Change_Mobile";
                    }
                    String phone = edtPhone.getText().toString();
                    presenter.getSMSCode(phone,s);
                } else {
                    UIUtils.showToast(this, second + "秒后再试");
                }
                break;
            case R.id.btn_ok:
                String phone = edtPhone.getText().toString();
                String pwd = edtPwd.getText().toString();
                String pwd2 = edtPwd2.getText().toString();
                String code = edtCode.getText().toString();
                if (llPhone.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(phone)) {
                        UIUtils.showToast(this, "请输入电话号码");
                        return;
                    }
                    tvTip.setText("请输入手机号" + phone + "收到的短信验证码");
                    llPhone.setVisibility(View.GONE);
                    llCode.setVisibility(View.VISIBLE);
                    tvTitle.setText("填写信息验证码");
                } else if (llCode.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(code)) {
                        UIUtils.showToast(this, "请输入验证码");
                        return;
                    }
                    llPhone.setVisibility(View.GONE);
                    llCode.setVisibility(View.GONE);
                    llPwd.setVisibility(View.VISIBLE);
                    tvTitle.setText("填写密码");
                    btnOk.setText("完成");
                } else if (llPwd.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(pwd)) {
                        UIUtils.showToast(this, "请输入密码");
                        return;
                    }
                    if (!pwd2.equals(pwd)) {
                        UIUtils.showToast(this, "确认密码错误");
                        return;
                    }
                    loadingDialog.show();
                    if (type.equals("0")) {
                        presenter.register(phone, pwd, code,type);
                    } else if (type.equals("1")) {
                        presenter.updatePwd(pwd2, pwd, code,type);
                    } else if (type.equals("2")) {
                        presenter.forgetPwd(phone, pwd, code,type);
                    }else if (type.equals("3")) {
                        presenter.updatePhone(phone, pwd, code,type);
                    }

                }
                break;
            case R.id.ll_go_login:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(LoginBean bean) {
        loadingDialog.dismiss();
        finish();
    }

    @Override
    public void onFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onGetCode() {
        UIUtils.showToast(this, "发送成功");
        handler.sendEmptyMessage(11);
    }

    @Override
    public void onShowToast(String s) {
        loadingDialog.dismiss();
        UIUtils.showToast(this, s);
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {
        loadingDialog.dismiss();
    }
}
