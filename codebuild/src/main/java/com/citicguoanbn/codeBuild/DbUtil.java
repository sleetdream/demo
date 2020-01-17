package com.citicguoanbn.codeBuild;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.Statement;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbUtil {

	private static DruidDataSource dataSource;

	private static QueryRunner run;

	private static Statement st;


	public DbUtil(){
		if(dataSource == null){
			dataSource = new DruidDataSource();
			dataSource.setDriverClassName(Constants.driverClass);
			dataSource.setInitialSize(Constants.initialSize);
			dataSource.setMinIdle(Constants.minIdle);
			dataSource.setMaxActive(Constants.maxActive);
			dataSource.setUrl(Constants.url);
			dataSource.setUsername(Constants.username);
			dataSource.setPassword(Constants.password);
		}

		if(run == null){
			run = new QueryRunner(dataSource);
		}
	}

	/**
	 * 查询返回list对象
	 *
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Object[] param, Class<T> clazz) {
		List<T> obj = null;
		try {
			obj = (List<T>) run.query(sql,param,new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public void excuteSql(String sql){
		try {
			run.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public List queryList(String sql){
		return queryForList(sql,null,HashMap.class);
	}


	//找出数据库表信息
	@SuppressWarnings("unchecked")
	public ArrayList getTableDescList(String tableName){
		return (ArrayList)queryList("select column_name AS 'Field', data_type   AS 'Type', character_maximum_length  AS `CharLen`, numeric_precision AS `NumLen`, numeric_scale AS `NumScale`, is_nullable AS `Null`, extra AS `Extra`,column_default  AS  `Default`,column_comment  AS  `Comment`,column_key  AS  `Key` FROM Information_schema.columns WHERE table_Name='"+tableName+"';");
	}

	//找出数据库表名和表方案信息
	@SuppressWarnings("unchecked")
	public ArrayList getTableDescList(String tableName,String tableSchema){
		String sql = "select column_name AS 'Field', data_type   AS 'Type', character_maximum_length  AS `CharLen`, numeric_precision AS `NumLen`, numeric_scale AS `NumScale`, is_nullable AS `Null`, extra AS `Extra`,column_default  AS  `Default`,column_comment  AS  `Comment`,column_key  AS  `Key` FROM Information_schema.columns WHERE table_Name='"+tableName+"' and table_schema='"+tableSchema+"';";
//    	System.out.println("sql>"+sql);
		return (ArrayList)queryList(sql);
	}
}
