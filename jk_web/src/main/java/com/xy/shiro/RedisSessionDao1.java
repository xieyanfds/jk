package com.xy.shiro;

import com.xy.jedis.RedisService;
import com.xy.utils.redis.RedisCacheKey;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author xieyan
 * @description
 * @date 2018/3/9.
 */
public class RedisSessionDao1 extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao1.class);

    private long expire;

    @Autowired
    private RedisService redisService;

    private String keyPrefix = "shiro_redis_session:";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        logger.debug("Read Redis.SessionId=" + sessionId.toString());

        Session session = redisService.get(this.keyPrefix+sessionId.toString(),SimpleSession.class);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    int i=0;
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        session.setTimeout(expire);
        long timeout = expire / 1000;
        //保存用户会话
        redisService.setex(this.keyPrefix+session.getId().toString(),session,timeout, TimeUnit.SECONDS);
        //获取用户id
        String uid = getUserId(session);
        if (!StringUtils.isEmpty(uid)) {
            //保存用户会话对应的UID
            try {
                redisService.setex(String.format(RedisCacheKey.USER_PERMISSION_ID,uid),uid.getBytes("utf-8"),timeout,TimeUnit.SECONDS);
                //保存在线UID
//                redisService.add(this.getKey(RedisConstant.UID_PRE, uid), timeout, ("online"+(i++)+"").getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.error("getBytes error:" + ex.getMessage());
            }
        }

    }


    public String getUserId(Session session) {
        SimplePrincipalCollection pricipal = (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        if (null != pricipal) {
            return pricipal.getPrimaryPrincipal().toString();
        }
        return null;
    }

    public String getKey(String prefix, String keyStr) {
        return prefix + keyStr;
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        //删除用户会话
        redisService.delete( session.getId().toString());
        /*//获取缓存的用户会话对应的UID
        String uid = redisService.get(this.getKey(RedisConstant.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除用户会话sessionid对应的uid
        redisService.delete(this.getKey(RedisConstant.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除在线uid
        redisService.delete(this.getKey(RedisConstant.UID_PRE, uid));
        //删除用户缓存的角色
        redisService.delete(this.getKey(RedisConstant.ROLE_PRE, uid));
        //删除用户缓存的权限
        redisService.delete(this.getKey(RedisConstant.PERMISSION_PRE, uid));*/
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        Set<String> keys = redisService.keys(this.keyPrefix+"*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = redisService.get(key,Session.class);
                sessions.add(s);
            }
        }
        return sessions;
    }

    /**
     * 当前用户是否在线
     *
     * @param uid 用户id
     * @return
     */
    public boolean isOnLine(String uid) {
        Set<String> keys = redisService.keys( uid);
        if (keys != null && keys.size() > 0) {
            return true;
        }
        return false;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}

