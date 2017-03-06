package com.android.ttbg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.ttbg.R;

public class GoodsRecommandItem
{   

    private Drawable imageDrawable;
    private String goods_lable;  

    private int participated_num;   //已参与人数
    private int total_num;			//总人数
    private int surplus_num;		//剩余人数
      
    public GoodsRecommandItem()   
    {   
        super();   
    }   
   
    public void setGoodsRecommandItem(Context context,String goods_lable, Drawable imageDrawable,int participated_num,int total_num,int surplus_num)   
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
        this.participated_num = participated_num;  
        this.total_num = total_num;
        this.surplus_num = surplus_num;
    }   
   
    
    public Drawable getImageDrawable()
    {
    	if(imageDrawable != null)
    	{
    		return imageDrawable;
    	}
    	
    	return null;
    }
    public int getParticipatedNum( )  
    {  
        return participated_num;  
    }  
    public int getTotalNum( )  
    {  
        return total_num;  
    } 
    public int getSurplusNum( )  
    {  
        return surplus_num;  
    } 
    public String getGoodsLable( )  
    {  
        return goods_lable;  
    } 
   

}   

