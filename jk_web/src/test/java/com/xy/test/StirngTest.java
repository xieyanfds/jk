package com.xy.test;

import com.google.common.collect.Lists;
import com.xy.interceptor.bean.ActionBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author xieyan
 * @description
 * @date 2018/1/5.
 */
public class StirngTest {
    public static void main(String[] args) {
        LinkedList<ActionBean> objects = Lists.newLinkedList();
        ActionBean actionBean = new ActionBean();
        actionBean.setNumber(1);
        objects.add(actionBean);

        ActionBean actionBean1 = new ActionBean();
        actionBean1.setNumber(4);
        objects.add(actionBean1);

        ActionBean actionBean2 = new ActionBean();
        actionBean2.setNumber(2);
        objects.add(actionBean2);

        ActionBean actionBean4 = new ActionBean();
        actionBean4.setNumber(7);
        objects.add(actionBean4);

        Collections.sort(objects, new Comparator<ActionBean>() {
            @Override
            public int compare(ActionBean o1, ActionBean o2) {
                return o2.getNumber().compareTo(o1.getNumber());
            }
        });
        System.out.println(objects);

    }
}
