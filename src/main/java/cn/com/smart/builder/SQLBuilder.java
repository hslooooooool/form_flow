package cn.com.smart.builder;

import com.mixsmart.utils.StringUtils;

/**
 * Builder SQL语句
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public class SQLBuilder {

	/**
	 * 根据提供的查询SQL语句生成统计语句
	 * @param sql SQL语句
	 * @return 返回统计语句
	 */
	public static String countSQL(String sql) {
		if(StringUtils.isEmpty(sql)) {
			return null;
		}
		//去掉最后一出现的order by 语句
		String orderBy = "order by";
		int orderByPos = sql.lastIndexOf(" "+orderBy+" ");
		if(orderByPos == -1) {
			orderBy = orderBy.toUpperCase();
			orderByPos = sql.lastIndexOf(" "+orderBy+" ");
		}
		int bracketsPos = sql.lastIndexOf(")");
		StringBuilder countSqlBuilder = new StringBuilder();
		if(orderByPos > 0 && orderByPos > bracketsPos) {
			sql = org.apache.commons.lang3.StringUtils.substringBeforeLast(sql, " "+orderBy+" ");
		}
		countSqlBuilder.append("select count(*) from ");
		boolean isLoop = true;
		String select = "select";
		String from = "from";
		int selectPos = sql.indexOf(select+" ", 0);
		int	selectUpPos = sql.indexOf(select.toUpperCase()+" ", 0);
		selectPos = getValidValue(selectPos, selectUpPos);
		
		int fromPos = 0;
		int fromUpPos = 0;
		int keyLen = 6;
		selectPos = selectPos + keyLen;
		while(isLoop) {
			fromPos = sql.indexOf(" "+from+" ", selectPos);
			fromUpPos = sql.indexOf(" "+from.toUpperCase()+" ", selectPos);
			fromPos = getValidValue(fromPos, fromUpPos);
			int selectPosTmp = sql.indexOf(select+" ", selectPos);
			selectUpPos = sql.indexOf(select.toUpperCase()+" ", selectPos);
			selectPos = getValidValue(selectPosTmp, selectUpPos);
			if(selectPos >-1 && selectPos < fromPos ) {
				selectPos = fromPos + keyLen;
			} else {
				isLoop = false;
			}
		}
		countSqlBuilder.append(sql.substring(fromPos + keyLen, sql.length()));
		return countSqlBuilder.toString();
	}
	
	/**
	 * 获取有效值
	 * @param value1
	 * @param value2
	 * @return
	 */
	private static int getValidValue(int value1, int value2) {
		if(value1 == -1) {
			return value2;
		} else if(value2 == -1) {
			return value1;
		} else if(value1 > -1 && value2 > -1) {
			return (value1 < value2)?value1:value2;
		} else {
			return value1;
		}
	}
	
}
