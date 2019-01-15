package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.FanBean;
import com.reafor.jiamixiu.bean.IBaseView;

public interface IFanView extends IBaseView{
    void loadData(FanBean bean);
    void onFollowSuccess();
}
