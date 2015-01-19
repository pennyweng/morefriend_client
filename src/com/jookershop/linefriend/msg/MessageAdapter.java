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


public class MessageAdapter extends ArrayAdapter<MessageItem> {
	    private Context mContext;
		DisplayImageOptions options;
		SimpleDateFormat sdf;
		
	    public MessageAdapter(Context context, ArrayList<MessageItem> interestItems) {
	        super(context, R.layout.msg_item, interestItems);
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
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.msg_item, parent, false);
	            
	            ViewHolder viewHolder = new ViewHolder();
	            viewHolder.msg = (TextView) convertView.findViewById(R.id.textView1);
	            viewHolder.fromLid = (TextView) convertView.findViewById(R.id.textView2);
	            viewHolder.fromImg = (ImageView) convertView.findViewById(R.id.imageView1);
	            viewHolder.toLid = (TextView) convertView.findViewById(R.id.textView3);
	            viewHolder.toImg = (ImageView) convertView.findViewById(R.id.imageView2);
	            viewHolder.ts = (TextView) convertView.findViewById(R.id.textView4);
	            viewHolder.bt = (Button) convertView.findViewById(R.id.button1);

	            convertView.setTag(viewHolder);	        
	    	}
	    	
	    	ViewHolder holder = (ViewHolder) convertView.getTag();	    	
	    	holder.msg.setText(msgItem.getMsg());

	    	holder.fromLid.setText(msgItem.getFromLid());
	    	MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + msgItem.getFromId(), 
	    			holder.fromImg, options, new SimpleImageLoadingListener(), null);	
	    	holder.fromImg.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AccountUtil.showInfo(mContext, msgItem.getFromId());
				}
			});
	    	
	    	holder.toLid.setText(msgItem.getToLid());
	    	MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + msgItem.getToId(), 
	    			holder.toImg, options, new SimpleImageLoadingListener(), null);	    	
	    	
	    	holder.toImg.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AccountUtil.showInfo(mContext, msgItem.getToId());
				}
			});	    	
	    	
	    	
	    	holder.ts.setText(sdf.format(new Date(msgItem.getTs())));
	    	
	    	if(msgItem.getFromId().equals(AccountUtil.getUid(mContext))) {
	    		holder.bt.setVisibility(View.INVISIBLE);
	    	} else {
	    		holder.bt.setVisibility(View.VISIBLE);
	    		holder.bt.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(getContext()).inflate(R.layout.send_msg, null);
						final EditText mm = (EditText) dialoglayout.findViewById(R.id.editText1);
				    	ImageView ii = (ImageView) dialoglayout.findViewById(R.id.imageView1);
						MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + msgItem.getFromId(), 
				    			ii, options, new SimpleImageLoadingListener(), null);	
						
						builder.setView(dialoglayout);
						final AlertDialog aa = builder.create();					
						Button cancel = (Button)dialoglayout.findViewById(R.id.Button03);
						cancel.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								aa.dismiss();
							}
						});						
						
						final Button bt = (Button)dialoglayout.findViewById(R.id.button1);
						bt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								bt.setEnabled(false);
								String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
								String fromLid = URLEncoder.encode(msgItem.getToLid());
								String toUid = URLEncoder.encode(msgItem.getFromId());
								String toLid = URLEncoder.encode(msgItem.getFromLid());
								String msg = URLEncoder.encode(mm.getText().toString());
								
								String url = Constants.BASE_URL + "new_msg/leave?fromUid=" + uid 
										+ "&fromLid=" + fromLid  
										+ "&toUid=" + toUid
										+ "&toLid=" + toLid 
										+ "&msg=" + msg;
								Log.d(Constants.TAG, "send msg url " + url );
								
								AsyncHttpGet ahg = new AsyncHttpGet(url);
								AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {						    @Override
								    public void onCompleted(final Exception e, AsyncHttpResponse response, String result1) {
									  	((Activity)mContext).runOnUiThread( new Runnable() {
											public void run() {
												
												bt.setEnabled(true);
												aa.dismiss();    
									    		if (e != null) {
										            e.printStackTrace();
										            Message.ShowMsgDialog(mContext, "無法留言, 請稍後再試！");
										            return;
										        }
									    		
												Builder MyAlertDialog = new AlertDialog.Builder(mContext);
												MyAlertDialog.setMessage("留言成功");
												DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
//														onBackPressed();
//														a.dismiss();
														((MessageActivity)mContext).refresh(false);
													}
												};
												MyAlertDialog.setNeutralButton("確認", OkClick);
												MyAlertDialog.show();
											}
										});							    		
							    	}
								});							
								
								
							}
						});
						
						

						aa.show();
						
					}
				});
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

