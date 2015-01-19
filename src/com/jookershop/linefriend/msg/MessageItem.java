package com.jookershop.linefriend.msg;

public class MessageItem {
	private String msg;
	private String fromId;
	private String fromLid;
	private String toId;
	private String toLid;
	private long ts;
	
	public MessageItem() {}
	public MessageItem(String msg, String fromId, String fromLid, String toId,
			String toLid, long ts) {
		super();
		this.msg = msg;
		this.fromId = fromId;
		this.fromLid = fromLid;
		this.toId = toId;
		this.toLid = toLid;
		this.ts = ts;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFromLid() {
		return fromLid;
	}
	public void setFromLid(String fromLid) {
		this.fromLid = fromLid;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getToLid() {
		return toLid;
	}
	public void setToLid(String toLid) {
		this.toLid = toLid;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	
	
}
