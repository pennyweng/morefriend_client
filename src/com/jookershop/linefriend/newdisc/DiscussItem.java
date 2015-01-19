package com.jookershop.linefriend.newdisc;

public class DiscussItem {
	private String id;
	private String name;
	private String desc;
	private String num;
	
	
	public DiscussItem() {
		
	}
	public DiscussItem(String id, String name, String desc, String num) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.num = num;

	}



	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}
	


	
}
