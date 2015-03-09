package com.jookershop.linefriend.discuss;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend4.R;
import com.jookershop.linefriend.account.AccountActivity;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.FileBody;

import eu.janmuller.android.simplecropimage.CropImage;

public class CreateDiscuss1Activity extends Activity {
	private ImageView mImageView;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo1.jpg";
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	private File mFileTemp;

	private SharedPreferences sp;
	private String categoryId;
	private int categoryType;
	private String[] lunch = { "欣賞電影", "逛書店", "聽音樂", "逛街購物", "運動健身", "工作賺錢", "打屁聊天", "畫畫", "旅遊", "吃遍美食", "遊戲", "彩妝", "手作品", "唱歌", "其它" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
//	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
//		this.getActionBar().hide();
		setContentView(R.layout.create_discuss1);
		sp = getSharedPreferences("linefriend", Context.MODE_APPEND);

		final Spinner spinner = (Spinner) this.findViewById(R.id.spinner1);
		ArrayAdapter lunchList = new ArrayAdapter<String>(CreateDiscuss1Activity.this,R.layout.spinner_item, lunch);
		spinner.setAdapter(lunchList);
		
		categoryType = Constants.TYPE_INTEREST;

//		RelativeLayout rl = (RelativeLayout) this.findViewById(R.id.rl);

		final TextView data = (TextView) this.findViewById(R.id.editText1);

		Button bt = (Button) this.findViewById(R.id.Button03);
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		final Button saveBt = (Button) this.findViewById(R.id.Button02);
		saveBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (data.getText().toString().length() <= 0) {
					Message.ShowMsgDialog(CreateDiscuss1Activity.this,
							"請輸入您想說的話!");
				} else if(!sp.contains(Constants.LINE_STORE_KEY)) {
					Message.ShowMsgDialog(CreateDiscuss1Activity.this,
							"請先填寫右上方的基本資料!");				
				} else {
					saveBt.setEnabled(false);
					categoryId = Constants.INTEREST_IDS[spinner.getSelectedItemPosition()];
					
					String uid = URLEncoder.encode(AccountUtil
							.getUid(CreateDiscuss1Activity.this));
					String lineId = URLEncoder.encode(sp.getString(
							Constants.LINE_STORE_KEY, ""));
					String postData = URLEncoder.encode(data.getText()
							.toString());

					String url = Constants.BASE_URL + "post/create?uid=" + uid
							+ "&lid=" + lineId + "&data=" + postData
							+ "&categoryType=" + categoryType + "&categoryId="
							+ categoryId + "&withPic=" + hasImage();

					Log.d(Constants.TAG, "insert url " + url);
					AsyncHttpPost post = new AsyncHttpPost(url);

					if (hasImage()) {
						AsyncHttpRequestBody body = new FileBody(mFileTemp);
						post.setBody(body);
					}

					AsyncHttpClient.getDefaultInstance().executeString(post,
							new AsyncHttpClient.StringCallback() {


								@Override
								public void onCompleted(final Exception e,
										AsyncHttpResponse source, final String result) {
									if(CreateDiscuss1Activity.this != null)
									CreateDiscuss1Activity.this
											.runOnUiThread(new Runnable() {
												public void run() {
													
													saveBt.setEnabled(true);
													mFileTemp.delete();
													if (e != null || (result != null && result.indexOf("error") != -1)) {
														Message.ShowMsgDialog(
																CreateDiscuss1Activity.this,
																"Opps....發生錯誤, 請稍後再試！");
														if(e != null) e.printStackTrace();
														return;
													}
													
													Builder MyAlertDialog = new AlertDialog.Builder(
															CreateDiscuss1Activity.this);
													MyAlertDialog
															.setMessage("建立成功");
													DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int which) {
															onBackPressed();
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

			}
		});

		final String[] items = new String[] { "用相機拍新照片", "從相簿取得照片" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("選擇照片");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) { // pick from
																	// camera
				if (item == 0) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					try {
						Uri mImageCaptureUri = null;
						String state = Environment.getExternalStorageState();
						if (Environment.MEDIA_MOUNTED.equals(state)) {
							mImageCaptureUri = Uri.fromFile(mFileTemp);
						}
						intent.putExtra(
								android.provider.MediaStore.EXTRA_OUTPUT,
								mImageCaptureUri);
						intent.putExtra("return-data", true);
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {

						e.printStackTrace();
					}
				} else { // pick from file
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent,
							"Complete action using"), PICK_FROM_FILE);
				}
			}
		});

		final AlertDialog dialog = builder.create();

		mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

	}

	private boolean hasImage() {
		return (mFileTemp.exists() && mFileTemp.length() > 100);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			startCropImage();
			break;
		case PICK_FROM_FILE:
			InputStream inputStream;
			try {
				inputStream = getContentResolver().openInputStream(
						data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				startCropImage();
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;

		case CROP_FROM_CAMERA:
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {

				return;
			}

			Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 35, stream);
			byte[] byteArray = stream.toByteArray();

			try {
				FileOutputStream fo = new FileOutputStream(mFileTemp.getPath());
				fo.write(byteArray);
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			mImageView.setImageBitmap(bitmap);
			break;
		}
	}

	private void startCropImage() {

		Intent intent = new Intent(this, CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 4);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, CROP_FROM_CAMERA);
	}

	public static void copyStream(InputStream input, OutputStream output)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

}
