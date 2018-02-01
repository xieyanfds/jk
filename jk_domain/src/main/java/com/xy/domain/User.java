package com.xy.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Dept dept;//用户与部门   多对一
	private Userinfo userInfo ;  //用户与用户扩展信息    一对一
	@JSONField(serialize = false)
	private Set<Role> roles = new HashSet<Role>(0);//用户与角色   多对多
	private String userName;//用户名
	private String password;//密码  要加密
	private Integer state;//状态
	@JSONField(serialize = false)
	private Set<AccessLog> accessLogs;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Userinfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<AccessLog> getAccessLogs() {
		return accessLogs;
	}

	public void setAccessLogs(Set<AccessLog> accessLogs) {
		this.accessLogs = accessLogs;
	}
}
