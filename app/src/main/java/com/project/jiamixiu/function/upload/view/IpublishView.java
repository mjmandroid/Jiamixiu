package com.project.jiamixiu.function.upload.view;

public interface IpublishView {
    void commitsuccess();
    void onError(String errmsg);
    void onFinish();

    void uploadVideoSuccess(String ossid);
    void uploadPictureSuccess(String imgUrl);
}
