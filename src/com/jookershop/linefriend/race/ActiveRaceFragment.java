package com.jookershop.linefriend.race;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend4.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class ActiveRaceFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;

	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ActiveRaceFragment newInstance() {
		ActiveRaceFragment fragment = new ActiveRaceFragment();
		return fragment;
	}

	public ActiveRaceFragment() {
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
		
		View rootView = inflater.inflate(R.layout.fragment_ativity_race, container,
				false);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
		AdUtil.showAD(this.getActivity(), adView);
		
		gridView = (ListView) rootView
				.findViewById(R.id.grid_view);

		ArrayList gi = new ArrayList<GameItem>();
		gi.add(new GameItem());
		gi.add(new GameItem());
		gi.add(new GameItem());
		gridView.setAdapter(new AllRaceAdapter(this.getActivity(),
				gi, options, this));
		
		
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
		
//		Button addBt = (Button) rootView.findViewById(R.id.Button02);
//		addBt.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				String uid = URLEncoder.encode(AccountUtil.getUid(AllPttFragment.this.getActivity()));
//				String lineId = URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
//				Log.d(Constants.TAG, "uid:" + uid + ",lineId:" + lineId);
//				
//
////				if(sp.contains(Constants.LINE_STORE_KEY)) {
//						Intent a = new Intent();
//						a.setClass(AllPttFragment.this.getActivity(), CreateDiscuss1Activity.class);
////						a.putExtra("mainColor", mainColor);
////						a.putExtra("title", title);
////						a.putExtra("categoryId", categoryId);
////						a.putExtra("currentType", currentType);
////						a.putExtra("subtitle", subtitle);
//						
//						AllPttFragment.this.getActivity().startActivity(a);
////				} else
////					Message.ShowMsgDialog(AllPostFragment.this.getActivity(), "麻煩先完成右上方的基本資料設定" );
//
//
//			}
//		});		
		return rootView;
	}

//	public void loadItmes(int page, final boolean first) {
//		String uid = URLEncoder.encode(AccountUtil.getUid(this.getActivity()));		
//		String url = Constants.BASE_URL + "post/searchall?uid=" + uid 
//					+ "&page=" + page;
//		Log.d(Constants.TAG, "get all post url " + url );
//		AsyncHttpGet ahg = new AsyncHttpGet(url);
//		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
//		    @Override
//		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
//		    	if(AllGameFragment.this != null && AllGameFragment.this.getActivity()!= null)
//					AllGameFragment.this.getActivity().runOnUiThread(new Runnable() {
//						public void run() {
//							progressBar1.setVisibility(View.INVISIBLE);
//						}
//					});
//		    	
//		    	if (e != null) {
//		            e.printStackTrace();
//		            return;
//		        }
//		        if(first && result.length() == 0) Message.ShowMsgDialog(AllGameFragment.this.getActivity(), "討論區目前沒資料!!!");
//				final ArrayList<GameItem> res = new ArrayList<GameItem>();
//				for (int index = 0; index < result.length(); index++) {
//					try {
//						JSONObject jo = result.getJSONObject(index);
//						res.add(GameItem.genPostItem(jo));
//					} catch (JSONException e1) {
//						e1.printStackTrace();
//					}
//				}
//				
//				if(AllGameFragment.this != null && AllGameFragment.this.getActivity()!= null)
//				AllGameFragment.this.getActivity().runOnUiThread(new Runnable() {
//					public void run() {
//						AllGameAdapter ia = (AllGameAdapter) gridView.getAdapter();
//						if(first) ia.clear();
//						ia.addAll(res);
//						ia.notifyDataSetChanged();
//					}
//				});
//		    }
//		});		
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		loadItmes(0, true);
//		resetGrid();
//	}
//	
//	
//	public void resetGrid() {
//		gridView.setOnScrollListener(new EndlessScrollListener() {
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
//						+ totalItemsCount);
//				loadItmes(page, false);
//			}
//		});		
//	}
//
//	public void refresh() {
//		loadItmes(0, true);
//		resetGrid();		
//	}
//
//	
//	public void refresh(String pid) {
//		AllGameAdapter ia = (AllGameAdapter) gridView.getAdapter();
//		for(int index = 0; index < ia.getCount(); index ++ ){
//			GameItem pi = ia.getItem(index);
//			if(pi.getPid().equals(pid)) {
//				pi.setReplyCount(pi.getReplyCount() + 1);
//			}
//		}
//		ia.notifyDataSetChanged();
//	}
}
