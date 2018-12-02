package com.project.jiamixiu.bean;

import java.util.List;

public class VideoRecommendResponse extends BaseBean {
    public List<VideoInfo> data;
    public class VideoInfo{
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
