package com.android.ttbg;



import java.util.ArrayList;
import java.util.List;

import com.android.ttbg.adapter.AddressManagerAdapter;
import com.android.ttbg.adapter.ObtainGoodsAdapter;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.view.AddressItem;
import com.android.ttbg.view.ObtainGoodsItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddressManagerActivity extends ActivityPack {
	
    private ImageView  title_back;
    private ListView list_view;
    
    private View empty_view_address;
    private TextView max_count_hint;
    private AddressManagerAdapter addressManagerAdapter;
    public final static int MAX_ADDRESS_COUNT = 4;
    TextView title_add_address;
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
		 
		 title_add_address = (TextView)findViewById(R.id.title_add_address);
		 if(getEmptyShareIndex() == -1)
		 {
			 title_add_address.setTextColor(0xff999999);
		 }
		 else
		 {
			 title_add_address.setTextColor(0xffff7700); 
		 }
		 title_add_address.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(AddressManagerActivity.this, AddressEditActivity.class);
					
					int emptyShareIndex = getEmptyShareIndex();
					if(emptyShareIndex != -1)
			        {	
						intent.putExtra("sharePreferenceID", emptyShareIndex);    
			        }
					else{
						return;
					}
					
					startActivity(intent);
		        }  
		  }); 
		 
		 
		 
		 Button button_address_add = (Button)findViewById(R.id.button_address_add);
		 button_address_add.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(AddressManagerActivity.this, AddressEditActivity.class);
					
					int emptyShareIndex = getEmptyShareIndex();
					if(emptyShareIndex != -1)
			        {	
						intent.putExtra("sharePreferenceID", emptyShareIndex);    
			        }
					
					startActivity(intent);
		        }  
		  }); 
		 empty_view_address = (View)findViewById(R.id.empty_view_address);
		 max_count_hint = (TextView)findViewById(R.id.max_count_hint);
		 list_view = ((ListView) findViewById(R.id.lv_address));
		 List<AddressItem> hashMapList = new ArrayList<AddressItem>();
	        //最多4个地址
	        for (int i = 0; i < MAX_ADDRESS_COUNT; i++) {
	        	if(OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i, OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT) == true)
	        	{
	        	AddressItem addressItem = new AddressItem();
	        	addressItem.setAddressMessage(
	        			i,
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_NAME+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_PHONE+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_AREA+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ADDRESS+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_CODE+i,""),
	        			OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_DEFAULT+i,OperatingSP.PREFERENCE_ADDRESS_DEFAULT_BSET)
	        			);
	            hashMapList.add(addressItem);
	        	}
	        }
	        
	        addressManagerAdapter = new AddressManagerAdapter(AddressManagerActivity.this, hashMapList);
	        list_view.setAdapter(addressManagerAdapter);

	}
	
	private int getEmptyShareIndex()
	{
		for (int i = 0; i < MAX_ADDRESS_COUNT; i++) {
			if(OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i, OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT) == false)
			{
				return i;
			}
		}
		return -1;//-1表示4个满了
	}
		 
	private void reflashListView()
	{
		 List<AddressItem> hashMapList = new ArrayList<AddressItem>();
	        //最多4个地址
	        for (int i = 0; i < MAX_ADDRESS_COUNT; i++) {
	        	if(OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i, OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT) == true)
	        	{
	        	AddressItem addressItem = new AddressItem();
	        	addressItem.setAddressMessage(
	        			i,
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_NAME+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_PHONE+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_AREA+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ADDRESS+i,""),
	        			OperatingSP.getString(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_CODE+i,""),
	        			OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_DEFAULT+i,OperatingSP.PREFERENCE_ADDRESS_DEFAULT_BSET)
	        			);
	            hashMapList.add(addressItem);
	        	}
	        }
	        
	        addressManagerAdapter.setData(hashMapList);

	        addressManagerAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		 if(getEmptyShareIndex() == -1)
		 {
			 title_add_address.setTextColor(0xff999999);
		 }
		 else
		 {
			 title_add_address.setTextColor(0xffff7700); 
		 }
		
		if(checkEmptyAddress())
		{
			list_view.setVisibility(View.GONE);
			empty_view_address.setVisibility(View.VISIBLE);
			max_count_hint.setVisibility(View.GONE);
		}
		else
		{
			list_view.setVisibility(View.VISIBLE);
			empty_view_address.setVisibility(View.GONE);
			max_count_hint.setVisibility(View.VISIBLE);
			reflashListView();
		}
		
		
	}


	private boolean checkEmptyAddress()
	{	
		
		for (int i = 0; i < MAX_ADDRESS_COUNT; i++) {
			if(OperatingSP.getBoolean(AddressManagerActivity.this, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i, OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT) == true)
			{
				return false;
			}
		}
		
		return true;
	}
	
	

}