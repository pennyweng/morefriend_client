package com.jookershop.linefriend.discuss;

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
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AllPostAdapter extends ArrayAdapter<PostItem> {
	private Context mContext;
	// public String [] colors ={"#A3A948"};
	// public String mainColor;
	private DisplayImageOptions options;
	// private String selectColor;
	private SharedPreferences sp;
	private AllPostFragment fdf;


	public AllPostAdapter(Context context, ArrayList<PostItem> interestItems,
			DisplayImageOptions options, AllPostFragment fdf ) {
		super(context, R.layout.category_item, interestItems);
		mContext = context;
		// this.mainColor = mainColor;
		this.options = options;
		// this.selectColor = selectColor;
		 this.fdf = fdf;
		sp = mContext.getSharedPreferences("linefriend", Context.MODE_APPEND);

	}

	public int getHeight(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mContext.getResources().getDisplayMetrics());		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PostItem postItem = getItem(position);
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.discuss_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView.findViewById(R.id.textView1);
			viewHolder.postImg = (ImageView) rowView
					.findViewById(R.id.imageView2);
			viewHolder.lid = (TextView) rowView.findViewById(R.id.textView3);
			viewHolder.replyCount = (TextView) rowView
					.findViewById(R.id.textView2);
			viewHolder.userImg = (ImageView) rowView
					.findViewById(R.id.imageView1);
			viewHolder.ts = (TextView) rowView.findViewById(R.id.TextView01);
			rowView.setTag(viewHolder);
		}

		TextView blockTv = (TextView) rowView.findViewById(R.id.TextView02);
		if (Constants.IS_SUPER) {
			blockTv.setVisibility(View.VISIBLE);
			blockTv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String uid = URLEncoder.encode(postItem.getUid());
					String url = Constants.BASE_URL + "adminblock/add?uid=" + uid;
					Log.d(Constants.TAG, "block user url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeString(get,
							new AsyncHttpClient.StringCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source, String result) {
									if (e != null) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										e.printStackTrace();
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
													Builder MyAlertDialog = new AlertDialog.Builder(
															mContext);
													MyAlertDialog
															.setMessage("封鎖成功");
													DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int which) {
															fdf.refresh();
														}
													};
													MyAlertDialog
															.setNeutralButton(
																	"確認",
																	OkClick);
													MyAlertDialog.show();
												}
											});
								}
							});

				}
			});			
			
		} else {
			blockTv.setVisibility(View.INVISIBLE);
		}
		
		
		TextView delTv = (TextView) rowView.findViewById(R.id.textView4);
		if (Constants.IS_SUPER || postItem.getUid().equals(AccountUtil.getUid(mContext))) {
			delTv.setVisibility(View.VISIBLE);
			delTv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
					String pid = URLEncoder.encode(postItem.getPid());

					String url = Constants.BASE_URL + "post/del?uid=" + uid
							+ "&categoryType="
							+ Constants.TYPE_INTEREST + "&pid=" + pid;

					Log.d(Constants.TAG, "del post url " + url);
					AsyncHttpGet get = new AsyncHttpGet(url);

					AsyncHttpClient.getDefaultInstance().executeString(get,
							new AsyncHttpClient.StringCallback() {

								@Override
								public void onCompleted(Exception e,
										AsyncHttpResponse source, String result) {
									if (e != null) {
										Message.ShowMsgDialog(mContext,
												"Opps....發生錯誤, 請稍後再試！");
										e.printStackTrace();
										return;
									}
									((Activity) mContext)
											.runOnUiThread(new Runnable() {
												public void run() {
													Builder MyAlertDialog = new AlertDialog.Builder(
															mContext);
													MyAlertDialog
															.setMessage("刪除成功");
													DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int which) {
															fdf.refresh();
														}
													};
													MyAlertDialog
															.setNeutralButton(
																	"確認",
																	OkClick);
													MyAlertDialog.show();
												}
											});
								}
							});

				}
			});
		} else {
			delTv.setVisibility(View.INVISIBLE);
		}
		
		
		
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.title.setText(postItem.getData());
		holder.lid.setText(postItem.getLid());
		int tt = (int) (System.currentTimeMillis() - postItem.getTs()) / 60000;
		if (tt <= 60)
			holder.ts.setText(tt + "分鐘前");
		else {
			int hh = tt / 60;
			holder.ts.setText(hh + "小時前");
		}
		
		
		if (postItem.getWithPC()) {
			holder.postImg.getLayoutParams().height = ImageHelper.getHeight(mContext, 160);
			holder.postImg.setVisibility(View.VISIBLE);			
//			UrlImageViewHelper.setUrlDrawable(holder.postImg,
//					Constants.IMAGE_BASE_URL + "post/image?pid=" + postItem.getPid());
			
			MainActivity.imageLoader.displayImage(
					Constants.IMAGE_BASE_URL + "post/image?pid=" + postItem.getPid(), 
					holder.postImg, options);
			holder.title.setTextSize(14);
//			holder.title.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
			holder.title.setGravity(Gravity.CENTER);
		} else {
			holder.postImg.getLayoutParams().height = ImageHelper.getHeight(mContext, 110);
			holder.postImg.setImageResource(android.R.color.transparent);
			holder.postImg.setImageDrawable(null);
			holder.postImg.setVisibility(View.INVISIBLE);
			holder.title.setTextSize(15);
			holder.title.setGravity(Gravity.CENTER);
		}
		holder.postImg.requestLayout();

		
//		UrlImageViewHelper.setUrlDrawable(
//				holder.userImg,
//				Constants.IMAGE_BASE_URL + "account/image?uid="
//						+ postItem.getUid(), R.drawable.emptyhead);
		MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
				+ postItem.getUid(), holder.userImg, ImageHelper.emptyHeadOptions);
		
		
		holder.userImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AccountUtil.showInfo(mContext, postItem.getUid());
			}
		});

		holder.replyCount.setText(postItem.getReplyCount() + "人回應");
		rowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new Builder(mContext);
				View dialoglayout = LayoutInflater.from(getContext()).inflate(
						R.layout.show_discuss, null);

				ImageView postImg = (ImageView) dialoglayout
						.findViewById(R.id.imageView1);
				if (postItem.getWithPC()) {
//					UrlImageViewHelper.setUrlDrawable(
//							postImg,
//							Constants.IMAGE_BASE_URL + "post/image?pid="
//									+ postItem.getPid());
					MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "post/image?pid="
							+ postItem.getPid(), postImg, options);
				} else {
					RelativeLayout rl1 = (RelativeLayout) dialoglayout
							.findViewById(R.id.rl1);
					rl1.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT, 50));
				}

				TextView title = (TextView) dialoglayout
						.findViewById(R.id.textView2);
				title.setText(postItem.getData());
				TextView name = (TextView) dialoglayout
						.findViewById(R.id.textView1);
				name.setText(postItem.getLid());

				ImageView userImg = (ImageView) dialoglayout
						.findViewById(R.id.imageView2);
//				UrlImageViewHelper.setUrlDrawable(userImg,
//						Constants.IMAGE_BASE_URL + "account/image?uid="
//								+ postItem.getUid(), R.drawable.emptyhead);
				MainActivity.imageLoader.displayImage(Constants.IMAGE_BASE_URL + "account/image?uid="
						+ postItem.getUid(), userImg, ImageHelper.emptyHeadOptions);
				userImg.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AccountUtil.showInfo(mContext, postItem.getUid());

					}
				});

				final EditText data = (EditText) dialoglayout
						.findViewById(R.id.editText1);
				final Button save = (Button) dialoglayout
						.findViewById(R.id.Button02);

				final ListView interestGV = (ListView) dialoglayout
						.findViewById(R.id.listView1);
				interestGV.setAdapter(new ReplyItemAdapter(mContext,
						new ArrayList<ReplyItem>()));
				final TextView countTv = (TextView) dialoglayout
						.findViewById(R.id.textView3);
				loadItmes(postItem.getPid(), interestGV, countTv);

				builder.setView(dialoglayout);
				final AlertDialog a = builder.create();

				save.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(!sp.contains(Constants.LINE_STORE_KEY) || sp.getString(Constants.LINE_STORE_KEY, "") == "")
							Message.ShowMsgDialog(mContext, "麻煩先完成基本資料設定" );						
						else if (data.getText().toString().length() <= 0) {
							Message.ShowMsgDialog(mContext, "請輸入留言!");
						} else {
							save.setEnabled(false);
							// (uid : String, lid : String, data : String, pid :
							// String)
							String uid = URLEncoder.encode(AccountUtil
									.getUid(mContext));
							String lineId = URLEncoder.encode(sp.getString(
									Constants.LINE_STORE_KEY, ""));
							
							String postData = URLEncoder.encode(data.getText()
									.toString());
							String pid = URLEncoder.encode(postItem.getPid());

							String url = Constants.BASE_URL
									+ "reply/create?uid=" + uid + "&lid="
									+ lineId + "&data=" + postData + "&pid="
									+ pid;

							Log.d(Constants.TAG, "insert url " + url);
							AsyncHttpGet get = new AsyncHttpGet(url);

							AsyncHttpClient.getDefaultInstance().executeString(
									get, new AsyncHttpClient.StringCallback() {

										@Override
										public void onCompleted(
												final Exception e,
												AsyncHttpResponse source,
												String result) {
											if(mContext != null)
											((Activity) mContext)
													.runOnUiThread(new Runnable() {
														public void run() {
															save.setEnabled(true);
															if (e != null) {
																Message.ShowMsgDialog(
																		mContext,
																		"Opps....發生錯誤, 請稍後再試！");
																e.printStackTrace();
																return;
															}

															Builder MyAlertDialog = new AlertDialog.Builder(
																	mContext);
															MyAlertDialog
																	.setMessage("留言成功");
															DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	// onBackPressed();
																	// a.dismiss();
																	data.setText("");
																	loadItmes(
																			postItem.getPid(),
																			interestGV,
																			countTv);
																	fdf.refresh(postItem.getPid());
																}
															};
															MyAlertDialog
																	.setNeutralButton(
																			"確認",
																			OkClick);
															MyAlertDialog
																	.show();
														}
													});
										}
									});
						}

					}
				});

				a.show();
			}
		});

		return rowView;
	}

	public void loadItmes(String pid, final ListView interestGV,
			final TextView countTv) {
		String url = Constants.BASE_URL + "reply/search?pid=" + pid;
		Log.d(Constants.TAG, "reply search url " + url);
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

						final ArrayList<ReplyItem> res = new ArrayList<ReplyItem>();
						for (int index = 0; index < result.length(); index++) {
							try {
								JSONObject jo = result.getJSONObject(index);
								res.add(ReplyItem.genReplyItem(jo));
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						}
						if (mContext != null)
							((Activity) mContext).runOnUiThread(new Runnable() {
								public void run() {
									ReplyItemAdapter ia = (ReplyItemAdapter) interestGV
											.getAdapter();
									ia.clear();
									for(int index = 0; index < res.size(); index ++)
										ia.add(res.get(index));
//									ia.addAll(res);
									countTv.setText(ia.getCount() + "則留言");
									ia.notifyDataSetChanged();
								}
							});
					}
				});
	}

	static class ViewHolder {
		protected TextView title;
		protected ImageView postImg;
		protected ImageView userImg;
		protected TextView lid;
		protected TextView ts;
		protected TextView replyCount;
	}

}
