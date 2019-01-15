package com.reafor.jiamixiu.function.message.inter;

import com.reafor.jiamixiu.bean.IBaseView;
import com.reafor.jiamixiu.bean.MyCommentBean;

public interface ICommentView extends IBaseView {
    void onLoadCommentData(MyCommentBean bean);
}
