package com.android.ttbg;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SearchResultActvity extends Activity {
	private static final String TAG = "SearchResultActvity";
	private TextView tv_search_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		tv_search_result = (TextView) findViewById(R.id.tv_search_result);
		SearchActivity.getActionBar(this, "搜索结果页", R.drawable.actionbar_gradient_red).show();
		doSearchQuery(getIntent()); 
	}

    private void doSearchQuery(Intent intent){  
        if(intent == null) {
            return; 
        } else {
            //如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
            if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            	//获取搜索内容
                String queryString = intent.getStringExtra(SearchManager.QUERY);
                tv_search_result.setText("您输入的搜索文字是："+queryString);
            }  
        }
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.null_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Log.d(TAG, "finish activity");
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
