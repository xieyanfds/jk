package com.xy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author fengwei //
 * @since 16/6/29 21:18
 */
@Component
public class ApplicationClosed implements ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationClosed.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("jk关闭");
        System.out.println("jk关闭");
    }

}