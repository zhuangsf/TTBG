package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextSwitcher;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.SearchActivity;
import com.android.ttbg.adapter.AllGoodsContentsAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.tools.DataCleanManager;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.MyRadioButton;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableListView;

public class AllGoodsFragment extends BaseFragment {
	private static final String TAG = AllGoodsFragment.class.getSimpleName();
	
	private static final int ALLGOODS_LIST_TYPE_LIST = 0;
	private static final int ALLGOODS_LIST_TYPE_GRID = 1;
	private View allGoodsFragment;
	private RadioGroup mRadioGroup;
	private String[] mStringLabel;
	private int[] mGoodsSortID;
	private PullableListView allgoods_content_listview;
	private PullToRefreshLayout ptrl;
	private boolean isFirstIn = true;
	private View search;
	private View icon_allgoods_listtype;
	private ImageView iv_allgoods_listtype;
	private int allgoods_listtype = ALLGOODS_LIST_TYPE_LIST;  //当前是list列表还是grid类型
	private int current_goods_type_index = 0;  //左侧商品类型序号
	@Override
	protected View initView() {
		allGoodsFragment = View.inflate(mContext, R.layout.fragment_allgoods, null);
		mStringLabel = mContext.getResources().getStringArray(R.array.classify_string);
		
		TypedArray ar = mContext.getResources().obtainTypedArray(R.array.allgood_sort_id);
		int len = ar.length();     
		mGoodsSortID = new int[len];     
		for (int i = 0; i < len; i++)     
		{
			mGoodsSortID[i] = ar.getResourceId(i, 0);
		}
		ar.recycle();
		
		//mGoodsSortID  = getActivity().getResources().getIntArray(R.array.allgood_sort_id);
		mRadioGroup = (RadioGroup) allGoodsFragment.findViewById(R.id.rg_allgoods_classify);
		if (allGoodsFragment != null) {
			reflashRadioGroup();
			initAllGoodsContentView(allGoodsFragment);
			initTitle(allGoodsFragment);
			

			 
		}
		
		
		
		return allGoodsFragment;
	}
	private void initTitle(View v) {
		// TODO Auto-generated method stub
		search = (View) v.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(mContext, SearchActivity.class);
					startActivity(intent);
		        }  
		  }); 
		iv_allgoods_listtype = (ImageView) v.findViewById(R.id.iv_allgoods_listtype);
		allgoods_listtype = OperatingSP.getInt(mContext,OperatingSP.PREFERENCE_ALLGOODS_LIST_TYPE,OperatingSP.PREFERENCE_ALLGOODS_LIST_TYPE_DEFAULT);
    	if(allgoods_listtype == ALLGOODS_LIST_TYPE_LIST)
    	{
    		iv_allgoods_listtype.setImageResource(R.drawable.icon_allgoods_list);
    	}
    	else
    	{
    		iv_allgoods_listtype.setImageResource(R.drawable.icon_allgoods_grid);
    	}
		icon_allgoods_listtype = (View) v.findViewById(R.id.icon_allgoods_listtype);
		icon_allgoods_listtype.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	if(allgoods_listtype == ALLGOODS_LIST_TYPE_LIST)
	        	{
	        		allgoods_listtype = ALLGOODS_LIST_TYPE_GRID;
	        		iv_allgoods_listtype.setImageResource(R.drawable.icon_allgoods_grid);
	        		
	        	}
	        	else
	        	{
	        		allgoods_listtype = ALLGOODS_LIST_TYPE_LIST;
	        		iv_allgoods_listtype.setImageResource(R.drawable.icon_allgoods_list);
	        	}
	        	//保存类型
	        	OperatingSP.setInt(mContext,OperatingSP.PREFERENCE_ALLGOODS_LIST_TYPE,allgoods_listtype);
	        	reflashRadioGroup();
	        }  
	  }); 
	}
	private void initAllGoodsContentView(View v) {
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.prl_allgoods));
		ptrl.setOnRefreshListener(new MyListener());
		allgoods_content_listview = (PullableListView) v.findViewById(R.id.allgoods_content_listview);
		initContentListView();
	}
	private void initContentListView()
	{
	   	List<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
        //测试数据
        for (int i = 0; i < 8; i++) {

            GoodsProperty goodsRecommandItem = new GoodsProperty();
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
	
	private void reflashRadioGroup( ) {
		// TODO Auto-generated method stub
		mRadioGroup.removeAllViews();
		if(allgoods_listtype == ALLGOODS_LIST_TYPE_GRID)
		{      
			for (int i = 0; i < mStringLabel.length; i++) {
				MyRadioButton tempButton = new MyRadioButton(getActivity());
				tempButton.setBackgroundColor(0xfff5f5f5); // 设置RadioButton的背景图片
				// tempButton.setButtonDrawable(R.drawable.xxx); // 设置按钮的样式
				tempButton.setPadding(10, 25, 10, 25); // 设置文字距离按钮四周的距离
				tempButton.setText(mStringLabel[i]);
				tempButton.setGravity(Gravity.CENTER);
				tempButton.setId(i);
				tempButton.setLines(2);
				tempButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);  
				tempButton.setTextColor(0xff666666);
				tempButton.setWidthHeight(36,36);
				tempButton.setDrawableTop(getActivity().getResources().getDrawable(mGoodsSortID[i]));
				tempButton.setCompoundDrawablesWithIntrinsicBounds(null, getActivity().getResources().getDrawable(mGoodsSortID[i]), null, null);
				tempButton.setButtonDrawable(android.R.color.transparent);
				if (i == current_goods_type_index) {
					tempButton.setTextColor(0xffff7700);  
					tempButton.setBackgroundColor(0xffffffff);
					tempButton.setChecked(true);
				} 
				
				mRadioGroup.addView(tempButton, 66, LinearLayout.LayoutParams.WRAP_CONTENT);
			}
			
			mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					for (int i = 0; i < mStringLabel.length; i++) {
						MyRadioButton tempButton = (MyRadioButton) mRadioGroup.findViewById(i);
						
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
		else
		{
			for (int i = 0; i < mStringLabel.length; i++) {
				RadioButton tempButton = new RadioButton(getActivity());
				tempButton.setBackgroundColor(0xfff5f5f5); // 设置RadioButton的背景图片
				// tempButton.setButtonDrawable(R.drawable.xxx); // 设置按钮的样式
				tempButton.setPadding(20, 25, 20, 25); // 设置文字距离按钮四周的距离
				tempButton.setText(mStringLabel[i]);
				tempButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				tempButton.setTextColor(0xff666666);
				tempButton.setId(i);
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
