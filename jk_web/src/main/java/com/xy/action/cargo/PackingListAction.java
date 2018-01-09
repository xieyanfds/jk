package com.xy.action.cargo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Export;
import com.xy.domain.PackingList;
import com.xy.domain.ShippingOrder;
import com.xy.domain.User;
import com.xy.service.ExportService;
import com.xy.service.PackingListService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xieyan
 * @description 装箱单
 * @date 2017/12/26.
 * toview always need to optimize
 */
public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {

	@Autowired
	private PackingListService packingListService;

	//注入报运单
	@Autowired
	private ExportService exportService;

	//model驱动
	private PackingList model = new PackingList();
	@Override
	public PackingList getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from PackingList ";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("packingListAction_list");		//配置分页按钮的转向链接
		page = packingListService.findPage(hql, page, PackingList.class, null);
		pushVS(page);
		return "plist";						//page list
	}

	/**
	 * 转向新增页面,查询出所有已电子报运的报运单
	 * @return
	 */
	public String tocreate(){
		//准备数据,已报运但未装船的
		List<Export> list = exportService.find("from Export where state = 2", Export.class, null);
		putContext("results", list);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		//添加细粒度权限控制
		//获取当前用户
		User user = super.getCurrUser();
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date());

		packingListService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
		//准备修改的数据
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		pushVS(obj);
		//查询还没有装船的
		List<Export> list = exportService.find("from Export where state = 2", Export.class, null);
		String exportIds = obj.getExportIds();
		String idS[] = exportIds.split(", ");
		//将自己装船的报运查询出来添加到集合中
		for (String id : idS) {
			Export export = exportService.get(Export.class, id);
			list.add(export);
		}
		putContext("exports", list);
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		packingList.setSeller(model.getSeller());
		packingList.setBuyer(model.getBuyer());
		packingList.setInvoiceNo(model.getInvoiceNo());
		packingList.setInvoiceDate(model.getInvoiceDate());
		packingList.setMarks(model.getMarks());
		packingList.setDescriptions(model.getDescriptions());
		packingList.setExportIds(model.getExportIds());
		packingList.setExportNos(model.getExportNos());

		//获取装箱单下的原来的报运单,将状态修改为已报运，未装船
		String exportIds = packingList.getExportIds();
		String[] oldid = exportIds.split(", ");
		exportService.changeState(oldid, 2);
	
		packingListService.saveOrUpdate(packingList);
		
		return "alist";
	}
	

	//删除多条
	public String delete(){
		packingListService.delete(PackingList.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		PackingList obj = packingListService.get(PackingList.class, model.getId());
        pushVS(obj);
		String exportIds = obj.getExportIds();
		if (!UtilFuns.isEmpty(exportIds)) {
			String[] split = exportIds.split(", ");
			List<Export> list = new ArrayList<Export>();
			
			for (String id : split) {
				list.add(exportService.get(Export.class, id));
			}
			putContext("results", list);
			
		}
		return "pview";			//转向查看页面
	}
	
	//提交
	public String submit(){
        String[] split = model.getId().split(", ");
        packingListService.changeState(split,1);
        return "alist";
    }

	//取消
	public String cancel(){
        String[] split = model.getId().split(", ");
        packingListService.changeState(split,0);
		return "alist";
	}
}
