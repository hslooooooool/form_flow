package cn.com.smart.web.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNSubSystem;

@Repository
public class SubSystemDao extends BaseDaoImpl<TNSubSystem> {

	private SqlMapping sqlMap;
	
	public SubSystemDao() {
		sqlMap = SQLResUtil.getOpSqlMap();
	}
	
	/**
	 * 获取子系统信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TNSubSystem> getSubSystems() {
		List<TNSubSystem> list = null;
		String sql = sqlMap.getSQL("get_sub_system");
		if(StringUtils.isEmpty(sql)) {
			return list;
		}
		try {
			SQLQuery query = (SQLQuery) super.getQuery(sql, true);
			query.addEntity(TNSubSystem.class);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
