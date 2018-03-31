package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.dao.IDictDao;

/**
 * 数据字典DAO
 * @author lmq
 * @date 2015年8月13日
 * @since 1.0
 */
@Repository("dictDao")
public class DictDao extends BaseDaoImpl<TNDict> implements IDictDao {
	
	private static SqlMapping sqlMap;

	public DictDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	@Override
	public boolean delete(Serializable id) throws DaoException  {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			List<TNDict> dicts = findAll();
			String[] ids = id.toString().split(",");
			List<String> idList = new ArrayList<String>();
			for(String idTmp : ids) {
				if(null != dicts && dicts.size()>0) {
					TreeHelper<TNDict> treeHelper = new TreeHelper<TNDict>();
					List<TNDict> listTmps = null;
					//String delIds = "'"+idTmp+"',";
					idList.add(idTmp);
					TNDict tmp = new TNDict();
					tmp.setId(idTmp);
					try {
						listTmps = treeHelper.outPutTree(dicts, tmp,false);
						if(null != listTmps && listTmps.size()>0) {
							for (int i = 0; i < listTmps.size(); i++) {
								if(i != (listTmps.size()-1)) {
									idList.add(listTmps.get(i).getId());
									//delIds += "'"+listTmps.get(i).getId()+"',";
								} else {
									idList.add(listTmps.get(i).getId());
									//delIds += "'"+listTmps.get(i).getId()+"'";
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(!idList.isEmpty()) {
						String delSql = "delete from t_n_dict where id in (:delIds)";
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("delIds", idList.toArray());
						if(executeSql(delSql,param)>0) {
							is = true;
						} else {
							is = false;
						}
					}
				}
			}
		}
		return is;
	}
	
	
	@Override
	public List<Object> getItems(String busiValue,String name) throws DaoException  {
		List<Object> lists = null;
		if(StringUtils.isNotEmpty(busiValue)) {
			String sql = sqlMap.getSQL("dict_item");
			if(StringUtils.isNotEmpty(sql)){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("busiValue", busiValue);
				if(StringUtils.isNotEmpty(name))
				  params.put("name", name);
				lists = queryObjSql(sql, params);
			}
		}
		return (lists != null && lists.size()>0)?lists:null;
	}
	
	
	@Override
	public List<TNDict> getItems(String busiValue) throws DaoException  {
		List<TNDict> lists = null;
		if(StringUtils.isNotEmpty(busiValue)) {
			String sql = "from "+TNDict.class.getName()+" d where d.parentId=(select id from "+TNDict.class.getName()+" where busiValue=:busiValue) and d.state='1' order by d.sortOrder asc";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("busiValue", busiValue);
			lists = queryHql(sql, params);
		}
		return (lists != null && lists.size()>0)?lists:null;
	}
	
	
	@Override
	public List<Object> getItemById(String id,String name) throws DaoException  {
		List<Object> lists = null;
		if(StringUtils.isNotEmpty(id)) {
			String sql = sqlMap.getSQL("dict_item_by_id");
			if(StringUtils.isNotEmpty(sql)){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", id);
				if(StringUtils.isNotEmpty(name))
				  params.put("name", name);
				lists = queryObjSql(sql, params);
			}
		}
		return (lists != null && lists.size()>0)?lists:null;
	}
	
	
	@Override
	public List<Object> queryObjAll() throws DaoException  {
		List<Object> objs = null;
		String sql = sqlMap.getSQL("dict_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			objs = queryObjSql(sql);
		} else {
			throw new DaoException("[dict_mgr_list]值为空");
		}
		return objs;
	}
	
	/**
	 * 获取所有有效的数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<TNDict> findValidAll() {
		List<TNDict> list = null;
		try {
			list = getQuery(" from "+TNDict.class.getName()+" where state='"+YesNoType.YES.getStrValue()+"'",false).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
