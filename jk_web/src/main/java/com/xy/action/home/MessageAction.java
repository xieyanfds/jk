package com.xy.action.home;

import com.opensymphony.xwork2.ModelDriven;
import com.xy.action.BaseAction;
import com.xy.domain.Message;
import com.xy.domain.User;
import com.xy.exception.SysException;
import com.xy.service.MessageService;
import com.xy.service.UserService;
import com.xy.utils.Page;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

	private Logger logger = LoggerFactory.getLogger(MessageAction.class);

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
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
            //自己创建的或者是自己要接收的
			String hql = "from Message where createBy='"+this.getCurrUser().getId()+"' or receiveId = '"+this.getCurrUser().getId()+"' order by createTime desc";
			//给页面提供分页数据
			page.setUrl("messageAction_list");		//配置分页按钮的转向链接
			page = messageService.findPage(hql, page, Message.class, null);
			pushVS(page);
		} catch (NumberFormatException e) {
			logger.error("list exception:{}",e);
		}
		return "plist";
	}

	public String listReceive(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//查询所有内容
			String parameter = request.getParameter("page.pageNo");
			if(!StringUtils.isEmpty(parameter)){
                page.setPageNo(Integer.parseInt(parameter));
            }
			String hql = "from Message where receiveId='"+this.getCurrUser().getId()+"' order by createTime desc";			//查询所有接收内容
			//给页面提供分页数据
			page.setUrl("messageAction_listReceive");		//配置分页按钮的转向链接
			page = messageService.findPage(hql, page, Message.class, null);
			pushVS(page);
		} catch (NumberFormatException e) {
			logger.error("listReceive exception:{}",e);
		}
		//已接收页面
		return "rlist";
	}

	//转向新增页面
	public String tocreate(){

		try {
			//准备数据
			String hql = "from User";
			List<User>  userList = userService.find(hql, User.class, null);
			super.putContext("userList", userList);		//页面就可以访问messageList
		} catch (Exception e) {
			logger.error("tocreate exception:{}",e);
		}
		return "pcreate";
	}

	//新增保存
	public String insert(){
		try {
			User curUser = super.getCurrUser();
			model.setCreateBy(curUser.getId());
			model.setCreateName(curUser.getUserInfo().getName());
			model.setCreateTime(new Date());
			User user = userService.get(User.class, model.getReceiveId());
			model.setReceive(user.getUserName());
			messageService.saveOrUpdate(model);
		} catch (Exception e) {
			logger.error("insert exception:{}",e);
		}
		//返回列表，重定向action_list
		return "alist";
	}

	//转向修改页面
	public String toupdate(){
		try {
			String hql = "from User";
			List<User>  userList = userService.find(hql, User.class, null);
			super.putContext("userList", userList);		//页面就可以访问messageList

			//准备修改的数据
			Message obj = messageService.get(Message.class, model.getId());
			super.pushVS(obj);
		} catch (Exception e) {
			logger.error("toupdate exception:{}",e);
		}
		return "pupdate";
	}

	//修改保存
	public String update(){
		try {
			Message message = messageService.get(Message.class, model.getId());

			//设置修改的属性，根据业务去掉自动生成多余的属性
			message.setReceiveId(model.getReceiveId());
			message.setTitle(model.getTitle());
			User user = userService.get(User.class, model.getReceiveId());
			message.setReceive(user.getUserName());
			message.setMessageTime(model.getMessageTime());
			message.setMessage(model.getMessage());

			messageService.saveOrUpdate(message);
		} catch (Exception e) {
			logger.error("update exception:{}",e);
		}
		return "alist";
	}

	//删除一条
	public String deleteById(){
		try {
			messageService.deleteById(Message.class, model.getId());
		} catch (Exception e) {
			logger.error("deleteById exception:{}",e);
		}
		return "alist";
	}


	//删除多条
	public String delete(){
		try {
			messageService.delete(Message.class, model.getId().split(", "));
		} catch (Exception e) {
			logger.error("delete exception:{}",e);
		}
		return "alist";
	}

	//查看
	public String toview(){

		try {
			String hql = "from User";
			List<User>  userList = userService.find(hql, User.class, null);
			super.putContext("userList", userList);

			Message obj = messageService.get(Message.class, model.getId());
			// 如果查看别人发送的邮件,则state 该为2
			if(obj.getReceiveId().equals(getCurrUser().getId())){
                //此时是接收人查看，修改状态为已读
                obj.setState(2);
                messageService.saveOrUpdate(obj); //重新保存,快照机制，session延长
            }
			super.pushVS(obj);
		} catch (Exception e) {
			logger.error("toview exception:{}",e);
		}
		//转向查看页面
		return "pview";
	}

	/**
	 * 主页查看，返回键冲突
	 * @return
	 */
	public String toview_(){
		try {
			String hql = "from User";
			List<User>  userList = userService.find(hql, User.class, null);
			super.putContext("userList", userList);
			//此时接收人必定是自己，修改状态
			Message obj = messageService.get(Message.class, model.getId());
			obj.setState(2);
			//重新保存,快照机制，session延长
			messageService.saveOrUpdate(obj);
			super.pushVS(obj);
		} catch (Exception e) {
			logger.error("toview_ exception:{}",e);
		}
		//转向查看页面
		return "pview_";
	}


	/**
	 * 未读信息
	 * select * from MESSAGE_C where receive_id='18077bdb-8dd3-4aae-95a2-078c963f8416' and state=1;
	 * @throws Exception
	 */
	public String unread() throws Exception{
		try {
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
		} catch (SysException e) {
			logger.error("unread exception:{}",e);
		}
		return NONE;
	}

}
