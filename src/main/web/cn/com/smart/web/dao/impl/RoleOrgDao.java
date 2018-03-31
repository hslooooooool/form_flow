package cn.com.smart.web.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNRoleOrg;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Repository("roleOrgDao")
public class RoleOrgDao extends BaseDaoImpl<TNRoleOrg>{

    private SqlMapping sqlMap;
	
	public RoleOrgDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public boolean delete(Map<String, Object> param) throws DaoException {
		boolean is = false;
		if(null == param || param.size() == 0) {
		    return is;
		}
 		String flag = StringUtils.handleNull(param.get("flag"));
		String delSql = null;
		if(StringUtils.isEmpty(flag)) {
		    //删除角色中的组织机构
			delSql = sqlMap.getSQL("del_role_org");
		} else {
		    //删除组织机构中的角色
			param.remove("flag");
			if("o".equals(flag)) {
				delSql = sqlMap.getSQL("del_org_role");
			}
		}
		if(StringUtils.isNotEmpty(delSql)) {
			//判断处理是否有逗号分割的多条数据组合
			for (String key : param.keySet()) {
				if(!param.get(key).getClass().isArray()) {
					String value = StringUtils.handleNull(param.get(key));
					if(StringUtils.isNotEmpty(value) && value.indexOf(",")>-1) {
						String[] values = value.split(",");
						param.put(key, values);
					}
				}
			}
			is = executeSql(delSql, param)>0?true:false;
		}
		param = null;
		return is;
	}
	
	
	/**
	 * 检测组织机构是否已经添加到角色里面
	 * @param roleId
	 * @param orgIds
	 * @return
	 */
	public boolean isOrgInRoleExist(String roleId,String[] orgIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(roleId) && null != orgIds && orgIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_org_in_role_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("roleId", roleId);
				param.put("orgIds", orgIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			orgIds = null;
		}
		return is;
	}
	
	/**
	 * 检测角色是否已经添加到组织机构里面
	 * @param orgId
	 * @param roleIds
	 * @return
	 */
	public boolean isRoleInOrgExist(String orgId,String[] roleIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(orgId) && null != roleIds && roleIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_role_in_org_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("orgId", orgId);
				param.put("roleIds", roleIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			roleIds = null;
		}
		return is;
	}
	
}
