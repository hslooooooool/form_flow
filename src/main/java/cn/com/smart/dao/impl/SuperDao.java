package cn.com.smart.dao.impl;

import java.lang.reflect.ParameterizedType;

import cn.com.smart.bean.BaseBean;

/**
 * 所有实现DAO的父类
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * @param <T>
 * 
 * @serial 2015年8月22日
 */
public abstract class SuperDao<T extends BaseBean> extends ExecuteDaoImpl {

	protected Class<?> clazz;

	public SuperDao() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			if(type instanceof ParameterizedType) {
				this.clazz = (Class<?>) type.getActualTypeArguments()[0];
			} else {
				this.clazz = Object.class;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.clazz = Object.class;
		}
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
}
