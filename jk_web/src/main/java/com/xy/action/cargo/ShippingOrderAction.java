package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.ShippingOrder;
import com.xy.service.ShippingOrderService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/26.
 */
public class ShippingOrderAction extends BaseAction implements ModelDriven<ShippingOrder> {

	@Autowired
	private ShippingOrderService shippingOrderService;


	/**
	 * model驱动
	 */
	private ShippingOrder model = new ShippingOrder();
	@Override
	public ShippingOrder getModel() {
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
		String hql = "from ShippingOrder ";
		//给页面提供分页数据
		//配置分页按钮的转向链接
		page.setUrl("packingListAction_list");
		page = shippingOrderService.findPage(hql, page, ShippingOrder.class, null);
		pushVS(page);
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
//		//准备数据
//		List<Export> list = exportService.find("from Export where state = 1", Export.class, null);
//		putContext("results", list);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		shippingOrderService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
		//准备修改的数据
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		pushVS(obj);
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		ShippingOrder packingList = shippingOrderService.get(ShippingOrder.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		//获取装箱单下的原来的报运单
		//获取新的勾选后的报运单

		shippingOrderService.saveOrUpdate(packingList);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		shippingOrderService.deleteById(ShippingOrder.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		shippingOrderService.delete(ShippingOrder.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		pushVS(obj);
		return "pview";			//转向查看页面
	}
	
	//提交
		public String submit(){
			ShippingOrder pl = shippingOrderService.get(ShippingOrder.class, model.getId());
			pl.setState(1);
			shippingOrderService.saveOrUpdate(pl);
			return "alist";
		}
		
		//取消
		public String cancel(){
			ShippingOrder pl = shippingOrderService.get(ShippingOrder.class, model.getId());
			pl.setState(0);
			shippingOrderService.saveOrUpdate(pl);
			return "alist";
		}
}
