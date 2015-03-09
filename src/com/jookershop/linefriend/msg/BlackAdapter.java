package com.jookershop.linefriend.msg;



import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend4.R.drawable;
import com.jookershop.linefriend4.R.id;
import com.jookershop.linefriend4.R.layout;
import com.jookershop.linefriend.account.AccountAdapter;
import com.jookershop.linefriend.account.AccountInfoAdapter;
import com.jookershop.linefriend.account.LikeItem;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.ImageHelper;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Bitmap;

public class BlackAdapter extends ArrayAdapter<FriendItem> {
	    private Context mContext;
//	    public String [] colors ={"#A3A948"};
	    public String mainColor;
	    private DisplayImageOptions options;
	    private String myLineId;
	    private String enableColor;
	    private BlackFragment tf;
	    
	    public BlackAdapter(Context context, ArrayList<FriendItem> interestItems, String mainColor, DisplayImageOptions options, String enableColor, BlackFragment tf) {
	        super(context, R.layout.category_item, interestItems);
	        mContext = context;
	        this.mainColor = mainColor;
	        this.options = options;
	        SharedPreferences sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
	        myLineId = sp.getString(Constants.LINE_STORE_KEY, "");
	        this.enableColor = enableColor;
	        this.tf = tf;
	     }
	    
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	final FriendItem interestItem = getItem(position);
	    	if (convertView == null) {
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.black_item, parent, false);
	            
	            ViewHolder viewHolder = new ViewHolder();
	            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
	            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView1);
	            viewHolder.lid = (TextView) convertView.findViewById(R.id.lid);
	            viewHolder.sendBt = (Button) convertView.findViewById(R.id.button1);
//	            viewHolder.addFriendBt = (Button) convertView.findViewById(R.id.Button03);
	            convertView.setTag(viewHolder);	        
	    	}
	    	
	    	ViewHolder holder = (ViewHolder) convertView.getTag();
	    	TextView title = holder.title;//(TextView) convertView.findViewById(R.id.title);
	    	final String lineId = interestItem.getLineId().replace("\n", "").replace(" ", "").trim();
	    	title.setText(lineId);
	    	
	    	ImageView iv = holder.iv;// (ImageView) convertView.findViewById(R.id.imageView1);
	    	Log.d(Constants.TAG, "get image:" + Constants.IMAGE_BASE_URL + "account/image?uid=" + interestItem.getUid());
//	    	UrlImageViewHelper.setUrlDrawable(iv, Constants.BASE_URL + "account/image?uid=" + interestItem.getUid());
	    	
	    	MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + interestItem.getUid(), 
	    			iv, options, new SimpleImageLoadingListener(), null);	    	
//	    	holder.sendBt.setBackgroundColor(Color.parseColor(enableColor));
//	    	holder.addFriendBt.setBackgroundColor(Color.parseColor(enableColor));
	    	
//			TextView delTv = (TextView) convertView.findViewById(R.id.textView1);
//			if (Constants.IS_SUPER || true) {
//				delTv.setVisibility(View.VISIBLE);
//				delTv.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						String myuid = URLEncoder.encode(AccountUtil.getUid(mContext));
//						String uid = URLEncoder.encode(interestItem.getUid());
////						uid : String, toUid: String
//						String url = Constants.BASE_URL + "trace/del?uid=" + myuid + "&toUid=" + uid ;
//
//						Log.d(Constants.TAG, "del trace  url " + url);
//						AsyncHttpGet get = new AsyncHttpGet(url);
//
//						AsyncHttpClient.getDefaultInstance().executeString(get,
//								new AsyncHttpClient.StringCallback() {
//
//									@Override
//									public void onCompleted(Exception e,
//											AsyncHttpResponse source, String result) {
//										if (e != null) {
//											Message.ShowMsgDialog(mContext,
//													"Opps....發生錯誤, 請稍後再試！");
//											e.printStackTrace();
//											return;
//										}
//										((Activity) mContext)
//												.runOnUiThread(new Runnable() {
//													public void run() {
//														try {
//															Thread.sleep(1500l);
//														} catch (InterruptedException e) {
//															// TODO Auto-generated catch block
//															e.printStackTrace();
//														}
//														tf.loadItmes(false);
//														Builder MyAlertDialog = new AlertDialog.Builder(
//																mContext);
//														MyAlertDialog
//																.setMessage("解除追蹤成功");
//														DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//															public void onClick(
//																	DialogInterface dialog,
//																	int which) {
//															}
//														};
//														MyAlertDialog
//																.setNeutralButton(
//																		"確認",
//																		OkClick);
//														MyAlertDialog.show();
//													}
//												});
//									}
//								});							
//
//
//					}
//				});
//			} else {
//				delTv.setVisibility(View.INVISIBLE);
//			}
			
	    	convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Message.showAccountInfo(mContext, interestItem);

				}
			});
	    	
	    	holder.sendBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String myuid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String uid = URLEncoder.encode(interestItem.getUid());
//					uid : String, toUid: String
					String url = Constants.BASE_URL + "block/del?uid=" + myuid + "&toUid=" + uid ;

					Log.d(Constants.TAG, "del block  url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeString(get,
							new AsyncHttpClient.StringCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source, String result) {
									if (e != null) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										e.printStackTrace();
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
													try {
														Thread.sleep(1500l);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													tf.loadItmes(false);
													Builder MyAlertDialog = new AlertDialog.Builder(
															mContext);
													MyAlertDialog
															.setMessage("解除封鎖");
													DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int which) {
														}
													};
													MyAlertDialog
															.setNeutralButton(
																	"確認",
																	OkClick);
													MyAlertDialog.show();
												}
											});
								}
							});						
					
//					final AlertDialog.Builder builder = new Builder(mContext);
//					View dialoglayout = LayoutInflater.from(getContext()).inflate(R.layout.send_msg, null);
//					final EditText mm = (EditText) dialoglayout.findViewById(R.id.editText1);
//			    	ImageView ii = (ImageView) dialoglayout.findViewById(R.id.imageView1);
//					MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + interestItem.getUid(), 
//			    			ii, options, new SimpleImageLoadingListener(), null);	
//					
//					builder.setView(dialoglayout);
//					final AlertDialog aa = builder.create();	
//					
//					Button cancel = (Button)dialoglayout.findViewById(R.id.Button03);
//					cancel.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							aa.dismiss();
//						}
//					});
//					
//					
//					Button bt = (Button)dialoglayout.findViewById(R.id.button1);
//					bt.setOnClickListener(new View.OnClickListener() {
//						
////						(fromUid : String, fromLid : String, toUid: String, toLid: String, msg : String)
//						@Override
//						public void onClick(View v) {
//							String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//							String fromLid = URLEncoder.encode(myLineId);
//							String toUid = URLEncoder.encode(interestItem.getUid());
//							String toLid = URLEncoder.encode(lineId);
//							String msg = URLEncoder.encode(mm.getText().toString());
//							
//							String url = Constants.BASE_URL + "new_msg/leave?fromUid=" + uid 
//									+ "&fromLid=" + fromLid  
//									+ "&toUid=" + toUid
//									+ "&toLid=" + toLid 
//									+ "&msg=" + msg;
//							Log.d(Constants.TAG, "send msg url " + url );
//							
//							AsyncHttpGet ahg = new AsyncHttpGet(url);
//							AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {						    @Override
//							    public void onCompleted(Exception e, AsyncHttpResponse response, String result1) {
//						    		aa.dismiss();    
//						    		if (e != null) {
//							            e.printStackTrace();
//							            Message.ShowMsgDialog(mContext, "無法留言, 請稍後再試！");
//							            return;
//							        }
//							        
//							        Message.ShowMsgDialog(mContext, "成功留言");
//						    	}
//							});							
//							
//							
//						}
//					});
//					
//					
//
//					aa.show();
				}
			});
	    	


	    	TextView lid = holder.lid;//(TextView) convertView.findViewById(R.id.lid);
			if (interestItem.getSex().equals("M")){
				lid.setText("男生");
				lid.setBackgroundColor(Color.parseColor("#AF003CBD"));
			}
			else if (interestItem.getSex().equals("F")){
				lid.setText("女生");
				lid.setBackgroundColor(Color.parseColor("#AFBD0013"));
			}
	    	
//	    	convertView.setBackgroundColor(Color.parseColor(mainColor));
	    	return convertView;
	    }
	 
	    static class ViewHolder {
	        protected TextView title;
	        protected ImageView iv;
	        protected  TextView lid;
	        protected Button sendBt;
//			protected Button addFriendBt;
	      }
	    
}

