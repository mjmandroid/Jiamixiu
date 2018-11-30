package com.project.jiamixiu.function.home.prenster;

import com.alibaba.sdk.android.oss.OSS;
import com.google.gson.Gson;
import com.project.jiamixiu.BaseApplication;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.OssUtils;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class VideoDetailPrenster {

    private IvideoDetailView ivideoDetailView;

    public  VideoDetailPrenster(IvideoDetailView ivideoDetailView) {
        this.ivideoDetailView = ivideoDetailView;
    }

    public void loadDetail(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.video_detail, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoDetailResponse response = new Gson().fromJson(result, VideoDetailResponse.class);
                try {
                    OSS mOss = OssUtils.getInstance().getOss();
                    String url = mOss.presignConstrainedObjectURL("jiamixiu", response.data.ossid+".mp4",30 * 60);
                    response.data.url = url;
                    ivideoDetailView.loadDetailSuccess(response.data);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showTosat(BaseApplication.getContext(),"获取视频失败！");
                }
            }

            @Override
            public void onRequestFail(String result, String code) {
                ivideoDetailView.laodDetailFail(result);
            }

            @Override
            public void onCompleted() {
                ivideoDetailView.loadFinish();
            }
        });
    }

    public void about(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.video_about, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ivideoDetailView.aboutSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                ivideoDetailView.laodDetailFail(result);
            }

            @Override
            public void onCompleted() {
                ivideoDetailView.loadFinish();
            }
        });
    }
}
