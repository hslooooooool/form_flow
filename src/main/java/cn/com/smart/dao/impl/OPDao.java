package cn.com.smart.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.smart.bean.NullEntity;
import cn.com.smart.exception.DaoException;
import cn.com.smart.filter.HandleFilterParam;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

/**
 * 通用DAO操作
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 */
@Repository("opDao")
public class OPDao extends BaseDaoImpl<NullEntity> {

	private SqlMapping sqlMap;
	
	public OPDao() {
		sqlMap = SQLResUtil.getOpSqlMap();
	}
	
	
	/**
	 * 查询数据
	 * @param resId SQL名称
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			String sql = sqlMap.getSQL(resId);
			if(StringUtils.isNotEmpty(sql)) {
				objs = queryObjSql(sql);
			} else {
				throw new DaoException("SQL语句为空--["+resId+"]值为空");
			}
			sql = null;
		}
		return objs;
	}
	
	
	/**
	 * 查询数据
	 * @param resId SQL名称
	 * @param params 参数
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,Map<String,Object> params) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			String sql = sqlMap.getSQL(resId);
			if(StringUtils.isNotEmpty(sql)) {
				objs = queryObjSql(sql, params);
			} else {
				throw new DaoException("SQL语句为空--["+resId+"]值为空");
			}
			sql = null;
		}
		params = null;
		return objs;
	}
	
	
	/**
	 * 查询数据
	 * @param resId SQL名称
	 * @param filterParam 要过滤的参数
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,FilterParam filterParam) throws DaoException{
		List<Object> objs = null;
		Map<String,Object> params = null;
		if(StringUtils.isNotEmpty(resId)) {
			if(null != filterParam) {
				params = new HandleFilterParam(filterParam).getParams();
			}
			objs = queryDatas(resId, params);
		} else {
			throw new DaoException("SQL语句为空--["+resId+"]值为空");
		}
		params = null;
		return objs;
	}
	
	
	/**
	 * 查询数据(分页)
	 * @param resId
	 * @param start
	 * @param rows
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,int start,int rows) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			String sql = sqlMap.getSQL(resId);
			if(StringUtils.isNotEmpty(sql)) {
				objs = queryObjSql(sql,null,start,rows);
			} else {
				throw new DaoException("SQL语句为空--["+resId+"]值为空");
			}
			sql = null;
		}
		return objs;
	}
	
	
	/**
	 * 查询数据(分页)
	 * @param resId
	 * @param params
	 * @param start
	 * @param rows
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,Map<String,Object> params,int start,int rows) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			String sql = sqlMap.getSQL(resId);
			if(StringUtils.isNotEmpty(sql)) {
				objs = queryObjSql(sql, params,start,rows);
			} else {
				throw new DaoException("SQL语句为空--["+resId+"]值为空");
			}
			sql = null;
		}
		params = null;
		return objs;
	}
	
	/**
	 * 查询数据
	 * @param resId 资源ID
	 * @param params 参数
	 * @param clazz 要转换的类
	 * @return 返回指定<code>class</code>实体对象列表
	 */
	public <E> List<E> queryDatas(String resId, Map<String, Object> params, Class<?> clazz) {
		List<E> lists = null;
		if(StringUtils.isEmpty(resId) && null != clazz) {
			throw new NullArgumentException();
		}
		String sql = sqlMap.getSQL(resId);
		if(StringUtils.isNotEmpty(sql)) {
			lists = super.querySqlToBean(sql, params, clazz);
		}
		return lists;
	}
	
	
	/**
	 * 查询数据(分页)
	 * @param resId
	 * @param filterParam
	 * @param start
	 * @param rows
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,FilterParam filterParam,int start,int rows) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			Map<String,Object> params = null;
			if(null != filterParam) {
				params = new HandleFilterParam(filterParam).getParams();
			}
			filterParam = null;
			objs = queryDatas(resId, params,start,rows);
		}
		
		return objs;
	}
	
	
	/**
	 * 查询数据(分页)
	 * @param resId
	 * @param params
	 * @param filterParam
	 * @param start
	 * @param rows
	 * @return List<Object> 对象集合
	 * @throws DaoException
	 */
	public List<Object> queryDatas(String resId,Map<String,Object> params,FilterParam filterParam,int start,int rows) throws DaoException {
		List<Object> objs = null;
		if(StringUtils.isNotEmpty(resId)) {
			Map<String,Object> filterParamMap = null;
			if(null != filterParam) {
				filterParamMap = new HandleFilterParam(filterParam).getParams();
			}
			if(null != params && null != filterParamMap) {
				params.putAll(filterParamMap);
			} else if(null == params && null != filterParamMap){
				params = filterParamMap;
			}
			filterParam = null;
			objs = queryDatas(resId, params,start,rows);
		}
		return objs;
	}
	
	
	@Override
	public Long count(String resId) throws DaoException{
		long total = 0;
		if(StringUtils.isNotEmpty(resId)) {
			//先判断是否有用户自定义的统计语句
			String countSql = getCountSql(resId);
			if(StringUtils.isNotEmpty(countSql)) {
				total = super.exeCountSql(countSql);
			} else {
				String sql = sqlMap.getSQL(resId);
				if(StringUtils.isNotEmpty(sql)) {
					total = super.countSql(sql);
				} else {
					throw new DaoException("SQL语句为空--["+resId+"]值为空");
				}
			}
		}
		return total;
	}
	
	
	@Override
	public Long count(String resId,Map<String,Object> params) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(resId)) {
			String countSql = getCountSql(resId);
			if(StringUtils.isNotEmpty(countSql)) {
				total = super.exeCountSql(countSql, params);
			} else {
				String sql = sqlMap.getSQL(resId);
				if(StringUtils.isNotEmpty(sql)) {
					total = super.countSql(sql, params);
				} else {
					throw new DaoException("SQL语句为空--["+resId+"]值为空");
				}
			}
		}
		return total;
	}

	
	/**
	 * 统计
	 * @param resId
	 * @param filterParam
	 * @return Long
	 * @throws DaoException
	 */
	public Long count(String resId,FilterParam filterParam) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(resId)) {
			Map<String,Object> params = null;
			if(null != filterParam) {
				params = new HandleFilterParam(filterParam).getParams();
			}
			filterParam = null;
			total = count(resId, params);
		}
		return total;
	}
	
	
	/**
	 * 统计
	 * @param resId
	 * @param params
	 * @param filterParam
	 * @return Long
	 * @throws DaoException
	 */
	public Long count(String resId,Map<String,Object> params,FilterParam filterParam) throws DaoException {
		long total = 0;
		if(StringUtils.isNotEmpty(resId)) {
			Map<String,Object> filterParamMap = null;
			if(null != filterParam) {
				filterParamMap = new HandleFilterParam(filterParam).getParams();
			}
			if(null != params && null != filterParamMap) {
				params.putAll(filterParamMap);
			} else if(null == params && null != filterParamMap){
				params = filterParamMap;
			}
			filterParam = null;
			total = count(resId, params);
		}
		return total;
	}
	
	
	/**
	 * 执行update,delete等的sql
	 * @param resId
	 * @param params
	 * @return Boolean
	 * @throws DaoException
	 */
	public boolean execute(String resId,Map<String,Object> params) throws DaoException {
		int result = -1;
		if(StringUtils.isNotEmpty(resId)) {
			String sql = sqlMap.getSQL(resId);
			if(sql.indexOf(";")>-1) {
				result = executeSql(Arrays.asList(sql.split(";")), params)?1:-1;
			} else {
				if(StringUtils.isNotEmpty(sql)) {
					result = executeSql(sql, params);
				}
			}
			sql = null;
		}
		params = null;
		return (result>0?true:false);
	}
	
	
	/**
	 * 根据resId获取用户自定义的统计SQL语句；
	 * 用户自定义的统计语句的名称为resId下划线加count；
	 * 如：resId名称为：user_list；则对应的统计语句的名称为：user_list_count
	 * @param resId 资源ID
	 * @return 返回自定义的统计SQL语句
	 */
	private String getCountSql(String resId) {
		String countResId = resId+"_count";
		return sqlMap.getSQL(countResId);
	}
}
