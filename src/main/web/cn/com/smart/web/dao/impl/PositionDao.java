package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNPosition;

import com.mixsmart.utils.StringUtils;

/**
 * 岗位DAO
 * @author lmq
 *
 */
@Repository("positionDao")
public class PositionDao extends BaseDaoImpl<TNPosition>{
	
	private SqlMapping sqlMap;
	
	public PositionDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	/**
	 * 通过组织机构ID查询岗位信息
	 * @param orgIds
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<TNPosition> queryByOrgIds(String[] orgIds) throws DaoException {
		List<TNPosition> lists = null;
		if(null != orgIds && orgIds.length>0) {
			try {
				String sql = sqlMap.getSQL("get_position_by_orgid");
				if(StringUtils.isEmpty(sql)) {
					return lists;
				}
				Map<String, Object> params = new HashMap<String, Object>(1);
				params.put("orgIds", orgIds);
				try {
					SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
					sqlQuery.addEntity(TNPosition.class);
					lists = sqlQuery.list();
				} catch (Exception e) {
					throw new DaoException(e);
				} finally {
					params = null;
				}
			} catch (DaoException e) {
				throw e;
			}
		}
		return lists;
	}
	
	
	/**
	 * 判断职位是否该组织结构（部门下的）
	 * @param positionId 职位ID
	 * @param orgId 部门ID（组织机构）
	 * @return 该用户是该组织机构的，则返回:true;否则返回:false
	 */
	public boolean isExistPositionInOrg(String positionId,String orgId) {
		boolean is = false;
		if(StringUtils.isEmpty(positionId) || StringUtils.isEmpty(orgId)) {
			return is;
		}
		String sql = sqlMap.getSQL("is_position_in_org");
		if(StringUtils.isNotEmpty(sql)) {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("id", positionId);
			param.put("orgId", orgId);
			if(countSql(sql, param)>0) {
				is = true;
			}
		}
		return is;
	}

	@Override
	public TNPosition find(Serializable id) throws DaoException {
		TNPosition position = null;
		if(null == id || StringUtils.isEmpty(id.toString())) {
			return position;
		}
		String sql = sqlMap.getSQL("get_position_by_id");
		if(StringUtils.isEmpty(sql)) {
			return position;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
			sqlQuery.addEntity(TNPosition.class);
			position = (TNPosition)sqlQuery.uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			params = null;
		}
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TNPosition> findAll() throws DaoException {
		List<TNPosition> positions = null;
		String sql = sqlMap.getSQL("get_position_all");
		if(StringUtils.isEmpty(sql)) {
			return positions;
		}
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, true);
			sqlQuery.addEntity(TNPosition.class);
			positions = sqlQuery.list();
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return positions;
	}

}
