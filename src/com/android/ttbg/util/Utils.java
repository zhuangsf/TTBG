package com.android.ttbg.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;


public class Utils {
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
