package com.reafor.jiamixiu.function.login.inter;

import com.reafor.jiamixiu.bean.IBaseView;
import com.reafor.jiamixiu.bean.LoginBean;

public interface IRegisterView extends IBaseView{
    void onSuccess(LoginBean bean);
    void onFail();
    void onGetCode();
}
