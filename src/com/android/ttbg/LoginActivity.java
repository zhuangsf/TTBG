package com.android.ttbg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.NetworkUtil;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = LoginActivity.this;
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
					Toast.makeText(mContext,"已发送验证码",Toast.LENGTH_SHORT).show();
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
	}
	
	
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