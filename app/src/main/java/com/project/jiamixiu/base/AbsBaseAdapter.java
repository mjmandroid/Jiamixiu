package com.project.jiamixiu.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsBaseAdapter<T> extends RecyclerView.Adapter<AbsBaseAdapter.Holder> {

    protected Context mContext;
    protected List<T> datas = new ArrayList<>();
    private int[] mLayoutid;

    public List<T> getDatas() {
        return datas;
    }

    public AbsBaseAdapter(Context mContext, int ...mLayoutid) {
        this.mContext = mContext;
        this.mLayoutid = mLayoutid;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public void addAllData(List<T> mDatas){
        this.datas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addData(T mData){
        this.datas.add(mData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutid[viewType],parent,false);
        //View view = View.inflate(mContext,mLayoutid[viewType],null);
        Holder holder = new Holder(view);
        return holder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        bindView(holder,datas.get(position),position);
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        T itemData = datas.get(position);
        Class clazz  = itemData.getClass();
        try {
            Field field = clazz.getDeclaredField("type");
            if (field != null){
               type = field.getInt(itemData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }

    protected abstract void bindView(Holder holder, T item, int position);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews = new SparseArray<>();
        private View mConvertView;
        public Holder(View itemView) {
            super(itemView);
            this.mConvertView = itemView;
        }
        public <V extends View> V getView(@IdRes int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return (V) view;
        }
    }
}
