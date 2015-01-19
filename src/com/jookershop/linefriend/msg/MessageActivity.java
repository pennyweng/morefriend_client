package com.jookershop.linefriend.msg;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.AdUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MessageActivity extends Activity {
	private ListView lv1;
	private String toId;
	private String toName;
	private String uid;
	private SharedPreferences sp;
	private ProgressBar progressBar1;
	private ProgressBar progressBar2;
	
	 private Handler handler = new Handler();
	 private Runnable runnable = new Runnable() {

	        public void run() {
	        	refresh(false);
	        	handler.postDelayed(this, 20000);
	    }
	 };	
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		sp = getSharedPreferences("linefriend", Context.MODE_PRIVATE);

	    //Remove notification bar
//	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    
		setContentView(R.layout.msg_chat);
		
		final AdView adView = (AdView) this.findViewById(R.id.adView);
		AdUtil.showAD(this, adView);		
		
		progressBar1 = (ProgressBar)this.findViewById(R.id.progressBar1);
		progressBar2 = (ProgressBar)this.findViewById(R.id.progressBar2);
//		this.getActionBar().hide();
//		
		uid = URLEncoder
				.encode(AccountUtil.getUid(MessageActivity.this));		
		toId = this.getIntent().getStringExtra("toId");
		toName = this.getIntent().getStringExtra("toName");
		lv1 = (ListView) this.findViewById(R.id.listView1);

		ImageView iva = (ImageView) this.findViewById(R.id.imageView1);
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid=" + toId, iva);
		
		
		TextView title = (TextView) this.findViewById(R.id.textView1);
		title.setText(toName);
		
		final EditText mm = (EditText) this.findViewById(R.id.editText1);
		
		MessageChatAdapter ma = new MessageChatAdapter(this, new ArrayList<MessageItem>(), uid);
		lv1.setAdapter(ma);
//		
		final Button bt = (Button) this.findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(!bt.isEnabled()) return;
				progressBar2.setVisibility(View.VISIBLE);
				if(mm.getText().toString() == "" || mm.getText().toString().trim().length() == 0) 
					return;				
				bt.setEnabled(false);

				
				String uid = URLEncoder.encode(AccountUtil.getUid(MessageActivity.this));
				String fromLid = URLEncoder.encode(sp.getString(Constants.LINE_STORE_KEY, ""));
				String toUid = URLEncoder.encode(toId);
				String toLid = URLEncoder.encode(toName);
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

						MessageActivity.this.runOnUiThread( new Runnable() {
							public void run() {
								
								progressBar2.setVisibility(View.INVISIBLE);
								bt.setEnabled(true);
					    		if (e != null) {
						            e.printStackTrace();
						            Message.ShowMsgDialog(MessageActivity.this, "無法留言, 請稍後再試！");
						            return;
						        }
					    		
								mm.setText("");
								try {
									Thread.sleep(1000l);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								MessageActivity.this.refresh(false);
							}
						});							    		
			    	}
				});						
				
				
//				refresh(true);
			}
		});
		
		Button deltv = (Button) findViewById(R.id.button2);
		deltv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = URLEncoder
						.encode(AccountUtil.getUid(MessageActivity.this));
				String toUid = URLEncoder.encode(toId);
				String url = Constants.BASE_URL + "new_msg/del?uid=" + uid + "&toUid="+toUid;
				Log.d(Constants.TAG, "del Message url " + url);
				AsyncHttpGet ahg = new AsyncHttpGet(url);
				AsyncHttpClient.getDefaultInstance().executeString(ahg,
						new AsyncHttpClient.StringCallback() {
							@Override
							public void onCompleted(Exception e,
									AsyncHttpResponse response, String result) {
								if (e != null) {
									e.printStackTrace();
									Message.ShowMsgDialog(MessageActivity.this, "刪除失敗, 請稍後再試！");
									return;
								}
								MessageActivity.this.runOnUiThread( new Runnable() {
									public void run() {
										MessageActivity.this.refresh(false);
									}
								});								

							}
						});		
				
			}
		});
		
	}
	
	
	@Override
	protected void onResume() {
		handler.postDelayed(runnable, 1000);
		super.onResume();
		
	}

	@Override
	protected void onPause() {
	     handler.removeCallbacks(runnable);
	     super.onPause();
	}
	
	
	public void refresh(final boolean manually){
		String uid = URLEncoder
				.encode(AccountUtil.getUid(MessageActivity.this));
//		(uid : String, toUid : String)
		String url = Constants.BASE_URL + "new_msg/get?uid=" + uid + "&toUid=" + toId;
		Log.d(Constants.TAG, "get Message url " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg,
				new AsyncHttpClient.JSONArrayCallback() {
					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse response, JSONArray result) {
						if (MessageActivity.this != null)
							MessageActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									progressBar1.setVisibility(View.INVISIBLE);
								}
							});						
						
						
						if (e != null) {
							e.printStackTrace();
							return;
						}
//						if (result.length() == 0)
//							Message.ShowMsgDialog(MessageActivity.this,
//									"目前沒有任何訊息!!!");
//						else {
//							 if(manually) Message.ShowMsgDialog(MessageActivity.this,
//										"更新完畢");
//						}

						final ArrayList<MessageItem> res = new ArrayList<MessageItem>();
						for (int index = 0; index < result.length(); index++) {
							try {
								JSONObject jo = result.getJSONObject(index);
								MessageItem fi = new MessageItem(jo
										.getString("msg"),
										jo.getString("from"), jo
												.getString("fromLid"), jo
												.getString("to"), jo
												.getString("toLid"), jo
												.getLong("ts"));
								res.add(fi);
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						}
						if (MessageActivity.this != null)
							MessageActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									MessageChatAdapter ia = (MessageChatAdapter) lv1
											.getAdapter();
									ia.clear();
//									ia.addAll(res);
									for(int index = 0; index < res.size(); index ++)
										ia.add(res.get(index));
									ia.notifyDataSetChanged();
								}
							});
					}
				});					
	}
	
}
