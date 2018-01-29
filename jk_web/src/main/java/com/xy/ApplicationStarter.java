package com.xy;

import com.xy.queue.LogActionEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 应用程序启动器
 *
 * @author fengwei
 *
 */
@Component
public class ApplicationStarter implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware {
	private Logger logger = LoggerFactory.getLogger(getClass());


	private ApplicationContext applicationContext;


	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		// 设置JVM的DNS缓存时间
		// http://docs.amazonaws.cn/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-jvm-ttl.html
		java.security.Security.setProperty("networkaddress.cache.ttl", "60");
	}

	@PostConstruct
	public void init(){

		LogActionEngine logActionEngine = applicationContext.getBean(LogActionEngine.class,"LogActionEngine");
		logActionEngine.start();

		logger.info("jk启动成功");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


}