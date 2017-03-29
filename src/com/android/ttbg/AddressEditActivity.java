package com.android.ttbg;



import com.android.ttbg.util.OperatingSP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

//启动的时候先判断一下本地是否有保存,有的话,就用本地的,不然就用服务器的
//总共存4个

public class AddressEditActivity extends ActivityPack {
	
    private ImageView  title_back;
    private TextView  title_save;
    private EditText et_address_name;
    private EditText et_address_phone;
    private EditText et_address_area;
    private EditText et_address_addr;
    private EditText et_address_zip;
    
    private TextView tv_tips_recriptor_name_error;
    
    private ToggleButton sb_edit_address_set_default;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_edit);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 
		 title_save = (TextView)findViewById(R.id.title_save);
		 title_save.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	checkAndSave();
		        }


		  }); 
		 
		 
		 et_address_name = (EditText)findViewById(R.id.et_address_name);
		 et_address_phone = (EditText)findViewById(R.id.et_address_phone);
		 et_address_area = (EditText)findViewById(R.id.et_address_area);
		 et_address_addr = (EditText)findViewById(R.id.et_address_addr);
		 et_address_zip = (EditText)findViewById(R.id.et_address_zip);
		 tv_tips_recriptor_name_error = (TextView)findViewById(R.id.tv_tips_recriptor_name_error);
		 tv_tips_recriptor_name_error.setVisibility(View.GONE);
		 
		 
		 //如果只有1个地址时,删除的按钮跟设置为默认的按钮都清空
	}
		 

	private void checkAndSave() {
		// TODO Auto-generated method stub
		if(et_address_name.getText().toString().length() <= 1)
		{
			tv_tips_recriptor_name_error.setVisibility(View.VISIBLE);
			return;
		}
		else{
			tv_tips_recriptor_name_error.setVisibility(View.GONE);
		}
		
		if(et_address_phone.getText().toString().length() != 11)
		{
			Toast.makeText(AddressEditActivity.this, "请填写正确的手机号码", 2000).show();
			return;
		}else if(et_address_addr.getText().toString().length() < 3)
		{
			Toast.makeText(AddressEditActivity.this, "请填写正确的地址,不包含特殊字符", 2000).show();
			return;
		}		
		else if(et_address_zip.getText().toString().length() != 6 && et_address_zip.getText().toString().length() != 0)
		{
			Toast.makeText(AddressEditActivity.this, "邮编格式错误", 2000).show();
			return;
		}
	}  
	
	
	

}