package com.android.ttbg.view;

public class AddressItem {
	private int id;
	private String name;
	private String phone;
	private String addressDetail;
	private Boolean bDefault;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getAddressDetail() {
		return addressDetail;
	}


	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}


	public Boolean getbDefault() {
		return bDefault;
	}


	public void setbDefault(Boolean bDefault) {
		this.bDefault = bDefault;
	}


	public AddressItem()   
    {   
        super();   
    }   
	
	
	public void setAddressMessage(int id,String name,String phone,String aera,String address,String code,boolean bDefault)
	{
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.addressDetail = aera+" "+address+" "+code;
		this.bDefault = bDefault;
	}
}
