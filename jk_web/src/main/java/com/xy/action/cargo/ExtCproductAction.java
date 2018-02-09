package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.ExtCproduct;
import com.xy.domain.Factory;
import com.xy.service.ExtCproductService;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xieyan
 * @description 合同附件管理
 * @date 2017/12/26.
 */
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct>{
private static final long serialVersionUID = 1L;
	
	private ExtCproduct model = new ExtCproduct();
	@Override
	public ExtCproduct getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<ExtCproduct> page = new Page<ExtCproduct>();;
	public Page<ExtCproduct> getPage() {
		return page;
	}
	public void setPage(Page<ExtCproduct> page) {
		this.page = page;
	}

	@Autowired
	private ExtCproductService extCproductService;
	@Autowired
	private FactoryService factoryService;
	/**
	 * 根据购销合同查询生产货物
	 * @return
	 * @throws Exception
	 * <a href="/jk_web/cargo/ExtCproductAction_tocreate?contract.id=2c90c5004ad63735014ad6d204060005">[货物]</a>
	 */
	public String tocreate()throws Exception{
		//根据购销合同查询生产货物
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		extCproductService.findPage("from ExtCproduct where contractProduct.id = ?", page, ExtCproduct.class, new String []{model.getContractProduct().getId()});
		page.setUrl("extCproductAction_tocreate");
		pushVS(page);
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '附件'", Factory.class, null);
		
		putContext("factoryList", fList);
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		extCproductService.saveOrUpdate(model);
		return tocreate();
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 * <a href="ExtCproductAction_toupdate.action?id=4028817a3357462e0133591b86ec0004">[修改]</a>
	 */
	public String toupdate()throws Exception{
		ExtCproduct ExtCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		pushVS(ExtCproduct);
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '附件'", Factory.class, null);
		
		putContext("factoryList", fList);
		return "toUpdate";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 * <a href="ExtCproductAction_delete.action?id=4028817a3357462e0133591b86ec0004&contract.id=4028817a3357462e0133591b86ec0002">[删除]</a>
	 */
	public String delete()throws Exception{
        //删除
		extCproductService.delete(ExtCproduct.class,model);
		return tocreate();
	}
	/*
	<a href="ExtCproductAction_delete.action?id=4028817a3357462e0133591b86ec0004&contract.id=4028817a3357462e0133591b86ec0002">[删除]</a>
	<a href="extCproductAction_tocreate.action?ExtCproduct.contract.id=4028817a3357462e0133591b86ec0002&ExtCproduct.id=4028817a3357462e0133591b86ec0004">[附件]</a>*/
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		//先查询
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		//设置修改的属性
		extCproduct.setFactory(model.getFactory());
		extCproduct.setFactoryName(model.getFactoryName());
		extCproduct.setProductNo(model.getProductNo());
		extCproduct.setProductImage(model.getProductImage());
		extCproduct.setCnumber(model.getCnumber());
	    extCproduct.setAmount(model.getAmount());
	    extCproduct.setPackingUnit(model.getPackingUnit());
	    extCproduct.setPrice(model.getPrice());
	    extCproduct.setOrderNo(model.getOrderNo());
	    extCproduct.setProductDesc(model.getProductDesc());   
	    extCproduct.setProductRequest(model.getProductRequest());
	    
		extCproductService.saveOrUpdate(extCproduct);
		return tocreate();
	}
}
