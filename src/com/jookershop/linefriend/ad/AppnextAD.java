package com.jookershop.linefriend.ad;

import android.content.Context;
import android.util.Log;

//import com.appnext.appnextsdk.Appnext;
//import com.appnext.appnextsdk.NoAdsInterface;
//import com.appnext.appnextsdk.OnAdLoadInterface;
//import com.appnext.appnextsdk.PopupClickedInterface;
//import com.appnext.appnextsdk.PopupOpenedInterface;

public class AppnextAD {
//	public static Appnext appnext;
//	
//	public static boolean noShow(Context context) {
//		if(appnext != null && appnext.isBubbleVisible()){
//			appnext.hideBubble();
//			return true;
//		} else return false;
//	}
//	
//	public static void show(Context context, final ADResult adr) {
//		appnext = new Appnext(context);
//		appnext.setAppID("54e7f86b-7c72-4b66-8880-c357f3ace287");
//		appnext.showBubble();
//		
//		appnext.setAdLoadInterface(new OnAdLoadInterface() { 
//			   @Override
//			   public void adLoaded() {
//			      Log.v("appnext", "on ad load"); 
//			      adr.setLoad(true);
//			   } 
//		});
//		
//		appnext.setNoAdsInterface(new NoAdsInterface() {
//		    @Override
//		    public void noAds() {
//		       Log.v("appnext", "no ads"); 
//		       adr.setLoad(false);
//		    } 
//		});
//
//
//		appnext.setPopupOpenedInterface(new PopupOpenedInterface() {
//		    @Override
//		    public void popupOpened() { 
//		           Log.v("appnext", "popup opened");
//		    }
//		});
//
//		appnext.setPopupClickedCallback(new PopupClickedInterface() {
//		    @Override
//		    public void popupClicked() {
//		           Log.v("appnext", "popup clicked"); 
//		           adr.setClick(true);
//		    } 
//		});		
//		
//	}
}
