package com.xy.domain;

import java.io.Serializable;
/**
 * @author xieyan
 * @description
 * @date 2017/12/26.
 */

public class Message extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
