package cn.com.smart.res;

import cn.com.smart.res.sqlmap.SqlMapping;

/**
 * SQL资源管理
 * @author lmq
 *
 */
public class SQLResUtil {

	/**
	 * 获取SQL资源
	 * @return 最基本的SQL资源
	 */
	public static SqlMapping getBaseSqlMap() {
		return new SqlMapping("/sql/base_sql_res.xml");
	}

	/**
	 * 获取SQL资源
	 * @return 最其他的SQL资源
	 */
	public static SqlMapping getOpSqlMap() {
		return new SqlMapping("/sql/op_sql_res.xml");
	}
	
}
