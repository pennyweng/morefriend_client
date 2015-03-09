package com.jookershop.linefriend.ad;


import android.content.Context;

import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

public class VungleAD {
	final public static VunglePub vunglePub = VunglePub.getInstance();
	
	public static void init(Context context) {
		final String app_id = "linefriend";

	      // initialize the Publisher SDK
	      vunglePub.init(context, app_id);		
	}
	
	public static void onPause() {
		vunglePub.onPause();
	}
	
	public static void onResume() {
		vunglePub.onPause();
	}
	
	public static boolean hasAd() {
		return vunglePub.isAdPlayable();		
	}
	
	public static void show(Context context, final ADResult adr) {
		vunglePub.setEventListeners(new EventListener(){

		    @Override
		    public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
		        // Called each time an ad completes. isCompletedView is true if at least  
		        // 80% of the video was watched, which constitutes a completed view.  
		        // watchedMillis is for the longest video view (if the user replayed the 
		        // video).
		    }

		    @Override
		    public void onAdStart() {
		        // Called before playing an ad
		    }

		    @Override
		    public void onAdEnd(boolean wasCallToActionClicked) {
		        // Called when the user leaves the ad and control is returned to your application
		    }

		    @Override
		    public void onAdPlayableChanged(boolean isAdPlayable) {
		        // Called when the playability state changes. if isAdPlayable is true, you can now play an ad.
		        // If false, you cannot yet play an ad.
		    }

		    @Override
		    public void onAdUnavailable(String reason) {
		        // Called when VunglePub.playAd() was called, but no ad was available to play
		    }

		  });
		
		vunglePub.playAd();
		
	}
	
	
}
