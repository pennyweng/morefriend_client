package com.jookershop.linefriend.lucky;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.ImageHelper;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class WinnerLuckyAdapter extends ArrayAdapter<LuckyWinnerItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;
	
	public WinnerLuckyAdapter(Context context, ArrayList<LuckyWinnerItem> interestItems,
			DisplayImageOptions options, WinnerLuckyFragment fdf) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		this.options = options;

		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 		

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LuckyWinnerItem postItem = getItem(position);
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.lucky_winner_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.award = (TextView) rowView
					.findViewById(R.id.textView2);
			viewHolder.desc = (TextView) rowView.findViewById(R.id.textView5);
			viewHolder.finalResult = (TextView) rowView.findViewById(R.id.textView4);
			viewHolder.issueTime = (TextView) rowView.findViewById(R.id.textView3);
			viewHolder.lid = (TextView) rowView.findViewById(R.id.textView1);
			viewHolder.totalN =(TextView) rowView.findViewById(R.id.pp);
			viewHolder.user = (ImageView) rowView.findViewById(R.id.imageView1);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
		+ postItem.getUid(), holder.user, ImageHelper.emptyHeadOptions);
		holder.award.setText("恭喜抽中" + postItem.getAward());
		holder.desc.setText(postItem.getDesc());
		holder.finalResult.setText("中獎號碼：" + postItem.getWinNumber());
		holder.issueTime.setText(formatter.format(new Date(postItem.getTs())));
		holder.lid.setText(postItem.getLid());
		holder.totalN.setText(postItem.getTotal() + "人參加記錄");
		holder.totalN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (postItem.getTotal() == 0)
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
							+ uid + "&lkid=" + postItem.getLkid();

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
		
		return rowView;
	}

	static class ViewHolder {
		protected TextView totalN;
		protected TextView lid;
		protected TextView award;
		protected TextView finalResult;
		protected TextView desc;
		protected TextView issueTime;
		protected ImageView user;
	}

}
