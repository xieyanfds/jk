package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.action.print.ShippingOrderPrint;
import com.xy.domain.PackingList;
import com.xy.domain.ShippingOrder;
import com.xy.domain.User;
import com.xy.service.PackingListService;
import com.xy.service.ShippingOrderService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/26.
 */
public class ShippingOrderAction extends BaseAction implements ModelDriven<ShippingOrder> {

	private Logger logger = LoggerFactory.getLogger(ShippingOrderAction.class);

	@Autowired
	private ShippingOrderService shippingOrderService;

	@Autowired
	private PackingListService packingListService;

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
		try {
			//查询所有内容
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			String hql = "from ShippingOrder order by createTime desc";
			//给页面提供分页数据
			//配置分页按钮的转向链接
			page.setUrl("shippingOrderAction_list");
			page = shippingOrderService.findPage(hql, page, null);
			pushVS(page);
		} catch (Exception e) {
			logger.error("list exception:{}",e);
		}
		return "list";
	}
	
	//转向新增页面
	public String tocreate(){
		try {
			//准备数据
			List<PackingList> list = packingListService.find("from PackingList where state = 1 order by createTime desc", PackingList.class, null);
			putContext("results", list);
		} catch (Exception e) {
			logger.error("tocreate exception:{}",e);
		}
		return "create";
	}
	
	//新增保存
	public String insert(){
		try {
			//添加细粒度权限控制
			//获取当前用户
			User user = super.getCurrUser();
			model.setCreateBy(user.getId());
			model.setCreateTime(new Date());
			model.setCreateDept(user.getDept().getId());

			shippingOrderService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		//返回列表，重定向action_list
		return "alist";
	}

	//转向修改页面
	public String toupdate(){
		try {
			//准备数据,查询出所有已提交装箱单
			List<PackingList> list = packingListService.find("from PackingList where state = 1", PackingList.class, null);

			//准备修改的数据
			ShippingOrder obj = shippingOrderService.get(model.getId());
			PackingList packingList = obj.getPackingList();
			list.add(packingList);

			//将原来选择的装箱单放入
			putContext("results", list);
			pushVS(obj);
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "update";
	}
	
	//修改保存
	public String update(){
		try {
			if(model.getId() != model.getPackingList().getId()){
                //说明修改了对应的装箱单
                //修改之前装箱单的状态为提交未委托
                PackingList oldPackingList = packingListService.get(PackingList.class,model.getPackingList().getId());
                oldPackingList.setState(1);
                //修改选择装箱单的状态，saveOrUpdate方法中有
                //删除之前生成的委托单
                shippingOrderService.deleteById(model.getPackingList().getId());
                //添加一条记录，新的委托单
                User user = super.getCurrUser();
                model.setCreateBy(user.getId());
                model.setCreateTime(new Date());
                model.setCreateDept(user.getDept().getId());
            }else{
                //只是修改了相应属性
            }
			shippingOrderService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "alist";
        //设置修改的属性
		/*ShippingOrder packingList = shippingOrderService.get(ShippingOrder.class, model.getPackingList().getId());

        packingList.setId(model.getId());//从原主键获得委托单数据，设置修改参数
        packingList.setOrderType(model.getOrderType());//运输方式
        packingList.setShipper(model.getShipper());//货主
        packingList.setConsignee(model.getConsignee());//提单抬头
        packingList.setNotifyParty(model.getNotifyParty());//正本通知人

        packingList.setLcNo(model.getLcNo());//信用证
        packingList.setPortOfLoading(model.getPortOfLoading());//装运港
        packingList.setPortOfTrans(model.getPortOfTrans());//转船港
        packingList.setPortOfDischarge(model.getPortOfDischarge());//卸货港

        packingList.setLoadingDate(model.getLoadingDate());//装期
        packingList.setLimitDate(model.getLimitDate());//效期
        packingList.setIsBatch(model.getIsBatch());//是否分批
        packingList.setIsTrans(model.getIsTrans());//是否转船

        packingList.setCopyNum(model.getCopyNum());//份数
        packingList.setRemark(model.getRemark());//扼要说明
        packingList.setSpecialCondition(model.getSpecialCondition());//运输要求
        packingList.setFreight(model.getFreight());//运费说明

        packingList.setCheckBy(model.getCheckBy());//复核人*/


	}
	
	//删除多条
	public String delete(){
		try {
			shippingOrderService.delete(model.getId().split(", "));
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}

		return "alist";
	}
	
	//查看
	public String toview(){
		try {
			ShippingOrder obj = shippingOrderService.get(model.getId());
			pushVS(obj);

			PackingList packingList = packingListService.get(PackingList.class, model.getId());
			putContext("o",packingList);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		//转向查看页面
        return "view";
	}
	
	//提交
	public String submit(){
		try {
			String[] split = model.getId().split(", ");
			shippingOrderService.changeState(split,1);
		} catch (Exception e) {
			logger.error("submit exception:{}",e);
		}
		return "alist";
	}

	//取消
	public String cancel(){
		try {
			String[] split = model.getId().split(", ");
			shippingOrderService.changeState(split,0);
		} catch (Exception e) {
			logger.error("cancel exception:{}",e);
		}

		return "alist";
	}

	/**
	 * 打印委托单
	 */
	public String print() throws Exception {
		try {
			//调用print方法
			WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ShippingOrderPrint shippingOrderPrint = (ShippingOrderPrint)currentWebApplicationContext.getBean("shippingOrderPrint");
			shippingOrderPrint.print(model);
		} catch (Exception e) {
			logger.error("print exception:{}",e);
		}
		return NONE;
	}
}
