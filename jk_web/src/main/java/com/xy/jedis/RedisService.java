package com.xy.jedis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.xy.shiro.JSONObject;
import com.xy.shiro.SerializeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xieyan
 * @description
 * @date 2018/3/2.
 */
public class RedisService {

    private static Logger logger = Logger.getLogger(RedisService.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //取消默认转换timestamps形式
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空bean转json的错误
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有日期统一格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //忽略在json中存在，但是在对象中不存在时转换错误
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入或更新缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
        return Boolean.valueOf(true);
    }

    /**
     * 写入缓存
     * 设置失效时间
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public <T> Boolean setex(String key, T t, long timeout, TimeUnit timeUnit) {
        boolean result = true;

        try {
            String json = mapper.writeValueAsString(t);
            this.redisTemplate.opsForValue().set(key, json, timeout, timeUnit);
        } catch (Exception var9) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + t, var9);
            result = false;
        }

        return Boolean.valueOf(result);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据class类型读取缓存
     *
     * @param key
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
            String value = (String) this.redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            JSONObject jsonObject = JSONObject.fromObject(value);
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                Object o = jsonObject.get(name);
                if (o != null) {
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (writeMethod != null) {
                        if (propertyDescriptor.getPropertyType() == Date.class) {
                            if(o instanceof String){
                                o = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(o.toString());
                            }else {
                                o = new Date(Long.parseLong(o.toString()));
                            }
                        }
                        writeMethod.invoke(result, o);
                    }
                }
            }
//            t = this.mapper.readValue(value, clazz);
        } catch (Exception var6) {
            logger.error("RedisService.get error", var6);
        }
        return result;
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public Boolean delete(String key) {
        this.redisTemplate.delete(key);
        return Boolean.valueOf(true);
    }


    /**
     * 删除对应的集合
     *
     * @param keys
     */
    public Boolean delete(Set<String> keys) {
        this.redisTemplate.delete(keys);
        return Boolean.valueOf(true);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 正则表达式
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置string 的value
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
        return Boolean.valueOf(true);
    }

    /**
     * 添加set集合元素
     *
     * @param key
     * @param set
     * @return
     */
    public Long sadd(String key, Set<String> set) {
        long l = 0;
        for (String s : set) {
            this.redisTemplate.opsForSet().add(key, s);
            l++;
        }
        return l;
    }

    /**
     * 添加hash子元素
     *
     * @param key
     * @param hashKey
     * @param t
     * @param <T>
     * @return
     */
    public <T> Boolean hset(String key, Object hashKey, T t) {
        boolean result = true;

        try {
            String json;
            if (t != null && t instanceof String) {
                json = t.toString();
            } else {
                json = mapper.writeValueAsString(t);
            }

            this.redisTemplate.opsForHash().put(key, hashKey, json);
        } catch (Exception var7) {
            logger.error("RedisService.set error, key=" + key + " value=" + t, var7);
            result = false;
        }

        return Boolean.valueOf(result);
    }


    /**
     * 设置key的有效期
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        this.redisTemplate.expire(key, timeout, unit);
        return Boolean.valueOf(true);
    }

    /**
     * 获取set集合元素
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public byte[] getByte(String s) {
        Object o = this.redisTemplate.opsForValue().get(s);
        return SerializeUtils.serialize(o);
    }

    public void flushDB() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public Set<String> keys(String pattern) {
        final ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();
        return (Set) this.redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                boolean done = false;
                HashSet keySet = new HashSet();

                while (!done) {
                    Cursor c = connection.scan(scanOptions);

                    try {
                        while (c.hasNext()) {
                            byte[] b = (byte[]) c.next();
                            keySet.add(new String(b));
                        }

                        done = true;
                    } catch (NoSuchElementException var6) {
                        RedisService.logger.error("RedisService.scan error", var6);
                    }
                }

                return keySet;
            }
        });
    }

    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        });
    }

    /*public Boolean setnx(int dbIndex, String key, String value) {
        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            return this.redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception var6) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + value, var6);
            boolean result = false;
            return Boolean.valueOf(result);
        }
    }

    public Boolean setex(int dbIndex, String key, String value, long timeout, TimeUnit timeUnit) {
        boolean result = true;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception var9) {
            logger.error("RedisService.setex error, key=" + key + " value=" + value, var9);
            result = false;
        }

        return Boolean.valueOf(result);
    }

    public String get(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.delete(key);
        return Boolean.valueOf(true);
    }

    public Boolean delete(int dbIndex, Set<String> keys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.delete(keys);
        return Boolean.valueOf(true);
    }

    public Boolean expire(int dbIndex, String key, long timeout, TimeUnit unit) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.expire(key, timeout, unit);
        return Boolean.valueOf(true);
    }

    public Long ttl(int dbIndex, final String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (Long)this.redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ttl(RedisService.this.redisTemplate.getKeySerializer().serialize(key));
            }
        });
    }

    public <T> Boolean set(int dbIndex, String key, T t) {
        boolean result = true;

        try {
            String json = this.mapper.writeValueAsString(t);
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            this.redisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException var6) {
            logger.error("RedisService.set error, key=" + key + " value=" + t, var6);
            result = false;
        }

        return Boolean.valueOf(result);
    }

    public <T> Boolean setnx(int dbIndex, String key, T t) {
        try {
            String json = this.mapper.writeValueAsString(t);
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            return this.redisTemplate.opsForValue().setIfAbsent(key, json);
        } catch (JsonProcessingException var6) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + t, var6);
            boolean result = false;
            return Boolean.valueOf(result);
        }
    }

    public <T> Boolean setex(int dbIndex, String key, T t, long timeout, TimeUnit timeUnit) {
        boolean result = true;

        try {
            String json = this.mapper.writeValueAsString(t);
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            this.redisTemplate.opsForValue().set(key, json, timeout, timeUnit);
        } catch (JsonProcessingException var9) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + t, var9);
            result = false;
        }

        return Boolean.valueOf(result);
    }

    public <T> Boolean multiset(int dbIndex, final Map<String, T> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    String json;
                    try {
                        json = RedisService.this.mapper.writeValueAsString(entry.getValue());
                    } catch (JsonProcessingException var6) {
                        continue;
                    }

                    connection.set(RedisService.this.redisTemplate.getKeySerializer().serialize(entry.getKey()), RedisService.this.redisTemplate.getValueSerializer().serialize(json));
                }

                return null;
            }
        });
        return Boolean.valueOf(true);
    }

    public Map<String, String> multiget(int dbIndex, final Map<String, String> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, String> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.get(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multiget error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var6 = map.entrySet().iterator(); var6.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var6.next();
                Object result = resultList.get(i);
                if(result != null && !StringUtils.isEmpty(result.toString())) {
                    try {
                        resultMap.put(entry.getKey(), result.toString());
                    } catch (Exception var10) {
                        logger.error("RedisService.multiget error", var10);
                    }
                }
            }
        }

        return resultMap;
    }

    public <T> Map<String, T> multiget(int dbIndex, final Map<String, String> map, Class<T> clazz) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, T> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.get(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multiget error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var7 = map.entrySet().iterator(); var7.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var7.next();
                Object result = resultList.get(i);
                if(result != null && !StringUtils.isEmpty(result.toString())) {
                    try {
                        resultMap.put(entry.getKey(), this.mapper.readValue(result.toString(), clazz));
                    } catch (Exception var11) {
                        logger.error("RedisService.multiget error", var11);
                    }
                }
            }
        }

        return resultMap;
    }

    public <T> Map<String, List<T>> multigetList(int dbIndex, final Map<String, String> map, Class<T> clazz) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, List<T>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.get(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multigetList error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var7 = map.entrySet().iterator(); var7.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var7.next();
                Object result = resultList.get(i);
                if(result != null && !StringUtils.isEmpty(result.toString())) {
                    try {
                        JavaType valueType = this.mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, new Class[]{clazz});
                        List<T> list = (List)this.mapper.readValue(result.toString(), valueType);
                        resultMap.put(entry.getKey(), list);
                    } catch (Exception var12) {
                        logger.error("RedisService.multigetList error", var12);
                    }
                }
            }
        }

        return resultMap;
    }

    public <T> T get(int dbIndex, String key, Class<T> clazz) {
        Object t = null;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            String value = (String)this.redisTemplate.opsForValue().get(key);
            if(StringUtils.isEmpty(value)) {
                return null;
            }

            t = this.mapper.readValue(value, clazz);
        } catch (Exception var6) {
            logger.error("RedisService.get error", var6);
        }

        return t;
    }

    public <T> List<T> getList(int dbIndex, String key, Class<T> clazz) {
        List list = null;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            String value = (String)this.redisTemplate.opsForValue().get(key);
            if(StringUtils.isEmpty(value)) {
                return new ArrayList();
            }

            JavaType valueType = this.mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, new Class[]{clazz});
            list = (List)this.mapper.readValue(value, valueType);
        } catch (Exception var7) {
            logger.error("RedisService.getList error", var7);
        }

        return list;
    }

    public Set<String> scan(int dbIndex, String pattern) {
        scanCountMonitor.update(1L);
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        final ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();
        return (Set)this.redisTemplate.execute(new RedisCallback<Set<String>>() {
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                boolean done = false;
                HashSet keySet = new HashSet();

                while(!done) {
                    Cursor c = connection.scan(scanOptions);

                    try {
                        while(c.hasNext()) {
                            byte[] b = (byte[])c.next();
                            keySet.add(new String(b));
                        }

                        done = true;
                    } catch (NoSuchElementException var6) {
                        RedisService.logger.error("RedisService.scan error", var6);
                    }
                }

                return keySet;
            }
        });
    }

    public Long incrby(int dbIndex, String key, long delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    public <T> Boolean convertAndSend(String channel, T message) {
        try {
            String json = this.mapper.writeValueAsString(message);
            this.redisTemplate.convertAndSend(channel, json);
        } catch (JsonProcessingException var4) {
            logger.error("RedisService.convertAndSend error, channel=" + channel + " message=" + message, var4);
        }

        return Boolean.valueOf(true);
    }

    public <T> T execute(int dbIndex, SessionCallback<T> session) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.execute(session);
    }

    public <T> T execute(int dbIndex, RedisCallback<T> redisCallback) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.execute(redisCallback);
    }

    public Long time() {
        return (Long)this.redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.time();
            }
        });
    }

    public String ping() {
        return (String)this.redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    public String lpop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().leftPop(key);
    }

    public String blpop(int dbIndex, String key, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().leftPop(key, timeout, timeUnit);
    }

    public String rpop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().rightPop(key);
    }

    public String brpop(int dbIndex, String key, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().rightPop(key, timeout, timeUnit);
    }

    public String rpoplpush(int dbIndex, String sourceKey, String destinationKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    public String brpoplpush(int dbIndex, String sourceKey, String destinationKey, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, timeUnit);
    }

    public String lindex(int dbIndex, String key, long index) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForList().index(key, index);
    }

    public Long linsert(int dbIndex, String key, String pivot, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    public Long llen(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().size(key);
    }

    public Long lpush(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().leftPush(key, value);
    }

    public Long lpushx(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    public List<String> lrange(int dbIndex, String key, long start, long end) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    public Long lrem(int dbIndex, String key, long count, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().remove(key, count, value);
    }

    public Boolean ltrim(int dbIndex, String key, long start, long end) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.opsForList().trim(key, start, end);
        return Boolean.valueOf(true);
    }

    public Boolean lset(int dbIndex, String key, long index, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.opsForList().set(key, index, value);
        return Boolean.valueOf(true);
    }

    public Long rpush(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    public Long rpushx(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    public Long hdel(int dbIndex, String key, Object... hashKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public Boolean hexists(int dbIndex, String key, Object hashKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Object hget(int dbIndex, String key, Object hashKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public <T> T hget(int dbIndex, String key, Object hashKey, Class<T> clazz) {
        Object t = null;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            Object value = this.redisTemplate.opsForHash().get(key, hashKey);
            if(value == null || StringUtils.isEmpty(value.toString())) {
                return null;
            }

            t = this.mapper.readValue(value.toString(), clazz);
        } catch (Exception var7) {
            logger.error("RedisService.hget error", var7);
        }

        return t;
    }

    public Map<Object, Object> hgetall(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().entries(key);
    }

    public Map<String, Map<String, String>> multihgetall(int dbIndex, final Map<String, String> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Map<String, String>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.hGetAll(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multihgetall error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var6 = map.entrySet().iterator(); var6.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var6.next();
                Map<String, String> result = (Map)resultList.get(i);
                if(result != null) {
                    resultMap.put(entry.getKey(), result);
                }
            }
        }

        return resultMap;
    }

    public <T> List<T> hgetList(int dbIndex, String key, Class<T> clazz) {
        ArrayList list = null;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            Map<Object, Object> map = this.redisTemplate.opsForHash().entries(key);
            if(map != null) {
                list = new ArrayList();
                Iterator var6 = map.keySet().iterator();

                while(var6.hasNext()) {
                    Object mapKey = var6.next();
                    String jsonStr = map.get(mapKey).toString();
                    if(StringUtils.isNotEmpty(jsonStr)) {
                        T t = JSONObject.fromObject(jsonStr).getBean(clazz);
                        list.add(t);
                    }
                }
            }
        } catch (Exception var10) {
            logger.error("RedisService.hgetList error", var10);
        }

        return list;
    }

    public <T> Map<String, List<T>> multihgetList(int dbIndex, final Map<String, String> map, Class<T> clazz) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, List<T>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.hGetAll(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multihgetList error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var7 = map.entrySet().iterator(); var7.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var7.next();
                Map<String, String> result = (Map)resultList.get(i);
                if(result != null) {
                    Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
                    ArrayList list = new ArrayList();

                    while(it.hasNext()) {
                        Map.Entry<String, String> resultEntry = (Map.Entry)it.next();
                        String jsonStr = (String)result.get(resultEntry.getKey());
                        if(StringUtils.isNotEmpty(jsonStr)) {
                            T t = JSONObject.fromObject(jsonStr).getBean(clazz);
                            list.add(t);
                        }
                    }

                    resultMap.put(entry.getKey(), list);
                }
            }
        }

        return resultMap;
    }

    public Long hincrby(int dbIndex, String key, Object hashKey, long delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public Double hincrbyfloat(int dbIndex, String key, Object hashKey, float delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().increment(key, hashKey, (double)delta);
    }

    public Set<Object> hkeys(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().keys(key);
    }

    public Map<String, Set<String>> multihkeys(int dbIndex, final Map<String, String> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Set<String>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.hKeys(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multihgetList error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var6 = map.entrySet().iterator(); var6.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var6.next();
                Set<String> result = (Set)resultList.get(i);
                if(result != null) {
                    resultMap.put(entry.getKey(), result);
                }
            }
        }

        return resultMap;
    }

    public Long hlen(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().size(key);
    }

    public List<Object> hmget(int dbIndex, String key, Collection<Object> hashKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    public Boolean hmset(int dbIndex, String key, Map<Object, Object> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.opsForHash().putAll(key, map);
        return Boolean.valueOf(true);
    }

    public <T> Boolean hset(int dbIndex, String key, Object hashKey, T t) {
        boolean result = true;

        try {
            RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
            String json;
            if(t != null && t instanceof String) {
                json = t.toString();
            } else {
                json = this.mapper.writeValueAsString(t);
            }

            this.redisTemplate.opsForHash().put(key, hashKey, json);
        } catch (JsonProcessingException var7) {
            logger.error("RedisService.set error, key=" + key + " value=" + t, var7);
            result = false;
        }

        return Boolean.valueOf(result);
    }

    public Boolean hsetnx(int dbIndex, String key, Object hashKey, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    public List<Object> hvals(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForHash().values(key);
    }

    public Map<String, Long> multihlen(int dbIndex, final Map<String, String> keyMap) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Long> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = keyMap.values().iterator();

                while(var2.hasNext()) {
                    String value = (String)var2.next();

                    try {
                        connection.hLen(RedisService.this.redisTemplate.getStringSerializer().serialize(value));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multihlen error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == keyMap.size()) {
            int i = 0;

            for(Iterator var6 = keyMap.keySet().iterator(); var6.hasNext(); ++i) {
                String key = (String)var6.next();
                resultMap.put(key, (Long)resultList.get(i));
            }
        }

        return resultMap;
    }

    public Map<String, Object> multihget(int dbIndex, final Map<String, String> keyMap, final String field) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Object> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = keyMap.values().iterator();

                while(var2.hasNext()) {
                    String value = (String)var2.next();

                    try {
                        connection.hGet(RedisService.this.redisTemplate.getStringSerializer().serialize(value), RedisService.this.redisTemplate.getStringSerializer().serialize(field));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multihget error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == keyMap.size()) {
            int i = 0;

            for(Iterator var7 = keyMap.keySet().iterator(); var7.hasNext(); ++i) {
                String key = (String)var7.next();
                resultMap.put(key, resultList.get(i));
            }
        }

        return resultMap;
    }

    public Map<String, Map<String, String>> multihget(int dbIndex, final Map<String, String> keyMap, final List<String> fieldList) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Map<String, String>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[][] fields = new byte[fieldList.size()][];

                for(int i = 0; i < fieldList.size(); ++i) {
                    fields[i] = RedisService.this.redisTemplate.getStringSerializer().serialize(fieldList.get(i));
                }

                Iterator var7 = keyMap.values().iterator();

                while(var7.hasNext()) {
                    String value = (String)var7.next();

                    try {
                        connection.hMGet(RedisService.this.redisTemplate.getStringSerializer().serialize(value), fields);
                    } catch (Exception var6) {
                        RedisService.logger.error("RedisService.multihget error", var6);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == keyMap.size()) {
            int i = 0;

            for(Iterator var7 = keyMap.keySet().iterator(); var7.hasNext(); ++i) {
                String key = (String)var7.next();
                List<String> result = (List)resultList.get(i);
                int j = 0;
                Map<String, String> map = new HashMap();

                for(Iterator var12 = fieldList.iterator(); var12.hasNext(); ++j) {
                    String field = (String)var12.next();
                    String value = (String)result.get(j);
                    map.put(field, value);
                }

                resultMap.put(key, map);
            }
        }

        return resultMap;
    }

    public <T> Map<String, T> multihget(int dbIndex, final String key, final Map<String, String> map, Class<T> clazz) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, T> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.hGet(RedisService.this.redisTemplate.getStringSerializer().serialize(key), RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multiget error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var8 = map.entrySet().iterator(); var8.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var8.next();
                Object result = resultList.get(i);
                if(result != null && !StringUtils.isEmpty(result.toString())) {
                    try {
                        resultMap.put(entry.getKey(), this.mapper.readValue(result.toString(), clazz));
                    } catch (Exception var12) {
                        logger.error("RedisService.multiget error", var12);
                    }
                }
            }
        }

        return resultMap;
    }

    public Boolean multihincrby(int dbIndex, final String key, final Map<String, Long> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry<String, Long> entry = (Map.Entry)var2.next();
                    connection.hIncrBy(RedisService.this.redisTemplate.getKeySerializer().serialize(key), RedisService.this.redisTemplate.getHashKeySerializer().serialize(entry.getKey()), ((Long)entry.getValue()).longValue());
                }

                return null;
            }
        });
        return Boolean.valueOf(true);
    }

    public Long sadd(int dbIndex, String key, String... values) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Map<String, Long> multisadd(int dbIndex, final String key, final Map<String, String> valueMap) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Long> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = valueMap.values().iterator();

                while(var2.hasNext()) {
                    String value = (String)var2.next();

                    try {
                        connection.sAdd(RedisService.this.redisTemplate.getStringSerializer().serialize(key), new byte[][]{RedisService.this.redisTemplate.getStringSerializer().serialize(value)});
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multisadd error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == valueMap.size()) {
            int i = 0;

            for(Iterator var7 = valueMap.keySet().iterator(); var7.hasNext(); ++i) {
                String value = (String)var7.next();
                Long result = (Long)resultList.get(i);
                resultMap.put(value, result);
            }
        }

        return resultMap;
    }

    public Long scard(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().size(key);
    }

    public Set<String> sdiff(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().difference(key, otherKey);
    }

    public Set<String> sdiff(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public Long sdiffstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    public Long sdiffstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    public Set<String> sinter(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().intersect(key, otherKey);
    }

    public Set<String> sinter(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    public Long sinterstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long sinterstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    public Boolean sismember(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().isMember(key, value);
    }

    public Set<String> smembers(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().members(key);
    }

    public Boolean smove(int dbIndex, String key, String value, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().move(key, value, destKey);
    }

    public String spop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForSet().pop(key);
    }

    public String srandmember(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return (String)this.redisTemplate.opsForSet().randomMember(key);
    }

    public List<String> srandmember(int dbIndex, String key, long count) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().randomMembers(key, count);
    }

    public Long srem(int dbIndex, String key, Object... values) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    public Set<String> sunion(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().union(key, otherKey);
    }

    public Set<String> sunion(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Long sunionstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    public Long sunionstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    public Boolean zadd(int dbIndex, String key, String value, double score) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().add(key, value, score);
    }

    public <T> Boolean multizadd(int dbIndex, final String key, final List<T> memberList, final List<Double> scoreList) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                int i = 0;

                for(Iterator var3 = memberList.iterator(); var3.hasNext(); ++i) {
                    T member = var3.next();
                    connection.zAdd(RedisService.this.redisTemplate.getKeySerializer().serialize(key), ((Double)scoreList.get(i)).doubleValue(), RedisService.this.redisTemplate.getValueSerializer().serialize(JSONObject.getJSONString(member)));
                }

                return null;
            }
        });
        return Boolean.valueOf(true);
    }

    public Long zcard(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().zCard(key);
    }

    public Long zcount(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().count(key, min, max);
    }

    public Map<String, Integer> multizcount(int dbIndex, final Map<String, String> map, final double min, final double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Integer> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.zCount(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()), min, max);
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multizrank error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var10 = map.entrySet().iterator(); var10.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var10.next();
                Object result = resultList.get(i);
                if(result != null) {
                    resultMap.put(entry.getKey(), Integer.valueOf(result.toString()));
                }
            }
        }

        return resultMap;
    }

    public Double zincrby(int dbIndex, String key, String value, double delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    public Long zinterstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long zinterstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    public Set<String> zrange(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().range(key, start, stop);
    }

    public Map<String, Set<String>> multizrange(int dbIndex, final Map<String, String> map, final long start, final long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Set<String>> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = map.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.zRange(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()), start, stop);
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multizrange error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == map.size()) {
            int i = 0;

            for(Iterator var10 = map.entrySet().iterator(); var10.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var10.next();
                Object result = resultList.get(i);
                if(result != null) {
                    resultMap.put(entry.getKey(), (Set)result);
                }
            }
        }

        return resultMap;
    }

    public Set<String> zrangebylex(int dbIndex, String key, RedisZSetCommands.Range range) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    public Set<String> zrangebysocre(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Set<String> zrangebysocre(int dbIndex, String key, double min, double max, int offset, int count) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max, (long)offset, (long)count);
    }

    public Long zrank(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().rank(key, value);
    }

    public Map<String, Integer> multizrank(int dbIndex, final Map<String, String> valueKeyMap) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        Map<String, Integer> resultMap = new HashMap();
        List<Object> resultList = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Iterator var2 = valueKeyMap.entrySet().iterator();

                while(var2.hasNext()) {
                    Map.Entry entry = (Map.Entry)var2.next();

                    try {
                        connection.zRank(RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getValue()), RedisService.this.redisTemplate.getStringSerializer().serialize(entry.getKey()));
                    } catch (Exception var5) {
                        RedisService.logger.error("RedisService.multizrank error", var5);
                    }
                }

                return null;
            }
        });
        if(resultList != null && resultList.size() == valueKeyMap.size()) {
            int i = 0;

            for(Iterator var6 = valueKeyMap.entrySet().iterator(); var6.hasNext(); ++i) {
                Map.Entry<String, String> entry = (Map.Entry)var6.next();
                Long result = (Long)resultList.get(i);
                if(result != null) {
                    try {
                        resultMap.put(entry.getKey(), Integer.valueOf(result.intValue()));
                    } catch (Exception var10) {
                        logger.error("RedisService.multizrank error", var10);
                    }
                }
            }
        }

        return resultMap;
    }

    public Long zrem(int dbIndex, String key, Object... values) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().remove(key, values);
    }

    public Long zremrangebyrank(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().removeRange(key, start, stop);
    }

    public Long zremrangebyscore(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    public Set<String> zrevrange(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().reverseRange(key, start, stop);
    }

    public Set<String> zrevrangebyscore(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public Set<String> zrevrangebyscore(int dbIndex, String key, double min, double max, int offset, int count) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, (long)offset, (long)count);
    }

    public Long zrevrank(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public Double zscore(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().score(key, value);
    }

    public Long zunionscore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    public Long zunionscore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(Integer.valueOf(dbIndex));
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static long getScanCountLastMinute() {
        return scanCountMonitor.getLastMinuteCount();
    }*/
}