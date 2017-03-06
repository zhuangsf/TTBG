package com.android.yybg.fragment;

import com.android.yybg.R;

import android.util.Log;
import android.view.View;



/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2016/10/24
 * 功能描述: 关心页面Fragment
 */
public class CareAboutFragment extends BaseFragment {
    private static final String TAG = CareAboutFragment.class.getSimpleName();

    @Override
    protected View initView() {
        Log.e(TAG, "关心页面Fragment页面被初始化了...");
        return View.inflate(mContext, R.layout.fragment_care_about, null);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "关心页面Fragment页面数据被初始化了...");
        super.initData();
    }

	@Override
	protected String getPageName() {
		return CareAboutFragment.class.getName();
	}
}
