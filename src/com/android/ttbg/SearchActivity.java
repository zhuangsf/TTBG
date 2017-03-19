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

import com.android.ttbg.util.Utils;


public class SearchActivity extends Activity {
	private static final String TAG = "SearchActivity";
    private ImageView  title_back;

    private ImageView add_menus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ListView lv=(ListView)findViewById(R.id.lv);
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		
		
		for(int i = 0;i < 20;i++)
		{
			Map<String,Object> map1=new HashMap<String, Object>();
			map1.put("item_history", "iphone7");
			data.add(map1);
		}
	
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		lv.setAdapter(new SimpleAdapter
				(this, data, R.layout.item_serach_history_list, new String[]{"item_history"}, new int[]{R.id.tv_search_item}));
		
		 setListViewHeightBasedOnChildren(lv);   
		 
		 
		 
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 

		 
	}

	
    public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }   

}
