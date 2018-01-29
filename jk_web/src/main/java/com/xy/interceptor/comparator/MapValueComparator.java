package com.xy.interceptor.comparator;

import com.xy.interceptor.bean.ActionBean;

import java.util.Comparator;
import java.util.Map;

/**
 * @author xieyan
 * @description map比较器类
 * @date 2018/1/24.
 */
public class MapValueComparator implements Comparator<Map.Entry<String, ActionBean>>{

    @Override
    public int compare(Map.Entry<String, ActionBean> o1, Map.Entry<String, ActionBean> o2) {
        return o2.getValue().getNumber().compareTo(o1.getValue().getNumber());
    }
}
