package com.reafor.jiamixiu.function.person;

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
import com.reafor.jiamixiu.function.person.adapter.VideoAdapter;
import com.reafor.jiamixiu.function.person.inter.IMyWorkView;
import com.reafor.jiamixiu.function.person.presenter.MyWorkPresenter;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements IMyWorkView{
    private ListView lvVideo;
    private ArrayList<MyWorkBean.MyWorkData> list = new ArrayList<>();
    private MyWorkPresenter presenter;
    private int page = 0;
    private  VideoAdapter videoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,null);
        lvVideo = (ListView)view.findViewById(R.id.lv_video);
//        list = (ArrayList<CollectVideoBean.VideoData>)getArguments().getSerializable("video");
        videoAdapter= new VideoAdapter(getContext(),list);
        lvVideo.setAdapter(videoAdapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        presenter = new MyWorkPresenter(this);
        presenter.loadData(page);
        return view;
    }

    @Override
    public void loadWorkData(MyWorkBean bean) {
        if (bean.data != null && bean.data.size() > 0){
            list.addAll(bean.data);
        }
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreWorkData(MyWorkBean bean) {

    }

    @Override
    public void onShowToast(String s) {

    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onCompleted() {

    }
}
