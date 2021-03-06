package com.android.ttbg;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.android.ttbg.fragment.BaseFragment;
import com.android.ttbg.fragment.CartFragment;
import com.android.ttbg.fragment.CountFragment;
import com.android.ttbg.fragment.CountFragment.onLoginListener;
import com.android.ttbg.fragment.MainFragment;
import com.android.ttbg.fragment.NewestFragment;
import com.android.ttbg.fragment.AllGoodsFragment;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.MyRadioButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivityPack implements onLoginListener  {
    private RadioGroup mRadioGroup;
    private List<BaseFragment> mBaseFragments;
    private int position; //当前选中的位置
    private BaseFragment mFragment;//刚显示的Fragment
    private MainFragment mMainFragment;
    private ImageView iv_home_to_check_more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化布局控件
        initView();
        //初始化数据
        initData();

        //设置监听
        setListener();
    }

    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中第一个
        mRadioGroup.check(R.id.main_tab_home);
    }

    private void initData() {
    	mMainFragment = new MainFragment();

        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(mMainFragment);
        mBaseFragments.add(new AllGoodsFragment());
        
        if(!Utils.WTF)
        {
        	mBaseFragments.add(new NewestFragment());
        	mBaseFragments.add(new CartFragment());
        }
       
        mBaseFragments.add(new CountFragment());
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        
        if(Utils.WTF)
        {
        MyRadioButton main_tab_newest = (MyRadioButton) findViewById(R.id.main_tab_newest);
        main_tab_newest.setVisibility(View.GONE);
        MyRadioButton main_tab_cart = (MyRadioButton) findViewById(R.id.main_tab_cart);
        main_tab_cart.setVisibility(View.GONE);
        }
        
        
    }

    
	@Override
	public void onLogin( ) {
		//登录之后要刷新一下界面  主要是获取一下信息
	}
    
	@Override
	protected void onResume() {
		super.onResume();

		//mMainFragment 初始化之后才能获取iv_home_to_check_more这个控件,不然会报空
		if(iv_home_to_check_more == null && mMainFragment != null && mMainFragment.getCheckMoreButton() != null)
		{
			iv_home_to_check_more = mMainFragment.getCheckMoreButton();
	    	iv_home_to_check_more.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	mRadioGroup.check(R.id.main_tab_allgoods);
		        }  
		  }); 
		}
	}

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_tab_home:
                    position = 0;
                    break;
                case R.id.main_tab_allgoods:
                    position = 1;
                    break;
                case R.id.main_tab_newest:
                    position = 2;
                    break;
                case R.id.main_tab_cart:
                    position = 3;
                    break;
                case R.id.main_tab_mine:
                	if(!Utils.WTF)
                    {
                		position = 4;
                    }else{
                    	position = 2;
                    }
                    
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment currentFragment = getFragment();

            //替换fragment
            //replaceFragment(currentFragment);

            replaceFragment(mFragment,currentFragment);
        }
    }

    /**
     *
     * @param lastFragment
     * @param currentFragment
     */
    private void replaceFragment(BaseFragment lastFragment, BaseFragment currentFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        //如果两个不相等,说明切换了Fragment
        if(lastFragment != currentFragment){
            mFragment = currentFragment;

            //隐藏刚显示的Fragment
            if(lastFragment != null){
                transaction.hide(lastFragment);
            }
            /**
             * 显示 或者 添加当前要显示的Fragment
             *
             * 如果当前要显示的Fragment没添加过 则 添加
             * 如果当前要显示的Fragment被添加过 则 隐藏
             */
            if(!currentFragment.isAdded()){
                if(currentFragment != null){
                    transaction.add(R.id.fl_main,currentFragment).commit();
                }
            }else {
                if (currentFragment != null){
                    transaction.show(currentFragment).commit();
                }
            }
        }
    }


    /**
     * 有问题代码
     * 替换Fragment
     * @param fragment
     */
    private void replaceFragment(BaseFragment fragment) {
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        //3.替换
        transaction.replace(R.id.fl_main, fragment);
        //4.提交事务
        transaction.commit();
    }

    /**
     * 根据返回到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        return mBaseFragments.get(position);
    }
}
