package com.xy.domain;

/**
 * @author xieyan
 * @description 系统使用反馈
 * @date 2017/12/26.
 */

public class SystemFeedback extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String message;
	private String createName;
	private String createDeptName;

	
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
	
	
	
	
}
