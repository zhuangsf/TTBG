package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.android.ttbg.*;
import com.android.ttbg.R;
import com.android.ttbg.SearchActivity;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.view.AddPopWindow;
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
		if (CountFragment != null) {
			View account_record = (View)CountFragment.findViewById(R.id.account_record);  
			
			account_record.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), HistoryActivity.class);
						startActivity(intent);
					}
				});
			
			
		   View account_obtain_goods = (View)CountFragment.findViewById(R.id.account_obtain_goods);  
		   account_obtain_goods.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), ObtainGoodsActivity.class);
					startActivity(intent);
				}
			});
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
