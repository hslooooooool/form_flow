package cn.com.smart.web.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNRolePosition;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Repository("rolePositionDao")
public class RolePositionDao extends BaseDaoImpl<TNRolePosition>{
	
	private SqlMapping sqlMap;
	
	public RolePositionDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}

	@Override
	public boolean delete(Map<String, Object> param) throws DaoException {
		boolean is = false;
		if(null != param && param.size()>0) {
			String flag = StringUtils.handleNull(param.get("flag"));
			String delSql = null;
			if(StringUtils.isEmpty(flag)) {
				delSql = sqlMap.getSQL("del_role_position");
			} else {
				param.remove("flag");
				if("p".equals(flag)) {
					delSql = sqlMap.getSQL("del_position_role");
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
	 * 检测岗位是否已经添加到角色里面
	 * @param roleId
	 * @param positionIds
	 * @return
	 */
	public boolean isPositionInRoleExist(String roleId,String[] positionIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(roleId) && null != positionIds && positionIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_position_in_role_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("roleId", roleId);
				param.put("positionIds", positionIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			positionIds = null;
		}
		return is;
	}
	
	/**
	 * 检测角色是否已经添加到岗位里面
	 * @param positionId
	 * @param roleIds
	 * @return
	 */
	public boolean isRoleInPositionExist(String positionId,String[] roleIds) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(positionId) && null != roleIds && roleIds.length>0) {
			String sql = SQLResUtil.getBaseSqlMap().getSQL("check_role_in_position_exits");
			if(StringUtils.isNotEmpty(sql)) {
				Map<String,Object> param = new HashMap<String, Object>(2);
				param.put("positionId", positionId);
				param.put("roleIds", roleIds);
				is = exeCountSql(sql, param)>0?true:false;
				param = null;
			}
			roleIds = null;
		}
		return is;
	}
	
	
}
