package com.android.ttbg.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextSwitcher;

import com.android.ttbg.R;


public class AllGoodsFragment extends BaseFragment {
    private static final String TAG = AllGoodsFragment.class.getSimpleName();
    private View allGoodsFragment;
    private RadioGroup mRadioGroup; 
    @Override
    protected View initView() {
        Log.e(TAG, "视频页面Fragment页面被初始化了...");
        allGoodsFragment = View.inflate(mContext, R.layout.fragment_allgoods,null);
        
        if(allGoodsFragment != null)
        {
        	initRadioGroup(allGoodsFragment);
        }
        return allGoodsFragment;
    }
    
    

    private void initRadioGroup(View v) {
		// TODO Auto-generated method stub
    	mRadioGroup = (RadioGroup) v.findViewById(R.id.rg_allgoods_classify);  
    	String[] mStringLabel=getActivity().getResources().getStringArray(R.array.classify_string);
    	for(int i=0; i<mStringLabel.length; i++)  
    	{  
    	    RadioButton tempButton = new RadioButton(getActivity());  
    	    tempButton.setBackgroundColor(0xfff5f5f5);   // 设置RadioButton的背景图片  
    	    //tempButton.setButtonDrawable(R.drawable.xxx);           // 设置按钮的样式  
    	    tempButton.setPadding(20, 20, 20, 20);                 // 设置文字距离按钮四周的距离   
    	    tempButton.setText(mStringLabel[i]);  
    	    tempButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
    	    tempButton.setTextColor(0xff666666);

    	    tempButton.setButtonDrawable(android.R.color.transparent);  
    	    mRadioGroup.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
    	}  
    	
    	mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
    	      
    	    @Override  
    	    public void onCheckedChanged(RadioGroup group, int checkedId) {  
    	        // TODO Auto-generated method stub  
    	        RadioButton tempButton = (RadioButton)mRadioGroup.findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton  
    	        // 以下就可以对这个RadioButton进行处理了  
    	    }  
    	});  
		
	}



	@Override
    protected void initData() {
        Log.e(TAG, "视频页面Fragment页面数据被初始化了...");
        super.initData();
    }
    
    
    @Override
   	protected String getPageName() {
   		return AllGoodsFragment.class.getName();
   	}

}
