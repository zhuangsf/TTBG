package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.AllGoodsContentsAdapterList;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.json.JsonControl;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;
import com.android.ttbg.view.PullableGridView;
import com.android.ttbg.view.PullableListView;
import com.finddreams.adbanner.ImagePagerAdapter;


public class NewestFragment extends BaseFragment {
    private static final String TAG = NewestFragment.class.getSimpleName();
    private View newestFragmentView;
    private PullableGridView newestGridView;
	private PullToRefreshLayout ptrl;
	private ArrayList<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
	private NewestGoodsAdapter newestGoodsAdapter;
	private Dialog mLoadingDialog;
	//最新揭晓
	Runnable runnableNewest = new Runnable(){
		  @Override
		  public void run() {
		    //
		    // TODO: http request.
		    //
			JsonControl.httpGet(JsonControl.HOME_PAGE+"apps/ajax/getLotteryList/0/0/20/1", mHandler,JsonControl.JSON_TYPE_NEWEST);
		  }
	};
	
    private Handler mHandler= new Handler()
    {
   		@Override
   		public void handleMessage(Message msg) {
   			switch (msg.what) {
   			case JsonControl.GET_SUCCESS_MSG:
   			{
            	JSONObject jsonObject=(JSONObject)msg.obj;
            	Utils.Log("NewestFragment GET_SUCCESS_MSG jsonObject:"+jsonObject+" msg.arg1 = "+msg.arg1);
            	if(jsonObject == null)
            	{
            		return;
            	}
        		JSONObject result;
        		switch(msg.arg1)
        		{
        		case JsonControl.JSON_TYPE_NEWEST:
        		{
        			try {
        			result = new JSONObject(jsonObject.toString());
        			Utils.Log("JsonControl.JSON_TYPE_NEWEST  result = "+result);
        			
        			String bSuccess = result.optString("success","");
        			Utils.Log("getJson JSON_TYPE_NEWEST bSuccess = "+bSuccess);
        			
        			int count = Integer.parseInt(result.optString("count","0"));
        			Utils.Log("getJson JSON_TYPE_NEWEST count = "+count);

        			JSONArray shoplists = result.getJSONArray("shoplists");
	        		int len = shoplists.length();
	        		Utils.Log("getJson JSON_TYPE_NEWEST len = "+len);
	        		if(len == 0)
	        		{
	        			return;
	        		}
	        		
	        		hashMapList.clear();
	        		
	        		
	        		//后续点击,把这个goodsItem传进去,就可以用于直接显示,不用再重新读网络
	        		for(int i =0;i<len;i++){
	        			
    	        		JSONObject obj = shoplists.getJSONObject(i);
    	        		Utils.Log("getJson hashMapList["+i+"] = "+obj.toString());

    	        		String id = obj.getString("id");
    	        		String sid = obj.getString("sid");
    	        		String title = obj.getString("title");
    	        		String title2 = obj.getString("title2");
    	        		String qishu = obj.getString("qishu");
    	        		String money = obj.getString("money");    	        		
    	        		String yunjiage = obj.getString("yunjiage");
    	        		String thumb = obj.getString("thumb");
    	        		String q_end_time = obj.getString("q_end_time");
    	        		String q_uid = obj.getString("q_uid");
    	        		String username = obj.getString("username");
    	        		String userphoto = obj.getString("userphoto");
    	        		String q_buynum = obj.getString("q_buynum");
    	        		String q_user_code = obj.getString("q_user_code");
    	        		String endtime = obj.getString("endtime");
    	        		
    	        		GoodsProperty goodsItem = new GoodsProperty();
    	        		goodsItem.setId(id);
    	        		goodsItem.setSid(sid);
    	        		goodsItem.setTitle(title);
    	        		goodsItem.setTitle2(title2);
    	        		goodsItem.setQishu(qishu);
    	        		goodsItem.setMoney("价值:¥ "+money);
    	        		goodsItem.setYunjiage(yunjiage);
    	        		goodsItem.setThumb(thumb);
    	        		goodsItem.setQ_end_time(q_end_time);
    	        		goodsItem.setQ_uid(q_uid);
    	        		goodsItem.setUsername(username);
    	        		goodsItem.setUserphoto(userphoto);
    	        		goodsItem.setQ_buynum(q_buynum);
    	        		goodsItem.setQ_user_code(q_user_code);
    	        		goodsItem.setEndtime(endtime);
    	                hashMapList.add(goodsItem);
    	      
    	        		}
	        		
	        		Utils.Log("hashMapList size = "+hashMapList.size());
	        		newestGoodsAdapter.setData(hashMapList);
	        		newestGoodsAdapter.notifyDataSetChanged();
	        		hideLoadingDialog();
        			} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
        		}
        		break;
        		default:
        			break;
        		}


            	
   			}
   			break;
   		  }
   			
   		}
   	};
	
	
	public void hideLoadingDialog(){      
		if(mLoadingDialog != null)
        {
			mLoadingDialog.dismiss();
        }
	}
	public void showLoadingDialog(){         
	       // AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Translucent_NoTitle);  
	        if(mLoadingDialog == null)
	        {
			    mLoadingDialog = new Dialog(mContext, R.style.Translucent_Dialog);
		        LayoutInflater inflater = getActivity().getLayoutInflater();  
		        final View layout = inflater.inflate(R.layout.layout_loading_dialog, null);//获取自定义布局  
		        ImageView animationIV;  
		        AnimationDrawable animationDrawable;
		        animationIV = (ImageView) layout.findViewById(R.id.iv_loading_dialog);
		        animationDrawable = (AnimationDrawable) animationIV.getDrawable();
				animationDrawable.start();
				mLoadingDialog.setCancelable(false);
				mLoadingDialog.setCanceledOnTouchOutside(false);
		        mLoadingDialog.setContentView(layout);  
		        mLoadingDialog.show();  
	        }
	        else
	        {
	        	 mLoadingDialog.show();  
	        }
/*	        WindowManager m = getActivity().getWindowManager();  
	        Display display = m.getDefaultDisplay();  //为获取屏幕宽、高  
	        android.view.WindowManager.LayoutParams p = mLoadingDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
	        //p.height = (int) (display.getHeight() * 0.3);   //高度设置为屏幕的0.3
	        p.width = (int) (display.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
	        mLoadingDialog.getWindow().setAttributes(p);     //设置生效  
*/	        
	     }   
	
	
    @Override
    protected View initView() {
        Log.e(TAG, "个人页面Fragment页面被初始化了...");
        newestFragmentView = View.inflate(mContext, R.layout.fragment_newest,null);
        
        
        if(newestFragmentView != null)
        {
        	intiNewestGoodsItems(newestFragmentView);
        //	initAllGoodsContentView(newestFragmentView);
        }
        showLoadingDialog();
        new Thread(runnableNewest).start();	
        return newestFragmentView;
    }

    
    private void intiNewestGoodsItems(View v) {
  
    	
   	newestGridView = (PullableGridView) v.findViewById(R.id.gridview_newest);
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.ptr_newest_refresh));
		ptrl.setOnRefreshListener(new MyListener());
/*    	List<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
        //测试数据
        for (int i = 0; i < 18; i++) {

            GoodsProperty goodsItem = new GoodsProperty();
            goodsItem.setGoodsItem(getActivity(), "测试测    "+i+"   试测试", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsItem);

        }*/

        newestGoodsAdapter = new NewestGoodsAdapter(getActivity());

        newestGridView.setAdapter(newestGoodsAdapter);
        newestGridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
        newestGridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

			}
		});
    }
    
    @Override
    protected void initData() {
        Log.e(TAG, "个人页面Fragment页面数据被初始化了...");
        super.initData();
    }

    
    
    @Override
   	protected String getPageName() {
   		return NewestFragment.class.getName();
   	}
}
