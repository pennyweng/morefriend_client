package com.jookershop.linefriend.game;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.EndlessScrollListener;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.ad.ADResult;
import com.jookershop.linefriend.ad.AdmobAD;
import com.jookershop.linefriend.ad.VponAD;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.constellation.ConstellationFragment;
import com.jookershop.linefriend.gift.ClickADFragment;
import com.jookershop.linefriend.interest.InterestAdapter;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.DisplayUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class GuessGameFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	DisplayImageOptions options;
	private String currentNumber;
	private boolean click;
	private String gid;
	private EditText guessNumber;


	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static GuessGameFragment newInstance(String nn, String gid) {
		GuessGameFragment fragment = new GuessGameFragment(nn, gid);
		return fragment;
	}

	public GuessGameFragment(String currentNumber, String gid) {
		this.currentNumber = currentNumber;
		this.gid = gid;

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.emptyhead)
				.showImageForEmptyUri(R.drawable.noimage)
				.showImageOnFail(R.drawable.noimage).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true).build();
	}

	private boolean checkValidNum() {
		try {
			String[] a = currentNumber.split("-");
			int guss = Integer.parseInt(guessNumber.getText().toString());
			return guss > Integer.parseInt(a[0])
					&& guss < Integer.parseInt(a[1]);
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Context mContext = GuessGameFragment.this.getActivity();
		final SharedPreferences sp = this.getActivity().getSharedPreferences(
				"linefriend", Context.MODE_APPEND);

		View rootView = inflater.inflate(R.layout.game_guess_number, container,
				false);
		TextView currentTv = (TextView) rootView.findViewById(R.id.textView2);
		currentTv.setText(currentNumber);
		guessNumber = (EditText) rootView.findViewById(R.id.editText1);
		final EditText desc = (EditText) rootView.findViewById(R.id.editText2);
		
		final ADResult adr = new ADResult();
		final ADResult adr1 = new ADResult();
		if(new Random().nextInt() % 2 == 0) {
			RelativeLayout adRelativeLayout3 = (RelativeLayout) rootView.findViewById(R.id.rl3);
			AdmobAD.show(mContext, adr, adRelativeLayout3);
			RelativeLayout adRelativeLayout4 = (RelativeLayout) rootView.findViewById(R.id.rl4);
			VponAD.show(mContext, adr1, adRelativeLayout4);
		} else {
			RelativeLayout adRelativeLayout3 = (RelativeLayout) rootView.findViewById(R.id.rl4);
			AdmobAD.show(mContext, adr, adRelativeLayout3);
			RelativeLayout adRelativeLayout4 = (RelativeLayout) rootView.findViewById(R.id.rl3);
			VponAD.show(mContext, adr1, adRelativeLayout4);
		}

		
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
				if (!adr.isClick() && !adr1.isClick()) {
					Message.ShowMsgDialog(mContext, "請用行動支持有興趣的廣告。如果沒有看到廣告，請過一陣子在玩。");
				} else if (!checkValidNum()) {
					Message.ShowMsgDialog(mContext, "請輸入正確數字");
				} else {
					final String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "game/guess/play?uid=" + uid 
							+ "&gid=" + gid 
							+ "&guess=" + URLEncoder.encode(guessNumber.getText().toString())
							+ "&desc=" + URLEncoder.encode(desc.getText().toString())
							+ "&lid=" + URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));

					Log.d(Constants.TAG, "game guess play url " + url);
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
																"此遊戲已經不存在");
													else if (result.indexOf("err:2") != -1)
														Message.ShowMsgDialog(
																mContext,
																"此遊戲已經結束");
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
													} else if(result.indexOf("winner") != -1) {
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														String rec = gid.substring(0, 3);
														MyAlertDialog
																.setMessage("恭喜您猜到了終極密碼。請立即加入版主的LINE ID:ahappychat，並主動告知您的中獎碼是" + rec + "。");
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
																.setMessage("沒猜中喔！目前終極密碼在" + result.replaceAll("##", "-") + "之間，過一陣子歡迎繼續參與此遊戲。");
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

//		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
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
//				click = true;
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
