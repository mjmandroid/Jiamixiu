package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.VidoeRecordResponse;

import java.util.List;

public interface IRecordVideoView {

    void loadSuccess(List<VidoeRecordResponse.Data> dataList);
    void loadFail(String errmsg);
    void requestCompleted();
}
