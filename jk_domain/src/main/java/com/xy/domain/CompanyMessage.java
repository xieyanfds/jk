package com.xy.domain;

import com.alibaba.fastjson.annotation.JSONField;

public class CompanyMessage extends BaseEntity{
	
	@JSONField(serialize=false)
	String id;
	String username;
	String leaveMessage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLeaveMessage() {
		return leaveMessage;
	}
	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}
	
}
