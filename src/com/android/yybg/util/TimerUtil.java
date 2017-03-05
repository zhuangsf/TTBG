package com.android.yybg.util;

import java.util.Formatter;
import java.util.Locale;

public class TimerUtil {

	public static String stringForTime(long timeMs) {
		StringBuilder mFormatBuilder;
		Formatter mFormatter;
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		String result;
		long totalMs = timeMs/10;
		long msSeconds = totalMs%100;
		long totalSeconds = timeMs / 1000;
		long seconds = totalSeconds % 60;
		long minutes = (totalSeconds / 60) % 60;
		long hours = totalSeconds / 3600;
		mFormatBuilder.setLength(0);
		if (hours > 0) {
			result = mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
		} else {
			result = mFormatter.format("%02d:%02d:%02d", minutes, seconds,msSeconds).toString();
		}
		mFormatter.close();
		return result;
	}
}
