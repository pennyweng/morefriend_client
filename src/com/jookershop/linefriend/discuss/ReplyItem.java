package com.jookershop.linefriend.discuss;

import org.json.JSONException;
import org.json.JSONObject;

public class ReplyItem {
	private String uid;
	private String lid;
	private String data;
	private String pid;
	private long ts;
	private String rid;
	
	public static ReplyItem genReplyItem(JSONObject ob ) {
		ReplyItem ri = new ReplyItem();
		try {
			ri.setUid(ob.getString("uid"));
			ri.setLid(ob.getString("lid"));
			ri.setData(ob.getString("data"));
			ri.setPid(ob.getString("pid"));
			ri.setTs(ob.getLong("ts"));
			ri.setRid(ob.getString("rid"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ri;
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
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	
	
}
