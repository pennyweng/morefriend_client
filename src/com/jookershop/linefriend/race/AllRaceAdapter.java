package com.jookershop.linefriend.race;


import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jookershop.linefriend3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class AllRaceAdapter extends ArrayAdapter<GameItem> {
	private Context mContext;
	// public String [] colors ={"#A3A948"};
	// public String mainColor;
	private DisplayImageOptions options;
	// private String selectColor;
	private SharedPreferences sp;
	private ActiveRaceFragment fdf;
	

	public AllRaceAdapter(Context context, ArrayList<GameItem> interestItems,
			DisplayImageOptions options, ActiveRaceFragment fdf ) {
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
		final GameItem postItem = getItem(position);
		View rowView = convertView;
//		if (rowView == null) {
			rowView = LayoutInflater.from(getContext()).inflate(
					R.layout.race_menu_item, parent, false);
//			String a = "<h4><FONT COLOR=\"#FADD05\">活動辦法</FONT></h4>上傳一張自己與自己養的寵物的照片，讓LINE友們自行挑選最像的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FADD05\">活動期間</FONT></h4>開始期間：2010/11/15<br />結束期間：只有有作品到達到200人的留言，比賽就結束。如果一個月內，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FADD05\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FADD05\">注意事項</FONT></h4>1. 照片一定要包含本人與自己養的寵物，領獎時，會要求其他與它的合照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 使用不當方式留言者，一經發現，會立即刪除留言並且再也無法參與其他比賽。";			
			String a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張照片包含本人與自己養的寵物，讓LINE友們自行挑選最像的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：2010/11/15<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果一個月內，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定要包含本人與自己養的寵物，領獎時，會要求其他與它的合照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 本公司有權力調整或中止所有比賽。";
			TextView tv = (TextView)rowView.findViewById(R.id.textView4);
			

			TextView title = (TextView)rowView.findViewById(R.id.textView1);
			TextView go = (TextView)rowView.findViewById(R.id.textView3);
			go.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					((FragmentActivity)mContext).getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container,
							RaceFragment.newInstance())
							.addToBackStack("race").commit();
					
				}
			});
			
			
			ImageView iv = (ImageView) rowView
					.findViewById(R.id.imageView2);
			
			if(position % 3 == 0) {
//				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張照片包含本人與自己養的寵物，讓LINE友們自行挑選最像的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：2010/11/15<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果一個月內，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定要包含本人與自己養的寵物。領獎時，會要求其他與它的合照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽。";
				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張照片包含本人與自己養的寵物，讓LINE友們自行挑選最像的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：即日起<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果2014/12/31之前，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定要包含本人與自己養的寵物。領獎時，會要求其他與它的合照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽，參加者不得有異議。";
				iv.setImageResource(R.drawable.dog);
				title.setText("比誰跟自己的寵物最有親子臉");
				tv.setText(Html.fromHtml(a));
			} else if(position % 3 == 1) {
//				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張自己大頭貼，讓LINE友們自行挑選最美麗眼睛的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：2010/11/15<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果一個月內，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定是本人並包含眼睛部位。領獎時，會要求其他生活照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽。";
				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張自己大頭貼，讓LINE友們自行挑選最美麗眼睛的照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：即日起<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果2014/12/31之前，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定是本人並包含眼睛部位。領獎時，會要求其他生活照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽，參加者不得有異議。";
				iv.setImageResource(R.drawable.eye);
				title.setText("比誰的眼睛看起來最漂亮");
				tv.setText(Html.fromHtml(a));
			}else if(position % 3 == 2) {
//				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張穿著最時尚的本人照片，讓LINE友們自行挑選最時尚的服裝照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：2010/11/15<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果一個月內，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定是本人。領獎時，會要求其他生活照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽。";
				a = "<h4><FONT COLOR=\"#FAF605\">活動辦法</FONT></h4>上傳一張穿著最時尚的本人照片，讓LINE友們自行挑選最時尚的服裝照片並留言，獲得的留言數超過設定目標並最多者，即是冠軍。<br /><h4><FONT COLOR=\"#FAF605\">活動期間</FONT></h4>開始期間：即日起<br />結束期間：只要有作品到達到200人的留言，比賽就結束。如果2014/12/31之前，沒有任何作品能獲得200人以上的留言，比賽冠軍從缺。<br /><h4><FONT COLOR=\"#FAF605\">活動獎勵</FONT></h4>1. 得到最多留言的參加者，就是本次的冠軍，即可獲得7-11的100元禮券。<br />2. 在冠軍的作品中，會抽出一名留言者，也可獲得7-11的100元禮券。<br /><h4><FONT COLOR=\"#FAF605\">注意事項</FONT></h4>1. 照片一定是本人。領獎時，會要求其他生活照。若發現有問題，立即取消資格並且再也無法參與其他比賽。<br />2. 留言若含有不雅文字、廣告文或不適當文字，一經發現，會立即刪除留言並且再也無法參與其他比賽。<br />3. 獲得獎勵者，請主動加入版主LINE帳號ahappychat，並告知得獎，以利後續獎品寄送作業。<br />4. 本公司有權力調整或中止所有比賽，參加者不得有異議。";
				iv.setImageResource(R.drawable.close);
				title.setText("比誰的穿著最時尚");
				tv.setText(Html.fromHtml(a));
			}
			
			
			//			ViewHolder viewHolder = new ViewHolder();
//			viewHolder.title = (TextView) rowView.findViewById(R.id.textView1);
//			viewHolder.postImg = (ImageView) rowView
//					.findViewById(R.id.imageView2);
//			viewHolder.lid = (TextView) rowView.findViewById(R.id.textView3);
//			viewHolder.replyCount = (TextView) rowView
//					.findViewById(R.id.textView2);
//			viewHolder.userImg = (ImageView) rowView
//					.findViewById(R.id.imageView1);
//			viewHolder.ts = (TextView) rowView.findViewById(R.id.TextView01);
//			rowView.setTag(viewHolder);
//		}

//		TextView delTv = (TextView) rowView.findViewById(R.id.textView4);
//		if (Constants.IS_SUPER || postItem.getUid().equals(AccountUtil.getUid(mContext))) {
//			delTv.setVisibility(View.VISIBLE);
//			delTv.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					String uid = URLEncoder.encode(AccountUtil.getUid(mContext));
//					String pid = URLEncoder.encode(postItem.getPid());
//
//					String url = Constants.BASE_URL + "post/del?uid=" + uid
//							+ "&categoryType="
//							+ Constants.TYPE_INTEREST + "&pid=" + pid;
//
//					Log.d(Constants.TAG, "del post url " + url);
//					AsyncHttpGet get = new AsyncHttpGet(url);
//
//					AsyncHttpClient.getDefaultInstance().executeString(get,
//							new AsyncHttpClient.StringCallback() {
//
//								@Override
//								public void onCompleted(Exception e,
//										AsyncHttpResponse source, String result) {
//									if (e != null) {
//										Message.ShowMsgDialog(mContext,
//												"Opps....發生錯誤, 請稍後再試！");
//										e.printStackTrace();
//										return;
//									}
//									((Activity) mContext)
//											.runOnUiThread(new Runnable() {
//												public void run() {
//													Builder MyAlertDialog = new AlertDialog.Builder(
//															mContext);
//													MyAlertDialog
//															.setMessage("刪除成功");
//													DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//														public void onClick(
//																DialogInterface dialog,
//																int which) {
//															fdf.refresh();
//														}
//													};
//													MyAlertDialog
//															.setNeutralButton(
//																	"確認",
//																	OkClick);
//													MyAlertDialog.show();
//												}
//											});
//								}
//							});
//
//				}
//			});
//		} else {
//			delTv.setVisibility(View.INVISIBLE);
//		}
//		
//		
//		
//		
//		ViewHolder holder = (ViewHolder) rowView.getTag();
//		holder.title.setText(postItem.getData());
//		holder.lid.setText(postItem.getLid());
//		int tt = (int) (System.currentTimeMillis() - postItem.getTs()) / 60000;
//		if (tt <= 60)
//			holder.ts.setText(tt + "分鐘前");
//		else {
//			int hh = tt / 60;
//			holder.ts.setText(hh + "小時前");
//		}
//		
//		
//		if (postItem.getWithPC()) {
//			holder.postImg.getLayoutParams().height = ImageHelper.getHeight(mContext, 160);
//			holder.postImg.setVisibility(View.VISIBLE);			
//			UrlImageViewHelper.setUrlDrawable(holder.postImg,
//					Constants.IMAGE_BASE_URL + "post/image?pid=" + postItem.getPid());
//			holder.title.setTextSize(14);
//			holder.title.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
//			holder.title.setGravity(Gravity.CENTER);
//		} else {
//			holder.postImg.getLayoutParams().height = ImageHelper.getHeight(mContext, 110);
//			holder.postImg.setImageResource(android.R.color.transparent);
//			holder.postImg.setImageDrawable(null);
//			holder.postImg.setVisibility(View.INVISIBLE);
//			holder.title.setTextSize(15);
//			holder.title.setGravity(Gravity.CENTER);
//		}
//		holder.postImg.requestLayout();
//
//		
//		UrlImageViewHelper.setUrlDrawable(
//				holder.userImg,
//				Constants.IMAGE_BASE_URL + "account/image?uid="
//						+ postItem.getUid(), R.drawable.emptyhead);
//		holder.userImg.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				AccountUtil.showInfo(mContext, postItem.getUid());
//			}
//		});
//
//		holder.replyCount.setText(postItem.getReplyCount() + "人回應");
//		rowView.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				final AlertDialog.Builder builder = new Builder(mContext);
//				View dialoglayout = LayoutInflater.from(getContext()).inflate(
//						R.layout.show_discuss, null);
//
//				ImageView postImg = (ImageView) dialoglayout
//						.findViewById(R.id.imageView1);
//				if (postItem.getWithPC()) {
//					UrlImageViewHelper.setUrlDrawable(
//							postImg,
//							Constants.IMAGE_BASE_URL + "post/image?pid="
//									+ postItem.getPid());
//				} else {
//					RelativeLayout rl1 = (RelativeLayout) dialoglayout
//							.findViewById(R.id.rl1);
//					rl1.setLayoutParams(new LayoutParams(
//							LayoutParams.MATCH_PARENT, 50));
//				}
//
//				TextView title = (TextView) dialoglayout
//						.findViewById(R.id.textView2);
//				title.setText(postItem.getData());
//				TextView name = (TextView) dialoglayout
//						.findViewById(R.id.textView1);
//				name.setText(postItem.getLid());
//
//				ImageView userImg = (ImageView) dialoglayout
//						.findViewById(R.id.imageView2);
//				UrlImageViewHelper.setUrlDrawable(userImg,
//						Constants.IMAGE_BASE_URL + "account/image?uid="
//								+ postItem.getUid(), R.drawable.emptyhead);
//
//				userImg.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						AccountUtil.showInfo(mContext, postItem.getUid());
//
//					}
//				});
//
//				final EditText data = (EditText) dialoglayout
//						.findViewById(R.id.editText1);
//				final Button save = (Button) dialoglayout
//						.findViewById(R.id.Button02);
//
//				final ListView interestGV = (ListView) dialoglayout
//						.findViewById(R.id.listView1);
//				interestGV.setAdapter(new ReplyItemAdapter(mContext,
//						new ArrayList<ReplyItem>()));
//				final TextView countTv = (TextView) dialoglayout
//						.findViewById(R.id.textView3);
//				loadItmes(postItem.getPid(), interestGV, countTv);
//
//				builder.setView(dialoglayout);
//				final AlertDialog a = builder.create();
//
//				save.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						if(!sp.contains(Constants.LINE_STORE_KEY) || sp.getString(Constants.LINE_STORE_KEY, "") == "")
//							Message.ShowMsgDialog(mContext, "麻煩先完成基本資料設定" );						
//						else if (data.getText().toString().length() <= 0) {
//							Message.ShowMsgDialog(mContext, "請輸入留言!");
//						} else {
//							save.setEnabled(false);
//							// (uid : String, lid : String, data : String, pid :
//							// String)
//							String uid = URLEncoder.encode(AccountUtil
//									.getUid(mContext));
//							String lineId = URLEncoder.encode(sp.getString(
//									Constants.LINE_STORE_KEY, ""));
//							
//							String postData = URLEncoder.encode(data.getText()
//									.toString());
//							String pid = URLEncoder.encode(postItem.getPid());
//
//							String url = Constants.BASE_URL
//									+ "reply/create?uid=" + uid + "&lid="
//									+ lineId + "&data=" + postData + "&pid="
//									+ pid;
//
//							Log.d(Constants.TAG, "insert url " + url);
//							AsyncHttpGet get = new AsyncHttpGet(url);
//
//							AsyncHttpClient.getDefaultInstance().executeString(
//									get, new AsyncHttpClient.StringCallback() {
//
//										@Override
//										public void onCompleted(
//												final Exception e,
//												AsyncHttpResponse source,
//												String result) {
//											if(mContext != null)
//											((Activity) mContext)
//													.runOnUiThread(new Runnable() {
//														public void run() {
//															save.setEnabled(true);
//															if (e != null) {
//																Message.ShowMsgDialog(
//																		mContext,
//																		"Opps....發生錯誤, 請稍後再試！");
//																e.printStackTrace();
//																return;
//															}
//
//															Builder MyAlertDialog = new AlertDialog.Builder(
//																	mContext);
//															MyAlertDialog
//																	.setMessage("留言成功");
//															DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//																public void onClick(
//																		DialogInterface dialog,
//																		int which) {
//																	// onBackPressed();
//																	// a.dismiss();
//																	data.setText("");
//																	loadItmes(
//																			postItem.getPid(),
//																			interestGV,
//																			countTv);
//																	fdf.refresh(postItem.getPid());
//																}
//															};
//															MyAlertDialog
//																	.setNeutralButton(
//																			"確認",
//																			OkClick);
//															MyAlertDialog
//																	.show();
//														}
//													});
//										}
//									});
//						}
//
//					}
//				});
//
//				a.show();
//			}
//		});

		return rowView;
	}

//	public void loadItmes(String pid, final ListView interestGV,
//			final TextView countTv) {
//		String url = Constants.BASE_URL + "reply/search?pid=" + pid;
//		Log.d(Constants.TAG, "reply search url " + url);
//		AsyncHttpGet ahg = new AsyncHttpGet(url);
//		AsyncHttpClient.getDefaultInstance().executeJSONArray(ahg,
//				new AsyncHttpClient.JSONArrayCallback() {
//					@Override
//					public void onCompleted(Exception e,
//							AsyncHttpResponse response, JSONArray result) {
//						if (e != null) {
//							e.printStackTrace();
//							return;
//						}
//
//						final ArrayList<ReplyItem> res = new ArrayList<ReplyItem>();
//						for (int index = 0; index < result.length(); index++) {
//							try {
//								JSONObject jo = result.getJSONObject(index);
//								res.add(ReplyItem.genReplyItem(jo));
//							} catch (JSONException e1) {
//								e1.printStackTrace();
//							}
//						}
//						if (mContext != null)
//							((Activity) mContext).runOnUiThread(new Runnable() {
//								public void run() {
//									ReplyItemAdapter ia = (ReplyItemAdapter) interestGV
//											.getAdapter();
//									ia.clear();
//									ia.addAll(res);
//									countTv.setText(ia.getCount() + "則留言");
//									ia.notifyDataSetChanged();
//								}
//							});
//					}
//				});
//	}

	static class ViewHolder {
		protected TextView title;
		protected ImageView postImg;
		protected ImageView userImg;
		protected TextView lid;
		protected TextView ts;
		protected TextView replyCount;
	}

}
