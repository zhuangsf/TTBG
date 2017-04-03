package com.android.ttbg;

import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingQQActivity extends ActivityPack {
	
    private ImageView  title_back;
    private TextView tv_finish;
    private EditTextWithDel edt_edit_qq_input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_qq);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 edt_edit_qq_input = (EditTextWithDel)findViewById(R.id.edt_edit_qq_input); 
		 edt_edit_qq_input.setText(OperatingSP.getString(SettingQQActivity.this, OperatingSP.PREFERENCE_SETTING_QQ  ,OperatingSP.PREFERENCE_SETTING_QQ_DEFAULT));
		 
		 tv_finish  = (TextView)findViewById(R.id.title_finish);
		 tv_finish.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(edt_edit_qq_input != null)
		        	{
		        		OperatingSP.setString(SettingQQActivity.this, OperatingSP.PREFERENCE_SETTING_QQ  ,edt_edit_qq_input.getText().toString());
		        		finish();
		        	}
		        }  
		  }); 
		 

		 
	}
	
	
	

}