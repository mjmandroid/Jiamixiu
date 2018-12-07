package com.project.jiamixiu.bean;

import java.util.List;

public class VidoeRecordResponse extends BaseBean {

    public List<Data> data;
    public class Data{
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

    /**
     *  "": "a5f30f46d09146b99d437b7b35d26a5d",
     "": 3,
     "": 0,
     "": 0,
     "": 0,
     "": 0,
     "": "测试视频",
     "": "超神",
     "": null,
     "": "/ResourcesMedio/images/20181004/b55b22b7e3f04098a853e3da410b28588.jpg",
     "": "7d9c5c3747c14c74929fcb3e44c4e0b1",
     "": "会飞的企鹅",
     "": null,
     "": "2018-10-04 16:10:26"
     */
}
