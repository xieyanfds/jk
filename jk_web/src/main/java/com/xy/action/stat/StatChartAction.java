package com.xy.action.stat;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.service.StatChartService;
import com.xy.utils.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class StatChartAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Autowired
	private StatChartService statChartService;
	/**
	 * 生产厂家销售情况
	 * @return
	 * @throws Exception
	 */
	public String factorysale() throws Exception {
		//查询数据
		String sql = "select factory_name,sum(amount) samount from contract_product_c group by factory_name order by samount desc";
		List<String> list = statChartService.execSQL(sql);
		
		//拼接数据
		String sb = getPieData(list);
		
		//输出到文件
		writeToFile("stat\\chart\\factorysale\\data.xml",sb);
	    
		return "factorysale";
	}
	/**
	 * 新版amChart实现
	 * 前十五名产品销售排行
	 */
	public String productsale() throws Exception {
		//1.执行sql语句，得到统计结果
		String sql = "select product_no,sum(amount) samount from contract_product_c group by product_no order by samount desc limit 0,15";
		List<String> list = statChartService.execSQL(sql);
		
	    //2.组织符合要求的json数据
	    StringBuilder sb = new StringBuilder();
	    sb.append("[");
	    /**
	     *      {
                    "country": "USA",
                    "visits": 4025,
                    "color": "#FF0F00"
                }
	     */
	    String colors[]={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D52D1","#2A0CD0","#8A0CCF","#CD0D74","#754DEB"};
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("{").append("\"product_no\":\"").append(list.get(i).substring(0, 3)).append("\",")
	    	              .append("\"samount\":\"").append(list.get((++i))).append("\",")
	    	              .append("\"color\":\"").append(colors[j++]).append("\"")
	    	.append("}").append(",");
	    	if(j>=colors.length){
	    		j=0;
	    	}
	    }
	    sb.delete(sb.length()-1, sb.length());
	    
	    sb.append("]");
	    
	    //3.将json数据放入值栈中
	    super.putContext("result", sb.toString());
	    
	    //4.返回结果
		return "productsale1";
	}
	/**
	 * 旧版
	 * 前十五名产品销售排行
	 * @return
	 * @throws Exception
	 */
	/*public String productsale() throws Exception {
		//查询数据
		String sql = "select product_no,sum(amount) samount from contract_product_c group by product_no order by samount desc limit 0,15";
		List<String> list = statChartService.execSQL(sql);
		
		//拼接数据
		String sb = genBarData("JavaEE28期产品销量排名",list);
		
		//输出到文件
		writeToFile("stat\\chart\\productsale\\data.xml",sb);
	    
		return "productsale";
	}*/
	/**
	 * 系统访问压力图
	 * @return
	 * @throws Exception
	 */
	public String onlineinfo() throws Exception {
		//查询数据
		String sql = "select a.a1, ifnull(b.c,0) from (select * from online_info_t) a left join (select date_format(login_time,'%H') a1, count(*) c" 
				+" from login_log_p group by  date_format(login_time,'%H') order by a1) b on (a.a1=b.a1) order by a.a1";
		List<String> list = statChartService.execSQL(sql);
		
		//拼接数据
		String sb = genBarData("",list);
		
		//输出到文件
		writeToFile("stat\\chart\\onlineinfo\\data.xml",sb);
	    
		return "onlineinfo";
	}
	/**
	 * 输出到文件
	 * @param sb
	 * @throws FileNotFoundException
	 */
	private void writeToFile(String filePath,String sb) throws FileNotFoundException {
		FileUtil fileUtil = new FileUtil();
	    String sPath = ServletActionContext.getServletContext().getRealPath("/");
	    fileUtil.createTxt(sPath,filePath , sb, "UTF-8");
	}
	/**
	 * 生成饼图的数据源
	 * @param list
	 * @return
	 */
	private String getPieData(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<pie>");
		
		for(int i=0;i<list.size();i++){
			sb.append("<slice title=\""+list.get(i)+"\" pull_out=\"true\">"+list.get(++i)+"</slice>");
		}
		
		sb.append("</pie>");
		return sb.toString();
	}
	/**
	 * 生成柱状图的数据源
	 * @param list
	 * @return
	 */
	private String genBarData(String name,List<String> list) {
		StringBuilder sb = new StringBuilder();
	    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<chart><series>");
	    
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
	    	i++;
	    }
	    
	    sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
	    
	    
	    j=0;
	    for(int i=0;i<list.size();i++){
	    	i++;
	    	sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
	    }
	    
	    sb.append("</graph></graphs>");
	    sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size><text><![CDATA[<b>"+name+"</b>]]></text></label></labels>");
	    sb.append("</chart>");
		return sb.toString();
	}
}
