package com.android.ttbg;

import java.util.ArrayList;
import java.util.List;

import com.android.ttbg.adapter.AllGoodsContentsAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HistoryContentsAdapter;
import com.android.ttbg.adapter.ObtainGoodsAdapter;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.HistoryItem;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.ObtainGoodsItem;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableListView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ObtainGoodsActivity extends Activity {
	
    private ImageView  title_back;
	private ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_obtain_goods);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 intiListView();
	}
	
	
	//public void setObtainGoodsItem(Context context,String goods_lable, Drawable imageDrawable,String goods_luck_code,String goods_pubilsh_time,String goods_join_num,String goods_order_num,String goods_state)   
	   
    private void intiListView() {
    	
    	list_view = ((ListView) findViewById(R.id.list_view));

	   	List<ObtainGoodsItem> hashMapList = new ArrayList<ObtainGoodsItem>();
        //测试数据
        for (int i = 0; i < 20; i++) {

        	ObtainGoodsItem obtainGoodsItem = new ObtainGoodsItem();
        	obtainGoodsItem.setObtainGoodsItem(ObtainGoodsActivity.this, "(第"+i+"云)红米4 16G 全网通 标准版 4G手机 只要一元啦 一元啦一元啦 智能插座", null, 
        			"10000035","7月七日 09:09","30","9459096","已完成");
            hashMapList.add(obtainGoodsItem);

        }

        ObtainGoodsAdapter obtainGoodsAdapter = new ObtainGoodsAdapter(ObtainGoodsActivity.this, hashMapList);

        list_view.setAdapter(obtainGoodsAdapter);
        list_view.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
        list_view.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
			}
		});
    }
}
