package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.AddressEditActivity;
import com.android.ttbg.AddressManagerActivity;
import com.android.ttbg.R;
import com.android.ttbg.R.color;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.AddressItem;
import com.android.ttbg.view.GoodsProperty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class AddressManagerAdapter extends BaseAdapter {

	private Activity mContext;
	private Dialog mDialog;
    private List<AddressItem> addressItems = null;
    public AddressManagerAdapter(Activity context, List<AddressItem> addressItems)
    {
        mContext = context;
        this.addressItems = addressItems;
    }

    public void setData(List<AddressItem> addressItems)
    {
    	this.addressItems = addressItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != addressItems)
        {
            count = addressItems.size();
        }
        return count;
    }

    @Override
    public AddressItem getItem(int position)
    {
    	AddressItem item = null;

        if (null != addressItems)
        {
            item = addressItems.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_my_address, null);
            
        	///String name;
        	//String phone;
        	//String aera;
        	//String address;
        	//String code;
        	//Boolean bDefault;
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_my_address_name);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.tv_my_address_phone);
            viewHolder.addressDetail = (TextView) convertView.findViewById(R.id.tv_my_address_detail);
            viewHolder.bDefault = (TextView) convertView.findViewById(R.id.tv_my_address_check);
            viewHolder.iv_check_defult = (ImageView) convertView.findViewById(R.id.iv_check_defult);
            viewHolder.rl_check_defult = (View) convertView.findViewById(R.id.rl_check_defult);
            viewHolder.edit = (TextView) convertView.findViewById(R.id.tv_my_address_edit);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.tv_my_address_delete);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        AddressItem addressItem = getItem(position);
        if (null != addressItem)
        {
            viewHolder.name.setText(addressItem.getName());
            viewHolder.phone.setText(addressItem.getPhone());
            viewHolder.addressDetail.setText(addressItem.getAddressDetail());
            if(addressItem.getbDefault())
            {
            	viewHolder.bDefault.setTextColor(0xffff7700);   //橙色
            	viewHolder.iv_check_defult.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_box_agreement_checked));
            }
            else
            {
            	viewHolder.bDefault.setTextColor(0xff999999);     //灰色
            	viewHolder.iv_check_defult.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_box_agreement_nochecked));
            }
            
            viewHolder.rl_check_defult.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	int activeCountIndex = 0;
			        for (int i = 0; i < AddressManagerActivity.MAX_ADDRESS_COUNT; i++) {
			        	OperatingSP.setBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_DEFAULT+i,false);
			        	
			        	if(OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i,OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT))
			        	{
			        		//如果是激活的,先设置为false,后面再设置成true
			        		AddressItem addressItem1 = getItem(activeCountIndex);
			        		addressItem1.setbDefault(false);
			        		//怎么知道点击了哪个?
			        		if(activeCountIndex == position)
			        		{
			        			OperatingSP.setBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_DEFAULT+i,true);
			        			addressItem1.setbDefault(true);
			        		}
			        		addressItems.set(activeCountIndex, (AddressItem)addressItem1);
			        		activeCountIndex++;
			        	}
			        	
			        }


			        notifyDataSetChanged();

		        }  
		  }); 
            
            
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	
		        	int activeCountIndex = 0;
			        for (int i = 0; i < AddressManagerActivity.MAX_ADDRESS_COUNT; i++) {
		        	
			        	if(OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i,OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT))
			        	{
			        		
			        		//怎么知道点击了哪个?
			        		if(activeCountIndex == position)
			        		{
								Intent intent = new Intent(mContext, AddressEditActivity.class);
								intent.putExtra("sharePreferenceID", i);     //先默认是1,后续要改
								intent.putExtra("EditMode", true);     //先默认是1,后续要改
								mContext.startActivity(intent);
			        			return;
			        		}
			        		activeCountIndex++;
			        	}
			        	
			        }
		        	
		        	
		        
		        	
		        	
		        	


		        }
            });
            
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	int activeCountIndex = 0;
			        for (int i = 0; i < AddressManagerActivity.MAX_ADDRESS_COUNT; i++) {
		        	
			        	if(OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+i,OperatingSP.PREFERENCE_ADDRESS_ACTIVE_DEFAULT))
			        	{
			        		
			        		//怎么知道点击了哪个?
			        		if(activeCountIndex == position)
			        		{
				            	if(OperatingSP.getBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_DEFAULT+i,OperatingSP.PREFERENCE_ADDRESS_DEFAULT_BSET) == true)
				            	{
				        			Toast.makeText(mContext, "默认地址不可删除", 2000).show();
				        			return;
				            	}
			        			showDeleteDialog(i,position);
			        			return;
			        		}
			        		activeCountIndex++;
			        	}
			        	
			        }
		        	
		        	
		        }
            });
        }

        return convertView;
	}

	
	public void showDeleteDialog(final int indexInSharePerference,final int position){         
	       // AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Translucent_NoTitle);  
	        
			mDialog = new Dialog(mContext, R.style.Translucent_NoTitle);
	        LayoutInflater inflater = mContext.getLayoutInflater();  
	        final View layout = inflater.inflate(R.layout.dialog_base, null);//获取自定义布局  
	          
	        TextView tv_message = (TextView)layout.findViewById(R.id.stub_base_dialog);  
	        tv_message.setText("确认删除?");
	        
	        Button button_cancel = (Button)layout.findViewById(R.id.btn_base_dialog_cancel);  
	        button_cancel.setOnClickListener(new OnClickListener() {  
	            @Override  
	            public void onClick(View arg0) {  
	                // TODO Auto-generated method stub  
	            	mDialog.dismiss();
	            }  
	        });     

	        Button button_ok = (Button)layout.findViewById(R.id.btn_base_dialog_measure);  
	        button_ok.setOnClickListener(new OnClickListener() {  
	              
	            @Override  
	            public void onClick(View arg0) {  
	                // TODO Auto-generated method stub  
	            	
                 	mDialog.dismiss();
	            	OperatingSP.setBoolean(mContext, OperatingSP.PREFERENCE_ADDRESS_ACTIVE+indexInSharePerference,false);
	            	addressItems.remove(position);
	            	notifyDataSetChanged();
	            	
	            }  
	        });  

	        mDialog.setContentView(layout);  
	        mDialog.show();  
	        
	        WindowManager m = mContext.getWindowManager();  
	        Display display = m.getDefaultDisplay();  //为获取屏幕宽、高  
	        android.view.WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
	        //p.height = (int) (display.getHeight() * 0.3);   //高度设置为屏幕的0.3
	        p.width = (int) (display.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
	        mDialog.getWindow().setAttributes(p);     //设置生效  
	        
	     }  
	
	
    private static class ViewHolder
    {
    	//int id;   //这个是sharepreference里面的id
    	TextView name;
    	TextView phone;
    	TextView addressDetail;
    	TextView bDefault;
    	ImageView iv_check_defult;
    	View rl_check_defult;
    	TextView edit;
    	TextView delete;
    }
}
