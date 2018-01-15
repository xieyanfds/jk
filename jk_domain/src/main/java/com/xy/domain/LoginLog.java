package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 登录日志
 * @date 2017/12/26.
 */

public class LoginLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String loginName;			
	private String ipAddress;			
	private Date loginTime;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getLoginName() {
		return this.loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}	
	
	public String getIpAddress() {
		return this.ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}	
	
	public Date getLoginTime() {
		return this.loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}	
}
