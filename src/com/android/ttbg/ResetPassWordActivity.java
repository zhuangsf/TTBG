package com.android.ttbg;

import org.json.JSONObject;

import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
    private boolean isRegister = false;
	private Dialog mLoadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		
		mContext = ResetPassWordActivity.this;
	    Bundle bundle = getIntent().getExtras();
	    if(bundle!=null){
	    	userName = bundle.getString(Urls.URL_USERNAME);
	    	sn_code = bundle.getString(Urls.URL_SNCODE);
	    	isRegister = bundle.getBoolean("isRegister",false);  
	    	
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
		        		if(!isRegister)
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
		        		else
		        		{
			        		final String url = JsonControl.LOGIN_RESET_PASSWORD+userName+"/"+Utils.getPasswordMD5Str(et_reset_password.getText().toString())+"/"+sn_code+"/2";
			    			new Thread(new Runnable() {
			    				@Override
			    				public void run() {
			    					//JsonControl.httpPost(JsonControl.LOGIN_PATH, postString, mHandler);
			    					JsonControl.sendPost(url, null,mHandler,JsonControl.POST_TYPE_RESET_SN_CODE);
			    				}
			    			}).start();
		        		}
		        		
		        		showLoadingDialog();
		        		v.setEnabled(false);	
		        	}
		        	else{
		        		Toast.makeText(mContext,"请输入密码",Toast.LENGTH_SHORT).show();
		        	}
		        }  
		  }); 
		 
		 if(isRegister)
		 {
			 btn_register_reset.setText("确认");
		 }
		 else{
			 btn_register_reset.setText("确认重置");
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
							if(isRegister)
							{
								Toast.makeText(mContext,"注册成功",Toast.LENGTH_SHORT).show();
							}
							else
							{
								Toast.makeText(mContext,"重置成功",Toast.LENGTH_SHORT).show();
							}
							
							Intent intent = new Intent(mContext, LoginActivity.class);
							startActivity(intent);
							finish();
						}
		        		hideLoadingDialog();
		        		btn_register_reset.setEnabled(false);	
					}
					break;
        		}
            }
        }  	
	};
	
	
	
	public void hideLoadingDialog(){      
		if(mLoadingDialog != null)
        {
			mLoadingDialog.dismiss();
        }
	}
	public void showLoadingDialog(){         
	       // AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Translucent_NoTitle);  
	        if(mLoadingDialog == null)
	        {
			    mLoadingDialog = new Dialog(mContext, R.style.Translucent_Dialog);
		        LayoutInflater inflater = getLayoutInflater();  
		        final View layout = inflater.inflate(R.layout.layout_loading_dialog, null);//获取自定义布局  
		        ImageView animationIV;  
		        AnimationDrawable animationDrawable;
		        animationIV = (ImageView) layout.findViewById(R.id.iv_loading_dialog);
		        animationDrawable = (AnimationDrawable) animationIV.getDrawable();
				animationDrawable.start();
				mLoadingDialog.setCancelable(false);
				mLoadingDialog.setCanceledOnTouchOutside(false);
		        mLoadingDialog.setContentView(layout);  
		        mLoadingDialog.show();  
	        }
	        else
	        {
	        	 mLoadingDialog.show();  
	        }
	        WindowManager m = getWindowManager();  
	        Display display = m.getDefaultDisplay();  //为获取屏幕宽、高  
	        android.view.WindowManager.LayoutParams p = mLoadingDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
	        //p.height = (int) (display.getHeight() * 0.3);   //高度设置为屏幕的0.3
	        p.width = (int) (display.getWidth() * 0.3);    //宽度设置为屏幕的0.5 
	        mLoadingDialog.getWindow().setAttributes(p);     //设置生效  

	     }   

}