package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Dept;
import com.xy.domain.Feedback;
import com.xy.domain.User;
import com.xy.service.DeptService;
import com.xy.service.FeedbackService;
import com.xy.utils.FastJsonUtil;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xieyan
 * @description 意见反馈
 * @date 2017/12/26.
 */
public class FeedbackAction extends BaseAction implements ModelDriven<Feedback> {
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private DeptService deptService;

	// model驱动
	private Feedback model = new Feedback();

	@Override
	public Feedback getModel() {
		return this.model;
	}

	// 作为属性驱动，接收并封装页面参数
	private Page page = new Page();

	public void setPage(Page page) {
		this.page = page;
	}

	//列表展示
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(!StringUtils.isEmpty(parameter)){
			page.setPageNo(Integer.parseInt(parameter));
		}
		//查询所有内容
		String hql = "from Feedback where (1 = 1 ";
		User user = super.getCurrUser();
		Integer degree = user.getUserInfo().getDegree();
		if (degree == 4) {
			// 说明是员工
			hql += "and createBy = '" + user.getId() + "'";

		} else if (degree == 3) {
			// 说明是部门经理，管理本部门
			hql += "and createDept = " + user.getDept().getId();

		} else if (degree == 2) {
			//说明是管理本部门及下属部门？？？？？
			hql += " and createDept in (select id from Dept where id like '"+super.getCurrUser().getDept().getId()+"%')";

		}else if(degree==1){
			//说明是副总需要创建一个中间表
			hql += " and createBy in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='员工') "
					+ "or createDept in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='部门')";
		}else if(degree==0){
			//说明是总经理

		}
		hql += ") or isShare = 1";
		//给页面提供分页数据
		page.setUrl("feedbackAction_list");		//配置分页按钮的转向链接
		page = feedbackService.findPage(hql, page, Feedback.class, null);
		pushVS(page);
		return "list";
	}

	//转向新增页面
	public String tocreate(){
		return "tocreate";
	}

	//新增保存
	public String insert(){
		User user = super.getCurrUser();
		Dept dept = user.getDept();
		String deptName = dept.getDeptName();
		model.setInputBy(deptName + ":" + user.getUserInfo().getName());
		model.setCreateBy(user.getId());
		model.setCreateDept(dept.getId());;
		feedbackService.saveOrUpdate(model);
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){

		//准备数据
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		super.pushVS(obj);
		return "toupdate";
	}

	//修改保存
	public String update(){
		Feedback feedback = feedbackService.get(Feedback.class, model.getId());

		//设置修改的属性
		if(feedback.getState()==0) {
			feedback.setTitle(model.getTitle());
			feedback.setClassType(model.getClassType());
			feedback.setTel(model.getTel());
			feedback.setIsShare(model.getIsShare());
			feedback.setContent(model.getContent());
			feedbackService.saveOrUpdate(feedback);
		}
		return "alist";
	}


	//删除多条
	public String delete(){
		feedbackService.delete(Feedback.class, model.getId().split(", "));
		return "alist";
	}


	/**
	 * 查看----
	 * @return
	 */
	public String toview(){
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		super.pushVS(obj);
		return "toview";
	}

	//转向解决页面
	public String tosolve(){
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		super.pushVS(obj);
		return "tosolve";
	}


	public String solve() throws Exception {
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		obj.setIsShare(model.getIsShare());
		User user = super.getCurrUser();
		Dept dept = user.getDept();
		String deptName = dept.getDeptName();
		obj.setAnswerBy(deptName+":"+user.getUserInfo().getName());
		obj.setSolveMethod(model.getSolveMethod());
		obj.setResolution(model.getResolution());
		obj.setDifficulty(model.getDifficulty());
		obj.setAnswerTime(new Date());
		obj.setState(1);
		feedbackService.saveOrUpdate(obj);
		return "alist";
	}

}
