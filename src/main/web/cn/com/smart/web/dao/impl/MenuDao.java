package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNMenu;
import cn.com.smart.web.dao.IMenuDao;

/**
 * 菜单DAO
 * @author lmq
 *
 */
@Repository("menuDao")
public class MenuDao extends BaseDaoImpl<TNMenu> implements IMenuDao {
	
	private SqlMapping sqlMap;
	private Map<String,Object> params;
	
	public MenuDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public List<Object> queryObjAll() throws DaoException {
		List<Object> objs = null;
		String sql = sqlMap.getSQL("menu_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			objs = queryObjSql(sql);
		} else {
			throw new DaoException("[menu_mgr_list]值为空");
		}
		return objs;
	}
	
	@Override
	public List<TNMenu> getValidAll() throws DaoException {
		List<TNMenu> menus = null;
		String hql = sqlMap.getSQL("valid_menu");
		if(StringUtils.isNotEmpty(hql)) {
			menus = queryHql(hql);
		} else {
			throw new DaoException("[valid_menu]值为空");
		}
		return (menus != null && menus.size()>0) ? menus : null;
	}
	
	
	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			List<TNMenu> lists = findAll();
			String[] ids = id.toString().split(",");
			for(String idTmp : ids) {
				if(null != lists && lists.size()>0) {
					TreeHelper<TNMenu> treeHelper = new TreeHelper<TNMenu>();
					List<TNMenu> listTmps = null;
					String delIds = idTmp+",";
					TNMenu tmp = new TNMenu();
					tmp.setId(idTmp);
					try {
						listTmps = treeHelper.outPutTree(lists, tmp,false);
						if(null != listTmps && listTmps.size()>0) {
							for (int i = 0; i < listTmps.size(); i++) {
								if(i != (listTmps.size()-1))
									delIds += listTmps.get(i).getId()+",";
								else
									delIds += listTmps.get(i).getId();
							}
						} else
							delIds = delIds.replace(",", "");
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						treeHelper = null;
						listTmps = null;
						tmp = null;
					}
					if(StringUtils.isNotEmpty(delIds)) {
						String sqls = sqlMap.getSQL("del_menu");
						if(StringUtils.isNotEmpty(sqls)) {
							String[] sqlDelArray = sqls.split(";");
							params = new HashMap<String, Object>(1);
							String[] delIdArray = delIds.split(",");
							params.put("menuIds", delIdArray);
							for (int i = 0; i < sqlDelArray.length; i++) {
								executeSql(sqlDelArray[i],params);
							}
							sqlDelArray = null;
							delIdArray = null;
							params = null;
							is = true;
						}
					}
				}
			}
		}
		return is;
	}

	
	@Override
	public List<TNMenu> queryMenus(List<String> roleIds) throws DaoException {
		List<TNMenu> menus = null;
		if(null != roleIds && roleIds.size()>0) {
			String hql = sqlMap.getSQL("menu_by_role");
			if(StringUtils.isNotEmpty(hql)) {
				params = new HashMap<String, Object>(1);
				params.put("roleIds", roleIds.toArray());
				menus = queryHql(hql, params);
				params = null;
			} else {
				throw new DaoException("[menu_by_role]值为空");
			}
		} 
		return menus;
	}
	
	
	@Override
	public List<Object> queryMenuIdByResourceId(String[] resourceIds) throws DaoException {
		List<Object> ids = null;
		if(null != resourceIds && resourceIds.length>0) {
			String sql = sqlMap.getSQL("query_menuid_by_resid");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("resourceIds", resourceIds);
				ids = queryObjSql(sql, params);
				params = null;
			} else {
				throw new DaoException("[menu_by_role]值为空");
			}
		}
		resourceIds = null;
		return ids;
	}
	
}
