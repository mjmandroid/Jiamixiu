package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.IBaseView;
import com.reafor.jiamixiu.bean.PersonInfoDetailBean;

public interface IPersonInfoView extends IBaseView {
    void loadPicSuccess(String fileUrl);
    void LoadPicFail();
    void getPersonInfo(PersonInfoDetailBean bean);
    void updateInfoSuccess();
    void updateFail();
}
