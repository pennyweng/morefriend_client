package com.jookershop.linefriend.ad;

import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyAd;
import com.jirbo.adcolony.AdColonyAdAvailabilityListener;
import com.jirbo.adcolony.AdColonyAdListener;
import com.jirbo.adcolony.AdColonyVideoAd;

import android.app.Activity;
import android.content.Context;

public class AdColonyAD {

	final static String APP_ID = "appdce8828e45494e7a8c";
	final static String ZONE_ID = "vz9b5c76bd2fa444cd9e";

	public static void onPause(Context ctx) {
		AdColony.pause();
	}

	public static void onResume(Context ctx) {
		AdColony.resume((Activity) ctx);
	}

	public static void init(Context ctx) {
		AdColony.configure((Activity) ctx, "version:1.0,store:google", APP_ID,
				ZONE_ID);
	}

	public static void show(Context ctx) {
		AdColony.addAdAvailabilityListener(new AdColonyAdAvailabilityListener() {

			@Override
			public void onAdColonyAdAvailabilityChange(boolean arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

		AdColonyVideoAd ad = new AdColonyVideoAd(ZONE_ID)
				.withListener(new AdColonyAdListener() {

					@Override
					public void onAdColonyAdAttemptFinished(AdColonyAd arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAdColonyAdStarted(AdColonyAd arg0) {
						// TODO Auto-generated method stub

					}
				});
		ad.show();
	}
}
