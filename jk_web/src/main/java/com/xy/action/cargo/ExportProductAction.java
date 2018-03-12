package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.ExportProduct;
import com.xy.domain.Factory;
import com.xy.service.ExportProductService;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xieyan
 * @description 报运单下货物
 * @date 2018/03/01.
 */
public class ExportProductAction extends BaseAction implements ModelDriven<ExportProduct>{
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(ExportProductAction.class);

	private ExportProduct model = new ExportProduct();
	
	@Override
	public ExportProduct getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<ExportProduct> page = new Page<>();
	public Page<ExportProduct> getPage() {
		return page;
	}
	public void setPage(Page<ExportProduct> page) {
		this.page = page;
	}

	@Autowired
	private ExportProductService exportProductService;

	@Autowired
	private FactoryService factoryService;

	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String toview()throws Exception{
		//查询货物
		try {
			ExportProduct contractProduct = exportProductService.get(ExportProduct.class, model.getId());
			pushVS(contractProduct);
			//查询生产厂家
			List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '货物'", Factory.class, null);

			putContext("factoryList", fList);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		return "toview";
	}

}
