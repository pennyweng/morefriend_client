package com.jookershop.linefriend.ad;

import com.jookershop.linefriend.Constants;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
//import net.youmi.android.banner.AdView;
//import net.youmi.android.banner.AdViewListener;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

public class YoumiAD {
	
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		AdView adView = new AdView(context, AdSize.FIT_SCREEN);
		
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				adr.setLoad(false);
				
				Log.d("YoumiAD", "onFailedToReceivedAd");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				adr.setLoad(true);
				Log.d("YoumiAD", "onReceivedAd");
				
			}

			@Override
			public void onSwitchedAd(AdView arg0) {
				adr.setClick(true);
				Log.d("YoumiAD", "onSwitchedAd");
			}

		});		
		adBannerLayout.addView(adView);
		
	}
}
