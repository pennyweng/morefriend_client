package com.jookershop.linefriend.today;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class HotNewFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private String mainColor;
	private GridView gridView;
	DisplayImageOptions options;
	private String enableColor;
	private ProgressBar progressBar1;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static HotNewFragment newInstance(int a) {
		HotNewFragment fragment = new HotNewFragment();
		return fragment;
	}
	
	public HotNewFragment() {
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
//		this.getActivity().getActionBar().show();
		
//		View rootView = inflater.inflate(R.layout.fragment_friend_live, container,
//				false);
		View rootView = inflater.inflate(R.layout.fragment_friend_live, null);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		
		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		mainColor = Constants.LIVE_COLOR;
		enableColor = Constants.LIVE_ENABLE_COLOR;
		
		gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new TodayNewFriendAdapter(this.getActivity(),
				new ArrayList<FriendItem>(), mainColor, options, enableColor));
		
		loadItmes(true);
		return rootView;
	}

	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "account/hot?uid=" + uid;
		Log.d(Constants.TAG, "online url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
				if(HotNewFragment.this != null && HotNewFragment.this.getActivity()!= null)
				HotNewFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});
				
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
//		        if(first && result.length() == 0) Message.ShowMsgDialog(HotNewFragment.this.getActivity(), "目前沒有人正在線上!!!");
				final ArrayList<FriendItem> res = new ArrayList<FriendItem>();
				for (int index = 0; index < result.length(); index++) {
					try {
//						 "in":"${interests}", "pn":"${places}", "cn":"${careers}", "on":"${olds}"
						JSONObject jo = result.getJSONObject(index);
						FriendItem fi = new FriendItem(jo.getString("uid"), jo.getString("nn"), jo.getString("lid"), jo.getString("s"));
						if(jo.has("in") && !jo.isNull("in"))
							fi.setInterestIds(jo.getString("in"));
						if(jo.has("pn") && !jo.isNull("pn"))
							fi.setPlaceIds(jo.getString("pn"));
						if(jo.has("cn") && !jo.isNull("cn"))
							fi.setCareerIds(jo.getString("cn"));
						if(jo.has("on") && !jo.isNull("on"))
							fi.setOldIds(jo.getString("on"));
						if(jo.has("constel") && !jo.isNull("constel"))
							fi.setConstellationIds(jo.getString("constel"));
						if(jo.has("mo") && !jo.isNull("mo"))
							fi.setMotionIds(jo.getString("mo"));						
						res.add(fi);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if(HotNewFragment.this != null && HotNewFragment.this.getActivity()!= null)
				HotNewFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						TodayNewFriendAdapter ia = (TodayNewFriendAdapter) gridView.getAdapter();
						for(int index = 0; index < res.size(); index ++)
						ia.add(res.get(index));
//						ia.addAll(res);
						ia.notifyDataSetChanged();
					}
				});
		    }
		});		
	}
}
