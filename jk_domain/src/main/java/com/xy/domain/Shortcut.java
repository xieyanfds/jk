package com.xy.domain;

/**
 * @author xieyan
 * @description
 * @date 2017/12/26.
 */

public class Shortcut extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String uid;			
	private String moduleIds;
	

	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}	
	
	public String getModuleIds() {
		return this.moduleIds;
	}
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}	
}
