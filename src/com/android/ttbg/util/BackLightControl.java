package com.android.ttbg.util;

import com.android.ttbg.BuildConfig;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Build;
import android.provider.Settings;

public class BackLightControl {

	public static int getBrightNess(Context context) {

		int screenMode = -1;
		int screenBrightness = -1;
        try {

            /**
             * 获得当前屏幕亮度的模式
             * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
             * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
             */
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            Utils.Log("screenMode = " + screenMode);
            // 获得当前屏幕亮度值 0--255
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Utils.Log("Global screenBrightness = " + screenBrightness);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        
        return screenBrightness;
    }
	
	
	 /**
     * @param mode 1:自动调节亮度，0为手动调节亮度
     *             需要 <uses-permission android:name="android.permission.WRITE_SETTINGS" />”权限
     */
    private static void setBrightnessMode(Context context ,int mode) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
    }
	
	
	public static void setBrightness(Activity context, int brightness) {
        
		if(brightness < 1)
		{
			brightness = 1;
		}
		else if(brightness > 255)
		{
			brightness = 254;
		}
		
        setBrightnessMode(context,0);
        
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        
}
	

	
	
}
