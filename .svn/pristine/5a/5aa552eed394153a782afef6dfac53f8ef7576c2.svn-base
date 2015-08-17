package com.searun.orderoperation.util;

import android.app.NotificationManager;
import android.content.Context;

import com.searun.orderoperation.application.ConstantPool;

public class NotificaionUtil {

	public static void clearNotification(Context context) {
		NotificationManager mgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
//		mgr.cancelAll();
		mgr.cancel(ConstantPool.DEFAULT_NOTIFICATION_ID);
	}

}
