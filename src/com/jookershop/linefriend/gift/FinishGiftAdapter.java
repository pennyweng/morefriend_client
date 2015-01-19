package com.jookershop.linefriend.gift;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.discuss.AllPostAdapter;
import com.jookershop.linefriend.discuss.CreateDiscussActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.jookershop.linefriend3.R;
import com.koushikdutta.async.http.AsyncHttpClient;
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
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
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
					R.layout.gift_win_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.textView1);
			viewHolder.maxCount = (TextView) rowView.findViewById(R.id.textView2);
			viewHolder.finishTime = (TextView) rowView.findViewById(R.id.textView3);
			viewHolder.code = (TextView) rowView.findViewById(R.id.textView4);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.title.setText(postItem.getName());
		MainActivity.imageLoader.displayImage(postItem.getImgUrl(), holder.icon, options,
				new SimpleImageLoadingListener(), null);
		holder.maxCount.setText("領獎資格：參加" + postItem.getMaxCount() + "次，目前已參加" + postItem.getClickCount() + "次");
		holder.finishTime.setText("完成時間：" + formatter.format(new Date(postItem.getFinishTime())));
		holder.code.setText("獎勵碼：" + postItem.getId().substring(0, 3) + "_" + uid.substring(0,3));
		return rowView;
	}

	static class ViewHolder {
		protected ImageView icon;
		protected TextView title;
		protected TextView maxCount;
		protected TextView finishTime;
		protected TextView code;
		
	}
}
