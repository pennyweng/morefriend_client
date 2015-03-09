package com.jookershop.linefriend.ad;

import com.vpadn.ads.VpadnAd;
import com.vpadn.ads.VpadnAdListener;
import com.vpadn.ads.VpadnAdRequest;
import com.vpadn.ads.VpadnAdSize;
import com.vpadn.ads.VpadnBanner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

public class VponAD {
	private static VpadnBanner vpadnBanner;
	public static String id = "8a8081824b9139e9014ba2c65e0527ba";

	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		vpadnBanner = new VpadnBanner((Activity) context, id, VpadnAdSize.SMART_BANNER,
				"TW");

		VpadnAdRequest adRequest = new VpadnAdRequest();
		adRequest.setEnableAutoRefresh(true);
		vpadnBanner.loadAd(adRequest);
		vpadnBanner.setAdListener(new VpadnAdListener() {
			 
			@Override
			public void onVpadnDismissScreen(VpadnAd arg0) {
				adr.setClick(true);
				Log.d("vpon", "onVpadnDismissScreen");
			}
 
			@Override
			public void onVpadnFailedToReceiveAd(VpadnAd arg0, VpadnAdRequest.VpadnErrorCode arg1) {
				Log.d("vpon", "onVpadnFailedToReceiveAd:" + arg1);
				adr.setLoad(false);
			}
 
			@Override
			public void onVpadnLeaveApplication(VpadnAd arg0) {
				Log.d("vpon", "onVpadnLeaveApplication");
			}
 
			@Override
			public void onVpadnPresentScreen(VpadnAd arg0) {
				Log.d("vpon", "onVpadnPresentScreen");
			}
 
			@Override
			public void onVpadnReceiveAd(VpadnAd arg0) {
				Log.d("vpon", "onVpadnReceiveAd");
				adr.setLoad(true);
			}
 
		});		


		adBannerLayout.addView(vpadnBanner);		
	}
	
	public static void shutdown() {
		if(vpadnBanner != null) {
			vpadnBanner.destroy();
			vpadnBanner = null;
		}
	}
}
