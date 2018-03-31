package cn.com.smart.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.service.ProcessFacade;
import cn.com.smart.service.SmartContextService;

import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.CollectionUtils;

/**
 * 流程上下文
 * 该类中的方法都不应该在事物中
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class ProcessContext {

	@Autowired
	private ProcessFacade pf;
	
	private List<ITaskAfterAware> taskAfters;

	public ProcessContext() {
		taskAfters = SmartContextService.finds(ITaskAfterAware.class);
	}
	
	/**
	 * 执行流程
	 * @param submitFormData
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public SmartResponse<String> execute(SubmitFormData submitFormData,String userId,String orgId) {
		SmartResponse<String> smartResp = pf.completeTask(submitFormData, userId, orgId);
		if(CollectionUtils.isNotEmpty(taskAfters)) {
			for (ITaskAfterAware taskAfter : taskAfters) {
				if((null == taskAfter.getProcessName() || ArrayUtils.isArrayContains(taskAfter.getProcessName(), submitFormData.getProcessName())) && 
					(null == taskAfter.getNodeName() || ArrayUtils.isArrayContains(taskAfter.getNodeName(), submitFormData.getTaskKey()))) {
					taskAfter.execute(submitFormData);
				}
			}
		}
		return smartResp;
	}
	
}
