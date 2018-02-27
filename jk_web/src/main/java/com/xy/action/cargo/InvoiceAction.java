package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.action.print.InvoicePrint;
import com.xy.domain.PackingList;
import com.xy.domain.Invoice;
import com.xy.domain.ShippingOrder;
import com.xy.domain.User;
import com.xy.service.*;
import com.xy.service.InvoiceService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author xieyan
 * @description 发票
 * @date 2017/12/26.
 */
public class InvoiceAction extends BaseAction implements ModelDriven<Invoice> {

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ShippingOrderService shippingOrderService;
	@Autowired
	private PackingListService packingListService;
	@Autowired
	static ContractService contractService;
	/**
	 * model驱动
	 */
	private Invoice model = new Invoice();

	@Override
	public Invoice getModel() {
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
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(!StringUtils.isEmpty(parameter)){
			page.setPageNo(Integer.parseInt(parameter));
		}
		String hql = "from Invoice order by createTime desc";
		//给页面提供分页数据
		//配置分页按钮的转向链接
		page.setUrl("invoiceAction_list");
		page = invoiceService.findPage(hql, page, Invoice.class, null);
		pushVS(page);
		return "list";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据，获取已提交或已委托的装箱单，因为他们是公用主键
		List<PackingList> list = packingListService.find("from PackingList where state != 0 order by createTime desc", PackingList.class, null);
		putContext("results", list);
		return "tocreate";
	}
	
	//新增保存
	public String insert(){
		//添加细粒度权限控制
		//获取当前用户
		User user = super.getCurrUser();
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setState(0);

		//从货运单获取发票号和发票时间
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		//设置报运的合同号
		model.setScNo(packingList.getExportNos());
		model.setCreateTime(new Date());
		packingList.setInvoiceDate(model.getCreateTime());
		packingList.setInvoiceNo(model.getId());
		//已开发票
		packingList.setState(3);
		//获取对应委托单，修改状态为二
//		ShippingOrder shippingOrder = shippingOrderService.get(ShippingOrder.class, model.getId());
//		shippingOrder.setState(2);
		invoiceService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
        //准备修改的数据
        Invoice obj = invoiceService.get(Invoice.class, model.getId());

        pushVS(obj);
		return "toupdate";
	}
	
	//修改保存
	public String update(){
		//获取发票对象
		Invoice invoice = invoiceService.get(Invoice.class, model.getId());

		// 设置修改的属性，根据业务去掉自动生成多余的属性
		invoice.setBlNo(model.getBlNo());
		invoice.setTradeTerms(model.getTradeTerms());

        invoiceService.saveOrUpdate(invoice);
        return "alist";

	}
	
	//删除多条
	public String delete(){
		invoiceService.delete(Invoice.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
        pushVS(obj);

        //转向查看页面
        return "toview";
	}
	
	//提交
	public String submit(){
        String[] split = model.getId().split(", ");
        invoiceService.changeState(split,1);

		return "alist";
	}

	//取消
	public String cancel(){
        String[] split = model.getId().split(", ");
        invoiceService.changeState(split,0);

		return "alist";
	}

	/**
	 * 带模板的打印
	 *
	 * @return
	 */
	@SuppressWarnings("resource")
	public String print() throws Exception {

		//调用print方法
		WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InvoicePrint invoicePrint = (InvoicePrint)currentWebApplicationContext.getBean("invoicePrint");
		invoicePrint.print(model);
		return NONE;
	}
}
