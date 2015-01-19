package com.jookershop.linefriend.newdisc;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout.LayoutParams;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.friend.FriendAdapter;
import com.jookershop.linefriend.friend.FriendFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;


public class DiscussFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private SharedPreferences sp;
	
	public static DiscussFragment newInstance() {
		DiscussFragment fragment = new DiscussFragment();
		return fragment;
	}

	public DiscussFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		if(this.getActivity().getActionBar() != null)
//			this.getActivity().getActionBar().show();
//		View rootView = inflater.inflate(R.layout.fragment_chat, container,
//				false);
		View rootView = inflater.inflate(R.layout.fragment_chat, null);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		
		final GridView gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

//		ArrayList<DiscussItem> jj = new ArrayList();
//		for(int index = 0; index < Constants.DISCUSS_MENU_TITLES.length; index ++) {
//			jj.add(new DiscussItem("1", 
//					Constants.DISCUSS_MENU_TITLES[index], 
//					Constants.DISCUSS_MENU_DESCS[index], 
//					Constants.DISCUSS_MENU_NUM[index]));
//		}
		
		gridView.setAdapter(new DiscussAdapter(this.getActivity(),new ArrayList<DiscussItem>()));
		
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "disc/searchall?uid="+uid;
		Log.d(Constants.TAG, "disc menu url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
	    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final JSONArray result) {
		        if (e != null) {
		            e.printStackTrace();
		            return;
		        }

		        if(DiscussFragment.this != null && DiscussFragment.this.getActivity()!= null)
		        DiscussFragment.this.getActivity().runOnUiThread( new Runnable() {
		        	public void run() {
				        DiscussAdapter ia = (DiscussAdapter) gridView.getAdapter();
						for (int index = 0; index < result.length(); index++) {
							try {
								JSONObject jj = result.getJSONObject(index);
								ia.add(new DiscussItem(jj.getString("id"), jj.getString("name"), jj.getString("desc"), jj.getString("num")));
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						}
						ia.notifyDataSetChanged();
					}
		        });
	    	}
		});		
		
		return rootView;
	}
}
