package com.jookershop.linefriend.ad;

//import com.yqtcaltcghe.AdController;
//import com.yqtcaltcghe.AdListener;

import android.content.Context;
import android.util.Log;

public class Leadbolt {

//	private static AdController interstitial;
//
//	public static void show(Context context, final ADResult adr) {
//		if (interstitial != null) {
//			interstitial.destroyAd();
//			interstitial = null;
//		}
//
//		interstitial = new AdController(context, "933162526", new AdListener() {
//
//			@Override
//			public void onAdCached() {
//				Log.d("Leadbolt", "onAdCached");
//			}
//
//			@Override
//			public void onAdClicked() {
//				Log.d("Leadbolt", "onAdClicked");
//				adr.setClick(true);
//			}
//
//			@Override
//			public void onAdClosed() {
//				Log.d("Leadbolt", "onAdClosed");
//			}
//
//			@Override
//			public void onAdFailed() {
//				Log.d("Leadbolt", "onAdFailed");
//				adr.setLoad(false);
//			}
//
//			@Override
//			public void onAdLoaded() {
//				Log.d("Leadbolt", "onAdLoaded");
//				adr.setLoad(true);
//				
//			}
//
//		});
//		interstitial.loadAd();
//
//	}
//
//	public static void shutDown() {
//		if (interstitial != null)
//			interstitial.destroyAd();
//	}
}
