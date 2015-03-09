package com.jookershop.linefriend.discuss;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.EndlessScrollListener;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.account.AccountInfoAdapter;
import com.jookershop.linefriend.account.LikeItem;
import com.jookershop.linefriend.friend.FriendAdapter;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import eu.janmuller.android.simplecropimage.CropImage;

public class NewDiscussFragment  extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private String mainColor;
	private int currentType = Constants.TYPE_INTEREST;
	private String title;
	private GridView gridView;
	private String categoryId;
	DisplayImageOptions options;	
	private String subtitle;

	private ImageView mImageView;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo1.jpg";
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	private File mFileTemp;
	private SharedPreferences sp;
	private ProgressBar progressBar1;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static NewDiscussFragment newInstance(int type, String title, String categoryId) {
		String color = "#A3A948";
		
		if(type == Constants.TYPE_INTEREST) color = Constants.INTEREST_COLOR;
		else if(type == Constants.TYPE_PLACE) color = Constants.PLACE_COLOR;
		else if(type == Constants.TYPE_CAREER) color = Constants.CAREER_COLOR;
		else if(type == Constants.TYPE_OLD) color = Constants.OLD_COLOR;
//		else if(type == Constants) color = Constants.OLD_COLOR;
		
		NewDiscussFragment fragment = new NewDiscussFragment();
		 Bundle args = new Bundle();
		 args.putInt("currentType", type);
		 args.putString("title", title);
		 args.putString("categoryId", categoryId);
		 fragment.setArguments(args);
		return fragment;
	}

	public NewDiscussFragment() {
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.emptyhead)
//		.showImageForEmptyUri(R.drawable.emptyhead)
//		.showImageOnFail(R.drawable.emptyhead)
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
		if(this.getActivity() != null)
//		this.getActivity().getActionBar().hide();
		sp = this.getActivity().getSharedPreferences("linefriend", Context.MODE_APPEND);
		
		View rootView = inflater.inflate(R.layout.fragment_friend_discuss, container,
				false);
		progressBar1 = (ProgressBar)rootView.findViewById(R.id.progressBar1);

		Bundle bData = this.getArguments();
		if(bData != null) {		
			currentType = bData.getInt("currentType");
			categoryId = bData.getString("categoryId");
			title = bData.getString("title");
		}
		
		
		
		Button addBt = (Button) rootView.findViewById(R.id.Button02);
		addBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = URLEncoder.encode(AccountUtil.getUid(NewDiscussFragment.this.getActivity()));
				String lineId = URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
				Log.d(Constants.TAG, "uid:" + uid + ",lineId:" + lineId);
				
//				sp.edit().putString(Constants.LINE_STORE_KEY, "ahappychat").commit();
//				if(sp.contains(Constants.LINE_STORE_KEY)) {
						Intent a = new Intent();
						a.setClass(NewDiscussFragment.this.getActivity(), CreateNewDiscussActivity.class);
						a.putExtra("title", title);
						a.putExtra("categoryId", categoryId);
						a.putExtra("currentType", currentType);
						
						NewDiscussFragment.this.getActivity().startActivity(a);
//				} else
//					Message.ShowMsgDialog(FriendDiscussFragment.this.getActivity(), "麻煩先完成右上方的基本資料設定" );


			}
		});
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(NewDiscussFragment.this.getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}
		
		String color = "#A3A948";
//		if(currentType == Constants.TYPE_INTEREST) {
//			color = Constants.INTEREST_ENABLE_COLOR;
//			addBt.setBackgroundColor(Color.parseColor(Constants.INTEREST_HEADER_COLOR));
//			addBt.setTextColor(Color.parseColor(Constants.INTEREST_ENABLE_COLOR));
//		}
//		else if(currentType == Constants.TYPE_PLACE) {
//			color = Constants.PLACE_ENABLE_COLOR;
//			addBt.setTextColor(Color.parseColor(Constants.PLACE_ENABLE_COLOR));
//			addBt.setBackgroundColor(Color.parseColor(Constants.PLACE_HEADER_COLOR));
//		}
//		else if(currentType == Constants.TYPE_CAREER) {
//			color = Constants.CAREER_ENABLE_COLOR;
//			addBt.setTextColor(Color.parseColor(Constants.CAREER_ENABLE_COLOR));
//			addBt.setBackgroundColor(Color.parseColor(Constants.CAREER_HEADER_COLOR));
//		}
//		else if(currentType == Constants.TYPE_OLD) {
//			color = Constants.OLD_ENABLE_COLOR;	
//			addBt.setTextColor(Color.parseColor(Constants.OLD_ENABLE_COLOR));
//			addBt.setBackgroundColor(Color.parseColor(Constants.OLD_HEADER_COLOR));
//		}
//		else if(currentType == Constants.TYPE_CONSTELLATION) {
//			color = Constants.CONSTELLATION_ENABLE_COLOR;	
//			addBt.setTextColor(Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR));
//			addBt.setBackgroundColor(Color.parseColor(Constants.CONSTELLATION_HEADER_COLOR));
//		}
		
		gridView = (GridView) rootView
				.findViewById(R.id.grid_view);

		gridView.setAdapter(new NewDiscussAdapter(this.getActivity(),
				new ArrayList<PostItem>(), mainColor, options, color, this, currentType, categoryId));
		
		final String sex = sp.getString(Constants.SEARCH_SEX, "");
		
		gridView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
						+ totalItemsCount);
				loadItmes(currentType + "", categoryId, page, sex, false, false);
//				FriendAdapter ia = (FriendAdapter) gridView.getAdapter();
//				ia.addAll(getNewInterestItem());
			}
		});
		return rootView;
	}

	public void loadItmes(String categoryType, String categoryId, int page, String sex, final boolean first, final boolean isInit) {
		String url = Constants.BASE_URL + "post/search?categoryType=" + categoryType 
					+ "&categoryId=" + categoryId 
					+ "&page=" + page;
		Log.d(Constants.TAG, "search post url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, JSONArray result) {
				if(NewDiscussFragment.this != null && NewDiscussFragment.this.getActivity()!= null)
					NewDiscussFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});
				
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
//		        if(first && result.length() == 0) Message.ShowMsgDialog(FriendDiscussFragment.this.getActivity(), "目前沒有人符合條件!!!");
				final ArrayList<PostItem> res = new ArrayList<PostItem>();
				for (int index = 0; index < result.length(); index++) {
					try {
						JSONObject jo = result.getJSONObject(index);
						res.add(PostItem.genPostItem(jo));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if(NewDiscussFragment.this != null && NewDiscussFragment.this.getActivity()!= null)
					NewDiscussFragment.this.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						NewDiscussAdapter ia = (NewDiscussAdapter) gridView.getAdapter();
						if(isInit) ia.clear();
//						ia.addAll(res);
						for(int index = 0; index < res.size(); index ++)
						ia.add(res.get(index));
						ia.notifyDataSetChanged();
					}
				});
		    }
		});		
	}

	@Override
	public void onResume() {
		super.onResume();
		loadItmes(currentType + "", categoryId, 0, "", true, true);
		resetGrid();
	}

	public void resetGrid() {
		gridView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("loadmore", "page:" + page + ",totalItemsCount:"
						+ totalItemsCount);
				loadItmes(currentType + "", categoryId, page, "", false, false);
			}
		});		
	}

	public void refresh() {
		loadItmes(currentType + "", categoryId, 0, "", true, true);
		resetGrid();		
	}	
	
	public void refresh(String pid) {
		FriendDiscussAdapter ia = (FriendDiscussAdapter) gridView.getAdapter();
		for(int index = 0; index < ia.getCount(); index ++ ){
			PostItem pi = ia.getItem(index);
			if(pi.getPid().equals(pid)) {
				pi.setReplyCount(pi.getReplyCount() + 1);
			}
		}
		ia.notifyDataSetChanged();
	}
	
	

	
}
