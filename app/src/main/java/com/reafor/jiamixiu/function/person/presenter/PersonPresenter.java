package com.reafor.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.LoginBean;
import com.reafor.jiamixiu.bean.MyInfoBean;
import com.reafor.jiamixiu.function.person.inter.IPersonView;
import com.reafor.jiamixiu.function.person.request.PersonRequest;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class PersonPresenter   {
    IPersonView personView;
    public PersonPresenter(IPersonView personView){
        this.personView = personView;
    }

    public void loadPersonInfo(){
        HashMap<String ,String> map = new HashMap<>();
        HttpManager.sendRequest(UrlConst.person_info_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                MyInfoBean  bean = new Gson().fromJson(response,MyInfoBean.class);
                personView.getPersonInfoSuccessed(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                personView.onFail();
                personView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
