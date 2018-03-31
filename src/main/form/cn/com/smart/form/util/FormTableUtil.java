package cn.com.smart.form.util;

import com.mixsmart.utils.StringUtils;

/**
 * 表单表格工具类
 * @author lmq
 * @create 2015年6月28日
 * @version 1.0 
 * @since 
 *
 */
public class FormTableUtil {

	/**
	 * 检测表名格式
	 * @param tableName
	 * @return
	 */
	public static boolean isCheckTableName(String tableName) {
		boolean is = false;
		if(StringUtils.isNotEmpty(tableName)) {
			if(tableName.startsWith("t_pf_")) {
				is = true;
			}
		}
		return is;
	}
	
}
