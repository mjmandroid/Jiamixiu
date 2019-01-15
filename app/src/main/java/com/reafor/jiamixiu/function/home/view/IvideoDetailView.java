package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.VideoCommentResponse;
import com.reafor.jiamixiu.bean.VideoDetailResponse;
import com.reafor.jiamixiu.bean.VideoRecommendResponse;
import com.reafor.jiamixiu.bean.VideoResponse;

import java.util.List;

public interface IvideoDetailView {
    void loadDetailSuccess(VideoDetailResponse.Data data);
    void laodDetailFail(String errmsg);
    void loadFinish();
    void loadRecommendFail(String errmesg);
    void aboutSuccess();
    void commentSuccess();
    void praiseSuccess();
    void favoriteSuccess();
    void shareSuccess();
    void getVideoRecommend(List<VideoRecommendResponse.VideoInfo> infos);
    void getVideoCommentList(List<VideoCommentResponse.Data> list);
}
