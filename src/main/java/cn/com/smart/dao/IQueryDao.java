package cn.com.smart.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.exception.DaoException;

/**
 * 查询DAO接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public interface IQueryDao<T extends BaseBean> extends IExecuteDao {

	 /**
	  * 主键查询
	  * @param id
	  * @return T 返回实体对象
	  * @throws DaoException
	  */
    public T find(Serializable id) throws DaoException;
    
    /**
     * 主键查询 
     * @param claszp
     * @param id
     * @return <E>
     * @throws DaoException
     */
    public <E> E find(Class<E> claszp,Serializable id) throws DaoException;
    
    
    /**
     * 多个主键批量查询
     * @param idArray
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> find(Serializable[] idArray) throws DaoException;
    
    /**
     * 多个主键批量查询
     * @param claszp
     * @param idArray
     * @return List<Object>
     * @throws DaoException
     */
    public List<Object> find(Class<?> claszp,Serializable[] idArray) throws DaoException;
    
    
    /**
     * 获得一个对象 
     * @param hql
     * @param param
     * @return T 返回实体对象
     * @throws DaoException
     */
    public T find(String hql, Map<String, Object> param) throws DaoException;
 
    
    /**
     * 查询所有数据
     * @return T 返回实体对象集合
     * @throws DaoException
     */
    public List<T> findAll() throws DaoException;
    
    
    /**
     * 查询所有数据
     * @param claszp
     * @return T 返回实体对象集合
     * @throws DaoException
     */
    public List<T> findAll(Class<?> claszp) throws DaoException;
    
    
    /**
     * 查询所有数据
     * @param claszp
     * @return List<Object> 返回对象集合
     * @throws DaoException
     */
    public List<Object> findObjAll(Class<?> claszp) throws DaoException;
 
    
    /**
     * 通过字段查询
     * @param param
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryByField(Map<String, Object> param) throws DaoException;
    
    /**
     * 通过字段查询
     * @param param
     * @param orderBy 排序 <br />
     * 如：按排序字段倒序( sortOrder asc)
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryByField(Map<String, Object> param,String orderBy) throws DaoException;
    
    /**
     * 通过字段查询
     * @param param 参数
     * @param start 开始位置
     * @param rows 返回数（返回数量，设置返回几条数据）
     * @param orderBy 排序 <br />
     * 如：按排序字段倒序( sortOrder asc)
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryByField(Map<String, Object> param, int start, int rows, String orderBy) throws DaoException;
    
    
    /**
     * 查询HQL语句
     * @param hql
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryHql(String hql) throws DaoException;
  
    
    /**
     * 查询HQL语句
     * @param hql
     * @param param
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryHql(String hql, Map<String, Object> param) throws DaoException;
    
    /**
     * 查询HQL语句集合(带分页)
     * @param hql
     * @param param
     * @param start 从第几条开始查询
     * @param rows 每页显示几条记录
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
    public List<T> queryHql(String hql, Map<String, Object> param, Integer start, Integer rows) throws DaoException;
    
    
    /**
     * 查询HQL语句
     * @param hql
     * @return List<Object> 返回对象集合
     * @throws DaoException
     */
    public List<Object> queryObjHql(String hql) throws DaoException;
    
   
    /**
     * 查询HQL语句
     * @param hql
     * @param param
     * @return List<Object> 返回对象集合
     * @throws DaoException
     */
    public List<Object> queryObjHql(String hql, Map<String, Object> param) throws DaoException; 
    
   
    /**
     * 查询集合(带分页)
     * @param hql
     * @param param
     * @param start 从第几条开始查询
     * @param rows  每页显示几条记录
     * @return List<Object> 返回对象集合
     * @throws DaoException
     */
    public List<Object> queryObjHql(String hql, Map<String, Object> param, Integer start, Integer rows) throws DaoException;
    
    /**
     * 查询HQL语句
     * @param hql HQL语句
     * @param param 参数
     * @return List<E> 返回对象集合
     * @throws DaoException
     */
    public <E> List<E> queryObjectByHql(String hql, Map<String, Object> param) throws DaoException;
    
    /**
     * 查询HQL语句
     * @param hql HQL语句
     * @param param 参数
     * @param start 从第几条开始查询
     * @param rows 每页显示几条记录
     * @return List<E> 返回对象集合
     * @throws DaoException
     */
    public <E> List<E> queryObjectByHql(String hql,Map<String, Object> param,Integer start,Integer rows) throws DaoException;
    
    
    /**
     * 查询SQL
     * @param sql
     * @return List<T> 返回实体对象集合
     * @throws DaoException
     */
	public List<T> querySql(String sql) throws DaoException;
	

	/**
	 * 带参数的SQL查询
	 * @param sql
	 * @param param
	 * @return List<T> 返回实体对象集合
	 * @throws DaoException
	 */
	public List<T> querySql(String sql,Map<String, Object> param) throws DaoException;
	

	/**
	 * SQL查询(带分页)
	 * @param sql
	 * @param param
	 * @param start 从第几条开始查询
	 * @param rows 每页显示几条记录
	 * @return List<T> 返回实体对象集合
	 * @throws DaoException
	 */
	public List<T> querySql(String sql,Map<String, Object> param,Integer start, Integer rows) throws DaoException;
 
    /**
     * 查询统计HQL<br />
     * 会在HQL语句前加count方法 <br />
     * 如：select count(*) from (hql)
     * @param hql
     * @return Long 返回统计结果
     * @throws DaoException
     */
    public Long count(String hql) throws DaoException;
 
    /**
     * 查询统计
     * @param param 参数
     * @return 返回统计结果 <br />
     * 返回类型为：Long
     * @throws DaoException
     */
    public Long count(Map<String, Object> param) throws DaoException;
    
    /**
     * 查询统计HQL <br />
     * 会在HQL语句前加count方法 <br />
     * 如：select count(*) from (hql)
     * @param hql
     * @param param 参数
     * @return 返回统计结果 <br />
     * 返回类型为：Long
     * @throws DaoException
     */
    public Long count(String hql, Map<String, Object> param) throws DaoException;
	
}
