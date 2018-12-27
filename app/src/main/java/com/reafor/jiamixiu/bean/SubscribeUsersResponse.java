package com.reafor.jiamixiu.bean;

import java.util.List;

public class SubscribeUsersResponse extends BaseBean {
    public List<Data> data;
    public class Data{
        public String nick;
        public String avator;
        public String f_id;
    }
}
