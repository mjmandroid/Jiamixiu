package com.reafor.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.MyWorkBean;
import com.reafor.jiamixiu.function.person.inter.IMyWorkView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class MyWorkPresenter {
    IMyWorkView workView;

    public MyWorkPresenter(IMyWorkView workView){
        this.workView = workView;
    }
    public void loadData(final int page){
        HashMap<String ,String> map = new HashMap<>();
        map.put("pageIndex",page+"");
        map.put("pageSize","20");
        HttpManager.sendRequest(UrlConst.work_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                MyWorkBean bean = new Gson().fromJson(response,MyWorkBean.class);
                if (page > 0){
                    workView.loadMoreWorkData(bean);
                }else {
                    workView.loadWorkData(bean);
                }
            }

            @Override
            public void onRequestFail(String result, String code) {
                workView.onLoadFail();
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
