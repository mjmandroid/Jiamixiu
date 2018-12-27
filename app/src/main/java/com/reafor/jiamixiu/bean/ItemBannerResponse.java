package com.reafor.jiamixiu.bean;

import java.util.List;

public class ItemBannerResponse extends BaseBean{
    public List<BannerInfo> data;
    public class BannerInfo{
        public String f_id;
        public String name;
        public String imgurl;
        public String url;
        public String sortid;
        public String categoryid;
        public String f_creatoruserid;
        public String f_creatortime;
    }
}
