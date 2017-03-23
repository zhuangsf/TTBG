package com.android.ttbg.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsRecommandItem;

public class GoodsRecommendAdapter extends BaseAdapter
{
    private Context mContext = null;
    private List<GoodsRecommandItem> goodsRecommandItems = null;

    public GoodsRecommendAdapter(Context context, List<GoodsRecommandItem> goodsRecommandItems)
    {
        mContext = context;
        this.goodsRecommandItems = goodsRecommandItems;
    }

    public void setData(List<GoodsRecommandItem> goodsRecommandItems)
    {
    	this.goodsRecommandItems = goodsRecommandItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != goodsRecommandItems)
        {
            count = goodsRecommandItems.size();
        }
        return count;
    }

    @Override
    public GoodsRecommandItem getItem(int position)
    {
    	GoodsRecommandItem item = null;

        if (null != goodsRecommandItems)
        {
            item = goodsRecommandItems.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_home_recommendation, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_goods_pic);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_good_sname);
            viewHolder.tv_participated_num = (TextView) convertView
                    .findViewById(R.id.tv_participated_num);
            viewHolder.tv_total_num = (TextView) convertView
                    .findViewById(R.id.tv_total_num);
            viewHolder.tv_surplus_num = (TextView) convertView
                    .findViewById(R.id.tv_surplus_num);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        GoodsRecommandItem goodsRecommandItem = getItem(position);
        if (null != goodsRecommandItem)
        {
            viewHolder.imageView.setImageDrawable(goodsRecommandItem.getImageDrawable());
            viewHolder.tv_goods_label.setText(goodsRecommandItem.getGoodsLable());
            viewHolder.tv_participated_num.setText(goodsRecommandItem.getParticipatedNum()+"");
            viewHolder.tv_total_num.setText(goodsRecommandItem.getTotalNum()+"");
            viewHolder.tv_surplus_num.setText(goodsRecommandItem.getSurplusNum()+"");
        }

        return convertView;
    }

    private static class ViewHolder
    {
    	ImageView imageView;
        TextView tv_goods_label;
        TextView tv_participated_num;
        TextView tv_total_num;
        TextView tv_surplus_num;
    }


    
    


}
