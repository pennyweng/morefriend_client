package com.jookershop.linefriend.game;

import org.json.JSONException;
import org.json.JSONObject;

public class RaceItem {
	
	private String uid;
	private String lid;
	private String data;
	private String pid;
	private long ts;
	private Boolean withPC;
	private int replyCount;
	
	public static RaceItem genPostItem( JSONObject ob) {
		RaceItem pi = new RaceItem();
		try {
			pi.setUid(ob.getString("uid"));			
			pi.setLid(ob.getString("lid"));
			pi.setData(ob.getString("data"));
			pi.setPid(ob.getString("pid"));
			pi.setTs(ob.getLong("ts"));
			pi.setWithPC(ob.getBoolean("with_pic"));
			if(ob.has("reply_count"))
			pi.setReplyCount(ob.getInt("reply_count"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}
	
	
	
	public int getReplyCount() {
		return replyCount;
	}



	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}



	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	public Boolean getWithPC() {
		return withPC;
	}
	public void setWithPC(Boolean withPC) {
		this.withPC = withPC;
	}
	
	
	
}
