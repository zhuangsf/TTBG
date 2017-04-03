package com.android.ttbg.util;

import android.content.Context;  
import android.content.SharedPreferences;  
import android.content.SharedPreferences.Editor;  
  
/*  
 * 基本功能：存储和访问SharedPreferences  
 * 创建：Jason  
 */  
public class OperatingSP { 
	public static final String PREFERENCE_NAME="TTBG";
	public static final String PREFERENCE_FIRSTOPEN="FIRSTOPEN";
	public static final String PREFERENCE_USER_IMAGE="AVATAR";
	
	//个人资料里用户名称
	public static final String PREFERENCE_SETTING_USER_NAME="USER_NAME";
	//性别信息
	public static final String PREFERENCE_SETTING_SEX="SEX";
	public static final String PREFERENCE_SETTING_SEX_DEFAULT="保密";
	//生日
	public static final String PREFERENCE_SETTING_BIRTHDAY="BIRTHDAY";
	public static final String PREFERENCE_SETTING_BIRTHDAY_DEFAULT="";
	//QQ
	public static final String PREFERENCE_SETTING_QQ="QQ";
	public static final String PREFERENCE_SETTING_QQ_DEFAULT="";
	//现居地
	public static final String PREFERENCE_SETTING_LIVE="LIVE";
	public static final String PREFERENCE_SETTING_LIVE_DEFAULT="";
	//QQ
	public static final String PREFERENCE_SETTING_HOME="HOME";
	public static final String PREFERENCE_SETTING_HOME_DEFAULT="";
	//设置里亮度调节
	public static final String PREFERENCE_SETTING_LIGHT_SETTING="LIGHT_SETTING";
	public static final boolean PREFERENCE_SETTING_LIGHT_SETTING_DEFAULT=false;
	
	public static final String PREFERENCE_SETTING_LIGHT_SETTING_SEEKBAR="SEEK_BAR_SETTING";
	public static final float PREFERENCE_SETTING_LIGHT_SETTING_SEEKBAR_DEFAULT=100f;
	
	//首页广告条的更新时间
	public static final String PREFERENCE_BANNER_LASTTIME="LAST_TIME";
	public static final String PREFERENCE_BANNER_LASTTIME_DEFAULT="";
	
	
	//收货人信息
	public static final String PREFERENCE_ADDRESS_NAME="ADDRESS_NAME";
	public static final String PREFERENCE_ADDRESS_PHONE="ADDRESS_PHONE";
	public static final String PREFERENCE_ADDRESS_AREA="ADDRESS_AREA";
	public static final String PREFERENCE_ADDRESS_ADDRESS="ADDRESS_ADDRESS";
	public static final String PREFERENCE_ADDRESS_CODE="ADDRESS_CODE";
	public static final String PREFERENCE_ADDRESS_DEFAULT="ADDRESS_DEFAULT";
	public static final boolean PREFERENCE_ADDRESS_DEFAULT_BSET=false;
	public static final String PREFERENCE_ADDRESS_ACTIVE="ADDRESS_ACTIVE";   //用于表示在数组里是否可用
	public static final boolean PREFERENCE_ADDRESS_ACTIVE_DEFAULT=false;
	


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
        		OperatingSP.PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Boolean bOn = sharedPreferences.getBoolean(PREFERENCE_SETTING_LIGHT_SETTING, PREFERENCE_SETTING_LIGHT_SETTING_DEFAULT);  
        return bOn;  
    }  
    
    
    
    
    
    
    //前面的就算了,后续的统一接口
    public static void setBoolean(Context context,String key,boolean bOn)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Editor editor = sharedPreferences.edit();// 获取编辑器  
        editor.putBoolean(key, bOn);  
        editor.commit();// 提交修改  
    }
    
    public static boolean getBoolean(Context context,String key,boolean bDefault)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        boolean retrunBoolean = sharedPreferences.getBoolean(key, bDefault);  
        return retrunBoolean;  
    }
    
    public static void setString(Context context,String key,String keyValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Editor editor = sharedPreferences.edit();// 获取编辑器  
        editor.putString(key, keyValue);  
        editor.commit();// 提交修改  
    }
    
    public static String getString(Context context,String key,String keyValueDefault)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        String retrunString = sharedPreferences.getString(key, keyValueDefault);  
        return retrunString;  
    }    
    
    public static void setFloat(Context context,String key,float keyValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Editor editor = sharedPreferences.edit();// 获取编辑器  
        editor.putFloat(key, keyValue);  
        editor.commit();// 提交修改  
    }
    
    public static float getFloat(Context context,String key,float keyValueDefault)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(  
        		PREFERENCE_NAME, Context.MODE_PRIVATE);  
        float retrunfloat = sharedPreferences.getFloat(key, keyValueDefault);  
        return retrunfloat;  
    }    
    
}  