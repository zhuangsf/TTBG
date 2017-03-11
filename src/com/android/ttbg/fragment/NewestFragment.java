package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextSwitcher;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.AllGoodsContentsAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.view.GoodsRecommandItem;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableGridView;
import com.android.ttbg.view.PullableListView;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2016/10/24
 * 功能描述: 个人页面Fragment
 */
public class NewestFragment extends BaseFragment {
    private static final String TAG = NewestFragment.class.getSimpleName();
    private View newestFragmentView;
    private PullableGridView newestGridView;
	private PullToRefreshLayout ptrl;
    @Override
    protected View initView() {
        Log.e(TAG, "个人页面Fragment页面被初始化了...");
        newestFragmentView = View.inflate(mContext, R.layout.fragment_newest,null);
        
        
        if(newestFragmentView != null)
        {
        	intiNewestGoodsItems(newestFragmentView);
        //	initAllGoodsContentView(newestFragmentView);
        }
        return newestFragmentView;
    }

    
    private void intiNewestGoodsItems(View v) {
  
    	
   	newestGridView = (PullableGridView) v.findViewById(R.id.gridview_newest);
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.ptr_newest_refresh));
		ptrl.setOnRefreshListener(new MyListener());
    	List<GoodsRecommandItem> hashMapList = new ArrayList<GoodsRecommandItem>();
        //测试数据
        for (int i = 0; i < 18; i++) {

            GoodsRecommandItem goodsRecommandItem = new GoodsRecommandItem();
            goodsRecommandItem.setGoodsRecommandItem(getActivity(), "测试测    "+i+"   试测试", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        NewestGoodsAdapter newestGoodsAdapter = new NewestGoodsAdapter(getActivity(), hashMapList);

        newestGridView.setAdapter(newestGoodsAdapter);
        newestGridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
        newestGridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

			}
		});
    }
    
    @Override
    protected void initData() {
        Log.e(TAG, "个人页面Fragment页面数据被初始化了...");
        super.initData();
    }

    
    
    @Override
   	protected String getPageName() {
   		return NewestFragment.class.getName();
   	}
}
