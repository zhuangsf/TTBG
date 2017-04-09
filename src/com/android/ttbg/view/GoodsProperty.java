package com.android.ttbg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.ttbg.R;
import com.android.ttbg.json.JsonControl;



//用于描述商品的所有属性
public class GoodsProperty
{   

    private Drawable imageDrawable;



	private int participated_num;   //已参与人数
    private int total_num;			//总人数
    private int surplus_num;		//剩余人数
//    private String goods_price;     //	商品单价
    
    

    private String id;   //	商品id
    private String sid;   //	商品系列id
    private String title;   //	商品标题
    private String title2;   //	商品标题2
    private String qishu;   //	期数
    private String money;   //	价值
    private String yunjiage;   //	商品单价
    private String thumb;   //	商品图片
    private String drawableUrl;  //商品图片完整路径,跟thumb有点多余,后续修改
    private String q_end_time;   //	商品揭晓时间
    private String q_uid;   //	中奖用户id
    private String username;   //	中奖用户名称
    private String userphoto;   //	中奖用户头像
    private String q_buynum;   //	中奖用户购买次数
    private String q_user_code;   //	中奖用户中奖码



    
    public String getDrawableUrl() {
		return drawableUrl;
	}

	public void setDrawableUrl(String drawableUrl) {
		this.drawableUrl = drawableUrl;
	}
    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getYunjiage() {
		return yunjiage;
	}

	public void setYunjiage(String yunjiage) {
		this.yunjiage = yunjiage;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
		this.drawableUrl = JsonControl.FILE_HEAD+thumb;
	}

	public String getQ_end_time() {
		return q_end_time;
	}

	public void setQ_end_time(String q_end_time) {
		this.q_end_time = q_end_time;
	}

	public String getQ_uid() {
		return q_uid;
	}

	public void setQ_uid(String q_uid) {
		this.q_uid = q_uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getQ_buynum() {
		return q_buynum;
	}

	public void setQ_buynum(String q_buynum) {
		this.q_buynum = q_buynum;
	}

	public String getQ_user_code() {
		return q_user_code;
	}

	public void setQ_user_code(String q_user_code) {
		this.q_user_code = q_user_code;
	}


	public GoodsProperty()   
    {   
        super();   
    }   
   
    public void setGoodsItem(Context context,String title, Drawable imageDrawable,int participated_num,int total_num,int surplus_num,String money)   
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
        this.money = money;
    }   
    public void setGoodsItemUrl(Context context,String title, String drawableUrl,int participated_num,int total_num,int surplus_num,String money)   
    {   
        this.title = title;   
        this.drawableUrl = drawableUrl;
        this.participated_num = participated_num;  
        this.total_num = total_num;
        this.surplus_num = surplus_num;
        this.money = money;
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

