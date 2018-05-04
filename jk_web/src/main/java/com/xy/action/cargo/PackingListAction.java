package com.xy.action.cargo;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Export;
import com.xy.domain.ExportProduct;
import com.xy.domain.PackingList;
import com.xy.domain.User;
import com.xy.service.ExportService;
import com.xy.service.PackingListService;
import com.xy.utils.DownloadUtil;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @author xieyan
 * @description 装箱单
 * @date 2017/12/26.
 */
public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {

	private Logger logger = LoggerFactory.getLogger(PackingListAction.class);

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
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			//查询所有内容
			String hql = "from PackingList where 1=1 ";
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
			//配置分页按钮的转向链接
			page.setUrl("packingListAction_list");
			page = packingListService.findPage(hql, page, PackingList.class, null);
			pushVS(page);
		} catch (Exception e) {
			logger.error("list exception:{}",e);
		}
		return "plist";
	}

	/**
	 * 转向新增页面,查询出所有已电子报运的报运单
	 * @return
	 */
	public String tocreate(){
		try {
			//准备数据,已报运但未装船的
			List<Export> list = exportService.find("from Export where state = 2 order by createTime desc", Export.class, null);
			putContext("results", list);
		} catch (Exception e) {
			logger.error("tocreate exception:{}",e);
		}
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		try {
			//添加细粒度权限控制
			//获取当前用户
			User user = super.getCurrUser();
			model.setCreateBy(user.getId());
			model.setCreateDept(user.getDept().getId());
			model.setCreateTime(new Date());

			packingListService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		//返回列表，重定向action_list
		return "alist";
	}

	//转向修改页面
	public String toupdate(){
		try {
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
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		try {
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
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "alist";
	}
	

	//删除多条
	public String delete(){
		try {
			packingListService.delete(PackingList.class, model.getId().split(", "));
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}

		return "alist";
	}
	
	//查看
	public String toview(){
		try {
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
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		return "pview";			//转向查看页面
	}
	
	//提交
	public String submit(){
		try {
			String[] split = model.getId().split(", ");
			packingListService.changeState(split,1);
		} catch (Exception e) {
			logger.error("submit exception:{}",e);
		}
		return "alist";
    }

	//取消
	public String cancel(){
		try {
			String[] split = model.getId().split(", ");
			packingListService.changeState(split,0);
		} catch (Exception e) {
			logger.error("cancel exception:{}",e);
		}
		return "alist";
	}

	public void print() throws Exception{
		try {
			//根据id查询装箱单
			PackingList pl = packingListService.get(PackingList.class, model.getId());
			//根据id 获取报运商品明细

			//设置路径
			// 读取工作薄
			String path = ServletActionContext.getServletContext().getRealPath("/") + "/make/xlsprint/tPARKINGLIST.xls";
			//获取response
			HttpServletResponse response = ServletActionContext.getResponse();

			//获取装箱单信息 根据id
			PackingList packingList = packingListService.get(PackingList.class, model.getId());

			// 抽取一些公用变量
			Row nRow = null;
			Cell nCell = null;
			int rowNo = 0;
			int cellNo = 0;

			InputStream is = new FileInputStream(path);
			Workbook wb = new HSSFWorkbook(is);

			//格式化日期
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// 读取工作表
			Sheet sheet = wb.getSheetAt(0);
			//填写或货主名称
			nRow = sheet.getRow(3);
			nCell = nRow.createCell(0);
			nCell.setCellValue(packingList.getSeller());
			//填写买方
			nRow = sheet.getRow(8);
			nCell = nRow.createCell(0);
			nCell.setCellValue(packingList.getBuyer());

			//填写发票号 Invoice No.
			nRow = sheet.getRow(15);
			nCell = nRow.createCell(0);
			nCell.setCellValue(packingList.getInvoiceNo());
			//填写时间 Date
			nRow = sheet.getRow(15);
			nCell = nRow.createCell(3);
			String invoiceDate = df.format(packingList.getInvoiceDate());
			nCell.setCellValue(invoiceDate);

			//填写唛头
			nRow = sheet.getRow(19);
			nCell = nRow.createCell(0);
			nCell.setCellValue(packingList.getMarks());
			//填写描述
			nRow = sheet.getRow(19);
			nCell = nRow.createCell(3);
			nCell.setCellValue(packingList.getDescriptions());


			//查询出所有报运单的ids
			String exportIds = packingList.getExportIds();
			String[] split = exportIds.split(",");
			for (String string : split) {
                //获取export 的信息
                Export export = exportService.get(Export.class, string);
                Set<ExportProduct> exportProducts = export.getExportProducts();
                rowNo = 19;
                nRow = sheet.getRow(19);
                nCell = nRow.getCell(2);
                CellStyle hb = nCell.getCellStyle();
                for (ExportProduct exportProduct : exportProducts) {
                    nRow = sheet.getRow(rowNo++);
                    nCell = nRow.createCell(2);
                    nCell.setCellStyle(hb);
                    nCell.setCellValue(exportProduct.getId());

                    //该商品的总数量
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(3);
                    nCell.setCellValue(exportProduct.getCnumber());
                    //设置商品的包装单位
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(4);
                    nCell.setCellValue(exportProduct.getPackingUnit());
                    //设置件数
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(6);
                    nCell.setCellValue(exportProduct.getBoxNum());
                    //设置单位
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(7);
                    nCell.setCellValue("CTNS");
                    //设置毛重
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(8);
                    nCell.setCellValue(UtilFuns.convertNull(exportProduct.getGrossWeight()));
                    //设置净重
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue(UtilFuns.convertNull(exportProduct.getNetWeight()));

                    //设置长
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue(UtilFuns.convertNull(exportProduct.getSizeLength()));
                    //设置x
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue("X");
                    //设置宽
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue(UtilFuns.convertNull(exportProduct.getSizeWidth()));
                    //设置x
                    nRow = sheet.getRow(rowNo);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue("X");
                    //设置高
                    nRow = sheet.getRow(rowNo++);
                    nCell = nRow.createCell(9);
                    nCell.setCellValue(UtilFuns.convertNull(exportProduct.getSizeHeight()));

                }
            }

			// 保存，关流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wb.write(baos);

			DownloadUtil downloadUtil = new DownloadUtil();
			downloadUtil.download(baos, response, "装箱单.xls");
		} catch (IOException e) {
			logger.error("print exception:{}",e);
		}
	}
}
