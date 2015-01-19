package com.jookershop.linefriend.subcategory;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.career.CareerFragment;
import com.jookershop.linefriend.constellation.ConstellationFragment;
import com.jookershop.linefriend.friend.FriendIDFragment;
import com.jookershop.linefriend.interest.InterestFragment;
import com.jookershop.linefriend.old.OldFragment;
import com.jookershop.linefriend.place.PlaceFragment;
import com.jookershop.linefriend.util.DisplayUtil;
import com.jookershop.linefriend3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class SubCategoryFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String CATEGORY_TYPE = "ctype";
	private static final String CATEGORY_ID = "cid";
	
	private ListView gridView;
	DisplayImageOptions options;
	private ProgressBar progressBar1;
	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private int categoryType;
	private String categoryId;
	private String goCategoryId;

	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static SubCategoryFragment newInstance(int ctype, String categoryId) {
		SubCategoryFragment fragment = new SubCategoryFragment();
		 Bundle args = new Bundle();
		 args.putInt(CATEGORY_TYPE, ctype);
		 args.putString(CATEGORY_ID, categoryId);
		 fragment.setArguments(args);		
		return fragment;
	}

	public SubCategoryFragment() {
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
		
		View rootView = inflater.inflate(R.layout.fragment_category_all, container,
				false);
		Bundle bd = this.getArguments();
		if(bd != null) {
			categoryType = bd.getInt(CATEGORY_TYPE);
			goCategoryId = bd.getString(CATEGORY_ID);
		}
		
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		adapter = new MyPagerAdapter(this.getChildFragmentManager());
		
		pager.setAdapter(adapter);
		pager.setCurrentItem(getPosition(categoryType, goCategoryId));

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setTextSize(DisplayUtil.sp2px(this.getActivity(), 15));
//		tabs.setTextColor(Color.parseColor("#828181"));
		tabs.setViewPager(pager);
	
		return rootView;
	}
	
	private int getPosition(int type, String value) {
		if(categoryType == Constants.TYPE_INTEREST) {
			for(int index = 0; index < Constants.INTEREST_IDS.length; index ++) {
				if(Constants.INTEREST_IDS[index] == value) return index;
			}
		} else if(categoryType == Constants.TYPE_PLACE) {
			for(int index = 0; index < Constants.PLACE_IDS.length; index ++) {
				if(Constants.PLACE_IDS[index] == value) return index;
			}
		} else if(categoryType == Constants.TYPE_CAREER) {
			for(int index = 0; index < Constants.CAREER_IDS.length; index ++) {
				if(Constants.CAREER_IDS[index] == value) return index;
			}
		} else if(categoryType == Constants.TYPE_OLD) {
			for(int index = 0; index < Constants.OLD_IDS.length; index ++) {
				if(Constants.OLD_IDS[index] == value) return index;
			}
		} else if(categoryType == Constants.TYPE_CONSTELLATION) {
			for(int index = 0; index < Constants.CONSTELLATION_IDS.length; index ++) {
				if(Constants.CONSTELLATION_IDS[index] == value) return index;
			}
		}	
		return 0;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private String[] TITLES;// = { "興趣分類", "地區分類", "職業分類", "年紀分類", "星座分類" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			ArrayList<String> titles = new ArrayList();
			if(categoryType == Constants.TYPE_INTEREST) {
				for(int index = 0; index < Constants.INTEREST_TITLES.length; index ++) {
					titles.add(Constants.INTEREST_TITLES[index]);
				}
			} else if(categoryType == Constants.TYPE_PLACE) {
				for(int index = 0; index < Constants.PLACE_TITLES.length; index ++) {
					titles.add(Constants.PLACE_TITLES[index]);
				}
			} else if(categoryType == Constants.TYPE_CAREER) {
				for(int index = 0; index < Constants.CAREER_TITLES.length; index ++) {
					titles.add(Constants.CAREER_TITLES[index]);
				}
			} else if(categoryType == Constants.TYPE_OLD) {
				for(int index = 0; index < Constants.OLD_TITLES.length; index ++) {
					titles.add(Constants.OLD_TITLES[index]);
				}
			} else if(categoryType == Constants.TYPE_CONSTELLATION) {
				for(int index = 0; index < Constants.CONSTELLATION_TITLES.length; index ++) {
					titles.add(Constants.CONSTELLATION_TITLES[index]);
				}
			}
			TITLES = titles.toArray(new String[]{});
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
			String color = "#A3A948";
			String enableColor = Constants.INTEREST_ENABLE_COLOR;
			
			if(categoryType == Constants.TYPE_INTEREST) {
				color = Constants.INTEREST_COLOR;
				enableColor = Constants.INTEREST_ENABLE_COLOR;
				categoryId = Constants.INTEREST_IDS[position];
			}
			else if(categoryType == Constants.TYPE_PLACE) {
				color = Constants.PLACE_COLOR;
				enableColor = Constants.PLACE_ENABLE_COLOR;
				categoryId = Constants.PLACE_IDS[position];
			}
			else if(categoryType == Constants.TYPE_CAREER) {
				color = Constants.CAREER_COLOR;
				enableColor = Constants.CAREER_ENABLE_COLOR;
				categoryId = Constants.CAREER_IDS[position];
			}
			else if(categoryType == Constants.TYPE_OLD) {
				color = Constants.OLD_COLOR;
				enableColor = Constants.OLD_ENABLE_COLOR;
				categoryId = Constants.OLD_IDS[position];
			}
			else if(categoryType == Constants.TYPE_CONSTELLATION) {
				color = Constants.CONSTELLATION_COLOR;
				enableColor = Constants.CONSTELLATION_ENABLE_COLOR;
				categoryId = Constants.CONSTELLATION_IDS[position];
			}
			
			return FriendIDFragment.newInstance(categoryType, categoryId, enableColor);
//			if(position == 0) {
//				return InterestFragment.newInstance(position + 1);
//		    } else if(position == 1){
//		    	return PlaceFragment.newInstance(position + 1);
//			} else if(position == 2) {
//				return CareerFragment.newInstance(position + 1);
//			} else if(position == 3){
//				return OldFragment.newInstance(position + 1);
//			} else {
//				return ConstellationFragment.newInstance(position + 1);
//				
//			}
			
		}

	}	
}
