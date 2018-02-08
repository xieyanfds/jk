package com.xy.job;

import com.xy.domain.Contract;
import com.xy.domain.User;
import com.xy.service.ContractService;
import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author xieyan
 * @description
 * @date 2018/2/7.
 */
public class ContractRemindJob {

	@Autowired
	private ContractService contractService;
	@Autowired
	private SimpleMailMessage simpleMailMessage;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserService userService;

	/**
	 * 以当前时间为标准，查询出交期到期的购销合同，并进行邮件发送，以提醒负责人
	 * @throws InterruptedException 
	 */
	public void send() throws Exception{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement s : stackTrace) {
			System.out.println(s);
		}
		System.out.println("到位了。。。。。。。。。");
		//查询是否有快要到船期的货物,提前一个星期提醒，已提交但是还没有报运
		LocalDate localDate = LocalDate.now().plusWeeks(1);
		LocalDate localDate1 = LocalDate.now().plusDays(3);
		String hql = "from Contract where (deliveryPeriod = '"+localDate+"' or deliveryPeriod = '"+localDate1+"') and state = 1";
		List<Contract> list = contractService.find(hql, Contract.class,null);
		//判断是否有值

		if(list!=null&&list.size()>0){
			//有需要提醒的
			for (final Contract contract : list) {
				final User user = userService.get(User.class, contract.getCreateBy());


				Thread.sleep(3000);//让当前线程休眠  3秒
				
				Thread th = new Thread(new Runnable() {
					@Override
					public void run() {
//						simpleMailMessage.setTo("644934121@qq.com");
						simpleMailMessage.setTo(user.getUserInfo().getEmail());
						simpleMailMessage.setSubject("火烧眉毛了！");
						simpleMailMessage.setText("您的合同号"+contract.getContractNo()+"；到期时间为"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(contract.getDeliveryPeriod())+"，期望您速度去解决！");
						javaMailSender.send(simpleMailMessage);
						System.out.println("发送成功");
					}
				});
				th.start();
			}
		}else{
			//没有到期的货物
			System.out.println("没有需要提醒的合同");
		}
	}
}
