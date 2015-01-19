package com.jookershop.linefriend.account;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jookershop.linefriend3.R;


public class AccountInfoAdapter extends BaseAdapter {
	    private Context mContext;
	    private LikeItem [] likeItems;
	    private String mainColor;
	    
	    // Constructor
	    public AccountInfoAdapter(Context c, LikeItem [] likeItems){
	        mContext = c;
	        this.likeItems = likeItems;
	        
	    }
	 
	    @Override
	    public int getCount() {
	        return likeItems.length;
	    }
	 
	    @Override
	    public Object getItem(int position) {
	    	return likeItems[position];
	    }
	 
	    @Override
	    public long getItemId(int position) {
	    	return position;
	    }
	 
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
          	final View row = inflater.inflate(R.layout.smallcategory_item, parent, false);
          	
          	TextView title = (TextView) row.findViewById(R.id.title);
          	title.setText(likeItems[position].getName());
          	row.setBackgroundColor(Color.parseColor(likeItems[position].getColor()));
          
	        return row;
	    }
	 
}

