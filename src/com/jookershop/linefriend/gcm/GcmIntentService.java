package com.jookershop.linefriend.gcm;

import java.io.InputStream;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        Log.d(Constants.TAG, "messageType " + messageType);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
        	Log.d(Constants.TAG, "Received: " + extras.toString());
        	
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server: " + extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
            	String [] data = extras.getString("message").split("##");
            	String img = "";
            	String msg = data[0];
            	if(data.length> 1) img = data[1]; 
            	
            	SharedPreferences sp = getSharedPreferences("linefriend", Context.MODE_APPEND);
        		boolean music = sp.getBoolean(Constants.NOTIFY_MUSIC, true);
        		boolean vir = sp.getBoolean(Constants.NOTIFY_VIR, false);
            	
        		if(sp.getBoolean(Constants.NOTIFY_STATUS, true)) {
        			sendNotification(msg, img, music, vir);
        		} else {
        			GCMTask task = new GCMTask();
        			task.sendUnRegistrationIdToBackend(this);        			
        		}
                
                Log.i(TAG, "Received: " +extras.getString("message"));
            }
        }
        
//        Log.d(Consts.TAG, "send notification" + extras.toString());
//        sendNotification("Received: " + extras.toString());
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
//    private void sendNotification(String msg) {
//        mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, Main1Activity.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
////        .setSmallIcon(R.drawable.ic_stat_gcm)
//        .setContentTitle("GCM Notification")
//        .setStyle(new NotificationCompat.BigTextStyle()
//        .bigText(msg))
//        .setContentText(msg);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//    }
    
    public void sendNotification(String msg, String url, boolean music, boolean vir )
    {
    	Intent aa = new Intent(this, MainActivity.class);
    	aa.putExtra("GoToMsg", true);
    	aa.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	
    	PendingIntent pIntent = PendingIntent.getActivity(this, 0, aa, PendingIntent.FLAG_UPDATE_CURRENT);
    	Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	
        Notification.Builder n1  = new Notification.Builder(this)
        .setContentTitle("分類交友")
        .setContentText(msg)
        .setSmallIcon(R.drawable.logo)
        .setContentIntent(pIntent)
//        .setSound(alarmSound)
//        .setVibrate(new long[] { 800, 800, 800, 800 })
        .setAutoCancel(true);
        if(music)
        	n1.setSound(alarmSound);
        if(vir) 
        	n1.setVibrate(new long[] { 500, 500});
        
        if(!url.equals("")) {
	        Bitmap mIcon11 = null;
	        try {
	          InputStream in = new java.net.URL(url).openStream();
	          mIcon11 = BitmapFactory.decodeStream(in);
	          if(mIcon11 != null) {
	        	  n1.setLargeIcon(big(this, mIcon11));
	          }
	        } catch (Exception e) {
	            Log.e("DownloadImageTask Error", e.getMessage());
	            e.printStackTrace();
	        }
        }
    	
    	Notification n = n1.getNotification();
    	NotificationManager notificationManager = 
    	  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    	notificationManager.notify(0, n);     	
    }        
    
    private static Bitmap big(Context mContext, Bitmap bitmap) {
		Resources res = mContext.getResources();
		int height = (int) res.getDimension(android.R.dimen.notification_large_icon_height);
		int width = (int) res.getDimension(android.R.dimen.notification_large_icon_width);    	
		return Bitmap.createScaledBitmap(bitmap, width, height, false); 
		
//    	  Matrix matrix = new Matrix(); 
//    	  matrix.postScale(3f,3f); //长和宽放大缩小的比例
//    	  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
//    	  return resizeBmp;
    }    
}
