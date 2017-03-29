package com.android.ttbg;



import com.android.ttbg.util.BackLightControl;
import com.android.ttbg.util.OperatingSP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends ActivityPack {
	
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
		 
	}
	
	
	

}