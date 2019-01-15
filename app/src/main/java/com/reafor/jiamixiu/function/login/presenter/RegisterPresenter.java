package com.reafor.jiamixiu.function.login.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.LoginBean;
import com.reafor.jiamixiu.function.login.inter.IRegisterView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

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
        HttpManager.sendRequest(UrlConst.register_url, map, new HttpRequestListener() {
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
    public void updatePhone(String phone,String pwd,String code,String type){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("pwd",pwd);
        map.put("code",code);
        HttpManager.sendRequest(UrlConst.update_phone, map, new HttpRequestListener() {
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
    public void forgetPwd(String phone,String pwd,String code,String type){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("newpwd",pwd);
        map.put("code",code);
        HttpManager.sendRequest(UrlConst.forget_pwd__url, map, new HttpRequestListener() {
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
    public void updatePwd(String newpwd,String pwd,String code,String type){
        HashMap<String ,String> map = new HashMap<>();
        map.put("newpwd",newpwd);
        map.put("pwd",pwd);
        map.put("code",code);

        HttpManager.sendRequest( UrlConst.update_pwd__url, map, new HttpRequestListener() {
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
