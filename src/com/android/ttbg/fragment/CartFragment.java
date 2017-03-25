package com.android.ttbg.fragment;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.android.ttbg.MyListener;
import com.android.ttbg.R;
import com.android.ttbg.adapter.GoodsRecommendAdapter;
import com.android.ttbg.adapter.HotRecommendAdapter;
import com.android.ttbg.adapter.NewestGoodsAdapter;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.NoScroolGridView;
import com.android.ttbg.view.PullToRefreshLayout;




public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();
	private View cartFragment;
	private PullToRefreshLayout ptrl;
    @Override
    protected View initView() {
    	cartFragment = View.inflate(mContext, R.layout.fragment_cart_empty, null);

		if (cartFragment != null) {

			initEmptyCartView(cartFragment);
			initHotRecommend(cartFragment);
		}
		return cartFragment;
    }

    private void initHotRecommend(View v) {
		// TODO Auto-generated method stub
    	NoScroolGridView gridView = (NoScroolGridView) v.findViewById(R.id.gridview_hot_recommend);
    	
    	List<GoodsProperty> hashMapList = new ArrayList<GoodsProperty>();
        //测试数据
        for (int i = 0; i < 8; i++) {

            GoodsProperty goodsRecommandItem = new GoodsProperty();
            goodsRecommandItem.setGoodsRecommandItem(getActivity(), "测试测    "+i+"   试测试,要两行啊要两行,这么多字够了吗", null, i*10, i*100, i*90,"价值:¥ 888.88");
            hashMapList.add(goodsRecommandItem);

        }

        HotRecommendAdapter goodsRecommendAdapter = new HotRecommendAdapter(getActivity(), hashMapList);

        gridView.setAdapter(goodsRecommendAdapter);
	}

	private void initEmptyCartView(View v) {
		// TODO Auto-generated method stub
		ptrl = ((PullToRefreshLayout) v.findViewById(R.id.ptr_cart_refresh));
		ptrl.setOnRefreshListener(new MyListener());
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
