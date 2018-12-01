package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.bean.VideoResponse;

public interface IvideoDetailView {
    void loadDetailSuccess(VideoDetailResponse.Data data);
    void laodDetailFail(String errmsg);
    void loadFinish();

    void aboutSuccess();
}
