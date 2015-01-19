package com.jookershop.linefriend.account;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;

import com.jookershop.linefriend.Constants;
import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend3.R;
import com.jookershop.linefriend.util.AccountUtil;
import com.jookershop.linefriend.util.Message;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.FileBody;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import eu.janmuller.android.simplecropimage.CropImage;

public class AccountActivity extends Activity {
	private ImageView mImageView;
	public static final String TEMP_PHOTO_FILE_NAME = "linefriend_photo.jpg";
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	private File mFileTemp;
	private EditText editText1;
//	private EditText editText2;
	
	private GridView interestGV;
	private GridView placeGV;
	private GridView careerGV;
	private GridView oldGV;
	private GridView calGV;	
	private GridView motionGV;
	
	private SharedPreferences sp;
	private RadioButton man;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.create_account);
		sp = getSharedPreferences("linefriend", Context.MODE_APPEND);

		Log.d(Constants.TAG, "isValidLineId1()" + isValidLineId1());
		
		String [] itia = new String []{};
		if(sp.contains(Constants.INTEREST_STORE_KEY))
			itia = sp.getString(Constants.INTEREST_STORE_KEY, "").split(",");
		interestGV = (GridView) this.findViewById(R.id.gridView1);
		interestGV.setAdapter(new AccountAdapter(this, Constants.INTEREST_TITLES,
				Constants.INTEREST_THUMBNAIL_IDS, Constants.INTEREST_COLOR, 
				Constants.INTEREST_IDS, itia));

		String [] itib = new String []{};
		if(sp.contains(Constants.PLACE_STORE_KEY))
			itib = sp.getString(Constants.PLACE_STORE_KEY, "").split(",");
		placeGV = (GridView) this.findViewById(R.id.placegv);
		placeGV.setAdapter(new AccountAdapter(this, Constants.PLACE_TITLES,
				Constants.PLACE_THUMBNAIL_IDS, Constants.PLACE_COLOR, 
				Constants.PLACE_IDS, itib));

		String [] itic = new String []{};
		if(sp.contains(Constants.CAREER_STORE_KEY))
			itic = sp.getString(Constants.CAREER_STORE_KEY, "").split(",");
		careerGV = (GridView) this.findViewById(R.id.careergv);
		careerGV.setAdapter(new AccountAdapter(this, Constants.CAREER_TITLES,
				Constants.CAREER_THUMBNAIL_IDS, Constants.CAREER_COLOR, 
				Constants.CAREER_IDS, itic));

		String [] itid = new String []{};
		if(sp.contains(Constants.OLD_STORE_KEY))
			itid = sp.getString(Constants.OLD_STORE_KEY, "").split(",");
		oldGV = (GridView) this.findViewById(R.id.oldgv);
		oldGV.setAdapter(new AccountAdapter(this, Constants.OLD_TITLES,
				Constants.OLD_THUMBNAIL_IDS, Constants.OLD_COLOR, 
				Constants.OLD_IDS, itid));

		String [] iticl = new String []{};
		if(sp.contains(Constants.CONSTELLATION_STORE_KEY))
			iticl = sp.getString(Constants.CONSTELLATION_STORE_KEY, "").split(",");
		calGV = (GridView) this.findViewById(R.id.calgv);
		calGV.setAdapter(new AccountAdapter(this, Constants.CONSTELLATION_TITLES,
				Constants.CONSTELLATION_THUMBNAIL_IDS, Constants.CONSTELLATION_COLOR, 
				Constants.CONSTELLATION_IDS, iticl));
		
		String [] itcdl = new String []{};
		if(sp.contains(Constants.MOTION_STORE_KEY))
			itcdl = sp.getString(Constants.MOTION_STORE_KEY, "").split(",");
		motionGV = (GridView) this.findViewById(R.id.motiongv);
		motionGV.setAdapter(new AccountAdapter(this, Constants.MOTION_TITLES,
				Constants.MOTION_IDS_THUMBNAIL_IDS, Constants.MOTION_COLOR, 
				Constants.MOTION_IDS, itcdl));
		
		
		Button bt = (Button) this.findViewById(R.id.Button03);
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(AccountActivity.this, MainActivity.class);
				AccountActivity.this.startActivity(it);
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
		
		
		editText1 = (EditText) this.findViewById(R.id.editText1);
		
		if(sp.contains(Constants.LINE_STORE_KEY)) {
			editText1.setText(sp.getString(Constants.LINE_STORE_KEY, ""));
			ImageLoader.getInstance().displayImage(Constants.IMAGE_BASE_URL
					+ "account/image?uid=" + sp.getString("uid", ""), mImageView, MainActivity.emptyHeadOptions,
					new SimpleImageLoadingListener(), null);			
		}
//		if(sp.contains(Constants.NICKNAME_STORE_KEY)) {
//			editText2.setText(sp.getString(Constants.NICKNAME_STORE_KEY, ""));
//		}
		
//		if(mFileTemp.exists() && mFileTemp.length() > 100) {
////			BitmapFactory.Options options = new BitmapFactory.Options();
////			options.inJustDecodeBounds = true;
//
//			Bitmap bitmap1 = BitmapFactory.decodeFile(mFileTemp.getPath());
//			mImageView.setImageBitmap(bitmap1);
//		}
		
		final CheckBox cb = (CheckBox) this.findViewById(R.id.checkBox1);
		cb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cb.isChecked()) {
					editText1.setText("暫時隱藏");
					editText1.setEnabled(false);
				} else {
					editText1.setEnabled(true);
					editText1.setText("");
//					editText1.setText(sp.getString(Constants.LINE_STORE_KEY, ""));		
				}			
			}
		});
		
		boolean isHide = sp.getBoolean(Constants.HIDE_STORE_KEY, false);
		if(isHide) {
			editText1.setText("暫時隱藏");
			editText1.setEnabled(false);
			cb.setChecked(true);
		} else {
			cb.setChecked(false);
			editText1.setEnabled(true);
		}
		
		
		Button delBt = (Button) this.findViewById(R.id.deletebt);
		delBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uid = URLEncoder.encode(AccountUtil.getUid(AccountActivity.this));
				String url = Constants.BASE_URL + "account/delete?uid=" + uid;
				AsyncHttpGet ahg = new AsyncHttpGet(url);
				AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
					
					@Override
				    public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
				        if (e != null) {
				        	Message.ShowMsgDialog(AccountActivity.this, "Opps....發生錯誤, 請稍後再試");
				            e.printStackTrace();
				            return;
				        }
						sp.edit().remove(Constants.INTEREST_STORE_KEY).commit();
						sp.edit().remove(Constants.PLACE_STORE_KEY).commit();
						sp.edit().remove(Constants.CAREER_STORE_KEY).commit();
						sp.edit().remove(Constants.OLD_STORE_KEY).commit();
						sp.edit().remove(Constants.CONSTELLATION_STORE_KEY).commit();
						sp.edit().remove(Constants.MOTION_STORE_KEY).commit();
						sp.edit().remove(Constants.LINE_STORE_KEY).commit();
						sp.edit().remove(Constants.NICKNAME_STORE_KEY).commit();
						sp.edit().remove(Constants.SEX_STORE_KEY).commit();
						cleanup(AccountActivity.this, 1l);

				        AccountActivity.this.runOnUiThread( new Runnable() {
							public void run() {
								Builder MyAlertDialog = new AlertDialog.Builder(AccountActivity.this);
								MyAlertDialog.setMessage("刪除成功");
								DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent it = new Intent();
										it.setClass(AccountActivity.this, MainActivity.class);
										AccountActivity.this.startActivity(it);
									}
								};
								MyAlertDialog.setNeutralButton("確定", OkClick);
								MyAlertDialog.show();
								
							}
						});
						
				    }
				});		
				
			}
		});
		
		
		Button saveBt = (Button) this.findViewById(R.id.Button02);
		saveBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String gi = generateCategoryId(interestGV);
				String gp = generateCategoryId(placeGV);
				String gc = generateCategoryId(careerGV);
				String go = generateCategoryId(oldGV);
				String gcal = generateCategoryId(calGV);
				
				if(!hasImage()) {
					Message.ShowMsgDialog(AccountActivity.this, "請務必選擇一張照片, 讓更多人認識您!");
				} else if(!hasLineId()) {
					Message.ShowMsgDialog(AccountActivity.this, "請務必輸入您的Line的ID, 讓更多人認識您!");
				} else if(!isValidLineId()) {
						Message.ShowMsgDialog(AccountActivity.this, "您輸入的Line ID格式不正確, 請再次確認!");
				} else if(gi.split(",").length > 3 
						|| gp.split(",").length > 3 
						|| gc.split(",").length > 3 
						|| go.split(",").length > 2 
						|| gcal.split(",").length > 1) {
					Message.ShowMsgDialog(AccountActivity.this, "請檢查基本資料的填寫數量：我的興趣最多填寫三個，我的生活地點最多三個，我的職業最多三個，我的年紀範圍最多兩個，我的星座最多一個!");
				} else {
				String uid = URLEncoder.encode(AccountUtil.getUid(AccountActivity.this));
				String lineId = URLEncoder.encode(editText1.getText().toString());
				String interests = URLEncoder.encode(gi);
				String places = URLEncoder.encode(gp);
				String careers = URLEncoder.encode(gc);
				String olds= URLEncoder.encode(go);
				String cals= URLEncoder.encode(gcal);
				String mos= URLEncoder.encode(generateCategoryId(motionGV));				
				
				sp.edit().putString(Constants.INTEREST_STORE_KEY, generateCategoryId(interestGV)).commit();
				sp.edit().putString(Constants.PLACE_STORE_KEY, generateCategoryId(placeGV)).commit();
				sp.edit().putString(Constants.CAREER_STORE_KEY, generateCategoryId(careerGV)).commit();
				sp.edit().putString(Constants.OLD_STORE_KEY, generateCategoryId(oldGV)).commit();
				sp.edit().putString(Constants.CONSTELLATION_STORE_KEY, generateCategoryId(calGV)).commit();
				sp.edit().putString(Constants.MOTION_STORE_KEY , generateCategoryId(motionGV)).commit();
				
//				mos
				sp.edit().putString(Constants.LINE_STORE_KEY, editText1.getText().toString()).commit();
//				sp.edit().putString(Constants.NICKNAME_STORE_KEY, editText2.getText().toString()).commit();
				
				if(cb.isChecked()) {
					sp.edit().putBoolean(Constants.HIDE_STORE_KEY, true).commit();
				} else sp.edit().putBoolean(Constants.HIDE_STORE_KEY, false).commit();
				
				String sex = "M";
				if(man.isChecked()) {
					sp.edit().putString(Constants.SEX_STORE_KEY, "M").commit();
					sex = "M";
				} else { 
					sp.edit().putString(Constants.SEX_STORE_KEY, "F").commit();
					sex = "F";
				}
				
				String url = Constants.BASE_URL + "account/create?uid=" + uid 
//						+ "&nickname=" + nickname 
						+ "&lineId=" + lineId 
						+ "&interests=" + interests 
						+ "&places="+ places 
						+ "&careers=" + careers 
						+ "&olds=" + olds
						+ "&constellations=" + cals
						+ "&motions=" + mos
						+ "&s=" + sex;
				
				Log.d(Constants.TAG, "insert url " + url );
				AsyncHttpPost post = new AsyncHttpPost(url);
				AsyncHttpRequestBody body = new FileBody(mFileTemp);
				post.setBody(body);
				
				AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {

					  @Override
					    public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
					        if (e != null) {
					        	Message.ShowMsgDialog(AccountActivity.this, "Opps....發生錯誤, 請稍後再試！");
					            e.printStackTrace();
					            return;
					        }
//					        cleanup(AccountActivity.this, 1l);
					        AccountActivity.this.runOnUiThread( new Runnable() {
								public void run() {
									Builder MyAlertDialog = new AlertDialog.Builder(AccountActivity.this);
									MyAlertDialog.setMessage("建立成功");
									DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											
											if(MainActivity.imageLoader != null) {
												MainActivity.imageLoader.clearMemoryCache();
												MainActivity.imageLoader.clearDiskCache();
											}
											
											Intent it = new Intent();
											it.setClass(AccountActivity.this, MainActivity.class);
											AccountActivity.this.startActivity(it);											
										}
									};
									MyAlertDialog.setNeutralButton("確認", OkClick);
									MyAlertDialog.show();
								}
							});
					    }
				});
				}
			}
		});
		
		
      	man = (RadioButton) findViewById(R.id.radioButton1);
      	final RadioButton women = (RadioButton) findViewById(R.id.RadioButton01);
      	
      	man.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(man.isChecked()) {
					women.setChecked(false);
				} else {
					women.setChecked(true);
				}
			}
		});

      	women.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(women.isChecked()) {
					man.setChecked(false);
				} else {
					man.setChecked(true);
				}
			}
		});
      	
      	if(sp.contains(Constants.SEX_STORE_KEY)) {
      		if(sp.getString(Constants.SEX_STORE_KEY, "") == "M") {
      			man.setChecked(true);
      			women.setChecked(false);
      		} else if(sp.getString(Constants.SEX_STORE_KEY, "") == "F") {
      			women.setChecked(true);
      			man.setChecked(false);
      		}
      	}
      	
      	
      	
	}

	private boolean hasImage() {
		return (mFileTemp.exists() && mFileTemp.length() > 100);		
	}
	
	private boolean hasLineId() {
		return (editText1.getText().toString() != null && editText1.getText().toString().length() > 0); 		
	}
	
	private boolean isValidLineId() {
		String lineId = editText1.getText().toString();
		if(lineId.equals("暫時隱藏")) return true;
		String regex = "([a-zA-Z0-9_-]*\\.*[a-zA-Z0-9_-]*)";
		Matcher matcher = Pattern.compile( regex ).matcher(lineId);
		return matcher.matches();
	}
	
	private boolean isValidLineId1() {
		String lineId = "0908812244";
		if(lineId.equals("暫時隱藏")) return true;
		String regex = "([a-zA-Z0-9_-]*\\.*[a-zA-Z0-9_-]*)";
		Matcher matcher = Pattern.compile( regex ).matcher(lineId);
		return matcher.matches();
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

		intent.putExtra(CropImage.ASPECT_X, 10);
		intent.putExtra(CropImage.ASPECT_Y, 10);

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
	
	
	public String generateCategoryId( GridView gv) {
		String ret = "";
		ListAdapter la = gv.getAdapter();
		for(int index = 0; index < la.getCount(); index ++) {
			LikeItem li = (LikeItem)la.getItem(index);
			if(li.isSelected()) {
				if(ret == "") ret = li.getCategoryId();
				else ret = ret + "," + li.getCategoryId();
			} 
		}
		
		return ret;
	}
	
	public String generateCategoryName( GridView gv) {
		String ret = "";
		ListAdapter la = gv.getAdapter();
		for(int index = 0; index < la.getCount(); index ++) {
			LikeItem li = (LikeItem)la.getItem(index);
			if(li.isSelected()) {
				if(ret == "") ret = li.getName();
				else ret = ret + "," + li.getName();
			} 
		}
		
		return ret;
	}	
	public static void cleanup(final Context context, long age) {
        try {
            // purge any *.urlimage files over a week old
            final String[] files = context.getFilesDir().list();
            if (files == null) {
                return;
            }
            for (final String file : files) {
                if (!file.endsWith(".urlimage")) {
                    continue;
                }

                final File f = new File(context.getFilesDir().getAbsolutePath() + '/' + file);
                if (System.currentTimeMillis() > f.lastModified() + age) {
                    f.delete();
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }	
}
