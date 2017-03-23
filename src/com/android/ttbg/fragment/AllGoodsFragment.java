package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextSwitcher;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.AllGoodsContentsAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.tools.DataCleanManager;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsRecommandItem;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableListView;

public class AllGoodsFragment extends BaseFragment {
	private static final String TAG = AllGoodsFragment.class.getSimpleName();
	private View allGoodsFragment;
	private RadioGroup mRadioGroup;
	private String[] mStringLabel;
	private PullableListView allgoods_content_listview;
	private PullToRefreshLayout ptrl;
	private boolean isFirstIn = true;
	
	@Override
	protected View initView() {
		allGoodsFragment = View.inflate(mContext, R.layout.fragment_allgoods, null);
		mStringLabel = getActivity().getResources().getStringArray(R.array.classify_string);
		if (allGoodsFragment != null) {
			initRadioGroup(allGoodsFragment);
			initAllGoodsContentView(allGoodsFragment);
		}
		
		return allGoodsFragment;
	}
	private void initAllGoodsContentView(View v) {
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.prl_allgoods));
		ptrl.setOnRefreshListener(new MyListener());
		allgoods_content_listview = (PullableListView) v.findViewById(R.id.allgoods_content_listview);
		initContentListView();
	}
	private void initContentListView()
	{
	   	List<GoodsRecommandItem> hashMapList = new ArrayList<GoodsRecommandItem>();
        //测试数据
        for (int i = 0; i < 8; i++) {

            GoodsRecommandItem goodsRecommandItem = new GoodsRecommandItem();
            goodsRecommandItem.setGoodsRecommandItem(getActivity(), "(第"+i+"云)红米4 16G 全网通 标准版 4G手机 只要一元啦 一元啦一元啦", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        AllGoodsContentsAdapter allGoodsContentsAdapter = new AllGoodsContentsAdapter(getActivity(), hashMapList);

        allgoods_content_listview.setAdapter(allGoodsContentsAdapter);
		allgoods_content_listview.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
		allgoods_content_listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
			}
		});
	}
	
	private void initRadioGroup(View v) {
		// TODO Auto-generated method stub
		mRadioGroup = (RadioGroup) v.findViewById(R.id.rg_allgoods_classify);

		for (int i = 0; i < mStringLabel.length; i++) {
			RadioButton tempButton = new RadioButton(getActivity());
			tempButton.setBackgroundColor(0xfff5f5f5); // 设置RadioButton的背景图片
			// tempButton.setButtonDrawable(R.drawable.xxx); // 设置按钮的样式
			tempButton.setPadding(20, 25, 20, 25); // 设置文字距离按钮四周的距离
			tempButton.setText(mStringLabel[i]);
			tempButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tempButton.setTextColor(0xff666666);

			tempButton.setButtonDrawable(android.R.color.transparent);
			if (i == 0) {
				tempButton.setTextColor(0xffff7700);
				tempButton.setBackgroundColor(0xffffffff);
			} 
			mRadioGroup.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		}

		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mStringLabel.length; i++) {
					RadioButton tempButton = (RadioButton) mRadioGroup.findViewById(i);
					
					Utils.Log("checkedId = "+checkedId);
					if (tempButton != null) {
						if (i == checkedId) {
							tempButton.setTextColor(0xffff7700);
							tempButton.setBackgroundColor(0xffffffff);
						} else {
							tempButton.setTextColor(0xff666666);
							tempButton.setBackgroundColor(0xfff5f5f5);
						}
					}

				}

			}
		});

	}

	@Override
	protected void initData() {
		Log.e(TAG, "视频页面Fragment页面数据被初始化了...");
		super.initData();
	}

	@Override
	protected String getPageName() {
		return AllGoodsFragment.class.getName();
	}

}
