package com.jookershop.linefriend.lucky;

import org.json.JSONException;
import org.json.JSONObject;

public class LuckyWinnerItem {

	private String uid;
	private String lkid;
	private String winNumber;
	private String desc;
	private int total;
	private String award;
	private long ts;
	private String lid;
	
	public String getUid() {
		return uid;
	}




	public void setUid(String uid) {
		this.uid = uid;
	}




	public String getLkid() {
		return lkid;
	}




	public void setLkid(String lkid) {
		this.lkid = lkid;
	}




	public String getWinNumber() {
		return winNumber;
	}




	public void setWinNumber(String winNumber) {
		this.winNumber = winNumber;
	}




	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}




	public int getTotal() {
		return total;
	}




	public void setTotal(int total) {
		this.total = total;
	}




	public String getAward() {
		return award;
	}




	public void setAward(String award) {
		this.award = award;
	}




	public long getTs() {
		return ts;
	}




	public void setTs(long ts) {
		this.ts = ts;
	}




	public String getLid() {
		return lid;
	}




	public void setLid(String lid) {
		this.lid = lid;
	}


	public static LuckyWinnerItem genPostItem( JSONObject jj) {
		LuckyWinnerItem pi = new LuckyWinnerItem();
		try {
				if(jj.has("uid") && !jj.isNull("uid"))
					pi.setUid(jj.getString("uid"));
				if(jj.has("nn") && !jj.isNull("nn"))
					pi.setWinNumber(jj.getString("nn"));
				if(jj.has("lid") && !jj.isNull("lid"))
					pi.setLid(jj.getString("lid"));				
				if(jj.has("desc") && !jj.isNull("desc"))
					pi.setDesc(jj.getString("desc"));	
				if(jj.has("award") && !jj.isNull("award"))
					pi.setAward(jj.getString("award"));					
				if(jj.has("total") && !jj.isNull("total"))
					pi.setTotal(jj.getInt("total"));	
				if(jj.has("ts") && !jj.isNull("ts"))
					pi.setTs(jj.getLong("ts"));	
				if(jj.has("lkid") && !jj.isNull("lkid"))
					pi.setLkid(jj.getString("lkid"));					
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}

}
