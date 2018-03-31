package cn.com.smart.dao;

import java.io.Serializable;
import java.util.List;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.exception.DaoException;

/**
 * 更新DAO接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public interface IUpdateDao<T extends BaseBean> extends IDeleteDao<T> {

	/**
	 * 保存一个对象
	 * @param o
	 * @return 成功返回ID值
	 * @throws DaoException
	 */
    public Serializable save(T o) throws DaoException;
    
    /**
     * 批量保存
     * @param list 
     * @return List 成功返回id集合
     * @throws DaoException
     */
    public List<Serializable> save(List<T> list) throws DaoException;
 
    /**
     * 保存或更新对象
     * @param o
     * @return Boolean <br />
     * 成功返回:true；否则返回:false
     * @throws DaoException
     */
    public boolean saveOrUpdate(T o) throws DaoException;
    
    /**
     * 更新对象
     * @param o
     * @return Boolean <br />
     * 成功返回:true；否则返回:false
     * @throws DaoException
     */
    public boolean update(T o) throws DaoException;
    
    /**
     * 批量更新
     * @param list
     * @return Boolean <br />
     * 成功返回:true；否则返回:false
     * @throws DaoException
     */
    public boolean update(List<T> list) throws DaoException;
	
}
