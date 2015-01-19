package com.jookershop.linefriend.interest;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;


public class InterestFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private RelativeLayout notificationRL;
	private ProgressBar progressBar1;
	
	public static InterestFragment newInstance(int sectionNumber) {
		InterestFragment fragment = new InterestFragment();
		// Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}

	public InterestFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		if(this.getActivity().getActionBar() != null)
//			this.getActivity().getActionBar().show();

	    
//		if(this.getActivity() != null && this.getActivity().getActionBar() != null)
//		this.getActivity().getActionBar().hide();

//		View rootView = inflater.inflate(R.layout.fragment_interest, container,
//				false);

		View rootView = inflater.inflate(R.layout.fragment_interest, null);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		
		SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		notificationRL = (RelativeLayout) rootView.findViewById(R.id.rl3);
		
		final GridView gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

		// Instance of ImageAdapter Class

		gridView.setAdapter(new InterestAdapter(this.getActivity(),
				getNewInterestItem()));
//		gridView.setOnScrollListener(new EndlessScrollListener() {
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				// Triggered only when new data needs to be appended to the list
//				// Add whatever code is needed to append new items to your
//				// AdapterView
//
//				// or customLoadMoreDataFromApi(totalItemsCount);
//				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
//						+ totalItemsCount);
//				InterestAdapter ia = (InterestAdapter) gridView.getAdapter();
//				ia.addAll(getNewInterestItem());
//			}
//		});
		// TextView textView = (TextView) rootView
		// .findViewById(R.id.section_label);
		// textView.setText(Integer.toString(getArguments().getInt(
		// ARG_SECTION_NUMBER)));
		
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "account/count?categoryType=" + Constants.TYPE_INTEREST 
				+ "&s=" + sp.getString(Constants.SEARCH_SEX, "") + "&uid=" + uid;
		Log.d(Constants.TAG, "count url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg, new AsyncHttpClient.JSONObjectCallback() {
	    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONObject result1) {
	    	
	    	if(InterestFragment.this != null && InterestFragment.this.getActivity() != null)
				InterestFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});	    	
	    	
		        if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		        
				try {
					final boolean hasNotification = !result1.isNull("n");
					JSONArray result = result1.getJSONArray("r");
					final HashMap<String, Integer> res = new HashMap<String, Integer>();
					for (int index = 0; index < result.length(); index++) {
						JSONObject jo = result.getJSONObject(index);
						res.put(jo.getString("id"), jo.getInt("count"));
					}
				
					if(InterestFragment.this != null && InterestFragment.this.getActivity() != null)
					InterestFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							InterestAdapter ia = (InterestAdapter) gridView.getAdapter();
							for(int index = 0; index < ia.getCount(); index ++) {
								if(res.containsKey(ia.getItem(index).getCategoryId()))
								ia.getItem(index).setCount(res.get(ia.getItem(index).getCategoryId()));
							}
							if(hasNotification) {
								notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
							} else {
								notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));								
							}
							ia.notifyDataSetChanged();
						}
					});
					
				} catch (JSONException e2) {
					e2.printStackTrace();
				}
		        
	    	}
		});		
		
		return rootView;
	}

	public ArrayList<InterestItem> getNewInterestItem() {
		ArrayList<InterestItem> result = new ArrayList<InterestItem>();
		for (int index = 0; index < Constants.INTEREST_THUMBNAIL_IDS.length; index++) {
			result.add(new InterestItem(Constants.INTEREST_THUMBNAIL_IDS[index], Constants.INTEREST_TITLES[index], Constants.INTEREST_IDS[index], 0));
		}
		return result;
	}
}
