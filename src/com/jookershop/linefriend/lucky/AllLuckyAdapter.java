package com.jookershop.linefriend.lucky;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
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
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.ImageHelper;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class AllLuckyAdapter extends ArrayAdapter<LuckyItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private ActiveLuckyFragment fdf;

	public AllLuckyAdapter(Context context, ArrayList<LuckyItem> interestItems,
			DisplayImageOptions options, ActiveLuckyFragment fdf) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		// this.mainColor = mainColor;
		this.options = options;
		// this.selectColor = selectColor;
		this.fdf = fdf;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LuckyItem postItem = getItem(position);
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.lucky_menu_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.maintitle = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.explain = (TextView) rowView
					.findViewById(R.id.textView5);
			viewHolder.award = (TextView) rowView.findViewById(R.id.TextView01);
			viewHolder.people = (Button) rowView.findViewById(R.id.Button01);
			viewHolder.go = (TextView) rowView.findViewById(R.id.textView3);
			viewHolder.mynumber = (Button) rowView
					.findViewById(R.id.Button02);
			viewHolder.image = (ImageView) rowView
					.findViewById(R.id.imageView1);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.maintitle.setText(postItem.getName());
		
		MainActivity.imageLoader.displayImage(postItem.getImage(), holder.image, ImageHelper.emptyHeadOptions);
		
		holder.explain.setText(Html.fromHtml(postItem.getDesc()));
		holder.award.setText(Html.fromHtml(postItem.getAward()));
		holder.people.setText(postItem.getCurrentCount() + "人參加");
		holder.go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!sp.contains(Constants.LINE_STORE_KEY)) {
					Message.ShowMsgDialog(mContext, "請先填寫右上方的基本資料!");
				} else
					checkTime(postItem.getId(), postItem.getName());

			}
		});

		holder.people.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (postItem.getCurrentCount() == 0)
					Message.ShowMsgDialog(mContext, "新抽獎目前還沒有人參加，快點加入吧。");
				else {
					final AlertDialog.Builder builder = new Builder(mContext);
					View dialoglayout = LayoutInflater.from(getContext())
							.inflate(R.layout.game_play_list, null);

					final ListView lv = (ListView) dialoglayout
							.findViewById(R.id.listView1);
					lv.setAdapter(new HistoryItemAdapter(mContext,
							new ArrayList<HistoryItem>()));
					
					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "lucky/history/all?uid="
							+ uid + "&lkid=" + postItem.getId();

					Log.d(Constants.TAG, "lucky history url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeJSONArray(get,
							new AsyncHttpClient.JSONArrayCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source,
										final JSONArray result) {
									if (e != null) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										e.printStackTrace();
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
//													
													HistoryItemAdapter ia = (HistoryItemAdapter) lv
															.getAdapter();
													
													ia.addAll(HistoryItem
															.parse(result));
													ia.notifyDataSetChanged();
												}
											});
								}
							});
					builder.setView(dialoglayout);
					final AlertDialog aa = builder.create();
					aa.show();
				}

			}
		});
		
		holder.mynumber.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (postItem.getCurrentCount() == 0)
					Message.ShowMsgDialog(mContext, "此抽獎目前您還沒參加，快點加入吧。");
				else {
					final AlertDialog.Builder builder = new Builder(mContext);
					View dialoglayout = LayoutInflater.from(getContext())
							.inflate(R.layout.game_play_list, null);

					final ListView lv = (ListView) dialoglayout
							.findViewById(R.id.listView1);
					lv.setAdapter(new HistoryItemAdapter(mContext,
							new ArrayList<HistoryItem>()));
					
					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "lucky/history/yourself?uid="
							+ uid + "&lkid=" + postItem.getId();

					Log.d(Constants.TAG, "lucky yourself history url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeJSONArray(get,
							new AsyncHttpClient.JSONArrayCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source,
										final JSONArray result) {
									if (e != null) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										e.printStackTrace();
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
//													
													HistoryItemAdapter ia = (HistoryItemAdapter) lv
															.getAdapter();
													
													ia.addAll(HistoryItem
															.parse(result));
													ia.notifyDataSetChanged();
												}
											});
								}
							});
					builder.setView(dialoglayout);
					final AlertDialog aa = builder.create();
					aa.show();
				}

			}
		});		
		return rowView;
	}

	static class ViewHolder {
		protected ImageView image;
		protected TextView maintitle;
		protected TextView explain;
		protected TextView award;
		protected Button people;
		protected Button mynumber;
		protected TextView go;
	}

	private void checkTime(final String lkid, final String title) {
		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
		String url = Constants.BASE_URL + "lucky/next_time?uid=" + uid
				+ "&lkid=" + lkid;

		Log.d(Constants.TAG, "lucky checkTime play url " + url);
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
								if (!result.equals("0")) {
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
									((FragmentActivity) mContext)
											.getSupportFragmentManager()
											.beginTransaction()
											.replace(
													R.id.container,
													JoinLuckyFragment
															.newInstance(
																	title,
																	lkid))
											.addToBackStack("alllucky").commit();
								}

							}
						});
					}
				});
	}
}
