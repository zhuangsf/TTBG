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
        		OperatingSharedPreferences.PREFERENCE_NAME, Context.MODE_PRIVATE);  
        Editor editor = sharedPreferences.edit();// 获取编辑器  
        editor.putBoolean(OperatingSharedPreferences.PREFERENCE_FIRSTOPEN, false);  
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
        		OperatingSharedPreferences.PREFERENCE_NAME, Context.MODE_PRIVATE);  
        // getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值true  
        boolean firstopen = sharedPreferences.getBoolean(OperatingSharedPreferences.PREFERENCE_FIRSTOPEN, true);  
        return firstopen;  
    }  
}  