package com.android.ttbg;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;


public abstract class ActivityPack extends Activity {

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
