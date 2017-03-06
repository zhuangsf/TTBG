package com.android.ttbg;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.ttbg.fragment.BaseFragment;
import com.android.ttbg.fragment.CareAboutFragment;
import com.android.ttbg.fragment.MainFragment;
import com.android.ttbg.fragment.PersonFragment;
import com.android.ttbg.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TabActivity {
    private RadioGroup mRadioGroup;
    private List<BaseFragment> mBaseFragments;
    private int position; //当前选中的位置
    private BaseFragment mFragment;//刚显示的Fragment

	private TabHost tabHost;
	private TextView main_tab_cart_num;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_tab_cart_num=(TextView) findViewById(R.id.main_tab_new_message);
        main_tab_cart_num.setVisibility(View.VISIBLE);
        main_tab_cart_num.setText("10");
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, AddExamActivity.class);
        spec=tabHost.newTabSpec("添加考试").setIndicator("添加考试").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,AddExamActivity.class);
        spec=tabHost.newTabSpec("我的考试").setIndicator("我的考试").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, AddExamActivity.class);
        spec=tabHost.newTabSpec("我的通知").setIndicator("我的通知").setContent(intent);
        tabHost.addTab(spec);
        
     
        intent=new Intent().setClass(this, AddExamActivity.class);
        spec=tabHost.newTabSpec("设置").setIndicator("设置").setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(1);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_home://添加考试
					tabHost.setCurrentTabByTag("首页");
					break;
				case R.id.main_tab_allgoods://我的考试
					tabHost.setCurrentTabByTag("所有商品");
					break;
				case R.id.main_tab_newest://我的通知
					tabHost.setCurrentTabByTag("最新揭晓");
					break;
				case R.id.main_tab_cart://设置
					tabHost.setCurrentTabByTag("购物车");
					break;
				case R.id.main_tab_mine://设置
					tabHost.setCurrentTabByTag("我的云购");
					break;
					
				default:
					tabHost.setCurrentTabByTag("首页");
					break;
				}
			}
		});
    }


}
