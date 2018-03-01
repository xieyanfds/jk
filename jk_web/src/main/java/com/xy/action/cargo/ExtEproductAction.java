package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.ExtEproduct;
import com.xy.domain.Factory;
import com.xy.service.ExtEproductService;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xieyan
 * @description 报运单下货物附件
 * @date 2018/03/01.
 */
public class ExtEproductAction extends BaseAction implements ModelDriven<ExtEproduct>{
private static final long serialVersionUID = 1L;
	
	private ExtEproduct  model = new ExtEproduct();
	@Override
	public ExtEproduct getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<ExtEproduct> page = new Page<>();
	public Page<ExtEproduct> getPage() {
		return page;
	}
	public void setPage(Page<ExtEproduct> page) {
		this.page = page;
	}

	@Autowired
	private ExtEproductService extEproductService;
	@Autowired
	private FactoryService factoryService;
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String toview()throws Exception{
		//查询货物
		ExtEproduct extEproduct = extEproductService.get(ExtEproduct.class, model.getId());
		pushVS(extEproduct);
		//查询生产厂家
		List<Factory> fList = factoryService.find("from Factory where state = 1 and ctype = '附件'", Factory.class, null);

		putContext("factoryList", fList);
		return "toview";
	}
}
