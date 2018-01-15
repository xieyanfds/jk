package com.xy.domain;

import java.util.Date;
/**
 * @author xieyan
 * @description 反馈
 * @date 2017/12/26.
 */

public class Feedback extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String inputBy;			
	private Date inputTime;			
	private String title;			
	private String content;			
	private String classType;			
	private String tel;			
	private String answerBy;			
	private Date answerTime;			
	private String solveMethod;			
	private String resolution;			
	private String difficulty;			
	private String isShare;			
	private Integer state;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getInputBy() {
		return this.inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}	
	
	public Date getInputTime() {
		return this.inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}	
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	
	public String getClassType() {
		return this.classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}	
	
	public String getTel() {
		return this.tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}	
	
	public String getAnswerBy() {
		return this.answerBy;
	}
	public void setAnswerBy(String answerBy) {
		this.answerBy = answerBy;
	}	
	
	public Date getAnswerTime() {
		return this.answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}	
	
	public String getSolveMethod() {
		return this.solveMethod;
	}
	public void setSolveMethod(String solveMethod) {
		this.solveMethod = solveMethod;
	}	
	
	public String getResolution() {
		return this.resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}	
	
	public String getDifficulty() {
		return this.difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}	
	
	public String getIsShare() {
		return this.isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	

	
	
}
