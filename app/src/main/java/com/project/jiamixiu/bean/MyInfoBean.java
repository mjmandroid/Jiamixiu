package com.project.jiamixiu.bean;

import java.io.Serializable;

public class MyInfoBean extends BaseBean implements Serializable{
    /**
     * data : {"f_id":"7d9c5c3747c14c74929fcb3e44c4e0b1","nick":"会飞的企鹅","mobile":"13532707638","email":null,"username":"张三","avator":null,"laston":"2018-09-18 00:14:22","follow":99,"fans":100,"mobileconfig":true,"customerid":null,"f_creatoruserid":null,"f_creatortime":"2018-09-18 00:00:44"}
     */

    public DataBean data;



    public static class DataBean implements Serializable{
        /**
         * f_id : 7d9c5c3747c14c74929fcb3e44c4e0b1
         * nick : 会飞的企鹅
         * mobile : 13532707638
         * email : null
         * username : 张三
         * avator : null
         * laston : 2018-09-18 00:14:22
         * follow : 99
         * fans : 100
         * mobileconfig : true
         * customerid : null
         * f_creatoruserid : null
         * f_creatortime : 2018-09-18 00:00:44
         */

        public String f_id;
        public String nick;
        public String mobile;
        public String email;
        public String username;
        public String avator;
        public String laston;
        public String follow;
        public String fans;
        public boolean mobileconfig;
        public String customerid;
        public String f_creatoruserid;
        public String f_creatortime;

    }
}
