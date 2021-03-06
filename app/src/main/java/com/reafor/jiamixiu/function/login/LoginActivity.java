package com.reafor.jiamixiu.function.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.reafor.jiamixiu.MainActivity;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.LoginBean;
import com.reafor.jiamixiu.function.login.inter.IRegisterView;
import com.reafor.jiamixiu.function.login.presenter.RegisterPresenter;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reafor.jiamixiu.utils.Const.LOGIN_SUCCESS_CODE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IRegisterView{

    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_pwd)
    TextView tv_pwd;
    @BindView(R.id.tv_no)
    TextView tv_no;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_ok)
    Button btnOk;
    RegisterPresenter presenter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_register.getPaint().setAntiAlias(true);//抗锯齿
        tv_no.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_no.getPaint().setAntiAlias(true);//抗锯齿
        tv_pwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_pwd.getPaint().setAntiAlias(true);//抗锯齿

        presenter = new RegisterPresenter(this);
        loadingDialog = new LoadingDialog(this);
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
                loadingDialog.show();
                presenter.login(phone,pwd);
                break;
        }
    }

    @Override
    public void onSuccess(LoginBean bean) {
        loadingDialog.dismiss();
        SharedPreferencesUtil.saveToken(bean.data.token);
        SharedPreferencesUtil.savePwd(edtPwd.getText().toString());
        SharedPreferencesUtil.saveExpireon(bean.data.expireon);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onGetCode() {

    }

    @Override
    public void onShowToast(String s) {
        loadingDialog.dismiss();
        UIUtils.showToast(this,s);
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }
}
