package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.IBaseView;
import com.project.jiamixiu.bean.MyInfoBean;

public interface IPersonView extends IBaseView{
    void getPersonInfoSuccessed(MyInfoBean bean);
    void onFail();
}
