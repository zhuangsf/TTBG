package com.android.ttbg.util;

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


}
