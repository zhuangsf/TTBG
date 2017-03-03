package com.android.yybg.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.android.yybg.R;
import com.android.yybg.util.TimerUtil;


import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainFragment extends BaseFragment {
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
    
    private ImageView count1_image;
    private ImageView count2_image;
    private ImageView count3_image;
    private ImageView count4_image;
    private TextView count1_time;
    private TextView count2_time;
    private TextView count3_time;
    private TextView count4_time;
    
    
    @Override
    protected View initView() {
        //Log.e(TAG, "首页页面Fragment页面被初始化了...");
        mainFragmentView = View.inflate(mContext, R.layout.fragment_main, null);
        
        if(mainFragmentView != null)
        {
        	initADPager(mainFragmentView);      //初始化页眉广告条
        	initCountDownPage(mainFragmentView);
        }
        return mainFragmentView;
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
        initPageAdapter();
        initCirclePoint(v);
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
    }    
    
    private void atomicOption() {
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
    
    private void initPageAdapter() {
        pageViews = new ArrayList<View>();
        ImageView img1 = new ImageView(getActivity());
        img1.setBackgroundResource(R.drawable.welcome1);
        pageViews.add(img1);
        ImageView img2 = new ImageView(getActivity());
        img2.setBackgroundResource(R.drawable.welcome2);
        pageViews.add(img2);
        ImageView img3 = new ImageView(getActivity());
        img3.setBackgroundResource(R.drawable.welcome3);
        pageViews.add(img3);
        ImageView img4 = new ImageView(getActivity());
        img4.setBackgroundResource(R.drawable.welcome4);
        pageViews.add(img4);
        adapter = new AdPageAdapter(pageViews);
    }
 
    private void initCirclePoint(View v) {
        ViewGroup group = (ViewGroup) v.findViewById(R.id.viewGroup);
        imageViews = new ImageView[pageViews.size()];
        // 广告栏的小圆点图标
        for (int i = 0; i < pageViews.size(); i++) {
            // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
            imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LayoutParams(10, 10));
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
    
    
    
}
