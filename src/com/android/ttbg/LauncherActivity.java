package com.android.ttbg;

import com.android.ttbg.adapter.LauncherPagerAdapter;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.view.ILauncherView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;



/**
 * 第一次启动页面
 * 
 * @author Ansen
 * @create time 2016-04-15
 */
@SuppressLint("ResourceAsColor")
public class LauncherActivity extends FragmentActivityPack implements ILauncherView {
	private ViewPager viewpagerLauncher;
	private LauncherPagerAdapter adapter;

	private ImageView[] tips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_luancher);
		
		if(!isFirst()){
			gotoVisit();
		}
		viewpagerLauncher = (ViewPager) findViewById(R.id.viewpager_launcher);
		adapter = new LauncherPagerAdapter(this, this);
		viewpagerLauncher.setOffscreenPageLimit(2);
		viewpagerLauncher.setCurrentItem(0);
		viewpagerLauncher.setOnPageChangeListener(changeListener);
		viewpagerLauncher.setAdapter(adapter);
		viewpagerLauncher.setOnPageChangeListener(changeListener);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);// 初始化底部显示控件
		tips = new ImageView[4];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			if (i == 0) {
				imageView.setBackgroundResource(R.drawable.dot01);
			} else {
				imageView.setBackgroundResource(R.drawable.dot02);
			}
			imageView.setScaleType(ScaleType.FIT_CENTER);
			tips[i] = imageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(20,20));
			layoutParams.leftMargin = 10;// 设置点点点view的左边距
			layoutParams.rightMargin = 10;// 设置点点点view的右边距
			layoutParams.bottomMargin = 50;
			group.addView(imageView, layoutParams);
		}
	}

	private OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int arg0) {}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		@Override
		public void onPageSelected(int index) {
			setImageBackground(index);// 改变点点点的切换效果

			View btnLayout = adapter.getViews().get(index).findViewById(R.id.btnLayout);
			if (index == tips.length - 1) {// 最后一个
				btnLayout.setVisibility(View.VISIBLE);
			} else {
				btnLayout.setVisibility(View.INVISIBLE);
			}
		}
	};

	/**
	 * 改变点点点的切换效果
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.dot01);
			} else {
				tips[i].setBackgroundResource(R.drawable.dot02);
			}
		}
	}



	private boolean isFirst() {
		Boolean user_first = OperatingSP.getBooleanFirstBoot(LauncherActivity.this);
		if (user_first) {// 第一次
			OperatingSP.setBooleanFirstBoot(LauncherActivity.this);
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	public void gotoVisit() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}	
	
	@Override
	public void gotoRegist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotoLogin() {
		// TODO Auto-generated method stub
		
	}
}
