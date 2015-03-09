package com.jookershop.linefriend.gift;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class FinishGiftFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private TextView tmsg;

	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FinishGiftFragment newInstance() {
		FinishGiftFragment fragment = new FinishGiftFragment();
		return fragment;
	}

	public FinishGiftFragment() {
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.emptyhead)
		.showImageForEmptyUri(R.drawable.noimage)
		.showImageOnFail(R.drawable.noimage)
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
		final SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		View rootView = inflater.inflate(R.layout.fragment_finish_gift, container,
				false);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		tmsg = (TextView) rootView.findViewById(R.id.textView2);
		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);
		
		gridView.setAdapter(new FinishGiftAdapter(this.getActivity(),
				new ArrayList<GiftItem>(), options));

		loadItmes(true);		
		return rootView;
	}

	public void loadItmes(final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));
//		if(Constants.IS_SUPER) uid = "50DE4E8F8F8476D79DA9C3CA264357F9B9B52E9F";
		String url = Constants.BASE_URL + "gift/finish/list?uid=" + uid;
		Log.d(Constants.TAG, "current finish gift url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
				if(FinishGiftFragment.this != null && FinishGiftFragment.this.getActivity()!= null)
					FinishGiftFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});
				
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		    	
//		        if(first && result.length() == 0) Message.ShowMsgDialog(ActiveGiftFragment.this.getActivity(), "目前沒有進行中的遊戲");
				final ArrayList<GiftItem> res = new ArrayList<GiftItem>();
				for (int index = 0; index < result.length(); index++) {
					try {

						JSONObject jo = result.getJSONObject(index);
						res.add(GiftItem.genPostItem(jo));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if(FinishGiftFragment.this != null && FinishGiftFragment.this.getActivity()!= null)
					FinishGiftFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						FinishGiftAdapter ia = (FinishGiftAdapter) gridView.getAdapter();
						for(int index = 0; index < res.size(); index ++)
						ia.add(res.get(index));
						if(res.size() == 0) tmsg.setVisibility(View.VISIBLE); 
						else tmsg.setVisibility(View.INVISIBLE); 
						ia.notifyDataSetChanged();
					}
				});
		    }
		});		
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	
	
}
