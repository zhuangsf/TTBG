package com.android.ttbg;

import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.view.EditTextWithDel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResetPassWordActivity extends ActivityPack {
	
    private ImageView  title_back;
    private TextView tv_finish;
    private EditTextWithDel edt_edit_qq_input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 

		 

		 
	}
	
	
	

}