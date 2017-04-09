package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import android.widget.TextView;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.SearchActivity;
import com.android.ttbg.adapter.AllGoodsContentsAdapterGrid;
import com.android.ttbg.adapter.AllGoodsContentsAdapterList;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.json.JsonControl;
import com.android.ttbg.tools.DataCleanManager;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.MyRadioButton;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableGridView;
import com.android.ttbg.view.PullableListView;
import com.finddreams.adbanner.ImagePagerAdapter;

public class AllGoodsFragment extends BaseFragment {
	private static final String TAG = AllGoodsFragment.class.getSimpleName();
	
	private static final int ALLGOODS_LIST_TYPE_LIST = 0;
	private static final int ALLGOODS_LIST_TYPE_GRID = 1;
	private View allGoodsFragment;
	private RadioGroup mRadioGroup;
	private String[] mStringLabel;
	private int[] mGoodsSortID;
	private PullableListView allgoods_content_listview;
	private PullableGridView allgoods_content_gridview;
	private AllGoodsContentsAdapterList allGoodsContentsAdapter;
	private AllGoodsContentsAdapterGrid allGoodsContentsAdapterGrid;
	
	private PullToRefreshLayout ptrl_list;
	private PullToRefreshLayout ptrl_grid;
	private boolean isFirstIn = true;
	private View search;
	private View icon_allgoods_listtype;
	private ImageView iv_allgoods_listtype;
	private int allgoods_listtype = ALLGOODS_LIST_TYPE_LIST;  //当前是list列表还是grid类型
	private int current_goods_type_index = 0;  //左侧商品类型序号
	private int allgoods_orderby = 0; //0 即将揭晓   1 人气  2 最新 3 价值低 4价值高
	private TextView tv_allgoods_announce;
	private TextView tv_allgoods_popular;
	private TextView tv_allgoods_newest;
	private TextView tv_allgoods_price;
	private ArrayList<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
	@Override
	protected View initView() {
		allGoodsFragment = View.inflate(mContext, R.layout.fragment_allgoods, null);
		mStringLabel = mContext.getResources().getStringArray(R.array.classify_string);
		allgoods_listtype = OperatingSP.getInt(mContext,OperatingSP.PREFERENCE_ALLGOODS_LIST_TYPE,OperatingSP.PREFERENCE_ALLGOODS_LIST_TYPE_DEFAULT);
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
			initTitle(allGoodsFragment);// 这个要比initAllGoodsContentView先初始化
			reflashRadioGroup();
			initAllGoodsContentView(allGoodsFragment);
		}
		
		new Thread(runnableAllGoods).start();
		
		return allGoodsFragment;
	}
	
	//最新揭晓
	Runnable runnableAllGoods = new Runnable(){
		  @Override
		  public void run() {
		    //
		    // TODO: http request.
		    //
			String orderflag = "30";
			String cateid = "0s";
			
			//orderflag	排序类型，30即将揭晓；40人气；20最新；50价值降序；60价值升序；80十元专区（替换限购类型）								
			if(allgoods_orderby == 0)
			{
				orderflag = "30";
			}else if(allgoods_orderby == 1)
			{
				orderflag = "40";
			}
			else if(allgoods_orderby == 2)
			{
				orderflag = "20";
			}
			else if(allgoods_orderby == 3)
			{
				orderflag = "50";
			}
			else if(allgoods_orderby == 4)
			{
				orderflag = "60";
			}

			JsonControl.httpGet(JsonControl.HOME_PAGE+"apps/ajax/getShopList/0/"+orderflag+"/0/30/1", mHandler,JsonControl.JSON_TYPE_ALLGOODS);
		  }
	};
	
    private Handler mHandler= new Handler()
	    {
	   		@Override
	   		public void handleMessage(Message msg) {
	   			switch (msg.what) {
	   			case JsonControl.GET_SUCCESS_MSG:
	   			{
	            	JSONObject jsonObject=(JSONObject)msg.obj;
	            	Utils.Log("GET_SUCCESS_MSG jsonObject:"+jsonObject+" msg.arg1 = "+msg.arg1);
	            	if(jsonObject == null)
	            	{
	            		return;
	            	}
	        		JSONObject result;
	        		switch(msg.arg1)
	        		{
	        		case JsonControl.JSON_TYPE_ALLGOODS:
	        		{
	        			try {
	            			result = new JSONObject(jsonObject.toString());
	            			Utils.Log("JsonControl.JSON_TYPE_ALLGOODS  result = "+result);
	            			
	            			if(result.toString().contains("连接不成功"))
	            			{
	            				return;
	            			}
	            			String bSuccess = result.optString("success","");
	            			Utils.Log("getJson JSON_TYPE_ALLGOODS bSuccess = "+bSuccess);
	            			
	            			if(bSuccess.toString().equals("0"))
	            			{
	            				//商品序号已经超出
	            				return;
	            			}
	            			
	            			String count = result.optString("count","");
	            			Utils.Log("getJson JSON_TYPE_ALLGOODS count = "+count);
	            			
	            			JSONArray shoplists = result.getJSONArray("shoplists");
	    	        		int len = shoplists.length();
	    	        		Utils.Log("getJson JSON_TYPE_ALLGOODS len = "+len);
	    	        		
	    	        		if(len == 0)
	    	        		{
	    	        			return;
	    	        		}
	    	        		
	    	        		hashMapList.clear();
	    	        		
	    	        		for(int i =0;i<len;i++){
	    	        			
	        	        		JSONObject obj = shoplists.getJSONObject(i);
	        	        		Utils.Log("getJson hashMapList["+i+"] = "+obj.toString());

	        	        		String id = obj.getString("id");
	        	        		String sid = obj.getString("sid");
	        	        		String cateid = obj.getString("cateid");
	        	        		String title = obj.getString("title");
	        	        		String title2 = obj.getString("title2");
	        	        		String qishu = obj.getString("qishu");
	        	        		String money = obj.getString("money");    	        		
	        	        		String yunjiage = obj.getString("yunjiage");
	        	        		String thumb = obj.getString("thumb");
	        	        		String brandid = obj.getString("brandid");
	        	        		String brandname = obj.getString("brandname");
	        	        		String zongrenshu = obj.getString("zongrenshu");
	        	        		String canyurenshu = obj.getString("canyurenshu");
	        	        		String shenyurenshu = obj.getString("shenyurenshu");
	        	        		
	    	                    GoodsProperty goodsRecommandItem = new GoodsProperty();
	    	                    goodsRecommandItem.setGoodsRecommandItemUrl(mContext, title, JsonControl.FILE_HEAD+thumb, Integer.parseInt(canyurenshu), Integer.parseInt(zongrenshu),Integer.parseInt(shenyurenshu),"价值:¥ "+money);
	    	                    hashMapList.add(goodsRecommandItem);
	    	        		}
	    	        		
	    	        			//一起刷新得了
	    	        			allGoodsContentsAdapter.setData(hashMapList);
	    	        			allGoodsContentsAdapter.notifyDataSetChanged();

	    	        			allGoodsContentsAdapterGrid.setData(hashMapList);
	    	        			allGoodsContentsAdapterGrid.notifyDataSetChanged();

	    	        	
	    	        		
	            			
	        			}catch (JSONException e) {
	         					// TODO Auto-generated catch block
	         					e.printStackTrace();
	         				}
	        		}
	        		break;
	        		default:
	        			break;
	        		}


	            	
	   			}
	   			break;
	   		  }
	   			
	   		}
	   	};
	
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

    	
		Utils.Log("initTitle allgoods_listtype = "+allgoods_listtype);
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
	        	Utils.Log("icon_allgoods_listtype onClick allgoods_listtype = "+allgoods_listtype);
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
	        	refrashContentListView();
	        }  
	  }); 
	}
	
	
	private void reflashOrderByTextColor()
	{
		tv_allgoods_announce.setTextColor(0xff666666);
		tv_allgoods_popular.setTextColor(0xff666666);
		tv_allgoods_newest.setTextColor(0xff666666);
		tv_allgoods_price.setTextColor(0xff666666);
		tv_allgoods_price.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.icon_allgoods_price_default), null);
		switch(allgoods_orderby)
		{
		case 0:
			tv_allgoods_announce.setTextColor(0xffff6600);
			break;
		case 1:
			tv_allgoods_popular.setTextColor(0xffff6600);
			break;
		case 2:
			tv_allgoods_newest.setTextColor(0xffff6600);
			break;
		case 3:
			tv_allgoods_price.setTextColor(0xffff6600);
			tv_allgoods_price.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.icon_allgoods_price_up), null);
			break;
		case 4:
			tv_allgoods_price.setTextColor(0xffff6600);
			tv_allgoods_price.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.icon_allgoods_price_down), null);
			break;
			default:
				tv_allgoods_announce.setTextColor(0xffff6600);
				break;
		}

	}
	
	private void initAllGoodsContentView(View v) {
		
		//allgoods_orderby
		tv_allgoods_announce = (TextView)v.findViewById(R.id.tv_allgoods_announce);
		tv_allgoods_announce.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	allgoods_orderby = 0;
	        	reflashOrderByTextColor();
	        }  
	    }); 
		tv_allgoods_popular = (TextView)v.findViewById(R.id.tv_allgoods_popular);
		tv_allgoods_popular.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	allgoods_orderby = 1;
	        	reflashOrderByTextColor();
	        }  
	    }); 
		tv_allgoods_newest = (TextView)v.findViewById(R.id.tv_allgoods_newest);
		tv_allgoods_newest.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	allgoods_orderby = 2;
	        	reflashOrderByTextColor();
	        }  
	    }); 
		tv_allgoods_price = (TextView)v.findViewById(R.id.tv_allgoods_price);
		tv_allgoods_price.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	if(allgoods_orderby == 3)
	        	{
	        		allgoods_orderby = 4;
	        	}
	        	else if(allgoods_orderby == 4)
	        	{
	        		allgoods_orderby = 3;
	        	}
	        	else
	        	{
	        		allgoods_orderby = 3;
	        	}
	        	reflashOrderByTextColor();
	        }  
	    }); 
		
		reflashOrderByTextColor();
		
		
		ptrl_grid = ((PullToRefreshLayout) v.findViewById(R.id.prl_allgoods_grid));
		ptrl_grid.setOnRefreshListener(new MyListener());
		ptrl_list = ((PullToRefreshLayout) v.findViewById(R.id.prl_allgoods_list));
		ptrl_list.setOnRefreshListener(new MyListener());
		allgoods_content_listview = (PullableListView) v.findViewById(R.id.allgoods_content_listview);
		allgoods_content_gridview = (PullableGridView) v.findViewById(R.id.allgoods_content_gridview);
		initContentListView();
	}
	private void initContentListView()
	{

        allGoodsContentsAdapter = new AllGoodsContentsAdapterList(getActivity());
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
		
		
        allGoodsContentsAdapterGrid = new AllGoodsContentsAdapterGrid(getActivity());
        allgoods_content_gridview.setAdapter(allGoodsContentsAdapterGrid);
        allgoods_content_gridview.setOnItemLongClickListener(new OnItemLongClickListener()
 		{

 			@Override
 			public boolean onItemLongClick(AdapterView<?> parent, View view,
 					int position, long id)
 			{
 				return true;
 			}
 		});
        allgoods_content_gridview.setOnItemClickListener(new OnItemClickListener()
 		{

 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id)
 			{
 			}
 		});
        
        refrashContentListView();
        
	}
	
	private void refrashContentListView()
	{
        if(allgoods_listtype == ALLGOODS_LIST_TYPE_LIST)
        {
        	ptrl_grid.setVisibility(View.GONE);
        	ptrl_list.setVisibility(View.VISIBLE);
        }
        else
        {
        	ptrl_grid.setVisibility(View.VISIBLE);
        	ptrl_list.setVisibility(View.GONE);
        }
	}
	
	private void reflashRadioGroup( ) {
		// TODO Auto-generated method stub
		mRadioGroup.removeAllViews();
		Utils.Log("reflashRadioGroup allgoods_listtype = "+allgoods_listtype);
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
					
					current_goods_type_index = checkedId;
					
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
				if (i == current_goods_type_index) {
					tempButton.setTextColor(0xffff7700);
					tempButton.setBackgroundColor(0xffffffff);
				} 
				mRadioGroup.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			}
			
			mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					current_goods_type_index = checkedId;
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
