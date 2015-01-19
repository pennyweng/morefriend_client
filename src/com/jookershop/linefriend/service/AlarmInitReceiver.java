package com.jookershop.linefriend.service;

import java.util.Calendar;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.util.SchedulerUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AlarmInitReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
		SchedulerUtil.register(context, sp.getInt(Constants.NOTIFY_HOUR, 17));
	}
}