package com.android.ttbg;

import org.json.JSONObject;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassWordActivity extends ActivityPack {
	
    private ImageView  title_back;
    private EditText et_reset_password;
    private Button btn_register_reset;
    private String userName;
    private String sn_code;
    private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		
		mContext = ResetPassWordActivity.this;
	    Bundle bundle = getIntent().getExtras();
	    if(bundle!=null){
	    	userName = bundle.getString(Urls.URL_USERNAME);
	    	sn_code = bundle.getString(Urls.URL_SNCODE);
	    }
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 et_reset_password = (EditText)findViewById(R.id.et_reset_password);
		 btn_register_reset  = (Button)findViewById(R.id.btn_register_reset);
		 btn_register_reset.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(et_reset_password.getText().toString().length() != 0)
		        	{
		        	//	/apps/login/resetpassword/13859926176/ccd2631de58d6458ed33d023b2515eb9/358453/1
		        		final String url = JsonControl.LOGIN_RESET_PASSWORD+userName+"/"+Utils.getPasswordMD5Str(et_reset_password.getText().toString())+"/"+sn_code+"/1";
		    			new Thread(new Runnable() {
		    				@Override
		    				public void run() {
		    					//JsonControl.httpPost(JsonControl.LOGIN_PATH, postString, mHandler);
		    					JsonControl.sendPost(url, null,mHandler,JsonControl.POST_TYPE_RESET_SN_CODE);
		    				}
		    			}).start();
		        	}
		        	else{
		        		Toast.makeText(mContext,"请输入密码",Toast.LENGTH_SHORT).show();
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
					case JsonControl.POST_TYPE_RESET_SN_CODE:
					{
						if(!success.equals("1"))
						{
							Toast.makeText(mContext,error_msg,Toast.LENGTH_SHORT).show();
						}
						else
						{
							Toast.makeText(mContext,"重置成功",Toast.LENGTH_SHORT).show();
							
							Intent intent = new Intent(mContext, LoginActivity.class);
							startActivity(intent);
							finish();
						}
					}
					break;
        		}
            }
        }  	
	};
	

}