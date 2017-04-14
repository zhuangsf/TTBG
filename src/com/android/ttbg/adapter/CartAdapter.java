package com.android.ttbg.adapter;

import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
    private boolean isLimit = false;
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

    
    public void setIsLimit(boolean isLimit)
    {
        this.isLimit = isLimit;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        CartProperty cartItem = getItem(position);
        if (null != cartItem)
        {
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
            
            //暂时不知道是什么,先屏蔽
            viewHolder.tv_cart_tobuy_count.setVisibility(View.GONE);
            
            viewHolder.et_cart_goods_count = (EditText) convertView.findViewById(R.id.et_cart_goods_count);
            viewHolder.iv_cart_delete_goods = (ImageView) convertView.findViewById(R.id.iv_cart_delete_goods);
            viewHolder.btn_cart_decrease = (ImageView) convertView.findViewById(R.id.btn_cart_decrease);
            viewHolder.btn_cart_increase = (ImageView) convertView.findViewById(R.id.btn_cart_increase);
            viewHolder.btn_cart_decrease.setOnClickListener(new ButtonClick(viewHolder.et_cart_goods_count,cartItem.getnCart_surplus_count(),viewHolder.btn_cart_decrease,viewHolder.btn_cart_increase)); 
            viewHolder.btn_cart_increase.setOnClickListener(new ButtonClick(viewHolder.et_cart_goods_count,cartItem.getnCart_surplus_count(),viewHolder.btn_cart_decrease,viewHolder.btn_cart_increase)); 
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


        	if(!cartItem.getCart_ended())//商品还未结束
        	{
	        	if(cartItem.getDrawableUrl() != null)
	        	{
	        		if(imageLoader != null)
	        		{
	        			imageLoader.downloadImage(cartItem.getDrawableUrl(), viewHolder.iv_cart_goodspic);
	        		}
	        	}
	

	            viewHolder.tv_cart_period.setText(cartItem.getCart_period());
	            viewHolder.tv_cart_goodsname.setText(cartItem.getCart_goodsname());
	            
	            
	    	    int fstart,fend;
	            String stringBefore = cartItem.getCart_surplus_count();
	            fstart="剩余".length();  
	            fend=("剩余"+cartItem.getnCart_surplus_count()).length(); 
	            SpannableStringBuilder style=new SpannableStringBuilder(stringBefore);     
	            style.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
	            
	            viewHolder.tv_cart_surplus_count.setText(style);
	            viewHolder.tv_cart_limit_count.setText(cartItem.getCart_limit_count());
	            
	            if(isLimit)
	            {
	            	viewHolder.tv_cart_limit_count.setVisibility(View.VISIBLE);
	            }
	            else
	            {
	            	viewHolder.tv_cart_limit_count.setVisibility(View.GONE);
	            }
	            //不能小于1
	            String str_data = viewHolder.et_cart_goods_count.getText().toString();
                if(! (TextUtils.isEmpty(str_data)  ||  (str_data.trim().length() == 0))  )
	            {
	            	if(Integer.parseInt( viewHolder.et_cart_goods_count.getText().toString() )  == 1)
		            {
	            		
		            	viewHolder.btn_cart_decrease.setImageResource(R.drawable.btn_decrease_disabled);
		            }
		            else
		            {
		            	viewHolder.btn_cart_decrease.setImageResource(R.drawable.btn_increase_normal);
		            }
	            	
		            if(Integer.parseInt( viewHolder.et_cart_goods_count.getText().toString() ) >= cartItem.getnCart_surplus_count())
		            {
		            	viewHolder.btn_cart_increase.setImageResource(R.drawable.btn_increase_disabled);
		            }
		            else
		            {
		            	viewHolder.btn_cart_increase.setImageResource(R.drawable.btn_increase_normal);
		            }
	            }
	            //不能大于剩余人数

	            
	        	if(bEditMode)
	        	{
	        		viewHolder.iv_checkbox.setVisibility(View.VISIBLE);
	        		viewHolder.iv_cart_delete_goods.setVisibility(View.VISIBLE);
	        		viewHolder.tv_cart_period.setVisibility(View.GONE);
	        		viewHolder.tv_cart_goodsname.setVisibility(View.GONE);
	        	}
	        	else
	        	{
	        		viewHolder.iv_checkbox.setVisibility(View.GONE);
	        		viewHolder.iv_cart_delete_goods.setVisibility(View.GONE);
	        		viewHolder.tv_cart_period.setVisibility(View.VISIBLE);
	        		viewHolder.tv_cart_goodsname.setVisibility(View.VISIBLE);
	        	}
	            
	            
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
    
	private class ButtonClick implements OnClickListener{

		EditText editview;
		int surplus_count;
		ImageView btn_increase;
		ImageView btn_decrease;

		public ButtonClick(EditText editview,int surplus_count,ImageView btn_decrease,ImageView btn_increase) {
			super();
			this.editview = editview;
			this.surplus_count = surplus_count;
			this.btn_decrease = btn_decrease;
			this.btn_increase = btn_increase;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            String str_data = editview.getText().toString();
            if(TextUtils.isEmpty(str_data)  ||  (str_data.trim().length() == 0))
            {
            	return;
            }
            int num = Integer.parseInt(editview.getText().toString());
			switch (v.getId()) {
			case R.id.btn_cart_increase:
				//加
				if (num < surplus_count) {
					editview.setText((++num) + "");
				}else {
					editview.setText(surplus_count+"");
				}
				break;
			case R.id.btn_cart_decrease:
				//减
				if (num > 1) {
					editview.setText((--num) + "");
				}else {
					editview.setText("1");
				}
				break;
			}
			
			updateBtnBackGround(num);
		}

		private void updateBtnBackGround(int num) {
			// TODO Auto-generated method stub
        	if(num == 1)
            {
        		
        		btn_decrease.setImageResource(R.drawable.btn_decrease_disabled);
            }
            else
            {
            	btn_decrease.setImageResource(R.drawable.btn_increase_normal);
            }
        	
            if(num >= surplus_count)
            {
            	btn_increase.setImageResource(R.drawable.btn_increase_disabled);
            }
            else
            {
            	btn_increase.setImageResource(R.drawable.btn_increase_normal);
            }
		}
		
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
        
        ImageView btn_cart_decrease;
        ImageView btn_cart_increase;
    }


    
    


}
