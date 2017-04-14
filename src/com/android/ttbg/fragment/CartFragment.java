package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.AllGoodsContentsAdapterGrid;
import com.android.ttbg.adapter.CartAdapter;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.view.CartProperty;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;




public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();
	private View cartFragment;
	private PullToRefreshLayout ptrl;
	private ListView cart_listview;
	private CartAdapter cartadapter;
	
	private ImageView empty_imageview;
	private TextView empty_textview;
	
	private TextView title_edit_finish;
	
	private boolean bEditMode = false;
    @Override
    protected View initView() {
    	cartFragment = View.inflate(mContext, R.layout.fragment_cart_empty, null);

		if (cartFragment != null) {

			initEmptyCartView(cartFragment);
			initListView(cartFragment);
			initHotRecommend(cartFragment);
		}
		return cartFragment;
    }

    private void initListView(View v) {
		// TODO Auto-generated method stub
    	cart_listview = (ListView) v.findViewById(R.id.cart_listview);
    	cartadapter = new CartAdapter(getActivity());

        cart_listview.setAdapter(cartadapter);
        cart_listview.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				return true;
			}
		});
        cart_listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

			}
		});

        List<CartProperty> hashMapList = new ArrayList<CartProperty>();
        //测试数据
       // setCartItemData(String drawableUrl,String cart_period,String cart_goodsname,boolean cart_ended,
    	//String cart_surplus_count,String cart_limit_count,String cart_tobuy_count,String cart_goods_count)
        for (int i = 0; i < 8; i++) {

            CartProperty cartItem = new CartProperty();
            cartItem.setCartItemData(null,"100818","苹果 ipad 一分钱不要白送啦快点来抢啊",false,"100","5","90","1");
            hashMapList.add(cartItem);
        }
        
        if(hashMapList.size() > 0)
        {
        	if(empty_imageview != null)
        	{
        		empty_imageview.setVisibility(View.GONE);
        	}
        	if(empty_textview != null)
        	{
        		empty_textview.setVisibility(View.GONE);
        	}
        }
        else{
        	title_edit_finish.setVisibility(View.GONE);
        }
        
        cartadapter.setData(hashMapList);

        cartadapter.notifyDataSetChanged();
        
        //要重新设置listview高度,不然只显示一行
        setListViewHeightBasedOnChildren(cart_listview);
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

	private void initHotRecommend(View v) {
		// TODO Auto-generated method stub
    	NoScroolGridView gridView = (NoScroolGridView) v.findViewById(R.id.gridview_hot_recommend);
    	
    	List<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
        //测试数据
        for (int i = 0; i < 8; i++) {

            GoodsProperty goodsRecommandItem = new GoodsProperty();
            goodsRecommandItem.setGoodsItem(getActivity(), "测试测    "+i+"   试测试,要两行啊要两行,这么多字够了吗", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        HotRecommendAdapter goodsRecommendAdapter = new HotRecommendAdapter(getActivity(), hashMapList);

        gridView.setAdapter(goodsRecommendAdapter);
	}

	private void initEmptyCartView(View v) {
		// TODO Auto-generated method stub
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.ptr_cart_refresh));
		ptrl.setOnRefreshListener(new MyListener());
		
		empty_imageview = (ImageView) v.findViewById(R.id.empty_imageview);
		empty_textview = (TextView) v.findViewById(R.id.empty_textview);
		
		title_edit_finish  = (TextView) v.findViewById(R.id.title_edit_finish);
		//title_edit_finish.setVisibility(View.GONE);
		title_edit_finish.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	if(cartadapter != null)
	        	{
	        		if(bEditMode)
	        		{
	        			bEditMode = false;
	        			cartadapter.setEditMode(false);
	        			title_edit_finish.setText("编辑");
	        		}
	        		else
	        		{
	        			bEditMode = true;
	        			cartadapter.setEditMode(true);
	        			title_edit_finish.setText("完成");
	        		}
	        		if(cartadapter != null)
	                {
	        			cartadapter.notifyDataSetChanged();
	                }
	        	}
	        }  
	  }); 
	}

	@Override
    protected void initData() {
        Log.e(TAG, "CareAboutFragment  initData...");
        super.initData();
    }

	@Override
	protected String getPageName() {
		return CartFragment.class.getName();
	}
}
