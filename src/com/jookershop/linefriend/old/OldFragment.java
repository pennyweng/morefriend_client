package com.jookershop.linefriend.old;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

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
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.interest.InterestAdapter;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.place.PlaceAdapter;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

/**
 * A placeholder fragment containing a simple view.
 */
public  class OldFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private RelativeLayout notificationRL;
	private ProgressBar progressBar1;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static OldFragment newInstance(int sectionNumber) {
		OldFragment fragment = new OldFragment();
		// Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}

	public OldFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		if(this.getActivity().getActionBar() != null)
//			this.getActivity().getActionBar().show();
//		View rootView = inflater.inflate(R.layout.fragment_interest, container,
//				false);
		View rootView = inflater.inflate(R.layout.fragment_interest, null);	
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
		
		SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		notificationRL = (RelativeLayout) rootView.findViewById(R.id.rl3);
		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		final GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);

		// Instance of ImageAdapter Class
		gridView.setAdapter(new OldAdapter(this.getActivity()));
		// TextView textView = (TextView) rootView
		// .findViewById(R.id.section_label);
		// textView.setText(Integer.toString(getArguments().getInt(
		// ARG_SECTION_NUMBER)));
		
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
		String url = Constants.BASE_URL + "account/count?categoryType=" + Constants.TYPE_OLD
				+ "&s=" + sp.getString(Constants.SEARCH_SEX, "") + "&uid=" + uid;
		Log.d(Constants.TAG, "count url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		
		AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg, new AsyncHttpClient.JSONObjectCallback() {
	    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONObject result1) {
				if(OldFragment.this != null && OldFragment.this.getActivity() != null)
				OldFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);		
					}
				});
			
	    	
		        if (e != null) {
		            e.printStackTrace();
		            return;
		        }
				final boolean hasNotification = !result1.isNull("n");
				JSONArray result;
				try {
					result = result1.getJSONArray("r");
					final HashMap<String, Integer> res = new HashMap<String, Integer>();
					for (int index = 0; index < result.length(); index++) {
						JSONObject jo = result.getJSONObject(index);
						res.put(jo.getString("id"), jo.getInt("count"));

					}
					
					if(OldFragment.this != null && OldFragment.this.getActivity() != null)
					OldFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							OldAdapter ia = (OldAdapter) gridView.getAdapter();
							Iterator<String> it = res.keySet().iterator();
							
							while(it.hasNext()) {
								String cid = it.next();
								ia.addCount(cid, res.get(cid));
							}
							ia.notifyDataSetChanged();
							if(hasNotification) {
								notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
							} else {
								notificationRL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));								
							}							
						}
					});					
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

	    	}
		});		
		
		return rootView;
	}
}
