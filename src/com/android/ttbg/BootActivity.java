package com.android.ttbg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

/**
 * 
 * @{#} BootActivity.java Create on 2013-5-2 下午9:10:01    
 *    
 * class desc:   启动画面
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0
 * @Author <a href="mailto:gaolei_xj@163.com">Leo</a>   
 *  
 *  
 */
public class BootActivity extends ActivityPack {

    //延迟3秒 
    private static final long SPLASH_DELAY_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity 
        new Handler().postDelayed(new Runnable() {
            @Override
			public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);
        
        //add for umeng
        MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
        
        
        //add for bugly
        CrashReport.initCrashReport(getApplicationContext(), "b5993e5675", false);
    }

    private void goHome() {
        Intent intent = new Intent(BootActivity.this, LauncherActivity.class);
        BootActivity.this.startActivity(intent);
        BootActivity.this.finish();
    }
}