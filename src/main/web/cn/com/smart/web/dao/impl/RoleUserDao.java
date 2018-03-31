package cn.com.smart.web.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNRoleUser;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Repository("roleUserDao")
public class RoleUserDao extends BaseDaoImpl<TNRoleUser>{
	
	private SqlMapping sqlMap;
	
	public RoleUserDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public boolean delete(Map<String, Object> param) throws DaoException {
		boolean is = false;
		if(null != param && param.size()>0) {
			String flag = StringUtils.handleNull(param.get("flag"));
			String delSql = null;
			if(StringUtils.isEmpty(flag)) {
				delSql = sqlMap.getSQL("del_role_user");
			} else {
				param.remove("flag");
				if("u".equals(flag)) {
					delSql = sqlMap.getSQL("del_user_role");
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
		}
		param = null;
		return is;
	}
	
	
	/**
	 * 检测用户是否已经添加到角色里面
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	public boolean isUserInRoleExist(String roleId,String[] userIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(roleId) && null != userIds && userIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_user_in_role_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("roleId", roleId);
				param.put("userIds", userIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			userIds = null;
		}
		return is;
	}
	
	/**
	 * 检测角色是否已经添加到用户里面
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	public boolean isRoleInUserExist(String userId,String[] roleIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(userId) && null != roleIds && roleIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_role_in_user_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("userId", userId);
				param.put("roleIds", roleIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			roleIds = null;
		}
		return is;
	}
}
