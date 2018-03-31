package cn.com.smart.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * List工具类；
 *  该类已过时，请使用{@link com.mixsmart.utils.CollectionUtils}类代替
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年8月22日
 */
@Deprecated
public class ListUtil extends BaseUtil {

	/**
	 * 判断是否含有
	 * @param objs
	 * @param obj
	 * @return 包含返回：true；否则返回：false
	 */
	public static boolean isContain(List<String> objs,String obj) {
		boolean is = false;
		if(null != objs && objs.size()>0 && null != obj) {
			for (Object value : objs) {
				if(value.toString().equals(obj.toString())) {
					is = true;
					break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断列表是否为空
	 * @param objs 列表对象
	 * @return　如果列表为空，返回：true；否则返回：false
	 */
	public static boolean isEmpty(List<?> objs) {
		boolean is = false;
		if(null == objs || objs.isEmpty()) {
			is = true;
		}
		return is;
	}
	
	/**
	 * 判断列表是否不为空
	 * @param objs 列表对象
	 * @return　如果列表不为空，返回：true；否则返回：false
	 */
	public static boolean isNotEmpty(List<?> objs) {
		return !isEmpty(objs);
	}
	
	/**
	 * 对象集合转换为字符串集合
	 * @param objs 列表对象
	 * @return 返回字符串集合
	 */
	public static List<String> toString(List<?> objs) {
		List<String> list = new ArrayList<String>();
		for (Object obj : objs) {
			list.add(obj.toString());
		}
		return list.size()>0?list:null;
	}
	
}
