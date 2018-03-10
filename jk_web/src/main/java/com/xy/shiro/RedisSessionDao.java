package com.xy.shiro;

import com.xy.domain.User;
import com.xy.utils.SysConstant;
import com.xy.utils.redis.RedisCacheKey;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xieyan
 * @description
 * @date 2018/3/10.
 */
public class RedisSessionDao extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private long expire;

    @Resource
    private RedisDao redisDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.info("创建seesion,id=[{}]",session.getId().toString());

        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        logger.debug("Read Redis.SessionId=" + new String(getKey(RedisCacheKey.SHIRO_REDIS_SESSION_PRE, sessionId.toString())));

        Session session = (Session) SerializationUtils.deserialize(redisDao.getByte(getKey(RedisCacheKey.SHIRO_REDIS_SESSION_PRE, sessionId.toString())));
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
        redisDao.add(this.getKey(RedisCacheKey.SHIRO_REDIS_SESSION_PRE, session.getId().toString()), timeout, SerializationUtils.serialize(session));

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
        redisDao.delete(this.getKey(RedisCacheKey.SHIRO_REDIS_SESSION_PRE, session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        logger.info("获取存活的session");
        Set<String> keys = redisDao.keys(RedisCacheKey.SHIRO_REDIS_SESSION_PRE + "*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = (Session) SerializationUtils.deserialize(redisDao.getByte(key));
                sessions.add(s);
            }
        }
        return sessions;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}