package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.VideoCommentResponse;
import com.reafor.jiamixiu.bean.VideoRecommendResponse;

import java.util.List;

public interface IRecommentView {

    void loadRecommentSuccess(List<VideoRecommendResponse.VideoInfo> list);
    void loadRecommentFail(String errorMsg);
    void requestComplete();
}
