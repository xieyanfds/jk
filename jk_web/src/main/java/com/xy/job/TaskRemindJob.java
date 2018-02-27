package com.xy.job;

import com.xy.domain.Contract;
import com.xy.domain.TaskList;
import com.xy.domain.User;
import com.xy.service.ContractService;
import com.xy.service.TaskListService;
import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xieyan
 * @description
 * @date 2018/2/7.
 */
public class TaskRemindJob {
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;

    /**
     * 以当前时间为标准，查询出有快要到截止日期的待办任务,提前三天提醒，并进行邮件发送，以提醒负责人
     * @throws InterruptedException
     */
    public void send() throws Exception{
        //查询是否有快要到截止日期的待办任务,提前三天提醒
        LocalDate localDate = LocalDate.now().plusDays(3);
        String hql = "from TaskList where endDate = '"+localDate+"'  and state = 0";
        List<TaskList> list = taskListService.find(hql, TaskList.class,null);
        //判断是否有值

        if(list!=null&&list.size()>0){
            //有需要提醒的
            for (final TaskList taskList : list) {
                final User user = userService.get(User.class, taskList.getUserId());
                Thread.sleep(3000);//让当前线程休眠  3秒

                Thread th = new Thread(new Runnable() {
                    public void run() {
//						simpleMailMessage.setTo("644934121@qq.com");
                        simpleMailMessage.setTo(user.getUserInfo().getEmail());
                        simpleMailMessage.setSubject("火烧眉毛了！");
                        simpleMailMessage.setText("您好，"+taskList.getUserName()+"！您的待办任务"+taskList.getContent()+"；到期时间为"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(taskList.getEndDate())+"，期望您速度去解决！");
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
