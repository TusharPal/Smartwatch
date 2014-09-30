package com.example.smartwatch;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class ServiceNotifications extends NotificationListenerService
{

	public void onNotificationPosted(StatusBarNotification statusBarNotification) 
	{
		Log.d("Notification added", statusBarNotification.getNotification().tickerText.toString());
	}

	public void onNotificationRemoved(StatusBarNotification statusBarNotification) 
	{
		Log.d("Notification removed", statusBarNotification.getNotification().tickerText.toString());
	}
}
