package cn.com.smart.flow.decide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.flow.ITaskSubmitBeforeAware;
import cn.com.smart.flow.bean.TaskBefore;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.dao.FlowFormDao;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.helper.ProcessHelper;
import cn.com.smart.web.dao.impl.PositionDao;
import cn.com.smart.web.dao.impl.UserDao;

/**
 * 部门过滤决策
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年11月17日
 */
@Component
public class DepartFilterDecide implements ITaskSubmitBeforeAware {

	private static final Logger log = Logger.getLogger(DepartFilterDecide.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private FlowFormDao flowFormDao;
	
	@Override
	public boolean taskExeBefore(TaskBefore taskBefore) {
		boolean is = true;
		String back = taskBefore.getFormData().getIsBack();
		if(StringUtils.isNotEmpty(back) && YesNoType.YES.getStrValue().equals(back)) { 
			return is;
		}
		return filterByAssigner(taskBefore);
	}
	
	/**
	 * 过滤处理者
	 * @param taskBefore
	 * @return
	 */
	public boolean filterByAssigner(TaskBefore taskBefore) {
		boolean is = true;
		List<ExtTaskModel> selectTaskModels = null;
		if(null == taskBefore.getTargetTaskModel()) {
			List<String> taskKeys = ProcessHelper.getSelectNextTaskKey(taskBefore.getFormData().getParams());
			if(CollectionUtils.isNotEmpty(taskKeys)) {
				List<TaskModel> taskModels = taskBefore.getTaskModel().getNextModels(TaskModel.class);
				selectTaskModels = ProcessHelper.getTaskModels(taskModels, taskKeys);
			}
		} else {
			selectTaskModels = new ArrayList<ExtTaskModel>();
			selectTaskModels.add(taskBefore.getTargetTaskModel());
		}
		if(CollectionUtils.isNotEmpty(selectTaskModels)) {
			String orgId = null;
			if(null != taskBefore.getFormData() && StringUtils.isNotEmpty(taskBefore.getFormData().getOrderId())) {
				Map<String,Object> orderParam = new HashMap<String, Object>();
				orderParam.put("orderId", taskBefore.getFormData().getOrderId());
				List<TFlowForm> flowForms = flowFormDao.queryByField(orderParam);
				if(CollectionUtils.isNotEmpty(flowForms)) {
					orgId = flowForms.get(0).getOrgId();
				}
			} else {
				orgId = taskBefore.getOrgId();
			}
			for (ExtTaskModel taskModel : selectTaskModels) {
				is = is && filterByDepart(taskModel, taskBefore, orgId);
			}
		}
		return is;
	}
	
	/**
	 * 按部门过滤
	 * @param taskModel 流程节点信息
	 * @param taskBefore 任务提交时传过来的参数
	 * @param orgId 起草人所在部门ID
	 * @return 过滤后，任务还有处理者，则表示成功；返回：true；否则返回：false
	 */
	@SuppressWarnings("unchecked")
	private boolean filterByDepart(ExtTaskModel taskModel,TaskBefore taskBefore,String orgId) {
		boolean is = true;
		if(YesNoType.YES.getStrValue().equals(taskModel.getIsDepartFilter())) {
			Map<String, Object> nextAssigners = taskBefore.getNextAssigners();
			if(null != nextAssigners && nextAssigners.size()>0) {
				List<String> assigners = (List<String>) nextAssigners.get(taskModel.getName());
				if(CollectionUtils.isNotEmpty(assigners)) {
					List<String> removeAssigners = new ArrayList<String>();
					for (String str : assigners) {
						if(!userDao.isExistUserInOrg(str, orgId) && 
								!positionDao.isExistPositionInOrg(str, orgId) && 
								!str.equals(orgId)) {
							removeAssigners.add(str);
						}
					}
					if(!removeAssigners.isEmpty())
						assigners.removeAll(removeAssigners);
					if(assigners.isEmpty())
						nextAssigners.remove(taskModel.getName());
					if(nextAssigners.isEmpty()) {
						is = false;
						log.error("下一步处理人，为空！");
					}
				}
			} else {
				String assignee = taskModel.getAssignee();
				String[] assignees = assignee.split(",");
				List<String> list = new ArrayList<String>();
				int startIndex = 2;
				String assigneeId = null;
				for(String str : assignees) {
					assigneeId = null;
					assigneeId = str.substring(startIndex);
					if(str.startsWith("u_")) {
						if(userDao.isExistUserInOrg(assigneeId, orgId))
							list.add(assigneeId);
					} else if(str.startsWith("p_")) {
						if(positionDao.isExistPositionInOrg(assigneeId, orgId))
							list.add(assigneeId);
					} else if(str.startsWith("d_")) {
						if(assigneeId.equals(orgId))
							list.add(assigneeId);
					}
				}
				if(CollectionUtils.isNotEmpty(list)) {
					if(null == nextAssigners) {
						nextAssigners = new HashMap<String, Object>();
						taskBefore.setNextAssigners(nextAssigners);
					}
					nextAssigners.put(taskModel.getName(), list);
				} else 
					is = false;
			}
		}//if
		return is;
	}

}
