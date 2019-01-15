package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.IBaseView;

public interface IConfirmCardView extends IBaseView {
    void loadPicSuccess(String fileUrl);
    void LoadPicFail();
    void onConfirmSuccess();
}
