package com.android.yybg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
public class BootActivity extends Activity {

    //延迟3秒 
    private static final long SPLASH_DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity 
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void goHome() {
        Intent intent = new Intent(BootActivity.this, LauncherActivity.class);
        BootActivity.this.startActivity(intent);
        BootActivity.this.finish();
    }
}