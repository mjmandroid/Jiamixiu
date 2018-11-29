package com.project.jiamixiu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TabBeanResponse extends BaseBean{
    public List<TabBean> data;
    public class TabBean{
        public String name;
        public String sortid;
        @SerializedName("f_id")
        public String fid;
    }
}
