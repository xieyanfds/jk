package com.xy.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author xieyan
 * @description
 * @date 2018/3/2.
 */
public class PropertyUtil extends PropertyPlaceholderConfigurer {
    private static Map<String, String> properties;

    public PropertyUtil() {
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
        super.processProperties(beanFactory, props);
        properties = new HashMap();
        Iterator var3 = props.keySet().iterator();

        while(var3.hasNext()) {
            Object key = var3.next();
            String keyStr = key.toString();
            properties.put(keyStr, props.getProperty(keyStr));
        }

    }

    public static String getProperty(String key) {
        return (String)properties.get(key);
    }
}
