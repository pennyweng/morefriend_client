package com.jookershop.linefriend.gift;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClient.StringCallback;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FinishGiftAdapter extends ArrayAdapter<GiftItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;
	private String uid;

	public FinishGiftAdapter(Context context, ArrayList<GiftItem> interestItems,
			DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
        uid = URLEncoder.encode(AccountUtil.getUid(mContext));

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GiftItem postItem = getItem(position);
		View rowView = convertView;

		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.gift_win_item2, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
//			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.finishTime = (TextView) rowView.findViewById(R.id.textView3);
			viewHolder.code = (TextView) rowView.findViewById(R.id.textView4);
			viewHolder.requestBt = (Button) rowView.findViewById(R.id.button1);
			viewHolder.msgCount = (TextView) rowView.findViewById(R.id.textView5);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.icon, options,
				new SimpleImageLoadingListener(), null);
//		holder.maxCount.setText("領獎資格：參加" + postItem.getMaxCount() + "次，目前已參加" + postItem.getClickCount() + "次");
		holder.finishTime.setText("完成時間：" + formatter.format(new Date(postItem.getFinishTime())));
		if(postItem.getCode() != null)
			holder.code.setText("獎勵碼:" + postItem.getCode());
//		else 
//			holder.code.setText("獎勵碼:" + postItem.getId().substring(0, 3) + "_" + uid.substring(0,3));
		
		if(postItem.isShowReport()) {
			holder.requestBt.setVisibility(View.VISIBLE);
			holder.msgCount.setVisibility(View.VISIBLE);
		} else {
			holder.requestBt.setVisibility(View.INVISIBLE);
			holder.msgCount.setVisibility(View.INVISIBLE);
		}
		
		
		if(postItem.getStatus() == 0) {
			holder.requestBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					if(postItem.getType().equals(GiftItem.TYPE_LINE)) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(mContext).inflate(
								R.layout.gift_repo_line, null);
						
						final TextView lid = (TextView) dialoglayout.findViewById(R.id.editText1);
						if(sp.contains("GIFT_REPO_LINE_LID"))
							lid.setText(sp.getString("GIFT_REPO_LINE_LID", ""));
						
						final TextView lname = (TextView) dialoglayout.findViewById(R.id.EditText01);
						if(sp.contains("GIFT_REPO_LINE_LNAME"))
							lname.setText(sp.getString("GIFT_REPO_LINE_LNAME", ""));
	
						
						builder.setView(dialoglayout);
						final AlertDialog a = builder.create();
						Button addBt = (Button) dialoglayout
								.findViewById(R.id.Button02);
						addBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(lid.getText().toString().equals("") || lname.getText().toString().equals("")) {
									Message.ShowMsgDialog(mContext, "請務必填寫所需資料，才能申請領取獎勵。");
								} else {
									sp.edit().putString("GIFT_REPO_LINE_LID", lid.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_LINE_LNAME", lname.getText().toString()).apply();
									
//									if(Constants.IS_SUPER) uid = "50DE4E8F8F8476D79DA9C3CA264357F9B9B52E9F";
									String url = Constants.BASE_URL + "gift/line/repo?uid=" + uid 
											+ "&lgid=" +  URLEncoder.encode(postItem.getId()) 
											+ "&lid=" +  URLEncoder.encode(lid.getText().toString()) 
											+ "&lnick=" +  URLEncoder.encode(lname.getText().toString())
											+ "&lname=" +  URLEncoder.encode(postItem.getName()) 
											+ "&code=" +  URLEncoder.encode(postItem.getCode());
									Log.d(Constants.TAG, "current line gift url " + url );
									
									AsyncHttpGet ahg = new AsyncHttpGet(url);
									AsyncHttpClient.getDefaultInstance().executeString(ahg, new StringCallback() {
									    @Override
									    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
									    	if(result.equals("")) {
									    		Message.ShowMsgDialog(mContext, "已成功送出申請。");
									    		a.dismiss();
									    	} else if(result.equals("err:1")){
									    		Message.ShowMsgDialog(mContext, "您尚未完成此獎勵，請完成後再來申請。若有任何問題歡迎語版主ahappychat聯絡。");
									    	} else if(result.equals("err:2")){
									    		Message.ShowMsgDialog(mContext, "此獎勵之前已經申請了，無法重新申請。");
									    	} else if(result.equals("err:3")){
									    		Message.ShowMsgDialog(mContext, "LINE的暱稱或者是ID填寫不夠確實。");
									    	}else if(result.equals("err:4")){
									    		Message.ShowMsgDialog(mContext, "此獎勵的驗證碼有誤。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}else if(result.equals("err:5")){
									    		Message.ShowMsgDialog(mContext, "此獎勵已經不存在。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}
									    }
									});		
								}
							}
						});
						
						Button exitBt = (Button) dialoglayout
								.findViewById(R.id.Button03);
						exitBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								a.dismiss();
							}
						});
						
						a.show();
					} else if(postItem.getType().equals(GiftItem.TYPE_MONEY)) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(mContext).inflate(
								R.layout.gift_repo_money, null);
	
						final TextView m1 = (TextView) dialoglayout.findViewById(R.id.editText1);
						if(sp.contains("GIFT_REPO_MONEY1"))
							m1.setText(sp.getString("GIFT_REPO_MONEY1", ""));
						
						final TextView m2 = (TextView) dialoglayout.findViewById(R.id.EditText01);
						if(sp.contains("GIFT_REPO_MONEY2"))
							m2.setText(sp.getString("GIFT_REPO_MONEY2", ""));
	
						final TextView m3 = (TextView) dialoglayout.findViewById(R.id.EditText02);
						if(sp.contains("GIFT_REPO_MONEY3"))
							m3.setText(sp.getString("GIFT_REPO_MONEY3", ""));
						
						final TextView m4 = (TextView) dialoglayout.findViewById(R.id.EditText03);
						if(sp.contains("GIFT_REPO_MONEY4"))
							m4.setText(sp.getString("GIFT_REPO_MONEY4", ""));
						
						
						builder.setView(dialoglayout);
						final AlertDialog a = builder.create();
						Button addBt = (Button) dialoglayout
								.findViewById(R.id.Button02);
						addBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(m1.getText().toString().equals("") || m2.getText().toString().equals("") 
										|| m3.getText().toString().equals("") || m4.getText().toString().equals("")) {
									Message.ShowMsgDialog(mContext, "請務必填寫所需資料，才能申請領取獎勵。");
								} else {
//									if(Constants.IS_SUPER) uid = "50DE4E8F8F8476D79DA9C3CA264357F9B9B52E9F";
									String url = Constants.BASE_URL + "gift/money/repo?uid=" + uid 
											+ "&lgid=" +  URLEncoder.encode(postItem.getId()) 
											+ "&bank_name=" +  URLEncoder.encode(m1.getText().toString()) 
											+ "&bank_code=" +  URLEncoder.encode(m2.getText().toString())
											+ "&account=" +  URLEncoder.encode(m3.getText().toString())
											+ "&account_name=" +  URLEncoder.encode(m4.getText().toString())
											+ "&code=" +  URLEncoder.encode(postItem.getCode());
									Log.d(Constants.TAG, "current line gift url " + url );
									
									AsyncHttpGet ahg = new AsyncHttpGet(url);
									AsyncHttpClient.getDefaultInstance().executeString(ahg, new StringCallback() {
									    @Override
									    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
									    	if(result.equals("")) {
									    		Message.ShowMsgDialog(mContext, "已成功送出申請。");
									    		a.dismiss();
									    	} else if(result.equals("err:1")){
									    		Message.ShowMsgDialog(mContext, "您尚未完成此獎勵，請完成後再來申請。若有任何問題歡迎語版主ahappychat聯絡。");
									    	} else if(result.equals("err:2")){
									    		Message.ShowMsgDialog(mContext, "此獎勵之前已經申請了，無法重新申請。");
									    	} else if(result.equals("err:3")){
									    		Message.ShowMsgDialog(mContext, "資料填寫不夠確實。");
									    	}else if(result.equals("err:4")){
									    		Message.ShowMsgDialog(mContext, "此獎勵的驗證碼有誤。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}else if(result.equals("err:5")){
									    		Message.ShowMsgDialog(mContext, "此獎勵已經不存在。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}
									    }
									});		
									
									
									sp.edit().putString("GIFT_REPO_MONEY1", m1.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_MONEY2", m2.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_MONEY3", m3.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_MONEY4", m4.getText().toString()).apply();
									a.dismiss();
								}							
							}
						});
						
						Button exitBt = (Button) dialoglayout
								.findViewById(R.id.Button03);
						exitBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								a.dismiss();
							}
						});
						
						a.show();
					} else if(postItem.getType().equals(GiftItem.TYPE_BAG)) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(mContext).inflate(
								R.layout.gift_repo_bag, null);
	
						final TextView m1 = (TextView) dialoglayout.findViewById(R.id.editText1);
						if(sp.contains("GIFT_REPO_BAG1"))
							m1.setText(sp.getString("GIFT_REPO_BAG1", ""));
						
						final TextView m2 = (TextView) dialoglayout.findViewById(R.id.EditText01);
						if(sp.contains("GIFT_REPO_BAG2"))
							m2.setText(sp.getString("GIFT_REPO_BAG2", ""));
	
						final TextView m3 = (TextView) dialoglayout.findViewById(R.id.EditText02);
						if(sp.contains("GIFT_REPO_BAG3"))
							m3.setText(sp.getString("GIFT_REPO_BAG3", ""));
						
						final TextView m4 = (TextView) dialoglayout.findViewById(R.id.EditText03);
						if(sp.contains("GIFT_REPO_BAG4"))
							m4.setText(sp.getString("GIFT_REPO_BAG4", ""));
	
						
						builder.setView(dialoglayout);
						final AlertDialog a = builder.create();
						Button addBt = (Button) dialoglayout
								.findViewById(R.id.Button02);
						addBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(m1.getText().toString().equals("") || m2.getText().toString().equals("") 
										|| m3.getText().toString().equals("") || m4.getText().toString().equals("")) {
									Message.ShowMsgDialog(mContext, "請務必填寫所需資料，才能申請領取獎勵。");
								} else {
									String url = Constants.BASE_URL + "gift/bag/repo?uid=" + uid 
											+ "&lgid=" +  URLEncoder.encode(postItem.getId()) 
											+ "&postcode=" +  URLEncoder.encode(m1.getText().toString()) 
											+ "&address=" +  URLEncoder.encode(m2.getText().toString())
											+ "&account=" +  URLEncoder.encode(m3.getText().toString())
											+ "&phone=" +  URLEncoder.encode(m4.getText().toString())
											+ "&code=" +  URLEncoder.encode(postItem.getCode());
									Log.d(Constants.TAG, "current line gift url " + url );
									
									AsyncHttpGet ahg = new AsyncHttpGet(url);
									AsyncHttpClient.getDefaultInstance().executeString(ahg, new StringCallback() {
									    @Override
									    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
									    	if(result.equals("")) {
									    		Message.ShowMsgDialog(mContext, "已成功送出申請。");
									    		a.dismiss();
									    	} else if(result.equals("err:1")){
									    		Message.ShowMsgDialog(mContext, "您尚未完成此獎勵，請完成後再來申請。若有任何問題歡迎語版主ahappychat聯絡。");
									    	} else if(result.equals("err:2")){
									    		Message.ShowMsgDialog(mContext, "此獎勵之前已經申請了，無法重新申請。");
									    	} else if(result.equals("err:3")){
									    		Message.ShowMsgDialog(mContext, "資料填寫不夠確實。");
									    	}else if(result.equals("err:4")){
									    		Message.ShowMsgDialog(mContext, "此獎勵的驗證碼有誤。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}else if(result.equals("err:5")){
									    		Message.ShowMsgDialog(mContext, "此獎勵已經不存在。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}
									    }
									});		
									
									
									sp.edit().putString("GIFT_REPO_BAG1", m1.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG2", m2.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG3", m3.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG4", m4.getText().toString()).apply();
									a.dismiss();
								}
							}
						});
						
						Button exitBt = (Button) dialoglayout
								.findViewById(R.id.Button03);
						exitBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								a.dismiss();
							}
						});
						
						a.show();					
					} else if(postItem.getType().equals(GiftItem.TYPE_SE)) {
						final AlertDialog.Builder builder = new Builder(mContext);
						View dialoglayout = LayoutInflater.from(mContext).inflate(
								R.layout.gift_repo_se, null);
	
						final TextView m1 = (TextView) dialoglayout.findViewById(R.id.editText1);
						if(sp.contains("GIFT_REPO_BAG1"))
							m1.setText(sp.getString("GIFT_REPO_BAG1", ""));
						
						final TextView m2 = (TextView) dialoglayout.findViewById(R.id.EditText01);
						if(sp.contains("GIFT_REPO_BAG2"))
							m2.setText(sp.getString("GIFT_REPO_BAG2", ""));
	
						final TextView m3 = (TextView) dialoglayout.findViewById(R.id.EditText02);
						if(sp.contains("GIFT_REPO_BAG3"))
							m3.setText(sp.getString("GIFT_REPO_BAG3", ""));
						
						final TextView m4 = (TextView) dialoglayout.findViewById(R.id.EditText03);
						if(sp.contains("GIFT_REPO_BAG4"))
							m4.setText(sp.getString("GIFT_REPO_BAG4", ""));
	
						
						builder.setView(dialoglayout);
						final AlertDialog a = builder.create();
						Button addBt = (Button) dialoglayout
								.findViewById(R.id.Button02);
						addBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(m1.getText().toString().equals("") || m2.getText().toString().equals("") 
										|| m3.getText().toString().equals("") || m4.getText().toString().equals("")) {
									Message.ShowMsgDialog(mContext, "請務必填寫所需資料，才能申請領取獎勵。");
								} else {
									String url = Constants.BASE_URL + "gift/se/repo?uid=" + uid 
											+ "&lgid=" +  URLEncoder.encode(postItem.getId()) 
											+ "&postcode=" +  URLEncoder.encode(m1.getText().toString()) 
											+ "&address=" +  URLEncoder.encode(m2.getText().toString())
											+ "&account=" +  URLEncoder.encode(m3.getText().toString())
											+ "&phone=" +  URLEncoder.encode(m4.getText().toString())
											+ "&code=" +  URLEncoder.encode(postItem.getCode());
									Log.d(Constants.TAG, "current line gift url " + url );
									
									AsyncHttpGet ahg = new AsyncHttpGet(url);
									AsyncHttpClient.getDefaultInstance().executeString(ahg, new StringCallback() {
									    @Override
									    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
									    	if(result.equals("")) {
									    		Message.ShowMsgDialog(mContext, "已成功送出申請。");
									    		a.dismiss();
									    	} else if(result.equals("err:1")){
									    		Message.ShowMsgDialog(mContext, "您尚未完成此獎勵，請完成後再來申請。若有任何問題歡迎語版主ahappychat聯絡。");
									    	} else if(result.equals("err:2")){
									    		Message.ShowMsgDialog(mContext, "此獎勵之前已經申請了，無法重新申請。");
									    	} else if(result.equals("err:3")){
									    		Message.ShowMsgDialog(mContext, "資料填寫不夠確實。");
									    	}else if(result.equals("err:4")){
									    		Message.ShowMsgDialog(mContext, "此獎勵的驗證碼有誤。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}else if(result.equals("err:5")){
									    		Message.ShowMsgDialog(mContext, "此獎勵已經不存在。若有任何問題歡迎語版主ahappychat聯絡。");
									    	}
									    }
									});		
									
									sp.edit().putString("GIFT_REPO_BAG1", m1.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG2", m2.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG3", m3.getText().toString()).apply();
									sp.edit().putString("GIFT_REPO_BAG4", m4.getText().toString()).apply();
									a.dismiss();
								}
							}
						});
						
						Button exitBt = (Button) dialoglayout
								.findViewById(R.id.Button03);
						exitBt.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								a.dismiss();
							}
						});
						
						a.show();					
					}
				}
			});
			holder.requestBt.setText("申請領獎");
			
		} else if(postItem.getStatus() == 1) {
			holder.requestBt.setText("審核中");
		} else if(postItem.getStatus() == 2) {
			holder.requestBt.setText("核對完成");
		} else if(postItem.getStatus() == 3) {
			holder.requestBt.setText("有問題");
		} else if(postItem.getStatus() == 4) {
			holder.requestBt.setText("已送出");
		}
		
		if(postItem.getStatus() == 1 || postItem.getStatus() == 2 || postItem.getStatus() == 3 || postItem.getStatus() == 4) {
			holder.requestBt.setOnClickListener(null);
			holder.msgCount.setVisibility(View.VISIBLE);
			holder.msgCount.setText(postItem.getMsgCount() + "則留言");
			holder.msgCount.setOnClickListener( new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final AlertDialog.Builder builder = new Builder(mContext);
					View dialoglayout = LayoutInflater.from(mContext).inflate(
							R.layout.gift_repo_msg, null);
					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//					if(Constants.IS_SUPER) uid = "50DE4E8F8F8476D79DA9C3CA264357F9B9B52E9F";
					final EditText msgt = (EditText)dialoglayout.findViewById(R.id.editText1);
					
					final ListView lv = (ListView) dialoglayout.findViewById(R.id.listView1);
//					ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, new String[]{});
//			    	lv.setAdapter(adapter);
					
					String url = Constants.BASE_URL + "gift/line/get/repo/msg?uid=" + uid + "&lgid=" + postItem.getId();
					Log.d(Constants.TAG, "current finish gift url " + url );
					AsyncHttpGet ahg = new AsyncHttpGet(url);
					AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg, new AsyncHttpClient.JSONArrayCallback() {
					    @Override
					    public void onCompleted(Exception e, AsyncHttpResponse response, final JSONArray result) {
					    	try {
								if(((Activity)mContext)!= null) {
									((Activity)mContext).runOnUiThread(new Runnable() {
										public void run() {
											try {
												ArrayList<HashMap<String,String>> ia = new ArrayList<HashMap<String,String>>();
										    	for(int index = 0; index < result.length(); index ++) {
										    		HashMap<String,String> item = new HashMap<String,String>();
										    		JSONObject jj = result.getJSONObject(index);
										    		int from = jj.getInt("from");
										    		String name = "";
										    		if(from == 0) name = "版主："; else name = "我：";
										    		item.put("date", formatter.format(new Date(jj.getLong("ts"))));
										    		item.put("body",  name + jj.getString("msg"));
										    		ia.add(item);
										    	}
										    	
										    	SimpleAdapter adapter = new SimpleAdapter(mContext, ia, android.R.layout.simple_list_item_2, 
										    			new String[] { "date","body" }, 
										    			new int[] { android.R.id.text2, android.R.id.text1 });
										    	lv.setAdapter(adapter);
										    	adapter.notifyDataSetChanged();
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
								}
						    	
					    	} catch (Exception a) {
					    		a.printStackTrace();
					    	}
					    }
					});		
					
					
					builder.setView(dialoglayout);
					final AlertDialog a = builder.create();
					Button sendBt = (Button)dialoglayout.findViewById(R.id.button1);
					sendBt.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//							if(Constants.IS_SUPER) uid = "50DE4E8F8F8476D79DA9C3CA264357F9B9B52E9F";
							
							String url = Constants.BASE_URL + "gift/line/repo/msg?uid=" + uid + "&lgid=" + postItem.getId() 
									+ "&msg=" + URLEncoder.encode(msgt.getText().toString());
							Log.d(Constants.TAG, "current finish gift url " + url );
							AsyncHttpGet ahg = new AsyncHttpGet(url);
							AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
							    @Override
							    public void onCompleted(Exception e, AsyncHttpResponse response, final String result) {
							    	try {
								    	if (e != null) {
								            e.printStackTrace();
								            Message.ShowMsgDialog(mContext, "發生錯誤，請稍後在試。");
								        } else {
								            Message.ShowMsgDialog(mContext, "成功送出留言。");
								            a.dismiss();
								        }
							    	} catch (Exception a) {
							    		a.printStackTrace();
							    	}
							    }
							});		
							
							
						}
					});					
					
					a.show();
				}
			});
			
		} else {
			holder.msgCount.setVisibility(View.INVISIBLE);
			holder.msgCount.setOnClickListener(null);
		}
		
		return rowView;
	}

	static class ViewHolder {
		protected ImageView icon;
		protected TextView title;
//		protected TextView maxCount;
		protected TextView finishTime;
		protected TextView code;
		protected Button requestBt;
		protected TextView msgCount;
	}
}
