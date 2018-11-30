package com.project.jiamixiu.bean;

public class OssAuthBean extends BaseBean{

   public DataBean data;
    public static class DataBean{
        public String securitytoken;
        public String accesskeysecret;
        public String accesskeyid;
        public String expiration;
    }

}
