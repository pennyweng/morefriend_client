package com.jookershop.linefriend.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.account.AccountInfoAdapter;
import com.jookershop.linefriend.account.LikeItem;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AccountUtil {

	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.emptyhead)
	.showImageForEmptyUri(R.drawable.emptyhead)
	.showImageOnFail(R.drawable.emptyhead)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.resetViewBeforeLoading(true)
	.build();
	
	public static String getGamilID(Context context) {
		String result = "";
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				String possibleEmail = account.name;
				if (possibleEmail.indexOf("@gmail.com") != -1)
					result = possibleEmail.substring(0,
							possibleEmail.indexOf("@gmail.com"));
			}
		}

		return result;
	}

	public static String getUid(Context context) {
		SharedPreferences  sp = context.getSharedPreferences("linefriend", Context.MODE_APPEND);
		if(sp.contains("uid")) {
			return sp.getString("uid", Installation.id(context));
		} else {
			String account = getGamilID(context);
			Log.d(Constants.TAG, "account:" + account);
			if (account != "") {
				String uu = Crytal.toHash(account);
				sp.edit().putString("uid", uu).apply();
				return uu;
			} else {
				String uu = Installation.id(context);
				sp.edit().putString("uid", uu).apply();
				return uu;				
			}
		}
	}

	public static void showInfo(final Context context, String uid) {
		String euid = URLEncoder.encode(uid);

		String url = Constants.BASE_URL + "account/" + euid;
		Log.d(Constants.TAG, "get user profile url " + url);
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
							FriendItem fi = new FriendItem(jo.getString("uid"),
									jo.getString("nn"), jo.getString("lid"), jo
											.getString("s"));
							if (jo.has("in") && !jo.isNull("in"))
								fi.setInterestIds(jo.getString("in"));
							if (jo.has("pn") && !jo.isNull("pn"))
								fi.setPlaceIds(jo.getString("pn"));
							if (jo.has("cn") && !jo.isNull("cn"))
								fi.setCareerIds(jo.getString("cn"));
							if (jo.has("on") && !jo.isNull("on"))
								fi.setOldIds(jo.getString("on"));
							if (jo.has("constel") && !jo.isNull("constel"))
								fi.setConstellationIds(jo.getString("constel"));
							if (jo.has("mo") && !jo.isNull("mo"))
								fi.setMotionIds(jo.getString("mo"));
							Message.showAccountInfo(context, fi);
//							showDialog(context, fi);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}

					}
				});

	}
	
//	private static void showDialog(Context mContext, FriendItem interestItem) {
//		final AlertDialog.Builder builder = new Builder(mContext);
//		final View dialoglayout = LayoutInflater.from(mContext).inflate(
//				R.layout.account_info, null);
//
//		ImageView idv = (ImageView) dialoglayout
//				.findViewById(R.id.imageView1);
//		
//		Bitmap bm =  MainActivity.imageLoader.loadImageSync(Constants.IMAGE_BASE_URL
//				+ "account/image?uid=" + interestItem.getUid());
//		idv.setImageBitmap(bm);
////		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL
////				+ "account/image?uid=" + interestItem.getUid(), idv,
////				options, new SimpleImageLoadingListener(), null);
//
//		ArrayList<LikeItem> ii = new ArrayList<LikeItem>();
//
//		if (interestItem.getInterestIds() != null
//				&& interestItem.getInterestIds().length() > 0) {
//			String[] iis = interestItem.getInterestIds().split(",");
//			for (int index = 0; index < iis.length; index++) {
//				ii.add(new LikeItem(iis[index], true,
//						Constants.INTEREST_MAP.get(iis[index]),
//						Constants.INTEREST_COLOR));
//			}
//		}
//
//		if (interestItem.getPlaceIds() != null
//				&& interestItem.getPlaceIds().length() > 0) {
//			String[] iis1 = interestItem.getPlaceIds().split(",");
//			for (int index = 0; index < iis1.length; index++) {
//				ii.add(new LikeItem(iis1[index], true,
//						Constants.PLACE_MAP.get(iis1[index]),
//						Constants.PLACE_COLOR));
//			}
//		}
//
//		if (interestItem.getCareerIds() != null
//				&& interestItem.getCareerIds().length() > 0) {
//			String[] iis2 = interestItem.getCareerIds().split(",");
//			for (int index = 0; index < iis2.length; index++) {
//				ii.add(new LikeItem(iis2[index], true,
//						Constants.CAREER_MAP.get(iis2[index]),
//						Constants.CAREER_COLOR));
//			}
//		}
//
//		if (interestItem.getOldIds() != null
//				&& interestItem.getOldIds().length() > 0) {
//			String[] iis3 = interestItem.getOldIds().split(",");
//			for (int index = 0; index < iis3.length; index++) {
//				ii.add(new LikeItem(iis3[index], true,
//						Constants.OLD_MAP.get(iis3[index]),
//						Constants.OLD_COLOR));
//			}
//		}
//
//		if (interestItem.getConstellationIds() != null
//				&& interestItem.getConstellationIds().length() > 0) {
//			String[] iis4 = interestItem.getConstellationIds().split(
//					",");
//			for (int index = 0; index < iis4.length; index++) {
//				ii.add(new LikeItem(iis4[index], true,
//						Constants.CONSTELLATION_MAP.get(iis4[index]),
//						Constants.CONSTELLATION_COLOR));
//			}
//		}
//
//		GridView interestGV = (GridView) dialoglayout
//				.findViewById(R.id.gridView1);
//		interestGV.setAdapter(new AccountInfoAdapter(mContext, ii
//				.toArray(new LikeItem[ii.size()])));
//
//		TextView tv1 = (TextView) dialoglayout
//				.findViewById(R.id.TextView01);
//		tv1.setText("大家好！我的LINE ID是" + interestItem.getLineId() + "。 以下是我的個人基本資料與興趣");
//
//		builder.setView(dialoglayout);
//		((Activity)mContext).runOnUiThread(new Runnable() {
//			  public void run() {
//					final AlertDialog a = builder.create();
//
//					Button exitBt = (Button) dialoglayout
//							.findViewById(R.id.Button03);
//					exitBt.setOnClickListener(new View.OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							a.dismiss();
//						}
//					});
//					a.show();
//
//			  }
//			});
//		
//		
//		
//		
//	}
}
