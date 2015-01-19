package com.jookershop.linefriend.discuss;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.EndlessScrollListener;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.interest.InterestAdapter;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class AllPostFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private GridView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;

	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AllPostFragment newInstance() {
		AllPostFragment fragment = new AllPostFragment();
		return fragment;
	}

	public AllPostFragment() {
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.emptyhead)
//		.showImageForEmptyUri(R.drawable.noimage)
//		.showImageOnFail(R.drawable.noimage)
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
		
		View rootView = inflater.inflate(R.layout.fragment_all_discuss, container,
				false);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new AllPostAdapter(this.getActivity(),
				new ArrayList<PostItem>(), options, this));
		
		
//		gridView.setOnScrollListener(new EndlessScrollListener() {
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
//						+ totalItemsCount);
//				loadItmes(page, false);
////				FriendAdapter ia = (FriendAdapter) gridView.getAdapter();
////				ia.addAll(getNewInterestItem());
//			}
//		});
		
		Button addBt = (Button) rootView.findViewById(R.id.Button02);
		addBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = URLEncoder.encode(AccountUtil.getUid(AllPostFragment.this.getActivity()));
				String lineId = URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
				Log.d(Constants.TAG, "uid:" + uid + ",lineId:" + lineId);
				

//				if(sp.contains(Constants.LINE_STORE_KEY)) {
						Intent a = new Intent();
						a.setClass(AllPostFragment.this.getActivity(), CreateDiscuss1Activity.class);
//						a.putExtra("mainColor", mainColor);
//						a.putExtra("title", title);
//						a.putExtra("categoryId", categoryId);
//						a.putExtra("currentType", currentType);
//						a.putExtra("subtitle", subtitle);
						
						AllPostFragment.this.getActivity().startActivity(a);
//				} else
//					Message.ShowMsgDialog(AllPostFragment.this.getActivity(), "麻煩先完成右上方的基本資料設定" );


			}
		});		
		return rootView;
	}

	public void loadItmes(int page, final boolean first) {
		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));		
		String url = Constants.BASE_URL + "post/searchall?uid=" + uid 
					+ "&page=" + page;
		Log.d(Constants.TAG, "get all post url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
		    	if(AllPostFragment.this != null && AllPostFragment.this.getActivity()!= null)
					AllPostFragment.this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							progressBar1.setVisibility(View.INVISIBLE);
						}
					});
		    	
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
		        if(first && result.length() == 0) Message.ShowMsgDialog(AllPostFragment.this.getActivity(), "討論區目前沒資料!!!");
				final ArrayList<PostItem> res = new ArrayList<PostItem>();
				for (int index = 0; index < result.length(); index++) {
					try {
						JSONObject jo = result.getJSONObject(index);
						res.add(PostItem.genPostItem(jo));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				
				if(AllPostFragment.this != null && AllPostFragment.this.getActivity()!= null)
				AllPostFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						AllPostAdapter ia = (AllPostAdapter) gridView.getAdapter();
						if(first) ia.clear();
						for(int index = 0; index < res.size(); index ++)
						ia.add(res.get(index));
//						ia.addAll(res);
						ia.notifyDataSetChanged();
					}
				});
		    }
		});		
	}

	@Override
	public void onResume() {
		super.onResume();
		loadItmes(0, true);
		resetGrid();
	}
	
	
	public void resetGrid() {
		gridView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
						+ totalItemsCount);
				loadItmes(page, false);
			}
		});		
	}

	public void refresh() {
		loadItmes(0, true);
		resetGrid();		
	}

	
	public void refresh(String pid) {
		AllPostAdapter ia = (AllPostAdapter) gridView.getAdapter();
		for(int index = 0; index < ia.getCount(); index ++ ){
			PostItem pi = ia.getItem(index);
			if(pi.getPid().equals(pid)) {
				pi.setReplyCount(pi.getReplyCount() + 1);
			}
		}
		ia.notifyDataSetChanged();
	}
}
