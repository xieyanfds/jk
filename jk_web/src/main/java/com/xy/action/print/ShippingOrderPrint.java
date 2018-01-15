package com.xy.action.print;

import com.xy.domain.Export;
import com.xy.domain.PackingList;
import com.xy.domain.ShippingOrder;
import com.xy.service.*;
import com.xy.utils.DownloadUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author xieyan
 * @description 打印委托单
 * @date 2017/12/26.
 */
@Component
public class ShippingOrderPrint {

	@Autowired
	ShippingOrderService shippingOrderService;
	@Autowired
	PackingListService packingListService;
	@Autowired
	ExportService exportService;

	public void print(ShippingOrder model) throws Exception{
		//根据id查询委托单
		ShippingOrder shippingOrder = shippingOrderService.get(ShippingOrder.class, model.getId());
		//设置路径
		// 读取工作薄
		String path = ServletActionContext.getServletContext().getRealPath("/") + "/make/xlsprint/tSHIPPINGORDER.xls";
		//获取response
		HttpServletResponse response = ServletActionContext.getResponse();

		//获取装箱单信息 根据id
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		//通过packinglist 获取到 报运单集合
		//数量Quantity
		int quantity = 0;
		//重量Gross Weight
		int grossWeight = 0;
		//体积Measurement
		int measurement = 0;
		//信用证
		String lcno = "";
		StringBuilder sb = new StringBuilder();
		String exportIds = packingList.getExportIds();
		String[] split = exportIds.split(",");
		for (String string : split) {
			//获取export 的信息
			Export export = exportService.get(Export.class, string);
			quantity += export.getBoxNums();
			grossWeight += export.getGrossWeights();
			measurement += export.getMeasurements();
			sb.append(export.getLcno());
		}
		lcno = sb.toString();
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
		nCell = nRow.createCell(cellNo);
		nCell.setCellValue(shippingOrder.getShipper());
		//填写提单抬头 Consignee
		nRow = sheet.getRow(8);
		nCell = nRow.createCell(cellNo);
		nCell.setCellValue(shippingOrder.getConsignee());
		//填写正本通知人 Orignal Notify Party
		nRow = sheet.getRow(15);
		nCell = nRow.createCell(cellNo);
		nCell.setCellValue(shippingOrder.getNotifyParty());
		//填写发票号 Invoice No.
		nRow = sheet.getRow(19);
		nCell = nRow.createCell(cellNo);
		nCell.setCellValue(packingList.getInvoiceNo());
		//填写时间 Date
		nRow = sheet.getRow(19);
		nCell = nRow.createCell(3);
		String invoiceDate = df.format(packingList.getInvoiceDate());
		nCell.setCellValue(invoiceDate);
		//填写信用证 L/C No.
		nRow = sheet.getRow(19);
		nCell = nRow.createCell(6);
		nCell.setCellValue(lcno);
		//填写装船港 Port of Loading
		nRow = sheet.getRow(23);
		nCell = nRow.createCell(0);
		nCell.setCellValue(shippingOrder.getPortOfLoading());
		//填写转船港 Port of Trans.
		nRow = sheet.getRow(23);
		nCell = nRow.createCell(3);
		nCell.setCellValue(shippingOrder.getPortOfTrans());
		//填写卸货港 Port of Discharge
		nRow = sheet.getRow(23);
		nCell = nRow.createCell(6);
		nCell.setCellValue(shippingOrder.getPortOfDischarge());
		//填写装期
		Row row = sheet.getRow(26);
		Cell cell = row.getCell(0);
		CellStyle cellStyle = cell.getCellStyle();
		nRow = sheet.getRow(27);
		nCell = nRow.createCell(0);
		// 将date格式化（日期转字符串）
		String str = df.format(shippingOrder.getLoadingDate());
		nCell.setCellValue(str);
		nCell.setCellStyle(cellStyle);
		//填写效期
		nRow = sheet.getRow(27);
		nCell = nRow.createCell(2);
		String format = df.format(shippingOrder.getLimitDate());
		nCell.setCellValue(format);
		//填写分批
		nRow = sheet.getRow(27);
		nCell = nRow.createCell(3);
		if(shippingOrder.getIsBatch()==0){
			nCell.setCellValue("否");
		}else{
			nCell.setCellValue("是");
		}
		nCell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);

		//填写转船
		nRow = sheet.getRow(27);
		nCell = nRow.createCell(5);
		if(shippingOrder.getIsTrans()==0){
			nCell.setCellValue("否");
		}else{
			nCell.setCellValue("是");
		}

		//填写份数
		nRow = sheet.getRow(27);
		nCell = nRow.createCell(7);
		nCell.setCellValue(shippingOrder.getCopyNum());
		//填写唛头
		nRow = sheet.getRow(31);
		nCell = nRow.createCell(0);
		nCell.setCellValue(packingList.getMarks());
		//填写描述
		nRow = sheet.getRow(31);
		nCell = nRow.createCell(3);
		nCell.setCellValue(shippingOrder.getRemark());
		//填写quantity
		nRow = sheet.getRow(31);
		nCell = nRow.createCell(5);
		nCell.setCellValue(quantity);
		//填写Gross Weigh
		nRow = sheet.getRow(31);
		nCell = nRow.createCell(6);
		nCell.setCellValue(grossWeight);
		//填写Measurement
		nRow = sheet.getRow(31);
		nCell = nRow.createCell(8);
		nCell.setCellValue(measurement);
		//填写运输要求 Special Condition
		nRow = sheet.getRow(37);
		nCell = nRow.createCell(1);
		nCell.setCellValue(shippingOrder.getSpecialCondition());
		//填写复合人
		nRow = sheet.getRow(43);
		nCell = nRow.createCell(7);
		nCell.setCellValue(shippingOrder.getCheckBy());

		// 保存，关流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);

		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(baos, response, "委托表.xls");
	}
}
