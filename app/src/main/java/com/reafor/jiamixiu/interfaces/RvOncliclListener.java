package com.reafor.jiamixiu.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface RvOncliclListener<T> {
    void onCick(RecyclerView.ViewHolder viewHolder, T item, int position);
}
