package com.android.ttbg;

import org.json.JSONObject;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;

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

public class ForgetActivity extends ActivityPack {
	
    private Context mContext;
    private ImageView  title_back;
    private String userName;
    private EditText et_username;
    private TextView tv_get_code;
    private TextView et_sn;
    private Button btn_register_nextstep;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		mContext = ForgetActivity.this;
		
		
	    Bundle bundle = getIntent().getExtras();
	    if(bundle!=null){
	    	userName = bundle.getString(Urls.URL_USERNAME);
	    }
	    
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 et_sn = (EditText)findViewById(R.id.et_sn);
		 et_username  = (EditText)findViewById(R.id.et_username);
		 if(userName != null)
		 {
			 et_username.setText(userName);
		 }
		 
		 
		 tv_get_code = (TextView)findViewById(R.id.tv_get_code);
		 tv_get_code.setOnClickListener(new TextViewClickListener(et_username)); 
		 
		 btn_register_nextstep  = (Button)findViewById(R.id.btn_register_nextstep);
		 btn_register_nextstep.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(!checkPhoneAndEamil() )
		        	{
		        		Toast.makeText(mContext,"请输入正确的手机号码或者邮箱",Toast.LENGTH_SHORT).show();
		        		return;
		        	}
		        	if(et_sn.getText().toString().length() == 0)
		        	{
		        		Toast.makeText(mContext,"请输入验证码",Toast.LENGTH_SHORT).show();
		        		return;
		        	}
		        	
		        }  
		  }); 
	}
	
	private boolean checkPhoneAndEamil()
	{
		if( Utils.isEmail(et_username.getText().toString())  ||  Utils.isMobile(et_username.getText().toString() ) )
		{
			return true;
		}
		else
		{
			return false;
		}
		
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
				if(!success.equals("1"))
				{
					Toast.makeText(mContext,error_msg,Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(mContext,"验证码已发送",Toast.LENGTH_SHORT).show();
					
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
	
	
	private class TextViewClickListener implements OnClickListener{

		EditText editview;

		public TextViewClickListener(EditText editview) {
			super();
			this.editview = editview;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!checkPhoneAndEamil())
			{
				Toast.makeText(mContext,"请输入正确的手机号码或者邮箱",Toast.LENGTH_SHORT).show();
				return;
			}
			timer.start();//开始计时
			v.setEnabled(false);  
			final String url = JsonControl.LOGIN_FORGET+editview.getText().toString();
			new Thread(new Runnable() {
				@Override
				public void run() {
					//JsonControl.httpPost(JsonControl.LOGIN_PATH, postString, mHandler);
					JsonControl.sendPost(url, null,mHandler);
				}
			}).start();
	
		}


		
	}
	
}
