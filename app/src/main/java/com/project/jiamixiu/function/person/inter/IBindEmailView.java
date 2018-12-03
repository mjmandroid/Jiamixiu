package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.IBaseView;

public interface IBindEmailView extends IBaseView {
    void onBindSuccess();
    void onFail();
    void onGetCode();
}
