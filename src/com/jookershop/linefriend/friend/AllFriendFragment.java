package com.jookershop.linefriend.friend;

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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.EndlessScrollListener;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.category.CategoryFragment.MyPagerAdapter;
import com.jookershop.linefriend.constellation.ConstellationFragment;
import com.jookershop.linefriend.discuss.FriendDiscussFragment;
import com.jookershop.linefriend.interest.InterestAdapter;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.DisplayUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class AllFriendFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";

	private String mainColor;
	private int currentType = Constants.TYPE_INTEREST;
	private String title;
	private GridView gridView;
	private String categoryId;
	DisplayImageOptions options;	
	private RelativeLayout notificationRL;
	private FragmentTabHost tabHost;
	private LinearLayout lla;
	private String enableColor;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private String subTitle;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AllFriendFragment newInstance(int type, String title, String categoryId) {
		String color = "#A3A948";
		String enableColor = Constants.INTEREST_ENABLE_COLOR;
		
		if(type == Constants.TYPE_INTEREST) {
			color = Constants.INTEREST_COLOR;
			enableColor = Constants.INTEREST_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_PLACE) {
			color = Constants.PLACE_COLOR;
			enableColor = Constants.PLACE_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_CAREER) {
			color = Constants.CAREER_COLOR;
			enableColor = Constants.CAREER_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_OLD) {
			color = Constants.OLD_COLOR;
			enableColor = Constants.OLD_ENABLE_COLOR;
		}
		else if(type == Constants.TYPE_CONSTELLATION) {
			color = Constants.CONSTELLATION_COLOR;
			enableColor = Constants.CONSTELLATION_ENABLE_COLOR;
		}
		
		AllFriendFragment fragment = new AllFriendFragment();//type, color, title, categoryId, enableColor
		 Bundle args = new Bundle();
		 args.putInt("type", type);
		 args.putString("color", color);
		 args.putString("title", title);
		 args.putString("categoryId", categoryId);
		 args.putString("enableColor", enableColor);
		 fragment.setArguments(args);
		return fragment;
	}

	public AllFriendFragment() {
	}
	
	public AllFriendFragment(int type, String color, String title, String categoryId, String enableColor) {
		mainColor= color;
		currentType = type;
		this.title = title;
		this.categoryId = categoryId;
		this.enableColor = enableColor;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
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
		
		final SharedPreferences sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		View rootView = inflater.inflate(R.layout.fragment_category_all, container,
				false);
		Bundle bundle = this.getArguments();
		if(bundle != null) {
			currentType = bundle.getInt("type");
			mainColor = bundle.getString("color");
			title = bundle.getString("title");
			categoryId = bundle.getString("categoryId");
			enableColor = bundle.getString("enableColor");			
		}
	
		
//		subTitle = title.substring(title.indexOf("/")+1);
//		
//		Bundle bn = new Bundle();
//		bn.putInt("currentType", currentType);
//		bn.putString("categoryId", categoryId);
//		bn.putString("mainColor", mainColor);
//		bn.putString("title", title);
//		bn.putString("subtitle", subTitle + "的討論區");
//		bn.putString("enableColor", enableColor);
		
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
//		
//		tabs.addTab(tabs.newTabSpec(subTitle + "的LINE友").setIndicator(subTitle + "的LINE友"), FriendIDFragment.class, bn );
//		tabs.addTab(tabs.newTabSpec(subTitle + "的討論區").setIndicator(subTitle + "的討論區"), FriendDiscussFragment.class, bn );
		
		subTitle = title.substring(title.indexOf("/")+1);
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		String [] tt = new String[1];
		tt[0] = subTitle + "的LINE友";
//		tt[1] = subTitle + "的討論區";
		adapter = new MyPagerAdapter(this.getChildFragmentManager(), tt );
		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setTextSize(DisplayUtil.sp2px(this.getActivity(), 14));
//		tabs.setTextColor(Color.parseColor("#037367"));
		tabs.setViewPager(pager);
	
		return rootView;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private String[] TITLES = null;

		public MyPagerAdapter(FragmentManager fm, String [] titles) {
			super(fm);
			this.TITLES = titles;
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
			if(position == 0) {
				return FriendIDFragment.newInstance(currentType, title, categoryId, enableColor);
		    } else {
		    	
		    	return FriendDiscussFragment.newInstance(currentType, subTitle, categoryId);
			}
			
		}

	}	
}
