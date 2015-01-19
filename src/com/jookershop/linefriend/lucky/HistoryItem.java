package com.jookershop.linefriend.lucky;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryItem {
	private long ts;
	private String uid;
	private String lid;
	private String gid;
	private String guess;
	private String desc;

	public static HistoryItem [] parse(JSONArray a) {
		ArrayList<HistoryItem> hi = new ArrayList<HistoryItem>();
		try {
			for(int index = 0; index < a.length(); index++) {
				HistoryItem aa = new HistoryItem();
				JSONObject tt = a.getJSONObject(index);
				if(tt.has("ts") && !tt.isNull("ts"))
					aa.setTs(tt.getLong("ts"));
				if(tt.has("uid") && !tt.isNull("uid"))
					aa.setUid(tt.getString("uid"));
				if(tt.has("nn") && !tt.isNull("nn"))
					aa.setGuess(tt.getString("nn"));
				if(tt.has("desc") && !tt.isNull("desc"))
					aa.setDesc(tt.getString("desc"));

				hi.add(aa);
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return hi.toArray(new HistoryItem[]{});
	}

	public long getTs() {
		return ts;
	}


	public void setTs(long ts) {
		this.ts = ts;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getGid() {
		return gid;
	}


	public void setGid(String gid) {
		this.gid = gid;
	}


	public String getGuess() {
		return guess;
	}


	public void setGuess(String guess) {
		this.guess = guess;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
