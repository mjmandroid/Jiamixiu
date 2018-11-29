package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.ItemBannerResponse;
import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.VideoResponse;

import java.util.List;

public interface IitemFragmentView {
    void laodSuccess(List<VideoResponse.VideoInfo> infos);
    void loadFail(String error_msg);
    void laodFinish();
    void getItemTags(List<ItemTagsResponse.TagInfo> tagList);
    void getItemBanner(List<ItemBannerResponse.BannerInfo> infos);
}
