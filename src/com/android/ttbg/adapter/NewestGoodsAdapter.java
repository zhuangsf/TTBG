package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.countdownview.CountdownView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewestGoodsAdapter extends BaseAdapter {

	private Context mContext;
    private List<GoodsProperty> goodsRecommandItems = null;
    public NewestGoodsAdapter(Context context, List<GoodsProperty> goodsRecommandItems)
    {
        mContext = context;
        this.goodsRecommandItems = goodsRecommandItems;
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
            convertView = mInflater.inflate(R.layout.item_newest_raffling, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_pic_newest_countdown_grid);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_goodsname_newest_countdown_grid);
            viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_price_newest_countdown_grid);
            
            viewHolder.newest_countdown_view = (CountdownView)convertView.findViewById(R.id.newest_countdown_view);
            long time211 = (long)3 * 60 * 1000;
            viewHolder.newest_countdown_view.start(time211);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        GoodsProperty goodsRecommandItem = getItem(position);
        Utils.Log("goodsRecommandItem = "+goodsRecommandItem);
        if (null != goodsRecommandItem)
        {
            viewHolder.imageView.setImageDrawable(goodsRecommandItem.getImageDrawable());
            viewHolder.tv_goods_label.setText(goodsRecommandItem.getGoodsTitle());
            viewHolder.tv_goods_price.setText(goodsRecommandItem.getGoods_price());
            
            long time211 = (long)3 * 60 * 1000;
            viewHolder.newest_countdown_view.start(time211);
            convertView.setTag(viewHolder);
        }

        return convertView;
	}

	
	
    private static class ViewHolder
    {
    	ImageView imageView;
        TextView tv_goods_label;
        TextView tv_goods_price;
        CountdownView newest_countdown_view;
    }
}
