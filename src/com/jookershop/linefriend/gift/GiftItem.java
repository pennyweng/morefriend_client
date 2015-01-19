package com.jookershop.linefriend.gift;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class GiftItem implements Serializable{
	
//	[{
//	        "ll": {
//	            "id": "f5a9917b-dd2e-41a7-abe6-a775eff9152e",
//	            "type": "FR_LINE",
//	            "name": "掰啾的二代貼圖-動的掰啾",
//	            "max_count": 40,
//	            "img": "https://sdl-stickershop.line.naver.jp/products/0/0/1/3712/LINEStorePC/thumbnail_shop.png",
//	            "position": 1,
//	            "ct": 1420831247192
//	        },
//	        "cc": 0
//	    }]
	
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private String id;
	private String type;	
	private String name;
	private int maxCount;
	private String imgUrl;
	private int position;
	private long createTime;
	private int clickCount;
	private long finishTime;
	
	public static String TYPE_LINE = "FR_LINE";
	public static String TYPE_MONEY = "FR_MONY";

	public static GiftItem genPostItem( JSONObject jo) {
		GiftItem pi = new GiftItem();
		try {
			if(jo.has("ll") && !jo.isNull("ll")) {
				JSONObject jj = jo.getJSONObject("ll");
				if(jj.has("id") && !jj.isNull("id"))
					pi.setId(jj.getString("id"));
				if(jj.has("name") && !jj.isNull("name"))
					pi.setName(jj.getString("name"));
				if(jj.has("type") && !jj.isNull("type"))
					pi.setType(jj.getString("type"));				
				if(jj.has("max_count") && !jj.isNull("max_count"))
					pi.setMaxCount(jj.getInt("max_count"));
				if(jj.has("ct") && !jj.isNull("ct"))
					pi.setCreateTime(jj.getLong("ct"));
				if(jj.has("img") && !jj.isNull("img"))
					pi.setImgUrl(jj.getString("img"));	
				if(jj.has("position") && !jj.isNull("position"))
					pi.setPosition(jj.getInt("position"));	
			}
			
			if(jo.has("cc") && !jo.isNull("cc"))
				pi.setClickCount(jo.getInt("cc"));
			if(jo.has("lt") && !jo.isNull("lt"))
				pi.setFinishTime(jo.getLong("lt"));			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}




	public long getFinishTime() {
		return finishTime;
	}




	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public int getMaxCount() {
		return maxCount;
	}




	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}




	public String getImgUrl() {
		return imgUrl;
	}




	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}




	public int getPosition() {
		return position;
	}




	public void setPosition(int position) {
		this.position = position;
	}




	public long getCreateTime() {
		return createTime;
	}




	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}




	public int getClickCount() {
		return clickCount;
	}




	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	
	

}
