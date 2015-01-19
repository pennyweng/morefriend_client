package com.jookershop.linefriend.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.account.AccountInfoAdapter;
import com.jookershop.linefriend.account.LikeItem;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.service.PlayReceiver;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class Message {
	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
			// .showImageOnLoading(R.drawable.emptyhead)
			.showImageForEmptyUri(R.drawable.emptyhead)
			.showImageOnFail(R.drawable.emptyhead).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
			.build();

	private static SharedPreferences sp;

	public void registerAlarm(Context context) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Intent intent = new Intent(context, PlayReceiver.class);
		intent.putExtra("msg", "play_hskay");

		PendingIntent pi = PendingIntent.getService(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, pi);
	}

	public static void ShowMsgDialog(final Context context, final String Msg) {
		if (context != null)
			((Activity) context).runOnUiThread(new Runnable() {
				public void run() {
					try {
						Builder MyAlertDialog = new AlertDialog.Builder(context);
						MyAlertDialog.setMessage(Msg);
						DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						};
						MyAlertDialog.setNeutralButton("確定", OkClick);
						MyAlertDialog.show();
					} catch (Exception e) {
						// e.printStackTrace();
					}

				}
			});
	}

	public static void showAccountInfo(final Context mContext,
			final FriendItem interestItem) {
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_PRIVATE);
		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				final AlertDialog.Builder builder = new Builder(mContext);
				View dialoglayout = LayoutInflater.from(mContext).inflate(
						R.layout.account_info, null);

				ImageView idv = (ImageView) dialoglayout
						.findViewById(R.id.imageView1);
				MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL
						+ "account/image?uid=" + interestItem.getUid(), idv,
						options, new SimpleImageLoadingListener(), null);

				ArrayList<LikeItem> ii = new ArrayList<LikeItem>();

				if (interestItem.getInterestIds() != null
						&& interestItem.getInterestIds().length() > 0) {
					String[] iis = interestItem.getInterestIds().split(",");
					for (int index = 0; index < iis.length; index++) {
						ii.add(new LikeItem(iis[index], true,
								Constants.INTEREST_MAP.get(iis[index]),
								Constants.INTEREST_COLOR));
					}
				}

				if (interestItem.getPlaceIds() != null
						&& interestItem.getPlaceIds().length() > 0) {
					String[] iis1 = interestItem.getPlaceIds().split(",");
					for (int index = 0; index < iis1.length; index++) {
						ii.add(new LikeItem(iis1[index], true,
								Constants.PLACE_MAP.get(iis1[index]),
								Constants.PLACE_COLOR));
					}
				}

				if (interestItem.getCareerIds() != null
						&& interestItem.getCareerIds().length() > 0) {
					String[] iis2 = interestItem.getCareerIds().split(",");
					for (int index = 0; index < iis2.length; index++) {
						ii.add(new LikeItem(iis2[index], true,
								Constants.CAREER_MAP.get(iis2[index]),
								Constants.CAREER_COLOR));
					}
				}

				if (interestItem.getOldIds() != null
						&& interestItem.getOldIds().length() > 0) {
					String[] iis3 = interestItem.getOldIds().split(",");
					for (int index = 0; index < iis3.length; index++) {
						ii.add(new LikeItem(iis3[index], true,
								Constants.OLD_MAP.get(iis3[index]),
								Constants.OLD_COLOR));
					}
				}

				if (interestItem.getConstellationIds() != null
						&& interestItem.getConstellationIds().length() > 0) {
					String[] iis4 = interestItem.getConstellationIds().split(
							",");
					for (int index = 0; index < iis4.length; index++) {
						ii.add(new LikeItem(iis4[index], true,
								Constants.CONSTELLATION_MAP.get(iis4[index]),
								Constants.CONSTELLATION_COLOR));
					}
				}

				if (interestItem.getMotionIds() != null
						&& interestItem.getMotionIds().length() > 0) {
					String[] iis5 = interestItem.getMotionIds().split(",");
					for (int index = 0; index < iis5.length; index++) {
						ii.add(new LikeItem(iis5[index], true,
								Constants.MOTION_MAP.get(iis5[index]),
								Constants.MOTION_COLOR));
					}
				}

				GridView interestGV = (GridView) dialoglayout
						.findViewById(R.id.gridView1);
				interestGV.setAdapter(new AccountInfoAdapter(mContext, ii
						.toArray(new LikeItem[ii.size()])));

				TextView tv1 = (TextView) dialoglayout
						.findViewById(R.id.TextView01);
				
				if (interestItem.getSex().equals("M")){
					tv1.setText("男生。LINE ID是" + interestItem.getLineId());
					tv1.setBackgroundColor(Color.parseColor("#AF614BA3"));
				}
				else if (interestItem.getSex().equals("F")){
					tv1.setText("女生。LINE ID是" + interestItem.getLineId());
					tv1.setBackgroundColor(Color.parseColor("#AFBA4C90"));
					
				}				
				
				

				builder.setView(dialoglayout);
				final AlertDialog a = builder.create();

				Button exitBt = (Button) dialoglayout
						.findViewById(R.id.Button03);
				exitBt.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
//						a.dismiss();
						String myuid = URLEncoder.encode(AccountUtil
								.getUid(mContext));
						String uid = URLEncoder.encode(interestItem.getUid());
						// uid : String, toUid: String
						String url = Constants.BASE_URL + "block/add?uid="
								+ myuid + "&toUid=" + uid;

						Log.d(Constants.TAG, "add block  url " + url);
						AsyncHttpGet get = new AsyncHttpGet(url);

						AsyncHttpClient.getDefaultInstance().executeString(get,
								new AsyncHttpClient.StringCallback() {

									@Override
									public void onCompleted(Exception e,
											AsyncHttpResponse source,
											String result) {
										if (e != null) {
											Message.ShowMsgDialog(mContext,
													"Opps....發生錯誤, 請稍後再試！");
											e.printStackTrace();
											return;
										}
										((Activity) mContext)
												.runOnUiThread(new Runnable() {
													public void run() {
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														MyAlertDialog
																.setMessage("封鎖成功");
														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
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
				});

				Button bt = (Button) dialoglayout.findViewById(R.id.Button04);
				bt.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						final AlertDialog.Builder builder = new Builder(
								mContext);
						View dialoglayout = LayoutInflater.from(mContext)
								.inflate(R.layout.send_msg, null);
						final EditText mm = (EditText) dialoglayout
								.findViewById(R.id.editText1);
						ImageView ii = (ImageView) dialoglayout
								.findViewById(R.id.imageView1);
						MainActivity.imageLoader.displayImage(
								Constants.IMAGE_BASE_URL + "account/image?uid="
										+ interestItem.getUid(), ii, options,
								new SimpleImageLoadingListener(), null);

						builder.setView(dialoglayout);
						final AlertDialog aa = builder.create();

						Button cancel = (Button) dialoglayout
								.findViewById(R.id.Button03);
						cancel.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								aa.dismiss();
							}
						});

						final Button bt = (Button) dialoglayout
								.findViewById(R.id.button1);
						bt.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								bt.setEnabled(false);
								String uid = URLEncoder.encode(AccountUtil
										.getUid(mContext));
								String fromLid = URLEncoder.encode(sp
										.getString(Constants.LINE_STORE_KEY, ""));
								// String fromLid = URLEncoder.encode(myLineId);
								String toUid = URLEncoder.encode(interestItem
										.getUid());
								String toLid = URLEncoder.encode(interestItem
										.getLineId());
								String msg = URLEncoder.encode(mm.getText()
										.toString());

								String url = Constants.BASE_URL
										+ "new_msg/leave?fromUid=" + uid
										+ "&fromLid=" + fromLid + "&toUid="
										+ toUid + "&toLid=" + toLid + "&msg="
										+ msg;
								Log.d(Constants.TAG, "send msg url " + url);

								AsyncHttpGet ahg = new AsyncHttpGet(url);
								AsyncHttpClient
										.getDefaultInstance()
										.executeString(
												ahg,
												new AsyncHttpClient.StringCallback() {
													@Override
													public void onCompleted(
															Exception e,
															AsyncHttpResponse response,
															String result1) {
														((Activity) mContext)
																.runOnUiThread(new Runnable() {
																	public void run() {
																		bt.setEnabled(true);
																	}
																});
														aa.dismiss();
														if (e != null) {
															e.printStackTrace();
															Message.ShowMsgDialog(
																	mContext,
																	"無法留言, 請稍後再試！");
															return;
														}

														Message.ShowMsgDialog(
																mContext,
																"成功留言");
													}
												});

							}
						});

						aa.show();

					}
				});

				Button addBt = (Button) dialoglayout
						.findViewById(R.id.Button02);
				addBt.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String myuid = URLEncoder.encode(AccountUtil
								.getUid(mContext));
						String uid = URLEncoder.encode(interestItem.getUid());
						// uid : String, toUid: String
						String url = Constants.BASE_URL + "trace/add?uid="
								+ myuid + "&toUid=" + uid;

						Log.d(Constants.TAG, "add trace  url " + url);
						AsyncHttpGet get = new AsyncHttpGet(url);

						AsyncHttpClient.getDefaultInstance().executeString(get,
								new AsyncHttpClient.StringCallback() {

									@Override
									public void onCompleted(Exception e,
											AsyncHttpResponse source,
											String result) {
										if (e != null) {
											Message.ShowMsgDialog(mContext,
													"Opps....發生錯誤, 請稍後再試！");
											e.printStackTrace();
											return;
										}
										((Activity) mContext)
												.runOnUiThread(new Runnable() {
													public void run() {
														Builder MyAlertDialog = new AlertDialog.Builder(
																mContext);
														MyAlertDialog
																.setMessage("追蹤成功");
														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
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
				});

				TextView dds = (TextView) dialoglayout
						.findViewById(R.id.textView1);				
				dds.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(mContext).inflate(
								R.layout.send_block, null);
						final EditText mm = (EditText) dialoglayout
								.findViewById(R.id.editText1);
						ImageView ii = (ImageView) dialoglayout
								.findViewById(R.id.imageView1);
						MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL
								+ "account/image?uid=" + interestItem.getUid(), ii,
								options, new SimpleImageLoadingListener(), null);

						builder.setView(dialoglayout);
						final AlertDialog aa = builder.create();

						Button cancel = (Button) dialoglayout
								.findViewById(R.id.Button03);
						cancel.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								aa.dismiss();
							}
						});

						final Button bt = (Button) dialoglayout
								.findViewById(R.id.button1);
						bt.setOnClickListener(new View.OnClickListener() {

							// (fromUid : String, fromLid : String, toUid: String,
							// toLid: String, msg : String)
							@Override
							public void onClick(View v) {
								if(mm.getText().toString() == "") {
									Message.ShowMsgDialog(mContext,
											"需要填寫確實檢舉原因，以利後續查證作業。");
									return;
								}
								
								bt.setEnabled(false);
								String uid = URLEncoder.encode(AccountUtil
										.getUid(mContext));
								String toUid = URLEncoder.encode(interestItem.getUid());
								String msg = URLEncoder.encode(mm.getText().toString());

//								(uid : String, toUid: String, desc : String)	
								String url = Constants.BASE_URL + "block/report?uid="
										+ uid + "&toUid="
										+ toUid + "&desc=" + msg;
								Log.d(Constants.TAG, "send msg url " + url);

								AsyncHttpGet ahg = new AsyncHttpGet(url);
								AsyncHttpClient.getDefaultInstance().executeString(ahg,
										new AsyncHttpClient.StringCallback() {
											@Override
											public void onCompleted(Exception e,
													AsyncHttpResponse response,
													String result1) {
												((Activity) mContext)
														.runOnUiThread(new Runnable() {
															public void run() {
																bt.setEnabled(true);
															}
														});
												aa.dismiss();
												if (e != null) {
													e.printStackTrace();
													Message.ShowMsgDialog(mContext,
															"無法檢舉, 請稍後再試！");
													return;
												}

												Message.ShowMsgDialog(mContext, "成功檢舉，我們會儘快為您處理。");
											}
										});

							}
						});

						aa.show();
					}
				});				
				// Button copyBt = (Button)
				// dialoglayout.findViewById(R.id.Button02);
				// copyBt.setOnClickListener(new View.OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// a.dismiss();
				// }
				// });
				a.show();
			}
		});
	}

}
