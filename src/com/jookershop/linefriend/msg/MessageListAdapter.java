package com.jookershop.linefriend.msg;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class MessageListAdapter extends ArrayAdapter<MessageItem> {
	    private Context mContext;
		DisplayImageOptions options;
		SimpleDateFormat sdf;
		
	    public MessageListAdapter(Context context, ArrayList<MessageItem> interestItems) {
	        super(context, R.layout.msg_chat_item, interestItems);
	        mContext = context;
	        sdf = new SimpleDateFormat("MM-dd hh:mm");
			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.emptyhead)
			.showImageForEmptyUri(R.drawable.emptyhead)
			.showImageOnFail(R.drawable.emptyhead)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.resetViewBeforeLoading(true)
			.build();	        
	     }
	    
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	final MessageItem msgItem = getItem(position);
	    	if (convertView == null) {

	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.msg_list_item, parent, false);
	    	}
	    	

	    	return convertView;
	    }
	    
	    static class ViewHolder {
	        protected TextView msg;
	        protected ImageView fromImg;
	        protected TextView fromLid;
	        protected ImageView toImg;
	        protected TextView toLid;
	        protected TextView ts;
	        protected Button bt;
	      }	    
	 
}

