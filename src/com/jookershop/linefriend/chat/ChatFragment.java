package com.jookershop.linefriend.chat;

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
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.friend.FriendAdapter;
import com.jookershop.linefriend.friend.FriendFragment;
import com.jookershop.linefriend.util.AdUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;


public class ChatFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private SharedPreferences sp;
	
	public static ChatFragment newInstance(int sectionNumber) {
		ChatFragment fragment = new ChatFragment();
		return fragment;
	}

	public ChatFragment() {
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

		gridView.setAdapter(new ChatAdapter(this.getActivity(),
				new ArrayList<ChatItem>()));
		
		String url = Constants.BASE_URL + "chat/search";
		Log.d(Constants.TAG, "count url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
	    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, final JSONArray result) {
		        if (e != null) {
		            e.printStackTrace();
		            return;
		        }

		        if(ChatFragment.this != null && ChatFragment.this.getActivity()!= null)
		        ChatFragment.this.getActivity().runOnUiThread( new Runnable() {
		        	public void run() {
				        ChatAdapter ia = (ChatAdapter) gridView.getAdapter();
						for (int index = 0; index < result.length(); index++) {
							try {
								Log.d(Constants.TAG, "chat string:" + result.getString(index));
								String [] v = result.getString(index).split("@");
								if(v.length == 6)
								ia.add(new ChatItem(v[0], v[1], Integer.parseInt(v[2]), Integer.parseInt(v[3]), v[4], v[5]));
								else if(v.length == 7)
								ia.add(new ChatItem(v[0], v[1], Integer.parseInt(v[2]), Integer.parseInt(v[3]), v[4], v[5], Integer.parseInt(v[6])));
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
