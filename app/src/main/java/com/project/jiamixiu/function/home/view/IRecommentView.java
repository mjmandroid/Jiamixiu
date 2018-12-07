package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.VideoCommentResponse;
import com.project.jiamixiu.bean.VideoRecommendResponse;

import java.util.List;

public interface IRecommentView {

    void loadRecommentSuccess(List<VideoRecommendResponse.VideoInfo> list);
    void loadRecommentFail(String errorMsg);
    void requestComplete();
}
