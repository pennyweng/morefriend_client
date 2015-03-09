package com.jookershop.linefriend.msg;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

public class MessageCenterOldActivity extends Activity {
	private ListView lv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_center);
//		this.getActionBar().hide();
		
		lv1 = (ListView) this.findViewById(R.id.listView1);
		MessageAdapter ma = new MessageAdapter(this, new ArrayList());
		lv1.setAdapter(ma);
		
		Button bt = (Button) this.findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refresh(true);
			}
		});
		
		TextView deltv = (TextView) findViewById(R.id.textView2);
		deltv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = URLEncoder
						.encode(AccountUtil.getUid(MessageCenterOldActivity.this));
				String url = Constants.BASE_URL + "msg/del?uid=" + uid;
				Log.d(Constants.TAG, "del Message url " + url);
				AsyncHttpGet ahg = new AsyncHttpGet(url);
				AsyncHttpClient.getDefaultInstance().executeString(ahg,
						new AsyncHttpClient.StringCallback() {
							@Override
							public void onCompleted(Exception e,
									AsyncHttpResponse response, String result) {
								if (e != null) {
									e.printStackTrace();
									Message.ShowMsgDialog(MessageCenterOldActivity.this, "刪除失敗, 請稍後再試！");
									return;
								}
								MessageCenterOldActivity.this.runOnUiThread( new Runnable() {
									public void run() {
										MessageCenterOldActivity.this.refresh(false);
									}
								});								

							}
						});		
				
			}
		});
		
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		refresh(false);
	}

	
	
	public void refresh(final boolean manually){
		String uid = URLEncoder
				.encode(AccountUtil.getUid(MessageCenterOldActivity.this));
		String url = Constants.BASE_URL + "msg/get?uid=" + uid;
		Log.d(Constants.TAG, "Message url " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg,
				new AsyncHttpClient.JSONArrayCallback() {
					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse response, JSONArray result) {
						if (e != null) {
							e.printStackTrace();
							return;
						}
						if (result.length() == 0)
							Message.ShowMsgDialog(MessageCenterOldActivity.this,
									"您目前沒有任何訊息!!!");
						else {
							 if(manually) Message.ShowMsgDialog(MessageCenterOldActivity.this,
										"更新完畢");
						}

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
						if (MessageCenterOldActivity.this != null)
							MessageCenterOldActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									MessageAdapter ia = (MessageAdapter) lv1
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
