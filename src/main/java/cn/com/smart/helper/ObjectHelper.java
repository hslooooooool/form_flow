package cn.com.smart.helper;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
public class ObjectHelper {
	
	protected static final Logger log = Logger.getLogger(ObjectHelper.class);

	/**
	 * 处理时间对象
	 * @param lists
	 * @return List<Object>
	 */
	public static List<Object> handleObjDate(List<Object> lists) {
		if(null != lists && lists.size()>0) {
			for (Object obj : lists) {
				if (obj instanceof Object[]) {
					Object[] objs = (Object[])obj;
					int count=0;
					for (Object obj2 : objs) {
						if(obj2 instanceof Date) {
							//log.info("时间格式:"+obj2.toString());
							if(null != obj2 && StringUtils.isNotEmpty(obj2.toString())) {
								objs[count] = new String(obj2.toString().substring(0,obj2.toString().length()-2));
							}
						}
						count++;
					}
					//log.info("数组对象");
				} else {
				//	log.info("非数组对象");
				}
			}
		}
		return lists;
	}
	
}
