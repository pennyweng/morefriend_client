package com.jookershop.linefriend.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RelativeLayout.LayoutParams;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;

public class AdUtil {
	public static final long AD_SHOW_PERIOD = 1209600000l;
	public static final long ALERT_NEXT_SHOW_PERIOD = 1209600000l;
	
	public static void showMsg(Context context) {
		final SharedPreferences sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);

			final AlertDialog.Builder builder = new Builder(context);
			builder.setMessage("不想要廣告干擾您交朋友嗎？只要點選並開啟下方廣告, 兩個禮拜內畫面上就不會有任何廣告出現!!!");
			builder.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							sp.edit().putLong(Constants.KEY_CLICK_ALERT, System.currentTimeMillis()).commit();
//							sp.edit().putBoolean(Constants.KEY_SHOW_ALERT, true).commit();
							dialog.dismiss();
						}
					});
//			builder.setNegativeButton("我知道了",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(
//								DialogInterface dialog,
//								int which) {
//							sp.edit().putLong(Constants.KEY_CLICK_ALERT, System.currentTimeMillis()).commit();
//							sp.edit().putBoolean(Constants.KEY_SHOW_ALERT, false).commit();
//							dialog.dismiss();
//						}
//					});			
			
			boolean showAd = !((System.currentTimeMillis() - sp.getLong(Constants.KEY_CLICK_AD, 0)) < AD_SHOW_PERIOD);
			boolean showAlert = !((System.currentTimeMillis() - sp.getLong(Constants.KEY_CLICK_ALERT, 0)) < ALERT_NEXT_SHOW_PERIOD);
//			boolean showReminderAlert = !((System.currentTimeMillis() - sp.getLong(Constants.KEY_CLICK_ALERT, 0)) < ALERT_NEXT_REMINDER_PERIOD);

			
			if(showAd && showAlert) {
				builder.create().show();
			} 

	}

	public static void showAD(Context context, final AdView adView) {
		final SharedPreferences sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
		if((System.currentTimeMillis() - sp.getLong(Constants.KEY_CLICK_AD, 0)) < AD_SHOW_PERIOD) {
			adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
			adView.destroy();
		} else {
			AdRequest adRequest = new AdRequest.Builder().build();
		    adView.loadAd(adRequest);		
			adView.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					super.onAdClosed();
					Log.d(Constants.TAG, "google ad onAdClosed");
				}
	
				@Override
				public void onAdFailedToLoad(int errorCode) {
					// TODO Auto-generated method stub
					super.onAdFailedToLoad(errorCode);
					Log.d(Constants.TAG, "google ad error" + errorCode);
				}
	
				@Override
				public void onAdLeftApplication() {
					// TODO Auto-generated method stub
					super.onAdLeftApplication();
					Log.d(Constants.TAG, "google ad onAdLeftApplication");
					sp.edit().putLong(Constants.KEY_CLICK_AD, System.currentTimeMillis()).commit();
					adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
				}
	
				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
					Log.d(Constants.TAG, "google ad load");
				}
	
				@Override
				public void onAdOpened() {
					super.onAdOpened();
					Log.d(Constants.TAG, "google ad onAdOpened");
					sp.edit().putLong(Constants.KEY_CLICK_AD, System.currentTimeMillis()).commit();
					adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
				}
				
			});
		}		
		
	}	
	
	
	public static void showAD1(Context context, final AdView adView) {
		final SharedPreferences sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
		if(sp.contains(Constants.KEY_CLICK_AD) && (System.currentTimeMillis() - sp.getLong(Constants.KEY_CLICK_AD, 0)) < AD_SHOW_PERIOD) {
			adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
			adView.destroy();
//			adView.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, 1));
		} else {
			final AlertDialog.Builder builder = new Builder(context);
			builder.setMessage("點選下方廣告, 一個月內就不會出現任何廣告!!!");
//			builder.setPositiveButton("下次再提醒我",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(
//								DialogInterface dialog,
//								int which) {
//							sp.edit().putBoolean(Constants.KEY_SHOW_ALERT, true).commit();
//							dialog.dismiss();
//						}
//					});
			builder.setNegativeButton("我知道了",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							sp.edit().putBoolean(Constants.KEY_SHOW_ALERT, false).commit();
							dialog.dismiss();
						}
					});			
			
			if(!sp.contains(Constants.KEY_SHOW_ALERT) || (sp.contains(Constants.KEY_SHOW_ALERT) && sp.getBoolean(Constants.KEY_SHOW_ALERT, true)))
			builder.create().show();

			
			AdRequest adRequest = new AdRequest.Builder().build();
		    adView.loadAd(adRequest);		
			adView.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					super.onAdClosed();
					Log.d(Constants.TAG, "google ad onAdClosed");
				}
	
				@Override
				public void onAdFailedToLoad(int errorCode) {
					// TODO Auto-generated method stub
					super.onAdFailedToLoad(errorCode);
					Log.d(Constants.TAG, "google ad error" + errorCode);
				}
	
				@Override
				public void onAdLeftApplication() {
					// TODO Auto-generated method stub
					super.onAdLeftApplication();
					Log.d(Constants.TAG, "google ad onAdLeftApplication");
					sp.edit().putLong(Constants.KEY_CLICK_AD, System.currentTimeMillis()).commit();
					adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
				}
	
				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
					Log.d(Constants.TAG, "google ad load");
				}
	
				@Override
				public void onAdOpened() {
					super.onAdOpened();
					Log.d(Constants.TAG, "google ad onAdOpened");
					sp.edit().putLong(Constants.KEY_CLICK_AD, System.currentTimeMillis()).commit();
					adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
				}
				
			});
		}		
		
	}
}
