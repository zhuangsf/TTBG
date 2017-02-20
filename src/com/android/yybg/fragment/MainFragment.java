package com.android.yybg.fragment;


import com.android.yybg.R;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2016/10/24
 * 功能描述: 首页页面的Fragment
 */
public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private View mainFragmentView;

    @Override
    protected View initView() {
        //Log.e(TAG, "首页页面Fragment页面被初始化了...");
        mainFragmentView = View.inflate(mContext, R.layout.fragment_main, null);
        return mainFragmentView;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
