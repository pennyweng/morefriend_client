package com.jookershop.linefriend.ad;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend4.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.RelativeLayout;

public class AdmobAD {
	public static String unitId = "ca-app-pub-9943629122951220/7624660391";
	
	public static void show(Context context, final ADResult adr, RelativeLayout adBannerLayout) {
		final AdView adView = new AdView(context);
		adView.setAdUnitId(unitId);
		adView.setAdSize(AdSize.SMART_BANNER);
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
				adr.setLoad(false);
			}

			@Override
			public void onAdLeftApplication() {
				// TODO Auto-generated method stub
				super.onAdLeftApplication();
				Log.d(Constants.TAG, "google ad onAdLeftApplication");
				adr.setClick(true);
			}

			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Log.d(Constants.TAG, "google ad load");
				adr.setLoad(true);

			}

			@Override
			public void onAdOpened() {
				super.onAdOpened();
				Log.d(Constants.TAG, "google ad onAdOpened");
				adr.setClick(true);
			}

		});
		
		adBannerLayout.addView(adView);
	}
}
