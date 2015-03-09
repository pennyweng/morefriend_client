package com.jookershop.linefriend.friend;

public class FriendItem {
	private Integer thumbId;
	private String uid;
	private String title;
	private String lineId;
	private String sex;
	private String interestIds ="";
	private String oldIds = "";
	private String careerIds = "";
	private String placeIds = "";
	private String constellationIds = "";
	private String motionIds = "";
	private boolean hasNotification;
	
	
	public FriendItem(Integer thumbId, String title) {
		super();
		this.thumbId = thumbId;
		this.title = title;
	}
	
	

	public String getMotionIds() {
		return motionIds;
	}



	public void setMotionIds(String motionIds) {
		this.motionIds = motionIds;
	}



	public boolean getHasNotification() {
		return hasNotification;
	}



	public void setHasNotification(boolean hasNotification) {
		this.hasNotification = hasNotification;
	}



	public FriendItem(String uid, String title, String lineId, String sex, 
			String interestIds, String oldIds, String careerIds, String placeIds, String constellationIds, String motionIds) {
		super();
		this.uid = uid;
		this.title = title;
		this.lineId = lineId;
		this.sex = sex;
		this.interestIds = interestIds;
		this.oldIds = oldIds;
		this.careerIds = careerIds;
		this.placeIds = placeIds;
		this.constellationIds = constellationIds;
		this.motionIds = motionIds;
	}
	
	public String getConstellationIds() {
		return constellationIds;
	}



	public void setConstellationIds(String constellationIds) {
		this.constellationIds = constellationIds;
	}



	public FriendItem(String uid, String title, String lineId, String sex) {
		super();
		this.uid = uid;
		this.title = title;
		this.lineId = lineId;
		this.sex = sex;
	}


	

	public String getInterestIds() {
		return interestIds;
	}



	public void setInterestIds(String interestIds) {
		this.interestIds = interestIds;
	}



	public String getOldIds() {
		return oldIds;
	}



	public void setOldIds(String oldIds) {
		this.oldIds = oldIds;
	}



	public String getCareerIds() {
		return careerIds;
	}



	public void setCareerIds(String careerIds) {
		this.careerIds = careerIds;
	}



	public String getPlaceIds() {
		return placeIds;
	}



	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	public String getUid() {
		return uid;
	}



	public void setUid(String uid) {
		this.uid = uid;
	}



	public String getLineId() {
		return lineId;
	}



	public void setLineId(String lineId) {
		this.lineId = lineId;
	}



	public Integer getThumbId() {
		return thumbId;
	}
	public void setThumbId(Integer thumbId) {
		this.thumbId = thumbId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
