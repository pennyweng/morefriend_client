package com.jookershop.linefriend.service;

import java.io.InputStream;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.friend.FriendItem;
import com.jookershop.linefriend.msg.MessageItem;
import com.jookershop.linefriend.util.AccountUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

public class PlayReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		showNotification(context);
	}

	public void showNotification(final Context context) {
		String uid = URLEncoder.encode(AccountUtil.getUid(context));
		String url = Constants.BASE_URL + "new_msg/notifiy?uid=" + uid;
		Log.d(Constants.TAG, "notifiy users " + url);
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeJSONObject(ahg,
				new AsyncHttpClient.JSONObjectCallback() {
					@Override
					public void onCompleted(Exception e,
							AsyncHttpResponse response, JSONObject jo) {
						if (e != null) {
							e.printStackTrace();
							return;
						}

						try {
							if(jo.has("msg")) {
							MessageItem fi = new MessageItem(jo
									.getString("msg"),
									jo.getString("from"), jo
											.getString("fromLid"), jo
											.getString("to"), jo
											.getString("toLid"), jo
											.getLong("ts"));
							
								String msg = "";
								if(fi.getFromLid() != null && fi.getFromLid().length() > 0)
								msg = fi.getFromLid() + "傳送訊息給您";
								else msg = "某人傳送訊息給您";
								String url = Constants.IMAGE_BASE_URL + "account/image?uid=" + fi.getFromId();
								
								sendNotification(msg, url, context);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
	}
	
    public void sendNotification(String msg, String url, Context context )
    {
    	PendingIntent pIntent = PendingIntent.getActivity(context, 0, 
    			new Intent(context, MainActivity.class), 0);

        Notification.Builder n1  = new Notification.Builder(context)
        .setContentTitle("Line分類交友")
        .setContentText(msg)
        .setSmallIcon(R.drawable.logo)
        .setContentIntent(pIntent)
        .setVibrate(new long[] { 800, 800, 800, 800 })
        .setAutoCancel(true);
        
        if(!url.equals("")) {
	        Bitmap mIcon11 = null;
	        try {
	          InputStream in = new java.net.URL(url).openStream();
	          mIcon11 = BitmapFactory.decodeStream(in);
	          if(mIcon11 != null) {
	        	  n1.setLargeIcon(big(mIcon11));
	          }
	        } catch (Exception e) {
	            Log.e("DownloadImageTask Error", e.getMessage());
	            e.printStackTrace();
	        }
        }
    	
    	Notification n = n1.getNotification();
    	NotificationManager notificationManager = 
    	  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    	notificationManager.notify(0, n);  

    } 	
    
    private static Bitmap big(Bitmap bitmap) {
  	  Matrix matrix = new Matrix(); 
  	  matrix.postScale(0.6f,0.6f); //长和宽放大缩小的比例
  	  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
  	  return resizeBmp;
  }    
}
