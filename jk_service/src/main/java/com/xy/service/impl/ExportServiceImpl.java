package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.xy.dao.BaseDao;
import com.xy.domain.Contract;
import com.xy.domain.ContractProduct;
import com.xy.domain.Export;
import com.xy.domain.ExportProduct;
import com.xy.domain.ExtCproduct;
import com.xy.domain.ExtEproduct;
import com.xy.service.ExportService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;


public class ExportServiceImpl implements ExportService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public Export get(Class<Export> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(Export entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增报运单
			entity.setState(0);//状态
			
			String ids [] = entity.getContractIds().split(", ");
			StringBuilder sb  = new StringBuilder();
			//遍历每个购销合同的id,得到每个购销合同对象，并修改状态为2
			for(String id :ids){
				Contract contract = baseDao.get(Contract.class, id);
				contract.setState(2);  //修改状态
				baseDao.saveOrUpdate(contract);//更新状态
				
				sb.append(contract.getContractNo()).append(" ");
			}
			entity.setCustomerContract(sb.toString());//设置合同及确认书号
			entity.setContractIds(UtilFuns.joinStr(ids, ","));
			entity.setInputDate(new Date());//设置制单日期
			
			//通过购销合同的集合，跳跃查询出购销合同下面的货物列表   11111,22222------------->'11111','22222'
		    String hql = "from ContractProduct where contract.id in ("+UtilFuns.joinInStr(ids)+")";
		    List<ContractProduct> list = baseDao.find(hql, ContractProduct.class, null);
		    
		    //数据搬家
		    Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		    for(ContractProduct cp :list){
		    	ExportProduct ep = new ExportProduct();//报运单下的商品
		    	ep.setBoxNum(cp.getBoxNum());
		    	ep.setCnumber(cp.getCnumber());
		    	ep.setFactory(cp.getFactory());
		    	ep.setOrderNo(cp.getOrderNo());
		    	ep.setPackingUnit(cp.getPackingUnit());
		    	ep.setPrice(cp.getPrice());
		    	ep.setProductNo(cp.getProductNo());
		    	ep.setExport(entity);//设置商品与报运单    多对一
		    	
		    	
		    	epSet.add(ep);
		    	
		    	//加载购销合同下当前货物下的附件列表
		    	Set<ExtCproduct> extCSet = cp.getExtCproducts();
		    	
		    	Set<ExtEproduct> extESet = new HashSet<ExtEproduct>();//报运单下的附件列表
		    	
		    	for(ExtCproduct extC :extCSet){
		    		ExtEproduct extE = new ExtEproduct();
		    		
		    		//拷贝对象的属性
		    		BeanUtils.copyProperties(extC, extE);
		    		extE.setId(null);
		    		
		    		extE.setExportProduct(ep);//附件与商品     多对一
		    		
		    		extESet.add(extE);//向列表中添加元素
		    	}
		    	
		    	//向报运单下的商品对象中添加附件列表
		    	ep.setExtEproducts(extESet);
		    }
		    
		    //外层循环退出时，设置一个报运单下有多个商品
		    entity.setExportProducts(epSet);
		}
		baseDao.saveOrUpdate(entity);//<set name="exportProducts" cascade="all" inverse="true" order-by="ORDER_NO">
		                             //<set name="extEproducts" cascade="all" inverse="true">说明商品保存时，附件也要级联
	}

	public void saveOrUpdateAll(Collection<Export> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Export> entityClass, Serializable id) {
		Export export =  baseDao.get(entityClass, id);
		String[] ids = export.getContractIds().split(",");
		//遍历每个购销合同的id,得到每个购销合同对象，并修改状态为1
		for(String cid :ids){
			Contract contract = baseDao.get(Contract.class, cid);
			contract.setState(1);  //修改状态
			baseDao.saveOrUpdate(contract);//更新状态
		}
		baseDao.deleteById(entityClass, id);//删除一个对象
	}

	public void delete(Class<Export> entityClass, Serializable[] ids) {
		
		for(Serializable id :ids){
			this.deleteById(Export.class,id);
		}
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		 for(String id :ids){
			Export export =  baseDao.get(Export.class, id);
			export.setState(state);
			baseDao.saveOrUpdate(export);//可以不写
			/*if(state==1){
				//提交
				String[] split = export.getContractIds().split(",");
				//遍历每个购销合同的id,得到每个购销合同对象，并修改状态为2
				for(String cid :split){
					Contract contract = baseDao.get(Contract.class, cid);
					contract.setState(2);  //修改状态
					baseDao.saveOrUpdate(contract);//更新状态
				}
			}else{
				//取消
				String[] split = export.getContractIds().split(",");
				//遍历每个购销合同的id,得到每个购销合同对象，并修改状态为1
				for(String cid :split){
					Contract contract = baseDao.get(Contract.class, cid);
					contract.setState(1);  //修改状态
					baseDao.saveOrUpdate(contract);//更新状态
				}
			}*/
		 }
	}

	public void updateE(Export result) {
		//1.加载出报运单对象
		Export export = baseDao.get(Export.class, result.getId());
		
		//2.修改报运单的属性
		export.setState(result.getState());
		export.setRemark(result.getRemark());
		
		
		//3.加载出报运单下的每个商品
		Set<ExportProduct> epSet = result.getExportProducts();
		for(ExportProduct ep :epSet){
			ExportProduct epp = baseDao.get(ExportProduct.class, ep.getId());
			//4.修改报运单下的每个商品的税金
			epp.setTax(ep.getTax());
			//5.保存报运单下的每个商品的修改数据
			baseDao.saveOrUpdate(epp);
		}
		
		//6.保存报运单修改结果
		baseDao.saveOrUpdate(export);
	}	
}
