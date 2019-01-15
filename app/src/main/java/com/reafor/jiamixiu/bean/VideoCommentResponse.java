package com.reafor.jiamixiu.bean;

import java.util.List;

public class VideoCommentResponse extends BaseBean {
    public List<Data> data;
    public class Data{

        public String f_id;
        public String sourthid;
        public String videoid;
        public String f_creatoruserid;
        public String f_creatortime;
        public String nick;
        public String message;
        public String avator;
        public int type = 0;
        public VideoRecommendResponse.VideoInfo recInfo;
    }
}
