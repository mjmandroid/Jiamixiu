package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.FanBean;
import com.project.jiamixiu.bean.IBaseView;

public interface IFanView extends IBaseView{
    void loadData(FanBean bean);
    void onFollowSuccess();
}
