package com.reafor.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.LoginBean;
import com.reafor.jiamixiu.function.person.inter.IBindEmailView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class BindEmailPrenter {
    IBindEmailView bindEmailView;
    public BindEmailPrenter(IBindEmailView bindEmailView){
        this.bindEmailView = bindEmailView;
    }

    public void bind(String email,String code){
        HashMap<String ,String> map = new HashMap<>();
        map.put("email",email);
        map.put("code",code);
        map.put("pwd", SharedPreferencesUtil.getPwd());
        HttpManager.sendRequest(UrlConst.update_email, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
               bindEmailView.onBindSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                bindEmailView.onFail();
                bindEmailView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void getEmailCode(String phone){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("provider","SMS_Change_Email");
        HttpManager.sendRequest(UrlConst.sms_uurl, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                bindEmailView.onGetCode();
            }

            @Override
            public void onRequestFail(String result, String code) {
                bindEmailView.onFail();
                bindEmailView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
