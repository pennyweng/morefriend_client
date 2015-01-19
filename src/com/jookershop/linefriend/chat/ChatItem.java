package com.jookershop.linefriend.chat;

public class ChatItem {
	private String id;
	private String name;
	private int manCount;
	private int womenCount;
	private String lineId;
	private String color;
	private int isOpen = 0; 
	
	
	
	public ChatItem(String id, String name, int manCount, int womenCount, String color,
			String lineId) {
		super();
		this.id = id;
		this.name = name;
		this.manCount = manCount;
		this.womenCount = womenCount;
		this.lineId = lineId;
		this.color = color;
		this.isOpen = 0;
	}
	

	public ChatItem(String id, String name, int manCount, int womenCount, String color,
			String lineId, int isOpen) {
		super();
		this.id = id;
		this.name = name;
		this.manCount = manCount;
		this.womenCount = womenCount;
		this.lineId = lineId;
		this.color = color;
		this.isOpen = isOpen;
	}
	
	
	
	public int getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getManCount() {
		return manCount;
	}
	public void setManCount(int manCount) {
		this.manCount = manCount;
	}
	public int getWomenCount() {
		return womenCount;
	}
	public void setWomenCount(int womenCount) {
		this.womenCount = womenCount;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
}
