package com.jookershop.linefriend.ad;



import com.appma.ad.AppAdView;
import com.appma.ad.AppConnection;

import android.content.Context;
import android.widget.RelativeLayout;

public class AppMaAD {
	private static AppAdView ad = null;
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		if(ad != null) {
			  AppConnection.getInstance(context).onDestory();
		      ad.onDestory();			
		}
		
		AppConnection.getInstance(context,adBannerLayout);
		ad = new AppAdView(context,adBannerLayout);
		ad.DisplayAd();
	}
}
