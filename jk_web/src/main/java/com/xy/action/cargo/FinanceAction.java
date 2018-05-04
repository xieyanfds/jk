package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Finance;
import com.xy.domain.PackingList;
import com.xy.domain.ShippingOrder;
import com.xy.domain.User;
import com.xy.service.FinanceService;
import com.xy.service.PackingListService;
import com.xy.service.ShippingOrderService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author xieyan
 * @description 财务
 * @date 2017/12/26.
 */
public class FinanceAction extends BaseAction implements ModelDriven<Finance> {

	private Logger logger = LoggerFactory.getLogger(FinanceAction.class);

	@Autowired
	private FinanceService financeService;
	@Autowired
	private PackingListService packingListService;
	@Autowired
	private ShippingOrderService shippingOrderService;

	/**
	 * model驱动
	 */
	private Finance model = new Finance();

	@Override
	public Finance getModel() {
		return this.model;
	}

	/**
	 * 作为属性驱动，接收并封装页面参数
	 */
	private Page page = new Page();

	public void setPage(Page page) {
		this.page = page;
	}



	/**
	 * 列表展示
	 * @return
	 */
	public String list(){
		try {
			//查询所有内容
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			String hql = "from Finance order by createTime desc";
			//给页面提供分页数据
			//配置分页按钮的转向链接
			page.setUrl("financeAction_list");
			page = financeService.findPage(hql, page, Finance.class, null);
			pushVS(page);
		} catch (NumberFormatException e) {
			logger.error("list exception:{}",e);
		}
		return "list";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		try {
			//准备数据，获取已提交或已委托的装箱单，因为他们是公用主键
			List<PackingList> list = packingListService.find("from PackingList where state != 0 order by createTime desc", PackingList.class, null);
			putContext("results", list);
		} catch (Exception e) {
			logger.error("tocreate exception:{}",e);
		}
		return "tocreate";
	}
	
	//新增保存
	public String insert(){
		try {
			//添加细粒度权限控制
			//获取当前用户
			User user = super.getCurrUser();
			model.setCreateTime(new Date());
			model.setCreateBy(user.getId());
			model.setCreateDept(user.getDept().getId());
			//获取装箱单
			PackingList packingList = packingListService.get(PackingList.class, model.getId());
			//已报账
			packingList.setState(4);
			//修改委托单状态已完成
			ShippingOrder shippingOrder = shippingOrderService.get(model.getId());
			shippingOrder.setState(2);


			financeService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		try {
			//准备修改的数据
			Finance obj = financeService.get(Finance.class, model.getId());

			pushVS(obj);
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "toupdate";
	}
	
	//修改保存
	public String update(){
		try {
			//获取发票对象
			Finance finance = financeService.get(Finance.class, model.getId());

			//设置修改的属性，根据业务去掉自动生成多余的属性
			finance.setInputBy(model.getInputBy());
			finance.setInputDate(model.getInputDate());

			financeService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "alist";

	}
	
	//删除多条
	public String delete(){
		try {
			financeService.delete(Finance.class, model.getId().split(", "));
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}
		return "alist";
	}
	
	//查看
	public String toview(){
		try {
			Finance obj = financeService.get(Finance.class, model.getId());
			pushVS(obj);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		//转向查看页面
        return "toview";
	}
	
	//提交
	public String submit(){
		try {
			String[] split = model.getId().split(", ");
			financeService.changeState(split,1);
		} catch (Exception e) {
			logger.error("submit exception:{}",e);
		}
		return "alist";
	}

	//取消
	public String cancel(){
		try {
			String[] split = model.getId().split(", ");
			financeService.changeState(split,0);
		} catch (Exception e) {
			logger.error("cancel exception:{}",e);
		}
		return "alist";
	}

}
