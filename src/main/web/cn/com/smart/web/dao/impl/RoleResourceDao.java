package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.bean.entity.TNRoleResource;
import cn.com.smart.web.dao.IRoleResourceDao;

import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Repository("roleResourceDao")
public class RoleResourceDao extends BaseDaoImpl<TNRoleResource> implements IRoleResourceDao {
	
	
	@Autowired
	private OPAuthDao opAuthDao;
	
	private SqlMapping sqlMap;
	private Map<String,Object> params;
	
	public RoleResourceDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	/**
	 * 保存角色资源数据
	 * @param roleId
	 * @param resources
	 * @return
	 */
	public boolean save(String roleId,List<TNResource> resources) throws DaoException {
		List<Serializable> ids = null;
		if(StringUtils.isNotEmpty(roleId) && null != resources && resources.size()>0) {
			List<TNRoleResource> roleResList = new ArrayList<TNRoleResource>();
	  		TNRoleResource roleRes = null;
			for (TNResource res : resources) {
				roleRes = new TNRoleResource();
				roleRes.setRoleId(roleId);
				roleRes.setResourceId(res.getId());
				roleRes.setOpAuths(res.authsToString());
				roleResList.add(roleRes);
			}
			roleRes = null;
			resources = null;
			ids = super.save(roleResList);
			roleResList = null;
		}
		return (null != ids && ids.size()>0)?true:false;
	}
	
	
	/**
	 * 更新角色资源数据
	 * @param roleId
	 * @param resources
	 * @return
	 */
	public boolean update(String roleId,List<TNResource> resources) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(roleId) && null != resources && resources.size()>0) {
			String hql = "update "+TNRoleResource.class.getName()+" t set t.opAuths=:opAuths where t.roleId=:roleId and t.resourceId=:resourceId";
			List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>(resources.size());
			for (TNResource res : resources) {
				params = new HashMap<String, Object>(3);
				params.put("opAuths", res.authsToString());
				params.put("roleId", roleId);
				params.put("resourceId", res.getId());
				listMaps.add(params);
			}
			params = null;
			resources = null;
			is = executeHql(hql, listMaps)>0?true:false;
			listMaps = null;
		}
		return is;
	}

	
	@Override
	public Map<String,List<TNOPAuth>> queryByRole(String roleId) throws DaoException {
		Map<String,List<TNOPAuth>> resAuthMaps = null;
		if(StringUtils.isNotEmpty(roleId)) {
			params = new HashMap<String, Object>(1);
			params.put("roleId", roleId);
			List<TNRoleResource> roleResList = queryByField(params);
			if(null != roleResList && roleResList.size()>0) {
				resAuthMaps = new HashMap<String, List<TNOPAuth>>();
				String[] authValueArray = null;
				List<TNOPAuth> auths = null;
				for (TNRoleResource roleRes : roleResList) {
					if(StringUtils.isNotEmpty(roleRes.getOpAuths())) {
						authValueArray = roleRes.getOpAuths().split(",");
						auths = opAuthDao.queryAuths(authValueArray);
					}//if
					resAuthMaps.put(roleRes.getResourceId(), auths);
				}//for
				authValueArray = null;
				auths = null;
			}
		}
		return resAuthMaps;
	}
	
	
	
	/**
	 * 删除权限
	 * @param values 操作权限值
	 * @return
	 */
	public boolean deleteAuth(String[] values) throws DaoException {
		boolean is = false;
		if(null != values && values.length>0) {
			List<TNRoleResource> lists = findAll();
			List<TNRoleResource> updateList = new ArrayList<TNRoleResource>();
			String regex = null;
			boolean isDel = false;
			if(null != lists && lists.size()>0) {
				for (TNRoleResource roleRes : lists) {
					isDel = false;
					if(StringUtils.isNotEmpty(roleRes.getOpAuths())) {
						for (int i = 0; i < values.length; i++) {
							if(StringUtils.isNotEmpty(roleRes.getOpAuths()) 
									&& ArrayUtils.isArrayContains(roleRes.getOpAuths(), values[i], ",")) {
								regex = ","+values[i]+",|"+values[i]+",|,"+values[i];
								roleRes.setOpAuths(roleRes.getOpAuths().replaceAll(regex, ""));
								isDel = true;
							}
						}//for
						if(isDel) {
							if(StringUtils.isNotEmpty(roleRes.getOpAuths())) {
								roleRes.setOpAuths(roleRes.getOpAuths().replaceAll(",,", ""));
							   if(roleRes.getOpAuths().length()>0 && roleRes.getOpAuths().lastIndexOf(",")>-1)
								   roleRes.setOpAuths(roleRes.getOpAuths().substring(0,roleRes.getOpAuths().length()-1));
							}
							updateList.add(roleRes);
						}
					}//if
				}//for
		    }
			if(updateList.size()>0) {
				is = update(updateList);
			}
			updateList = null;
		}
		return is;
	}
	
	
	/**
	 * 更新操作权限
	 * @param srcValue
	 * @param desValue
	 * @return
	 */
	public boolean updateAuth(String srcValue,String desValue) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(srcValue) && StringUtils.isNotEmpty(desValue)) {
			List<TNRoleResource> lists = findAll();
			List<TNRoleResource> updateList = new ArrayList<TNRoleResource>();
			if(null != lists && lists.size()>0) {
				for (TNRoleResource roleRes : lists) {
					if(StringUtils.isNotEmpty(roleRes.getOpAuths())) {
						if(ArrayUtils.isArrayContains(roleRes.getOpAuths(), srcValue, ",")) {
							roleRes.setOpAuths(roleRes.getOpAuths().replaceAll(srcValue+",", desValue+","));
						 	updateList.add(roleRes);
						}
					}//if
				}//for
		    }
			if(updateList.size()>0) {
				is = update(updateList);
			}
			updateList = null;
		}
		return is;
	}
	
	
	/**
	 * 按角色删除
	 * @param roleId
	 * @return
	 */
	public boolean deleteByRole(String roleId) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(roleId)) {
			params = new HashMap<String, Object>(1);
			params.put("roleId", roleId);
			String delSql = sqlMap.getSQL("del_roleres_by_role");
			if(StringUtils.isNotEmpty(delSql)) {
				executeSql(delSql, params);
				is = true;
			}
			params = null;
		}
		return is;
	}
	
	
	@Override
	public List<TNRoleResource> queryByUriRoles(String uri,List<String> roleIds) throws DaoException {
		List<TNRoleResource> roleResList = null;
		if(StringUtils.isNotEmpty(uri) && null != roleIds && roleIds.size()>0) {
			String hql = "from "+TNRoleResource.class.getName()+" t where t.resourceId=(select id from "+TNResource.class.getName()+" where uri=:uri) and t.roleId in (:roleIds)";
			params = new HashMap<String, Object>(2);
			params.put("uri", uri);
			params.put("roleIds", roleIds.toArray());
			roleResList = queryHql(hql, params);
			params = null;
			roleIds = null;
		}
		return roleResList;
	}
}
