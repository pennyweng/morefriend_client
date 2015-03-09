package com.jookershop.linefriend.gift;

import java.net.URLEncoder;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.ad.ADResult;
import com.jookershop.linefriend.ad.AdBuddizAD;
import com.jookershop.linefriend.ad.AdmobAD;
import com.jookershop.linefriend.ad.AppnextAD;
import com.jookershop.linefriend.ad.AxonixAD;
import com.jookershop.linefriend.ad.FlurryAD;
import com.jookershop.linefriend.ad.Leadbolt;
import com.jookershop.linefriend.ad.MillennialAD;
import com.jookershop.linefriend.ad.StartAppAD;
import com.jookershop.linefriend.ad.VponAD;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
//import com.millennialmedia.android.MMAdView;
//import com.millennialmedia.android.MMRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClickAD2Fragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	DisplayImageOptions options;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ClickAD2Fragment newInstance() {
		ClickAD2Fragment fragment = new ClickAD2Fragment();
		return fragment;
	}

	public ClickAD2Fragment() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Context mContext = ClickAD2Fragment.this.getActivity();
		final SharedPreferences sp = this.getActivity().getSharedPreferences(
				"linefriend", Context.MODE_APPEND);

		View rootView = inflater.inflate(R.layout.all_ads1, container,
		false);

		final ADResult adr1 = new ADResult();
		final ADResult adr2 = new ADResult();
//		final ADResult adr3 = new ADResult();
		
		RelativeLayout adRelativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
		RelativeLayout adRelativeLayout2 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout2);
		AdmobAD.show(ClickAD2Fragment.this.getActivity(), adr1, adRelativeLayout1);
		VponAD.show(ClickAD2Fragment.this.getActivity(), adr2, adRelativeLayout2);
		
		Button cancel = (Button) rootView.findViewById(R.id.Button03);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStackImmediate();
				
			}
		});
		
		Button save = (Button) rootView.findViewById(R.id.Button02);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int loadCount = 0;
				if(adr1.isLoad()) loadCount = loadCount + 1;
				if(adr2.isLoad()) loadCount = loadCount + 1;
//				if(adr3.isLoad()) loadCount = loadCount + 1;
				
				int clickCount = 0;
				if(adr1.isClick()) clickCount = clickCount + 1;
				if(adr2.isClick()) clickCount = clickCount + 1;
//				if(adr3.isClick()) clickCount = clickCount + 1;
				
				if (loadCount < 2) {
					Message.ShowMsgDialog(mContext, "目前廣告數量不足，請稍候再嘗試。");
				} if (clickCount < 2) {
					Message.ShowMsgDialog(mContext, "請先挑選兩則有興趣的廣告並點選它。");
				} else {
					final String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "queue/go?uid=" + uid;
					Log.d(Constants.TAG, "go today" + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeString(get,
							new AsyncHttpClient.StringCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source,
										final String result) {
									
									if (e != null ) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										if(e != null)
										e.printStackTrace();
										return;
									}
									if(source != null && source.getHeaders() != null 
											&& source.getHeaders().getHeaders() != null 
											&& source.getHeaders().getHeaders().getResponseCode() != 200) 	{
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");	
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														MyAlertDialog
																.setMessage("今天報到成功。" );
														
														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																getFragmentManager().popBackStackImmediate();
															}
														};
														MyAlertDialog
																.setNeutralButton(
																		"確認",
																		OkClick);
														MyAlertDialog.show();
												}
											});
								}
							});
				}

			}
		});

//		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
////		adView.setAdUnitId("");
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);
//		adView.setAdListener(new AdListener() {
//			@Override
//			public void onAdClosed() {
//				super.onAdClosed();
//				Log.d(Constants.TAG, "google ad onAdClosed");
//			}
//
//			@Override
//			public void onAdFailedToLoad(int errorCode) {
//				// TODO Auto-generated method stub
//				super.onAdFailedToLoad(errorCode);
//				Log.d(Constants.TAG, "google ad error" + errorCode);
////				click = true;
//				Builder MyAlertDialog = new AlertDialog.Builder(
//						mContext);
//				MyAlertDialog
//						.setMessage("目前廣告無法秀出，請稍候再嘗試。" );
//				
//				DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//					public void onClick(
//							DialogInterface dialog,
//							int which) {
//						getFragmentManager().popBackStackImmediate();
//					}
//				};
//				MyAlertDialog
//						.setNeutralButton(
//								"確認",
//								OkClick);
//				MyAlertDialog.show();			
//			}
//
//			@Override
//			public void onAdLeftApplication() {
//				// TODO Auto-generated method stub
//				super.onAdLeftApplication();
//				Log.d(Constants.TAG, "google ad onAdLeftApplication");
//				click = true;
//				// sp.edit().putLong(Constants.KEY_CLICK_AD,
//				// System.currentTimeMillis()).commit();
//				// adView.setLayoutParams(new
//				// LayoutParams(LayoutParams.MATCH_PARENT, 0));
//			}
//
//			@Override
//			public void onAdLoaded() {
//				super.onAdLoaded();
//				Log.d(Constants.TAG, "google ad load");
//
//			}
//
//			@Override
//			public void onAdOpened() {
//				super.onAdOpened();
//				Log.d(Constants.TAG, "google ad onAdOpened");
//				click = true;
//				// sp.edit().putLong(Constants.KEY_CLICK_AD,
//				// System.currentTimeMillis()).commit();
//				// adView.setLayoutParams(new
//				// LayoutParams(LayoutParams.MATCH_PARENT, 0));
//			}
//
//		});
		return rootView;
	}


	
}
