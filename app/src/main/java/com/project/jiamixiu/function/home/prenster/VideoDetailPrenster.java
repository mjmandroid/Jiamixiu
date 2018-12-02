package com.project.jiamixiu.function.home.prenster;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.google.gson.Gson;
import com.project.jiamixiu.BaseApplication;
import com.project.jiamixiu.bean.VideoCommentResponse;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.bean.VideoRecommendResponse;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.OssUtils;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                    OSSLog.logDebug(url);
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

    public void commit(String v_id,String comment,String sourthId){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",v_id);
        sMap.put("comment",comment);
        sMap.put("sourthId",sourthId);
        HttpManager.sendRequest(UrlConst.video_comment, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ivideoDetailView.commentSuccess();
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

    public void praise(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.vido_praise, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ivideoDetailView.praiseSuccess();
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

    public void favorite(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.video_favorite, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ivideoDetailView.favoriteSuccess();
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

    public void share(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.video_share, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ivideoDetailView.shareSuccess();
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

    public void getRecommendVideo(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex","0");
        sMap.put("pageSize","1");
        HttpManager.sendRequest(UrlConst.video_recommend, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoRecommendResponse response = new Gson().fromJson(result, VideoRecommendResponse.class);
                ivideoDetailView.getVideoRecommend(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                ivideoDetailView.loadRecommendFail(result);
            }

            @Override
            public void onCompleted() {
                ivideoDetailView.loadFinish();
            }
        });
    }

    public void getCommentList(String id,String pageIndex,String pageSize,VideoRecommendResponse.VideoInfo videoInfo){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.video_comment_list, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoCommentResponse response = new Gson().fromJson(result, VideoCommentResponse.class);
                List<VideoCommentResponse.Data> dataList = new ArrayList<>();
                if (response.data != null && response.data.size() > 0){
                    VideoCommentResponse.Data head = response.new Data();
                    head.type = 1;
                    head.recInfo = videoInfo;
                    dataList.add(head);
                    dataList.addAll(response.data);
                    ivideoDetailView.getVideoCommentList(dataList);
                } else {
                    ivideoDetailView.getVideoCommentList(dataList);
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

}
