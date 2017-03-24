package com.android.ttbg.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.android.ttbg.R;
import com.android.ttbg.SearchActivity;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.json.JsonControl;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.TimerUtil;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.AddPopWindow;
import com.android.ttbg.view.GoodsRecommandItem;
import com.android.ttbg.view.NoScroolGridView;
import com.finddreams.adbanner.ImagePagerAdapter;
import com.finddreams.bannerview.CircleFlowIndicator;
import com.finddreams.bannerview.ViewFlow;

//todo


// 还需要写一个在当前界面打开网络的接口


public class MainFragment extends BaseFragment implements ViewFactory,OnClickListener{
    private static final String TAG = MainFragment.class.getSimpleName();
    private View mainFragmentView;
    private AsyncImageLoader imageLoader;
    
	private ViewFlow mViewFlow;     //广告页控件
	private CircleFlowIndicator mFlowIndicator;   //指示点点
	private ArrayList<String> imageUrlList = new ArrayList<String>();
	ArrayList<String> linkUrlArray= new ArrayList<String>();
    
    private ImageView count1_image;
    private ImageView count2_image;
    private ImageView count3_image;
    private ImageView count4_image;
    private TextView count1_time;
    private TextView count2_time;
    private TextView count3_time;
    private TextView count4_time;
    
    private TextSwitcher switcher;
    
    private View searchButton;
    private PopupMenu popupMenu;
    
    private static final int MSG_TEST_SWITCHER_TEST=0;

    private ImageView add_menus;
    
    private Handler mHandler= new Handler()
    {
   		@Override
   		public void handleMessage(Message msg) {
   			switch (msg.what) {
   			case MSG_TEST_SWITCHER_TEST:
   			{
   				if(switcher != null)
   				{
   		            int fstart,fend;
   		            
   		            String hintText = "恭喜你中了"+String.valueOf(new Random().nextInt())+"万元,得瑟去吧";
   		            fstart="恭喜你中了".length();  
   		            fend=("恭喜你中了"+String.valueOf(new Random().nextInt())).length(); 
   		            SpannableStringBuilder style=new SpannableStringBuilder(hintText);     
   		            style.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   

   					switcher.setText(style);  
   				}
   				Message new_msg = new Message();
   				new_msg.what = MSG_TEST_SWITCHER_TEST;
   				mHandler.sendMessageDelayed(new_msg, 2000);
   			}
   			break;
   			case JsonControl.GET_SUCCESS_MSG:
   			{
            	JSONObject jsonObject=(JSONObject)msg.obj;
            	Utils.Log("login success jsonObject:"+jsonObject);
            	if(jsonObject == null)
            	{
            		return;
            	}
        		JSONObject result;
        		switch(msg.arg1)
        		{
        		case JsonControl.JSON_TYPE_BANNER:
        		{
    				try {
    					result = new JSONObject(jsonObject.toString());
    	        		String bSuccess = result.optString("success","");
    	        		
    	        		Utils.Log("getJson bSuccess "+bSuccess);
    	        		
    	        		
    	        		//这个变量实际没什么用,图片下载已经用本地缓存来判断,如果文件名改变,会自动下载,否则用缓存的图片
    	        		String lastTime = result.optString("lasttime","");
    	        		
    	        		Utils.Log("getJson lastTime "+lastTime);
    	        		JSONArray banners = result.getJSONArray("banners");
    	        		int len = banners.length();
    	        		
    	        		imageUrlList.clear();
    	        		linkUrlArray.clear();
    	        		
    	        		for(int i =0;i<len;i++){
    	        		JSONObject obj = banners.getJSONObject(i);
    	        		
    	        		String title = obj.getString("title");
    	        		String link = obj.getString("link");
    	        		String img = obj.getString("img");
    	        		
    	        		imageUrlList.add(img);
    	        		linkUrlArray.add(link);
    	        		Utils.Log("getJson banners["+i+"].title = "+title);
    	        		Utils.Log("getJson banners["+i+"].link = "+link);
    	        		Utils.Log("getJson banners["+i+"].img = "+img);
    	      
    	        		}
    	        		
    	        		//放在这里,如果没网络,那么会显示为空
    	        		if(mViewFlow != null)
    	        		{
	    	        		mViewFlow.setAdapter(new ImagePagerAdapter(mContext, imageUrlList,
	    	        				linkUrlArray).setInfiniteLoop(true));
	    	        		mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
	    	        		mViewFlow.startAutoFlowTimer(); // 启动自动播放
    	        		}
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
        		}
        			break;
        			
        			
        		case JsonControl.JSON_TYPE_NEWEST:
        		{
        			try {
        			result = new JSONObject(jsonObject.toString());
        			Utils.Log("JsonControl.JSON_TYPE_NEWEST  result = "+result);
        			} catch (JSONException e) {
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
   	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = new AsyncImageLoader(mContext);  
    }
   	
   	
    @Override
    protected View initView() {
        //Log.e(TAG, "首页页面Fragment页面被初始化了...");
        mainFragmentView = View.inflate(mContext, R.layout.fragment_main, null);
        
        if(mainFragmentView != null)
        {
        	initSearchView(mainFragmentView); 
        	initADPager(mainFragmentView);      //初始化页眉广告条
        	initNewestSwitcher(mainFragmentView);
        	initCountDownPage(mainFragmentView);
        	intiGoodsItems(mainFragmentView);
        }
        
        

        

        
        return mainFragmentView;
    }

    
    private void initSearchView(View v) {
		// TODO Auto-generated method stub
    	LinearLayout searchButton = (LinearLayout)v.findViewById(R.id.title_search);  
		searchButton.setOnClickListener(this);
		
		
		 add_menus = (ImageView) v.findViewById(R.id.add_menus);
		 add_menus.setOnClickListener(this);
	}

    
	@Override
	public void onClick(View v) {
       if (v.getId() == R.id.title_search) {
			Intent intent = new Intent(mContext, SearchActivity.class);
			startActivity(intent);
		}
       else if(v.getId() == R.id.add_menus)
       {
			AddPopWindow addPopWindow = new AddPopWindow(getActivity());
			addPopWindow.showPopupWindow(add_menus);
       }
	}
    
	private void initNewestSwitcher(View v) {
    	switcher = (TextSwitcher) v.findViewById(R.id.ts_newest_info);  
    	switcher.setFactory(this); 
    	
    	switcher.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));  
        // 设置切出动画  
    	switcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_up));  
    	
		Message msg = new Message();
		msg.what = MSG_TEST_SWITCHER_TEST;
		mHandler.sendMessageDelayed(msg, 2000);
		
		
        new Thread(new Runnable() {
			@Override
			public void run() {
				//读取最新揭晓
				JsonControl.httpGet(JsonControl.HOME_PAGE+"apps/ajax/getLotteryList/0/0/10/1", mHandler,JsonControl.JSON_TYPE_NEWEST);
			}
		}).start();
		
    }
    
    
    @Override  
    public void onStop()
    {
    	super.onStop();
    	mHandler.removeCallbacksAndMessages(null);
    }
    
    @Override  
    public View makeView() {   
        TextView textView = new TextView(mContext);   
        textView.setSingleLine(true);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(0xff222222);  
        textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        return textView;   
    }  
    
    private void intiGoodsItems(View v) {
    	NoScroolGridView gridView = (NoScroolGridView) v.findViewById(R.id.gridview_recommend);
    	
    	List<GoodsRecommandItem> hashMapList = new ArrayList<GoodsRecommandItem>();
        //测试数据
        for (int i = 0; i < 8; i++) {

            GoodsRecommandItem goodsRecommandItem = new GoodsRecommandItem();
            goodsRecommandItem.setGoodsRecommandItem(mContext, "测试测    "+i+"   试测试", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        GoodsRecommendAdapter goodsRecommendAdapter = new GoodsRecommendAdapter(mContext, hashMapList);

        gridView.setAdapter(goodsRecommendAdapter);
    }
    
    
    
    
    private void initCountDownPage(View v) {
        count1_image = (ImageView) v.findViewById(R.id.count1_image);
        count2_image = (ImageView) v.findViewById(R.id.count1_image);
        count3_image = (ImageView) v.findViewById(R.id.count1_image);
        count4_image = (ImageView) v.findViewById(R.id.count1_image);
        count1_time = (TextView) v.findViewById(R.id.count1_time);
        count2_time = (TextView) v.findViewById(R.id.count2_time);
        count3_time = (TextView) v.findViewById(R.id.count3_time);
        count4_time = (TextView) v.findViewById(R.id.count4_time);
        
     
        CountDownTimer timer1 = new CountDownTimer(180000, 35) {
          @Override
          public void onTick(long millisUntilFinished) {
        	  count1_time.setText(TimerUtil.stringForTime(millisUntilFinished));
          }
          @Override
          public void onFinish() {
        	 count1_time.setText("正在开奖");
          }
        };
     
        
        CountDownTimer timer2 = new CountDownTimer(170000, 35) {
            @Override
            public void onTick(long millisUntilFinished) {
          	  count2_time.setText(TimerUtil.stringForTime(millisUntilFinished));
            }
            @Override
            public void onFinish() {
          	 count2_time.setText("正在开奖");
            }
          };
  
          
          CountDownTimer timer3 = new CountDownTimer(160000, 35) {
              @Override
              public void onTick(long millisUntilFinished) {
            	  count3_time.setText(TimerUtil.stringForTime(millisUntilFinished));
              }
              @Override
              public void onFinish() {
            	 count3_time.setText("正在开奖");
              }
            };

            
            CountDownTimer timer4 = new CountDownTimer(150000, 35) {
                @Override
                public void onTick(long millisUntilFinished) {
              	  count4_time.setText(TimerUtil.stringForTime(millisUntilFinished));
                }
                @Override
                public void onFinish() {
              	 count4_time.setText("正在开奖");
                }
              };
              timer1.start();
              timer2.start();
              timer3.start();
              timer4.start();
    }

    private void initADPager(View v) {

		mViewFlow = (ViewFlow) v.findViewById(R.id.viewflow);
		mFlowIndicator = (CircleFlowIndicator) v.findViewById(R.id.viewflowindic);
		mViewFlow.setFlowIndicator(mFlowIndicator);
		mViewFlow.setTimeSpan(4500);
		mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置

        new Thread(new Runnable() {
			@Override
			public void run() {
				//读取服务器广告条数据
				JsonControl.httpGet(JsonControl.HOME_PAGE+"apps/ajax/getBanner", mHandler,JsonControl.JSON_TYPE_BANNER);
			}
		}).start();
		
    }    

    
    @Override
    protected void initData() {
        super.initData();
    }
    

    
   
    
    
    @Override
	protected String getPageName() {
		return MainFragment.class.getName();
	}
}

