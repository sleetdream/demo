/*
 * ISConsole Copyright 2011 ruibaotong COMPANY, Co.ltd . 
 * All rights reserved.
 * Package:com.rbt.common.codeBuild
 * FileName: Constants.java
 */
package com.citicguoanbn.codeBuild;

/**
 * 功能：存放代码生成工具所需要的所有常量 date:2018-05-08
 */
public class Constants {

	/*
	 * 链接数据库信息
	 */
	public static final String TYPE = "mysql";

	public static final String driverClass = "com.mysql.jdbc.Driver";
	public static final int initialSize = 10;
	public static final int minIdle = 10;
	public static final int maxActive = 100;

	public static final String url = "jdbc:mysql://172.18.86.37:3306/demo??useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";
	public static final String username = "root";
	public static final String password = "root";
}
