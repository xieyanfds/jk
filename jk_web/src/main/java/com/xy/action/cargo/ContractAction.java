package com.xy.action.cargo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xy.action.print.ContractPrint;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Contract;
import com.xy.domain.User;
import com.xy.service.ContractService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author xieyan
 * @description 购销合同
 * @date 2017/12/26.
 */
public class ContractAction extends BaseAction implements ModelDriven<Contract>{
private static final long serialVersionUID = 1L;
	
	private Contract model = new Contract();
	
	@Override
	public Contract getModel() {
		return model;
	}

	/**
	 * 分页查询
	 */
	private Page<Contract> page = new Page<Contract>();;
	public Page<Contract> getPage() {
		return page;
	}
	public void setPage(Page<Contract> page) {
		this.page = page;
	}

	@Autowired
	private ContractService contractService;
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//根据权限控制显示的数据
		/*HttpServletRequest request = ServletActionContext.getRequest();
		String parameter = request.getParameter("page.pageNo");
		if(!StringUtils.isEmpty(parameter)){
			page.setPageNo(Integer.parseInt(parameter));
		}*/
		//查询所有内容
		String hql = "from Contract where 1=1";
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
			//需要创建一个中间表
			hql+=" and createBy in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='员工') "
					+ "or createDept in (select id from 中间表 where uid = "+super.getCurrUser().getId()+" and type='部门')";
		}else if(degree==0){
			//说明是总经理
			
		}
		hql += " order by createTime desc";
		page = contractService.findPage(hql, page, Contract.class, null);
		//设置分页的url地址
		page.setUrl("contractAction_list");
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
		Contract contract = contractService.get(Contract.class, model.getId());
		pushVS(contract);
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
		
		contractService.saveOrUpdate(model);
		return "dlist";
	}
	/**
	 * 修改,跳转页面
	 * @return
	 * @throws Exception
	 */
	public String toupdate()throws Exception{
		Contract contract = contractService.get(Contract.class, model.getId());
		pushVS(contract);
		return "toUpdate";
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		// 先查询
		Contract contract = contractService.get(Contract.class, model.getId());
		
		//设置修改的属性
		contract.setCustomName(model.getCustomName());
		contract.setPrintStyle(model.getPrintStyle());
		contract.setContractNo(model.getContractNo());
		contract.setOfferor(model.getOfferor());
		contract.setInputBy(model.getInputBy());
		contract.setCheckBy(model.getCheckBy());
		contract.setInspector(model.getInspector());
		contract.setSigningDate(model.getSigningDate());
		contract.setShipTime(model.getShipTime());
		contract.setDeliveryPeriod(model.getDeliveryPeriod());
        contract.setImportNum(model.getImportNum());
        contract.setTradeTerms(model.getTradeTerms());
        contract.setCrequest(model.getCrequest());
        contract.setRemark(model.getRemark());
		contract.setUpdateBy(super.getCurrUser().getId());
		contract.setUpdateTime(new Date());
        
        //更新
		contractService.saveOrUpdate(contract);
		return "dlist";
	}
	
	public String delete()throws Exception{
		contractService.delete(Contract.class, model.getId().split(", "));
		
		return list();
	}
	/**
	 * 提交
	 * @return
	 * @throws Exception
	 */
	public String submit()throws Exception{
		String[] split = model.getId().split(", ");
		contractService.changeState(split, 1);
		
		return list();
	}
	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		String[] split = model.getId().split(", ");
		contractService.changeState(split, 0);
		
		return list();
	}
	/**
	 * 打印
	 */
	public String print() throws Exception {
		//1.根据购销合同的id,得到购销合同对象
		Contract contract = contractService.get(Contract.class, model.getId());
		
		//2.指定path
		//应用程序的根路径
		String path = ServletActionContext.getServletContext().getRealPath("/");

		//3.指定response
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ContractPrint cp = new ContractPrint();
		cp.print(contract, path, response);
		
		return NONE;
	}
}
