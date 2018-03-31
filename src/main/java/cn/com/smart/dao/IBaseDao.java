package cn.com.smart.dao;

import java.io.Serializable;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.exception.DaoException;

/**
 * 基础DAO的接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public interface IBaseDao<T extends BaseBean> extends IUpdateDao<T> {
 
	 /**
	  * 获取序号 
	  * @param id
	  * @return Integer
	  * @throws DaoException
	  */
    public int getSortOrder(Serializable id) throws DaoException;
    
}
