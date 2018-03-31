/**
 * 
 */
package cn.com.smart.flow.decide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.model.TaskModel.PerformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.flow.ITaskSubmitBeforeAware;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.bean.TaskBefore;
import cn.com.smart.service.SmartContextService;

/**
 * 驳回判断
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 2016年1月5日
 */
@Component
public class BackDecide implements ITaskSubmitBeforeAware {
	
	@Autowired
	private SnakerEngineFacets facet;
	
	@Override
	public boolean taskExeBefore(TaskBefore taskBefore) {
		boolean is = true;
		String back = taskBefore.getFormData().getIsBack();
		if(StringUtils.isNotEmpty(back) && YesNoType.YES.getStrValue().equals(back)) { 
			Map<String,Object> nextAssigners = taskBefore.getNextAssigners();
			String taskKey = null;
			if(null != nextAssigners && nextAssigners.size()>0) {
				taskKey = nextAssigners.keySet().iterator().next().toString();
			} else {
				Object outputName = taskBefore.getFormData().getParams().get("nextLineName");
				if(null != outputName && StringUtils.isNotEmpty(outputName.toString())) {
					if(outputName.toString().indexOf("_")>-1) {
						taskKey = outputName.toString().split("_")[1];
					}
				}
			}
			if(StringUtils.isNotEmpty(taskKey)) {
				List<String> operators = getBackOperators(taskKey, taskBefore.getFormData().getOrderId());
				if(CollectionUtils.isNotEmpty(operators)) 
					nextAssigners.put(taskKey, operators);
				else {
					//按部门节点过滤
					DepartFilterDecide departFilter = SmartContextService.find(DepartFilterDecide.class);
					is = departFilter.filterByAssigner(taskBefore);
				}
			}
		}
		return is;
	}

	/**
	 * 
	 * @param taskKey
	 * @param orderId
	 * @return
	 */
	public List<String> getBackOperators(String taskKey, String orderId) {
		Set<String> backOperators = null;
		List<String> operators = null;
		if(StringUtils.isEmpty(taskKey) || StringUtils.isEmpty(orderId)) {
			return null;
		}
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setOrderId(orderId);
		queryFilter.setName(taskKey);
		List<HistoryTask> hisTasks = facet.getEngine().query().getHistoryTasks(queryFilter);
		if(CollectionUtils.isNotEmpty(hisTasks)) {
			backOperators = new HashSet<String>();
			if(PerformType.ANY.ordinal() == hisTasks.get(0).getPerformType()) {
				for (int i = 0; i < hisTasks.size(); i++) {
					//跳过系统自动执行的和管理员强制跳转的
					if(!SnakerEngine.ADMIN.equals(hisTasks.get(i).getOperator())) {
						backOperators.add(hisTasks.get(i).getOperator());
						break;
					}
				}//for
			} else {
				String parentTaskId = null;
				for (int i = 0; i < hisTasks.size(); i++) {
					//跳过系统自动执行的和管理员强制跳转的
					if(!SnakerEngine.ADMIN.equals(hisTasks.get(i).getOperator())) {
						parentTaskId = hisTasks.get(i).getParentTaskId();
						break;
					}
				}//for
				if(null != parentTaskId) {
					for (HistoryTask hisTask : hisTasks) {
						if(parentTaskId.equals(hisTask.getParentTaskId())) {
							backOperators.add(hisTasks.get(0).getOperator());
						}
					}
				}//if
			}
			if(!backOperators.isEmpty()) {
				operators = new ArrayList<String>();
				operators.addAll(backOperators);
				backOperators = null;
			}
		}
		return operators;
	} 
	
}
