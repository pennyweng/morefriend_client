package com.jookershop.linefriend.friend;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.EndlessScrollListener;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.interest.InterestAdapter;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class FriendIDFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private String mainColor;
	private int currentType = Constants.TYPE_INTEREST;
//	private String title;
	private GridView gridView;
	private String categoryId;
	DisplayImageOptions options;
	private String enableColor;
	private ProgressBar progressBar1;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FriendIDFragment newInstance(int type, String title, String categoryId, String enableColor) {
		String color = Constants.PLACE_COLOR;
		
//		if(type == Constants.TYPE_INTEREST) color = Constants.INTEREST_COLOR;
//		else if(type == Constants.TYPE_PLACE) color = Constants.PLACE_COLOR;
//		else if(type == Constants.TYPE_CAREER) color = Constants.CAREER_COLOR;
//		else if(type == Constants.TYPE_OLD) color = Constants.OLD_COLOR;
			
		FriendIDFragment fragment = new FriendIDFragment(type, color, categoryId, enableColor);
		// Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}

	public static FriendIDFragment newInstance(int type, String categoryId, String enableColor) {
		String color = Constants.PLACE_COLOR;
		
//		if(type == Constants.TYPE_INTEREST) color = Constants.INTEREST_COLOR;
//		else if(type == Constants.TYPE_PLACE) color = Constants.PLACE_COLOR;
//		else if(type == Constants.TYPE_CAREER) color = Constants.CAREER_COLOR;
//		else if(type == Constants.TYPE_OLD) color = Constants.OLD_COLOR;
			
		FriendIDFragment fragment = new FriendIDFragment(type, color, categoryId, enableColor);
		// Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}
	
	public FriendIDFragment() {
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
	
	public FriendIDFragment(int type, String color, String categoryId, String enableColor) {
		mainColor= color;
		currentType = type;
//		this.title = title;
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
//		this.getActivity().getActionBar().hide();
		SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		View rootView = inflater.inflate(R.layout.fragment_friend_id, container,
				false);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		
//		Bundle bData = this.getArguments();
//		if(bData != null) {
//			currentType = bData.getInt("currentType");
//			categoryId = bData.getString("categoryId");
//			mainColor = bData.getString("mainColor");
//			title = bData.getString("title");
//			enableColor = bData.getString("enableColor");
//		}
		
		gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new FriendAdapter(this.getActivity(),
				new ArrayList<FriendItem>(), mainColor, options, enableColor));
		
		final String sex = sp.getString(Constants.SEARCH_SEX, "");
		loadItmes(currentType + "", categoryId, 0, sex, true);
		gridView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
						+ totalItemsCount);
				loadItmes(currentType + "", categoryId, page, sex, false);
//				FriendAdapter ia = (FriendAdapter) gridView.getAdapter();
//				ia.addAll(getNewInterestItem());
			}
		});
		return rootView;
	}

	public void loadItmes(String categoryType, String categoryId, int page, String sex, final boolean first) {
		String url = Constants.BASE_URL + "account/search?categoryType=" + categoryType 
					+ "&categoryId=" + categoryId 
					+ "&page=" + page
					+ "&s=" + sex;
		Log.d(Constants.TAG, "search url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
		    	if(FriendIDFragment.this != null && FriendIDFragment.this.getActivity()!= null)
					FriendIDFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							progressBar1.setVisibility(View.INVISIBLE);
						}
					});
		    	
		    	
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		        if(first && result.length() == 0) Message.ShowMsgDialog(FriendIDFragment.this.getActivity(), "很抱歉, 此類別目前沒有人符合條件!!!");
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
				if(FriendIDFragment.this != null && FriendIDFragment.this.getActivity()!= null)
				FriendIDFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						FriendAdapter ia = (FriendAdapter) gridView.getAdapter();
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
