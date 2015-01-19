package com.jookershop.linefriend.util;

import java.util.Calendar;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.service.PlayReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SchedulerUtil {
	
	public static void register( Context context, int hour) {
//		Calendar calendar = Calendar.getInstance();
//		Log.d(Constants.TAG, "calendar:" + calendar.getTimeInMillis());
////		calendar.add(Calendar.DAY_OF_YEAR, 1);
//		calendar.set(Calendar.HOUR_OF_DAY, hour);
//		calendar.set(Calendar.MINUTE, 30);
//		calendar.set(Calendar.SECOND, 0);
//		Log.d(Constants.TAG, "after calendar:" + calendar.getTimeInMillis());
		
		Long mm = 3600000l * hour;
//		Long mm = 1000l * hour;
	    Intent intenta = new Intent(context, PlayReceiver.class);
	    intenta.putExtra("msg", "play_hskay");
	    Log.d(Constants.TAG, "mm" + mm);

	    PendingIntent pi = PendingIntent.getBroadcast(context, 0, intenta, PendingIntent.FLAG_UPDATE_CURRENT);	    
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, mm, pi);
		
	}
	
	public static void unregister(Context context) {
	    Intent intenta = new Intent(context, PlayReceiver.class);
	    intenta.putExtra("msg", "play_hskay");

	    PendingIntent pi = PendingIntent.getBroadcast(context, 0, intenta, PendingIntent.FLAG_UPDATE_CURRENT);	    
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pi);
	}
	
	public static void registerNow( Context context) {
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.SECOND, 15);
	 
	    Intent intent = new Intent(context, PlayReceiver.class);
	    intent.putExtra("msg", "play_hskay");
	 
	    PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
	         
	    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//	    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);		
	    am.cancel(pi);
	}
	
}
