package com.jookershop.linefriend.ad;

import com.jookershop.linefriend.MainActivity;
import com.jookershop.linefriend.util.AccountUtil;

import cn.aow.android.DAOW;
import android.content.Context;

public class DomobOffer {
	public static String PUBLISHID = "96ZJ08mAzeUWnwTCKH";
	
	public static void init(Context ctx){
		DAOW.getInstance(ctx).init(ctx , PUBLISHID, AccountUtil.getUid(ctx));
	}
	
	public static void show(Context ctx) {
		DAOW.getInstance(ctx).show(ctx);
	}
}
