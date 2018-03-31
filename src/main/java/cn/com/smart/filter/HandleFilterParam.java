package cn.com.smart.filter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import cn.com.smart.filter.bean.FilterParam;

import com.mixsmart.utils.StringUtils;

/**
 * 处理搜索参数 过滤参数值为空的 <br />
 * parseParam() 方法获取bean中的属性及值
 * @author lmq
 * 
 */
public class HandleFilterParam {

	private FilterParam searchParam;

	Map<String, Object> params = null;

	/**
	 * 处理搜索参数
	 * @param searchParam
	 */
	public HandleFilterParam(FilterParam searchParam) {
		this.searchParam = searchParam;
		if(null != searchParam) {
			params = new HashMap<String, Object>();
			parseParam(searchParam.getClass());
		}
	}

	/**
	 * 解析参数
	 * @param clasz
	 */
	protected void parseParam(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		if(fields.length > 0) {
			try {
				for (int i = 0; i < fields.length; i++) {
				    if(Modifier.isStatic(fields[i].getModifiers()) || Modifier.isFinal(fields[i].getModifiers())) {
		                continue;
		            }
					PropertyDescriptor propertyDesc = new PropertyDescriptor(fields[i].getName(), clazz);
					Method method = propertyDesc.getReadMethod();
					Object value = method.invoke(this.searchParam);
					if(null != value && StringUtils.isNotEmpty(value.toString())) {
						params.put(fields[i].getName(), value);
					}
				}
			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Class<?> superClazz = clazz.getSuperclass();
			if (null != superClazz) {
				parseParam(superClazz);
			}
		}
	}

	/**
	 * 获取参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getParams() {
		return params.isEmpty()?null:params;
	}

}
