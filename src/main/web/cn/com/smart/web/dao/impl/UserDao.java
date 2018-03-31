package cn.com.smart.web.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.filter.HandleFilterParam;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.res.sqlmap.SqlMapping;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.filter.bean.UserSearchParam;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Repository("userDao")
public class UserDao extends BaseDaoImpl<TNUser> {
	
	private SqlMapping sqlMap;
	private Map<String, Object> params;
	
	public UserDao() {
		sqlMap = SQLResUtil.getBaseSqlMap();
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @return
	 */
	public TNUser queryUser(String username) throws DaoException {
		TNUser user = null;
		String sql = sqlMap.getSQL("get_user_by_username");
		if(StringUtils.isNotEmpty(sql) && StringUtils.isNotEmpty(username)) {
			params = new HashMap<String, Object>();
			params.put("username", username);
			try {
				SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
				sqlQuery.addEntity(TNUser.class);
				user = (TNUser)sqlQuery.uniqueResult();
			} catch (Exception e) {
				throw new DaoException(e);
			} finally {
				params = null;
			}
			if(null == user) {
			   log.info("用户名错误--输入用户名["+username+"]--");
			}
		} else {
			log.info("HQL为空--");
		}
		return user;
	}
	
	/**
	 * 登录用户
	 * @param username
	 * @param password
	 * @return
	 * @throws DaoException
	 */
	public TNUser queryLogin(String username, String password) throws DaoException {
		TNUser user = null;
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return user;
		}
		String sql = sqlMap.getSQL("user_login_sql");
		if(StringUtils.isEmpty(sql)) {
			return user;
		}
		params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", password);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
			sqlQuery.addEntity(TNUser.class);
			user = (TNUser)sqlQuery.uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			params = null;
		}
		return user;
	}
	
	
	/**
	 * 用户登录
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TNUser> queryUsers(String username) throws DaoException {
		List<TNUser> users = null;
		String sql = sqlMap.getSQL("get_user_by_username");
		if(StringUtils.isNotEmpty(sql) && StringUtils.isNotEmpty(username)) {
			params = new HashMap<String, Object>();
			params.put("username", username);
			try {
				SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
				sqlQuery.addEntity(TNUser.class);
				users = sqlQuery.list();
			} catch (Exception e) {
				throw new DaoException(e);
			} finally {
				params = null;
			}
			if(null == users || users.size()<1) {
			   log.info("用户名错误--输入用户名["+username+"]--");
			}
		} else {
			log.info("HQL为空--");
		}
		return users;
	}
	
	/**
	 * 查询用户列表
	 * @param searchParam  搜索参数
	 * @param start 
	 * @param rows
	 * @return
	 */
	public List<Object> queryObjPage(UserSearchParam searchParam,int start,int rows) throws DaoException {
		List<Object> lists = null;
		params = new HandleFilterParam(searchParam).getParams();
		String sql = sqlMap.getSQL("user_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			lists = queryObjSql(sql, params, start, rows);
		}
		searchParam = null;
		return lists;
	}
	
	/**
	 * 统计用户列表--
	 * @param searchParam 搜索参数
	 * @param start
	 * @param rows
	 * @return
	 */
	public long queryObjCount(UserSearchParam searchParam) throws DaoException {
		long total = 0;
		params = new HandleFilterParam(searchParam).getParams();
		String sql = sqlMap.getSQL("user_mgr_list");
		if(StringUtils.isNotEmpty(sql)) {
			total = countSql(sql, params);
		}
		searchParam = null;
		return total;
	}
	

	/**
	 * 检测用户名是否存在
	 * @param username
	 * @return 如果用户已存在；返回：true；否则返回：false
	 */
	public boolean isExistUsername(String  username) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(username)) {
			String sql = sqlMap.getSQL("is_user_exist");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("username", username);
				long count = countSql(sql, params);
				is = count>0?true:false;
			}
			params = null;
		}
		return is;
	}
	
	
	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] ids = id.toString().split(",");
			String sqls = sqlMap.getSQL("del_user");
			if(StringUtils.isNotEmpty(sqls)) {
				String[] sqlArray = sqls.split(";");
				params = new HashMap<String, Object>(1);
				params.put("userIds", ids);
				int count = 0;
				for (int i = 0; i < sqlArray.length; i++) {
					count += executeSql(sqlArray[i], params);
				}
				is = count>0?true:false;
				params = null;
				sqlArray= null;
			}
			ids = null;
		}
		return is;
	}
	
	
	/**
	 * 修改用户密码
	 * @param userIdArray
	 * @param newPwd
	 * @return
	 */
	public boolean changePwd(String userId,String newPwd) throws DaoException {
		boolean is = false;
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(newPwd)) {
			String updateSql = sqlMap.getSQL("change_pwd");
			if(StringUtils.isNotEmpty(updateSql)) {
				params = new HashMap<String, Object>(2);
				params.put("userId", userId);
				params.put("newPwd", newPwd);
				is = executeSql(updateSql,params)>0 ? true:false;
				params = null;				
			}
		}
		return is;
	}
	
	/**
	 * 批量修改用户密码
	 * @param userIdArray
	 * @param newPwd
	 * @return
	 */
	public boolean batchChangePwd(String[] userIdArray,String newPwd) throws DaoException {
		boolean is = false;
		if(null != userIdArray && userIdArray.length>0 && StringUtils.isNotEmpty(newPwd)) {
			String updateSql = sqlMap.getSQL("batch_change_pwd");
			if(StringUtils.isNotEmpty(updateSql)) {
				params = new HashMap<String, Object>(2);
				params.put("userIds", userIdArray);
				params.put("newPwd", newPwd);
				is = executeSql(updateSql,params)>0 ? true:false;
				params = null;				
			}
		}
		userIdArray = null;
		return is;
	}
	
	/**
	 * 查询角色ID
	 * @param userId
	 * @param flag
	 * @return
	 */
	public List<String> queryMenuRoleIds(String userId) throws DaoException {
		List<String> roleIds = null;
		if(StringUtils.isNotEmpty(userId)) {
			String sql = sqlMap.getSQL("query_menuroleid_by_user");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("userId", userId);
				List<Object> lists = queryObjSql(sql, params);
				if(null != lists && lists.size()>0) {
					roleIds = new ArrayList<String>(lists.size());
					for (Object obj : lists) {
						roleIds.add(StringUtils.handleNull(obj));
					}
				}//if
				lists = null;
				params = null;
			}//if
		}//if
		return roleIds;
	}
	
	/**
	 * 查询角色ID
	 * @param userId
	 * @param flag
	 * @return
	 */
	public List<String> queryRoleIds(String userId) throws DaoException {
		List<String> roleIds = null;
		if(StringUtils.isNotEmpty(userId)) {
			String sql = sqlMap.getSQL("query_roleid_by_user");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("userId", userId);
				List<Object> lists = queryObjSql(sql, params);
				if(null != lists && lists.size()>0) {
					roleIds = new ArrayList<String>(lists.size());
					for (Object obj : lists) {
						roleIds.add(StringUtils.handleNull(obj));
					}
				}//if
				lists = null;
				params = null;
			}
		}//if
		return roleIds;
	}
	
	
	/**
	 * 查询组织机构ID
	 * @param userId
	 * @return
	 */
	public List<String> queryOrgIds(String userId) throws DaoException {
		List<String> orgIds = null;
		if(StringUtils.isNotEmpty(userId)) {
			String sql = sqlMap.getSQL("query_orgid_by_user");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("userId", userId);
				List<Object> lists = queryObjSql(sql, params);
				if(null != lists && lists.size()>0) {
					orgIds = new ArrayList<String>(lists.size());
					for (Object obj : lists) {
						orgIds.add(StringUtils.handleNull(obj));
					}
				}//if
				lists = null;
				params = null;
			}
		}//if
		return orgIds;
	}
	
	
	/**
	 * 通过组织组织机构ID查询用户信息
	 * @param orgIds
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<TNUser> queryByOrgIds(String[] orgIds) throws DaoException {
		List<TNUser> users = null;
		if(null != orgIds && orgIds.length>0) {
			String sql = sqlMap.getSQL("get_users_by_orgid");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>(1);
				params.put("orgIds", orgIds);
				try {
					SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
					sqlQuery.addEntity(TNUser.class);
					users = sqlQuery.list();
				} catch (Exception e) {
					throw new DaoException(e);
				} finally {
					params = null;
				}
			}
		}//if
		return users;
	}
	
	/**
	 * 判断用户是否该组织结构（部门下的）
	 * @param userId 用户ID
	 * @param orgId 部门ID（组织机构）
	 * @return 该用户是该组织机构的，则返回:true;否则返回:false
	 */
	public boolean isExistUserInOrg(String userId,String orgId) {
		boolean is = false;
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(orgId)) {
			String sql = sqlMap.getSQL("is_user_in_org");
			if(StringUtils.isNotEmpty(sql)) {
				params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("orgId", orgId);
				if(super.countSql(sql, params)>0) {
					is = true;
				}
				params = null;
			}
		}
		return is;
	}



	@Override
	public TNUser find(Serializable id) throws DaoException {
		TNUser user = null;
		if(null == id || StringUtils.isEmpty(id.toString())) {
			return user;
		}
		String sql = sqlMap.getSQL("get_user_by_id");
		if(StringUtils.isEmpty(sql)) {
			return user;
		}
		params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
			sqlQuery.addEntity(TNUser.class);
			user = (TNUser)sqlQuery.uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			params = null;
		}
		return user;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<TNUser> findAll() throws DaoException {
		List<TNUser> users = null;
		String sql = sqlMap.getSQL("get_user_all");
		if(StringUtils.isEmpty(sql)) {
			return users;
		}
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, true);
			sqlQuery.addEntity(TNUser.class);
			users = sqlQuery.list();
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return users;
	}
	
	/**
	 * 根据职位ID获取用户信息
	 * @param positionIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TNUser> findByPositionId(String[] positionIds) {
		List<TNUser> users = null;
		String sql = sqlMap.getSQL("get_user_by_positionid");
		if(StringUtils.isEmpty(sql)) {
			return users;
		}
		params = new HashMap<String, Object>();
		params.put("positionIds", positionIds);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, params, true);
			sqlQuery.addEntity(TNUser.class);
			users = sqlQuery.list();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			params = null;
		}
		return users;
	}
}
