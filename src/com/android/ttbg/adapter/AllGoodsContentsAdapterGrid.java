package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AllGoodsContentsAdapterGrid extends BaseAdapter {

	private Context mContext;
    private List<GoodsProperty> goodsItems = null;
    public AllGoodsContentsAdapterGrid(Context context, List<GoodsProperty> goodsItems)
    {
        mContext = context;
        this.goodsItems = goodsItems;
    }

    public void setData(List<GoodsProperty> goodsItems)
    {
    	this.goodsItems = goodsItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != goodsItems)
        {
            count = goodsItems.size();
        }
        return count;
    }

    @Override
    public GoodsProperty getItem(int position)
    {
    	GoodsProperty item = null;

        if (null != goodsItems)
        {
            item = goodsItems.get(position);
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
            convertView = mInflater.inflate(R.layout.item_allgood_grid, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_allgoods_good_grid);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_allgoods_grid_name);
            viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_allgoods_grid_price);
            viewHolder.pb_participation = (ProgressBar) convertView.findViewById(R.id.cpb_allgoods_grid);
            viewHolder.item_rightline = (View)  convertView.findViewById(R.id.item_rightline);
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
            viewHolder.imageView.setImageDrawable(goodsItem.getImageDrawable());
            viewHolder.tv_goods_label.setText(goodsItem.getGoodsTitle());
            viewHolder.tv_goods_price.setText(goodsItem.getGoods_price());
            if(position%2!=0) {
            	viewHolder.item_rightline.setVisibility(View.GONE);
            }
        }

        return convertView;
	}

	
	
    private static class ViewHolder
    {
    	ImageView imageView;
        TextView tv_goods_label;
        TextView tv_goods_price;
        ProgressBar pb_participation;
        View item_rightline;
    }
}
