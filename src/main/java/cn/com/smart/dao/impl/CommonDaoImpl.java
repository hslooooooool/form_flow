package cn.com.smart.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;
import cn.com.smart.builder.SQLBuilder;
import cn.com.smart.dao.ICommonDao;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.sqlmap.SQLVarParamFilter;
import cn.com.smart.validate.ExecuteValidator;
import cn.com.smart.validate.ValidateException;
import cn.com.smart.validate.Validator;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

public class CommonDaoImpl implements ICommonDao {
    
    protected Logger log = null;

	public CommonDaoImpl() {
        log = LoggerFactory.getLogger(getClass());
    }

    @Autowired
	protected SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Long exeCountSql(String sql, Map<String, Object> param) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(sql)) {
			try {
				log.info("统计数据SQL["+sql+"]");
				SQLQuery query = (SQLQuery)getQuery(sql, param, true);
				Object obj = query.uniqueResult();
				total = Long.parseLong(obj.toString());
			} catch (Exception e) {
				log.info("统计数据SQL["+sql+"]--[异常]--["+e.getMessage()+"]");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}
		}
		return total;
	}
	
	@Override
	public Long countSql(String sql, Map<String, Object> param) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(sql)) {
			//sql = "select count(*) from ("+sql+") t";
			String countSql = SQLBuilder.countSQL(sql);
			if(StringUtils.isEmpty(countSql)) {
				throw new NullArgumentException("统计SQL语句为空");
			}
			total = exeCountSql(countSql, param);
		}
		return total;
	}

	@Override
	public Integer executeSql(String sql) throws DaoException {
		int result = 0;
		if(StringUtils.isNotEmpty(sql)) {
			log.info("执行SQL["+sql+"]");
			try {
				result = getQuery(sql, null, true).executeUpdate();
			} catch (Exception e) {
				log.info("执行SQL["+sql+"]--[异常]--["+e.getMessage()+"]");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
		    }
		}
		return result;
	}

	@Override
	public Integer executeSql(String sql,Map<String, Object> param) throws DaoException {
		int result = 0;
		if(StringUtils.isNotEmpty(sql)) {
			try {
				log.info("执行SQL["+sql+"]");
			    result = getQuery(sql, param, true).executeUpdate(); 
			}catch (Exception e) {
				log.info("执行SQL["+sql+"]--[异常]--["+e.getMessage()+"]");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
		    }
		}
		return result;
	}

	@Override
	public Integer executeSql(String sql,List<Map<String, Object>> params) throws DaoException {
		int result = 0;
		if(StringUtils.isEmpty(sql)) {
			return result;
		}
		log.info("执行SQL["+sql+"]");
		try {
			if (null != params && params.size() > 0) {
				for(Map<String,Object> param : params) {
					result += getQuery(sql, param, true).executeUpdate();
				}
			}
		} catch (Exception e) {
			log.info("执行SQL["+sql+"]--[异常]--["+e.getMessage()+"]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
	    }
		return result;
	}


	@Override
	public boolean executeSql(List<String> sqls, Map<String, Object> param) throws DaoException {
		boolean result = false;
		if(null != sqls && sqls.size()>0) {
			result = executeSql(sqls.toArray(new String[sqls.size()]),param);
		}
		return result;
	}


	@Override
	public boolean executeSql(String[] sqls, Map<String, Object> param) throws DaoException {
		boolean result = false;
		if(null != sqls && sqls.length>0) {
			int res = 0;
			try {
			   for(String sql : sqls) {
				   if(StringUtils.isNotEmpty(sql)) {
					  res += getQuery(sql, param, true).executeUpdate();
				   }
			    }
			} catch (Exception e) {
				log.info("执行SQL-[异常]--["+e.getMessage()+"]");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}
			result = res>0?true:false;
		}
		return result;
	}


	@Override
	public boolean executeSql(List<String> sqls,List<Map<String, Object>> params) throws DaoException {
		boolean result = false;
		if(null != sqls && sqls.size()>0) {
			result = executeSql(sqls.toArray(new String[sqls.size()]),params);
		}
		return result;
	}


	@Override
	public boolean executeSql(String[] sqls, List<Map<String, Object>> params) throws DaoException {
		boolean result = false;
		if(null != sqls && sqls.length>0) {
			int res = 0;
			int count = 0;
			try {
			    for(String sql : sqls) {
			    	if(StringUtils.isNotEmpty(sql)) {
			    		log.info("执行SQL["+sql+"]");
						if (null != params && params.size() > 0) {
							Map<String, Object> param = params.get(count);
							res += getQuery(sql, param, true).executeUpdate();
						}
					    count++;
			    	}
			   }
			} catch (Exception e) {
				log.info("执行SQL--[异常]--["+e.getMessage()+"]");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}
			result = res>0?true:false;
		}
		return result;
	}
	
	@Override
	public Serializable saveObj(BaseBean bean) throws DaoException {
		Serializable id = null;
		if(null != bean){
			if(StringUtils.isEmpty(bean.getId())) {
				String prefix = bean.getPrefix();
				String idNum = StringUtils.createSerialNum();
				if(StringUtils.isNotEmpty(prefix)) {
					idNum = prefix.toUpperCase()+"_"+idNum; 
				}
				bean.setId(idNum);
			}
			if(bean instanceof DateBean && ((DateBean)bean).getCreateTime() == null) {
				((DateBean)bean).setCreateTime(new Date());
			}
			//验证数据
			Validator validator = new ExecuteValidator(bean);
			try {
				log.info("正在验证数据格式...");
				if(validator.validate()) {
					log.info("保存数据...");
					try {
						id = getSession().save(bean);
						log.info("数据保存[成功]...");
					} catch (Exception e) {
						log.info("数据保存[失败]...");
						e.printStackTrace();
						id = null;
					}
				} else {
					log.error("验证数据格式[失败]...");
				}
			} catch (ValidateException e1) {
				log.info("验证数据失败---["+e1.getMessage()+"]--");
				e1.getStackTrace();
				throw new DaoException(e1.getMessage(),e1.getCause());
			} catch (Exception e) {
				e.getStackTrace();
				throw new DaoException(e.getMessage(),e.getCause());
			}
		}
		return id;
	}
	
	
	@Override
	public List<Serializable> saveObj(List<? extends BaseBean> beans) throws DaoException {
		List<Serializable> ids = null;
		boolean is = false;
		log.info("批量保存数据...");
		if(null != beans && beans.size()>0) {
			ids = new ArrayList<Serializable>();
			Validator validator = null;
			try {
				for (BaseBean o : beans) {
					if(StringUtils.isEmpty(o.getId())) {
						String prefix = o.getPrefix();
						String idNum = StringUtils.createSerialNum();
						if(StringUtils.isNotEmpty(prefix)) {
							idNum = prefix.toUpperCase()+"-"+idNum; 
						}
						o.setId(idNum);
					}
					if(o instanceof DateBean && ((DateBean)o).getCreateTime() == null) {
						((DateBean)o).setCreateTime(new Date());
					}
					//验证数据
					validator = new ExecuteValidator(o);
					if(validator.validate()) {
						Serializable id = getSession().save(o);
						if(null != id) {
							ids.add(id);
							is = true;
						} else {
							is = false;
							ids = null;
							break;
						}
					} else {
						log.info("数据验证失败...");
						is = false;
						break;
					}
				}
				if(is) {
				  log.info("数据保存[成功]...");
				} else {
					log.info("数据保存[失败]...");
					ids = null;
				}
			} catch (ValidateException e) {
				log.info("验证数据失败---["+e.getMessage()+"]--");
				log.info("数据保存[失败]...");
				e.getStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}catch (Exception e) {
				log.info("数据保存[失败]...");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}
		}
		return ids;
	}
	
	
	@Override
	public boolean updateObj(BaseBean bean) throws DaoException {
		boolean is = false;
		if(null == bean) {
	    	return is;
	    }
		log.info("更新数据ID["+bean.getId()+"]");
		Validator validator = new ExecuteValidator(bean);
		try {
			log.info("正在验证数据格式...");
			if(validator.validate()) {
				try {
					getSession().update(bean);
					is = true;
					log.info("更新数据ID["+bean.getId()+"][成功]");
				} catch (Exception e) {
					log.info("更新数据ID["+bean.getId()+"][失败]");
					is = false;
					e.printStackTrace();
				}	
			} else {
				log.info("数据格式验证[失败]");
			}
		} catch (ValidateException e) {
			log.info("数据格式验证[失败]----["+e.getMessage()+"]--");
			is = false;
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return is;
	}

	@Override
	public boolean updateObj(List<? extends BaseBean> beans) throws DaoException {
		boolean is = false;
		log.info("批量更新数据...");
		if(null != beans && beans.size()>0) {
			Validator validator = null;
			try {
				for (BaseBean o : beans) {
					//验证数据
					validator = new ExecuteValidator(o);
					log.info("正在验证数据格式...");
					if(validator.validate()) {
						getSession().update(o);
						is = true;
					} else {
						log.info("数据验证失败...");
						is = false;
						break;
					}
				}
				if(is) {
				  log.info("数据更新[成功]...");
				} else {
					log.info("数据更新[失败]...");
				}
			} catch (ValidateException e) {
				log.info("验证数据失败---["+e.getMessage()+"]--");
				log.info("数据更新[失败]...");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}catch (Exception e) {
				log.info("数据更新[失败]...");
				e.printStackTrace();
				throw new DaoException(e.getLocalizedMessage(), e.getCause());
			}
		}
		return is;
	}
	

	/**
	 * 获取查询对象
	 * @param statement
	 * @param param
	 * @param isSql 标记statement参数内容是否为SQL语句
	 * <ul><li>true--表示statement参数内容为SQL语句</li>
	 *     <li>false--表示statement参数内容为HQL语句</li>
	 * </ul>
	 * @return Query
	 * @throws Exception
	 */
	protected Query getQuery(String statement,Map<String,Object> param,boolean isSql) throws Exception {
		Query query = null;
		if(StringUtils.isEmpty(statement)) {
			throw new NullArgumentException("statement 参数为空 ");
		}
		//处理SQL或HQL语句
		SQLVarParamFilter sqlVarFilter = new SQLVarParamFilter(statement, param);
		statement = sqlVarFilter.filter();
		param = sqlVarFilter.getParams();
		query = isSql?(getSession().createSQLQuery(statement)):(getSession().createQuery(statement));
		if(null != param) 
			query.setProperties(param);
		return query;
	}
	
	/**
	 * 获取查询对象
	 * @param statement
	 * @param isSql 标记statement参数内容是否为SQL语句
	 * <ul><li>true--表示statement参数内容为SQL语句</li>
	 *     <li>false--表示statement参数内容为HQL语句</li>
	 * </ul>
	 * @return Query
	 * @throws Exception
	 */
	protected Query getQuery(String statement,boolean isSql) throws Exception {
		Query query = null;
		if(StringUtils.isNotEmpty(statement)) {
			query = isSql?(getSession().createSQLQuery(statement)):(getSession().createQuery(statement));
		}
		return query;
	}
	
	
	@Override
	public List<Object> queryObjSql(String sql) throws DaoException {
		return queryObjSql(sql, null);
	}
	
	@Override
	public List<Object> queryObjSql(String sql,Map<String, Object> param) throws DaoException {
		return queryObjSql(sql, param, null, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryObjSql(String sql,Map<String, Object> param,Integer start, Integer rows) throws DaoException {
		List<Object> list = null;
		if(StringUtils.isEmpty(sql)) {
			log.error("SQL语句为空！");
			return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, param, true);
			if(null != start && null != rows) {
				query.setFirstResult(start);
				query.setMaxResults(rows);
			}
			list = query.list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}
	
	@Override
	public Long exeCountSql(String sql) throws DaoException {
		return exeCountSql(sql, null);
	}
	
	@Override
	public Long countSql(String sql) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(sql)) {
			String countSql = SQLBuilder.countSQL(sql);
			if(StringUtils.isEmpty(countSql)) {
				throw new NullArgumentException("统计SQL语句为空");
			}
			total = exeCountSql(countSql);
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToBean(String sql, Class<?> toBean) throws DaoException {
		List<E> list = null;
		if(StringUtils.isEmpty(sql)) {
	    	return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, true);
			addScalars(query, toBean);
			list = query.setResultTransformer(Transformers.aliasToBean(toBean)).list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToBean(String sql, Map<String, Object> param, Class<?> toBean) throws DaoException {
		List<E> list = null;
		if(StringUtils.isEmpty(sql)) {
	    	return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, param, true);
			addScalars(query, toBean);
			list = query.setResultTransformer(Transformers.aliasToBean(toBean)).list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToBean(String sql, Map<String, Object> param, Class<?> toBean, Integer start, Integer rows) throws DaoException {
		List<E> list = null;
		if(StringUtils.isEmpty(sql)) {
	    	return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, param, true);
			if(null != start && null != rows) {
				query.setFirstResult(start);
				query.setMaxResults(rows);
			}
			addScalars(query, toBean);
			list = query.setResultTransformer(Transformers.aliasToBean(toBean)).list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}
	
	/**
	 * SQL查询对象中添加toBeanClass属性
	 * @param sqlQuery SQLQuery对象
	 * @param toBeanClass 转换Bean
	 * @return 返回处理后的SQLQuery对象
	 */
	protected SQLQuery addScalars(SQLQuery sqlQuery, Class<?> toBeanClass){
		if(null == toBeanClass) {
			return sqlQuery;
		}
		List<Field> fields = new ArrayList<Field>();
		getAllFields(fields, toBeanClass);
		for(Field field : fields){
			if(!field.getName().equals("serialVersionUID")){
				if(field.getType() == Integer.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
				} else if(field.getType() == Double.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
				} else if(field.getType() == Long.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
				} else if(field.getType() == Float.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
				} else if(field.getType() == Date.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.DATE);
				} else if(field.getType() == String.class){
					sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
				} else{
					sqlQuery.addScalar(field.getName());
				}
			}
		}
		return sqlQuery;
	}
	
	
	private void getAllFields(List<Field> fields,Class<?> toBeanClass) {
		if(null == fields || null == toBeanClass) {
			throw new NullArgumentException();
		}
		Field[] array = toBeanClass.getDeclaredFields();
		if(array.length > 0) {
			fields.addAll(Arrays.asList(array));
		}
		toBeanClass = toBeanClass.getSuperclass();
		if(null != toBeanClass && !(toBeanClass.isAssignableFrom(Object.class))) {
			getAllFields(fields, toBeanClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToEntity(String sql, Class<? extends BaseBeanImpl> entity) throws DaoException {
		List<E> list = null;
		if(StringUtils.isEmpty(sql)) {
	    	return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, true);
			query.addEntity(entity);
			list = query.list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToEntity(String sql, Map<String, Object> param, Class<? extends BaseBeanImpl> entity) throws DaoException {
		List<E> list = null;
		if(StringUtils.isEmpty(sql)) {
	    	return null;
	    }
		try {
			SQLQuery query = (SQLQuery)getQuery(sql, param, true);
			query.addEntity(entity);
			list = query.list();
			log.info("通过SQL查询数据[成功]");
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> querySqlToEntity(String sql, Map<String, Object> param, 
			Class<? extends BaseBeanImpl> entity, Integer start, Integer rows) throws DaoException {
		if (StringUtils.isEmpty(sql)) {
			return null;
		}
		try {
			SQLQuery query = (SQLQuery) getQuery(sql, param, true);
			if (null != start && null != rows) {
				query.setFirstResult(start);
				query.setMaxResults(rows);
			}
			query.addEntity(entity);
			List<E> list = query.list();
			log.info("通过SQL查询数据[成功]");
			return list;
		} catch (Exception e) {
			log.info("通过SQL查询数据[失败]");
			e.printStackTrace();
			throw new DaoException(e.getLocalizedMessage(), e.getCause());
		}
	}
}
