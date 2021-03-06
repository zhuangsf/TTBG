package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AllGoodsContentsAdapterList extends BaseAdapter {

	private Context mContext;
    private List<GoodsProperty> goodsRecommandItems = null;
    private AsyncImageLoader imageLoader;
    public AllGoodsContentsAdapterList(Context context, List<GoodsProperty> goodsRecommandItems)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
        this.goodsRecommandItems = goodsRecommandItems;
    }

    public AllGoodsContentsAdapterList(Context context)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
    }

    
    public void setData(List<GoodsProperty> goodsRecommandItems)
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
    public GoodsProperty getItem(int position)
    {
    	GoodsProperty item = null;

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
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_allgood_list, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_allgood_pic_list);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_allgood_name_list);
            viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_allgood_price_list);
            viewHolder.pb_participation = (ProgressBar) convertView.findViewById(R.id.pb_participation);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        GoodsProperty goodsItem = getItem(position);
        if (null != goodsItem)
        {
        	if(goodsItem.getDrawableUrl() != null)
        	{
        		if(imageLoader != null)
        		{
        			imageLoader.downloadImage(goodsItem.getDrawableUrl(), viewHolder.imageView);
        		}
        	}
        	else
        	{
        		viewHolder.imageView.setImageDrawable(goodsItem.getImageDrawable());
        	}
            viewHolder.tv_goods_label.setText(goodsItem.getGoodsTitle());
            viewHolder.tv_goods_price.setText(goodsItem.getMoney());
            viewHolder.pb_participation.setMax(goodsItem.getTotalNum());
            //设置动画效果
            ObjectAnimator animation = ObjectAnimator.ofInt(viewHolder.pb_participation, "progress", 0, goodsItem.getParticipatedNum());
            animation.setDuration(1000); 
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            
        }

        return convertView;
	}

	
	
    private static class ViewHolder
    {
    	ImageView imageView;
        TextView tv_goods_label;
        TextView tv_goods_price;
        ProgressBar pb_participation;
    }
}
