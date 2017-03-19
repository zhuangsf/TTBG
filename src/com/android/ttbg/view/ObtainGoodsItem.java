package com.android.ttbg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.ttbg.R;

public class ObtainGoodsItem
{   

    private Drawable imageDrawable;
    private String goods_lable;       //商品名称
	private String goods_luck_code;       //幸运云购码
    private String goods_pubilsh_time;     //揭晓时间
    private String goods_join_num;   //参与人数
    private String goods_order_num;   //订单号
    private String goods_state;       //状态
    
    
	public String getGoods_order_num() {
		return "订单号: "+goods_order_num;
	}

	public void setGoods_order_num(String goods_order_num) {
		this.goods_order_num = goods_order_num;
	}

	public String getGoods_state() {
		return goods_state;
	}

	public void setGoods_state(String goods_state) {
		this.goods_state = goods_state;
	}

	public String getGoods_lable() {
		return goods_lable;
	}

	public void setGoods_lable(String goods_lable) {
		this.goods_lable = goods_lable;
	}

	public String getGoods_luck_code() {
		return goods_luck_code;
	}

	public void setGoods_luck_code(String goods_luck_code) {
		this.goods_luck_code = goods_luck_code;
	}

	public String getGoods_pubilsh_time() {
		return "揭晓时间: "+ goods_pubilsh_time;
	}

	public void setGoods_pubilsh_time(String goods_pubilsh_time) {
		this.goods_pubilsh_time = goods_pubilsh_time;
	}

	public String getGoods_join_num() {
		return "本云参与"+goods_join_num+"人次";
	}

	public void setGoods_join_num(String goods_join_num) {
		this.goods_join_num = goods_join_num;
	}


	public ObtainGoodsItem()   
    {   
        super();   
    }   
   
    public void setObtainGoodsItem(Context context,String goods_lable, Drawable imageDrawable,String goods_luck_code,String goods_pubilsh_time,String goods_join_num,String goods_order_num,String goods_state)   
    {   
        this.goods_lable = goods_lable;   
        if(imageDrawable == null)
        {
        	this.imageDrawable = context.getResources().getDrawable(R.drawable.goods_pic_default);
        }
        else
        {
        	this.imageDrawable = imageDrawable;  
        }
        this.goods_luck_code = goods_luck_code;  
        this.goods_join_num = goods_join_num;
        this.goods_pubilsh_time = goods_pubilsh_time;
        this.goods_order_num = goods_order_num;
        this.goods_state = goods_state;

    }   
   
    
    public Drawable getImageDrawable()
    {
    	if(imageDrawable != null)
    	{
    		return imageDrawable;
    	}
    	
    	return null;
    }

    
    
}