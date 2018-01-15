package com.xy.action.print;

import com.xy.domain.Contract;
import com.xy.domain.Export;
import com.xy.domain.Invoice;
import com.xy.domain.PackingList;
import com.xy.service.ContractService;
import com.xy.service.ExportService;
import com.xy.service.InvoiceService;
import com.xy.service.PackingListService;
import com.xy.service.impl.ContractServiceImpl;
import com.xy.service.impl.ExportServiceImpl;
import com.xy.service.impl.InvoiceServiceImpl;
import com.xy.service.impl.PackingListServiceImpl;
import com.xy.utils.DownloadUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author xieyan
 * @description 打印发票
 * @date 2017/12/26.
 */
@Component
public class InvoicePrint {
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	PackingListService packingListService;
	@Autowired
	ExportService exportService;
	@Autowired
	ContractService contractService;

	public void print(Invoice model)throws IOException {
		// 读取工作薄
		String path = ServletActionContext.getServletContext().getRealPath("/") + "/make/xlsprint/tINVOICE.xls";
		System.out.println(path);
		InputStream is = new FileInputStream(path);

		Workbook wb = new HSSFWorkbook(is);

		// 读取工作表
		Sheet sheet = wb.getSheetAt(0);

		// 获取发票
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
		
		// 获取要操作的单元格(发票ID)
		Row row0 = sheet.getRow(15);
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue(obj.getId());// 向单元格中添值

		// 获取要操作的单元格(发票日期)
		Cell cell1 = row0.createCell(2);
		// 将date格式化（日期转字符串）
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(obj.getCreateTime());
		cell1.setCellValue(str);// 向单元格中添值

		// 获取要操作的单元格(发票号)
		Cell cell2 = row0.createCell(5);
		cell2.setCellValue(obj.getScNo());// 向单元格中添值

		// 获取要操作的单元格(提单号)
		Cell cell3 = row0.createCell(9);
		cell3.setCellValue(obj.getBlNo());// 向单元格中添值

		// 获取要操作的单元格(买方)
		Row row1 = sheet.getRow(8);
		Cell cell11 = row1.createCell(0);
		//获取买方和卖方
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		cell11.setCellValue(packingList.getBuyer());// 向单元格中添值
		Row row2 = sheet.getRow(3);
		Cell cell21 = row2.createCell(0);
		cell21.setCellValue(packingList.getSeller());// 向单元格中添值
		
		// 获取要操作的单元格(唛头)
		Row row3 = sheet.getRow(23);
		Cell cell31 = row3.createCell(0);
		cell31.setCellValue(packingList.getMarks());// 向单元格中添值
		
		// 获取要操作的单元格(单位)
		Cell cell32 = row3.createCell(2);
		cell32.setCellValue(packingList.getDescriptions());
		Cell cell33 = row3.createCell(7);
		cell33.setCellValue("箱");
		
		//发票所包含的报运单，获取总数量，总金额
		String exportIds = packingList.getExportIds();
		
		//遍历出每一个报运单
		int quantity = 0;//总箱数
		double amount = 0.0;//总金额
		String[] eps = exportIds.split(", ");//按,拆分字符串
		for (String ep : eps) {
			//获得总箱数
			Export export = exportService.get(Export.class, ep);
			quantity += export.getBoxNums();
			//获取每一个购销合同
			String contractIds = export.getContractIds();
			String[] cis = contractIds.split(",");
			for (String ci : cis) {
			Contract contract = contractService.get(Contract.class, ci);
			//计算发票总金额
			amount += contract.getTotalAmount();
			}
		}
		
		//将总箱数写到表格中
		Cell cell34 = row3.createCell(5);
		cell34.setCellValue(quantity);
		
		//将发票总金额写到表中
		Cell cell35 = row3.createCell(9);
		cell35.setCellValue(amount+"  RMB");
		
		// 保存，关流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		HttpServletResponse response = ServletActionContext.getResponse();
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(baos, response, "发票.xls");
	}
}
