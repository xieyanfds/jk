package com.xy.action.baseinfo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Factory;
import com.xy.domain.Product;
import com.xy.service.FactoryService;
import com.xy.service.ProductService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author xieyan
 * @description 产品信息
 * @date 2017/12/26.
 */

public class ProductAction extends BaseAction implements ModelDriven<Product> {

	@Autowired
	private ProductService productService;
	@Autowired
	private FactoryService factoryService;

	private String moduleName = "baseinfo_product";
	public String getModuleName() {
		return moduleName;
	}


	public void setModel(Product model) {
		this.model = model;
	}

	private Product model = new Product();
	@Override
	public Product getModel() {
		return this.model;
	}

	private Page<Product> page = new Page<>();
	public Page<Product> getPage() {
		return page;
	}
	public void setPage(Page<Product> page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		//查询所有内容
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		String hql = "from Product";
		//配置分页按钮的转向链接
		page.setUrl("productAction_list");
		//给页面提供分页数据
		page = productService.findPage(hql, page, Product.class, null);
		pushVS(page);
		return "list";
	}
	
	//转向新增页面
	public String tocreate(){
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1", Factory.class, null);

		putContext("factoryList", fList);
		return "tocreate";
	}
	
	//新增保存
	public String insert(){
		productService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//查询出所有的Factory
		List<Factory> factoryList = factoryService.find("from Factory", Factory.class, null);
		//将facrotyList放入值栈中
		super.putContext("factoryList", factoryList);

		//调用productService的get方法,获取页面上已勾选的Product对象
		Product product = productService.get(Product.class, model.getId());
		//将Product对象放入值栈中
		super.pushVS(product);

		return "toupdate";
	}
	
	//修改保存
	public String update(){
		Product product = productService.get(Product.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		product.setProductNo(model.getProductNo());
		product.setProductImage(model.getProductImage());
		product.setDescription(model.getDescription());
		product.setFactoryName(model.getFactoryName());
		product.setFactory(model.getFactory());
		product.setPrice(model.getPrice());
		product.setSizeLenght(model.getSizeLenght());
		product.setSizeWidth(model.getSizeWidth());
		product.setSizeHeight(model.getSizeHeight());
		product.setColor(model.getColor());
		product.setPacking(model.getPacking());
		product.setPackingUnit(model.getPackingUnit());
		product.setType(model.getType());
		product.setMpsizeLenght(model.getMpsizeLenght());
		product.setMpsizeWidth(model.getMpsizeWidth());
		product.setMpsizeHeight(model.getMpsizeHeight());
		product.setRemark(model.getRemark());
		
		productService.saveOrUpdate(product);
		
		return "alist";
	}
	
	//删除多条
	public String delete(){
		productService.delete(Product.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Product obj = productService.get(Product.class, model.getId());

		return "toview";			//转向查看页面
	}
}
