package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.SystemFeedback;
import com.xy.domain.User;
import com.xy.service.SystemFeedbackService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		User user = super.getCurrUser();
		String hql = "from SystemFeedback s where 1=1 ";
		int degree = user.getUserInfo().getDegree();
		if(degree==4){
			//说明是员工
			hql+=" and createBy = '"+user.getId()+"'";

		}else if(degree==3){
			//说明是部门经理，管理本部门
			hql+=" and createDept = '"+user.getDept().getId()+"'";

		}else if(degree==2){
			//说明是管理本部门及下属部门？？？？？
			hql+=" and createDept in (select id from Dept where id like '"+super.getCurrUser().getDept().getId()+"%')";

		}else if(degree==1){
			//说明是副总？？？？？
			//需要创建一个中间表
			hql+=" and createBy in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='员工') "
					+ "or createDept in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='部门')";
		}else if(degree==0){
			//说明是总经理

		}
		//按创建顺序排序
		hql += " order by createTime desc";

		//给页面提供分页数据
		page.setUrl("systemFeedbackAction_list");		//配置分页按钮的转向链接
		page = systemFeedbackService.findPage(hql, page, SystemFeedback.class, null);
		pushVS(page);
		return "plist";
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
		model.setCreateTime(new Date());
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
	
}
