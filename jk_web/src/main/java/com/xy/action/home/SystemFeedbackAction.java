package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.SystemFeedback;
import com.xy.domain.User;
import com.xy.service.SystemFeedbackService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xieyan
 * @description 系统使用反馈
 * @date 2017/12/26.
 */
public class SystemFeedbackAction extends BaseAction implements ModelDriven<SystemFeedback> {
	@Autowired
	private SystemFeedbackService systemFeedbackService;

	private SystemFeedback model = new SystemFeedback();
	@Override
	public SystemFeedback getModel() {
		return this.model;
	}

	private Page page = new Page();
	public void setPage(Page page) {
		this.page = page;
	}

	//列表展示
	public String list(){
		User user = super.getCurrUser();

		String hql = "from SystemFeedback s where 1=1 ";

		int degree = user.getUserInfo().getDegree();

		if (degree == 4) { // 四级是普通员工只能自己看
			hql += "and createBy = '" + user.getId() + "'";
		} else if (degree == 3) { // 三级管理本部门, 可以看所有本部门的信息
			hql += "and createDept like '" + user.getDept().getId() + "%'";
		} else if (degree == 2) { // 二级管理所有下属部门, 可以查看管辖区域内的信息
			hql += "and createDept like '" + user.getDept().getParent().getId() + "%'";
		} else if (degree == 1) {
			hql += "and createDept = '" + user.getDept().getId() + "'";
		} else if (degree == 0) {
			hql += "and createDept = '" + user.getDept().getId() + "'";
		}

		//按创建顺序排序
		hql += " order by createTime desc";

		//给页面提供分页数据
		page.setUrl("systemFeedbackAction_list");		//配置分页按钮的转向链接
		page = systemFeedbackService.findPage(hql, page, SystemFeedback.class, null);
		super.putContext("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		User currUser = super.getCurrUser();
		model.setCreateBy(currUser.getId());
		model.setCreateDept(currUser.getDept().getId());
		model.setCreateName(currUser.getUserName());
		model.setCreateDeptName(currUser.getDept().getDeptName());
		systemFeedbackService.saveOrUpdate(model);

		//返回列表，重定向action_list
		return "alist";
	}

	
	//删除一条
	public String deleteById(){
		systemFeedbackService.deleteById(SystemFeedback.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		systemFeedbackService.delete(SystemFeedback.class, model.getId().split(", "));
		return "alist";
	}
	
	//查看
	public String toview(){
		SystemFeedback obj = systemFeedbackService.get(SystemFeedback.class, model.getId());
		super.pushVS(obj);

		//转向查看页面
		return "pview";
	}
}
