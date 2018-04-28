package com.xy.action;

import com.xy.dao.springdao.SqlDao;
import com.xy.domain.Message;
import com.xy.domain.User;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xieyan
 * @description 分发操作
 * @date 2017/12/26.
 */
public class HomeAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(HomeAction.class);

	@Autowired
	private SqlDao sqlDao;

	private String moduleName;		//动态指定跳转的模块，在struts.xml中配置动态的result
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String fmain(){
		return "fmain";
	}
	
	public String title(){
		//获取session
		//User curUser = (User)session.get(SysConstant.CURRENT_USER_INFO);
		//ActionContext.getContext().getValueStack().push(curUser);
		
		return "title";
	}

	//转向moduleName指向的模块
	public String tomain(){
		try {
			if(moduleName.equals("home")) {
                // 加载最新6条留言
                // 获取当前用户
                User user = super.getCurrUser();
                // 查询当前用户下最新的6条留言
                String sql = "select * from MESSAGE_C where RECEIVE_ID = '" + user.getId() + "' and state = 1 order by create_time desc limit 0,6";
                List<Map<String, Object>> result = sqlDao.executeSQLforListMap(sql);
                List<Message> megList = new ArrayList<>();
                for (Map<String, Object> map : result) {

                    Message message = new Message();
                    message.setId((String) map.get("MESSAGES_ID"));
					message.setCreateName((String) map.get("CREATE_NAME"));
					message.setTitle((String) map.get("TITLE"));
					message.setMessage((String)map.get("MESSAGE"));
					message.setCreateTime((Date) map.get("create_time"));
                    megList.add(message);
                }

                super.putContext("megList", megList);
            }
		} catch (Exception e) {
			logger.error("tomain exception:{}",e);
		}
		return "tomain";
	}
	public String toleft(){
		//获取request
		//String moduleName = (String)request.get("moduleName");
		HttpServletRequest request = ServletActionContext.getRequest();
		String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(request.getSession().getCreationTime()));
		System.out.println("session init time : "+format);
		String formatl = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(request.getSession().getLastAccessedTime()));
		System.out.println("session LastAccessed time : "+formatl);
		int formatm = request.getSession().getMaxInactiveInterval();
		System.out.println("session MaxInactiveInterval time : "+formatm);
		//this.setModuleName(moduleName);

		return "toleft";
	}
	public void tomodule(){
		session.put("moduleRemark",moduleName);
		System.out.println("moduleRemark："+moduleName);
		System.out.println("im here-------------------------------");
	}

}
