package com.android.ttbg.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;


public class Utils {
	
	public final static boolean WTF = true;    //造假专用,后续用读取网络的方式
	
	
	private final static boolean isDebug = true;
	private final static String TAG = Utils.class.getPackage() + "."
			+ Utils.class.getSimpleName();
	/**
	 * running log
	 * 
	 * @param s
	 */
	public static void Log(String s) {
		if (isDebug) {
			Log.i("jockey", s);
			
		}
	}

	/**
	 * error log
	 * 
	 * @param tag
	 * @param s
	 */
	public static void Log(String tag, String s) {
		if (isDebug) {
		Log.d(tag, s);
		}
	}
	
	
	/**
	* 验证邮箱输入是否合法
	* 
	* @param strEmail
	* @return
	*/
	public static boolean isEmail(String strEmail) {
	// String strPattern =
	// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";


	Pattern p = Pattern.compile(strPattern);
	Matcher m = p.matcher(strEmail);
	return m.matches();
	}

	/**
	* 验证是否是手机号码
	* 
	* @param str
	* @return
	*/
	public static boolean isMobile(String str) {
	Pattern pattern = Pattern.compile("1[0-9]{10}");
	Matcher matcher = pattern.matcher(str);
	if (matcher.matches()) {
	return true;
	} else {
	return false;
	}
	}
	
	
	   /**   
     * MD5 加密   
     */     
    public static String getPasswordMD5Str(String str) {     
    	    	
        MessageDigest messageDigest = null;     
        try {     
            messageDigest = MessageDigest.getInstance("MD5");     
            messageDigest.reset();     
            messageDigest.update(str.getBytes("UTF-8"));     
        } catch (NoSuchAlgorithmException e) {     
            System.out.println("NoSuchAlgorithmException caught!");     
            return null;  
        } catch (UnsupportedEncodingException e) {     
            e.printStackTrace();  
            return null;  
        }     
     
        byte[] byteArray = messageDigest.digest();     
        StringBuffer md5StrBuff = new StringBuffer();     
        for (int i = 0; i < byteArray.length; i++) {                 
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)     
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));     
            else     
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));     
        }     
     
        return md5StrBuff.toString();     
    }   
	
	public static String getInternelStoragePath() {
		try{
Log("Environment.getExternalStorageState() = "+Environment.getExternalStorageState()+" Environment.MEDIA_MOUNTED = "+Environment.MEDIA_MOUNTED);

		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			File sdCardDir = Environment.getExternalStorageDirectory();
			Log("sdCardDir = "+sdCardDir);
			if(sdCardDir != null)
			{
				Log("sdCardDirPath = "+sdCardDir.getCanonicalPath()+"/TTBG/");
				return sdCardDir.getCanonicalPath()+"/TTBG/";
			}
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getInternelStoragePath(Context context) {
		ArrayList storagges = new ArrayList();
		StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
			getVolumeList.setAccessible(true);
			Object[] params = {};
			Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
			if (invokes != null) {
				StorageInfo info = null;
				for (int i = 0; i < invokes.length; i++) {
					Object obj = invokes[i];
					Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
					String path = (String) getPath.invoke(obj, new Object[0]);
					info = new StorageInfo(path);
					File file = new File(info.path);
					if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
						Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
						String state = null;
						try {
							Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
							state = (String) getVolumeState.invoke(storageManager, info.path);
							info.state = state;
						} catch (Exception e) {
							e.printStackTrace();
						}
						info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();

						Log.e("jockeyTrack", "info.isRemoveable = " + info.isRemoveable + " path = " + path + " info.isMounted() = " + info.isMounted());
						if (info.isMounted() && !info.isRemoveable) {
							return info.path + "/TTBG";
						}
					}
				}
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		storagges.trimToSize();

		return null;
	}

}
