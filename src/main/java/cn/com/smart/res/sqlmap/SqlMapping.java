package cn.com.smart.res.sqlmap;

import java.util.Map;

import org.apache.log4j.Logger;

import com.mixsmart.utils.StringUtils;

/**
 * SQL映射对象
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public class SqlMapping {
	protected final String LOG_TAG = "<smartweb2--SQL语句映射：>";
	protected final Logger log = Logger.getLogger(SqlMapping.class);
	private Map<String,String> SQL_MAP;

	public SqlMapping(String xmlPath) {
		try {
			SQLMapFile sqlMapFile = LoadingSQLMapFile.getInstance().loadFile(xmlPath);
			if(null != sqlMapFile) {
				SQL_MAP = sqlMapFile.getSqlMaps();
			}
		} catch (SQLMapException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 取得xml映射文件中相应的sql语句
	 * @param sqlName sql语句名称，通过名称对应到sql
	 * @return 返回sql语句
	 */
	public String getSQL(String sqlName) {
		String sql = null;
		log.info(this.LOG_TAG + "sqlMap-name:" + sqlName);
		if(null != SQL_MAP && SQL_MAP.size()>0 && StringUtils.isNotEmpty(sqlName)) {
		  	sql = SQL_MAP.get(sqlName);
		}
		if(StringUtils.isEmpty(sql)) {
		    try {
                Map<String,String> sqlMaps = LoadingSQLMapFile.getInstance().getDbSqlMap().getSqlMaps();
                if(null != sqlMaps && sqlMaps.size() > 0) {
                    sql = sqlMaps.get(sqlName);
                }
            } catch (SQLMapException e) {
                e.printStackTrace();
            }
		}
		if(StringUtils.isNotEmpty(sql)) {
            sql = sql.replaceAll("\n", " ");
            sql = sql.replaceAll("\r", " ");
            sql = sql.replaceAll("\t", " ");
            sql = sql.replaceAll(" +", " ");
        } else {
            log.error("资源名称为["+sqlName+"]的值为空");
        }
		return sql;
	}
}
