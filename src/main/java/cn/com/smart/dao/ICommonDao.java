package cn.com.smart.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.exception.DaoException;

public interface ICommonDao {

	/**
     * 查询SQL
     * @param sql
     * @param toBean 结果要转换的对象bean
     * @return List<E> 返回toBean参数指定的对象集合
     * @throws DaoException
     */
	public <E> List<E> querySqlToBean(String sql,Class<?> toBean) throws DaoException;
	
	
	/**
	 * 带参数的SQL查询
	 * @param sql
	 * @param param
	 * @param toBean 结果要转换的对象bean
	 * @return List<E> 返回toBean参数指定的对象集合
	 * @throws DaoException
	 */
	public <E> List<E> querySqlToBean(String sql,Map<String, Object> param,Class<?> toBean) throws DaoException;
	
	
	/**
	 * SQL查询(带分页)
	 * @param sql
	 * @param param
	 * @param toBean 结果要转换的对象bean
	 * @param start 从第几条开始查询
	 * @param rows 每页显示几条记录
	 * @return List<E> 返回toBean参数指定的对象集合
	 * @throws DaoException
	 */
	public <E> List<E> querySqlToBean(String sql,Map<String, Object> param,Class<?> toBean,Integer start, Integer rows) throws DaoException;
 
    
	/**
	 * 查询SQL语句
	 * @param sql
	 * @return List<Object> 返回对象集合
	 * @throws DaoException
	 */
	public List<Object> queryObjSql(String sql) throws DaoException;
	

	/**
	 * 带参数的SQL查询,返回类型为Object数组
	 * @param sql
	 * @param param
	 * @return List<Object> 返回对象集合
	 * @throws DaoException
	 */
	public List<Object> queryObjSql(String sql,Map<String, Object> param) throws DaoException;
	

	/**
	 * SQL查询(带分页)
	 * @param sql
	 * @param param
	 * @param start 从第几条开始查询
	 * @param rows 每页显示几条记录
	 * @return List<Object> 返回对象集合
	 * @throws DaoException
	 */
	public List<Object> queryObjSql(String sql,Map<String, Object> param,Integer start, Integer rows) throws DaoException;
 
	/**
     * 查询SQL
     * @param sql
     * @param entity 实体对象
     * @return List<E> 返回entity参数指定的对象集合
     * @throws DaoException
     */
	public <E> List<E> querySqlToEntity(String sql,Class<? extends BaseBeanImpl> entity) throws DaoException;
	
	
	/**
	 * 带参数的SQL查询
	 * @param sql
	 * @param param
	 * @param entity 实体对象
	 * @return List<E> 返回entity参数指定的对象集合
	 * @throws DaoException
	 */
	public <E> List<E> querySqlToEntity(String sql,Map<String, Object> param,Class<? extends BaseBeanImpl> entity) throws DaoException;
	
	
	/**
	 * SQL查询(带分页)
	 * @param sql
	 * @param param
	 * @param entity 实体对象
	 * @param start 从第几条开始查询
	 * @param rows 每页显示几条记录
	 * @return List<E> 返回entity参数指定的对象集合
	 * @throws DaoException
	 */
	public <E> List<E> querySqlToEntity(String sql,Map<String, Object> param,Class<? extends BaseBeanImpl> entity,Integer start, Integer rows) throws DaoException;
 
	
	/**
     * 直接执行sql语句进行统计
     * @param sql 
     * @return Long
     * @throws DaoException
     */
    public Long exeCountSql(String sql) throws DaoException;
    
    /**
     * 直接执行sql语句进行统计
     * @param sql
     * @param param
     * @return Long
     * @throws DaoException
     */
    public Long exeCountSql(String sql,Map<String, Object> param) throws DaoException;
    
    
    /**
     * 查询统计SQL <br />
     * 会在SQL语句前加count方法 <br />
     * 如：select count(*) from (sql)
     * @param sql
     * @return Long
     * @throws DaoException
     */
    public Long countSql(String sql) throws DaoException;
 
    /**
     * 查询统计SQL <br />
     * 会在SQL语句前加count方法 <br />
     * 如：select count(*) from (sql)
     * @param sql
     * @param param 参数
     * @return Long
     * @throws DaoException
     */
    public Long countSql(String sql, Map<String, Object> param) throws DaoException;
    
    /**
     * 保存一个对象
     * @param bean 
     * @return 成功返回ID值
     * @throws DaoException
     */
    public Serializable saveObj(BaseBean bean) throws DaoException;
    
    
    /**
     * 保存一个对象
     * @param bean 
     * @return 成功返回ID值
     * @throws DaoException
     */
    public List<Serializable> saveObj(List<? extends BaseBean> beans) throws DaoException;
    
    /**
     * 更新对象
     * @param bean
     * @return Boolean <br />
     * 成功返回:true；否则返回:false
     * @throws DaoException
     */
    public boolean updateObj(BaseBean bean) throws DaoException;
    
    /**
     * 更新对象
     * @param beans
     * @return Boolean <br />
     * 成功返回:true；否则返回:false
     * @throws DaoException
     */
    public boolean updateObj(List<? extends BaseBean> beans) throws DaoException;
    
    
    /**
     * 执行SQL语句
     * @param sql
     * @return Integer
     * @throws DaoException
     */
	public Integer executeSql(String sql) throws DaoException;
	
	/**
	 * 执行SQL语句
	 * @param sql
	 * @param param
	 * @return Integer
	 * @throws DaoException
	 */
    public Integer executeSql(String sql,Map<String, Object> param) throws DaoException;
    
    /**
     * 执行SQL语句
     * @param sql
     * @param params
     * @return Integer
     * @throws DaoException
     */
    public Integer executeSql(String sql, List<Map<String, Object>> params) throws DaoException;
    
    
    /**
     * 批量执行SQL语句
     * @param sqls
     * @param param
     * @return Boolean 执行成功返回:true
     * @throws DaoException
     */
    public boolean executeSql(List<String> sqls,Map<String, Object> param) throws DaoException;
    
    /**
     * 批量执行SQL语句
     * @param sqls
     * @param param
     * @return Boolean 执行成功返回:true
     * @throws DaoException
     */
    public boolean executeSql(String[] sqls,Map<String, Object> param) throws DaoException;
    
    /**
     * 批量执行SQL语句
     * @param sqls
     * @param params
     * @return Boolean
     * @throws DaoException
     */
    public boolean executeSql(List<String> sqls,List<Map<String, Object>> params) throws DaoException;
    
    /**
     * 批量执行SQL语句
     * @param sqls
     * @param params
     * @return Boolean
     * @throws DaoException
     */
    public boolean executeSql(String[] sqls,List<Map<String, Object>> params) throws DaoException;
	
}
