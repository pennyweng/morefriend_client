package com.jookershop.linefriend.ad;

//import com.millennialmedia.android.MMAd;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMException;
//import com.millennialmedia.android.MMRequest;
//import com.millennialmedia.android.MMSDK;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

//import com.millennialmedia.android.RequestListener;

public class MillennialAD {
//	//Constants for tablet sized ads (728x90)
//	private static final int IAB_LEADERBOARD_WIDTH = 728;
//	private static final int IAB_LEADERBOARD_HEIGHT = 90;
//
//	private static final int MED_BANNER_WIDTH = 480;
//	private static final int MED_BANNER_HEIGHT = 60;
//
//	//Constants for phone sized ads (320x50)
//	private static final int BANNER_AD_WIDTH = 320;
//	private static final int BANNER_AD_HEIGHT = 50;
//	
//	
//	public static void show(Context context, final ADResult adr, RelativeLayout adRelativeLayout) {
//		MMAdView adView = new MMAdView(context);
//
//		// Set your apid
//		adView.setApid("193413");
//
//		// (Highly Recommended) Set the id to preserve your ad on configuration changes. Save Battery!
//		// Each MMAdView you give requires a unique id.
//		adView.setId(MMSDK.getDefaultAdId());
//
//		int placementWidth = BANNER_AD_WIDTH;
//		int placementHeight = BANNER_AD_HEIGHT;
//
//		// (Optional) Set the ad size
//		if(canFit(IAB_LEADERBOARD_WIDTH, context))
//		{
//			placementWidth = IAB_LEADERBOARD_WIDTH;
//			placementHeight = IAB_LEADERBOARD_HEIGHT;
//		}
//		else if(canFit(MED_BANNER_WIDTH, context))
//		{
//			placementWidth = MED_BANNER_WIDTH;
//			placementHeight = MED_BANNER_HEIGHT;
//		}
//
//		// (Optional) Set the AdView size based on the placement size. You could use WRAP_CONTENT and not specify the placement size
//		adView.setWidth(placementWidth);
//		adView.setHeight(placementHeight);
//
//		int layoutWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementWidth, context.getResources().getDisplayMetrics());
//		int layoutHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementHeight, context.getResources().getDisplayMetrics());
//
//
//		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(layoutWidth, layoutHeight);
//		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//
//		adRelativeLayout.addView(adView, layoutParams);
//
//
//
//		// (Optional/Recommended) Set meta data (will be applied to subsequent ad requests)
//		MMRequest request = new MMRequest();
//		adView.setMMRequest(request);
//		adView.getAd();
//		
////		int placementWidth = BANNER_AD_WIDTH;
////		int placementHeight = BANNER_AD_HEIGHT;
////
////		//Finds an ad that best fits a users device.
////		if(canFit(IAB_LEADERBOARD_WIDTH, context)) {
////		    placementWidth = IAB_LEADERBOARD_WIDTH;
////		    placementHeight = IAB_LEADERBOARD_HEIGHT;
////		} else if(canFit(MED_BANNER_WIDTH, context)) {
////		    placementWidth = MED_BANNER_WIDTH;
////		    placementHeight = MED_BANNER_HEIGHT;
////		}
////		
////		MMAdView adView = new MMAdView(context);
////		adView.setApid("193413");
////		
////		adView.setWidth(placementWidth);
////		adView.setHeight(placementHeight);
////
////		//Calculate the size of the adView based on the ad size. Replace the width and height values if needed.
////		int layoutWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementWidth, context.getResources().getDisplayMetrics());
////		int layoutHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementHeight, context.getResources().getDisplayMetrics());
////
////		//Create the layout parameters using the calculated adView width and height.
////		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(layoutWidth, layoutHeight);
////
////		//This positions the banner.
////		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
////		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
////
////		adView.setLayoutParams(layoutParams);
////		adView.setListener(new RequestListener() {
////
////			@Override
////			public void MMAdOverlayClosed(MMAd arg0) {
////				Log.d("MillennialAD", "MMAdOverlayClosed");
////			}
////
////			@Override
////			public void MMAdOverlayLaunched(MMAd arg0) {
////				Log.d("MillennialAD", "MMAdOverlayLaunched");
////			}
////
////			@Override
////			public void MMAdRequestIsCaching(MMAd arg0) {
////				Log.d("MillennialAD", "MMAdRequestIsCaching");
////			}
////
////			@Override
////			public void onSingleTap(MMAd arg0) {
////				Log.d("MillennialAD", "onSingleTap");
////			}
////
////			@Override
////			public void requestCompleted(MMAd arg0) {
////				Log.d("MillennialAD", "requestCompleted");
////			}
////
////			@Override
////			public void requestFailed(MMAd arg0, MMException arg1) {
////				// TODO Auto-generated method stub
////				
////			}
////			
////		});
////		
////
////		
////
////
////		//Set your metadata in the MMRequest object
////		MMRequest request = new MMRequest();
////		adView.setMMRequest(request);
////
////
////		//Sets the id to preserve your ad on configuration changes.
////		adView.setId(MMSDK.getDefaultAdId());
////		adRelativeLayout.addView(adView);
//////		adRelativeLayout.addView(adView, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
////		adView.getAd();		
//	}
//	
//	protected static boolean canFit(int adWidth, Context context) {
//	    int adWidthPx = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, adWidth, context.getResources().getDisplayMetrics());
//	    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//	    return metrics.widthPixels >= adWidthPx;
//	}	
}
