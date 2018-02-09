package com.xy.domain;

import java.util.Date;

/**
 * @author xieyan
 * @description 留言
 * @date 2017/12/26.
 */
public class Message extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;
	private String createName;          // 创建人姓名
	private String receive;          // 接收人姓名
	private String receiveId;			//接收人id
	private Date messageTime;			//时间
	private String title;
	private String message;			//留言
	private Integer state = 1;			//状态

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getReceiveId() {
		return this.receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public Date getMessageTime() {
		return this.messageTime;
	}
	public void setMessageTime(Date messagetime) {
		this.messageTime = messagetime;
	}	
	
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
