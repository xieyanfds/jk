package com.xy.action;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.dao.springdao.SqlDao;
import com.xy.domain.Message;
import com.xy.domain.User;
import com.xy.service.MessageService;

/**
 * 与留言相关的action
 * 
 * @author joseph
 *
 */
public class MessageAction extends BaseAction implements ModelDriven<Message> {
	private SqlDao sqlDao;

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	private Message model = new Message();

	@Override
	public Message getModel() {
		return model;
	}

	private static final long serialVersionUID = 1L;
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 首页展示最新6条留言
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
	/*	// 获取当前用户
		User user = super.getCurUser();
		// 获取用户所在部门
		Dept dept = user.getDept();
		// 查询当前部门下最新的6条留言
		String sql = "select CONTENT,CREATE_BY from MESSAGE_B where CREATE_DEPT = '" + dept.getId()
				+ "' and rownum < 7";
		List result = sqlDao.executeSQL(sql);
		StringBuffer sb = new StringBuffer();
		// [{"content":"3123","createBy":"3232"},{"":"","":""},]
		sb.append("[");
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			sb.append("{\"content\":\"").append(ite.next());
			sb.append("\",\"createBy\":\"").append(ite.next());
			sb.append("\"},");
		}
		sb.delete(sb.length() - 1, sb.length()).append("]");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		System.out.println(sb.toString());
		response.getWriter().print(sb.toString());*/
		return NONE;
	}

	public String insert() throws Exception {
		User curUser = super.getCurrUser();
		model.setCreateBy(curUser.getUserName());
		model.setCreateDept(curUser.getDept().getId());
		messageService.saveOrUpdate(model);

		System.out.println(model.getContent());
		return "list";
	}

}
