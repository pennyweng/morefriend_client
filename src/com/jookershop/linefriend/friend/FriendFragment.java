package com.jookershop.linefriend.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.discuss.FriendDiscussFragment;
import com.jookershop.linefriend.util.AdUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class FriendFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private String mainColor;
	private int currentType = Constants.TYPE_INTEREST;
	private String title;
	private GridView gridView;
	private String categoryId;
	DisplayImageOptions options;	
	private RelativeLayout notificationRL;
	private FragmentTabHost tabHost;
	private LinearLayout lla;
	private String enableColor;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FriendFragment newInstance(int type, String title, String categoryId) {
		String color = "#A3A948";
		String enableColor = Constants.INTEREST_ENABLE_COLOR;
		
		if(type == Constants.TYPE_INTEREST) {
			color = Constants.INTEREST_COLOR;
			enableColor = Constants.INTEREST_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_PLACE) {
			color = Constants.PLACE_COLOR;
			enableColor = Constants.PLACE_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_CAREER) {
			color = Constants.CAREER_COLOR;
			enableColor = Constants.CAREER_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_OLD) {
			color = Constants.OLD_COLOR;
			enableColor = Constants.OLD_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_CONSTELLATION) {
			color = Constants.CONSTELLATION_COLOR;
			enableColor = Constants.CONSTELLATION_ENABLE_COLOR;
		}
		
		FriendFragment fragment = new FriendFragment(type, color, title, categoryId, enableColor);
		// Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}

//	public FriendFragment() {
//	}
	
	public FriendFragment(int type, String color, String title, String categoryId, String enableColor) {
		mainColor= color;
		currentType = type;
		this.title = title;
		this.categoryId = categoryId;
		this.enableColor = enableColor;
		
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.emptyhead)
		.showImageForEmptyUri(R.drawable.emptyhead)
		.showImageOnFail(R.drawable.emptyhead)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.resetViewBeforeLoading(true)
		.build();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		if(this.getActivity() != null && this.getActivity().getActionBar() != null)
//		this.getActivity().getActionBar().hide();
		SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		View rootView = inflater.inflate(R.layout.fragment_friend_post, container,
				false);
		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		if(mainColor == null || mainColor == "") mainColor = Constants.INTEREST_COLOR;
		RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.rl);
		rl.setBackgroundColor(Color.parseColor(mainColor));
		
		notificationRL = (RelativeLayout) rootView.findViewById(R.id.rl3);
		lla = (LinearLayout) rootView.findViewById(R.id.lla);
		TextView titleTv = (TextView) rootView.findViewById(R.id.textView1);
		titleTv.setText(title);
		
		tabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
		
		Button backbt = (Button)rootView.findViewById(R.id.button1);
		backbt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FriendFragment.this.getActivity().getSupportFragmentManager().popBackStack();
			}
		});		

		backbt.setBackgroundColor(Color.parseColor(mainColor));
		updateUi();
		
//		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
//		String url = Constants.BASE_URL + "/linefriend/notify/get?uid=" + uid;
//		Log.d(Constants.TAG, "get notification url " + url );
//		AsyncHttpGet ahg = new AsyncHttpGet(url);
//		AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg, new AsyncHttpClient.JSONObjectCallback() {
//	    @Override
//		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONObject result1) {
//		        if (e != null) {
//		            e.printStackTrace();
//		            return;
//		        }
//		        
//				final boolean hasNotification = !result1.isNull("n");
//				if(FriendFragment.this != null && FriendFragment.this.getActivity() != null)
//					FriendFragment.this.getActivity().runOnUiThread(new Runnable() {
//					public void run() {
//						if(hasNotification) {
//							notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//						} else {
//							notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));								
//						}
//					}
//				});
//	    	}
//		});
		
		return rootView;
	}

	
	private void updateUi() {
		String subTitle = title.substring(title.indexOf("/")+1);
		
		Bundle bn = new Bundle();
		bn.putInt("currentType", currentType);
		bn.putString("categoryId", categoryId);
		bn.putString("mainColor", mainColor);
		bn.putString("title", title);
		bn.putString("subtitle", subTitle + "的討論區");
		bn.putString("enableColor", enableColor);
		
	    tabHost.setup(this.getActivity(), getChildFragmentManager(), R.id.realtabcontent);		
		tabHost.addTab(tabHost.newTabSpec(subTitle + "的LINE友").setIndicator(subTitle + "的LINE友"), FriendIDFragment.class, bn );
//		tabHost.addTab(tabHost.newTabSpec(subTitle + "的討論區").setIndicator(subTitle + "的討論區"), FriendDiscussFragment.class, bn );
//		tabHost.addTab(tabHost.newTabSpec("我要七嘴八舌").setIndicator("我要七嘴八舌"), FriendDiscussFragment.class, bn );

		TabWidget tw = (TabWidget)tabHost.findViewById(android.R.id.tabs);
		View tabView = tw.getChildTabViewAt(0);
		TextView tv = (TextView)tabView.findViewById(android.R.id.title);
		tv.setTextSize(14);
		
		View tabView1 = tw.getChildTabViewAt(1);
		TextView tv1 = (TextView)tabView1.findViewById(android.R.id.title);
		tv1.setTextSize(14);
		tv.setTextColor(Color.parseColor("#6B6B6B"));
		tv1.setTextColor(Color.parseColor("#6B6B6B"));
		
//		if(currentType == Constants.TYPE_INTEREST) {
//			lla.setBackgroundColor(Color.parseColor(Constants.INTEREST_HEADER_COLOR));
//			tv.setTextColor(Color.parseColor(Constants.INTEREST_ENABLE_COLOR));
//			tv1.setTextColor(Color.parseColor(Constants.INTEREST_ENABLE_COLOR));
//		} else if(currentType == Constants.TYPE_PLACE) {
//			lla.setBackgroundColor(Color.parseColor(Constants.PLACE_HEADER_COLOR));
//			tv.setTextColor(Color.parseColor(Constants.PLACE_ENABLE_COLOR));
//			tv1.setTextColor(Color.parseColor(Constants.PLACE_ENABLE_COLOR));			
//		} else if(currentType == Constants.TYPE_CAREER) {
//			lla.setBackgroundColor(Color.parseColor(Constants.CAREER_HEADER_COLOR));
//			tv.setTextColor(Color.parseColor(Constants.CAREER_ENABLE_COLOR));
//			tv1.setTextColor(Color.parseColor(Constants.CAREER_ENABLE_COLOR));			
//		} else if(currentType == Constants.TYPE_OLD) {
//			lla.setBackgroundColor(Color.parseColor(Constants.OLD_HEADER_COLOR));
//			tv.setTextColor(Color.parseColor(Constants.OLD_ENABLE_COLOR));
//			tv1.setTextColor(Color.parseColor(Constants.OLD_ENABLE_COLOR));			
//		}else if(currentType == Constants.TYPE_CONSTELLATION) {
//			lla.setBackgroundColor(Color.parseColor(Constants.CONSTELLATION_HEADER_COLOR));
//			tv.setTextColor(Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR));
//			tv1.setTextColor(Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR));			
//		}		
	}
//	public void loadItmes(String categoryType, String categoryId, int page, String sex, final boolean first) {
//		String url = Constants.BASE_URL + "account/search?categoryType=" + categoryType 
//					+ "&categoryId=" + categoryId 
//					+ "&page=" + page
//					+ "&s=" + sex;
//		Log.d(Constants.TAG, "search url " + url );
//		AsyncHttpGet ahg = new AsyncHttpGet(url);
//		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
//		    @Override
//		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
//		        if (e != null) {
//		            e.printStackTrace();
//		            return;
//		        }
//		        if(first && result.length() == 0) Message.ShowMsgDialog(FriendFragment.this.getActivity(), "很抱歉, 此類別目前沒有人符合條件!!!");
//				final ArrayList<FriendItem> res = new ArrayList<FriendItem>();
//				for (int index = 0; index < result.length(); index++) {
//					try {
////						 "in":"${interests}", "pn":"${places}", "cn":"${careers}", "on":"${olds}"
//						JSONObject jo = result.getJSONObject(index);
//						FriendItem fi = new FriendItem(jo.getString("uid"), jo.getString("nn"), jo.getString("lid"), jo.getString("s"));
//						if(jo.has("in") && !jo.isNull("in"))
//							fi.setInterestIds(jo.getString("in"));
//						if(jo.has("pn") && !jo.isNull("pn"))
//							fi.setPlaceIds(jo.getString("pn"));
//						if(jo.has("cn") && !jo.isNull("cn"))
//							fi.setCareerIds(jo.getString("cn"));
//						if(jo.has("on") && !jo.isNull("on"))
//							fi.setOldIds(jo.getString("on"));
//						res.add(fi);
//					} catch (JSONException e1) {
//						e1.printStackTrace();
//					}
//				}
//				if(FriendFragment.this != null && FriendFragment.this.getActivity()!= null)
//				FriendFragment.this.getActivity().runOnUiThread(new Runnable() {
//					public void run() {
//						FriendAdapter ia = (FriendAdapter) gridView.getAdapter();
//						ia.addAll(res);
//						ia.notifyDataSetChanged();
//					}
//				});
//		    }
//		});		
//	}

	
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(Constants.TAG, "FriendFragment");	
	}	
}
