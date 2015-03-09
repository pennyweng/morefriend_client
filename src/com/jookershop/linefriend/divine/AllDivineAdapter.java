package com.jookershop.linefriend.divine;

import java.net.URLEncoder;
import java.util.ArrayList;

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
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class AllDivineAdapter extends ArrayAdapter<DivineItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private ActiveDivineFragment fdf;

	public AllDivineAdapter(Context context, ArrayList<DivineItem> interestItems,
			DisplayImageOptions options, ActiveDivineFragment fdf) {
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
		final DivineItem postItem = getItem(position);
		View rowView = convertView;
		rowView = LayoutInflater.from(getContext()).inflate(
		R.layout.divine_menu_item, parent, false);
		TextView go = (TextView) rowView.findViewById(R.id.textView3);
		go.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((FragmentActivity) mContext)
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(
						R.id.container,
						MaDuFragment
								.newInstance(postItem.getGid()))
				.addToBackStack("alldivine").commit();	
				
			}
		});
		
//		if (rowView == null) {
//			rowView = LayoutInflater.from(getContext()).inflate(
//					R.layout.game_menu_item, parent, false);
//
//			ViewHolder viewHolder = new ViewHolder();
//			viewHolder.maintitle = (TextView) rowView
//					.findViewById(R.id.textView2);
//			viewHolder.explain = (TextView) rowView
//					.findViewById(R.id.textView5);
//			viewHolder.award = (TextView) rowView.findViewById(R.id.TextView01);
//			viewHolder.people = (Button) rowView.findViewById(R.id.Button01);
//			viewHolder.title = (TextView) rowView.findViewById(R.id.textView1);
//			viewHolder.go = (TextView) rowView.findViewById(R.id.textView3);
//			viewHolder.showHistory = (Button) rowView
//					.findViewById(R.id.Button01);
//
//			rowView.setTag(viewHolder);
//		}
//
//		ViewHolder holder = (ViewHolder) rowView.getTag();
//		
//		final String nn = postItem.getSmallNumber() + "-"
//				+ postItem.getBigNumber();		
//
//		if(postItem.getType().equals(DivineItem.TYPE_GUESS)) {
//			holder.title.setText("遊戲名稱：" + postItem.getName());
//			holder.maintitle.setText(nn);
//		} else if(postItem.getType().equals(DivineItem.TYPE_AB)) {
//			holder.title.setText("");
//			holder.maintitle.setText(postItem.getName());
//		} else if(postItem.getType().equals(DivineItem.TYPE_LUCKYN)) {
//			holder.title.setText("");
//			holder.maintitle.setText(postItem.getName());
//		} else {
//			holder.title.setText("");
//			holder.maintitle.setText(postItem.getName());			
//		}
//		
//		holder.explain.setText(Html.fromHtml(postItem.getIntro()));
//		holder.award.setText(Html.fromHtml(postItem.getReward()));
//		holder.people.setText(postItem.getCount() + "人參加");
//		holder.go.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (!sp.contains(Constants.LINE_STORE_KEY)) {
//					Message.ShowMsgDialog(mContext, "請先填寫右上方的基本資料!");
//				} else {
//					checkTime(postItem, nn);
//				}
//
//			}
//		});
//
//		holder.showHistory.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (postItem.getCount() == 0)
//					Message.ShowMsgDialog(mContext, "新遊戲目前還沒有人參加，快點加入吧。");
//				else {
//					final AlertDialog.Builder builder = new Builder(mContext);
//					View dialoglayout = LayoutInflater.from(getContext())
//							.inflate(R.layout.game_play_list, null);
//
//					final ListView lv = (ListView) dialoglayout
//							.findViewById(R.id.listView1);
//					lv.setAdapter(new HistoryItemAdapter(mContext,
//							new ArrayList<HistoryItem>()));
//					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//					String url = Constants.BASE_URL + "game/all/history?uid="
//							+ uid + "&gid=" + postItem.getGid();
//
//					Log.d(Constants.TAG, "game guess history url " + url);
//					AsyncHttpGet get = new AsyncHttpGet(url);
//
//					AsyncHttpClient.getDefaultInstance().executeJSONArray(get,
//							new AsyncHttpClient.JSONArrayCallback() {
//
//								@Override
//								public void onCompleted(Exception e,
//										AsyncHttpResponse source,
//										final JSONArray result) {
//									if (e != null) {
//										Message.ShowMsgDialog(mContext,
//												"Opps....發生錯誤, 請稍後再試！");
//										e.printStackTrace();
//										return;
//									}
//									((Activity) mContext)
//											.runOnUiThread(new Runnable() {
//												public void run() {
//													HistoryItemAdapter ia = (HistoryItemAdapter) lv
//															.getAdapter();
//													ia.addAll(HistoryItem
//															.parse(result, postItem.getType()));
//													ia.notifyDataSetChanged();
//												}
//											});
//								}
//							});
//					builder.setView(dialoglayout);
//					final AlertDialog aa = builder.create();
//					aa.show();
//				}
//
//			}
//		});
		return rowView;
	}

	static class ViewHolder {
		protected TextView maintitle;
		protected TextView explain;
		protected TextView award;
		protected Button people;
		protected TextView title;
		protected TextView go;
		protected Button showHistory;

	}

//	private void checkTime(final DivineItem gi, final String currentNumber) {
//		String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//		final String gid = gi.getGid();
//		final String gameType = gi.getType();
//		
//		String url = Constants.BASE_URL + "game/all/next_time?uid=" + uid
//				+ "&gid=" + gid;
//
//		Log.d(Constants.TAG, "game checkTime play url " + url);
//		AsyncHttpGet get = new AsyncHttpGet(url);
//
//		AsyncHttpClient.getDefaultInstance().executeString(get,
//				new AsyncHttpClient.StringCallback() {
//
//					@Override
//					public void onCompleted(Exception e,
//							AsyncHttpResponse source, final String result) {
//						if (e != null) {
//							Message.ShowMsgDialog(mContext,
//									"Opps....發生錯誤, 請稍後再試！");
//							e.printStackTrace();
//							return;
//						}
//						((Activity) mContext).runOnUiThread(new Runnable() {
//							public void run() {
//								if (!result.equals("0") && !Constants.IS_SUPER) {
//									int s = Integer.parseInt(result) / 60000;
//									if (s >= 60) {
//										int h = s / 60;
//										int m = s % 60;
//										Message.ShowMsgDialog(mContext, "需過"
//												+ h + "小時又" + m + "分鐘才能再玩一次");
//
//									} else {
//										Message.ShowMsgDialog(mContext, "需過"
//												+ s + "分鐘才能再玩一次");
//									}
//								} else {
//									if(gameType.equals(DivineItem.TYPE_GUESS))
//									((FragmentActivity) mContext)
//											.getSupportFragmentManager()
//											.beginTransaction()
//											.replace(
//													R.id.container,
//													GuessGameFragment
//															.newInstance(
//																	currentNumber,
//																	gid))
//											.addToBackStack("allgame").commit();
//									else if (gameType.equals(DivineItem.TYPE_AB))
//										((FragmentActivity) mContext)
//										.getSupportFragmentManager()
//										.beginTransaction()
//										.replace(
//												R.id.container,
//												ABGameFragment
//														.newInstance(gi))
//										.addToBackStack("allgame").commit();								
//									else if (gameType.equals(DivineItem.TYPE_LUCKYN))
//										((FragmentActivity) mContext)
//										.getSupportFragmentManager()
//										.beginTransaction()
//										.replace(
//												R.id.container,
//												LuckyNGameFragment
//														.newInstance(gi.getGid()))
//										.addToBackStack("allgame").commit();									
//									
//									
//								}
//
//							}
//						});
//					}
//				});
//	}
}
