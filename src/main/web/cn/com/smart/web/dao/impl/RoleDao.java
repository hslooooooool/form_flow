package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.constant.IConstant;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNRole;

/**
 * 角色DAO
 * @author lmq
 *
 */
@Repository("roleDao")
public class RoleDao extends BaseDaoImpl<TNRole>{
	
	private SqlMapping sqlMap;
	private Map<String,Object> params;
	
	public RoleDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	/**
	 * 获取管理员角色
	 * @return
	 */
	public TNRole adminRole() throws DaoException {
		log.info("获取管理员角色");
		TNRole role = null;
		String hql = "from "+TNRole.class.getName()+" t where t.flag='"+IConstant.ROLE_SUPER_ADMIN+"'";
		List<TNRole> lists = queryHql(hql);
		//管理员角色只能有一个
		if(null != lists && lists.size()>0 && lists.size()==1) {
			role = lists.get(0);
		}
		return role;
	}

	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] ids = id.toString().split(",");
			if(!isContainsSuperAdminRole(ids)) {
				String sqls = sqlMap.getSQL("del_role");
				if(StringUtils.isNotEmpty(sqls)) {
					params = new HashMap<String, Object>(1);
					params.put("roleIds", ids);
					String[] sqlDelArray = sqls.split(";");
					for (int i = 0; i < sqlDelArray.length; i++) {
						executeSql(sqlDelArray[i],params);
					}
					is = true;
				}
			} else {
				log.error("删除的角色中含有超级管理员角色，超级管理员角色不能删除！");
			}
		}
		return is;
	}
	
	
	/**
	 * 判断角色ID中是否含有超级管理员角色
	 * @param ids
	 * @return
	 */
	public boolean isContainsSuperAdminRole(String[] ids) throws DaoException  {
		boolean is = false;
		if(null != ids && ids.length>0) {
			List<TNRole> roles = find(ids);
			if(null != roles && roles.size()>0) {
				for (TNRole role : roles) {
					if(IConstant.ROLE_SUPER_ADMIN.equals(role.getFlag())) {
						is = true;
						break;
					}
				}
			}
		}
		return is;
	}
	
	
}
