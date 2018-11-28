package com.project.jiamixiu.function.login.inter;

import com.project.jiamixiu.bean.IBaseView;
import com.project.jiamixiu.bean.LoginBean;

public interface IRegisterView extends IBaseView{
    void onSuccess(LoginBean bean);
    void onFail();
    void onGetCode();
}
