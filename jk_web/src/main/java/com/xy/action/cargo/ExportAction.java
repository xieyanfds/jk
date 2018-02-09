package com.xy.action.cargo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Contract;
import com.xy.domain.Export;
import com.xy.domain.ExportProduct;
import com.xy.domain.User;
import com.xy.service.ContractService;
import com.xy.service.ExportProductService;
import com.xy.service.ExportService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import com.xy.webservice.EpService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xieyan
 * @description 合同管理
 * @date 2017/12/26.
 */
public class ExportAction extends BaseAction implements ModelDriven<Export>{
private static final long serialVersionUID = 1L;
	
	private Export model = new Export();
	
	public Export getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page page = new Page();;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	@Autowired
	private ExportService exportService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ExportProductService exportProductService;

	@Resource(name="exportClient")
	private EpService epService;

	/**
	 * 查询状态唯一的所有购销合同
	 * @return
	 * @throws Exception
	 */
	public String contractList() throws Exception {
		//查询状态为1的购销合同
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		String hql = "from Contract where state=1";
		//分页查询
		contractService.findPage(hql, page, Contract.class, null);
		page.setUrl("exportAction_contractList");
		 
		//放入值栈
		super.pushVS(page);
		
		return "contractList";
	}
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//根据权限控制显示的数据
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询所有内容
		String parameter = request.getParameter("page.pageNo");
		if(parameter!=null){
			page.setPageNo(Integer.parseInt(parameter));
		}
		String hql = "from Export where 1=1";
		User currUser = super.getCurrUser();
		//获取用户等级
		int degree = currUser.getUserInfo().getDegree();
		if(degree==4){
			//说明是员工
			hql+=" and createBy = '"+currUser.getId()+"'";
			
		}else if(degree==3){
			//说明是部门经理，管理本部门
			hql+=" and createDept = '"+currUser.getDept().getId()+"'";
			
		}else if(degree==2){
			//说明是管理本部门及下属部门？？？？？
			hql+=" and createDept in (select id from Dept where id like '"+super.getCurrUser().getDept().getId()+"%')";
		}else if(degree==1){
			//说明是副总？？？？？
			
		}else if(degree==0){
			//说明是总经理
			
		}
		page = exportService.findPage(hql, page, Export.class, null);
		//设置分页的url地址
		page.setUrl("exportAction_list");
		//将page对象压入栈顶
		pushVS(page);
		return "list";
	}
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String toview()throws Exception{
		Export export = exportService.get(Export.class, model.getId());
		pushVS(export);
		return "toview";
	}
	/**
	 * 新增，跳转页面
	 * @return
	 * @throws Exception
	 */
	public String tocreate()throws Exception{
		
		return "tocreate";
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert()throws Exception{
		//添加细粒度权限控制
		//获取当前用户
		User user = super.getCurrUser();
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date());

		exportService.saveOrUpdate(model);
		return contractList();
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		//1.根据id,得到一个对象
		Export export = exportService.get(Export.class, model.getId());
		
		//2.将对象放入值栈中
		pushVS(export);
		
		//3.addTRRecord("mRecordTable", "id", "productNo", "cnumber", "grossWeight", "netWeight", "sizeLength", "sizeWidth", "sizeHeight", "exPrice", "tax");
		StringBuilder sb = new StringBuilder();
		Set<ExportProduct> exportProducts = export.getExportProducts();//关联级别的数据检索
		//遍历集合
		for(ExportProduct ep :exportProducts){
			sb.append("addTRRecord(\"mRecordTable\", \"").append(ep.getId());
			sb.append("\", \"").append(ep.getProductNo());
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getCnumber()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getGrossWeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getNetWeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeLength()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeWidth()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getSizeHeight()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getExPrice()));
			sb.append("\", \"").append(UtilFuns.convertNull(ep.getTax())).append("\");");
		}
		
		 //4.将接好的串放入值栈中
		putContext("mRecordData", sb.toString());
		
		//5.跳页面
		return "toupdate";
	}
	private String mr_changed[];
	private String mr_id[];
	private Integer mr_cnumber[];
	private Double mr_grossWeight[];
	private Double mr_netWeight[];
	private Double mr_sizeLength[];
	private Double mr_sizeWidth[];
	private Double mr_sizeHeight[];
	private Double mr_exPrice[];
	private Double mr_tax[];

	public void setMr_changed(String[] mr_changed) {
		this.mr_changed = mr_changed;
	}
	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}
	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}
	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}
	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}
	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}
	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}
	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}
	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}
	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		// 先查询
		Export export = exportService.get(Export.class, model.getId());
		
		//设置修改的属性

		//2.设置修改的属性
		export.setInputDate(model.getInputDate());
		
		
        export.setLcno(model.getLcno());
        export.setConsignee(model.getConsignee());
        export.setShipmentPort(model.getShipmentPort());
        export.setDestinationPort(model.getDestinationPort());
        export.setTransportMode(model.getTransportMode());
        export.setPriceCondition(model.getPriceCondition());
        export.setMarks(model.getMarks());//唛头是指具有一定格式的备注信息
        export.setRemark(model.getRemark());
       
        Set<ExportProduct> epSet =new HashSet<ExportProduct>();//商品列表
//        Set<ExportProduct> epSet = export.getExportProducts();
//        epSet.clear();
        for(int i=0;i<mr_id.length;i++){
        	//遍历数组，得到每个商品对象
        	ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
        	
        	if("1".equals(mr_changed[i])){
        		ep.setCnumber(mr_cnumber[i]);
        		ep.setGrossWeight(mr_grossWeight[i]);
        		ep.setNetWeight(mr_netWeight[i]);
        		ep.setSizeLength(mr_sizeLength[i]);
        		ep.setSizeWidth(mr_sizeWidth[i]);
        		ep.setSizeHeight(mr_sizeHeight[i]);
        		ep.setExPrice(mr_exPrice[i]);
        		ep.setTax(mr_tax[i]);
        	}
        	epSet.add(ep);
        }
        
        //设置报运单与商品列表的关系 
        export.setExportProducts(epSet);
        //更新
		exportService.saveOrUpdate(export);
		return list();
	}
	
	public String delete()throws Exception{
		exportService.delete(Export.class, model.getId().split(", "));
		
		return list();
	}
	/**
	 * 提交
	 * @return
	 * @throws Exception
	 */
	public String submit()throws Exception{
		String[] split = model.getId().split(", ");
		exportService.changeState(split, 1);
		
		return list();
	}
	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		String[] split = model.getId().split(", ");
		exportService.changeState(split, 0);
		
		return list();
	}
	/**
	 * 出口报运
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		//获取报运单对象
		Export export = exportService.get(Export.class, model.getId());
		
		//转json串
		String jsonString = JSON.toJSONString(export);
		System.out.println(jsonString);
		//调用webservice服务
		String resultJson = epService.exportE(jsonString);
		//处理返回结果
		Export result = JSON.parseObject(resultJson,Export.class);
		exportService.updateE(result);
		
		//跳转
		return list();
	}
}
