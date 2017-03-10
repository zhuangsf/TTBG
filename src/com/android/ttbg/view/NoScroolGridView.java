package com.android.ttbg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScroolGridView extends GridView implements Pullable{  
	  
    public NoScroolGridView(Context context) {  
        super(context);  
    }  
  
    public NoScroolGridView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public NoScroolGridView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    //不出现滚动条  
    @Override  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
    
    
	@Override
	public boolean canPullDown()
	{
        if (getCount() == 0)  
        {  
            // 没有item的时候也可以下拉刷新  
            return true;  
        } else if (getFirstVisiblePosition() == 0  
                && getChildAt(0).getTop() >= 0)  
        {  
            // 滑到顶部了  
            return true;  
        } else  
            return false;  
	}

	
	
	@Override
	public boolean canPullUp()
	{
        if (getCount() == 0)  
        {  
            // 没有item的时候也可以上拉加载  
            return true;  
        } else if (getLastVisiblePosition() == (getCount() - 1))  
        {  
            // 滑到底部了  
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null  
                    && getChildAt(  
                            getLastVisiblePosition()  
                                    - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())  
                return true;  
        }  
        return false;  
	}
}  