package com.android.yybg.view;

import android.content.Context;
import android.util.AttributeSet;



public class VerticalLampView extends
		BaseAutoScrollTextView<LampBean> {

	public VerticalLampView(Context context, AttributeSet attrs,
							int defStyle) {
		super(context, attrs, defStyle);
	}

	public VerticalLampView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalLampView(Context context) {
		super(context);
	}

	@Override
	public String getTextInfo(LampBean data) {
		return data.info;
	}

	/**
	 * 这里面的高度应该和你的xml里设置的高度一致
	 */
	@Override
	protected int getAdertisementHeight() {
		return 40;
	}

}
