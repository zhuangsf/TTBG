package com.android.ttbg;



import com.android.ttbg.util.OperatingSharedPreferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends Activity {
	
    private ImageView  title_back;
    private View item_setting_edit;
    
    private ToggleButton toggle_switch;
    boolean toggle_switchEnable = true;
    private View rly_setting_light;
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
		 

		 item_setting_edit = (View)findViewById(R.id.item_setting_edit);
		 item_setting_edit.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(SettingActivity.this, SettingEditActivity.class);
					startActivity(intent);
		        }  
		  }); 
		 
		 rly_setting_light = (View)findViewById(R.id.rly_setting_light);
		 boolean bOn = OperatingSharedPreferences.getLightSetting(SettingActivity.this);
		 
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
					OperatingSharedPreferences.setLightSetting(SettingActivity.this,isChecked);
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
	}
	
	
	

}