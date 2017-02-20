package com.android.yybg.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.yybg.R;
import com.android.yybg.view.ILauncherView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * ViewPager适配器
 * @author ansen
 * @create time 2016-04-15
 */
public class LauncherPagerAdapter extends PagerAdapter implements OnClickListener{
	private ILauncherView launcherView;
	
	private List<View> views;
	//每页显示的图片
	private int[] images=new int[]{R.drawable.welcome1,R.drawable.welcome2,R.drawable.welcome3,R.drawable.welcome1};
	
	public LauncherPagerAdapter(Context context,ILauncherView launcherView){
		views=new ArrayList<View>();
		this.launcherView=launcherView;
		//初始化每页显示的View
		for(int i=0;i<images.length;i++){
			View item=LayoutInflater.from(context).inflate(R.layout.activity_luancher_pager_item, null);
			ImageView imageview=(ImageView) item.findViewById(R.id.imageview);
			imageview.setImageResource(images[i]);
			item.findViewById(R.id.btnLogin).setOnClickListener(this);
			item.findViewById(R.id.btnReg).setOnClickListener(this);
			item.findViewById(R.id.btnVisit).setOnClickListener(this);
			views.add(item);
		}
	}
	
	public List<View> getViews() {
		return views;
	}
	
	@Override
	public int getCount() {
		return views == null ? 0 : views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btnLogin)
		{
			launcherView.gotoLogin();
		}else if(v.getId() == R.id.btnReg)
		{
			launcherView.gotoRegist();
		}else if(v.getId() == R.id.btnVisit)
		{
			launcherView.gotoVisit();
		}
		
	}
	
}
