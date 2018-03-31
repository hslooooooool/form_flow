package cn.com.smart.flow.scanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.IQueryService;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.core.ServiceContext;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.TaskModel;
import org.springframework.stereotype.Component;

import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.scanner.listener.AbstractScanTaskListener;
import cn.com.smart.service.SmartContextService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;

/**
 * 任务扫描器
 * @author lmq  2017年3月22日
 * @version 1.0
 * @since 1.0
 */
@Component
public class TaskScanner extends AbstractFlowScanner<AbstractScanTaskListener> {
	
	public TaskScanner() {
		init();
	}
	
	private void init() {
		super.addListeners(SmartContextService.finds(AbstractScanTaskListener.class));
	}
	
	@Override
	public void run() {
		LoggerUtils.info(logger, "正在执行任务扫描...");
		int intPage = 1;
		Page<Task> page = new Page<Task>();
		page.setPage(intPage);
		page.setPageSize(100);
		IQueryService query = ServiceContext.getEngine().query();
		QueryFilter queryFilter = new QueryFilter();
		//获取待办任务，每次查询100条数据
		List<Task> tasks = query.getActiveTasks(page, queryFilter);
		Map<String, TaskModel> taskModelCache = new HashMap<String, TaskModel>();
		while(CollectionUtils.isNotEmpty(tasks)) {
			LoggerUtils.debug(logger, "执行第"+intPage+"次");
			for (Task task : tasks) {
				TaskModel taskModel = task.getModel();
				TFlowForm flowForm = null;
				if(null == taskModel) {
					flowForm = flowFormServ.getFlowFormByOrderId(task.getOrderId());
					if(null != flowForm) {
						//缓存任务模型，从缓存中获取任务模型，如果没有，则从数据库中查询(流程ID和任务KEY能唯一任务模型)
						//不同的任务，对应的任务模型可能是同一个的，为了避免重复查询，采用了临时缓存
						String cacheKey = flowForm.getProcessId() + "_"+ task.getTaskName();
						taskModel = taskModelCache.get(cacheKey);
						if(null == taskModel) {
							taskModel = facets.getTaskModel(flowForm.getProcessId(), task.getTaskName());
							taskModelCache.put(cacheKey, taskModel);
						}
						task.setModel(taskModel);
					}
				}//if
				notifyListener(task, flowForm);
			}//for
			intPage++;
			page.setPage(intPage);
			tasks = query.getActiveTasks(page, queryFilter);
		} //while
		LoggerUtils.info(logger, "任务扫描定时器执行结束...");
		taskModelCache.clear();
		taskModelCache = null;
		
	}
	
}
