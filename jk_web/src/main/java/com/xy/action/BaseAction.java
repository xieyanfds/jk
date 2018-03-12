package com.xy.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xy.domain.User;
import com.xy.utils.SysConstant;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


//通过RequestAware, SessionAware, ApplicationAware
//实行接口获得request,session,application对象，action中就可直接调用
/**
 * @author xieyan
 * @description 通用action
 * @date 2017/12/26.
 */
public class BaseAction extends ActionSupport implements RequestAware, SessionAware, ApplicationAware{
	private static Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	private static final long serialVersionUID = 1L;

	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;

	public Map<String, Object> getRequest() {
		return request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	/**
	 * push对象， 将对象放入值栈的栈顶
	 * @param obj
	 */
	public void pushVS(Object obj){
		ActionContext.getContext().getValueStack().push(obj);
	}
	/**
	 * 将key-value对放入值栈的 context中
	 * @param obj
	 */
	public void putContext(String key ,Object obj){
		ActionContext.getContext().put(key, obj);
	}
	/**
	 * 获取当前用户
	 */
	public User getCurrUser(){
		User user = (User)session.get(SysConstant.CURRENT_USER_INFO);
		return user;
	}
}
