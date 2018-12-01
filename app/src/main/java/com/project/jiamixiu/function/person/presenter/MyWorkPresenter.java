package com.project.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.CollectVideoBean;
import com.project.jiamixiu.bean.MyWorkBean;
import com.project.jiamixiu.function.person.inter.IMyWorkView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

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

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
