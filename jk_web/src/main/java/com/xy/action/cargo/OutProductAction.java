package com.xy.action.cargo;

import com.xy.action.BaseAction;
import com.xy.domain.ContractProduct;
import com.xy.service.ContractProductService;
import com.xy.utils.DownloadUtil;
import com.xy.utils.UtilFuns;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author xieyan
 * @description 出货表
 * @date 2017/12/26.
 */
public class OutProductAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(OutProductAction.class);

	/**
	 * 接受页面上的船期
	 */
	private String inputDate;
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}


	@Autowired
	private ContractProductService contractProductService;

	/**
	 * 进入出货表打印页面
	 * @return
	 * @throws Exception
	 */
	public String toedit()throws Exception{
		return "toedit";
	}

	/**
	 * 使用模板打印
	 * @return
	 * @throws Exception
	 */
	public String print() throws Exception {
		//通用变量

		try {
			int rowNo=0,cellNo=1;
			Row nRow =null;
			Cell nCell = null;


			//1.读取工作簿
			String path = ServletActionContext.getServletContext().getRealPath("/")+"/make/xlsprint/tOUTPRODUCT.xls";
			System.out.println(path);
			System.out.println(inputDate);

			InputStream is = new FileInputStream(path);
			Workbook wb = new HSSFWorkbook(is);

			//2.读取工作表
			Sheet sheet = wb.getSheetAt(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 18));

			cellNo=1;//重置

			//3.创建行对象
			//=========================================大标题=============================
			nRow = sheet.getRow(rowNo++);//读取行对象
			nCell = nRow.getCell(cellNo);
			//设置单元格的内容
			nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");//2015-01   2015-12


			//=======================================小标题=================================
			rowNo++;

			//=======================================数据输出=================================================
			nRow = sheet.getRow(rowNo);//读取第三行
			CellStyle customCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle orderNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle cNumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle factoryCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
			CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();


			String hql= "from ContractProduct  where date_format(contract.shipTime,'%Y-%m') ='"+inputDate+"'";
			List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);//查询出符合指定船期的货物列表

			for(ContractProduct cp :list){
                nRow = sheet.createRow(rowNo++);//产生数据行
                nRow.setHeightInPoints(24);//设置行高

                cellNo=1;
                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getCustomName());//客户名称
                nCell.setCellStyle(customCellStyle);//设置文本样式

                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getContractNo());//订单号--- 合同号
                nCell.setCellStyle(orderNoCellStyle);//设置文本样式

                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getProductNo());     //        货号
                nCell.setCellStyle(productNoCellStyle);//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getCnumber());//      数量
                nCell.setCellStyle(cNumberCellStyle);//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getFactoryName());//工厂名
                nCell.setCellStyle(factoryCellStyle);//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));//交期
                nCell.setCellStyle(deliveryPeriodCellStyle);//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));//船期
                nCell.setCellStyle(shipTimeCellStyle);//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getTradeTerms());//贸易条款
                nCell.setCellStyle(tradeTermsCellStyle);//设置文本样式

            }


			//======================================输出到客户端（下载）========================================
			DownloadUtil downUtil = new DownloadUtil();

			ByteArrayOutputStream  baos = new ByteArrayOutputStream();//流  内存中的缓存区
			wb.write(baos);//将excel表格中的内容输出到缓存
			baos.close();//刷新缓存


			HttpServletResponse response = ServletActionContext.getResponse();//得到response对象

			downUtil.download(baos, response, "出货表.xls");//如果是中文，下载时可能会产生乱码，如何解决？
		} catch (Exception e) {
			logger.error("print exception:{}",e);
		}
		return NONE;
	}
	/**
	* 打印出货表
	* @return
	* @throws Exception
	*/
	public String printNotTemplate() throws Exception {

		try {
			//通用变量
			int rowNo=0,cellNo=1;
			Row nRow =null;
			Cell nCell = null;


			//1.创建工作簿
			Workbook wb = new HSSFWorkbook();

			//2.创建工作表
			Sheet sheet = wb.createSheet();

			//设置列宽   本身是个bug会出现一些偏差
			sheet.setColumnWidth(cellNo++, 26*256);
			sheet.setColumnWidth(cellNo++, 11*256);
			sheet.setColumnWidth(cellNo++, 29*256);
			sheet.setColumnWidth(cellNo++, 12*256);
			sheet.setColumnWidth(cellNo++, 15*256);
			sheet.setColumnWidth(cellNo++, 10*256);
			sheet.setColumnWidth(cellNo++, 10*256);
			sheet.setColumnWidth(cellNo++, 8*256);

			cellNo=1;//重置

			//3.创建行对象
			//=========================================大标题=============================
			nRow = sheet.createRow(rowNo++);//创建行对象
			nRow.setHeightInPoints(36);//设置行高
			nCell = nRow.createCell(cellNo);//创建单元格对象

			//合并单元格 起始行，结束行，起始列，结束列
			sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));//横向合并单元格

			// 单元格合并
			// 四个参数分别是：起始行，起始列，结束行，结束列
			//    sheet.addMergedRegion(new Region(0, (short) (celln + 1), 0,
			//            (short) (celle + 1)));

			//设置单元格的内容
			nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");//2015-01   2015-12

			//设置单元格样式
			nCell.setCellStyle(this.bigTitle(wb));


			//=======================================小标题=================================
			String titles[] = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};

			//创建小标题的行对象
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(26.25f);//设置行高

			//创建单元格对象，并设置内容 ，并设置样式
			for(String title :titles){
                nCell = nRow.createCell(cellNo++);//创建单元格对象
                nCell.setCellValue(title);//设置内容
                nCell.setCellStyle(this.title(wb));//设置样式
            }

			//=======================================数据输出=================================================
			String hql= "from ContractProduct  where date_format(contract.shipTime,'%Y-%m') ='"+inputDate+"'";
			List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);//查询出符合指定船期的货物列表

			for(ContractProduct cp :list){
                nRow = sheet.createRow(rowNo++);//产生数据行
                nRow.setHeightInPoints(24);//设置行高

                cellNo=1;
                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getCustomName());//客户名称
                nCell.setCellStyle(this.text(wb));//设置文本样式

                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getContractNo());//订单号--- 合同号
                nCell.setCellStyle(this.text(wb));//设置文本样式

                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getProductNo());     //        货号
                nCell.setCellStyle(this.text(wb));//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getCnumber());//      数量
                nCell.setCellStyle(this.text(wb));//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getFactoryName());//工厂名
                nCell.setCellStyle(this.text(wb));//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));//交期
                nCell.setCellStyle(this.text(wb));//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));//船期
                nCell.setCellStyle(this.text(wb));//设置文本样式


                nCell = nRow.createCell(cellNo++);//产生单元格对象
                nCell.setCellValue(cp.getContract().getTradeTerms());//贸易条款
                nCell.setCellStyle(this.text(wb));//设置文本样式

            }


			//======================================输出到客户端（下载）========================================
			DownloadUtil downUtil = new DownloadUtil();

			ByteArrayOutputStream  baos = new ByteArrayOutputStream();//流  内存中的缓存区
			wb.write(baos);//将excel表格中的内容输出到缓存
			baos.close();//刷新缓存


			HttpServletResponse response = ServletActionContext.getResponse();//得到response对象

			downUtil.download(baos, response, "出货表.xls");//如果是中文，下载时可能会产生乱码，如何解决？
		} catch (Exception e) {
			logger.error("printNotTemplate exception:{}",e);
		}
		return NONE;
	}
	//大标题的样式
	public CellStyle bigTitle(Workbook wb){
		CellStyle style = null;
		try {
			style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)16);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗

			style.setFont(font);

			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		} catch (Exception e) {
			logger.error("bigTitle exception:{}",e);
		}

		return style;
	}
	//小标题的样式
	public CellStyle title(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
	
	//文字样式
	public CellStyle text(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
}
