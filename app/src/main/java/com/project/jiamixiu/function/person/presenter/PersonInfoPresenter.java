package com.project.jiamixiu.function.person.presenter;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.MyInfoBean;
import com.project.jiamixiu.bean.PersonInfoDetailBean;
import com.project.jiamixiu.function.person.activity.PersonInfoActivity;
import com.project.jiamixiu.function.person.inter.IPersonInfoView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.DialogUtils;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class PersonInfoPresenter {
    IPersonInfoView personView;
    public PersonInfoPresenter(IPersonInfoView iPersonInfoView){
        this.personView = iPersonInfoView;
    }

    public void getPersonInfo(){
        HashMap<String ,String> map = new HashMap<>();
        HttpManager.sendRequest(UrlConst.person_detail_info_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PersonInfoDetailBean bean = new Gson().fromJson(response,PersonInfoDetailBean.class);
                personView.getPersonInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                personView.onLoadFail();
                personView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void updateInfo(String s,int i){
        HashMap<String ,String> map = new HashMap<>();
        map.put("value",s);
        String url = "";
        switch (i){
            case 1:
                url = UrlConst.avator_update_url;
                break;
            case 2:
                url = UrlConst.gender_update_url;
                break;
            case 3:
                url = UrlConst.nick_update_url;
                break;
            case 4:
                url = UrlConst.sign_update_url;
                break;
            case 5:
                url = UrlConst.birth_update_url;
                break;
            case 6:
                url = UrlConst.address_update_url;
                break;
            case 7:
                url = UrlConst.region_update_url;
                break;
        }
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                personView.updateInfoSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                personView.updateFail();
                personView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void upLoadingFile(String file){
        HttpManager.sendRequestFile(UrlConst.uploadFile,file ,new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    personView.loadPicSuccess(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                personView.onLoadFail();
                personView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }


}
