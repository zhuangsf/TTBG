package com.android.ttbg;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.EditTextWithDel;


public class SearchActivity extends ActivityPack {
	private static final String TAG = "SearchActivity";
    private ImageView  title_back;
    private TextView title_tv_search;
    private TextView bt_search_clear_his;
    private ImageView add_menus;
    private Context mContext;
    private EditTextWithDel title_et_search;
    private SimpleAdapter simpleAdapter;
    private ListView lv;
    private List<Map<String,Object>> history_data=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mContext = SearchActivity.this;
		bt_search_clear_his  = (TextView)findViewById(R.id.bt_search_clear_his);
		bt_search_clear_his.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	OperatingSP.setString(mContext, OperatingSP.PREFERENCE_SEARCH_RECORD,  OperatingSP.PREFERENCE_SEARCH_RECORD_DEFAULT);
	        	history_data.clear();
	        	bt_search_clear_his.setVisibility(View.GONE);
        		simpleAdapter.notifyDataSetChanged();
        		setListViewHeightBasedOnChildren(lv);  
	        }  
	  }); 
		title_et_search = (EditTextWithDel)findViewById(R.id.title_et_search);
		
		title_tv_search = (TextView)findViewById(R.id.title_tv_search);
		title_tv_search.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	if(title_et_search.getText().toString().length() != 0)	
		        	{
		        		//跳转到搜索页面
		        		
		        		//保存
		        		StringBuilder stringBuilder = new StringBuilder();  
		        		String historyRecords = OperatingSP.getString(mContext, OperatingSP.PREFERENCE_SEARCH_RECORD, OperatingSP.PREFERENCE_SEARCH_RECORD_DEFAULT);
		        		if(!historyRecords.equals(""))
		        		{
		        			stringBuilder.append(historyRecords);
		        		}
		        		stringBuilder.append(title_et_search.getText().toString());
		        		stringBuilder.append("#");
		        		OperatingSP.setString(mContext, OperatingSP.PREFERENCE_SEARCH_RECORD, stringBuilder.toString());
		        		
		        		Utils.Log("stringBuilder = :"+stringBuilder.toString());

	        			String[] arrayRecords = stringBuilder.toString().split("#");  //用#当分割符
	        		
	        			history_data.clear();
	        			for(int i = 0;i < arrayRecords.length;i++)
	        			{
	        				Utils.Log("arrayRecords["+i+"]  = :"+arrayRecords[i]);
	        				Map<String,Object> map1=new HashMap<String, Object>();
	        				map1.put("item_history",arrayRecords[i]);
	        				history_data.add(map1);
	        			}
	        			if(history_data.size() == 0)
	        			{
	        				bt_search_clear_his.setVisibility(View.GONE);
	        			}
	        			else
	        			{
	        				bt_search_clear_his.setVisibility(View.VISIBLE);
	        			}
		        		
		        		simpleAdapter.notifyDataSetChanged();
		        		setListViewHeightBasedOnChildren(lv);   
		        	}
		        }  
		  }); 
		
		initListView();
		



		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 

		 
	}

	
    private void initListView() {
		// TODO Auto-generated method stub
		lv=(ListView)findViewById(R.id.lv);
		
		
		String historyRecords = OperatingSP.getString(mContext, OperatingSP.PREFERENCE_SEARCH_RECORD, OperatingSP.PREFERENCE_SEARCH_RECORD_DEFAULT);
		if(historyRecords != null &&!historyRecords.equals(""))
		{
			String[] arrayRecords = historyRecords.split("#");  //用_当分割符
		
			history_data.clear();
			for(int i = 0;i < arrayRecords.length;i++)
			{
				Map<String,Object> map1=new HashMap<String, Object>();
				map1.put("item_history",arrayRecords[i]);
				history_data.add(map1);
			}
	
		}
		
		if(history_data.size() == 0)
		{
			bt_search_clear_his.setVisibility(View.GONE);
		}
		else
		{
			bt_search_clear_his.setVisibility(View.VISIBLE);
		}
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		simpleAdapter = new SimpleAdapter(this, history_data, R.layout.item_serach_history_list, new String[]{"item_history"}, new int[]{R.id.tv_search_item});
		lv.setAdapter(simpleAdapter);
		
		 setListViewHeightBasedOnChildren(lv);   
		 
	}


	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
        Utils.Log("history_data.size()  = :"+history_data.size());
        int totalHeight = 0;   
        for (int i = 0, len = history_data.size(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (history_data.size() - 1));   
        Utils.Log("params.height  = :"+params.height);
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }   

}
