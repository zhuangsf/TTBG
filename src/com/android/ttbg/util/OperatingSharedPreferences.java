package com.android.ttbg.util;

import android.content.Context;  
import android.content.SharedPreferences;  
import android.content.SharedPreferences.Editor;  
  
/*  
 * 基本功能：存储和访问SharedPreferences  
 * 创建：Jason  
 */  
public class OperatingSharedPreferences { 
	public static final String PREFERENCE_NAME="TTBG";
	public static final String PREFERENCE_FIRSTOPEN="FIRSTOPEN";
	public static final String PREFERENCE_USER_IMAGE="AVATAR";
	
	//个人资料里用户名称
	public static final String PREFERENCE_SETTING_USER_NAME="USER_NAME";
	//设置里亮度调节
	public static final String PREFERENCE_SETTING_LIGHT_SETTING="LIGHT_SETTING";
	public static final boolean PREFERENCE_SETTING_LIGHT_SETTING_DEFAULT=false;
    /**  
     * <pre>  
     * 基本功能：保存启动标识到SharedPreferences  
     * 编写：Jason  
     * @param context  
     * @param opentimes  
     * </pre>  
     */  
    public static void setBooleanFirstBoot(Context context) {  
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Editor editor = sharedPreferences.edit();// 获取编辑器  
        editor.putBoolean(PREFERENCE_FIRSTOPEN, false);  
        editor.commit();// 提交修改  
    }  
  
    /**  
     * <pre>  
     * 基本功能：取得SharedPreferences中存储的启动标识  
     * 编写：Jason  
     * @param context  
     * @return  
     * </pre>  
     */  
    public static boolean getBooleanFirstBoot(Context context) {  
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        // getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值true  
        boolean firstopen = sharedPreferences.getBoolean(PREFERENCE_FIRSTOPEN, true);  
        return firstopen;  
    }  
    
    public static void saveUserImage(Context context,String urlpath) {  
    SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
    Editor editor = sharedPreferences.edit();// 获取编辑器  
    editor.putString(PREFERENCE_USER_IMAGE, urlpath);
    editor.commit();
    }
    
    public static void saveUserName(Context context,String name) {  
    SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
    Editor editor = sharedPreferences.edit();
    editor.putString(PREFERENCE_SETTING_USER_NAME, name);
    editor.commit();
    }
    public static String getUserName(Context context) {  
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        String username = sharedPreferences.getString(PREFERENCE_SETTING_USER_NAME, "");  
        return username;  
    }  
    
    public static void setLightSetting(Context context,Boolean bOn) {  
    SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
    Editor editor = sharedPreferences.edit();
    editor.putBoolean(PREFERENCE_SETTING_LIGHT_SETTING, bOn);
    editor.commit();
    }
    public static boolean getLightSetting(Context context) {  
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		OperatingSharedPreferences.PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Boolean bOn = sharedPreferences.getBoolean(PREFERENCE_SETTING_LIGHT_SETTING, PREFERENCE_SETTING_LIGHT_SETTING_DEFAULT);  
        return bOn;  
    }  
    
}  