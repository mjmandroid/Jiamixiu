package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.IBaseView;

public interface IBindEmailView extends IBaseView {
    void onBindSuccess();
    void onFail();
    void onGetCode();
}
