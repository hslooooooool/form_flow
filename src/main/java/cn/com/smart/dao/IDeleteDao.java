package cn.com.smart.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.exception.DaoException;

/**
 * 删除DAO接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public interface IDeleteDao<T extends BaseBean> extends IQueryDao<T> {

	/**
	 * 删除一个对象 
	 * @param o 实体Bean
	 * @return Boolean 删除成功返回：true；失败返回：false
	 * @throws DaoException
	 */
    public boolean delete(T o) throws DaoException;
    
    /**
     * 按实体Bean 批量删除
     * @param list 实体对象集合
     * @return Boolean 删除成功返回：true；失败返回：false
     * @throws DaoException
     */
    public boolean delete(List<T> list) throws DaoException;
    
    /**
     * 根据主键ID删除
     * @param id 主键ID 多个主键ID之间用英文逗号“,”隔开
     * @return Boolean 删除成功返回：true；失败返回：false
     * @throws DaoException
     */
    public boolean delete(Serializable id) throws DaoException;
    
    /**
     * 根据参数删除
     * 与deleteByField方法一样
     * @param param 
     * @return Boolean 删除成功返回：true；失败返回：false
     * @see cn.com.smart.dao.IDeleteDao#deleteByField(Map)
     * @throws DaoException
     */
    public boolean delete(Map<String,Object> param) throws DaoException;
    
    /**
     * 根据实体Bean的属性删除
     * @param param 实体Bean属性与值对应MAP <br />
     * 如:param.put("name","张三")；表示删除实体Bean属性为name，值为“张三” 对应数据库表中的数据
     * @return Boolean 删除成功返回：true；失败返回：false
     * @throws DaoException
     */
    public boolean deleteByField(Map<String,Object> param) throws DaoException;
	
}
