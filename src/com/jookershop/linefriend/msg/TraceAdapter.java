package com.jookershop.linefriend.msg;



import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend3.R.drawable;
import com.jookershop.linefriend3.R.id;
import com.jookershop.linefriend3.R.layout;
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

public class TraceAdapter extends ArrayAdapter<FriendItem> {
	    private Context mContext;
//	    public String [] colors ={"#A3A948"};
	    public String mainColor;
	    private DisplayImageOptions options;
	    private String myLineId;
	    private String enableColor;
	    private TraceFragment tf;
	    
	    public TraceAdapter(Context context, ArrayList<FriendItem> interestItems, String mainColor, DisplayImageOptions options, String enableColor, TraceFragment tf) {
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
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trace_item, parent, false);
	            
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
	    	
			TextView delTv = (TextView) convertView.findViewById(R.id.textView1);
			if (Constants.IS_SUPER || true) {
				delTv.setVisibility(View.VISIBLE);
				delTv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String myuid = URLEncoder.encode(AccountUtil.getUid(mContext));
						String uid = URLEncoder.encode(interestItem.getUid());
//						uid : String, toUid: String
						String url = Constants.BASE_URL + "trace/del?uid=" + myuid + "&toUid=" + uid ;

						Log.d(Constants.TAG, "del trace  url " + url);
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
																.setMessage("解除追蹤成功");
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


					}
				});
			} else {
				delTv.setVisibility(View.INVISIBLE);
			}
			
	    	convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Message.showAccountInfo(mContext, interestItem);
//					final AlertDialog.Builder builder = new Builder(mContext);
//					View dialoglayout = LayoutInflater.from(getContext()).inflate(R.layout.account_info, null);
//						
//					
//					ImageView idv = (ImageView) dialoglayout.findViewById(R.id.imageView1);
//			    	MainActivity.imageLoader.displayImage(Constants.BASE_URL + "account/image?uid=" + interestItem.getUid(), 
//			    			idv, options, new SimpleImageLoadingListener(), null);
//			    	
//					ArrayList<LikeItem> ii = new ArrayList<LikeItem>();
//					
//					if(interestItem.getInterestIds() != null && interestItem.getInterestIds().length() > 0) {
//						String [] iis = interestItem.getInterestIds().split(","); 
//						for(int index = 0; index < iis.length; index ++){
//							ii.add(new LikeItem(iis[index], true, Constants.INTEREST_MAP.get(iis[index]), Constants.INTEREST_COLOR));
//						}
//					}
//					
//					if(interestItem.getPlaceIds() != null && interestItem.getPlaceIds().length() > 0) {
//						String [] iis1 = interestItem.getPlaceIds().split(",");
//						for(int index = 0; index < iis1.length; index ++){
//							ii.add(new LikeItem(iis1[index], true, Constants.PLACE_MAP.get(iis1[index]), Constants.PLACE_COLOR));
//						}
//					}
//					
//					if(interestItem.getCareerIds() != null && interestItem.getCareerIds().length() > 0) {
//						String [] iis2 = interestItem.getCareerIds().split(",");
//						for(int index = 0; index < iis2.length; index ++){
//							ii.add(new LikeItem(iis2[index], true, Constants.CAREER_MAP.get(iis2[index]), Constants.CAREER_COLOR));
//						}					
//					}
//					
//					if(interestItem.getOldIds() != null && interestItem.getOldIds().length() > 0) {
//						String [] iis3 = interestItem.getOldIds().split(",");
//						for(int index = 0; index < iis3.length; index ++){
//							ii.add(new LikeItem(iis3[index], true, Constants.OLD_MAP.get(iis3[index]), Constants.OLD_COLOR));
//						}					
//					}
//
//					if(interestItem.getConstellationIds() != null && interestItem.getConstellationIds().length() > 0) {
//						String [] iis4 = interestItem.getConstellationIds().split(",");
//						for(int index = 0; index < iis4.length; index ++){
//							ii.add(new LikeItem(iis4[index], true, Constants.CONSTELLATION_MAP.get(iis4[index]), Constants.CONSTELLATION_COLOR));
//						}					
//					}					
//					
//					GridView interestGV = (GridView) dialoglayout.findViewById(R.id.gridView1);
//					interestGV.setAdapter(new AccountInfoAdapter(mContext, ii.toArray(new LikeItem[ii.size()])));
//
//					TextView tv1 = (TextView )dialoglayout.findViewById(R.id.TextView01);
//					tv1.setText("大家好！我的LINE ID是" + lineId +"。 以下是我的個人基本資料與興趣");
//
//					builder.setView(dialoglayout);
//					final AlertDialog a = builder.create();
//					
//					Button exitBt = (Button) dialoglayout.findViewById(R.id.Button03);
//					exitBt.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							a.dismiss();
//						}
//					});
//
//					
//					Button bt = (Button)dialoglayout.findViewById(R.id.Button04);
//					bt.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//						
//							final AlertDialog.Builder builder = new Builder(mContext);
//							View dialoglayout = LayoutInflater.from(getContext()).inflate(
//									R.layout.send_msg, null);
//							final EditText mm = (EditText) dialoglayout
//									.findViewById(R.id.editText1);
//							ImageView ii = (ImageView) dialoglayout
//									.findViewById(R.id.imageView1);
//							MainActivity.imageLoader.displayImage(Constants.BASE_URL
//									+ "account/image?uid=" + interestItem.getUid(), ii,
//									options, new SimpleImageLoadingListener(), null);
//
//							builder.setView(dialoglayout);
//							final AlertDialog aa = builder.create();
//
//							Button cancel = (Button) dialoglayout
//									.findViewById(R.id.Button03);
//							cancel.setOnClickListener(new View.OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									aa.dismiss();
//								}
//							});
//
//							final Button bt = (Button) dialoglayout
//									.findViewById(R.id.button1);
//							bt.setOnClickListener(new View.OnClickListener() {
//
//								// (fromUid : String, fromLid : String, toUid: String,
//								// toLid: String, msg : String)
//								@Override
//								public void onClick(View v) {
//									bt.setEnabled(false);
//									String uid = URLEncoder.encode(AccountUtil
//											.getUid(mContext));
//									String fromLid = URLEncoder.encode(myLineId);
//									String toUid = URLEncoder.encode(interestItem.getUid());
//									String toLid = URLEncoder.encode(lineId);
//									String msg = URLEncoder.encode(mm.getText().toString());
//
//									String url = Constants.BASE_URL + "new_msg/leave?fromUid="
//											+ uid + "&fromLid=" + fromLid + "&toUid="
//											+ toUid + "&toLid=" + toLid + "&msg=" + msg;
//									Log.d(Constants.TAG, "send msg url " + url);
//
//									AsyncHttpGet ahg = new AsyncHttpGet(url);
//									AsyncHttpClient.getDefaultInstance().executeString(ahg,
//											new AsyncHttpClient.StringCallback() {
//												@Override
//												public void onCompleted(Exception e,
//														AsyncHttpResponse response,
//														String result1) {
//													((Activity) mContext)
//															.runOnUiThread(new Runnable() {
//																public void run() {
//																	bt.setEnabled(true);
//																}
//															});
//													aa.dismiss();
//													if (e != null) {
//														e.printStackTrace();
//														Message.ShowMsgDialog(mContext,
//																"無法留言, 請稍後再試！");
//														return;
//													}
//
//													Message.ShowMsgDialog(mContext, "成功留言");
//												}
//											});
//
//								}
//							});
//
//							aa.show();
//						}
//					});
//
//					
//					Button removeBt = (Button) dialoglayout.findViewById(R.id.Button02);
//					removeBt.setText("移除追蹤");
//					removeBt.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							String myuid = URLEncoder.encode(AccountUtil.getUid(mContext));
//							String uid = URLEncoder.encode(interestItem.getUid());
////							uid : String, toUid: String
//							String url = Constants.BASE_URL + "trace/del?uid=" + myuid + "&toUid=" + uid ;
//
//							Log.d(Constants.TAG, "del trace  url " + url);
//							AsyncHttpGet get = new AsyncHttpGet(url);
//
//							AsyncHttpClient.getDefaultInstance().executeString(get,
//									new AsyncHttpClient.StringCallback() {
//
//										@Override
//										public void onCompleted(Exception e,
//												AsyncHttpResponse source, String result) {
//											if (e != null) {
//												Message.ShowMsgDialog(mContext,
//														"Opps....發生錯誤, 請稍後再試！");
//												e.printStackTrace();
//												return;
//											}
//											((Activity) mContext)
//													.runOnUiThread(new Runnable() {
//														public void run() {
//															Builder MyAlertDialog = new AlertDialog.Builder(
//																	mContext);
//															MyAlertDialog
//																	.setMessage("移除成功");
//															DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//																public void onClick(
//																		DialogInterface dialog,
//																		int which) {
//																}
//															};
//															MyAlertDialog
//																	.setNeutralButton(
//																			"確認",
//																			OkClick);
//															MyAlertDialog.show();
//														}
//													});
//										}
//									});							
//							a.dismiss();
//						}
//					});
//			
//					a.show();
					
				}
			});
	    	
	    	holder.sendBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final AlertDialog.Builder builder = new Builder(mContext);
					View dialoglayout = LayoutInflater.from(getContext()).inflate(R.layout.send_msg, null);
					final EditText mm = (EditText) dialoglayout.findViewById(R.id.editText1);
			    	ImageView ii = (ImageView) dialoglayout.findViewById(R.id.imageView1);
					MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + interestItem.getUid(), 
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
					
					
					Button bt = (Button)dialoglayout.findViewById(R.id.button1);
					bt.setOnClickListener(new View.OnClickListener() {
						
//						(fromUid : String, fromLid : String, toUid: String, toLid: String, msg : String)
						@Override
						public void onClick(View v) {
							String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
							String fromLid = URLEncoder.encode(myLineId);
							String toUid = URLEncoder.encode(interestItem.getUid());
							String toLid = URLEncoder.encode(lineId);
							String msg = URLEncoder.encode(mm.getText().toString());
							
							String url = Constants.BASE_URL + "new_msg/leave?fromUid=" + uid 
									+ "&fromLid=" + fromLid  
									+ "&toUid=" + toUid
									+ "&toLid=" + toLid 
									+ "&msg=" + msg;
							Log.d(Constants.TAG, "send msg url " + url );
							
							AsyncHttpGet ahg = new AsyncHttpGet(url);
							AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {						    @Override
							    public void onCompleted(Exception e, AsyncHttpResponse response, String result1) {
						    		aa.dismiss();    
						    		if (e != null) {
							            e.printStackTrace();
							            Message.ShowMsgDialog(mContext, "無法留言, 請稍後再試！");
							            return;
							        }
							        
							        Message.ShowMsgDialog(mContext, "成功留言");
						    	}
							});							
							
							
						}
					});
					
					

					aa.show();
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

