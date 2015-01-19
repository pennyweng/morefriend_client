package com.jookershop.linefriend.lucky;

import org.json.JSONException;
import org.json.JSONObject;

public class LuckyItem {
	
	private String id;
	private String name;
	private String image;
	private String desc;
	private int maxCount;
	private String award;
	private int currentCount;
	
	
	
	public int getCurrentCount() {
		return currentCount;
	}


	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}


	public String getAward() {
		return award;
	}


	public void setAward(String award) {
		this.award = award;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public int getMaxCount() {
		return maxCount;
	}


	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static LuckyItem genPostItem( JSONObject jj) {
		LuckyItem pi = new LuckyItem();
		try {
				if(jj.has("id") && !jj.isNull("id"))
					pi.setId(jj.getString("id"));
				if(jj.has("name") && !jj.isNull("name"))
					pi.setName(jj.getString("name"));
				if(jj.has("image") && !jj.isNull("image"))
					pi.setImage(jj.getString("image"));				
				if(jj.has("desc") && !jj.isNull("desc"))
					pi.setDesc(jj.getString("desc"));	
				if(jj.has("award") && !jj.isNull("award"))
					pi.setAward(jj.getString("award"));					
				if(jj.has("max_count") && !jj.isNull("max_count"))
					pi.setMaxCount(jj.getInt("max_count"));	
				if(jj.has("current_count") && !jj.isNull("current_count"))
					pi.setCurrentCount(jj.getInt("current_count"));					
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}

}
