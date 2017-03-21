package com.android.ttbg;

import com.android.ttbg.util.OperatingSharedPreferences;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingNameActivity extends Activity {
	
    private ImageView  title_back;
    private TextView tv_finish;
    private EditTextWithDel edt_edit_name_input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_name);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 edt_edit_name_input = (EditTextWithDel)findViewById(R.id.edt_edit_name_input); 
		 
		 
		 tv_finish  = (TextView)findViewById(R.id.title_finish);
		 tv_finish.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(edt_edit_name_input != null)
		        	{
		        		OperatingSharedPreferences.saveUserName(SettingNameActivity.this, edt_edit_name_input.getText().toString());
		        		finish();
		        	}
		        }  
		  }); 
		 

		 
	}
	
	
	

}