package cn.com.smart.dao;

import java.util.List;
import java.util.Map;

import cn.com.smart.exception.DaoException;

/**
 * 执行DAO接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public interface IExecuteDao extends ICommonDao {

	 /**
	  * 执行HQL语句
	  * @param hql HQL语句
	  * @return Integer
	  * @throws DaoException
	  */
    public Integer executeHql(String hql) throws DaoException;
 
    /**
     * 执行HQL语句
     * @param hql
     * @param param 参数
     * @return Integer
     * @throws DaoException
     */
    public Integer executeHql(String hql,Map<String, Object> param) throws DaoException;
    
    /**
     * 执行HQL语句
     * @param hql
     * @param params 参数
     * @return Integer
     * @throws DaoException
     */
    public Integer executeHql(String hql, List<Map<String, Object>> params) throws DaoException;
	
}
