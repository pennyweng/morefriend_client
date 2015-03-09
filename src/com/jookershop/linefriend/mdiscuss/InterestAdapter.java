package com.jookershop.linefriend.mdiscuss;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.friend.AllFriendFragment;
import com.jookershop.linefriend.friend.FriendFragment;
import com.jookershop.linefriend.subcategory.SubCategoryFragment;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class InterestAdapter extends ArrayAdapter<InterestItem> {
	    private Context mContext;
	    
	    public InterestAdapter(Context context, ArrayList<InterestItem> interestItems) {
	        super(context, R.layout.category_item, interestItems);
	        mContext = context;
	     }
	    
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	final InterestItem interestItem = getItem(position);
//	    	if (convertView == null) {
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
//	         }
	    	ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
//	    	iv.setImageResource(interestItem.getThumbId());
	    	MainActivity.imageLoader.displayImage("drawable://" + interestItem.getThumbId(), 
	    			iv, MainActivity.options, new SimpleImageLoadingListener(), null);	
	    	
	    	TextView title = (TextView) convertView.findViewById(R.id.title);
	    	title.setText(interestItem.getTitle());

	    	TextView countTv = (TextView) convertView.findViewById(R.id.counttv);
	    	countTv.setText("線友人數:" + interestItem.getCount() + "人");
	    	
	    	final String detailTitle = mContext.getString(R.string.title_section1) + "/" + interestItem.getTitle();
	    	convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					((FragmentActivity)mContext).getSupportFragmentManager()
//					.beginTransaction()
//					.replace(R.id.container,
//							AllFriendFragment.newInstance(Constants.TYPE_INTEREST, detailTitle, interestItem.getCategoryId()))
//							.addToBackStack("interest").commit();
					((FragmentActivity)mContext).getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container,
							SubCategoryFragment.newInstance(Constants.TYPE_INTEREST, interestItem.getCategoryId()))
							.addToBackStack("interest").commit();					
					
				}
			});
	    	
//	    	convertView.setBackgroundColor(Color.parseColor(Constants.INTEREST_COLOR));
	    	return convertView;
	    }
	 
}

