package com.android.ttbg;



import com.android.ttbg.tools.DataCleanManager;
import com.android.ttbg.util.BackLightControl;
import com.android.ttbg.util.OperatingSP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends ActivityPack {
	
    private ImageView  title_back;
    
    private ToggleButton toggle_switch;
    boolean toggle_switchEnable = true;
    private View rly_setting_light;
    
    private TextView tv_account_item_tips;
    
    
    private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 //编辑个人资料
		 View item_setting_edit = (View)findViewById(R.id.item_setting_edit);
		 item_setting_edit.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(SettingActivity.this, SettingEditActivity.class);
					startActivity(intent);
		        }  
		  }); 
		 
		 
		//亮度调节
		 rly_setting_light = (View)findViewById(R.id.rly_setting_light);
		 boolean bOn = OperatingSP.getLightSetting(SettingActivity.this);
		 
		 if(bOn)
		 {
			 rly_setting_light.setVisibility(View.VISIBLE);
		 }
		 else
		 {
			 rly_setting_light.setVisibility(View.GONE);
		 }
		 toggle_switch  = (ToggleButton)findViewById(R.id.toggle_switch);
		 toggle_switch.setChecked(bOn);
		 toggle_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					OperatingSP.setLightSetting(SettingActivity.this,isChecked);
					 if(isChecked)
					 {
						 rly_setting_light.setVisibility(View.VISIBLE);
					 }
					 else
					 {
						 rly_setting_light.setVisibility(View.GONE);
					 }
				}
		 });
		 
		 
		//亮度进度条调节
		 SeekBar progress_setting_light = (SeekBar)findViewById(R.id.progress_setting_light);
		 int currentBrightness = BackLightControl.getBrightNess(SettingActivity.this);
		 progress_setting_light.setMax(255);
		 progress_setting_light.setProgress(currentBrightness);
		 
		 progress_setting_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
	            @Override
	            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	                    int brightness = progress;
	                    BackLightControl.setBrightness(SettingActivity.this,brightness);

	            }

	            @Override
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }

	            @Override
	            public void onStopTrackingTouch(SeekBar seekBar) {

	            }
	        });
		 
		 
		 //设置缓存数值大小
		 tv_account_item_tips = (TextView)findViewById(R.id.tv_account_item_tips);
		 try{
			 tv_account_item_tips.setText(DataCleanManager.getCacheSize(SettingActivity.this));
		 }catch (Exception e) {  
             // TODO Auto-generated catch block  
             e.printStackTrace();  
         }  
		 
		 //清除缓存功能
		 View item_setting_clear = (View)findViewById(R.id.item_setting_clear);
		 item_setting_clear.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	showClearCacheDialog();
		        }  
		  }); 
		 
		 //版本更新
		 View item_setting_update = (View)findViewById(R.id.item_setting_update);
		 item_setting_update.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	showUpdateDialog();
		        }  
		  }); 
		 
		 //关于我们菜单
		 View item_setting_about = (View)findViewById(R.id.item_setting_about);
		 item_setting_about.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
					startActivity(intent);
		        }  
		  });
		 
	}
	

	public void showUpdateDialog(){         
	       // AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Translucent_NoTitle);  
	        
			mDialog = new Dialog(this, R.style.Translucent_NoTitle);
	        LayoutInflater inflater = getLayoutInflater();  
	        final View layout = inflater.inflate(R.layout.dialog_base, null);//获取自定义布局  

	       // builder.setIcon(R.drawable.ic_launcher);//设置标题图标  
	       // builder.setTitle(R.string.hello_world);//设置标题内容  
	        //builder.setMessage("");//显示自定义布局内容  
	          
	        TextView tv_message = (TextView)layout.findViewById(R.id.stub_base_dialog);  
	        tv_message.setText("已是最新版本啦!");
	        
	        Button button_cancel = (Button)layout.findViewById(R.id.btn_base_dialog_cancel);  
	        button_cancel.setVisibility(View.GONE);

	        Button button_ok = (Button)layout.findViewById(R.id.btn_base_dialog_measure);  
	        button_ok.setOnClickListener(new OnClickListener() {  
	              
	            @Override  
	            public void onClick(View arg0) {  
	                // TODO Auto-generated method stub  
	            	mDialog.dismiss();
	            }  
	        });  

	        mDialog.setContentView(layout);  
	        mDialog.show();  
	        
	        WindowManager m = getWindowManager();  
	        Display display = m.getDefaultDisplay();  //为获取屏幕宽、高  
	        android.view.WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
	        //p.height = (int) (display.getHeight() * 0.3);   //高度设置为屏幕的0.3
	        p.width = (int) (display.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
	        mDialog.getWindow().setAttributes(p);     //设置生效  
	        
	     }  
	
	
	
	public void showClearCacheDialog(){         
       // AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Translucent_NoTitle);  
        
		mDialog = new Dialog(this, R.style.Translucent_NoTitle);
        LayoutInflater inflater = getLayoutInflater();  
        final View layout = inflater.inflate(R.layout.dialog_base, null);//获取自定义布局  

       // builder.setIcon(R.drawable.ic_launcher);//设置标题图标  
       // builder.setTitle(R.string.hello_world);//设置标题内容  
        //builder.setMessage("");//显示自定义布局内容  
          
        TextView tv_message = (TextView)layout.findViewById(R.id.stub_base_dialog);  
        tv_message.setText("确定清除本地缓存?");
        
        Button button_cancel = (Button)layout.findViewById(R.id.btn_base_dialog_cancel);  
        button_cancel.setOnClickListener(new OnClickListener() {  
              
            @Override  
            public void onClick(View arg0) {  
                // TODO Auto-generated method stub  
            	mDialog.dismiss();
            }  
        });       
        Button button_ok = (Button)layout.findViewById(R.id.btn_base_dialog_measure);  
        button_ok.setOnClickListener(new OnClickListener() {  
              
            @Override  
            public void onClick(View arg0) {  
                // TODO Auto-generated method stub  
            	mDialog.dismiss();
            	
            	//这边可能要加个异步的操作,先这样吧
            	DataCleanManager.cleanInternalCache(SettingActivity.this);
            	if(tv_account_item_tips != null)
            	{
            		try{
            		tv_account_item_tips.setText(DataCleanManager.getCacheSize(SettingActivity.this));
            		}catch (Exception e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    }  
            	}
            }  
        });  

        mDialog.setContentView(layout);  
        mDialog.show();  
        
        WindowManager m = getWindowManager();  
        Display display = m.getDefaultDisplay();  //为获取屏幕宽、高  
        android.view.WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
        //p.height = (int) (display.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (display.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
        mDialog.getWindow().setAttributes(p);     //设置生效  
        
     }  
	

}