package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.dao.IOrgDao;

import com.mixsmart.utils.StringUtils;

/**
 * 组织机构DAO
 * @author lmq
 *
 */
@Repository("orgDao")
public class OrgDao extends BaseDaoImpl<TNOrg> implements IOrgDao {
	
	private SqlMapping sqlMap;
	
	public OrgDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public TNOrg find(Serializable id) throws DaoException {
		TNOrg org = null;
		if(null == id || StringUtils.isEmpty(id.toString())) {
			return org;
		}
		String sql = sqlMap.getSQL("get_org_by_id");
		if(StringUtils.isEmpty(sql)) {
			return org;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
			sqlQuery.addEntity(TNOrg.class);
			org = (TNOrg)sqlQuery.uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			params = null;
		}
		return org;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TNOrg> findAll() throws DaoException {
		List<TNOrg> orgs = null;
		String sql = sqlMap.getSQL("get_org_all");
		if(StringUtils.isEmpty(sql)) {
			return orgs;
		}
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, true);
			sqlQuery.addEntity(TNOrg.class);
			orgs = sqlQuery.list();
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return orgs;
	}
	
	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			List<TNOrg> dicts = findAll();
			String[] ids = id.toString().split(",");
			for(String idTmp : ids) {
				if(null != dicts && dicts.size()>0) {
					TreeHelper<TNOrg> treeHelper = new TreeHelper<TNOrg>();
					List<TNOrg> listTmps = null;
					TNOrg tmp = new TNOrg();
					tmp.setId(idTmp);
					try {
						listTmps = treeHelper.outPutTree(dicts, tmp,false);
						if(null != listTmps && listTmps.size()>0) {
							String[] delIdArray = new String[listTmps.size()];
							for (int i = 0; i < listTmps.size(); i++) {
								delIdArray[i] = listTmps.get(i).getId();
							}
							ids = ArrayUtils.addAll(ids, delIdArray);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						listTmps = null;
						treeHelper = null;
						tmp = null;
					}
					if(ids.length>0) {
						String delSql = sqlMap.getSQL("del_org");
						if(StringUtils.isNotEmpty(delSql)) {
							Map<String, Object> param = new HashMap<String, Object>(1);
							param.put("ids", ids);
							if(executeSql(delSql,param)>0) {
								is = true;
							} else {
								is = false;
							}
							param = null;
					     }
					}
				}
			}
			ids = null;
		}
		return is;
	}
	

	/**
	 * 
	 * @param pId
	 * @param type
	 * @return
	 */
	public int getSortOrder(String pId, String type) throws DaoException {
		List<Object> list = null;
		int sortOrder = 1;
		String sql = sqlMap.getSQL("org_sort_order");
		if(StringUtils.isNotEmpty(sql)) {
			Map<String,Object> param = new HashMap<String, Object>(2);
			param.put("parentId", pId);
			param.put("type", type);
			list = queryObjSql(sql,param);
			if (list.size() > 0&& null!=list) {
				if(StringUtils.isNotEmpty(StringUtils.handleNull(list.get(0)))) {
					sortOrder = Integer.parseInt(list.get(0).toString())+1;
				} else {
					sortOrder = 1;
				}
			}
			param = null;
			list = null;
	    }
		return sortOrder;
	}
}
