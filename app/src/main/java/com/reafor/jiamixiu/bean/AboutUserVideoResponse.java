package com.reafor.jiamixiu.bean;

import java.util.List;

public class AboutUserVideoResponse extends BaseBean {
    public List<VideoSruct> data;
    public class VideoSruct{
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
        public int type = 0;
        public UserInfoResponse.UserInfo userInfo;
        public List<SubscribeUsersResponse.Data> headView;
    }
}
