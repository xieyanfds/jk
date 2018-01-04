package com.xy.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.ContractProduct;
import com.xy.domain.Factory;
import com.xy.service.ContractProductService;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xieyan
 * @description 购销合同下货物
 * @date 2017/12/26.
 */
public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct>{
private static final long serialVersionUID = 1L;
	
	private ContractProduct model = new ContractProduct();
	
	@Override
	public ContractProduct getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<ContractProduct> page = new Page<ContractProduct>();;
	public Page<ContractProduct> getPage() {
		return page;
	}
	public void setPage(Page<ContractProduct> page) {
		this.page = page;
	}

	@Autowired
	private ContractProductService contractProductService;

	@Autowired
	private FactoryService factoryService;
	/**
	 * 根据购销合同查询生产货物
	 * @return
	 * @throws Exception
	 * <a href="/jk_web/cargo/contractProductAction_tocreate?contract.id=2c90c5004ad63735014ad6d204060005">[货物]</a>
	 */
	public String tocreateAndList()throws Exception{
		//根据购销合同查询生产货物
		contractProductService.findPage("from ContractProduct where contract.id = ?", page,ContractProduct.class,
				new String[]{model.getContract().getId()});
		page.setUrl("contractProductAction_tocreateAndList");
		pushVS(page);
//		putContext("page",page);
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '货物'", Factory.class, null);
		
		putContext("factoryList", fList);
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		contractProductService.saveOrUpdate(model);
		
		return tocreateAndList();
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 * <a href="contractProductAction_toupdate.action?id=4028817a3357462e0133591b86ec0004">[修改]</a>
	 */
	public String toupdate()throws Exception{
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		pushVS(contractProduct);
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '货物'", Factory.class, null);
		
		putContext("factoryList", fList);
		return "toUpdate";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 * <a href="contractProductAction_delete.action?id=4028817a3357462e0133591b86ec0004&contract.id=4028817a3357462e0133591b86ec0002">[删除]</a>
	 */
	public String delete()throws Exception{
        //删除
		contractProductService.delete(model);
		return tocreateAndList();
	}
	/*
	<a href="contractProductAction_delete.action?id=4028817a3357462e0133591b86ec0004&contract.id=4028817a3357462e0133591b86ec0002">[删除]</a>
	<a href="extCproductAction_tocreate.action?contractProduct.contract.id=4028817a3357462e0133591b86ec0002&contractProduct.id=4028817a3357462e0133591b86ec0004">[附件]</a>*/
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		//先查询
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		//设置修改的属性
		contractProduct.setFactory(model.getFactory());
		contractProduct.setFactoryName(model.getFactoryName());
		contractProduct.setProductNo(model.getProductNo());
		contractProduct.setProductImage(model.getProductImage());
		contractProduct.setCnumber(model.getCnumber());
	    contractProduct.setAmount(model.getAmount());
	    contractProduct.setPackingUnit(model.getPackingUnit());
	    contractProduct.setLoadingRate(model.getLoadingRate());
	    contractProduct.setBoxNum(model.getBoxNum());
	    contractProduct.setPrice(model.getPrice());
	    contractProduct.setOrderNo(model.getOrderNo());
	    contractProduct.setProductDesc(model.getProductDesc());   
	    contractProduct.setProductRequest(model.getProductRequest());
		contractProductService.saveOrUpdate(contractProduct);
		return tocreateAndList();
	}
}
