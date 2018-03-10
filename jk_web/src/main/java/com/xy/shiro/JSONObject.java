package com.xy.shiro;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xieyan
 * @description
 * @date 2018/3/9.
 */
public class JSONObject extends LinkedHashMap<String, Object> {
    private static ObjectMapper mapper = new ObjectMapper();

    public JSONObject() {
    }

    public static JSONObject fromObject(String json) {
        try {
            mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
            mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
            return (JSONObject)mapper.readValue(json, JSONObject.class);
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static JSONObject fromObject(Object object) {
        if(object instanceof String) {
            return fromObject(object.toString());
        } else {
            try {
                return fromObject(mapper.writeValueAsString(object));
            } catch (JsonProcessingException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static String getJSONString(Object object) {
        if(object instanceof String) {
            return object.toString();
        } else {
            try {
                return mapper.writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static <T> T getBean(String json, Class<T> c) {
        if(json == null) {
            return null;
        } else {
            Object t = null;

            try {
                t = mapper.readValue(json, c);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return (T)t;
        }
    }

    public JSONObject getJSONObject(String key) {
        return (JSONObject)mapper.convertValue(super.get(key), JSONObject.class);
    }

    public <T> T getBean(Class<T> c) {
        return mapper.convertValue(this, c);
    }

    public JSONArray getJSONArray(String key) {
        JSONArray ja = new JSONArray();
        List<Object> list = (List)super.get(key);
        if(list != null) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Object o = var4.next();
                ja.add(o);
            }
        }

        return ja;
    }

    public boolean isNull(String key) {
        return !super.containsKey(key);
    }

    public boolean isNotNull(String key) {
        return !this.isNull(key);
    }

    public String getString(String key) {
        Object object = super.get(key);
        return object == null?null:object.toString();
    }

    public int getInt(String key) {
        Object object = super.get(key);
        return object != null && StringUtils.isNumeric(object.toString())?Integer.parseInt(object.toString()):-1;
    }

    public long getLong(String key) {
        Object object = super.get(key);
        return object != null && StringUtils.isNumeric(object.toString())?Long.parseLong(object.toString()):-1L;
    }

    public boolean getBoolean(String key) {
        Object object = super.get(key);
        return object == null?false:Boolean.parseBoolean(object.toString());
    }

    @Override
    public String toString() {
        String json = null;

        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
        }

        return json;
    }
}