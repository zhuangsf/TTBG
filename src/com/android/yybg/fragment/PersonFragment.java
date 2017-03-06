package com.android.yybg.fragment;

import android.util.Log;
import android.view.View;

import com.android.yybg.R;
import com.android.yybg.fragment.BaseFragment;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2016/10/24
 * 功能描述: 个人页面Fragment
 */
public class PersonFragment extends BaseFragment {
    private static final String TAG = PersonFragment.class.getSimpleName();

    @Override
    protected View initView() {
        Log.e(TAG, "个人页面Fragment页面被初始化了...");
        return View.inflate(mContext, R.layout.fragment_person,null);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "个人页面Fragment页面数据被初始化了...");
        super.initData();
    }

    
    
    @Override
   	protected String getPageName() {
   		return PersonFragment.class.getName();
   	}
}
