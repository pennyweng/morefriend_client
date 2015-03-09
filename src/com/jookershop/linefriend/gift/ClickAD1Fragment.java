package com.jookershop.linefriend.gift;

import java.net.URLEncoder;

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
public class ClickAD1Fragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	DisplayImageOptions options;
	private boolean click;
	private ADResult adr; 


	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ClickAD1Fragment newInstance(GiftItem giftItem) {
		ClickAD1Fragment fragment = new ClickAD1Fragment();
		Bundle bi = new Bundle();
		bi.putSerializable("gi", giftItem);
		fragment.setArguments(bi);		
		return fragment;
	}

	public ClickAD1Fragment() {
		adr = new ADResult();
		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.emptyhead)
				.showImageForEmptyUri(R.drawable.noimage)
				.showImageOnFail(R.drawable.noimage).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true).build();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Context mContext = ClickAD1Fragment.this.getActivity();
		final SharedPreferences sp = this.getActivity().getSharedPreferences(
				"linefriend", Context.MODE_APPEND);

//		View rootView = inflater.inflate(R.layout.gift_click_interstitial, container,
//				false);
//
//		View rootView = inflater.inflate(R.layout.gift_click_axonix, container,
//				false);
		
//		View rootView = inflater.inflate(R.layout.gift_click_millen, container,
//				false);

		View rootView = inflater.inflate(R.layout.all_ads, container,
		false);
		
		final GiftItem gi;
		if(this.getArguments() != null) {
			gi = (GiftItem)this.getArguments().getSerializable("gi");
		} else return rootView;
		
		
//		AxonixAD.show(ClickADFragment.this.getActivity(), adr, rootView);
//		RelativeLayout adRelativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
//		FlurryAD.show(ClickAD1Fragment.this.getActivity(), adr, adRelativeLayout1);
//		RelativeLayout adRelativeLayout2 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout2);		
//		VponAD.show(ClickAD1Fragment.this.getActivity(), adr, adRelativeLayout2);
//		RelativeLayout adRelativeLayout3 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout3);		
//		StartAppAD.show(ClickAD1Fragment.this.getActivity(), adr, adRelativeLayout3);
//		RelativeLayout adRelativeLayout4 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout4);		
//		MillennialAD.show(ClickADFragment.this.getActivity(), adr, adRelativeLayout4);
		
		
//		Button showAdBt = (Button) rootView.findViewById(R.id.button1);
//		showAdBt.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
////				AppnextAD.show(ClickADFragment.this.getActivity(), adr);
////				Leadbolt.show(ClickADFragment.this.getActivity(), adr);
////				AxonixAD.show(ClickADFragment.this.getActivity(), adr);
//				AdBuddizAD.show(ClickADFragment.this.getActivity(), adr);
//			}
//		});
		

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
				if (!adr.isLoad()) {
					Message.ShowMsgDialog(mContext, "目前廣告無法秀出，請稍候再嘗試。");
				} if (!adr.isClick()) {
					Message.ShowMsgDialog(mContext, "請先開啟廣告並點選它。");
				} else {
					final String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = "";
					
					if(gi.getType().equals(GiftItem.TYPE_LINE))
						url = Constants.BASE_URL + "gift/line/play?uid=" + uid 
								+ "&lgid=" + gi.getId() 
								+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else if(gi.getType().equals(GiftItem.TYPE_MONEY))
						url = Constants.BASE_URL + "gift/money/play?uid=" + uid 
						+ "&lgid=" + gi.getId() 
						+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else if(gi.getType().equals(GiftItem.TYPE_BAG))
						url = Constants.BASE_URL + "gift/bag/play?uid=" + uid 
						+ "&lgid=" + gi.getId() 
						+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
					else return;
					
					Log.d(Constants.TAG, "gift play url " + url);
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
													if (result.indexOf("err:4") != -1)
														Message.ShowMsgDialog(
																mContext,
																"此個人獎勵已經不存在");
													else if (result
															.indexOf("err:3") != -1) {
														String[] r = result
																.split(":");
														if (r.length == 3) {
															int s = Integer
																	.parseInt(r[2]) / 60000;
															Message.ShowMsgDialog(
																	mContext,
																	"需過"
																			+ s
																			+ "分鐘才能再玩一次");
														}
													} else if(result.indexOf("achived") != -1) {
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														
														String uid = AccountUtil.getUid(mContext);
														String rec = gi.getId().substring(0, 3) + "_" + uid.substring(0,3);
														if(gi.getCode() != null)
															rec = gi.getCode();
														
														MyAlertDialog
																.setMessage("恭喜您完成了此個人獎勵。請儘速到完成獎勵區兌換。");
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
													} else {
														
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														MyAlertDialog
																.setMessage("目前您已經參與了"+ result +"次。過一陣子歡迎繼續參與此個人獎勵。" );
														
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
												}
											});
								}
							});
				}

			}
		});

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
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
				Log.d(Constants.TAG, "google ad error" + errorCode);
//				click = true;
				Builder MyAlertDialog = new AlertDialog.Builder(
						mContext);
				MyAlertDialog
						.setMessage("目前廣告無法秀出，請稍候再嘗試。" );
				
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

			@Override
			public void onAdLeftApplication() {
				// TODO Auto-generated method stub
				super.onAdLeftApplication();
				Log.d(Constants.TAG, "google ad onAdLeftApplication");
				click = true;
				// sp.edit().putLong(Constants.KEY_CLICK_AD,
				// System.currentTimeMillis()).commit();
				// adView.setLayoutParams(new
				// LayoutParams(LayoutParams.MATCH_PARENT, 0));
			}

			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Log.d(Constants.TAG, "google ad load");

			}

			@Override
			public void onAdOpened() {
				super.onAdOpened();
				Log.d(Constants.TAG, "google ad onAdOpened");
				click = true;
				// sp.edit().putLong(Constants.KEY_CLICK_AD,
				// System.currentTimeMillis()).commit();
				// adView.setLayoutParams(new
				// LayoutParams(LayoutParams.MATCH_PARENT, 0));
			}

		});
		return rootView;
	}


	
}
