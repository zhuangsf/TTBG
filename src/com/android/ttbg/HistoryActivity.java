package com.android.ttbg;

import java.util.ArrayList;
import java.util.List;

import com.android.ttbg.adapter.AllGoodsContentsAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HistoryContentsAdapter;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.HistoryItem;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class HistoryActivity extends ActivityPack {
	
    private ImageView  title_back;
	private PullToRefreshLayout ptrl;
	private PullableListView pullable_list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 intiListView();
	}
	
	
	
    private void intiListView() {
    	
		ptrl = ((PullToRefreshLayout) findViewById(R.id.prl));
		ptrl.setOnRefreshListener(new MyListener());
		pullable_list_view = (PullableListView) findViewById(R.id.pullable_list_view);
	   	List<HistoryItem> hashMapList = new ArrayList<HistoryItem>();
        //测试数据
        for (int i = 0; i < 20; i++) {

            HistoryItem historyItem = new HistoryItem();
            historyItem.setHistoryItem(HistoryActivity.this, "(第"+i+"云)红米4 16G 全网通 标准版 4G手机 只要一元啦 一元啦一元啦", null, "张三","7月七日 09:09","65432");
            hashMapList.add(historyItem);

        }

        HistoryContentsAdapter historyContentsAdapter = new HistoryContentsAdapter(HistoryActivity.this, hashMapList);

        pullable_list_view.setAdapter(historyContentsAdapter);
        pullable_list_view.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
        pullable_list_view.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				//点击最后一项要记得直接返回
			}
		});
		
		
		
    }
}
