package com.reafor.jiamixiu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoResponse extends BaseBean implements Serializable{
    public ArrayList<VideoInfo> data;
    public class VideoInfo implements Serializable{
        public String f_id;//视频id
        public String viewnum;//播放次数
        public String commentnum;//评论数
        public String sharenum;//分享次数
        public String favoritenum;//
        public String likenum;//点赞次数
        public String name;//
        public String description;//
        public String thumbnail;//
        public String coverimg;//	封面图
        public String f_creatoruserid;//
        public String nick;//发布人昵称
        public String ossid;//阿里OSS返回的objectid
        public String avator;//发布人头像
        public String f_creatortime;//
    }
}
