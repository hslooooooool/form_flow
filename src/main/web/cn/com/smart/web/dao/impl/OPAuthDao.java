package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.dao.IOPAuthDao;

/**
 * 操作权限Dao
 * @author lmq
 * @date 2015年8月13日
 * @since 1.0
 */
@Repository("opAuthDao")
public class OPAuthDao extends BaseDaoImpl<TNOPAuth> implements IOPAuthDao {

	@Autowired
	private ResourceDao resDao;
	@Autowired
	private RoleResourceDao roleResDao;
	
	private SqlMapping sqlMap;
	private Map<String,Object> params;
	
	public OPAuthDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public List<TNOPAuth> queryAll() throws DaoException {
		return queryByField(null, " createTime asc");
	}
	
	/**
	 * 获取所有数据(按钮)
	 * @param start
	 * @param rows
	 * @return
	 */
	public List<Object> queryAuthAll(int start,int rows) throws DaoException {
		List<Object> lists = null;
		String sql = sqlMap.getSQL("opauth_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			lists = queryObjSql(sql, null, start, rows);
		} else {
			throw new DaoException("SQL语句为空--[opauth_mgr_list]值为空");
		}
		return lists;
	}
	
	/**
	 * 统计
	 * @return
	 */
	public long countAuthAll() throws DaoException {
		long count = 0;
		String sql = sqlMap.getSQL("opauth_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			count = countSql(sql, null);
		} else {
			throw new DaoException("SQL语句为空--[opauth_mgr_list]值为空");
		}
		return count;
	}

	
	@Override
	public List<TNOPAuth> queryAuths(String[] values) throws DaoException {
		List<TNOPAuth> lists = null;
		if(null != values && values.length>0) {
			String sql = sqlMap.getSQL("opauths_by_value");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("values", values);
				lists = queryHql(sql, params);
			} else {
				throw new DaoException("SQL语句为空--[opauths_by_value]值为空");
			}
		}
		return lists;
	}

	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] ids = id.toString().split(",");
			String delSql = sqlMap.getSQL("del_op_auth");
			if(StringUtils.isNotEmpty(delSql)) {
				params = new HashMap<String, Object>(1);
				params.put("ids", ids);
				List<TNOPAuth> lists = find(ids);
				if(null != lists && lists.size()>0) {
					if(executeSql(delSql, params)>0) {
						//删除与资源关联的数据
						String[] valueArray = new String[lists.size()];
						for (int i=0;i<lists.size();i++) {
							valueArray[i] = lists.get(i).getValue();
						}
						lists = null;
						resDao.deleteAuth(valueArray);
						//删除与角色资源关联的数据
						roleResDao.deleteAuth(valueArray);
						valueArray = null;
						is = true;
					}
				}
			} else {
				throw new DaoException("SQL语句为空--[del_op_auth]值为空");
			}
		}
		return is;
	}


	@Override
	public boolean update(TNOPAuth o) throws DaoException {
		boolean is = false;
		TNOPAuth oldAuth = find(o.getId());
		if(null != oldAuth) {
			String srcValue = oldAuth.getValue();
			String desValue = o.getValue();
			oldAuth.setName(o.getName());
			oldAuth.setSortOrder(o.getSortOrder());
			oldAuth.setValue(o.getValue());
			o = null;
			if(super.update(oldAuth)) {
				is = true;
				resDao.updateAuth(srcValue, desValue);
				roleResDao.updateAuth(srcValue, desValue);
			}
		}
		return is;
	}
}
