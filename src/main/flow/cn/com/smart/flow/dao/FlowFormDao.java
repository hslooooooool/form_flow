package cn.com.smart.flow.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.form.dao.FormDao;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.dao.impl.UserDao;

import com.mixsmart.utils.StringUtils;

/**
 * 流程表单数据关联表
 * @author lmq
 * @create 2015年6月10日
 * @version 1.0 
 * @since 
 *
 */
@Repository
public class FlowFormDao extends BaseDaoImpl<TFlowForm> {

	@Autowired
	private FormDao formDao;
	@Autowired
	private UserDao userDao;
	
	public String[] getFlowFormInfo(String orderId) {
		String[] infos = null;
		if(StringUtils.isNotEmpty(orderId)) {
			String sql = SQLResUtil.getOpSqlMap().getSQL("query_flow_form_info");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderId", orderId);
			try {
				List<Object> lists = queryObjSql(sql, param);
				if(null != lists && lists.size()>0) {
					infos = new String[2];
					Object[] objs = (Object[])lists.get(0);
					infos[0] = objs[0].toString();
					infos[1] = objs[1].toString();
				}
				lists = null;
			} catch (DaoException e) {
				e.printStackTrace();
			} finally {
				param = null;
			}
		}
		return infos;
	}
	
	
	/**
	 * 删除与流程实例相关联的数据  <br/>
	 * 1、找出与流程实例ID管理的表单 <br />
	 * 2、删除该表单中与流程实例相关联的数据 <br />
	 * 3、从管理表中删除关联数据
	 * @param orderIds
	 * @return
	 */
	public boolean deleteAssocData(String[] orderIds) {
		boolean is  = false;
		Map<String,Object> param = new HashMap<String, Object>(1);
		param.put("orderId", orderIds);
		List<TFlowForm> flowForms = null;
		try {
			flowForms = queryByField(param);
			is = deleteAssocByObj(flowForms);
		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			flowForms = null;
			param = null;
		}
		return is;
	}
	
	/**
	 * 删除数据
	 * @param flowForms
	 * @return
	 */
	public boolean deleteAssocByObj(List<TFlowForm> flowForms) {
		boolean is = false;
		Map<String,Object> param = null;
		try {
			if(null != flowForms && flowForms.size()>0) {
				String delSql = SQLResUtil.getOpSqlMap().getSQL("del_form_data");
				param = new HashMap<String, Object>();
				String sql = SQLResUtil.getOpSqlMap().getSQL("get_table_name");
				for (TFlowForm flowForm : flowForms) {
					param.put("formId", flowForm.getFormId());
					List<Object> objs = formDao.queryObjSql(sql, param);
					if(null != objs && objs.size()>0) {
						for (Object obj : objs) {
							if(StringUtils.isNotEmpty(delSql)) {
								param.clear();
							    param.put("formDataId", flowForm.getFormDataId());
							    executeSql(delSql.replace("${table}",obj.toString()), param);
							}
						}//for
					}//if
					delete(flowForm);
				}//for
				is = true;
			}//if
		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			flowForms = null;
			param = null;
		}
		return is;
	}
	
	
	/**
	 * 获取下一步参与者
	 * @param configAssignees
	 * @param orderId
	 * @param userId 当流程实例未启动时，用该参数来实现按部门过滤
	 * @param isDepartFilter
	 * @return
	 * @throws DaoException
	 */
	public List<TNUser> getNextNodeAssigners(String configAssignees,String orderId,String userId,boolean isDepartFilter) throws DaoException {
		List<TNUser> users = null;
		if(StringUtils.isNotEmpty(configAssignees)) {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("select distinct * from t_n_user u where (");
			ArrayList<String> userIds = new ArrayList<String>();
			ArrayList<String> departIds = new ArrayList<String>();
			ArrayList<String> positionIds = new ArrayList<String>();
			String[] assignees = configAssignees.split(",");
			int startIndex = 2;
			for (String assignee : assignees) {
				if(assignee.startsWith("u_")) {
					userIds.add(assignee.substring(startIndex));
				} else if(assignee.startsWith("d_")) {
					departIds.add(assignee.substring(startIndex));
				} else if(assignee.startsWith("p_")) {
					positionIds.add(assignee.substring(startIndex));
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			if(userIds.size()>0) {
				sqlBuilder.append(" u.id in(:ids) ");
				param.put("ids", userIds.toArray());
			}
			if(departIds.size()>0) {
				if(param.size()>0) {sqlBuilder.append(" or ");}
				sqlBuilder.append(" u.org_id in(:orgIds) ");
				param.put("orgIds", departIds.toArray());
			}
			if(positionIds.size()>0) {
				if(param.size()>0) {sqlBuilder.append(" or ");}
				sqlBuilder.append(" u.position_id in(:positionIds) ");
				param.put("positionIds", positionIds.toArray());
			}
			sqlBuilder.append(")");
			if(isDepartFilter) {
				if(StringUtils.isNotEmpty(orderId)) {
					Map<String,Object> orderParam = new HashMap<String, Object>();
					orderParam.put("orderId", orderId);
					List<TFlowForm> flowForms = queryByField(orderParam);
					userId = flowForms.get(0).getUserId();
				}
				sqlBuilder.append(" and u.org_id=(select ut.org_id from t_n_user ut where ut.id=:userId)");
				param.put("userId", userId);
			}
			users = userDao.querySql(sqlBuilder.toString(), param);
		}
		return users;
	}
	
	/**
	 * 更新流程实力标题
	 * @param orderId
	 * @param title
	 */
	public void updateTitle(String orderId, String title) {
		String sql = "update t_flow_form set title=:title where order_id=:orderId";
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("title", title);
		param.put("orderId", orderId);
		super.executeSql(sql, param);
		param.clear();
	}
}
