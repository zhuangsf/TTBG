package com.android.ttbg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.ttbg.R;

public class HistoryItem
{   

    private Drawable imageDrawable;
    private String goods_lable;       //商品名称
	private String goods_owner;       //获得者
    private String goods_pubilsh_time;     //揭晓时间
    private String goods_current_no;   //当前期数
    
    
    public String getGoods_lable() {
		return goods_lable;
	}

	public void setGoods_lable(String goods_lable) {
		this.goods_lable = goods_lable;
	}

	public String getGoods_owner() {
		return "获得者: "+goods_owner;
	}

	public void setGoods_owner(String goods_owner) {
		this.goods_owner = goods_owner;
	}

	public String getGoods_pubilsh_time() {
		return "结果时间: "+goods_pubilsh_time;
	}

	public void setGoods_pubilsh_time(String goods_pubilsh_time) {
		this.goods_pubilsh_time = goods_pubilsh_time;
	}

	public String getGoods_current_no() {
		return "第"+goods_current_no+"云正在进行";
	}

	public void setGoods_current_no(String goods_current_no) {
		this.goods_current_no = goods_current_no;
	}



    



	public HistoryItem()   
    {   
        super();   
    }   
   
    public void setHistoryItem(Context context,String goods_lable, Drawable imageDrawable,String goods_owner,String goods_pubilsh_time,String goods_current_no)   
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
        this.goods_owner = goods_owner;  
        this.goods_current_no = goods_current_no;
        this.goods_pubilsh_time = goods_pubilsh_time;

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