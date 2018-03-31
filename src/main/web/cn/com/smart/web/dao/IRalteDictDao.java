package cn.com.smart.web.dao;

import java.util.List;
import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.exception.DaoException;
import cn.com.smart.web.bean.entity.TNDict;

/**
 * 关联数据字典
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 * 
 * @param <T>
 */
public interface IRalteDictDao<T extends BaseBean> {

	 /**
     * 关联数据字典
     * @param ts
     * @param busiValue
     */
    public void relateDict(List<T> ts,String busiValue) throws DaoException;
    
    /**
     * 关联数据字典
     * @param t
     * @param busiValue
     */
    public void relateDict(T t,String busiValue) throws DaoException;
    
    /**
     * 关联数据字典（当多个表字段关联数据字典时使用该方法）
     * @param ts
     * @param busiValues 
     * @throws DaoException
     */
    public void relateDict(List<T> ts,String[] busiValues) throws DaoException;
    
    /**
     * 关联数据字典（当多个表字段关联数据字典时使用该方法）
     * @param t
     * @param busiValues
     * @throws DaoException
     */
    public void relateDict(T t,String[] busiValues) throws DaoException;
    
    
    /**
     * 更具查询出来的数据字典的值； 赋值给具体的业务对象
     * @param ts
     * @param dicts
     */
    public void asgmtValueByDict(List<T> ts,List<TNDict> dicts);
    
    /**
     * 更具查询出来的数据字典的值； 赋值给具体的业务对象
     * @param t
     * @param dicts
     */
    public void asgmtValueByDict(T t,List<TNDict> dicts);
    
    /**
     * 更具查询出来的数据字典的值； 赋值给具体的业务对象
     * @param ts
     * @param dictMaps 结构：busiValue为key，相关值放到List <br />
     * 获取数据时，通过dictMaps.get(busiValue)获取对应数据
     */
    public void asgmtValueByDict(List<T> ts,Map<String,List<TNDict>> dictMaps);
    
    /**
     * 更具查询出来的数据字典的值； 赋值给具体的业务对象
     * @param t
     * @param dictMaps 结构：busiValue为key，相关值放到List <br />
     * 获取数据时，通过dictMaps.get(busiValue)获取对应数据
     */
    public void asgmtValueByDict(T t,Map<String,List<TNDict>> dictMaps);
   
	
}
