package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.IBaseView;
import com.reafor.jiamixiu.bean.MyInfoBean;

public interface IPersonView extends IBaseView{
    void getPersonInfoSuccessed(MyInfoBean bean);
    void onFail();
}
