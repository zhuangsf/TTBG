package com.android.ttbg.adapter;

import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.ttbg.R;
import com.android.ttbg.json.JsonControl;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.CartProperty;

public class CartAdapter extends BaseAdapter
{
    private Context mContext = null;
    private List<CartProperty> cartItems = null;
    private AsyncImageLoader imageLoader;
    private boolean bEditMode = false;
    public CartAdapter(Context context)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
    }
    
    public CartAdapter(Context context, List<CartProperty> cartItems)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
        this.cartItems = cartItems;
    }

    public void setData(List<CartProperty> cartItems)
    {
    	this.cartItems = cartItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != cartItems)
        {
            count = cartItems.size();
        }
        return count;
    }

    @Override
    public CartProperty getItem(int position)
    {
    	CartProperty item = null;

        if (null != cartItems)
        {
            item = cartItems.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    public void setEditMode(boolean bEditMode)
    {
        this.bEditMode = bEditMode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_cart, null);
            

            viewHolder.iv_cart_goodspic = (ImageView) convertView.findViewById(R.id.iv_cart_goodspic);
            viewHolder.tv_cart_goods_label = (TextView) convertView.findViewById(R.id.tv_cart_goods_label);
            viewHolder.iv_checkbox = (CheckBox) convertView.findViewById(R.id.tb_select_goods);
            viewHolder.tv_cart_period = (TextView) convertView.findViewById(R.id.tv_cart_period);
            viewHolder.tv_cart_goodsname = (TextView) convertView.findViewById(R.id.tv_cart_goodsname);
            viewHolder.tv_cart_ended = (TextView) convertView.findViewById(R.id.tv_cart_ended);
            viewHolder.tv_cart_surplus_count = (TextView) convertView.findViewById(R.id.tv_cart_surplus_count);
            viewHolder.tv_cart_limit_count = (TextView) convertView.findViewById(R.id.tv_cart_limit_count);
            viewHolder.tv_cart_tobuy_count = (TextView) convertView.findViewById(R.id.tv_cart_tobuy_count);
            viewHolder.et_cart_goods_count = (EditText) convertView.findViewById(R.id.et_cart_goods_count);
            viewHolder.iv_cart_delete_goods = (ImageView) convertView.findViewById(R.id.iv_cart_delete_goods);
            
            viewHolder.iv_cart_delete_goods.setOnClickListener(new View.OnClickListener() {  
    	        public void onClick(View v) {  

    	        }  
    	    }); 
            viewHolder.layout_over_goods_delete = (View) convertView.findViewById(R.id.layout_over_goods_delete); 

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        CartProperty cartItem = getItem(position);
        if (null != cartItem)
        {
        	if(!cartItem.getCart_ended())//商品还未结束
        	{
	        	if(cartItem.getDrawableUrl() != null)
	        	{
	        		if(imageLoader != null)
	        		{
	        			imageLoader.downloadImage(cartItem.getDrawableUrl(), viewHolder.iv_cart_goodspic);
	        		}
	        	}
	
	        	if(bEditMode)
	        	{
	        		viewHolder.iv_checkbox.setVisibility(View.VISIBLE);
	        		viewHolder.iv_cart_delete_goods.setVisibility(View.VISIBLE);
	        	}
	        	else
	        	{
	        		viewHolder.iv_checkbox.setVisibility(View.GONE);
	        		viewHolder.iv_cart_delete_goods.setVisibility(View.GONE);
	        	}
	            viewHolder.tv_cart_period.setText(cartItem.getCart_period());
	            viewHolder.tv_cart_goodsname.setText(cartItem.getCart_goodsname());
	            viewHolder.tv_cart_surplus_count.setText(cartItem.getCart_surplus_count());
	            viewHolder.tv_cart_limit_count.setText(cartItem.getCart_limit_count());
	            viewHolder.et_cart_goods_count.setText(cartItem.getCart_goods_count());
        	}
        	else//商品已结束
        	{
        		//
        	}
        	
        	if(position == (getCount() - 1)) //最后一项,还要判断是否有已结束的商品
        	{
        		viewHolder.layout_over_goods_delete.setVisibility(View.VISIBLE);
        	}
        	else
        	{
        		viewHolder.layout_over_goods_delete.setVisibility(View.GONE);
        	}

        }

        return convertView;
    }
    


    private static class ViewHolder
    {
    	ImageView iv_cart_goodspic;
    	CheckBox iv_checkbox;
        TextView tv_cart_goods_label;  //商品标签,无作用,不显示
        TextView tv_cart_period;    //第几期
        TextView tv_cart_goodsname;   //商品名称
        TextView tv_cart_ended;      //显示已结束
        TextView tv_cart_surplus_count;    //剩余人次
        TextView tv_cart_limit_count;     //限购人次  ,会显示成剩余xxx人次,限购5人次
        TextView tv_cart_tobuy_count;       //这个还不知道干嘛用
        EditText et_cart_goods_count;      //显示要买多少次
        ImageView iv_cart_delete_goods;   //删除垃圾桶
        View layout_over_goods_delete;  //清空已结束商品,就显示最后一行
    }


    
    


}
