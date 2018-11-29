package com.project.jiamixiu.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.jiamixiu.MainActivity;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.LoginBean;
import com.project.jiamixiu.function.login.inter.IRegisterView;
import com.project.jiamixiu.function.login.presenter.RegisterPresenter;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.jiamixiu.utils.Const.LOGIN_SUCCESS_CODE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IRegisterView{

    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_pwd)
    TextView tv_pwd;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_ok)
    Button btnOk;
    RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(this);
    }

    @OnClick({R.id.tv_register,R.id.tv_pwd,R.id.btn_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.tv_pwd:
                Intent intent2 = new Intent(this,RegisterActivity.class);
                intent2.putExtra("type","2");
                startActivity(intent2);
                break;
            case R.id.btn_ok:
                String phone = edtPhone.getText().toString();
                String pwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    UIUtils.showToast(this,"请输入电话号码");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    UIUtils.showToast(this,"请输入密码");
                    return;
                }
                presenter.login(phone,pwd);
                break;
        }
    }

    @Override
    public void onSuccess(LoginBean bean) {
        SharedPreferencesUtil.saveToken(bean.data.token);
        setResult(LOGIN_SUCCESS_CODE);
        finish();

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onGetCode() {

    }

    @Override
    public void onShowToast(String s) {
        UIUtils.showToast(this,s);
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onCompleted() {

    }
}
