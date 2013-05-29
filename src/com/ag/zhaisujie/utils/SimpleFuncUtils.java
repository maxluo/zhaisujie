package com.ag.zhaisujie.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * SimpleFuncUtils.java
 * @author max.Luo
 * @email max_null@sina.com
 * 2013-5-29
 */
public class SimpleFuncUtils {

	public static void startPhoneIntent(Context context, String phoneNum) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + phoneNum));
		context.startActivity(intent);
	}
	
}

