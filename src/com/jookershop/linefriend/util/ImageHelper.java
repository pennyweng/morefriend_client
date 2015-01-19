package com.jookershop.linefriend.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

import com.jookershop.linefriend3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageHelper {
	public static 	DisplayImageOptions	emptyOptions = new DisplayImageOptions.Builder()
//	.showImageOnLoading(R.drawable.emptyhead)
//	.showImageForEmptyUri(R.drawable.noimage)
//	.showImageOnFail(R.drawable.noimage)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.resetViewBeforeLoading(true)
	.build();
	
	public static 	DisplayImageOptions	emptyHeadOptions = new DisplayImageOptions.Builder()
//	.showImageOnLoading(R.drawable.emptyhead)
	.showImageForEmptyUri(R.drawable.emptyhead)
	.showImageOnFail(R.drawable.emptyhead)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.resetViewBeforeLoading(true)
	.build();
	
	public static int getHeight(Context context, int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());		
	}	
	
}
