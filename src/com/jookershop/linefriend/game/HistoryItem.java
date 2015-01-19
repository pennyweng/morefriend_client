package com.jookershop.linefriend.game;

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
	private String gtype;
	
	
	public static HistoryItem [] parse(JSONArray a, String gType) {
		ArrayList<HistoryItem> hi = new ArrayList<HistoryItem>();
		try {
			for(int index = 0; index < a.length(); index++) {
				HistoryItem aa = new HistoryItem();
				aa.setGtype(gType);
				
				JSONObject tt = a.getJSONObject(index);
				if(tt.has("ts") && !tt.isNull("ts"))
					aa.setTs(tt.getLong("ts"));
				if(tt.has("h") && !tt.isNull("h")) {
					String raw = tt.getString("h");
					String [] data = raw.split("##");
					aa.setUid(data[0]);
					aa.setGuess(data[1]);
					if(data.length > 2)
						aa.setDesc(data[2]);
					
					if(gType.equals(GameItem.TYPE_AB) && data.length > 3)
						aa.setGuess(data[1] + "\n" + data[3]);				
				}
				hi.add(aa);
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return hi.toArray(new HistoryItem[]{});
	}

	
	public String getGtype() {
		return gtype;
	}


	public void setGtype(String gtype) {
		this.gtype = gtype;
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
