package com.jookershop.linefriend.account;

public class LikeItem {
	private String categoryId;
	private boolean selected;
	private String name;
	private String color = "#009989";
	
	public LikeItem(String categoryId, boolean selected, String name) {
		super();
		this.categoryId = categoryId;
		this.selected = selected;
		this.name = name;
	}
	
	public LikeItem(String categoryId, boolean selected, String name, String color) {
		super();
		this.categoryId = categoryId;
		this.selected = selected;
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	} 
	
	
	
}
