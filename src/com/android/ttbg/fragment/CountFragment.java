package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.ttbg.*;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.view.AddPopWindow;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;



public class CountFragment extends BaseFragment {
    private static final String TAG = CountFragment.class.getSimpleName();
	private View CountFragment;
	private PullToRefreshLayout ptrl;
	
	private boolean bLogin = false;   //当前是否已登录
    @Override
    protected View initView() {
    	CountFragment = View.inflate(mContext, R.layout.fragment_account, null);
		if (CountFragment != null) {
			View account_record = (View)CountFragment.findViewById(R.id.account_record);  
			
			
			bLogin = OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_LOGIN, OperatingSP.PREFERENCE_LOGIN_DEFAULT);
			
			reflahLoginView(CountFragment);
			
			
			account_record.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), HistoryActivity.class);
						startActivity(intent);
					}
				});
			
			
		   View account_obtain_goods = (View)CountFragment.findViewById(R.id.account_obtain_goods);  
		   account_obtain_goods.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), ObtainGoodsActivity.class);
					startActivity(intent);
				}
			});
		   
		   View account_help = (View)CountFragment.findViewById(R.id.account_help);  
		   account_help.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), WebViewActivity.class);
			        intent.putExtra(Urls.URL_TITLE, "帮助与反馈");
			        intent.putExtra(Urls.URL_CONTENT, Urls.URL_HELP);
					startActivity(intent);
				}
			});
		   ImageView btn_account_message = (ImageView)CountFragment.findViewById(R.id.btn_account_message);  
		   btn_account_message.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
					startActivity(intent);
				}
			});
		   
		   ImageView btn_account_setting = (ImageView)CountFragment.findViewById(R.id.btn_account_setting);  
		   btn_account_setting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), SettingActivity.class);
					startActivity(intent);
				}
			});
		   
		   
		}
		return CountFragment;
    }



	private void reflahLoginView(View v) {
		// TODO Auto-generated method stub
		View rly_account_offline = (View)v.findViewById(R.id.rly_account_offline);  
		View rly_account_online = (View)v.findViewById(R.id.rly_account_online);  
		
		if(bLogin)
		{
			rly_account_offline.setVisibility(View.GONE);
			rly_account_online.setVisibility(View.VISIBLE);
		}
		else
		{
			rly_account_offline.setVisibility(View.VISIBLE);
			rly_account_online.setVisibility(View.GONE);
		}
		Button btn_account_login = (Button)v.findViewById(R.id.btn_account_login);  
		btn_account_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
		
		Button btn_account_register = (Button)v.findViewById(R.id.btn_account_register);  
		btn_account_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}



	@Override
    protected void initData() {
        Log.e(TAG, "CountFragment  initData...");
        super.initData();
    }

	@Override
	protected String getPageName() {
		return CountFragment.class.getName();
	}
}
