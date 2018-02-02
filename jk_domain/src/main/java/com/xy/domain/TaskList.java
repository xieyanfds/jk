package com.xy.domain;

import java.util.Date;


/**
 * @author xieyan
 * @description 待办任务
 * @date 2017/12/26.
 */
public class TaskList extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;			
	private String userId;			//执行者id
	private String userName;     //执行者姓名
	private String pusherId;		//发布者id
	private String pusherName;     //发布者姓名
	private String content;			//任务内容
	private Date pushDate;			//发布日期
	private Date endDate;			//任务截止日期
	private Integer state;			//状态
	private String major;   //重要程度
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	
	public String getPusherId() {
		return this.pusherId;
	}
	public void setPusherId(String pusherId) {
		this.pusherId = pusherId;
	}	
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	
	public Date getPushDate() {
		return this.pushDate;
	}
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}	
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getPusherName() {
		return pusherName;
	}
	public void setPusherName(String pusherName) {
		this.pusherName = pusherName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}	
	
}
