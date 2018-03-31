package cn.com.smart.utils;

import org.hibernate.internal.SessionFactoryImpl;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.service.SmartContextService;

/**
 * 数据库工具类
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public class DBUtil {
	
	/**
	 * 判断是否为oracle
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isOracle() {
		return isDB("Oracle");
	}
	
	
	/**
	 * 判断是否为MySQL
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isMySQL() {
		return isDB("MySQL");
	}
	
	
	/**
	 * 判断是否为DB2
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isDB2() {
		return isDB("DB2");
	}
	
	
	/**
	 * 判断是否为PostgreSQL
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isPostgreSQL() {
		return isDB("PostgreSQL");
	}
	
	
	/**
	 * 判断是否为Sybase
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isSybase() {
		return isDB("Sybase");
	}
	
	
	/**
	 * 判断是否为SQLServer
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isSQLServer() {
		return isDB("SQLServer");
	}
	
	
	/**
	 * 是否是指定的数据库
	 * @param dbname
	 * @return 是返回：true；否则返回：false
	 */
	public static boolean isDB(String dbname) {
		boolean is = false;
		if(StringUtils.isNotEmpty(dbname)) {
			//SessionFactoryImpl sessionFactory = (SessionFactoryImpl)SpringBeanFactoryUtil.getInstance().getBean("sessionFactory");
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl)SmartContextService.findByName("sessionFactory");
			if(null != sessionFactory) {
				String dialect = sessionFactory.getDialect().toString();
				dialect = dialect.substring(dialect.lastIndexOf(".")+1, dialect.length());
				is = dialect.startsWith(dbname);
			}
		}
		return is;
	}
	
}
