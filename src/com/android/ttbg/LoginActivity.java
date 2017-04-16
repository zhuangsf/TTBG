package com.android.ttbg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.tencent.Util;
import com.android.ttbg.util.NetworkUtil;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends ActivityPack {
	
	private AutoCompleteTextView tv_username;
	private EditText et_password;
    private Button btn_login;
    private ImageView iv_clear_password;
    private Context mContext;
    private TextView tv_forgetpassword;
    private static Tencent mTencent;
    private ImageView iv_login_qq;
    private UserInfo mInfo;
    private static final String QQ_APP_ID="1106028097";
    private static final String TAG="LoginActivity";
    private View layout_register_right;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = LoginActivity.this;

		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		if (mTencent == null) {
			mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
		}
		// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
		// 初始化视图
		initViews();
	}

//	2	短信配置异常，联系管理员		
//	3	该账号不存在		
//	1	已发送验证码		
//	4	请求频繁，请稍后在发送。		

	private Handler mHandler= new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);                    
            if(msg.what ==JsonControl.POST_SUCCESS_MSG) {  
				JSONObject jsonObject = (JSONObject) msg.obj;
				// upload pic success
				String success = jsonObject.optString("success", "");
				String error_msg = jsonObject.optString("msg", "");
				Utils.Log("POST_SUCCESS_MSG success:"+success);
				if(!success.equals("1"))
				{
					Toast.makeText(mContext,error_msg,Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();

					String uid = jsonObject.optString("uid", "");
					String ushell = jsonObject.optString("ushell", "");
					
					
					//保存登录信息
					OperatingSP.setBoolean(mContext, OperatingSP.PREFERENCE_LOGIN, true);
					OperatingSP.setString(mContext, OperatingSP.PREFERENCE_UID, uid);
					OperatingSP.setString(mContext, OperatingSP.PREFERENCE_USHELL, ushell);
					
					//这边需要增加一个当前登录时间的字段,用于保存登录时间.登录建议一星期有效
					finish();
				}

            }
        }  	
	};
	
	
	private class EditViewClickListener implements OnClickListener{

		EditText editview;

		public EditViewClickListener(EditText editview) {
			super();
			this.editview = editview;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, ForgetActivity.class);
			if(editview.getText().toString().length() != 0)
			{
				intent.putExtra(Urls.URL_USERNAME, editview.getText().toString());
			}
			startActivity(intent);
		}


		
	}
	
	
	
	private void initViews() {
		// TODO Auto-generated method stub
		tv_username = (AutoCompleteTextView)findViewById(R.id.tv_username);
		et_password = (EditText)findViewById(R.id.et_password);
		iv_clear_password  = (ImageView)findViewById(R.id.iv_clear_password);
		tv_username.addTextChangedListener(new MyTextWatcher(tv_username));
		et_password.addTextChangedListener(new MyTextWatcher(et_password));
		tv_forgetpassword = (TextView)findViewById(R.id.tv_forgetpassword);
		tv_forgetpassword.setOnClickListener(new EditViewClickListener(tv_username)); 
		layout_register_right = (View)findViewById(R.id.layout_register_right);
		layout_register_right.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
				Intent intent = new Intent(mContext, RegisterActivity.class);
				startActivity(intent);
	        	finish();
	        }  
	  }); 
		View layout_back_left = (View)findViewById(R.id.layout_back_left);
		layout_back_left.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	finish();
	        }  
	  }); 
		
		btn_login  = (Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	if(!NetworkUtil.isNetworkAvailable(mContext))
	        	{
	        		Toast.makeText(mContext,"您的网络不给力哦",Toast.LENGTH_SHORT).show();
	        		return;
	        	}
	        	
	        	if(! checkPhoneAndEamil())
	        	{
	        		Toast.makeText(mContext,"请输入正确的手机号码或者邮箱",Toast.LENGTH_SHORT).show();
	        		return;
	        	}
	        	try {
	    		//	result.put("username", tv_username.getText().toString());
	    		//	result.put("password", et_password.getText().toString());
	    			String passworkMD5string = Utils.getPasswordMD5Str(et_password.getText().toString());
	    			final String postString = "username="+tv_username.getText().toString()+"&password="+passworkMD5string;
	    			
	    			Utils.Log(" httpPut postString:" + postString);

	    			// send to server
	    			new Thread(new Runnable() {
	    				@Override
	    				public void run() {
	    					//JsonControl.httpPost(JsonControl.LOGIN_PATH, postString, mHandler);
	    					JsonControl.sendPost(JsonControl.LOGIN_PATH, postString,mHandler,JsonControl.POST_TYPE_LOGIN);
	    				}
	    			}).start();
	    		} catch (Exception e) {
	    			Utils.Log(" httpPut error:" + e);
	    			e.printStackTrace();
	    		}
	        	
	        }  
	  }); 
		
		iv_login_qq=(ImageView)findViewById(R.id.iv_login_qq);
		iv_login_qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mTencent.isSessionValid()) {
					mTencent.login(LoginActivity.this, "all", loginListener);
					Log.d(TAG, "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
				} else{
					//just test
					logout();
					Util.toastMessage(LoginActivity.this, "qq logout");
					
				}
			}
		});
	}
	
	//add for qq login start
	IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
        	super.doComplete(values);
        	Log.d(TAG, "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            updateLoginButton();
        }
    };
    private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
			Util.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {
			Log.d(TAG, "doComplete qq login success:" + values.toString());
			//TODO success login
		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(LoginActivity.this, "onCancel: ");
			Util.dismissDialog();
		}

	}
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread(){

						@Override
						public void run() {
							JSONObject json = (JSONObject)response;
							if(json.has("figureurl")){
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
								} catch (JSONException e) {

								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
//			mUserInfo.setText("");
//			mUserInfo.setVisibility(android.view.View.GONE);
//			mUserLogo.setVisibility(android.view.View.GONE);
		}
	}
    
    private void updateLoginButton() {
		if (mTencent != null && mTencent.isSessionValid()) {
                //login success
		} else {
			//login fail
		}
	}
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
	    if (requestCode == Constants.REQUEST_LOGIN ||
	    	requestCode == Constants.REQUEST_APPBAR) {
	    	//在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，登录成功后无法成功回传数据。解决办法如下
	    	//在调用login的Activity或者Fragment重写onActivityResult方法
	    	//mTencent.handleLoginData(data, loginListener);
	    	Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
	    }

	    super.onActivityResult(requestCode, resultCode, data);
	}
    
    public void logout()
    {
           mTencent.logout(this);
    }
  //add for qq login end
	
	private boolean checkPhoneAndEamil()
	{
		if( Utils.isEmail(tv_username.getText().toString())  ||  Utils.isMobile(tv_username.getText().toString() ) )
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	private class MyTextWatcher implements TextWatcher{

        private EditText editText;
        private MyTextWatcher(EditText editText) {
            this.editText = editText;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String text = s.toString();
            if(text == null || text.length() == 0)
            {
            	btn_login.setEnabled(false);
            	return;
            }
            
            boolean loginButtonEnable = false;
            switch (editText.getId()){
                case R.id.et_password:
                	if(tv_username.getText().toString().length() != 0)
                	{
                		btn_login.setEnabled(true);
                	}
                    break;
                case R.id.tv_username:
                	if(et_password.getText().toString().length() != 0)
                	{
                		btn_login.setEnabled(true);
                	}
                    break;
            }
            

        }

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
    }
}
