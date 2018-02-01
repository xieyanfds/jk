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

import java.util.ArrayList;
import java.util.Date;

/**
 * @author xieyan
 * @description 系统反馈
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
	private Page page = new Page(); // 封装页面的参数，主要当前页参数

	public void setPage(Page page) {
		this.page = page;
	}

	//列表展示
	public String list(){
		String hql = "from Feedback where (1 = 1 ";			//查询所有内容
		User user = super.getCurrUser();
		Integer degree = user.getUserInfo().getDegree();
		if (degree == 4) {
			hql += "and createBy = '" + user.getId() + "'";
			// 部门管理者能查本部门人员创建的contract
		} else if (degree == 3) {
			hql += "and createDept = " + user.getDept().getId();
			// 管理本部门及下属部门者可以查看所有由本部门人员和下属部门人员创建的意见
		} else if (degree == 2) {
			ArrayList<Dept> list = new ArrayList<>();
			deptService.findSubDept(list, Dept.class, user.getDept().getId());
			StringBuilder sb = new StringBuilder("(");
			for (Dept dept : list) {
				sb.append("'").append(dept.getId()).append("'").append(",");
			}
			sb.append("'").append(user.getDept().getId()).append("'").append(")");
			hql += "and createDept in " + sb.toString();
			// 跨部门管理者,需要修改表结构,尚未实现
		}

		hql += ") or isShare = 1";
		//给页面提供分页数据
		page.setUrl("feedbackAction_list");		//配置分页按钮的转向链接
		page = feedbackService.findPage(hql, page, Feedback.class, null);
		pushVS(page);
		return "list";						//page list
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
		Feedback feedback = feedbackService.get(Feedback.class, model.getId());
		String json = FastJsonUtil.toJSONString(feedback);
		FastJsonUtil.write_json(ServletActionContext.getResponse(), json);
		return NONE;
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
		String json = FastJsonUtil.toJSONString(obj);
		FastJsonUtil.write_json(ServletActionContext.getResponse(), json);
		return NONE;
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
