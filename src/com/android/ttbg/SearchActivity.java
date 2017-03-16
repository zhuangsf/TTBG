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
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class SearchActivity extends Activity {
	private static final String TAG = "SearchActivity";
	private TextView tv_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		tv_search = (TextView) findViewById(R.id.tv_search);
		getActionBar(this, "搜索页", R.drawable.actionbar_gradient_blue).show();
	}

	@SuppressLint("InflateParams")
	public static ActionBar getActionBar(Activity act, String title, int bgId) {
		ActionBar actionBar = act.getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(false);
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
			if (bgId > 0) {
				actionBar.setBackgroundDrawable(act.getResources().getDrawable(bgId));
			}
			actionBar.setTitle(title);
		} else {
			Toast.makeText(act, "无法获得ActionBar", Toast.LENGTH_SHORT).show();
		}
		return actionBar;
	}
	
	private void initSearchView(Menu menu) {
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    if(searchView == null){
	        Log.d(TAG, "Fail to get SearchView.");
	    } else {
	    	//设置搜索框默认自动缩小为图标
	    	searchView.setIconifiedByDefault(true);
	    	//设置是否显示搜索按钮。搜索按钮只显示一个箭头图标，Android暂不支持显示文本。
	    	//查看Android源码，搜索按钮用的控件是ImageView，所以。。。
	    	searchView.setSubmitButtonEnabled(true);
	    	//设置搜索框内的默认显示的提示文本
	    	//searchView.setQueryHint(getResources().getString(R.string.please_input));
	    	
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        //关联搜索结果的activity
	        ComponentName cn = new ComponentName(this, SearchResultActvity.class);
	        //从activity中获取相关搜索信息，就是searchable的xml设置
	        SearchableInfo info = searchManager.getSearchableInfo(cn); 
	        if(info == null){ 
	            Log.d(TAG, "Fail to get SearchResultActvity."); 
	        }      
	        //将activity的搜索信息与search view关联
	        searchView.setSearchableInfo(info); 
	    }
	}
	
	//如果设备有物理菜单按键，需要将其屏蔽才能显示OverflowMenu
	//API18以下需要该函数在右上角强制显示选项菜单
	public static void forceShowOverflowMenu(Context context) {
		try {
			ViewConfiguration config = ViewConfiguration.get(context);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "forceShowOverflowMenu err="+e.toString());
		}
	}

    //显示OverflowMenu的Icon 
	public static void setOverflowIconVisible(int featureId, Menu menu) {  
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
                try {  
                    Method m = menu.getClass().getDeclaredMethod(  
                            "setOptionalIconsVisible", Boolean.TYPE);  
                    m.setAccessible(true);  
                    m.invoke(menu, true);  
                } catch (Exception e) {  
                    Log.d(TAG, e.getMessage());  
                }  
            }  
        }  
    } 	

    @Override  
    public boolean onMenuOpened(int featureId, Menu menu) {  
    	//显示菜单项左侧的图标
        setOverflowIconVisible(featureId, menu);  
        return super.onMenuOpened(featureId, menu);  
    }  
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		//对搜索框做初始化
		initSearchView(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_about) {
			Toast.makeText(this, "这个是顶部导航栏的演示demo", Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_quit) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
