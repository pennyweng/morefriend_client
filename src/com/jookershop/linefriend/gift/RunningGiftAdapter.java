package com.jookershop.linefriend.gift;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class RunningGiftAdapter extends ArrayAdapter<GiftItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;

	public RunningGiftAdapter(Context context, ArrayList<GiftItem> interestItems,
			DisplayImageOptions options) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);

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
					R.layout.gift_running_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.num = (TextView) rowView.findViewById(R.id.textView2);
//			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
//			viewHolder.progressBar1 = (ProgressBar) rowView.findViewById(R.id.progressBar1);
//			viewHolder.attend = (Button) rowView
//					.findViewById(R.id.button1);
			viewHolder.cancel = (Button)  rowView
			.findViewById(R.id.button2);
			viewHolder.sendCount = (TextView) rowView.findViewById(R.id.textView3);
			

			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.sendCount.setText("目前發送到" + postItem.getSendCount() + "號");
		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.icon, options,
				new SimpleImageLoadingListener(), null);
		
		holder.num.setText(postItem.getCurrentCount() + "號");
//		holder.maxCount.setText("領獎資格：參加" + postItem.getMaxCount() + "次，目前已參加" + postItem.getClickCount() + "次");
//		holder.progressBar1.setMax(postItem.getMaxCount());
//		holder.progressBar1.setProgress(postItem.getClickCount());
		holder.cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Builder MyAlertDialog = new AlertDialog.Builder(mContext);
				MyAlertDialog.setMessage("確定要放棄此排隊號碼嗎？");
				DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						delTask(postItem);
//						((FragmentActivity) mContext).getSupportFragmentManager()
//						.beginTransaction()
//						.replace(R.id.container,
//								AllGiftFragment.newInstance()).commit();
						
						
//						((FragmentActivity) mContext)
//						.getSupportFragmentManager()
//						.beginTransaction()
//						.replace(
//								R.id.container,
//								ClickADFragment
//										.newInstance(gi))
//						.addToBackStack("alldivine").commit();
					}
				};
				DialogInterface.OnClickListener cClick = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				};				
				MyAlertDialog.setNeutralButton("確定", OkClick);
				MyAlertDialog.setNegativeButton("取消", cClick);
				MyAlertDialog.show();
				
				
				
			}
		});
		
//		if(Constants.IS_SUPER) {
//			holder.gidTv.setText(postItem.getId());
//			holder.gidTv.setVisibility(View.VISIBLE);
//		} else holder.gidTv.setVisibility(View.INVISIBLE);
		
//		holder.attend.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
////				if (!sp.contains(Constants.LINE_STORE_KEY)) {
////					Message.ShowMsgDialog(mContext, "請先填寫右上方的基本資料!");
////				} else {
//					checkTime(postItem);
////				}
//				
////				addTask(postItem);
//
//			}
//		});

		return rowView;
	}

	static class ViewHolder {
		protected ImageView icon;
		protected TextView title;
//		protected TextView maxCount;
//		protected ProgressBar progressBar1;
//		protected Button attend;
		protected Button cancel;
		protected TextView num;
		protected TextView sendCount;
	}

	private void delTask(final GiftItem gi) {
		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		final String id = gi.getId();
		
		String url = Constants.BASE_URL + "queue/del?uid=" + uid
				+ "&lgid=" + id + "&giftType=" + gi.getType();

		Log.d(Constants.TAG, "gift delTask play url " + url);
		AsyncHttpGet get = new AsyncHttpGet(url);

		AsyncHttpClient.getDefaultInstance().executeString(get,
				new AsyncHttpClient.StringCallback() {

					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse source, final String result) {
						if (e != null) {
							Message.ShowMsgDialog(mContext,
									"Opps....發生錯誤, 請稍後再試！");
							e.printStackTrace();
							return;
						}
						((Activity) mContext).runOnUiThread(new Runnable() {
							public void run() {
								if(result.indexOf("errmsg:") != -1) {
									Message.ShowMsgDialog(mContext,result.split(":")[1]);
								} else if(result.equals("")) {
									Message.ShowMsgDialog(mContext, "取消成功");
								}
							}
						});
					}
				});
	}

	
	private void checkTime(final GiftItem gi) {
		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		final String id = gi.getId();
		final String giftType = gi.getType();
		
		String url = "";
		if(giftType.equals(GiftItem.TYPE_LINE)) {
			url = Constants.BASE_URL + "gift/line/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_MONEY)) {
			url = Constants.BASE_URL + "gift/money/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_BAG)) {
			url = Constants.BASE_URL + "gift/bag/next_time?uid=" + uid
					+ "&lgid=" + id;			
		} else if(giftType.equals(GiftItem.TYPE_SE)) {
			url = Constants.BASE_URL + "gift/se/next_time?uid=" + uid
					+ "&lgid=" + id;			
		}

		Log.d(Constants.TAG, "gift checkTime play url " + url);
		AsyncHttpGet get = new AsyncHttpGet(url);

		AsyncHttpClient.getDefaultInstance().executeString(get,
				new AsyncHttpClient.StringCallback() {

					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse source, final String result) {
						if (e != null) {
							Message.ShowMsgDialog(mContext,
									"Opps....發生錯誤, 請稍後再試！");
							e.printStackTrace();
							return;
						}
						((Activity) mContext).runOnUiThread(new Runnable() {
							public void run() {
								if (!result.equals("0") && !Constants.IS_SUPER) {
									int s = Integer.parseInt(result) / 60000;
									if (s >= 60) {
										int h = s / 60;
										int m = s % 60;
										Message.ShowMsgDialog(mContext, "需過"
												+ h + "小時又" + m + "分鐘才能再玩一次");

									} else {
										Message.ShowMsgDialog(mContext, "需過"
												+ s + "分鐘才能再玩一次");
									}
								} else {
//									if(giftType.equals(GiftItem.TYPE_LINE)) {
										((FragmentActivity) mContext)
										.getSupportFragmentManager()
										.beginTransaction()
										.replace(
												R.id.container,
												ClickADFragment
														.newInstance(gi))
										.addToBackStack("alldivine").commit();
//									}	
//									else if(giftType.equals(GiftItem.TYPE_MONEY)) {
//										((FragmentActivity) mContext)
//										.getSupportFragmentManager()
//										.beginTransaction()
//										.replace(
//												R.id.container,
//												ClickADFragment
//														.newInstance(gi))
//										.addToBackStack("alldivine").commit();
//									}
								}

							}
						});
					}
				});
	}
}
