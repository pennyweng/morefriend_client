package com.jookershop.linefriend.game;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class GameItem implements Serializable{
	
/**
	 * 
	 */
//	private static final long serialVersionUID = 2322934321246741554L;
	//	[
//    {
//        "game_info": {
//            "gid": "1f4c40b1-17c0-48b5-8e6f-e40379b30344",
//            "name": "終極密碼",
//            "st": 1416791670909,
//            "gtype": "tg",
//            "et": 0,
//            "introduction": "系統會自動選定一個範圍的數字，讓其餘的人猜數字。如果沒猜中的話，系統要會依猜出的數字將範圍縮小，讓下一個輪到的人繼續猜，直到猜中數字為止。猜中這個數字的人就獲勝。",
//            "reward": "獲勝者可獲得7-11的100元禮券。",
//            "is_finish": false,
//            "has_win": false,
//            "winner": null
//        },
//        "small": 0,
//        "big": 200000,
//        "count": 0
//    }
//]	
	public static String TYPE_GUESS = "tg";
	public static String TYPE_AB = "ab";
	public static String TYPE_LUCKYN = "ln";	
	private String gid;
	private String name;
	private long startTime;
	private String type;
	private long endTime;
	private String intro;
	private String reward;
	private boolean isFinish;
	private boolean hasWinner;
	private String wid;
	private long smallNumber;
	private long bigNumber;
	private int count;
	private String winnerLid;
	private String winNumber;
	private int total;
	private int number;
	
	
	
	
	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getWinNumber() {
		return winNumber;
	}


	public void setWinNumber(String winNumber) {
		this.winNumber = winNumber;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public static GameItem genPostItem( JSONObject jo) {
		GameItem pi = new GameItem();
		try {
			if(jo.has("game_info") && !jo.isNull("game_info")) {
				JSONObject jj = jo.getJSONObject("game_info");
				if(jj.has("gid") && !jj.isNull("gid"))
					pi.setGid(jj.getString("gid"));
				if(jj.has("name") && !jj.isNull("name"))
					pi.setName(jj.getString("name"));
				if(jj.has("st") && !jj.isNull("st"))
					pi.setStartTime(jj.getLong("st"));				
				if(jj.has("gtype") && !jj.isNull("gtype"))
					pi.setType(jj.getString("gtype"));	
				if(jj.has("et") && !jj.isNull("et"))
					pi.setEndTime(jj.getLong("et"));				
				if(jj.has("introduction") && !jj.isNull("introduction"))
					pi.setIntro(jj.getString("introduction"));	
				if(jj.has("reward") && !jj.isNull("reward"))
					pi.setReward(jj.getString("reward"));	
				if(jj.has("is_finish") && !jj.isNull("is_finish"))
					pi.setFinish(jj.getBoolean("is_finish"));				
				if(jj.has("has_win") && !jj.isNull("has_win"))
					pi.setHasWinner(jj.getBoolean("has_win"));
				if(jj.has("winner") && !jj.isNull("winner"))
					pi.setWid(jj.getString("winner"));
				if(jj.has("winner_lid") && !jj.isNull("winner_lid"))
					pi.setWinnerLid(jj.getString("winner_lid"));
				if(jj.has("winner_n") && !jj.isNull("winner_n"))
					pi.setWinNumber(jj.getString("winner_n"));	
				if(jj.has("total") && !jj.isNull("total"))
					pi.setTotal(jj.getInt("total"));					
			}
			
			if(jo.has("small") && !jo.isNull("small"))
				pi.setSmallNumber(jo.getLong("small"));
			if(jo.has("big") && !jo.isNull("big"))
				pi.setBigNumber(jo.getLong("big"));
			if(jo.has("count") && !jo.isNull("count"))
				pi.setCount(jo.getInt("count"));	
			if(jo.has("number") && !jo.isNull("number"))
				pi.setNumber(jo.getInt("number"));			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pi;
	}


	public String getWinnerLid() {
		return winnerLid;
	}


	public void setWinnerLid(String winnerLid) {
		this.winnerLid = winnerLid;
	}


	public String getGid() {
		return gid;
	}


	public void setGid(String gid) {
		this.gid = gid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	public String getIntro() {
		return intro;
	}


	public void setIntro(String intro) {
		this.intro = intro;
	}


	public String getReward() {
		return reward;
	}


	public void setReward(String reward) {
		this.reward = reward;
	}


	public boolean isFinish() {
		return isFinish;
	}


	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}


	public boolean isHasWinner() {
		return hasWinner;
	}


	public void setHasWinner(boolean hasWinner) {
		this.hasWinner = hasWinner;
	}


	public String getWid() {
		return wid;
	}


	public void setWid(String wid) {
		this.wid = wid;
	}


	public long getSmallNumber() {
		return smallNumber;
	}


	public void setSmallNumber(long smallNumber) {
		this.smallNumber = smallNumber;
	}


	public long getBigNumber() {
		return bigNumber;
	}


	public void setBigNumber(long bigNumber) {
		this.bigNumber = bigNumber;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	
	

	
	
	
}
