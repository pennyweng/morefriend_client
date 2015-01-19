package com.jookershop.linefriend.lucky;

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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class JoinLuckyFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	DisplayImageOptions options;
	private String title;
	private boolean click;
	private String lkid;
	private EditText guessNumber;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static JoinLuckyFragment newInstance(String title, String lkid) {
		JoinLuckyFragment fragment = new JoinLuckyFragment(title, lkid);
		return fragment;
	}

	public JoinLuckyFragment(String title, String lkid) {
		this.title = title;
		this.lkid = lkid;

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
		final Context mContext = JoinLuckyFragment.this.getActivity();
		final SharedPreferences sp = this.getActivity().getSharedPreferences(
				"linefriend", Context.MODE_APPEND);

		View rootView = inflater.inflate(R.layout.lucky_join, container,
				false);
		TextView currentTv = (TextView) rootView.findViewById(R.id.textView2);
		currentTv.setText(title);

		final EditText desc = (EditText) rootView.findViewById(R.id.editText2);
		
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
				if (!click) {
					Message.ShowMsgDialog(mContext, "請先點選下方廣告");
				} else {
					final String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "lucky/playgame?uid=" + uid 
							+ "&lkid=" + lkid 
							+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""))
							+ "&desc=" + URLEncoder.encode(desc.getText().toString());

					Log.d(Constants.TAG, "lucky play url " + url);
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
													if (result.indexOf("err:1") != -1)
														Message.ShowMsgDialog(
																mContext,
																"此抽獎已經不存在");
													else if (result.indexOf("err:2") != -1)
														Message.ShowMsgDialog(
																mContext,
																"此抽獎人數已經到達上限，正等待開獎");
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
													} else {
														
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														MyAlertDialog
																.setMessage("您的抽獎號碼為" + result + "。歡迎過一陣子繼續參與此抽獎。越多抽獎號碼，中獎機率越高。");
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
				click = true;
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
