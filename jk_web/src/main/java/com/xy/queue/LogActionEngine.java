package com.xy.queue;

import com.xy.domain.AccessLog;
import com.xy.service.AccessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xieyan
 * @description
 * @date 2018/1/24.
 */
@Component
public class LogActionEngine extends Thread {

    @Autowired
    private AccessLogService accessLogService;

    private static final Logger logger = LoggerFactory.getLogger(LogActionEngine.class);

    private static LinkedBlockingQueue<AccessLog> queue = new LinkedBlockingQueue<>();
    private static boolean terminate = false;
    private static int timeout = 10;


    public LogActionEngine(String name){
        this.setName(name);
    }

    public LogActionEngine(){

    }

    public static void pushActionEvent(AccessLog logAction) {
        try {
            queue.put(logAction);
        } catch (Exception e) {
            logger.error("pushEvent error", e);
        }
    }

    @Override
    public void run() {
        while (!terminate) {
            try {
                AccessLog accessLog = queue.take();
                AccessLog result = accessLogService.get(AccessLog.class, accessLog.getId());
                result.setNumber(accessLog.getNumber());
                //快照机制只在事务中起作用，所以要保存
                accessLogService.saveOrUpdate(result);

            } catch (Exception e) {
                logger.error("save log index exception:",e);
            }
        }
    }

    @ManagedAttribute(description = "event堆积个数")
    public long getEventQueueSize(){
        return queue.size();
    }

}
