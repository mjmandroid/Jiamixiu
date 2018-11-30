package com.project.jiamixiu.function.person;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.jiamixiu.R;

public class VideoFragment extends Fragment {
    ListView lvVideo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,null);
        lvVideo = (ListView)view.findViewById(R.id.lv_video);
        return view;
    }


}
