package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.R.color;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.AddressItem;
import com.android.ttbg.view.GoodsProperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AddressManagerAdapter extends BaseAdapter {

	private Context mContext;
    private List<AddressItem> addressItems = null;
    public AddressManagerAdapter(Context context, List<AddressItem> addressItems)
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
	public View getView(int position, View convertView, ViewGroup parent) {
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
            	viewHolder.bDefault.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.check_box_agreement_checked), null, null, null);
            }
            else
            {
            	viewHolder.bDefault.setTextColor(0xff999999);     //灰色
            	viewHolder.bDefault.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.check_box_agreement_nochecked), null, null, null);
            }
        }

        return convertView;
	}

	
	
    private static class ViewHolder
    {
    	int id;
    	TextView name;
    	TextView phone;
    	TextView addressDetail;
    	TextView bDefault;
    }
}
