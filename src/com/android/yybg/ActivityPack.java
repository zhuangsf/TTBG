package com.android.yybg;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

public abstract class ActivityPack extends Activity {

	protected void onResume() {
		super.onResume();
	    //add for umeng
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
	    //add for umeng
		MobclickAgent.onPause(this);
	}
	
}
