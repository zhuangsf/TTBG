package com.android.ttbg.view;

import android.content.Context;

public class CartProperty {
	
	private String drawableUrl;
	//private String cart_goods_label;  //这个没用,先屏蔽
	private String cart_period;
	private String cart_goodsname;
	private boolean cart_ended;
	private String cart_surplus_count;
	private String cart_limit_count;
	private String cart_tobuy_count;
	private String cart_goods_count;
	
	
	
	public void setCartItemData(String drawableUrl,String cart_period,String cart_goodsname,boolean cart_ended,
			String cart_surplus_count,String cart_limit_count,String cart_tobuy_count,String cart_goods_count)
	{
		this.drawableUrl = drawableUrl;
		this.cart_period = cart_period;
		this.cart_goodsname = cart_goodsname;
		this.cart_ended = cart_ended;
		this.cart_surplus_count = cart_surplus_count;
		this.cart_limit_count = cart_limit_count;
		this.cart_tobuy_count = cart_tobuy_count;
		this.cart_goods_count = cart_goods_count;
	}
	
	public String getDrawableUrl() {
		return drawableUrl;
	}
	public void setDrawableUrl(String drawableUrl) {
		this.drawableUrl = drawableUrl;
	}
	public String getCart_period() {
		return cart_period;
	}
	public void setCart_period(String cart_period) {
		this.cart_period = cart_period;
	}
	public String getCart_goodsname() {
		return cart_goodsname;
	}
	public void setCart_goodsname(String cart_goodsname) {
		this.cart_goodsname = cart_goodsname;
	}
	public boolean getCart_ended() {
		return cart_ended;
	}
	public void setCart_ended(boolean cart_ended) {
		this.cart_ended = cart_ended;
	}
	public String getCart_surplus_count() {
		return cart_surplus_count;
	}
	public void setCart_surplus_count(String cart_surplus_count) {
		this.cart_surplus_count = cart_surplus_count;
	}
	public String getCart_limit_count() {
		return cart_limit_count;
	}
	public void setCart_limit_count(String cart_limit_count) {
		this.cart_limit_count = cart_limit_count;
	}
	public String getCart_tobuy_count() {
		return cart_tobuy_count;
	}
	public void setCart_tobuy_count(String cart_tobuy_count) {
		this.cart_tobuy_count = cart_tobuy_count;
	}
	public String getCart_goods_count() {
		return cart_goods_count;
	}
	public void setCart_goods_count(String cart_goods_count) {
		this.cart_goods_count = cart_goods_count;
	}
	

}
