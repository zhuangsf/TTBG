package com.android.yybg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;



import java.util.ArrayList;
import java.util.List;

import com.android.yybg.R;


/**
 *
 * 跑马灯
 */
public class LampView extends FrameLayout {

    VerticalLampView lampView;

    private Context mContext = null;
    private List<LampBean> list=new ArrayList<>();

    public LampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        setView();
    }

    private void setView() {
    	View view = inflate(getContext(), R.layout.lamp_layout, this);
    	lampView = (VerticalLampView)view.findViewById(R.id.lamp_view);
        initData();
        initView();
    }

    private void initView() {
    	if(lampView == null)
    	{
    		return;
    	}
        lampView.setData((ArrayList<LampBean>) list);
        lampView.setTextSize(15);
        lampView.setTimer(2000);
        lampView.start();

    }

    private void initData() {
        LampBean bean = new LampBean();
        bean.info = "踏青零食上京东，百万零食1元秒";
        list.add(bean);

        bean = new LampBean();
        bean.info = "看老刘中国行，满129减50！";
        list.add(bean);

        bean = new LampBean();
        bean.info = "高姿CC霜全渠道新品首发，领券199减50，点击查看";
        list.add(bean);

    }
}
