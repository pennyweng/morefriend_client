package com.jookershop.linefriend.game;

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
import com.jookershop.linefriend.account.AccountActivity;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.game.HistoryItemAdapter.ViewHolder;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.ImageHelper;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend4.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class WinnerGameAdapter extends ArrayAdapter<GameItem> {
	private Context mContext;
	private DisplayImageOptions options;
	private SharedPreferences sp;
	private SimpleDateFormat formatter;
	
	public WinnerGameAdapter(Context context, ArrayList<GameItem> interestItems,
			DisplayImageOptions options, WinnerGameFragment fdf) {
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
		final GameItem postItem = getItem(position);
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.game_winner_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.user = (ImageView) rowView
					.findViewById(R.id.imageView1);
			
			viewHolder.guess = (TextView) rowView
					.findViewById(R.id.textView1);
			
			viewHolder.desc = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.issueTime = (TextView) rowView.findViewById(R.id.textView3);
			
			viewHolder.finalNumber = (TextView) rowView.findViewById(R.id.textView4);
			viewHolder.total = (TextView) rowView.findViewById(R.id.pp);
			viewHolder.code = (TextView) rowView.findViewById(R.id.textView5);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.guess.setText(postItem.getWinnerLid());
		if(postItem.getWid() != null && postItem.getWid() != "") {
			MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
					+ postItem.getWid(), holder.user, ImageHelper.emptyHeadOptions);
			
			if( postItem.getWid().equals(AccountUtil.getUid(mContext)) || Constants.IS_SUPER) {
				String rec = postItem.getGid().substring(0, 3);
				holder.code.setText("中獎碼:" + rec);
				holder.code.setVisibility(View.VISIBLE);
			} else 
				holder.code.setVisibility(View.INVISIBLE);
		}
		
		holder.user.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AccountUtil.showInfo(mContext, postItem.getWid());
			}
		});
		
		holder.desc.setText(postItem.getReward());
		holder.issueTime.setText(formatter.format(new Date(postItem.getEndTime())));
		
		if(postItem.getTotal()!= 0) {
			holder.total.setText(postItem.getTotal() + "人參加記錄");
			holder.total.setVisibility(View.VISIBLE);
			holder.total.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final AlertDialog.Builder builder = new Builder(mContext);
					View dialoglayout = LayoutInflater.from(getContext())
							.inflate(R.layout.game_play_list, null);

					final ListView lv = (ListView) dialoglayout
							.findViewById(R.id.listView1);
					lv.setAdapter(new HistoryItemAdapter(mContext,
							new ArrayList<HistoryItem>()));
					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String url = Constants.BASE_URL + "game/guess/history?uid="
							+ uid + "&gid=" + postItem.getGid();

					Log.d(Constants.TAG, "game guess history url " + url);
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
													HistoryItemAdapter ia = (HistoryItemAdapter) lv
															.getAdapter();
													ia.addAll(HistoryItem
															.parse(result, postItem.getType()));
													ia.notifyDataSetChanged();
												}
											});
								}
							});
					builder.setView(dialoglayout);
					final AlertDialog aa = builder.create();
					aa.show();
				
				}
			});
			
		} else {
			holder.total.setVisibility(View.INVISIBLE);
		}
		

		if(postItem.getType().equals(GameItem.TYPE_GUESS) && postItem.getWinNumber() != null) {
			holder.finalNumber.setVisibility(View.VISIBLE);
			holder.finalNumber.setText("終極密碼：" + postItem.getWinNumber());
		} else if(postItem.getType().equals(GameItem.TYPE_AB) && postItem.getWinNumber() != null) {
			holder.finalNumber.setVisibility(View.VISIBLE);
			holder.finalNumber.setText("1A2B猜數字：" + postItem.getWinNumber());
		} else if(postItem.getType().equals(GameItem.TYPE_LUCKYN)) {
			holder.finalNumber.setVisibility(View.VISIBLE);
			holder.finalNumber.setText("戳戳樂中獎");
		}
		else {
			holder.finalNumber.setVisibility(View.INVISIBLE);
		}
		return rowView;
	}

	static class ViewHolder {
		protected ImageView user;
		protected TextView guess;
		protected TextView desc;
		protected TextView issueTime;
		protected TextView total;
		protected TextView finalNumber;
		protected TextView code;

	}

}
