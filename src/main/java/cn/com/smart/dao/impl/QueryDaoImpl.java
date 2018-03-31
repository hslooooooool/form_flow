package cn.com.smart.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.dao.IQueryDao;
import cn.com.smart.exception.DaoException;

/**
 * 查询Dao实现类
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <T>
 */
public abstract class QueryDaoImpl<T extends BaseBean> extends SuperDao<T> implements IQueryDao<T>{
	
	@SuppressWarnings("unchecked")
	@Override
	public T find(Serializable id) throws DaoException {
		T t = null;
		if(null == id || StringUtils.isEmpty(id.toString())) {
	    	return t;
	    }
		log.info("通过主键ID["+id+"]查询数据");
		try {
			 t = (T) getSession().get(clazz, id);
		    log.info("通过主键ID["+id+"]查询数据[成功]");
		} catch (Exception e) {
			log.info("通过主键ID["+id+"]查询数据[失败]");
			e.printStackTrace();
			t = null;
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E find(Class<E> clazz,Serializable id) throws DaoException {
		E t = null;
		if(null == clazz || null == id || StringUtils.isEmpty(id.toString())) {
	    	return t;
	    }
		log.info("通过主键ID["+id+"]查询数据");
		try {
			 t = (E) getSession().get(clazz, id);
		    log.info("通过主键ID["+id+"]查询数据[成功]");
		} catch (Exception e) {
			log.info("通过主键ID["+id+"]查询数据[失败]");
			e.printStackTrace();
			t = null;
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return t;
	}
	
	@Override
	public List<T> find(Serializable[] idArray) throws DaoException {
		List<T> lists = null;
		try {
			if(null == idArray || idArray.length<1) {
		    	return lists;
		    }
			log.info("多个主键批量查询数据");
			if(idArray.length>0) {
				String hql = " from "+clazz.getName()+" where id in (:idArray)";
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("idArray", idArray);
				lists = queryHql(hql,param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return lists;
	}
	
	@Override
	public List<Object> find(Class<?> claszp,Serializable[] idArray) throws DaoException {
		List<Object> lists = null;
		try {
			if(null == claszp || null == idArray || idArray.length<1) {
		    	return lists;
		    }
			log.info("多个主键批量查询数据");
			if(idArray.length>0) {
				String hql = " from "+claszp.getName()+" where id in (:idArray)";
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("idArray", idArray);
				lists = queryObjHql(hql,param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T find(String hql, Map<String, Object> param) throws DaoException {
		T t = null;
		if(StringUtils.isEmpty(hql)) {
	    	return t;
	    }
		try {
			Query query = getQuery(hql, param, false);
			t = (T) query.uniqueResult();
			log.info("查询数据HQL["+hql+"]--成功--");
		} catch (Exception e) {
			log.info("查询数据HQL["+hql+"]--失败--");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return t;
	}

	@Override
	public List<T> findAll() throws DaoException {
		return this.findAll(getClazz());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<?> clazz) throws DaoException {
		List<T> lists = null;
		if(null == clazz) {
	    	return lists;
	    }
		log.info("获取表中的所有数据");
		try {
			lists = (List<T>) getQuery(" from "+clazz.getName(),false).list();
		    log.info("获取表中的所有数据[成功]");
		} catch (Exception e) {
			log.info("获取表中的所有数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findObjAll(Class<?> clazz) throws DaoException {
		List<Object> lists = null;
		if(null == clazz) {
	    	return lists;
	    }
		log.info("获取表中的所有数据");
		try {
			lists = (List<Object>) getQuery(" from "+clazz.getName(), false).list();
		    log.info("获取表中的所有数据[成功]");
		} catch (Exception e) {
			log.info("获取表中的所有数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return lists;
	}
	
	@Override
	public List<T> queryByField(Map<String, Object> param) throws DaoException {
		String hql = combinHQL(param);
		log.info("通过HQL查询数据["+hql+"]");
		List<T> list = queryHql(hql, param);
		return list;
	}
	
	@Override
	public List<T> queryByField(Map<String, Object> param,String orderBy) throws DaoException {
		String hql = combinHQL(param);
		if(StringUtils.isNotEmpty(orderBy)) {
			hql += " order by "+orderBy;
		}
		log.info("通过HQL查询数据["+hql+"]");
		List<T> list = queryHql(hql, param);
		return list;
	}
	
	@Override
	public List<T> queryByField(Map<String, Object> param, int start, int rows,String orderBy) throws DaoException {
		String hql = combinHQL(param);
		if(StringUtils.isNotEmpty(orderBy)) {
			hql += " order by "+orderBy;
		}
		log.info("通过HQL查询数据["+hql+"]");
		List<T> list = queryHql(hql, param, start, rows);
		return list;
	}
	
	@Override
	public List<T> queryHql(String hql) throws DaoException {
		return queryHql(hql, null);
	}
	
	@Override
	public List<T> queryHql(String hql, Map<String, Object> param) throws DaoException {
		return queryHql(hql, param, null, null);
	}

	@Override
	public List<T> queryHql(String hql,Map<String, Object> param,Integer start,Integer rows) throws DaoException {
		if(StringUtils.isEmpty(hql)) {
	    	return null;
	    }
		return getList(hql,param, false, start, rows);
	}
	
	@Override
	public List<Object> queryObjHql(String hql) throws DaoException {
		return queryObjHql(hql,null);
	}
	
	@Override
	public List<Object> queryObjHql(String hql, Map<String, Object> param) throws DaoException {
		return queryObjHql(hql, param, null, null);
	}

	@Override
	public List<Object> queryObjHql(String hql,Map<String, Object> param,Integer start,Integer rows) throws DaoException {
		if(StringUtils.isEmpty(hql)) {
	    	return null;
	    }
		return getList(hql, param, false, start, rows);
	}
	
	@Override
    public <E> List<E> queryObjectByHql(String hql, Map<String, Object> param) throws DaoException {
        return queryObjectByHql(hql, param, null, null);
    }
    
    @Override
    public <E> List<E> queryObjectByHql(String hql,Map<String, Object> param,Integer start,Integer rows) throws DaoException {
        List<E> list = getList(hql, param, false, start, rows);
        return list;
    }
	
	@Override
	public List<T> querySql(String sql) throws DaoException {
		return querySql(sql, null);
	}

	@Override
	public List<T> querySql(String sql, Map<String, Object> param) throws DaoException {
		return querySql(sql, param, null, null);
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<T> querySql(String sql, Map<String, Object> param,Integer start, Integer rows) throws DaoException {
		if (StringUtils.isEmpty(sql)) {
			return null;
		}
		try {
			SQLQuery query = (SQLQuery) getQuery(sql, param, true);
			if (null != start && null != rows) {
				query.setFirstResult(start);
				query.setMaxResults(rows);
			}
			query.addEntity(getClazz());
			List<T> list = query.list();
			log.info("通过SQL查询数据[成功]");
			return list;
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
	}

	@Override
	public Long count(String hql) throws DaoException {
		return count(hql, null);
	}
	
	@Override
	public Long count(Map<String,Object> param) throws DaoException {
		String hql = combinHQL(param);
		return count(hql,param);
	}
	
	@Override
	public Long count(String hql, Map<String, Object> param) throws DaoException {
		long total = 0;
		if(StringUtils.isEmpty(hql)) {
	    	return total;
	    }
		if(StringUtils.isNotEmpty(hql)) {
			hql = "select count(*) "+hql;
		}
		try {
			Query query = getQuery(hql, param, false);
			Object obj = query.uniqueResult();
			total = Long.parseLong(obj.toString());
		} catch (Exception e) {
			log.info("统计数据HQL["+hql+"]--[异常]--["+e.getMessage()+"]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return total;
	}
	
	/**
	 * 组合HQL语句
	 * @param param 参数
	 * @return 返回组合后的HQL语句
	 */
	private String combinHQL(Map<String,Object> param) {
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from "+clazz.getName()+" ");
		if(null != param && param.size()>0) {
			hqlBuilder.append(" where ");
			buildWhere(hqlBuilder, param);
		}//end if
		return hqlBuilder.toString();
	}

	/**
	 * 获取列表
	 * @param statement 语句
	 * @param param 参数
	 * @param isSql 是否SQL语句
	 * @param start 开始位置
	 * @param rows 显示长度
	 * @param <E> 返回类型
	 * @return 返回列表
	 */
	@SuppressWarnings("unchecked")
    private <E> List<E> getList(String statement, Map<String, Object> param, boolean isSql, Integer start, Integer rows) {
		try {
			Query query = getQuery(statement, param, isSql);
			if(null != start && null != rows) {
				query.setFirstResult(start);
				query.setMaxResults(rows);
			}
			List<E> list = query.list();
			log.info("通过HQL查询数据[成功]");
			return list;
		} catch (Exception e) {
			log.info("通过HQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
	}


	/**
	 * 参数组合成HQL条件
	 * @param hqlBuilder StringBuilder对象
	 * @param param 参数
	 */
	protected void buildWhere(StringBuilder hqlBuilder, Map<String, Object> param) {
		int count = 0;
		for (String key : param.keySet()) {
			if(count > 0) {
				hqlBuilder.append(" and ");
			}
			if(null != param.get(key) && param.get(key).getClass().isArray()) {
				hqlBuilder.append(key+" in (:"+key+")");
			} else {
				hqlBuilder.append(key+"=:"+key);
			}
			count++;
		}
	}
}
