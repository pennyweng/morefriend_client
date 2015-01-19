package com.jookershop.linefriend.gcm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.util.AccountUtil;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

public class GCMTask {
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    String SENDER_ID = "54829273586";	
	
    
//    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();

    public void launchGCM(Context context) {
//		GCMTask task = new GCMTask();
        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices(context)) {
            String regid = getRegistrationId(context);
            Log.d(Constants.TAG, "regid is " + regid);
            if (regid.isEmpty()) {
                registerInBackground(context);
            } else {
            	sendRegistrationIdToBackend(context, regid);
            }
        } else {
            Log.i(Constants.TAG, "No valid Google Play Services APK found.");
        }    	
    	
    }
    
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, context,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(Constants.TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(Constants.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(Constants.TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(Constants.TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    public void registerInBackground(final Context context) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                    String regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(context, regid);

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences("linefriend",
                Context.MODE_PRIVATE);
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    public void sendRegistrationIdToBackend(Context context, String regid ) {
    	String uid = URLEncoder.encode(AccountUtil.getUid(context));
		String url = Constants.BASE_URL + "gcm/register?uid=" + uid 
				+ "&gcmId=" + regid;
		Log.d(Constants.TAG, "register url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
	
		    }
		});		
    }
    
    public void sendUnRegistrationIdToBackend(Context context) {
    	String uid = URLEncoder.encode(AccountUtil.getUid(context));
		String url = Constants.BASE_URL + "gcm/unregister?uid=" + uid;
		Log.d(Constants.TAG, "unregister url " + url );
		AsyncHttpGet ahg = new AsyncHttpGet(url);
		AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
		    @Override
		    public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
		    	if (e != null) {
		            e.printStackTrace();
		            return;
		        }
	
		    }
		});		
    }    
}
