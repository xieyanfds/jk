package com.xy.utils;


/**
 * @author xieyan
 * @description 系统全局常量配置类
 * @date 2018/3/2.
 */
public class SysConstant {

	//当前用户session name
	public static String CURRENT_USER_INFO = "_CURRENT_USER";
	//使用的数据库 Oracle/SQLServer
	public static String USE_DATABASE = "MySql";
	//使用的数据库版本 10g/2000
	public static String USE_DATABASE_VER = "5.0";

	//session中存储的访问模块数集合
	public static String ALL_ACTIONNAME = "ALL_ACTIONNAME";
	//session中存储的一级菜单权限集合
	public static String ALL_PERMISSION = "ALL_PERMISSION";
	//默认密码
	public static String DEFAULT_PASS = "123456";

	//分页时一页显示多少条记录
	public static int PAGE_SIZE = 10;

	//分页时默认第一页
	public static int PAGE_NO = 1;
}
