package com.project.jiamixiu.bean;

import java.util.ArrayList;

public class MyWorkBean extends BaseBean {
    public ArrayList<MyWorkData> data;
    public static class MyWorkData{
        /**
         * f_id : a5f30f46d09146b99d437b7b35d26a5d
         * viewnum : 3
         * commentnum : 0
         * sharenum : 0
         * favoritenum : 1
         * likenum : 1
         * name : 测试视频
         * description : 超神
         * thumbnail : null
         * coverimg : /ResourcesMedio/images/20181004/b55b22b7e3f04098a853e3da410b28588.jpg
         * f_creatoruserid : 7d9c5c3747c14c74929fcb3e44c4e0b1
         * nick : 会飞的企鹅
         * avator : null
         * f_creatortime : 2018-10-04 16:10:26
         */

        public String f_id;
        public String viewnum;
        public String commentnum;
        public String sharenum;
        public String favoritenum;
        public String likenum;
        public String name;
        public String description;
        public String thumbnail;
        public String coverimg;
        public String f_creatoruserid;
        public String nick;
        public String avator;
        public String f_creatortime;
    }

}
