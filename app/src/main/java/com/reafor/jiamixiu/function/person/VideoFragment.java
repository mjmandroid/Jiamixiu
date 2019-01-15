package com.reafor.jiamixiu.function.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.MyWorkBean;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.person.activity.VideoCollectActivity;
import com.reafor.jiamixiu.function.person.activity.VideoCollectDetailActivity;
import com.reafor.jiamixiu.function.person.adapter.VideoAdapter;
import com.reafor.jiamixiu.function.person.inter.IMyWorkView;
import com.reafor.jiamixiu.function.person.presenter.MyWorkPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements IMyWorkView{
    private ListView lvVideo;
    private ArrayList<MyWorkBean.MyWorkData> list = new ArrayList<>();
    private MyWorkPresenter presenter;
    private int page = 0;
    private  VideoAdapter videoAdapter;
    private final int STATE_REFRESH = 0,STATE_LOADMORE = 1;
    private int load_state = STATE_REFRESH;
    private RefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,null);
        lvVideo = (ListView)view.findViewById(R.id.lv_video);
        refreshLayout = view.findViewById(R.id.refresh);
//        list = (ArrayList<CollectVideoBean.VideoData>)getArguments().getSerializable("video");
        videoAdapter= new VideoAdapter(getContext(),list);
        lvVideo.setAdapter(videoAdapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MyVideoDetailsActivity.class);
                intent.putExtra("id",list.get(position).f_id);
                intent.putExtra("videoThumbUrl",list.get(position).coverimg);
                intent.putExtra("page",page);
                intent.putExtra("list", datasCast());
                startActivity(intent);
            }
        });
        presenter = new MyWorkPresenter(this);
        presenter.loadData(page);
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            load_state = STATE_REFRESH;
            page = 0;
            presenter.loadData(page);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            load_state = STATE_LOADMORE;
            presenter.loadData(page);
        });
        return view;
    }

    @Override
    public void loadWorkData(MyWorkBean bean) {
        if (load_state == STATE_REFRESH){
            list.clear();
            if (bean.data != null && bean.data.size() > 0){
                list.addAll(bean.data);
                page ++;
            }
            refreshLayout.finishRefresh();
        } else {
            if (bean.data != null && bean.data.size() > 0){
                list.addAll(bean.data);
                page ++;
            }
            refreshLayout.finishLoadMore();
        }

        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreWorkData(MyWorkBean bean) {
        if (bean.data != null && bean.data.size() > 0){
            list.addAll(bean.data);
            page ++;
        }
        refreshLayout.finishLoadMore();
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowToast(String s) {

    }

    @Override
    public void onLoadFail() {
        if (load_state == STATE_REFRESH){

            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onCompleted() {

    }

    ArrayList<VideoResponse.VideoInfo> original;
    private ArrayList<VideoResponse.VideoInfo> datasCast(){
        if (original == null){
            original = new ArrayList<>();
            VideoResponse res = new VideoResponse();
            for (MyWorkBean.MyWorkData sruct : list) {
                VideoResponse.VideoInfo info = res.new VideoInfo();
                info.f_id = sruct.f_id;
                info.viewnum = sruct.viewnum;
                info.commentnum = sruct.commentnum;
                info.sharenum = sruct.sharenum;
                info.favoritenum = sruct.favoritenum;
                info.likenum = sruct.likenum;
                info.name = sruct.name;
                info.description = sruct.description;
                info.thumbnail = sruct.thumbnail;
                info.coverimg = sruct.coverimg;
                info.f_creatoruserid = sruct.f_creatoruserid;
                info.nick = sruct.nick;
                info.avator = sruct.avator;
                info.ossid = sruct.ossid;
                info.f_creatortime = sruct.f_creatortime;
                original.add(info);
            }
        }
        return original;
    }
}
