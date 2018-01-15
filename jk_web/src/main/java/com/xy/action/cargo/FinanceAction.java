package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Finance;
import com.xy.domain.Invoice;
import com.xy.domain.User;
import com.xy.service.FinanceService;
import com.xy.service.InvoiceService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * @author xieyan
 * @description 财务
 * @date 2017/12/26.
 */
public class FinanceAction extends BaseAction implements ModelDriven<Finance> {

	@Autowired
	private FinanceService financeService;
	@Autowired
	private InvoiceService invoiceService;

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
		//查询所有内容
		//可以添加细粒度权限控制
		String hql = "from Finance ";
		//给页面提供分页数据
		//配置分页按钮的转向链接
		page.setUrl("financeAction_list");
		page = financeService.findPage(hql, page, Finance.class, null);
		pushVS(page);
		return "list";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据
		List<Invoice> invoices = invoiceService.find("from Invoice where state = 1", Invoice.class, null);
		putContext("invoiceList",invoices);
		return "tocreate";
	}
	
	//新增保存
	public String insert(){
		//添加细粒度权限控制
		//获取当前用户
		User user = super.getCurrUser();
		model.setCreateTime(new Date());
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());

		financeService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
        //准备修改的数据
        Finance obj = financeService.get(Finance.class, model.getId());

        pushVS(obj);
		return "toupdate";
	}
	
	//修改保存
	public String update(){
		//获取发票对象
		Finance finance = financeService.get(Finance.class, model.getId());

		//设置修改的属性，根据业务去掉自动生成多余的属性
		finance.setInputBy(model.getInputBy());
		finance.setInputDate(model.getInputDate());

		financeService.saveOrUpdate(model);
        return "alist";

	}
	
	//删除多条
	public String delete(){
		financeService.delete(Finance.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Finance obj = financeService.get(Finance.class, model.getId());
        pushVS(obj);

        //转向查看页面
        return "toview";
	}
	
	//提交
	public String submit(){
        String[] split = model.getId().split(", ");
		financeService.changeState(split,1);

		return "alist";
	}

	//取消
	public String cancel(){
        String[] split = model.getId().split(", ");
		financeService.changeState(split,0);

		return "alist";
	}

}
