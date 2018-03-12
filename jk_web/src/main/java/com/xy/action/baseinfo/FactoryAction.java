package com.xy.action.baseinfo;


import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Factory;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xieyan
 * @description 厂家信息
 * @date 2017/12/26.
 */
public class FactoryAction extends BaseAction implements ModelDriven<Factory> {

	private Logger logger = LoggerFactory.getLogger(FactoryAction.class);

	@Autowired
	private FactoryService factoryService;

	private Factory model = new Factory();
	@Override
	public Factory getModel() {
		return this.model;
	}
	
	private Page<Factory> page = new Page<>();
	public void setPage(Page<Factory> page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			//查询所有内容
			String hql = "from Factory";
			//给页面提供分页数据
			//配置分页按钮的转向链接
			page.setUrl("factoryAction_list");
			page = factoryService.findPage(hql, page, Factory.class, null);
			pushVS(page);
		} catch (NumberFormatException e) {
			logger.error("list exception:{}",e);
		}
		return "list";
	}
	
	//转向新增页面
	public String tocreate(){
		
		
		return "tocreate";
	}
	
	//新增保存
	public String insert(){
		try {
			factoryService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		//返回列表，重定向action_list
		return "alist";
	}

	//转向修改页面
	public String toupdate(){
		//准备修改的数据
		try {
			Factory obj = factoryService.get(Factory.class, model.getId());
			pushVS(obj);
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "toupdate";
	}
	
	//修改保存
	public String update(){
		try {
			Factory factory = factoryService.get(Factory.class, model.getId());

			//设置修改的属性，根据业务去掉自动生成多余的属性
			factory.setFactoryName(model.getFactoryName());
			factory.setCtype(model.getCtype());
			factory.setFullName(model.getFullName());
			factory.setFactoryName(model.getFactoryName());
			factory.setContacts(model.getContacts());
			factory.setPhone(model.getPhone());
			factory.setMobile(model.getMobile());
			factory.setFax(model.getFax());
			factory.setAddress(model.getAddress());
			factory.setInspector(model.getInspector());
			factory.setRemark(model.getRemark());
			factory.setOrderNo(model.getOrderNo());
			factory.setState(model.getState());


			factoryService.saveOrUpdate(factory);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "alist";
	}
	

	//删除多条
	public String delete(){
		try {
			String[] ids = model.getId().split(", ");
			factoryService.delete(Factory.class, ids);
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}
		return "alist";
	}
	
	//查看
	public String toview(){
		try {
			Factory obj = factoryService.get(Factory.class, model.getId());
			pushVS(obj);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		return "toview";			//转向查看页面
	}
}
