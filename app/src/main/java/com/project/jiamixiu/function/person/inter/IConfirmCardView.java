package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.IBaseView;

public interface IConfirmCardView extends IBaseView {
    void loadPicSuccess(String fileUrl);
    void LoadPicFail();
    void onConfirmSuccess();
}
