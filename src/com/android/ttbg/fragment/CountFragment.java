package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.view.GoodsRecommandItem;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;



public class CountFragment extends BaseFragment {
    private static final String TAG = CountFragment.class.getSimpleName();
	private View CountFragment;
	private PullToRefreshLayout ptrl;
    @Override
    protected View initView() {
    	CountFragment = View.inflate(mContext, R.layout.fragment_account, null);
    //	CountFragment = View.inflate(mContext, R.layout.fragment_menu, null);
		if (CountFragment != null) {

		}
		return CountFragment;
    }



	@Override
    protected void initData() {
        Log.e(TAG, "CountFragment  initData...");
        super.initData();
    }

	@Override
	protected String getPageName() {
		return CountFragment.class.getName();
	}
}
