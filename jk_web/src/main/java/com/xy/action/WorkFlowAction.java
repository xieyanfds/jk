package com.xy.action;


import com.opensymphony.xwork2.ModelDriven;
import com.xy.domain.Feedback;
import com.xy.domain.User;
import com.xy.service.FeedbackService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xieyan
 * @description 待办任务
 * @date 2017/12/26.
 */
public class WorkFlowAction extends BaseAction implements ModelDriven<Feedback> {
	@Autowired
	private FeedbackService feedbackService;

	//model驱动
	private Feedback model = new Feedback();
	@Override
	public Feedback getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		User curUser = this.getCurrUser();
		String hql = "from Feedback where answerBy='"+curUser.getId()+"' and state = '2'";			//查询待办任务
		//给页面提供分页数据
		page.setUrl("workFlowAction_list");		//配置分页按钮的转向链接
		page = feedbackService.findPage(hql, page, Feedback.class, null);
		super.pushVS(page);
		
		return "plist";					//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据
		List<Feedback> feedbackList = feedbackService.feedbackList();
		super.putContext("feedbackList", feedbackList);		//页面就可以访问feedbackList
		
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		 model.setState(0);
		feedbackService.saveOrUpdate(model);
		return list();			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
				
		//准备修改的数据
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		super.pushVS(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		
		Feedback feedback = feedbackService.get(Feedback.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
/*		feedback.setInputBy(model.getInputBy());
		feedback.setInputTime(model.getInputTime());
		feedback.setTitle(model.getTitle());
		feedback.setClassType(model.getClassType());
		feedback.setContent(model.getContent());
		feedback.setTel(model.getTel());*/
		
		
		feedback.setAnswerTime(model.getAnswerTime());
		feedback.setTel(model.getTel());
		feedback.setSolveMethod(model.getSolveMethod());
		feedback.setResolution(model.getResolution());
		feedback.setDifficulty(model.getDifficulty());
		feedback.setIsShare(model.getIsShare());
		feedback.setState(3);
		
		feedbackService.saveOrUpdate(feedback);
		
		return list();
	}
	
	//删除一条
	public String deleteById(){
		feedbackService.deleteById(Feedback.class, model.getId());
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		feedbackService.delete(Feedback.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Feedback obj = feedbackService.get(Feedback.class, model.getId());
		super.pushVS(obj);
		
		return "pview";			//转向查看页面
	}
	//提交
	public String submit(){
			String ids[]=model.getId().split(", ");
			feedbackService.submit(ids,1);
		return list();
	}
	//取消
	public String cancel(){
		String ids[]=model.getId().split(", ");
		feedbackService.submit(ids,0);
		return list();
	}
}
