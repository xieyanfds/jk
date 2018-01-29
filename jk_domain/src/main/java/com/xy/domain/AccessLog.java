package com.xy.domain;

public class AccessLog extends BaseEntity{

	private String id;
	private String userId;
	private String moduleKey;
	private String moduleName;
	private String moduleCurl;
	private Integer number;
	private String ico;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCurl() {
		return moduleCurl;
	}

	public void setModuleCurl(String moduleCurl) {
		this.moduleCurl = moduleCurl;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}
}
