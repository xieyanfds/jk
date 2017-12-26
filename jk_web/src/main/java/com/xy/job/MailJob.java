package com.xy.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.xy.domain.Contract;
import com.xy.service.ContractService;

public class MailJob {
	
	private ContractService contractService;
	private SimpleMailMessage simpleMailMessage;
	private JavaMailSender javaMailSender;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
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
		//查询是否有快要到船期的货物
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String hql = "from Contract where deliveryPeriod = '"+date+"'";
		List<Contract> list = contractService.find(hql, Contract.class,null);
		//判断是否有值
		if(list!=null&&list.size()>0){
			//有需要提醒的
			for (final Contract contract : list) {
				Thread.sleep(3000);//让当前线程休眠  3秒
				
				Thread th = new Thread(new Runnable() {
					public void run() {
						simpleMailMessage.setTo("644934121@qq.com");
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
