package com.reafor.jiamixiu.function.person.presenter;

import com.reafor.jiamixiu.function.person.inter.IConfirmCardView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConfirmCardPresenter {
    IConfirmCardView cardView;
    public ConfirmCardPresenter(IConfirmCardView cardView){
        this.cardView = cardView;
    }
    public void upLoadingFile(String file){
        HttpManager.sendRequestFile(UrlConst.uploadFile,file ,new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    cardView.loadPicSuccess(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                cardView.onLoadFail();
                cardView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void confirm(String realname,String cardNo,String cardImg1,String cardImg2,String pwd){
        HashMap<String ,String> map = new HashMap<>();
        map.put("realname",realname);
        map.put("cardNo",cardNo);
        map.put("cardImg1",cardImg1);
        map.put("cardImg2",cardImg2);
        map.put("pwd",pwd);

        HttpManager.sendRequest(UrlConst.card_update_url,map ,new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cardView.onConfirmSuccess();

            }

            @Override
            public void onRequestFail(String result, String code) {
                cardView.onLoadFail();
                cardView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
