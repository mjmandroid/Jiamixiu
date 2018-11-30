package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.IBaseView;
import com.project.jiamixiu.bean.PersonInfoDetailBean;

public interface IPersonInfoView extends IBaseView {
    void loadPicSuccess(String fileUrl);
    void LoadPicFail();
    void getPersonInfo(PersonInfoDetailBean bean);
    void updateInfoSuccess();
    void updateFail();
}
