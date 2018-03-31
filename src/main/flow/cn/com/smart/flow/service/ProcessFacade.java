package cn.com.smart.flow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TransitionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.flow.IFlowConstant;
import cn.com.smart.flow.IProcessExecuteAware;
import cn.com.smart.flow.ITaskSubmitBeforeAware;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.bean.DataClassify;
import cn.com.smart.flow.bean.OutputClassify;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.bean.TaskBefore;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.dao.FlowFormDao;
import cn.com.smart.flow.dao.FlowProcessDao;
import cn.com.smart.flow.decide.DepartFilterDecide;
import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.helper.DataClassifyHelper;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.form.bean.NameValueMap;
import cn.com.smart.form.bean.QueryFormData;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.form.interceptor.SubmitFormContext;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.SmartContextService;
import cn.com.smart.utils.StringUtil;
import cn.com.smart.web.constant.IWebConstant;
import cn.com.smart.web.plugins.OrgUserZTreeData;

/**
 * 流程运行处理类
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Component
public class ProcessFacade {
	//private static final Logger log = Logger.getLogger(ProcessFacade.class);

	@Autowired
	private FlowProcessDao flowProcessDao;
	@Autowired
	private FlowFormDao flowFormDao;
	@Autowired
	private FlowFormService flowFormServ; //流程表单服务类
	@Autowired
	private FlowAttachmentService flowAttServ; //流程附件服务类
	@Autowired
	private SnakerEngineFacets facets; //流程引擎
	
	/**
	 * 获取表单数据信息
	 * @param formId
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public SmartResponse<QueryFormData> getFormData(String formId,String orderId,String userId) {
		 SmartResponse<QueryFormData> chRes = null;
		 //流程实例ID(orderId)为空时，表示当前流程实例还未启动，流程在第一个节点
		 if(StringUtils.isEmpty(orderId)) {
			//获取表单里面填写的值
			chRes = flowFormServ.getFormData(formId, userId);
		 } else {
		    //获取表单里面填写的值
		    chRes = flowFormServ.getFormData(orderId);
		  }
		return chRes;
	}
	
	/**
	 * 出口线分类
	 * @param outputs
	 * @return
	 */
	public OutputClassify outputClassify(List<TransitionModel> outputs) {
		OutputClassify classify = null;
		if(null == outputs || outputs.size()<1) {
			return classify;
		}
		classify = new OutputClassify();
		List<NameValueMap> backLines = new ArrayList<NameValueMap>();
		List<NameValueMap> normalLines = new ArrayList<NameValueMap>();
		NameValueMap nameValMap = null;
		TransitionModel tmp = null;
		for (TransitionModel tm : outputs) {
			nameValMap = new NameValueMap();
			nameValMap.setName(tm.getDisplayName());
			if(null != tm.getTarget() && StringUtils.isNotEmpty(tm.getTarget().getName())) {
				nameValMap.setValue(tm.getName()+"_"+tm.getTarget().getName());
			} else {
				nameValMap.setValue(tm.getName());
			}
			try {
				nameValMap.setOther(tm.getIsCheckForm().toString());
			} catch (Exception ex) {
				nameValMap.setOther(YesNoType.YES.getStrValue());
			}
			if(IFlowConstant.FLOW_PATH_TYPE_BACK.equals(tm.getType())) {
				addOutputLine(backLines, tmp, tm, nameValMap);
			} else {
				addOutputLine(normalLines, tmp, tm, nameValMap);
			}
			tmp = tm;
		}
		classify.setBackLines((backLines.size()<1?null:backLines));
		classify.setNormalLines((normalLines.size()<1?null:normalLines));
		return classify;
	}
	
	/**
	 * 获取流程表单信息
	 * @param processId
	 * @return
	 */
	public SmartResponse<TForm> getForm(String processId) throws ServiceException {
		SmartResponse<TForm> smartResp = new SmartResponse<TForm>();
		if(StringUtils.isEmpty(processId)) {
			return smartResp;
		}
		try {
			TForm form = flowProcessDao.getFormByProcessId(processId);
			if(null != form) {
				smartResp.setData(form);
				smartResp.setResult(IWebConstant.OP_SUCCESS);
				smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return smartResp;
	}
	
	/**
	 * 保存或更新；
	 * 该方法已过期；请使用{@link cn.com.smart.form.service.IFormDataService#saveOrUpdateForm(Map, String, String, String, Integer)} 
	 * 方法代替
	 * @param data
	 * @param userId
	 * @return
	 */
	@Deprecated
	public SmartResponse<String> saveOrUpdateForm(SubmitFormData data,String userId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		//判断数据是否存在
		if(null == data || StringUtils.isEmpty(data.getFormId())) {
			return smartResp;
		}
		if(StringUtils.isEmpty(data.getFormDataId())) {
			String id = flowFormServ.saveForm(data.getParams(), data.getFormId(),userId,data.getFormState());
			if(StringUtils.isNotEmpty(id)) {
				smartResp.setResult(IWebConstant.OP_SUCCESS);
				smartResp.setMsg("表单数据保存成功");
				smartResp.setData(id);
			}
		} else {
			boolean is = SubmitFormContext.getInstance().before(data.getFormId(), data.getFormDataId(), data.getParams(), userId);
			if(is && flowFormServ.updateForm(data.getParams(), data.getFormId(), data.getFormDataId(),userId,data.getFormState())){
				smartResp.setResult(IWebConstant.OP_SUCCESS);
				smartResp.setMsg("表单数据保存成功");
				smartResp.setData(data.getFormDataId());
			}
		}
		YesNoType state = YesNoType.NO;
		if(IWebConstant.OP_SUCCESS.equals(smartResp.getResult())) {
			state = YesNoType.YES;
		}
		SubmitFormContext.getInstance().after(state, data.getFormId(), data.getFormDataId(), data.getParams(), userId);
		return smartResp;
	}
	
	/**
	 * 获取流程实例标题
	 * @param orderId
	 * @return
	 */
	public String[] getNameAndTitle(String orderId) {
		String[] array = null;
		if(StringUtils.isNotEmpty(orderId)) {
			array = flowFormDao.getFlowFormInfo(orderId);
		}
		return array;
	}
	
	/**
	 * 领取任务
	 * @param processId
	 * @param taskId
	 * @param taskKey
	 * @param userId
	 * @return
	 */
	public synchronized SmartResponse<String> takeTask(String processId,String taskId,String taskKey,String userId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(facets.isTakeTask(processId, taskKey)) {
			if(!this.isHasTakeTask(taskId)) {
				if(facets.takeTask(taskId, userId)) {
					smartResp.setResult(IWebConstant.OP_SUCCESS);
					smartResp.setMsg("任务领取成功");
				} else {
					smartResp.setMsg("任务领取失败");
				}
			} else {
				smartResp.setMsg("该任务已被领取");
			}
		} else {
			smartResp.setMsg("该任务不可以领取");
		}
		return smartResp;
	}
	
	/**
	 * 完成任务
	 * @param submitFormData
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public SmartResponse<String> completeTask(SubmitFormData submitFormData,String userId,String orgId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("任务处理失败");
		try {
			ProcessHelper.initProcessNameOrId(facets, submitFormData);
			String formDataId = submitFormData.getFormDataId();
			Map<String,Object> params = submitFormData.getParams();
			smartResp = saveOrUpdateForm(submitFormData,userId);
			if(!IWebConstant.OP_SUCCESS.equals(smartResp.getResult())) {
				return smartResp;
			}
			List<String> outputNames = ProcessHelper.getOutputNames(params);
			Map<String,Object> nextAssigners = ProcessHelper.getNextAssigner(params);
			
			submitFormData.setFormDataId(smartResp.getData());
			//获取处理意见
			String handleSuggest = StringUtils.handleNull(params.get(IFlowConstant.FLOW_HANDLE_SUGGEST));
			//过滤变量(获取流程变量)
			Map<String,Object> flowVar = flowFormServ.filterFlowVar(submitFormData.getFormId(), params);
			params = flowVar;
			if(StringUtils.isNotEmpty(handleSuggest)) {
				if(handleSuggest.length()>1500) {
					handleSuggest = handleSuggest.substring(0,1500);
				}
				handleSuggest = StringUtil.escapeHtml(handleSuggest);
				params.put("handleSuggest", handleSuggest);
			}
			ExtTaskModel model = facets.getTaskModel(submitFormData.getProcessId(), submitFormData.getTaskKey());
			 if(null != model) {
				 submitFormData.setTaskName(model.getDisplayName());
			 }
			boolean isSuccess = true;
			//执行任务前置方法
			List<ITaskSubmitBeforeAware> taskBeforeList = SmartContextService.finds(ITaskSubmitBeforeAware.class);
			if(null == nextAssigners) {
				nextAssigners = new HashMap<String, Object>();
			}
			for (ITaskSubmitBeforeAware taskSubBefore : taskBeforeList) {
				TaskBefore taskBefore = new TaskBefore(submitFormData, userId, orgId, 
						outputNames, nextAssigners, params,model);
				isSuccess = isSuccess && taskSubBefore.taskExeBefore(taskBefore);
			}
			if(isSuccess) {
				//判断流程实例是否启动[当实例ID和任务ID为空时，创建流程实例]
				if(StringUtils.isEmpty(submitFormData.getOrderId()) && 
						StringUtils.isEmpty(submitFormData.getTaskId())) {
					params = (null == params)?new HashMap<String, Object>():params;
					params.put(IFlowConstant.FLOW_CREATE_USER, userId);
					//启动一个流程实例
					String[] arrays = null;
					//ExtTaskModel model = facets.getTaskModel(submitFormData.getProcessId(), submitFormData.getTaskKey());
					if(null != outputNames && outputNames.size()>0) {
						if(null != nextAssigners && nextAssigners.size()>0) 
							arrays = facets.startInsAndExecute(submitFormData.getProcessId(), userId, outputNames, nextAssigners, params);
						else 
							arrays = facets.startInsAndExecute(submitFormData.getProcessId(), userId, outputNames, params);
					} else 
						arrays = facets.startInsAndExecute(submitFormData.getProcessId(), userId, params);
					if(null != arrays && arrays.length>2) {//流程实例启动成功
						submitFormData.setOrderId(arrays[0]);
						submitFormData.setTaskId(arrays[1]);
						submitFormData.setTaskKey(arrays[2]);
						ExtProcess extProcess = facets.getProcess(submitFormData.getProcessId());
						//获取标题
						String title = flowFormServ.getInsTitle(submitFormData,userId,extProcess.getDisplayName());
						flowFormServ.saveFlowForm(submitFormData,userId, orgId, title);
						//更新流程实例未启动之前，上传了的附件(流程实例与附件关联起来)
						if(StringUtils.isEmpty(formDataId)) {
							flowAttServ.updateAtt(submitFormData,userId);
						}
						smartResp.setResult(IWebConstant.OP_SUCCESS);
						smartResp.setMsg("流程提交成功");
						isSuccess = true;
					} else {
						submitFormData.setFormState(1);
						saveOrUpdateForm(submitFormData,userId);
						smartResp.setMsg("该任务无法处理");
						smartResp.setResult(IWebConstant.OP_FAIL);
						throw new RuntimeException(smartResp.getMsg());
					}
				} else {
					//完成任务
					if(null != flowVar && flowVar.size()>0 && StringUtils.isNotEmpty(submitFormData.getOrderId())) {
						facets.addVar2Order(submitFormData.getOrderId(), flowVar);
					}
					if(null != outputNames && outputNames.size()>0) {
						if(null != nextAssigners && nextAssigners.size()>0) 
							facets.execute(submitFormData.getTaskId(), userId, outputNames, nextAssigners, params);
						else 
							facets.execute(submitFormData.getTaskId(), userId, outputNames, params);
					} else 
						facets.execute(submitFormData.getTaskId(), userId, params);
					smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
					smartResp.setMsg("任务处理成功");
					isSuccess = true;
					flowFormServ.updateInsTitle(submitFormData.getParams(), formDataId);
				}
				if(isSuccess) {
					 List<IProcessExecuteAware> processExeList = SmartContextService.finds(IProcessExecuteAware.class);
					 for (IProcessExecuteAware processExe : processExeList) {
						processExe.taskExeAfter(submitFormData,userId,orgId);
					}
				}
			} else {
				smartResp.setResult(IWebConstant.OP_FAIL);
				smartResp.setMsg("该任务无法处理");
				throw new RuntimeException("该任务无法处理");
			}
		} catch (Exception e) {
			smartResp.setResult(IWebConstant.OP_FAIL);
			smartResp.setMsg("该任务无法处理");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return smartResp;
	}
	
	/**
	 * 删除流程实例
	 * @param orderId 实例ID
	 * @return
	 */
	public SmartResponse<String> deleteOrder(String orderId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isEmpty(orderId)) {
			return smartResp;
		}
		String[] orderIds = orderId.split(IConstant.MULTI_VALUE_SPLIT);
		for (int i = 0; i < orderIds.length; i++) {
			facets.deleteOrder(orderIds[i]);
		}
		//删除其他关联数据
		flowFormDao.deleteAssocData(orderIds);
		smartResp.setResult(IWebConstant.OP_SUCCESS);
		smartResp.setMsg("实例删除成功");
		return smartResp;
	}
	
	/**
	 * 获取历史任务
	 * @param orderId
	 * @return
	 */
	public SmartResponse<HistoryTask> findHistTasks(String orderId) {
		SmartResponse<HistoryTask> smartResp = new SmartResponse<HistoryTask>();
		if(StringUtils.isEmpty(orderId)) {
			return smartResp;
		}
		List<HistoryTask> histTasks = facets.getEngine().query().getHistoryRelateTasks(new QueryFilter().setOrderId(orderId).order(QueryFilter.ASC).orderBy("finish_time"));
		if(null != histTasks && histTasks.size()>0) {
			smartResp.setResult(IWebConstant.OP_SUCCESS);
			smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
			//处理流程意见
			for (HistoryTask histTask : histTasks) {
				if(null != histTask.getVariableMap() && histTask.getVariableMap().size()>0) {
					String handleSuggest = StringUtils.handleNull(histTask.getVariableMap().get(IFlowConstant.FLOW_HANDLE_SUGGEST));
					if(StringUtils.isNotEmpty(handleSuggest)) {
						handleSuggest = StringUtil.unescapeHtml(handleSuggest);
					}
					histTask.setVariable(handleSuggest);
				} else {
					histTask.setVariable("");
				}
			}
			smartResp.setDatas(histTasks);
		}
 		return smartResp;
	}
	
	/**
	 * 待办分类
	 * @param workItems
	 * @return
	 */
	public SmartResponse<DataClassify<WorkItem>> todoClassify(List<WorkItem> workItems) {
		SmartResponse<DataClassify<WorkItem>> smartResp = new SmartResponse<DataClassify<WorkItem>>();
		List<DataClassify<WorkItem>> todoClassifys = DataClassifyHelper.todoClassify(workItems);
		if(null != todoClassifys && todoClassifys.size()>0) {
			smartResp.setResult(IWebConstant.OP_SUCCESS);
			smartResp.setMsg(IWebConstant.OP_SUCCESS_MSG);
			smartResp.setDatas(todoClassifys);
			todoClassifys = null;
		}
		return smartResp;
	}
	
	/**
	 * 与任务模版元素关联 <br />
	 * 如：判断任务是否需要领取(用于待办查询)
	 * @param workItems
	 * @param userId
	 */
	public void assocTaskModel(List<WorkItem> workItems,String userId) {
		if(null != workItems && workItems.size()>0) {
			String processId = null;
			List<TaskModel> taskModels = null;
			boolean isTake = false;
			for (WorkItem workItem : workItems) {
				isTake = false;
				if(StringUtils.isEmpty(processId) || !processId.equals(workItem.getProcessId())) {
					processId = workItem.getProcessId();
					taskModels = facets.getProcess(processId).getModel().getTaskModels();
				} 
				try {
				   String value = StringUtils.handleNull(workItem.getTaskVariableMap().get("S-ACTOR"));
				   if(StringUtils.handleNull(value).equals(userId) || 
						   StringUtils.isNotEmpty(workItem.getTaskTakeTime())){
					   isTake = true;
				   }
				   ExtTaskModel taskModel = ProcessHelper.getCurrentTaskModel(taskModels, workItem.getTaskKey());
				   if(YesNoType.YES.getStrValue().equals(taskModel.getIsTakeTask()) && !isTake){
					   workItem.setIsTake(YesNoType.YES.getStrValue());
				   }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 *  * 获取指定任务的处理人
	 * @param processId 流程ID
	 * @param orderId 流程实例ID
	 * @param userId 当流程实例未启动时，用该参数来实现按部门过滤
	 * @param taskKey 任务key
	 * @return
	 */
	public SmartResponse<OrgUserZTreeData> getAssignees(String processId,String orderId,String userId,String taskKey) {
		SmartResponse<OrgUserZTreeData> chRes = new SmartResponse<OrgUserZTreeData>();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(taskKey)) {
			 ExtTaskModel taskModel = facets.getTaskModel(processId, taskKey);
			 if(null != taskModel) {
				 String assignees = taskModel.getAssignee();
				 boolean isFilter = false;
				 if(YesNoType.YES.getStrValue().equals(taskModel.getIsDepartFilter())) {
					 isFilter = true;
				 }
				 chRes = flowFormServ.getNextNodeAssigner(assignees, orderId,userId, isFilter);
			 }
		}
		return chRes;
	}
	
	
	
	/**
	 * 执行跳转任务
	 * @param processId
	 * @param orderId
	 * @param operator
	 * @param nextAssigners
	 * @param args
	 * @param nodeName
	 * @return
	 */
	public SmartResponse<String> executeAndJump(String processId, String orderId, String operator, Map<String, Object> nextAssigners,Map<String, Object> args, String nodeName) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(StringUtils.isNotEmpty(processId) && StringUtils.isNotEmpty(orderId) 
				&& StringUtils.isNotEmpty(operator) && StringUtils.isNotEmpty(nodeName)) {
			List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
			if(null != tasks && tasks.size()>0) {
				try {
					SubmitFormData submitFormData = new SubmitFormData();
					submitFormData.setOrderId(orderId);
					submitFormData.setProcessId(processId);
					submitFormData.setTaskKey(nodeName);
					//submitFormData.setIsBack(StringUtils.handleNull(args.get("isBack")));
					submitFormData.setIsBack(YesNoType.NO.getStrValue());
					
					ExtTaskModel model = facets.getTaskModel(submitFormData.getProcessId(),submitFormData.getTaskKey());
					TaskBefore taskBefore = new TaskBefore(submitFormData, operator, null,null, nextAssigners, null,null);
					taskBefore.setTargetTaskModel(model);
					taskBefore.setNextAssigners(nextAssigners);
					
					DepartFilterDecide departFilter = SmartContextService.find(DepartFilterDecide.class);
					departFilter.taskExeBefore(taskBefore);
				    tasks = facets.executeAndJump(tasks.get(0).getId(), operator, taskBefore.getNextAssigners(), args, nodeName);
				    smartResp.setResult(IWebConstant.OP_SUCCESS);
				    smartResp.setMsg("处理成功");
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new RuntimeException(ex);
				}
			}
		}
		return smartResp;
	}
	
	
	/**
	 * 获取一下节点及处理人
	 * @param smartResp
	 * @return
	 */
	public void getTaskAndActors(SmartResponse<Object> smartResp) {
		if(null != smartResp && 
				IWebConstant.OP_SUCCESS.equals(smartResp.getResult())) {
			List<Object> objs = smartResp.getDatas();
			Map<String,Object> param = new HashMap<String, Object>();
			String sql = SQLResUtil.getOpSqlMap().getSQL("get_task_actors");
			if(StringUtils.isNotEmpty(sql)) {
				List<Object> newList = new ArrayList<Object>();
				for (Object obj : objs) {
					Object[] objArray = (Object[])obj;
					param.clear();
					param.put("orderId", objArray[1]);
					List<ExtTaskModel> taskModels = getActivityTaskModel(StringUtils.handleNull(objArray[0]), 
							StringUtils.handleNull(objArray[1]));
					boolean isDepartFilter = false;
					List<Object> nextNodeAndHandlers = new ArrayList<Object>();
					TFlowForm flowForm = null;
					if(taskModels.size()>1) {
						System.out.println(objArray[0].toString());
					}
					for (ExtTaskModel taskModel : taskModels) {
						if(YesNoType.YES.getStrValue().equals(taskModel.getIsDepartFilter())) {
							isDepartFilter = true;
						}
						if(isDepartFilter) {
							if(flowForm == null) {
								List<TFlowForm> flowForms = flowFormDao.queryByField(param);
								if(CollectionUtils.isNotEmpty(flowForms)) {
									flowForm = flowForms.get(0);
								}
							} 
							if(null != flowForm) {
								param.put("orgId", flowForm.getOrgId());	
							}
						}
						param.put("taskKey", taskModel.getName());
						nextNodeAndHandlers.addAll(flowProcessDao.queryObjSql(sql, param));
					}//for
					Object[] objArrayExts = new Object[objArray.length+1];
					System.arraycopy(objArray, 0, objArrayExts, 0, objArray.length);
					objArrayExts[objArrayExts.length-1] = nextNodeAndHandlers;
					newList.add(objArrayExts);
				}
				objs = null;
				smartResp.setDatas(newList);
			}
		}
	} 
	
	/**
	 * 获取当前流程实例下的活动任务模型
	 * @param processId
	 * @param orderId
	 * @return
	 */
	public List<ExtTaskModel> getActivityTaskModel(String processId, String orderId) {
		List<ExtTaskModel> taskModels = null;
		if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(processId)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", orderId);
			List<Object> list = flowProcessDao.queryObjSql(SQLResUtil.getOpSqlMap().getSQL("get_taskmodel_order"), param);
		    List<TaskModel> models = facets.getProcess(processId).getModel().getModels(TaskModel.class);
		    if(CollectionUtils.isNotEmpty(models) && CollectionUtils.isNotEmpty(list)) {
		    	taskModels = new ArrayList<ExtTaskModel>();
		    	for (Object obj : list) {
					Object[] objArray = (Object[]) obj;
					ExtTaskModel taskModel = ProcessHelper.getCurrentTaskModel(models,
							StringUtils.handleNull(objArray[1]));
					if(null != taskModel) {
						taskModels.add(taskModel);
					}
				}
		    }//if
		}//if
		return taskModels;
	}
	
	/**
	 * 判断任务是否被领取
	 * @param taskId 任务ID
	 * @return 如果领取；返回：true；否则返回：false
	 */
	private boolean isHasTakeTask(String taskId) {
		boolean is = false;
		if(StringUtils.isNotEmpty(taskId)) {
			String countSql = SQLResUtil.getOpSqlMap().getSQL("is_take_task");
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("taskId", taskId);
			if(flowProcessDao.exeCountSql(countSql, param)>0) {
				is = true;
			}
		}
		return is;
	}
	
	/**
	 * 获取历史任务
	 * @param orderId
	 * @param taskKey
	 * @return
	 */
	public List<Object> getHisTasks(String orderId, String taskKey) {
		List<Object> lists = null;
		if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(taskKey)) {
			String sql = SQLResUtil.getOpSqlMap().getSQL("get_hist_task_by_order_taskKey");
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("taskKey", taskKey);
			param.put("orderId", orderId);
			lists = flowProcessDao.queryObjSql(sql, param);
		}
		return lists;
	}

	/**
	 * 添加流程出口（有两种类型的出口，一种是驳回的，一种正常流转的）
	 * @param outputs
	 * @param tmp
	 * @param tm
	 * @param nameValMap
	 */
	private void addOutputLine(List<NameValueMap> outputs, TransitionModel tmp,TransitionModel tm, NameValueMap nameValMap) {
		if(null == tmp) {
			outputs.add(nameValMap);
		} else if(tm.getSortNum()<tmp.getSortNum()) {
			outputs.add(0, nameValMap);
		} else {
			outputs.add(nameValMap);
		}
	}
	
}
