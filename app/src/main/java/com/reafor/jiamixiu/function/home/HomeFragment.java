package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.reafor.jiamixiu.MainActivity;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.bean.TabBeanResponse;
import com.reafor.jiamixiu.function.home.prenster.HomePrenster;
import com.reafor.jiamixiu.function.home.view.IhomeView;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment  extends BaseFragment implements IhomeView{
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    private HomePrenster prenster;
    private LoadingDialog loadingDialog;
    private List<Fragment> mFragments;
    private List<TabBeanResponse.TabBean> tabList;
    private MyPagerAdapter pagerAdapter;
    private MainActivity parentActivity;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home_layout,null);
        ButterKnife.bind(this,view);
        loadingDialog = new LoadingDialog(mContext);
        prenster = new HomePrenster(this);
        loadingDialog.show();
        prenster.acquireTabs();
        parentActivity = (MainActivity) getActivity();
        initEvent();
        return view;
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                viewPager.setCurrentItem(pos);
                BaseFragment fragment = (BaseFragment) mFragments.get(pos);
                if (pos == 0){
                    fragment.updateList("");
                } else {
                    pos--;
                    fragment.updateList(tabList.get(pos).fid);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tv_cancel.setOnClickListener(view-> parentActivity.drawerLayout.openDrawer(Gravity.RIGHT));
        et_search.setOnClickListener(view->startActivity(new Intent(mContext,SearchVideoActivity.class)));
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
    }

    @Override
    public void getTabsSuccess(List<TabBeanResponse.TabBean> listTab) {
        for (int i = 0; i < listTab.size() + 1; i++) {
            ItemFragment itemFragment = new ItemFragment();
            if (i == 0){
                itemFragment.setTag("");
            } else {
                itemFragment.setTag(listTab.get(i-1).sortid);
            }
            mFragments.add(itemFragment);
        }
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabList = listTab;
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        for (TabBeanResponse.TabBean bean : listTab) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.name));
        }
        prenster.setTabWidth(tabLayout,20);
        //tabLayout.getTabAt(0).select();
    }

    @Override
    public void getTabsFail(String mess) {
        ToastUtil.showTosat(mContext,mess);
        loadingDialog.dismiss();
    }

    @Override
    public void requestFinish() {
        loadingDialog.dismiss();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}
