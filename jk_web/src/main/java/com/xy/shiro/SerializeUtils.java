package com.xy.shiro;

import com.alibaba.fastjson.serializer.SerializeWriter;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.codec.Base64;

import java.io.*;

/**
 * @author xieyan
 * @description
 * @date 2018/3/9.
 */
public class SerializeUtils {
    public static Object deserialize(byte[] str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("deserialize session error", e);
        } finally {
            try {
                ois.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static <T> byte[] serialize(T obj) {
        if(obj == null) {
            return null;
        } else {
            ByteArrayOutputStream bos = null;
            ObjectOutputStream oos = null;
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();
                return Base64.encode(bos.toByteArray());
            } catch (Exception e) {
                throw new RuntimeException("serialize session error", e);
            } finally {
                try {
                    oos.close();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
