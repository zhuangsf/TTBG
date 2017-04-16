package com.android.ttbg;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;

public class RegisterActivity extends ActivityPack {
	
    private ImageView  title_back;
    private ToggleButton checkbox_register_agreement;
    private TextView title_login;
    private Context mContext;
    private EditText et_username;
    private EditText et_sn;
    private Button btn_register_nextstep;
    private TextView tv_get_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mContext = RegisterActivity.this;
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 title_login = (TextView)findViewById(R.id.title_login);
		 title_login.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
		        	finish();
		        }  
		  }); 
		 
		 tv_get_code  = (TextView)findViewById(R.id.tv_get_code);
		 tv_get_code.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(et_username.getText().toString().length() == 0)
		        	{
		        		Toast.makeText(mContext,"请输入手机号码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}
		        	else if(!Utils.isMobile(et_username.getText().toString()))
		        	{
		        		Toast.makeText(mContext,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}		        	
					timer.start();//开始计时
					v.setEnabled(false);  
		        	//http://www.1ybgo.com/apps/login/getregcode/13859926176	
		        			
		        	final String url = JsonControl.REGISTER+et_username.getText().toString();
	    			new Thread(new Runnable() {
	    				@Override
	    				public void run() {
	    					JsonControl.sendPost(url, null,mHandler,JsonControl.POST_TYPE_REGISTER);
	    				}
	    			}).start();
		        	
		        	
		        	
		        }  
		  }); 
		 
		 et_sn = (EditText)findViewById(R.id.et_sn);
		 et_username  = (EditText)findViewById(R.id.et_username);
		 btn_register_nextstep  = (Button)findViewById(R.id.btn_register_nextstep);
		 
		 btn_register_nextstep.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(et_username.getText().toString().length() == 0)
		        	{
		        		Toast.makeText(mContext,"请输入手机号码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}
		        	else if(!Utils.isMobile(et_username.getText().toString()))
		        	{
		        		Toast.makeText(mContext,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}else if(et_sn.getText().toString().length() == 0)
		        	{
		        		Toast.makeText(mContext,"请输入验证码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}
		        	
		        	final String url = JsonControl.LOGIN_CHECK_REGISTE_CODE+et_username.getText().toString()+"/"+et_sn.getText().toString();
	    			new Thread(new Runnable() {
	    				@Override
	    				public void run() {
	    					JsonControl.sendPost(url, null,mHandler,JsonControl.POST_TYPE_CHECK_SN_CODE);
	    				}
	    			}).start();
		        	
		        	
		        }  
		  }); 
		 
		 
		 
		 checkbox_register_agreement = (ToggleButton)findViewById(R.id.checkbox_register_agreement);
		 checkbox_register_agreement.setOnClickListener(new OnClickListener() {      
	            public void onClick(View v) {          
	                // 当按钮第一次被点击时候响应的事件        
	                if (checkbox_register_agreement.isChecked()) {              
	                	btn_register_nextstep.setEnabled(true);
	                }   
	                // 当按钮再次被点击时候响应的事件  
	                else {              
	                	btn_register_nextstep.setEnabled(false);
	                }      
	            }  
	          });  
	}
	
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
				
				
				switch(msg.arg1)
        		{
				case JsonControl.POST_TYPE_REGISTER:
				{
					if(!success.equals("1"))
					{
						Toast.makeText(mContext,error_msg,Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(mContext,"验证码已发送",Toast.LENGTH_SHORT).show();
						
					}
				}
					break;
				case JsonControl.POST_TYPE_CHECK_SN_CODE:
				{
					if(!success.equals("1"))
					{
						Toast.makeText(mContext,error_msg,Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent intent = new Intent(mContext, ResetPassWordActivity.class);
						intent.putExtra(Urls.URL_USERNAME, et_username.getText().toString());
						intent.putExtra(Urls.URL_SNCODE, et_sn.getText().toString());
						intent.putExtra("isRegister", true);
						startActivity(intent);
						
					}
				}
					break;
        		}

            }
        }  	
	};
	
	 private CountDownTimer timer = new CountDownTimer(120000, 1000) {  
		  
	        @Override  
	        public void onTick(long millisUntilFinished) {  
	        	tv_get_code.setText((millisUntilFinished / 1000)+"");  
	        }  
	  
	        @Override  
	        public void onFinish() {  
	        	tv_get_code.setEnabled(true);  
	        	tv_get_code.setText("获取验证码");  
	        }  
	    };  
}