package cn.com.smart.flow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.flow.IFlowConstant;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.dao.FlowFormDao;
import cn.com.smart.form.bean.QueryFormData;
import cn.com.smart.form.bean.entity.TFormField;
import cn.com.smart.form.dao.FormFieldDao;
import cn.com.smart.form.service.IFormDataService;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.utils.ObjectUtil;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.constant.IWebConstant;
import cn.com.smart.web.plugins.OrgUserZTreeData;
import cn.com.smart.web.service.UserService;

/**
 * 流程表单
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Service
public class FlowFormService extends MgrServiceImpl<TFlowForm> {

	@Autowired
	private FormFieldDao formFieldDao;
	//private FormDataBusi formDataBusi;
	@Autowired
	private UserService userServ;
	@Autowired
	private IFormDataService formDataServ;
	
	/**
	 * 获取表单数据
	 * @param formId
	 * @param userId
	 * @return
	 */
	public SmartResponse<QueryFormData> getFormData(String formId,String userId) {
		return formDataServ.getFormData(null, formId, userId);
	}
	
	/**
	 * 获取流程表单信息通过流程实例ID
	 * @param orderId 流程实例ID
	 * @return
	 */
	public TFlowForm getFlowFormByOrderId(String orderId) {
		TFlowForm flowForm = null;
		if(StringUtils.isNotEmpty(orderId)) {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("orderId", orderId);
			SmartResponse<TFlowForm> flowFormResp = super.findByParam(param);
			if(OP_SUCCESS.equals(flowFormResp.getResult())) {
				flowForm = flowFormResp.getDatas().get(0);
			}
		}
		return flowForm;
	}
	
	/**
	 * 获取表单数据
	 * @param orderId
	 * @return
	 */
	public SmartResponse<QueryFormData> getFormData(String orderId) {
		SmartResponse<QueryFormData> smartResp = new SmartResponse<QueryFormData>();
		if(StringUtils.isEmpty(orderId)) {
			return smartResp;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		try {
			List<TFlowForm> flowForms = getDao().queryByField(params);
			if(null != flowForms && flowForms.size()>0) {
			    smartResp = formDataServ.getFormData(flowForms.get(0).getFormDataId(), flowForms.get(0).getFormId(), null);
				//smartResp = formDataBusi.getFormData(orderId, flowForms.get(0).getFormId(), null);
				QueryFormData formData = new QueryFormData();
				formData.setName("formDataId");
				formData.setValue(flowForms.get(0).getFormDataId());
				smartResp.setData(formData);
			}
		} catch (DaoException e) {
				e.printStackTrace();
		}
		return smartResp;
	}
	
	
	/**
	 * 保存表单数据
	 * @param datas
	 * @param formId
	 * @param userId
	 * @param formState 表单状态
	 * 1--保存(但未提交)
	 * 0-- 保存（并提交）
	 * @return
	 */
	public String saveForm(Map<String,Object> datas,String formId,String userId,Integer formState) {
		String id = null;
		if(StringUtils.isNotEmpty(formId) && null != datas && datas.size()>0) {
			//id = formDataBusi.saveForm(datas, formId, userId, formState);
			id = formDataServ.saveForm(datas, formId, userId, formState);
		}
		return id;
	}
	
	
	
	/**
	 * 更新表单数据
	 * @param datas
	 * @param formId
	 * @param formDataId 表单数据ID
	 * @param userId 
	 * @param formState 表单状态 <br />
	 * 1--保存(但未提交) <br />
	 * 0-- 保存（并提交）
	 * 
	 * @return
	 */
	public boolean updateForm(Map<String,Object> datas,String formId,String formDataId,String userId,Integer formState) {
		boolean is = false;
		if(StringUtils.isNotEmpty(formId) && null != datas && datas.size()>0 && StringUtils.isNotEmpty(formDataId)) {
			//is = formDataBusi.updateForm(datas, formId, formDataId,userId, formState);
			is = formDataServ.updateForm(datas, formId, formDataId, userId, formState);
		}
		return is;
	}
	
	
	/**
	 * 根据流程ID和用户ID，获取流程名称和用户姓名，组合流程实例标题 <br />
	 * 如：流程实例名称(用户姓名)
	 * @param processId
	 * @param userId
	 * @return
	 */
	protected String createTitle(String processId,String userId) {
		String title = null;
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(processId)) {
			return title;
		}
		Map<String,Object> params = new HashMap<String, Object>(2);
		params.put("processId", processId);
		params.put("userId", userId);
		try {
			String sql = SQLResUtil.getOpSqlMap().getSQL("get_userfull_flowname");
			if(StringUtils.isNotEmpty(sql)) {
				List<Object> lists = getDao().queryObjSql(sql, params);
				if(null != lists && lists.size()>0) {
					String processName = lists.get(0).toString();
					String fullName = lists.get(1).toString();
					title = processName + "("+fullName+")";
				}
				lists = null;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			params = null;
		}
		return title;
	}
	
	
	/**
	 * 获取实例标题
	 * @param submitFormData
	 * @param userId
	 * @param processName
	 * @return
	 */
	public String getInsTitle(SubmitFormData submitFormData,String userId, String processName) {
		String insTitle = null;
		LoggerUtils.debug(logger, "提交参数长度："+submitFormData.getParams());
		if(null == submitFormData || StringUtils.isEmpty(userId)) return insTitle;
		insTitle = getTitleFormParams(submitFormData.getParams(), submitFormData.getFormId(), processName);
		if(StringUtils.isEmpty(insTitle)) {
			insTitle = createTitle(submitFormData.getProcessId(), userId);
		}
		return insTitle;
	}
	
	/**
	 * 从参数中获取标题
	 * @param datas
	 * @param formId
	 * @param processName
	 * @return
	 */
	private String getTitleFormParams(Map<String, Object> datas, String formId, String processName) {
		String title = null;
		if(null == datas || datas.size() == 0 || StringUtils.isEmpty(formId)) {
			LoggerUtils.error(logger, "表单提交的数据为空");
			return title;
		}
		//获取流程实例标题对应的字段ID
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_institle_fieldid_by_form");
		if(StringUtils.isEmpty(sql)) {
			return title;
		}
		Map<String,Object> params = new HashMap<String, Object>(1);
		params.put("formId", formId);
		processName = StringUtils.isEmpty(processName)?"":(processName+"-");
		List<Object> lists = getDao().queryObjSql(sql, params);
		if(null != lists && lists.size()>0) {
			String insTitleFieldId = StringUtils.handleNull(lists.get(0));
			title = StringUtils.handleNull(datas.get(insTitleFieldId));
			if(StringUtils.isNotEmpty(title)) {
				title = processName + title;
			} else {
				LoggerUtils.info(logger, "标题获取失败");
				title = null;
			}
		}
		return title;
	}
	
	
	/**
	 * 过滤变量(从表单中获取流程变量)
	 * @param formId
	 * @param requestParams
	 * @return
	 */
	public Map<String,Object> filterFlowVar(String formId,Map<String,Object> requestParams) {
		Map<String,Object> flowVarMap = null;
		if(StringUtils.isEmpty(formId) || null == requestParams || requestParams.size()<1 ) {
			return flowVarMap;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("formId", formId);
		flowVarMap = new HashMap<String, Object>();
		try {
			List<TFormField> lists = formFieldDao.queryByField(params);
			for (TFormField formField : lists) {
				if(IFlowConstant.FLOW_VAR_FIELD_FLAG.equals(formField.getFlow())) {
					Object value = requestParams.get(formField.getTableFieldId());
					if(null != value) {
						value = ObjectUtil.covertDataType(value);
						flowVarMap.put(formField.getTableFieldId(), value);
					}
				}
			}
			lists = null;
		} catch (DaoException e) {
			e.printStackTrace();
			flowVarMap = null;
		}
		return flowVarMap;
	}
	
	
	/**
	 * 保存流程表单关联数据
	 * @param formData
	 * @param userId
	 * @param orgId 部门id
	 * @param title 标题
	 * @return
	 * @throws Exception
	 */
	public SmartResponse<String> saveFlowForm(SubmitFormData formData,String userId,String orgId,String title) throws Exception {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		LoggerUtils.info(logger, "保存流程表单关联数据信息...");
		if(null != formData && StringUtils.isNotEmpty(formData.getOrderId()) && 
				StringUtils.isNotEmpty(formData.getFormDataId()) && 
				StringUtils.isNotEmpty(formData.getFormId())) {
			TFlowForm flowForm = new TFlowForm();
			flowForm.setFormId(formData.getFormId());
			flowForm.setOrderId(formData.getOrderId());
			flowForm.setProcessId(formData.getProcessId());
			flowForm.setProcessName(formData.getProcessName());
			flowForm.setFormDataId(formData.getFormDataId());
			flowForm.setUserId(userId);
			flowForm.setTitle(StringUtils.handleNull(title));
			flowForm.setOrgId(orgId);
			
			SmartResponse<String> chRes = super.save(flowForm);
			if(chRes.getResult().equals(IWebConstant.OP_SUCCESS)) {
				LoggerUtils.info(logger, "保存流程表单关联数据信息[成功]...");
				smartResp.setResult(IWebConstant.OP_SUCCESS);
				smartResp.setData(chRes.getData());
				smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
			} else {
				LoggerUtils.error(logger, "保存流程表单关联数据信息[失败]...");
			}
			flowForm = null;
		}
		return smartResp;
	}
	
	/**
	 * 获取下一节点参与者
	 * @param configAssignees
	 * @param orderId
	 * @param userId
	 * @param isDepartFilter
	 * @return
	 */
	public List<String> getNextNodeAssigners(String configAssignees,String orderId,String userId,boolean isDepartFilter) {
		List<String> userIds = null;
		try {
			List<TNUser> users = getDao().getNextNodeAssigners(configAssignees, orderId, userId, isDepartFilter);
			if(CollectionUtils.isNotEmpty(users)) {
				userIds = new ArrayList<String>();
				for (TNUser user : users) {
					userIds.add(user.getId());
				}
			}
			users = null;
		} catch (ServiceException ex) {
			ex.printStackTrace();
		} catch (DaoException ex) {
			ex.printStackTrace();
		}
		return userIds;
	}
	
	/**
	 * 获取下一节点参与者
	 * @param configAssignees
	 * @param orderId
	 * @param userId
	 * @param isDepartFilter
	 * @return
	 */
	public SmartResponse<OrgUserZTreeData> getNextNodeAssigner(String configAssignees,String orderId,String userId,boolean isDepartFilter) {
		SmartResponse<OrgUserZTreeData> smartResp = new SmartResponse<OrgUserZTreeData>();
		try {
			List<TNUser> users = getDao().getNextNodeAssigners(configAssignees, orderId, userId, isDepartFilter);
			if(null != users && users.size()>0) {
				smartResp = userServ.getOrgUserZTreeByUser(users);
			}
		} catch (ServiceException ex) {
			ex.printStackTrace();
		} catch (DaoException ex) {
			ex.printStackTrace();
		}
		return smartResp;
	}
	
	/**
	 * 检测流程实例标题是否已经存在
	 * @param submitFormData 表单提交的数据
	 * @return 返回SmartResponse对象；
	 * 如果标题已经存在getResult()等于“1”；否则等于“-1”
	 */
	public SmartResponse<String> checkInsTitle(SubmitFormData submitFormData) {
		LoggerUtils.info(logger, "正在检测流程实例标题是否已经存在");
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("流程实例标题检测失败");
		if(null == submitFormData) return smartResp;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("formId", submitFormData.getFormId());
		//获取流程实例标题对应的字段ID
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_institle_fieldid_by_form");
		if(StringUtils.isNotEmpty(sql)) {
			List<Object> lists = null;
			try {
				//获取
				lists = getDao().queryObjSql(sql, params);
				if(CollectionUtils.isNotEmpty(lists)) {
					String insTitleFieldId = lists.get(0).toString();
					String insTitle = StringUtils.handleNull(submitFormData.getParams().get(insTitleFieldId));
					smartResp.setData(insTitleFieldId);
					if(StringUtils.isNotEmpty(insTitle)) {
						sql = SQLResUtil.getOpSqlMap().getSQL("check_instance_title");
						if(StringUtils.isNotEmpty(sql)) {
							params.put("title", insTitle);
							if(getDao().exeCountSql(sql, params)>0) {
								smartResp.setResult(OP_SUCCESS);
								smartResp.setMsg("标题已经存在");
							} else {
								smartResp.setMsg("标题未存在");
							}
						}
					}
				}
			} catch (DaoException ex) {
				ex.printStackTrace();
			} finally {
				lists = null;
			}
		}
		LoggerUtils.info(logger, smartResp.getMsg());
		return smartResp;
	}
	
	
	/**
	 * 更新标题
	 * @param orderId
	 * @param title
	 * @return
	 */
	public boolean updateInsTitle(String orderId, String title) {
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(title)) {
			return false;
		}
		getDao().updateTitle(orderId, title);
		return true;
	}
	
	/**
	 * 更新标题
	 * @param datas
	 * @param formDataId
	 * @return
	 */
	public boolean updateInsTitle(Map<String, Object> datas, String formDataId) {
		boolean is = false;
		if(null == datas || datas.size()==0 || StringUtils.isEmpty(formDataId)) {
			return is;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formDataId", formDataId);
		List<TFlowForm> flowForms = super.findByParam(param).getDatas();
		String title = null;
		if(null == flowForms || flowForms.size() == 0) {
			return is;
		}
		TFlowForm flowForm = flowForms.get(0);
		title = getTitleFormParams(datas, flowForm.getFormId(), null);
		if(StringUtils.isNotEmpty(title)) {
			String displayName = "";
			String sql = SQLResUtil.getOpSqlMap().getSQL("get_flow_displayname");
			if(StringUtils.isNotEmpty(sql)) {
				param.clear();
				param.put("formDataId", formDataId);
				List<Object> objList = getDao().queryObjSql(sql, param);
				if(CollectionUtils.isNotEmpty(objList)) {
					displayName = StringUtils.handleNull(objList.get(0));
				}
			}
			title = displayName+"-"+title;
			is = this.updateInsTitle(flowForm.getOrderId(), title);
		}
		return is;
	}

	@Override
	public FlowFormDao getDao() {
		return (FlowFormDao)super.getDao();
	}
}
