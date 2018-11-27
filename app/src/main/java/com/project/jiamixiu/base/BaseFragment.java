package com.project.jiamixiu.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;


public abstract class BaseFragment extends Fragment {

	protected Context mContext;//上下文
	
	protected List<BaseFragment> fragmentList= new ArrayList<>();//存放fragment的集合
	
	protected FragmentManager fragmentManager= getFragmentManager();//fragment管理器

	@Override
	public void onAttach(Context context) {
		mContext=context;
		super.onAttach(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 初始化布局
	 */
	public abstract View initView();
	
	/**
	 * 初始化数据
	 */
	public abstract void initData();
//	protected BaseActivity getBaseActivity(){
//		return (BaseActivity) getActivity();
//	}

	public void onDoSomething() {}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//OkhttpUtils.getInstance().cancelTag(this);
	}
}
