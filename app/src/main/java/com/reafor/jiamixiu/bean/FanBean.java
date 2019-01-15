package com.reafor.jiamixiu.bean;

import java.util.ArrayList;

public class FanBean extends BaseBean{
    public ArrayList<FanData> data;
    public static class FanData {

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
         * isfollow : true
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
        public boolean isfollow;

    }
}
