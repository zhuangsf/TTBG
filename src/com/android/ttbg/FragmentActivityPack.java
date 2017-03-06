package com.android.ttbg;

import com.umeng.analytics.MobclickAgent;

import android.support.v4.app.FragmentActivity;

public class FragmentActivityPack extends FragmentActivity {

	@Override
	protected void onResume() {
		super.onResume();
	    //add for umeng
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	    //add for umeng
		MobclickAgent.onPause(this);
	}
}
