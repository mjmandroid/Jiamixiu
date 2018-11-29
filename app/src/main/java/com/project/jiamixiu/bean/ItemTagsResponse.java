package com.project.jiamixiu.bean;

import java.util.List;

public class ItemTagsResponse extends BaseBean {
    public List<TagInfo> data;
    public class TagInfo{

        public String name;
        public String sortid;
        public String categoryid;

    }
}
