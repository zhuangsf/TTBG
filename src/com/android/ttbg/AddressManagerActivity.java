package com.android.ttbg;



import com.android.ttbg.util.OperatingSP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
		 
		 TextView title_add_address = (TextView)findViewById(R.id.title_add_address);
		 title_add_address.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(AddressManagerActivity.this, AddressEditActivity.class);
					startActivity(intent);
		        }  
		  }); 
		 
		 
		 
		 Button button_address_add = (Button)findViewById(R.id.button_address_add);
		 button_address_add.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(AddressManagerActivity.this, AddressEditActivity.class);
					
			        intent.putExtra("sharePreferenceID", 1);     //先默认是1,后续要改
					
					startActivity(intent);
		        }  
		  }); 
		 
	}
		 


	
	
	

}