package com.android.ttbg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.ttbg.R;



//用于描述商品的所有属性
public class GoodsProperty
{   

    private Drawable imageDrawable;


    private int participated_num;   //已参与人数
    private int total_num;			//总人数
    private int surplus_num;		//剩余人数
    private String goods_price;     //	商品单价
      
    
    private String id;	//商品id
	private String sid;	//	商品系列id
    private String cateid;	//	分类id
    private String title;	//	商品标题
    private String title2;	//	商品标题2
    private String qishu;	//	期数
    private String money;	//	价值
    private String thumb;	//	商品图片
    private String brandid;	//	品牌id
    private String brandname;	//	品牌名称
    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCateid() {
		return cateid;
	}

	public void setCateid(String cateid) {
		this.cateid = cateid;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getQishu() {
		return qishu;
	}

	public void setQishu(String qishu) {
		this.qishu = qishu;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}


	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}



    
    
    
    public String getGoods_price() {
		return goods_price;
	}

	public GoodsProperty()   
    {   
        super();   
    }   
   
    public void setGoodsRecommandItem(Context context,String title, Drawable imageDrawable,int participated_num,int total_num,int surplus_num,String goods_price)   
    {   
        this.title = title;   
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
        this.goods_price = goods_price;
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
    public String getGoodsTitle( )  
    {  
        return title;  
    } 
   

}   

