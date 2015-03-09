package com.jookershop.linefriend;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;









//import com.flurry.android.FlurryAgent;
//import com.flurry.android.FlurryAgent;
import com.jookershop.linefriend.account.AccountActivity;
import com.jookershop.linefriend.ad.AdColonyAD;
import com.jookershop.linefriend.ad.AppnextAD;
import com.jookershop.linefriend.ad.DomobOffer;
import com.jookershop.linefriend.ad.VponAD;
import com.jookershop.linefriend.ad.VungleAD;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.category.CategoryFragment;
import com.jookershop.linefriend.chat.ChatFragment;
import com.jookershop.linefriend.constellation.ConstellationFragment;
import com.jookershop.linefriend.discuss.AllPostFragment;
import com.jookershop.linefriend.divine.AllDivineFragment;
import com.jookershop.linefriend.friend.FriendAdapter;
import com.jookershop.linefriend.friend.FriendIDFragment;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.game.AllGameFragment;
import com.jookershop.linefriend.gcm.GCMTask;
import com.jookershop.linefriend.gift.AllGiftFragment;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.lucky.LuckyMenuFragment;
import com.jookershop.linefriend.msg.NewMessageFragment1;
import com.jookershop.linefriend.newdisc.DiscussFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.online.OnlineFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.today.TodayFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend.util.SchedulerUtil;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
//import com.millennialmedia.android.MMSDK;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
//import com.purplebrain.adbuddiz.sdk.AdBuddiz;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
//import android.widget.PopupMenu;
//import com.startapp.android.publish.StartAppSDK;

//public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

public class MainActivity extends FragmentActivity {
	private String[] lunch = { "各項分類區", "訊息中心/追蹤名單/黑名單", "正在線上的人",
			"今日新進/搶手/活躍人物", "團體遊戲區", "歡喜抽獎區", "個人獎勵區", "LINE群組聊天室", };
	private String[] lunch1 = { "各項分類區", "訊息中心/追蹤名單/黑名單", "正在線上的人",
			"今日新進/搶手/活躍人物", "團體遊戲區", "歡喜抽獎區", "個人獎勵區", "LINE群組聊天室",
			"最新話題" };

	private String[] notiHour = { "1小時", "2小時", "3小時", "6小時", "12小時", "18小時",
			"24小時", "48小時", "72小時" };
	private int[] hh = { 1, 2, 3, 6, 12, 18, 24, 48, 72 };
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private SharedPreferences sp;
	static File cacheDir;
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options;
	public static DisplayImageOptions emptyHeadOptions;
	private int prePosition = -1;
	private InterestFragment pif = null;
	private PlaceFragment ppf = null;
	private CareerFragment pcf = null;
	private OldFragment pof = null;
	private ConstellationFragment pconf = null;
	private OnlineFragment ponlinef = null;
	private ChatFragment pchatf = null;
	private DiscussFragment paf = null;
	private NewMessageFragment1 nmf = null;
	private LuckyMenuFragment agf = null;
	private AllGameFragment aeef = null;
	private CategoryFragment cf = null;
	private TodayFragment tf = null;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MMSDK.initialize(this);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		sp = getSharedPreferences("linefriend", Context.MODE_APPEND);
		if (Constants.IS_SUPER)
			lunch = lunch1;

		setContentView(R.layout.activity_main);
		SchedulerUtil.unregister(this);

		// sp.edit().remove(Constants.NOTIFY_HOUR).commit();
		if (sp.getBoolean(Constants.NOTIFY_STATUS, true)) {
			// SchedulerUtil.unregister(this);
			// SchedulerUtil.register(this, sp.getInt(Constants.NOTIFY_HOUR,
			// 3));
			GCMTask task = new GCMTask();
			task.launchGCM(this);
		} else {

		}

		final Spinner spinner = (Spinner) this.findViewById(R.id.spinner1);
		// spinner.setBackgroundColor(Color.parseColor("#000000"));
		ArrayAdapter lunchList = new ArrayAdapter<String>(MainActivity.this,
				R.layout.spinner_item, lunch);
		// lunchList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(lunchList);

		// savedInstanceState.getBoolean("GoToMsg")
		Bundle it = this.getIntent().getExtras();

		if (it != null && it.getBoolean("GoToMsg", false))
			spinner.setSelection(1);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(Constants.TAG, "onItemSelected in");

				for (int index = 0; index < getSupportFragmentManager()
						.getBackStackEntryCount(); index++) {
					getSupportFragmentManager().popBackStack();
				}

				if (position == 0) {
					cf = CategoryFragment.newInstance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, cf).commit();
				} else if (position == 1) {
					nmf = NewMessageFragment1.newInstance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, nmf).commit();
				} else if (position == 2) {
					ponlinef = OnlineFragment.newInstance(position + 1);
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, ponlinef).commit();

				} else if (position == 3) {
					tf = TodayFragment.newInstance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, tf).commit();

//				} else if (position == 4) {
//					paf = DiscussFragment.newInstance();
//					getSupportFragmentManager().beginTransaction()
//							.replace(R.id.container, paf).commit();
				} else if (position == 4) {
					aeef = AllGameFragment.newInstance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, aeef).commit();

				} else if (position == 5) {
					agf = LuckyMenuFragment.newInstance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, agf).commit();

				} else if (position == 7) {
					pchatf = ChatFragment.newInstance(position + 1);
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, pchatf).commit();

				} else if (position == 6) {
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.container,
									AllGiftFragment.newInstance()).commit();

				} else if (position == 8) {
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.container,
									AllPostFragment.newInstance()).commit();

				}

				prePosition = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		final Button menuBt = (Button) this.findViewById(R.id.button1);
		menuBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// openOptionsMenu();
				// Creating the instance of PopupMenu
				PopupMenu popup = new PopupMenu(MainActivity.this, menuBt);
				// Inflating the Popup using xml file
				popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

				// registering popup with OnMenuItemClickListener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						int id = item.getItemId();
						if (id == R.id.action_settings) {
							Intent it = new Intent();
							it.setClass(MainActivity.this,
									AccountActivity.class);
							MainActivity.this.startActivity(it);

							return true;
						} else if (id == R.id.search_settings) {
							MainActivity.this.runOnUiThread(new Runnable() {
								public void run() {

									Builder MyAlertDialog = new AlertDialog.Builder(
											MainActivity.this);

									MyAlertDialog.setMessage("想要查詢男生還是女生呢?");
									DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											sp.edit()
													.putString(
															Constants.SEARCH_SEX,
															"M").commit();
											finish();
											startActivity(getIntent());
										}
									};
									DialogInterface.OnClickListener OkClick1 = new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											sp.edit()
													.putString(
															Constants.SEARCH_SEX,
															"F").commit();
											finish();
											startActivity(getIntent());
										}
									};
									DialogInterface.OnClickListener OkClick2 = new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											sp.edit()
													.putString(
															Constants.SEARCH_SEX,
															"").commit();
											finish();
											startActivity(getIntent());
										}
									};
									MyAlertDialog.setNeutralButton("男生",
											OkClick);
									MyAlertDialog.setPositiveButton("女生",
											OkClick1);
									MyAlertDialog.setNegativeButton("都可以",
											OkClick2);
									MyAlertDialog.show();

								}
							});

							return true;
						} else if (id == R.id.hide_settings) {
							MainActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									Builder MyAlertDialog = new AlertDialog.Builder(
											MainActivity.this);
									View dialoglayout = LayoutInflater.from(
											MainActivity.this).inflate(
											R.layout.setting_hide, null);
									MyAlertDialog.setView(dialoglayout);
									final AlertDialog aa = MyAlertDialog
											.create();

									final ToggleButton switch1 = (ToggleButton) dialoglayout
											.findViewById(R.id.toggleButton1);
									switch1.setChecked(sp.getBoolean(
											Constants.HIDE_SETTING, false));

									Button cancel = (Button) dialoglayout
											.findViewById(R.id.Button04);
									cancel.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											aa.dismiss();
										}
									});

									Button save = (Button) dialoglayout
											.findViewById(R.id.Button02);
									save.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											sp.edit()
													.putBoolean(
															Constants.HIDE_SETTING,
															switch1.isChecked())
													.commit();
											String uid = URLEncoder.encode(AccountUtil
													.getUid(MainActivity.this));
											String url = Constants.BASE_URL
													+ "account/hidesetting?uid="
													+ uid + "&isHide="
													+ switch1.isChecked();
											AsyncHttpGet ahg = new AsyncHttpGet(
													url);
											AsyncHttpClient
													.getDefaultInstance()
													.executeString(
															ahg,
															new AsyncHttpClient.StringCallback() {

																@Override
																public void onCompleted(
																		Exception e,
																		AsyncHttpResponse source,
																		String result) {
																	if (e != null) {
																		Message.ShowMsgDialog(
																				MainActivity.this,
																				"Opps....發生錯誤, 請稍後再試");
																		e.printStackTrace();
																		return;
																	}

																	MainActivity.this
																			.runOnUiThread(new Runnable() {
																				public void run() {
																					Builder MyAlertDialog = new AlertDialog.Builder(
																							MainActivity.this);
																					if (switch1
																							.isChecked())
																						MyAlertDialog
																								.setMessage("隱藏成功");
																					else
																						MyAlertDialog
																								.setMessage("取消隱藏成功");

																					DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
																						public void onClick(
																								DialogInterface dialog,
																								int which) {
																						}
																					};
																					MyAlertDialog
																							.setNeutralButton(
																									"確定",
																									OkClick);
																					MyAlertDialog
																							.show();

																				}
																			});

																}
															});

											aa.dismiss();

										}
									});
									aa.show();
								}
							});

							return true;
						} else if (id == R.id.notify_settings) {
							MainActivity.this.runOnUiThread(new Runnable() {
								public void run() {

									Builder MyAlertDialog = new AlertDialog.Builder(
											MainActivity.this);
									View dialoglayout = LayoutInflater.from(
											MainActivity.this).inflate(
											R.layout.setting_notify1, null);
									// final Spinner spinner = (Spinner)
									// dialoglayout.findViewById(R.id.spinner1);
									// ArrayAdapter lunchList = new
									// ArrayAdapter<String>(MainActivity.this,R.layout.spinner_item,
									// notiHour);
									// spinner.setAdapter(lunchList);
									// spinner.setSelection(getPosition(sp.getInt(Constants.NOTIFY_HOUR,
									// 3)));

									MyAlertDialog.setView(dialoglayout);
									final AlertDialog aa = MyAlertDialog
											.create();

									// final Switch switch1 = (Switch)
									// dialoglayout
									// .findViewById(R.id.switch1);
									// switch1.setChecked(sp.getBoolean(Constants.NOTIFY_STATUS,
									// true));
									// switch1.setOnCheckedChangeListener(new
									// CompoundButton.OnCheckedChangeListener()
									// {
									//
									// @Override
									// public void
									// onCheckedChanged(CompoundButton
									// buttonView, boolean isChecked) {
									// }
									// });

									final ToggleButton switch1 = (ToggleButton) dialoglayout
											.findViewById(R.id.toggleButton1);
									switch1.setChecked(sp.getBoolean(
											Constants.NOTIFY_STATUS, true));
									final ToggleButton switch2 = (ToggleButton) dialoglayout
											.findViewById(R.id.ToggleButton01);
									switch2.setChecked(sp.getBoolean(
											Constants.NOTIFY_MUSIC, true));
									final ToggleButton switch3 = (ToggleButton) dialoglayout
											.findViewById(R.id.ToggleButton02);
									switch3.setChecked(sp.getBoolean(
											Constants.NOTIFY_VIR, false));

									Button cancel = (Button) dialoglayout
											.findViewById(R.id.Button04);
									cancel.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											aa.dismiss();
										}
									});

									Button save = (Button) dialoglayout
											.findViewById(R.id.Button02);
									save.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// sp.edit().putInt(Constants.NOTIFY_HOUR,
											// hh[spinner.getSelectedItemPosition()]).commit();
											sp.edit()
													.putBoolean(
															Constants.NOTIFY_STATUS,
															switch1.isChecked())
													.commit();
											sp.edit()
													.putBoolean(
															Constants.NOTIFY_MUSIC,
															switch2.isChecked())
													.commit();
											sp.edit()
													.putBoolean(
															Constants.NOTIFY_VIR,
															switch3.isChecked())
													.commit();
											// SchedulerUtil.unregister(MainActivity.this);
											GCMTask task = new GCMTask();
											if (switch1.isChecked()) {
												Log.d(Constants.TAG,
														"switch is checked");

												task.launchGCM(MainActivity.this);
												// SchedulerUtil.register(MainActivity.this,
												// sp.getInt(Constants.NOTIFY_HOUR,
												// 3));
											} else {
												Log.d(Constants.TAG,
														"switch is un checked");
												task.sendUnRegistrationIdToBackend(MainActivity.this);
												// SchedulerUtil.unregister(MainActivity.this);

											}
											aa.dismiss();

										}
									});
									aa.show();

								}
							});

							return true;

						}

						// Toast.makeText(MainActivity.this,"You Clicked : " +
						// item.getTitle(),Toast.LENGTH_SHORT).show();
						return true;
					}
				});

				popup.show();// showing popup menu
			}
		});

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"JunkFolder");
		else
			cacheDir = this.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.threadPoolSize(4)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(13)
				// default
				.diskCache(
						new com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache(
								cacheDir))
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.imageDownloader(new BaseImageDownloader(this)) // default
				.writeDebugLogs().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		if (!sp.getBoolean("cleanimg", false)) {
			imageLoader.clearMemoryCache();
			imageLoader.clearDiscCache();
			sp.edit().putBoolean("cleanimg", true).commit();
		}

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.emptyhead)
				// .showImageForEmptyUri(R.drawable.emptyhead)
				// .showImageOnFail(R.drawable.emptyhead)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true).build();

		emptyHeadOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.emptyhead)
				.showImageForEmptyUri(R.drawable.emptyhead)
				.showImageOnFail(R.drawable.emptyhead).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true).build();

		if(!AdUtil.showMsg(this)) loadMsg();
		Constants.init();
		
		loadData();
//		MMSDK.setLogLevel(MMSDK.LOG_LEVEL_DEBUG);
//		 FlurryAgent.setLogEnabled(false);
//	     FlurryAgent.setLogLevel(2);
//	     FlurryAgent.init(this, "B4WJFDKP854T8MV5BF8T");
//	     
//	     StartAppSDK.init(this, "102850087", "202342664", true);
//	     
//	     AdBuddiz.setPublisherKey("7ee6a234-4ee2-4017-bf02-981ba252fc9e");
//	     AdBuddiz.cacheAds(this);
//		AdManager.getInstance(this).init("b4595776c7c7f7dc", "c81da798d667dff4", false);
		AdManager.getInstance(this).init("6ffcbf053089276a", "7166a9a4ecd75f96", false);
		OffersManager.getInstance(this).onAppLaunch();
		VungleAD.init(this);
		DomobOffer.init(this);
		AdColonyAD.init(this);
//		SpotManager.getInstance(this).checkSDKProcess("b4595776c7c7f7dc", "c81da798d667dff4");
	}

	@Override
	public void onBackPressed() {
		Log.d(Constants.TAG,
				"getSupportFragmentManager().getBackStackEntryCount()"
						+ getSupportFragmentManager().getBackStackEntryCount());
		VponAD.shutdown();
		
//		if(!AppnextAD.noShow(this)) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				this.runOnUiThread(new Runnable() {
					public void run() {
						try {
							Builder MyAlertDialog = new AlertDialog.Builder(
									MainActivity.this);
							MyAlertDialog.setMessage("確定要離開Line分類交友嗎？");
							DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									MainActivity.this.finish();
								}
							};
							MyAlertDialog.setNeutralButton("確定", OkClick);
							MyAlertDialog.setNegativeButton("取消", null);
							MyAlertDialog.show();
						} catch (Exception e) {
							// e.printStackTrace();
						}
	
					}
				});
	
			} else {
				getSupportFragmentManager().popBackStack();
			}
//		}
	}

	  @Override
	  protected void onPause() {
	      super.onPause();
	      VungleAD.onPause();
	      AdColonyAD.onPause(this);
	  }	
	
	@Override
	public void onResume() {
		super.onResume();
		VungleAD.onResume();
		AdColonyAD.onResume(this);
		Log.d(Constants.TAG, "MainActivity");
	}

	public int getPosition(int value) {
		int result = 2;
		for (int index = 0; index < hh.length; index++) {
			if (hh[index] == value)
				result = index;
		}
		return result;
	}

	private void loadMsg() {
		String version = sp.getString("admin_msg_version", "0");
		String uid = URLEncoder.encode(AccountUtil.getUid(MainActivity.this));
		String url = Constants.BASE_URL + "admin_msg/get?uid=" + uid + "&ver="
				+ version;
		Log.d(Constants.TAG, "loadMsg url " + url);
		try {
			AsyncHttpGet ahg = new AsyncHttpGet(url);
			AsyncHttpClient.getDefaultInstance().executeString(ahg,
					new AsyncHttpClient.StringCallback() {
						@Override
						public void onCompleted(Exception e,
								AsyncHttpResponse response, String jo) {
							if (e != null) {
								e.printStackTrace();
								return;
							}
							try {
								if (jo != null && jo != "") {
									String ver = jo.split("##")[0];
									final String data = jo.split("##")[1];
									sp.edit()
											.putString("admin_msg_version", ver)
											.apply();
									if (MainActivity.this != null)
										MainActivity.this
												.runOnUiThread(new Runnable() {
													public void run() {

														Builder MyAlertDialog = new AlertDialog.Builder(
																MainActivity.this);
														TextView msg = new TextView(
																MainActivity.this);
														msg.setTextSize(16);
														msg.setText(Html.fromHtml(data));
														msg.setMovementMethod(LinkMovementMethod
																.getInstance());
														MyAlertDialog
																.setView(msg);

														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int which) {
															}
														};
														MyAlertDialog
																.setNeutralButton(
																		"確定",
																		OkClick);
														MyAlertDialog.show();
													}
												});
								}
							} catch (Throwable e1) {
								e1.printStackTrace();
							}

						}
					});

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private void loadData() {
		try {
			if (!sp.contains(Constants.LINE_STORE_KEY)) {
				String uid = URLEncoder.encode(AccountUtil
						.getUid(MainActivity.this));
				String url = Constants.BASE_URL + "account/" + uid;
				Log.d(Constants.TAG, "get account url " + url);
				AsyncHttpGet ahg = new AsyncHttpGet(url);
				AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg,
						new AsyncHttpClient.JSONObjectCallback() {
							@Override
							public void onCompleted(Exception e,
									AsyncHttpResponse response, JSONObject jo) {
								if (e != null) {
									e.printStackTrace();
									return;
								}
								try {
									FriendItem fi = new FriendItem(jo
											.getString("uid"), jo
											.getString("nn"), jo
											.getString("lid"), jo
											.getString("s"));
									if (jo.has("in") && !jo.isNull("in"))
										fi.setInterestIds(jo.getString("in"));
									if (jo.has("pn") && !jo.isNull("pn"))
										fi.setPlaceIds(jo.getString("pn"));
									if (jo.has("cn") && !jo.isNull("cn"))
										fi.setCareerIds(jo.getString("cn"));
									if (jo.has("on") && !jo.isNull("on"))
										fi.setOldIds(jo.getString("on"));
									if (jo.has("constel")
											&& !jo.isNull("constel"))
										fi.setConstellationIds(jo
												.getString("constel"));
									if (jo.has("mo") && !jo.isNull("mo"))
										fi.setMotionIds(jo.getString("mo"));

									sp.edit()
											.putString(
													Constants.INTEREST_STORE_KEY,
													fi.getInterestIds())
											.commit();
									sp.edit()
											.putString(
													Constants.PLACE_STORE_KEY,
													fi.getPlaceIds()).commit();
									sp.edit()
											.putString(
													Constants.CAREER_STORE_KEY,
													fi.getCareerIds()).commit();
									sp.edit()
											.putString(Constants.OLD_STORE_KEY,
													fi.getOldIds()).commit();
									sp.edit()
											.putString(
													Constants.CONSTELLATION_STORE_KEY,
													fi.getConstellationIds())
											.commit();
									sp.edit()
											.putString(
													Constants.MOTION_STORE_KEY,
													fi.getMotionIds()).commit();
									sp.edit()
											.putString(
													Constants.LINE_STORE_KEY,
													fi.getLineId()).commit();
									sp.edit()
											.putString(Constants.SEX_STORE_KEY,
													fi.getSex()).commit();

									if (fi.getLineId().equals("暫時隱藏")) {
										sp.edit()
												.putBoolean(
														Constants.HIDE_STORE_KEY,
														true).commit();
									} else
										sp.edit()
												.putBoolean(
														Constants.HIDE_STORE_KEY,
														false).commit();
								} catch (Throwable e1) {
									e1.printStackTrace();
								}

							}
						});
			} else {
				Log.d(Constants.TAG, "there is no line");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		OffersManager.getInstance(this).onAppExit();
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// intent) {
	// super.onActivityResult(requestCode, resultCode, intent);
	// for (Fragment fragment : getSupportFragmentManager().getFragments()) {
	// if(fragment.getChildFragmentManager() != null &&
	// fragment.getChildFragmentManager().getFragments() != null)
	// for (Fragment subfragment :
	// fragment.getChildFragmentManager().getFragments()) {
	// Log.d(Constants.TAG, "requestCode:" + requestCode +",resultCode" +
	// resultCode);
	// subfragment.onActivityResult(requestCode, resultCode, intent);
	// }
	// }
	// }
	
	
}
