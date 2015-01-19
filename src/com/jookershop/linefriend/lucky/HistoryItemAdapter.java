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
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.ImageHelper;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {
	private Context mContext;
	private SimpleDateFormat formatter;


	public HistoryItemAdapter(Context context, ArrayList<HistoryItem> interestItems) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, mContext.getResources().getDisplayMetrics());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HistoryItem postItem = getItem(position);
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.game_history_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.user = (ImageView) rowView
					.findViewById(R.id.imageView1);
			
			viewHolder.guess = (TextView) rowView
					.findViewById(R.id.textView1);
			
			viewHolder.desc = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.issueTime = (TextView) rowView.findViewById(R.id.textView3);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
				+ postItem.getUid(), holder.user, ImageHelper.emptyHeadOptions);

		holder.user.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AccountUtil.showInfo(mContext, postItem.getUid());
			}
		});
		
		holder.guess.setText("我的抽獎序號：" + postItem.getGuess());
		holder.desc.setText(postItem.getDesc());
		holder.issueTime.setText(formatter.format(new Date(postItem.getTs())));

		return rowView;
	}

	static class ViewHolder {
		protected ImageView user;
		protected TextView guess;
		protected TextView desc;
		protected TextView issueTime;

	}

}
