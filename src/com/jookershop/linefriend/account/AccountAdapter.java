package com.jookershop.linefriend.account;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend4.R;


public class AccountAdapter extends BaseAdapter {
	    private Context mContext;
	    private String [] titles;
	    private Integer [] thumbnailIds;
	    private String color;
	    private String [] categoryIds;
//	    private ArrayList<String> clicked;
	    private String [] selectedIds;
	    private HashMap<String, LikeItem> likeItems;
	    
	    // Constructor
	    public AccountAdapter(Context c, String [] titles, Integer [] thumbnailIds, String color, String [] categoryIds, String [] selectedIds ){
	        mContext = c;
	        this.titles = titles;
	        this.thumbnailIds = thumbnailIds;
	        this.color = color;
	        this.categoryIds = categoryIds;
	        this.selectedIds = selectedIds;
//	        clicked = new ArrayList<String>();
	        likeItems = new HashMap<String, LikeItem>();
	        
	        for( int index = 0; index < categoryIds.length; index ++) {
	        	if(Arrays.asList(selectedIds).contains(categoryIds[index]))
	        		likeItems.put(categoryIds[index], new LikeItem(categoryIds[index], true, titles[index]));
	        	else 
	        		likeItems.put(categoryIds[index], new LikeItem(categoryIds[index], false, titles[index]));
	        }
	        
	    }
	 
	    @Override
	    public int getCount() {
	        return thumbnailIds.length;
	    }
	 
	    @Override
	    public Object getItem(int position) {
	    	return likeItems.values().toArray()[position];
	    }
	 
	    @Override
	    public long getItemId(int position) {
	    	return Long.parseLong(likeItems.values().toArray(new LikeItem[]{})[position].getCategoryId());
	    }
	 
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
          	final View row = inflater.inflate(R.layout.smallcategory_item, parent, false);
          	
          	TextView title = (TextView) row.findViewById(R.id.title);
          	title.setText(titles[position]);

          	
          	row.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ColorDrawable rowBackground = (ColorDrawable) row.getBackground();
					int rowColor = rowBackground.getColor();
					boolean add = true;
					
					if(rowColor == Color.parseColor(Constants.INTEREST_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.INTEREST_ENABLE_COLOR));
					} else if(rowColor == Color.parseColor(Constants.INTEREST_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.INTEREST_COLOR));
						add = false;
					} else if(rowColor == Color.parseColor(Constants.PLACE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.PLACE_ENABLE_COLOR));
//						clicked.add(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.PLACE_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.PLACE_COLOR));
						add = false;
//						clicked.remove(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.CAREER_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.CAREER_ENABLE_COLOR));
//						clicked.add(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.CAREER_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.CAREER_COLOR));
						add = false;
//						clicked.remove(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.OLD_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.OLD_ENABLE_COLOR));
//						clicked.add(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.OLD_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.OLD_COLOR));
						add = false;
//						clicked.remove(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.CONSTELLATION_COLOR));
						add = false;
//						clicked.remove(categoryIds[position]);
					}else if(rowColor == Color.parseColor(Constants.CONSTELLATION_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR));
//						clicked.add(categoryIds[position]);
					} else if(rowColor == Color.parseColor(Constants.MOTION_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.MOTION_ENABLE_COLOR));
//						clicked.add(categoryIds[position]);
					}else if(rowColor == Color.parseColor(Constants.MOTION_ENABLE_COLOR)) {
						row.setBackgroundColor(Color.parseColor(Constants.MOTION_COLOR));
						add = false;
					} 
					
					if(add) 
						likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
					else likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], false, titles[position]));
				}
			});    
          	
          	row.setBackgroundColor(Color.parseColor(color));
          	
          	
          	if(selectedIds !=null && Arrays.asList(selectedIds).contains(categoryIds[position])) {
	          	int rowColor = Color.parseColor(color);
				if(rowColor == Color.parseColor(Constants.INTEREST_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.INTEREST_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} else if(rowColor == Color.parseColor(Constants.PLACE_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.PLACE_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} else if(rowColor == Color.parseColor(Constants.CAREER_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.CAREER_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} else if(rowColor == Color.parseColor(Constants.OLD_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.OLD_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} else if(rowColor == Color.parseColor(Constants.CONSTELLATION_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.CONSTELLATION_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} else if(rowColor == Color.parseColor(Constants.MOTION_COLOR)) {
					row.setBackgroundColor(Color.parseColor(Constants.MOTION_ENABLE_COLOR));
					likeItems.put(categoryIds[position], new LikeItem(categoryIds[position], true, titles[position]));
//					if(!clicked.contains(categoryIds[position]))
//					clicked.add(categoryIds[position]);
				} 
          	}
        
	        return row;
	    }
	 
}

