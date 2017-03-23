package com.android.ttbg.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.android.ttbg.R;
import com.android.ttbg.SearchActivity;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.TimerUtil;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.AddPopWindow;
import com.android.ttbg.view.GoodsRecommandItem;
import com.android.ttbg.view.NoScroolGridView;




public class MainFragment extends BaseFragment implements ViewFactory,OnClickListener{
    private static final String TAG = MainFragment.class.getSimpleName();
    private View mainFragmentView;

    private ViewPager adViewPager;
    private LinearLayout pagerLayout;
    private List<View> pageViews;
    private ImageView[] imageViews;
    private ImageView imageView;
    private AdPageAdapter adapter;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private boolean isContinue = true;   
    
    ImageView ad_image1;
    ImageView ad_image2;
    ImageView ad_image3;
    ImageView ad_image4;
    
    
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
   			
   			}
   		}
   	};
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
			Intent intent = new Intent(getActivity(), SearchActivity.class);
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
    	
    	switcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom));  
        // 设置切出动画  
    	switcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_up));  
    	
		Message msg = new Message();
		msg.what = MSG_TEST_SWITCHER_TEST;
		mHandler.sendMessageDelayed(msg, 2000);
    }
    
    
    @Override  
    public void onStop()
    {
    	super.onStop();
    	mHandler.removeCallbacksAndMessages(null);
    }
    
    @Override  
    public View makeView() {   
        TextView textView = new TextView(getActivity());   
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
            goodsRecommandItem.setGoodsRecommandItem(getActivity(), "测试测    "+i+"   试测试", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        GoodsRecommendAdapter goodsRecommendAdapter = new GoodsRecommendAdapter(getActivity(), hashMapList);

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
    	
    	Context context = getActivity();
    	
        // 从布局文件中获取ViewPager父容器
        pagerLayout = (LinearLayout) v.findViewById(R.id.view_pager_content);
        // 创建ViewPager
        adViewPager = new ViewPager(getActivity());
        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 根据屏幕信息设置ViewPager广告容器的宽高
        adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
                dm.heightPixels * 2 / 5));
        // 将ViewPager容器设置到布局文件父容器中
        pagerLayout.addView(adViewPager);
        initPageAdapter(v);
        adViewPager.setAdapter(adapter);
        adViewPager.setOnPageChangeListener(new AdPageChangeListener());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(atomicInteger.get());
                        atomicOption();
                    }
                }
            }
        }).start();
        
        
/*        String imgUrl = "http://i03.pic.sogou.com/1223d97ec8923cfd";  
        
        //for test  
        AsyncImageLoader loader = new AsyncImageLoader(context);  
          
        //将图片缓存至外部文件中  
        loader.setCache2File(true);
        //设置外部缓存文件夹  
        
        Utils.Log("context.getCacheDir().getAbsolutePath() = "+context.getCacheDir().getAbsolutePath());
        
        loader.setCachedDir(context.getCacheDir().getAbsolutePath());  
          
        //下载图片，第二个参数是否缓存至内存中  
        loader.downloadImage(imgUrl,new AsyncImageLoader.ImageCallback() {  
            @Override  
            public void onImageLoaded(Bitmap bitmap, String imageUrl) {  
                if(bitmap != null){  
                //	ad_image1.setImageBitmap(bitmap);	
                }else{  
                    //下载失败，设置默认图片  
                }  
            }  
        });  */
        
    }    
    
    private void atomicOption() {
    	
    	if(imageViews == null)
    	{
    		return;
    	}
    	
        atomicInteger.incrementAndGet();
        if (atomicInteger.get() > imageViews.length - 1) {
            atomicInteger.getAndAdd(-5);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
 
        }
    }
    /*
     * 每隔固定时间切换广告栏图片
     */
    private final Handler viewHandler = new Handler() {
 
        @Override
        public void handleMessage(Message msg) {
            adViewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
 
    };
    
    @Override
    protected void initData() {
        super.initData();
    }
    
    private void initPageAdapter(View v) {
        pageViews = new ArrayList<View>();
        adapter = new AdPageAdapter(pageViews);
        
        ad_image1 = new ImageView(getActivity());
        ad_image1.setBackgroundResource(R.drawable.welcome1);
        pageViews.add(ad_image1);
        ad_image2 = new ImageView(getActivity());
        ad_image2.setBackgroundResource(R.drawable.welcome2);
        pageViews.add(ad_image2);
        ad_image3 = new ImageView(getActivity());
        ad_image3.setBackgroundResource(R.drawable.welcome3);
        pageViews.add(ad_image3);
        ad_image4 = new ImageView(getActivity());
        ad_image4.setBackgroundResource(R.drawable.welcome4);
        pageViews.add(ad_image4);
        adapter = new AdPageAdapter(pageViews);
        
        initCirclePoint(v);
    }
 
    private void initCirclePoint(View v) {
        ViewGroup group = (ViewGroup) v.findViewById(R.id.viewGroup);
        group.removeAllViews();
        
        if(pageViews.size() == 0)
        {
        	return;
        }
        imageViews = new ImageView[pageViews.size()];
        // 广告栏的小圆点图标
        for (int i = 0; i < pageViews.size(); i++) {
            // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
            imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15,15);
            lp.setMargins(18, 0,18, 0);
            imageView.setLayoutParams(lp);
            imageViews[i] = imageView;
            // 初始值, 默认第0个选中
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.dot01);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.dot02);
            }
            // 将小圆点放入到布局中
            group.addView(imageViews[i]);
        }
    }    
    
    
    /**
     * ViewPager 页面改变监听器
     */
    private final class AdPageChangeListener implements OnPageChangeListener {
 
        /**
         * 页面滚动状态发生改变的时候触发
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
 
        /**
         * 页面滚动的时候触发
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
 
        /**
         * 页面选中的时候触发
         */
        @Override
        public void onPageSelected(int arg0) {
            // 获取当前显示的页面是哪个页面
            atomicInteger.getAndSet(arg0);
            // 重新设置原点布局集合
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.dot01);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.dot02);
                }
            }
        }
    }
 
    private final class AdPageAdapter extends PagerAdapter {
        private List<View> views = null;
 
        /**
         * 初始化数据源, 即View数组
         */
        public AdPageAdapter(List<View> views) {
            this.views = views;
        }
 
        
        public void setData(List<View> views) {
            this.views = views;
        }
        
        /**
         * 从ViewPager中删除集合中对应索引的View对象
         */
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }
 
        /**
         * 获取ViewPager的个数
         */
        @Override
        public int getCount() {
            return views.size();
        }
 
        /**
         * 从View集合中获取对应索引的元素, 并添加到ViewPager中
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }
 
        /**
         * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联 这个方法是必须实现的
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }    
    
    
    @Override
	protected String getPageName() {
		return MainFragment.class.getName();
	}
}
