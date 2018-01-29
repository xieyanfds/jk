package com.xy.utils.file;

import java.util.UUID;

public class UpLoadUtils {
	/**
	 * 传入一个文件名,进行切割,切割后再重新命名,唯一
	 * @param fileName
	 * @return
	 */
	public static String getUUIDName(String fileName){
		int last = fileName.lastIndexOf(".");
		String files = fileName .substring(last,fileName.length());
		String name = UUID.randomUUID().toString().replace("-","");
		return name+files;
	}
	public static void main(String[] args) {
		String nnn = getUUIDName("jav.jpg");
		System.out.println(nnn);
	}
}
