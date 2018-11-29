package com.project.jiamixiu.bean;

public class PersonInfoDetailBean {
    public DataBean data;
     /*"data": {
        "f_id": "7d9c5c3747c14c74929fcb3e44c4e0b1",
                "nick": "会飞的企鹅",
                "mobile": "13532707638",
                "email": "13532707638",
                "username": "张三",
                "avator": "13532707638",
                "birthday": "13532707638",
                "realname": "13532707638",
                "cardno": "13532707638",
                "cardimg1": "13532707638",
                "cardimg2": "13532707638",
                "gender": 0,
                "region": "13532707638",
                "address": "13532707638",
                "sign": "13532707638",
                "laston": "2018-09-18 00:14:22",
                "follow": 99,
                "fans": 100,
                "mobileconfig": true,
                "customerid": "13532707638",
                "amt": 0,
                "cashamt": 0,
                "level": 0,
                "birthday":"13532707638" ,
                "f_creatoruserid": "13532707638",
                "f_creatortime": "2018-09-18 00:00:44"
    }*/

    public static class DataBean {
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

        public String gender;
        public String realname;
        public String cardno;
        public String cardimg2;
        public String region;
        public String address;

        public String sign;
        public String amt;
        public String cashamt;
        public String level;
        public String birthday;
    }
}
