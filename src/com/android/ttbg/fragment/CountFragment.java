package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ttbg.*;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.json.JsonControl;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.AddPopWindow;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;



public class CountFragment extends BaseFragment {
    private static final String TAG = CountFragment.class.getSimpleName();
	private View CountFragment;
	private PullToRefreshLayout ptrl;
	private onLoginListener mCallback;
	private boolean bLogin = false;   //当前是否已登录
    private AsyncImageLoader imageLoader;
	private TextView tv_account_name;
	private TextView tv_account_grade;
	private ImageView iv_account_icon;
	private TextView tv_account_score;
	private TextView tv_account_balance;
    @Override
    protected View initView() {
    	imageLoader = new AsyncImageLoader(mContext);  
    	
    	CountFragment = View.inflate(mContext, R.layout.fragment_account, null);
		if (CountFragment != null) {
			View account_record = (View)CountFragment.findViewById(R.id.account_record);  
			
			
			
			
			initLoginView(CountFragment);
			
			
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


    //用这个方法,跟服务器交互可能有点多
    @Override
	public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Utils.Log("CountFragment onHiddenChanged hidden = "+hidden);
        if(!hidden)
        {    
            if(CountFragment != null)
            {
            	reflahLoginView(CountFragment);
            }
        }
    }
    

    @Override
	public void onResume() {
        super.onResume();
        if(CountFragment != null)
        {
        	reflahLoginView(CountFragment);
        }

    }
    
    
	public interface onLoginListener {
		public void onLogin( );
	}

	
	
	
	private Handler mHandler= new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);                    
            if(msg.what ==JsonControl.POST_SUCCESS_MSG) {  
				JSONObject jsonObject = (JSONObject) msg.obj;
				// upload pic success
				String success = jsonObject.optString("success", "");
				Utils.Log("POST_SUCCESS_MSG success:"+success);
				if(!success.equals("1"))
				{
					Toast.makeText(mContext,"信息读取失败",Toast.LENGTH_SHORT).show();
				}
				else
				{
					//Toast.makeText(mContext,"获取用户信息成功",Toast.LENGTH_SHORT).show();
					try {
					JSONObject j_user_info = jsonObject.getJSONObject("userinfo");

	        		String uid = j_user_info.getString("uid");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_ID,uid);
	        		String username = j_user_info.getString("username");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_NAME,username);
	        		
	        		tv_account_name.setText(username+"(id:"+uid+")");
	        		
	        		String mobile = j_user_info.getString("mobile");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_MOBILE,mobile);
	        		String img = j_user_info.getString("img");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_IMAGE_PATH,img);
	        		//头像有问题,先不处理
	        	//	imageLoader.downloadImage(JsonControl.FILE_HEAD+img, iv_account_icon);
	        		 int fstart,fend;
	        		
	        		String money = j_user_info.getString("money");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_MONEY,money);
	        		
	        		String spanString1 = "余额(元)\n"+money;
	                fstart="余额(元)\n".length();  
	                fend=spanString1.length(); 
	                SpannableStringBuilder style1=new SpannableStringBuilder(spanString1);     
	                style1.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
	                tv_account_balance.setText(style1);
	        		
	        		String score = j_user_info.getString("score");    	      
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_SCORE,score);
	        		
	        	   
	                String spanString2 = "福分\n"+score;
	                fstart="福分\n".length();  
	                fend=spanString2.length(); 
	                SpannableStringBuilder style2=new SpannableStringBuilder(spanString2);     
	                style2.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
	        		tv_account_score.setText(style2);
	        		
	        		String jingyan = j_user_info.getString("jingyan");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_JINGYAN,jingyan);
	        		String sex = j_user_info.getString("sex");
	        		if(sex != null)
	        		{
	        			if(sex.equals("1")) //1女2男3保密
	        			{
	        				OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_SEX,"女");
	        			}
	        			else if(sex.equals("2")) 
	        			{
	        				OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_SEX,"男");
	        			}else {
	        				OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_SEX,"保密");
	        			}
	        		}
	
	        		String birthday = j_user_info.getString("birthday");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_BIRTHDAY,birthday);
	        		String qq_bak = j_user_info.getString("qq_bak");
	        		if(qq_bak.equals("null"))
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_QQ,"");
	        		}
	        		else
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_QQ,qq_bak);
	        		}
	        		String live_name = j_user_info.getString("live_name");
	        		if(live_name.equals("null"))
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_LIVE,"");
	        		}
	        		else
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_LIVE,live_name);
	        		}

	        		String home_name = j_user_info.getString("home_name");
	        		if(home_name.equals("null"))
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_HOME,"");
	        		}
	        		else
	        		{
	        			OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_HOME,home_name);
	        		}
	        		String qianming = j_user_info.getString("qianming");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_SIGNATURE,qianming);
	        		String groupid = j_user_info.getString("groupid");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_GROUP,groupid);
	        		String yungoudj = j_user_info.getString("yungoudj");
	        		OperatingSP.setString(mContext,OperatingSP.PREFERENCE_SETTING_USER_DENGJI,yungoudj);
	        		
	        		tv_account_grade.setText(yungoudj);
	        		
					}catch (JSONException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				}


				}

            }
        }  	
	};
	
	
	

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// 这个方法是用来确认当前的Activity容器是否已经继承了该接口，如果没有将抛出异常
		try {
			mCallback = (onLoginListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}
    
    
	private void reflahLoginView(View v) {
		// TODO Auto-generated method stub
    	
    	bLogin = OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_LOGIN, OperatingSP.PREFERENCE_LOGIN_DEFAULT);
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
		
		
		tv_account_name = (TextView)v.findViewById(R.id.tv_account_name);  
		tv_account_grade = (TextView)v.findViewById(R.id.tv_account_grade);  
		iv_account_icon = (ImageView)v.findViewById(R.id.iv_account_icon);   
		tv_account_balance = (TextView)v.findViewById(R.id.tv_account_balance);  
		tv_account_score = (TextView)v.findViewById(R.id.tv_account_score);  

		Utils.Log("reflahLoginView bLogin = "+bLogin);
		if(bLogin == true)
		{
			//刚刚登录,可以开始获取一些信息了
			mCallback.onLogin();
			final String postString = "uid="+
			OperatingSP.getString(mContext, OperatingSP.PREFERENCE_UID, OperatingSP.PREFERENCE_UID_DEFAULT)+"&ushell="+
			OperatingSP.getString(mContext, OperatingSP.PREFERENCE_USHELL, OperatingSP.PREFERENCE_USHELL_DEFAULT);
			// 读取用户数据
			new Thread(new Runnable() {
				@Override
				public void run() {
					//JsonControl.httpPost(JsonControl.LOGIN_PATH, postString, mHandler);
					JsonControl.sendPost(JsonControl.USER_INFO, postString,mHandler,JsonControl.POST_TYPE_USER);
				}
			}).start();
			
		}
    }
    

	private void initLoginView(View v) {
		// TODO Auto-generated method stub
		reflahLoginView(v);
		

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
