package com.android.ttbg;



import java.util.ArrayList;

import org.json.JSONArray;

import com.android.ttbg.pickerviewbean.GetJsonDataUtil;
import com.android.ttbg.pickerviewbean.JsonBean;
import com.android.ttbg.util.OperatingSP;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

//启动的时候先判断一下本地是否有保存,有的话,就用本地的,不然就用服务器的
//总共存4个

public class AddressEditActivity extends ActivityPack {
	
	
	
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
	
	
	
    private ImageView  title_back;
    private TextView  title_save;
    private EditText et_address_name;
    private EditText et_address_phone;
    private EditText et_address_area;
    private EditText et_address_addr;
    private EditText et_address_zip;
    
    private TextView tv_tips_recriptor_name_error;
    
    private ToggleButton sb_edit_address_set_default;
    
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了

                        Toast.makeText(AddressEditActivity.this,"开始解析数据",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Toast.makeText(AddressEditActivity.this,"解析数据成功",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(AddressEditActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    
    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView  pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);

                Toast.makeText(AddressEditActivity.this,tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
            }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_edit);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 
		 title_save = (TextView)findViewById(R.id.title_save);
		 title_save.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	checkAndSave();
		        }


		  }); 
		 
		 
		 et_address_name = (EditText)findViewById(R.id.et_address_name);
		 et_address_phone = (EditText)findViewById(R.id.et_address_phone);
		 et_address_area = (EditText)findViewById(R.id.et_address_area);
		 et_address_addr = (EditText)findViewById(R.id.et_address_addr);
		 et_address_zip = (EditText)findViewById(R.id.et_address_zip);
		 tv_tips_recriptor_name_error = (TextView)findViewById(R.id.tv_tips_recriptor_name_error);
		 tv_tips_recriptor_name_error.setVisibility(View.GONE);
		 
		 
		 mHandler.sendEmptyMessage(MSG_LOAD_DATA);
		 
		 EditText et_address_area = (EditText)findViewById(R.id.et_address_area);
		 et_address_area.setInputType(InputType.TYPE_NULL);
		 InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 imm.hideSoftInputFromWindow(et_address_area.getWindowToken(),0); 
		 et_address_area.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		        	 
		        	inputMethodManager.hideSoftInputFromWindow(AddressEditActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		        	if (isLoaded){
	                    ShowPickerView();
	                }else {
	                    Toast.makeText(AddressEditActivity.this,"数据暂未解析成功，请等待",Toast.LENGTH_SHORT).show();
	                }
		        }


		  }); 
		 
		 //如果只有1个地址时,删除的按钮跟设置为默认的按钮都清空
	}
		 

	private void checkAndSave() {
		// TODO Auto-generated method stub
		if(et_address_name.getText().toString().length() <= 1)
		{
			tv_tips_recriptor_name_error.setVisibility(View.VISIBLE);
			return;
		}
		else{
			tv_tips_recriptor_name_error.setVisibility(View.GONE);
		}
		
		if(et_address_phone.getText().toString().length() != 11)
		{
			Toast.makeText(AddressEditActivity.this, "请填写正确的手机号码", 2000).show();
			return;
		}else if(et_address_addr.getText().toString().length() < 3)
		{
			Toast.makeText(AddressEditActivity.this, "请填写正确的地址,不包含特殊字符", 2000).show();
			return;
		}		
		else if(et_address_zip.getText().toString().length() != 6 && et_address_zip.getText().toString().length() != 0)
		{
			Toast.makeText(AddressEditActivity.this, "邮编格式错误", 2000).show();
			return;
		}
	}  
	
	
	

}