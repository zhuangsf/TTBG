package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.HistoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoryContentsAdapter extends BaseAdapter {

	private Context mContext;
    private List<HistoryItem> HistoryItems = null;
    public HistoryContentsAdapter(Context context, List<HistoryItem> HistoryItems)
    {
        mContext = context;
        this.HistoryItems = HistoryItems;
    }

    public void setData(List<HistoryItem> HistoryItems)
    {
    	this.HistoryItems = HistoryItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != HistoryItems)
        {
            count = HistoryItems.size();
        }
        return count+1;   //多一个是无更多提示的textview
    }

    @Override
    public HistoryItem getItem(int position)
    {
    	HistoryItem item = null;

        if (null != HistoryItems)
        {
            item = HistoryItems.get(position);
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
            convertView = mInflater.inflate(R.layout.item_my_record_end, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_record_pic);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_record_name);
            viewHolder.tv_goods_owner = (TextView) convertView.findViewById(R.id.tv_record_owner);
            viewHolder.tv_goods_pubilsh_time = (TextView) convertView.findViewById(R.id.tv_record_end_time);
            viewHolder.tv_goods_current_no = (TextView) convertView.findViewById(R.id.tv_record_last);
            
            viewHolder.rl_body = (RelativeLayout) convertView.findViewById(R.id.rl_body);
            viewHolder.tv_norecord_hint = (TextView) convertView.findViewById(R.id.tv_norecord_hint);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        if(position == (getCount() - 1))
        {	
        viewHolder.rl_body.setVisibility(View.GONE);
        viewHolder.tv_norecord_hint.setVisibility(View.VISIBLE);
        return convertView;
        }
        
        HistoryItem HistoryItem = getItem(position);
        Utils.Log("HistoryItem = "+HistoryItem);
        if (null != HistoryItem)
        {
            viewHolder.imageView.setImageDrawable(HistoryItem.getImageDrawable());
            viewHolder.tv_goods_label.setText(HistoryItem.getGoods_lable());
            viewHolder.tv_goods_owner.setText(HistoryItem.getGoods_owner());
            viewHolder.tv_goods_pubilsh_time.setText(HistoryItem.getGoods_pubilsh_time());
            viewHolder.tv_goods_current_no.setText(HistoryItem.getGoods_current_no());
            
            viewHolder.rl_body.setVisibility(View.VISIBLE);
            viewHolder.tv_norecord_hint.setVisibility(View.GONE);

        }

        return convertView;
	}


	
    private static class ViewHolder
    {
    	ImageView imageView;
    	RelativeLayout rl_body;
        TextView tv_goods_label;//商品名称
        TextView tv_goods_owner;//获得者
        TextView tv_goods_pubilsh_time;//揭晓时间
        TextView tv_goods_current_no;//当前期数
        
        TextView tv_norecord_hint;  //最近三个月的提示语
    }
}
