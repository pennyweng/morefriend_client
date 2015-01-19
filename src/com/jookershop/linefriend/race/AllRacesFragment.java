package com.jookershop.linefriend.race;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.jookershop.linefriend.util.DisplayUtil;
import com.jookershop.linefriend3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class AllRacesFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AllRacesFragment newInstance() {
		AllRacesFragment fragment = new AllRacesFragment();
		return fragment;
	}

	public AllRacesFragment() {
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
		
		View rootView = inflater.inflate(R.layout.fragment_race_all, container,
				false);
		
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		adapter = new MyPagerAdapter(this.getChildFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

//		tabs.setIndicatorColor(Color.parseColor("#474747"));
		tabs.setTextSize(DisplayUtil.sp2px(this.getActivity(), 14));
		tabs.setTextColor(Color.parseColor("#037367"));
		tabs.setViewPager(pager);

		
		
//		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);
//
//		final AdView adView = (AdView) rootView.findViewById(R.id.adView);
//		AdUtil.showAD(this.getActivity(), adView);
//		
//		gridView = (ListView) rootView
//				.findViewById(R.id.grid_view);
//
//		ArrayList gi = new ArrayList<GameItem>();
//		gi.add(new GameItem());
//		gi.add(new GameItem());
//		gi.add(new GameItem());
//		gridView.setAdapter(new AllGameAdapter(this.getActivity(),
//				gi, options, this));
		
		
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
	
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "進行中的比賽", "已結束的比賽" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return ActiveRaceFragment.newInstance();
		}

	}	
}
