package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.VideoCommentResponse;

import java.util.List;

public interface IvideoView {
    void getCommentList(List<VideoCommentResponse.Data> dataList);
    void loadFail(String errmsg);
    void loadFinish();
    void commentSuccess();
    void praiseSuccess();
}
