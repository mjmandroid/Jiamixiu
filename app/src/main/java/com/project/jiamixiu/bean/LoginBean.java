package com.project.jiamixiu.bean;

public class LoginBean extends BaseBean {

    /**
     * data : {"token":"d234c7eed3e44f45b2789a6ca6e1939a","expireon":"2018-09-19 00:10:18"}
     */
    public DataBean data;

    public static class DataBean {
        /**
         * token : d234c7eed3e44f45b2789a6ca6e1939a
         * expireon : 2018-09-19 00:10:18
         */
        public String token;
        public String expireon;
    }
}
