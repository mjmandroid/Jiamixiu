package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.ItemBannerResponse;
import com.reafor.jiamixiu.bean.ItemTagsResponse;
import com.reafor.jiamixiu.bean.VideoResponse;

import java.util.List;

public interface IitemFragmentView {
    void laodSuccess(List<VideoResponse.VideoInfo> infos);
    void loadFail(String error_msg);
    void laodFinish();
    void getItemTags(List<ItemTagsResponse.TagInfo> tagList);
    void getItemBanner(List<ItemBannerResponse.BannerInfo> infos);
}
