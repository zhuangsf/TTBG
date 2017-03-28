package com.android.ttbg;



import com.android.ttbg.util.OperatingSP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddressManagerActivity extends ActivityPack {
	
    private ImageView  title_back;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addressmanager);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 TextView title_back = (TextView)findViewById(R.id.title_add_address);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(AddressManagerActivity.this, AddressEditActivity.class);
					startActivity(intent);
		        }  
		  }); 
		 
	}
		 


	
	
	

}