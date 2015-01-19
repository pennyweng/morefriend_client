package com.jookershop.linefriend.discuss;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.discuss.FriendDiscussAdapter.ViewHolder;
import com.jookershop.linefriend.friend.FriendFragment;
import com.jookershop.linefriend.util.AccountUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class ReplyItemAdapter extends ArrayAdapter<ReplyItem> {
	    private Context mContext;
	    
	    public ReplyItemAdapter(Context context, ArrayList<ReplyItem> interestItems) {
	        super(context, R.layout.category_item, interestItems);
	        mContext = context;
	     }
	    
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	final ReplyItem replyItem = getItem(position);
	    	
	    	if (convertView == null) {
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_discuss_item, parent, false);
	            ViewHolder viewHolder = new ViewHolder();
	            viewHolder.title = (TextView) convertView.findViewById(R.id.textView4);
	            viewHolder.lid = (TextView) convertView.findViewById(R.id.textView1);
	            viewHolder.userImg = (ImageView) convertView.findViewById(R.id.imageView1);
	            convertView.setTag(viewHolder);	        
	    	}
	    	
	    	ViewHolder holder = (ViewHolder) convertView.getTag();
	    	holder.title.setText(replyItem.getData());
	    	holder.lid.setText(replyItem.getLid());
//	    	UrlImageViewHelper.setUrlDrawable(holder.userImg, Constants.IMAGE_BASE_URL + "account/image?uid=" + replyItem.getUid());
	    	MainActivity.imageLoader.displayImage( 
	    			Constants.IMAGE_BASE_URL + "account/image?uid=" + replyItem.getUid(), 
	    			holder.userImg, MainActivity.emptyHeadOptions);

//	    	Bitmap bm =  MainActivity.imageLoader.loadImageSync(Constants.IMAGE_BASE_URL
//					+ "account/image?uid=" + interestItem.getUid());
//			idv.setImageBitmap(bm);			
	    	
	    	holder.userImg.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AccountUtil.showInfo(mContext, replyItem.getUid());
					
				}
			});	    	
	    	
	    	return convertView;
	    }
	    
	    
	    static class ViewHolder {
	        protected TextView title;
	        protected ImageView userImg;
	        protected TextView lid;
	      }	    
	 
}

