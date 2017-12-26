package com.xy.action.cargo;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Export;
import com.xy.domain.PackingList;
import com.xy.service.ExportService;
import com.xy.service.PackingListService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;


/**
 * @Description:	PackingList
 * @Author:			
 * @Company:		
 * @CreateDate:		2016-8-15 16:07:10
 */

public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {
	//注入service
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}
	//注入报运单
	private ExportService exportService;
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	//model驱动
	private PackingList model = new PackingList();
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
		String hql = "from PackingList o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("packingListAction_list");		//配置分页按钮的转向链接
		page = packingListService.findPage(hql, page, PackingList.class, null);
		pushVS(page);
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据
		List<Export> list = exportService.find("from Export where state = 1", Export.class, null);
		putContext("results", list);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		packingListService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
		//准备修改的数据
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		pushVS(obj);
		String exportids = obj.getExportIds();
		System.out.println(exportids);
		String ids[] = exportids.split(", ");
		List<Export> exports = new ArrayList<Export>();
		for (String id : ids) {
			Export export = exportService.get(Export.class, id);
			exports.add(export);
		}
		putContext("exports", exports);
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
		packingList.setState(model.getState());
		packingList.setCreateBy(model.getCreateBy());
		packingList.setCreateDept(model.getCreateDept());
		packingList.setCreateTime(model.getCreateTime());
		//获取装箱单下的原来的报运单
		String exportIds = packingList.getExportIds();
		String[] oldid = exportIds.split(", ");
	
		//获取新的勾选后的报运单
		String exportids = model.getExportIds();
		String[] newid = exportids.split(", ");
		exportService.changeState(newid, 1);
		
		packingListService.saveOrUpdate(packingList);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		packingListService.deleteById(PackingList.class, model.getId());
		
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
			PackingList pl = packingListService.get(PackingList.class, model.getId());
			pl.setState(1);
			packingListService.saveOrUpdate(pl);
			return "alist";
		}
		
		//取消
		public String cancel(){
			PackingList pl = packingListService.get(PackingList.class, model.getId());
			pl.setState(0);
			packingListService.saveOrUpdate(pl);
			return "alist";
		}
}
