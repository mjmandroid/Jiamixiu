package com.project.jiamixiu.function.login.presenter;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.LoginBean;
import com.project.jiamixiu.function.login.inter.IRegisterView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class RegisterPresenter {
    IRegisterView registerView;
    public RegisterPresenter(IRegisterView registerView){
        this.registerView = registerView;
    }

    public void register(String phone,String pwd,String code,String type){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("password",pwd);
        map.put("code",code);
        String url  = "";
        if (type.equals("0")) {
           url = UrlConst.register_url;
        } else if (type.equals("1")) {
            url = UrlConst.update_pwd__url;
        } else if (type.equals("2")) {
            url = UrlConst.forget_pwd__url;
        }
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                LoginBean bean = new Gson().fromJson(response,LoginBean.class);
                registerView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                registerView.onFail();
                registerView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void login(String phone,String pwd){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("password",pwd);
        HttpManager.sendRequest(UrlConst.login_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                LoginBean bean = new Gson().fromJson(response,LoginBean.class);
                registerView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                registerView.onFail();
                registerView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void getSMSCode(String phone,String provider){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("provider",provider);
        HttpManager.sendRequest(UrlConst.sms_uurl, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                LoginBean bean = new Gson().fromJson(response,LoginBean.class);
                registerView.onGetCode();
            }

            @Override
            public void onRequestFail(String result, String code) {
                registerView.onFail();
                registerView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }

}
