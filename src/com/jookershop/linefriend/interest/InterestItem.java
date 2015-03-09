package com.jookershop.linefriend.interest;

public class InterestItem {
	private Integer thumbId;
	private String title;
	private String categoryId;
	private int count;
	
	public InterestItem(Integer thumbId, String title, String categoryId, int count) {
		super();
		this.thumbId = thumbId;
		this.title = title;
		this.categoryId = categoryId;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
