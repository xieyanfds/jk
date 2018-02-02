package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.dao.springdao.SqlDao;
import com.xy.domain.Message;
import com.xy.domain.User;
import com.xy.exception.SysException;
import com.xy.service.MessageService;
import com.xy.service.UserService;
import com.xy.utils.FastJsonUtil;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author xieyan
 * @description 留言板
 * @date 2017/12/26.
 */
public class MessageAction extends BaseAction implements ModelDriven<Message> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SqlDao sqlDao;

	private Message model = new Message();

	@Override
	public Message getModel() {
		return model;
	}

	private Page page = new Page();
	public void setPage(Page page) {
		this.page = page;
	}

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	//列表展示
	public String list(){

		//查询所有内容
		String hql = "from Message where createBy='"+this.getCurrUser().getId()+"' or receiveId = '"+this.getCurrUser().getId()+"' order by createTime desc";
		//给页面提供分页数据
		page.setUrl("messageAction_list");		//配置分页按钮的转向链接
		page = messageService.findPage(hql, page, Message.class, null);
		super.putContext("page", page);
		return "plist";
	}

	public String listReceive(){
		String hql = "from Message where receiveId='"+this.getCurrUser().getId()+"' order by createTime desc";			//查询所有接收内容
		//给页面提供分页数据
		page.setUrl("messageAction_listReceive");		//配置分页按钮的转向链接
		page = messageService.findPage(hql, page, Message.class, null);
		super.putContext("page", page);
		return "rlist";						//已接收页面
	}

	//转向新增页面
	public String tocreate(){
		//准备数据
		String hql = "from User";
		List<User>  userList = userService.find(hql, User.class, null);
		super.putContext("userList", userList);		//页面就可以访问messageList
		return "pcreate";
	}

	//新增保存
	public String insert(){
		User curUser = super.getCurrUser();
		model.setCreateBy(curUser.getId());
		model.setCreateTime(new Date());
		User user = userService.get(User.class, model.getReceiveId());
		model.setReceive(user.getUserName());
		messageService.saveOrUpdate(model);
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		String hql = "from User";
		List<User>  userList = userService.find(hql, User.class, null);
		super.putContext("userList", userList);		//页面就可以访问messageList

		//准备修改的数据
		Message obj = messageService.get(Message.class, model.getId());
		super.pushVS(obj);

		return "pupdate";
	}

	//修改保存
	public String update(){
		Message message = messageService.get(Message.class, model.getId());

		//设置修改的属性，根据业务去掉自动生成多余的属性
		message.setReceiveId(model.getReceiveId());
		message.setTitle(model.getTitle());
		User user = userService.get(User.class, model.getReceiveId());
		message.setReceive(user.getUserName());
		message.setMessageTime(model.getMessageTime());
		message.setMessage(model.getMessage());

		messageService.saveOrUpdate(message);

		return "alist";
	}

	//删除一条
	public String deleteById(){
		messageService.deleteById(Message.class, model.getId());

		return "alist";
	}


	//删除多条
	public String delete(){
		messageService.delete(Message.class, model.getId().split(", "));

		return "alist";
	}

	//查看
	public String toview(){

		String hql = "from User";
		List<User>  userList = userService.find(hql, User.class, null);
		super.putContext("userList", userList);		//页面就可以访问messageList

		Message obj = messageService.get(Message.class, model.getId());
		// 如果查看别人发送的邮件,则state 该为2
		if(obj.getReceiveId().equals(this.getCurrUser().getId())){
			//此时是接收人查看，修改状态为已读
			obj.setState(2);
			messageService.saveOrUpdate(obj); //重新保存
		}
		super.pushVS(obj);

		return "pview";			//转向查看页面
	}


	/**
	 * 未读信息
	 * select * from MESSAGE_C where receive_id='18077bdb-8dd3-4aae-95a2-078c963f8416' and state=1;
	 * @throws Exception
	 */
	public String unread() throws Exception{
		String hql = "from Message where receiveId ='"+this.getCurrUser().getId()+"' and state = 1";

		List<Message> list = messageService.find(hql, Message.class, null);

		String num = ""+list.size();
		//System.out.println(number);

		try {
			// 将数量写到前台界面上
			ServletActionContext.getResponse().getWriter().write(num);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SysException("ajax有毒");
		}
		return NONE;
	}

}
