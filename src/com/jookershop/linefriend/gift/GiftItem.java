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
	private String code;
	private int baseMoney;
	private boolean showReport;
	private int status;
	private int msgCount;
	private long currentCount;
	private long sendCount;
	private int nMaxCount;
	
	public static String TYPE_LINE = "FR_LINE";
	public static String TYPE_MONEY = "FR_MONY";
	public static String TYPE_BAG = "FR_BAG";
	public static String TYPE_SE = "FR_SE";

	
	public long getSendCount() {
		return sendCount;
	}




	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}




	public long getCurrentCount() {
		return currentCount;
	}




	public void setCurrentCount(long currentCount) {
		this.currentCount = currentCount;
	}




	public int getnMaxCount() {
		return nMaxCount;
	}




	public void setnMaxCount(int nMaxCount) {
		this.nMaxCount = nMaxCount;
	}




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
				if(jj.has("max_ncount") && !jj.isNull("max_ncount"))
					pi.setnMaxCount(jj.getInt("max_ncount"));				
				if(jj.has("ct") && !jj.isNull("ct"))
					pi.setCreateTime(jj.getLong("ct"));
				if(jj.has("img") && !jj.isNull("img"))
					pi.setImgUrl(jj.getString("img"));	
				if(jj.has("position") && !jj.isNull("position"))
					pi.setPosition(jj.getInt("position"));
				if(jj.has("base_money") && !jj.isNull("base_money"))
					pi.setBaseMoney(jj.getInt("base_money"));				
			}
			
			if(jo.has("cc") && !jo.isNull("cc"))
				pi.setClickCount(jo.getInt("cc"));
			if(jo.has("lt") && !jo.isNull("lt"))
				pi.setFinishTime(jo.getLong("lt"));	
			
			if(jo.has("new_code") && !jo.isNull("new_code"))
				pi.setCode(jo.getString("new_code"));
			
			if(jo.has("status") && !jo.isNull("status")) {
				pi.setStatus(jo.getInt("status"));
				if(pi.getStatus() == -1) {
					pi.setShowReport(false);
				} else pi.setShowReport(true);
			}
			
			if(jo.has("leave_msg_count") && !jo.isNull("leave_msg_count"))
				pi.setMsgCount(jo.getInt("leave_msg_count"));
			if(jo.has("cn") && !jo.isNull("cn"))
				pi.setCurrentCount(jo.getLong("cn"));
			if(jo.has("sc") && !jo.isNull("sc"))
				pi.setSendCount(jo.getLong("sc"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}




	public int getBaseMoney() {
		return baseMoney;
	}




	public boolean isShowReport() {
		return showReport;
	}




	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}




	public int getStatus() {
		return status;
	}




	public void setStatus(int status) {
		this.status = status;
	}




	public int getMsgCount() {
		return msgCount;
	}




	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}




	public void setBaseMoney(int baseMoney) {
		this.baseMoney = baseMoney;
	}




	public long getFinishTime() {
		return finishTime;
	}




	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}




	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
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
