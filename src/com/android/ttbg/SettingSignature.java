package com.android.ttbg;

import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingSignature extends ActivityPack {
	
    private ImageView  title_back;
    private TextView tv_finish;
    private EditText edt_edit_input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_signatrue);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 edt_edit_input = (EditText)findViewById(R.id.edt_edit_input); 
		 edt_edit_input.setText(OperatingSP.getString(SettingSignature.this, OperatingSP.PREFERENCE_SETTING_SIGNATURE  ,OperatingSP.PREFERENCE_SETTING_SIGNATURE_DEFAULT));
		 
		 tv_finish  = (TextView)findViewById(R.id.title_finish);
		 tv_finish.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(edt_edit_input != null)
		        	{
		        		OperatingSP.setString(SettingSignature.this, OperatingSP.PREFERENCE_SETTING_SIGNATURE  ,edt_edit_input.getText().toString());
		        		finish();
		        	}
		        }  
		  }); 
		 

		 
	}
	
	
	

}