package cn.com.smart.flow.scanner.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.EndModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TransitionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.flow.IFlowConstant;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.bean.entity.TFlowForm;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.service.ProcessFacade;
import cn.com.smart.init.config.InitSysConfig;

/**
 * 自动执行任务监听者；扫描到的任务；如果符合自动执行的条件；
 * 则该任务自动执行；一般处理超时任务自动执行的功能
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class AutoExecuteTaskListener extends AbstractScanTaskListener {

	private SimpleDateFormat dateFormat;
	@Autowired
	private SnakerEngineFacets facets;
	@Autowired
    private ProcessFacade processFacade;

	public AutoExecuteTaskListener() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Override
	protected void execute(Task task, TFlowForm flowForm) {
		TaskModel taskModel = task.getModel();
		String autoAllTask = InitSysConfig.getInstance().getValue("flow.task.expire.all.auto");
		YesNoType yesNo = YesNoType.getObjByStrValue(autoAllTask);
		boolean isAllTaskAuto = (null == yesNo)?false:yesNo.getValue();
		if(isAllTaskAuto) {
			if(null != taskModel && StringUtils.isNotEmpty(taskModel.getExpireTime()) 
					&& !"0".equals(taskModel.getExpireTime()) && 
					StringUtils.isNum(taskModel.getExpireTime()) 
					&& YesNoType.YES.getStrValue().equals(taskModel.getAutoExecute())) {
				autoExeTask(task, flowForm);
			}
		} else {
			if(null != taskModel && StringUtils.isNotEmpty(task.getExpireTime()) 
					&& YesNoType.YES.getStrValue().equals(taskModel.getAutoExecute())) {
				autoExeTask(task, flowForm);
			}
		}
	}
	
	/**
	 * 自动执行任务
	 * @param task
	 * @param flowForm
	 */
	private void autoExeTask(Task task, TFlowForm flowForm) {
		if(null == task) {
			throw new IllegalArgumentException("task为空");
		}
		LoggerUtils.debug(logger, "正在执行["+task.getDisplayName()+"]任务的自动完成任务触发器...");
		if(null == task.getModel()) {
			throw new IllegalArgumentException("taskModel为空");
		}
		String strExpireTime = task.getExpireTime();
		long expireTime = 0l;
		long currentTime = System.currentTimeMillis();
		try {
			if(StringUtils.isEmpty(strExpireTime)) {
				String strCreateTime = task.getCreateTime();
				float hours = Float.parseFloat(task.getModel().getExpireTime());
				if(hours>0) {
					Date createTime = dateFormat.parse(strCreateTime);
					long millis = (long)(hours*60*60*1000);
					expireTime = createTime.getTime() + millis;
				}
			} else {
				expireTime = dateFormat.parse(strExpireTime).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(currentTime < expireTime || expireTime == 0) {
			LoggerUtils.info(logger, "该任务["+task.getDisplayName()+"]未到超时时间或该任务没有配置超时时间.");
			return;
		}
		NodeModel nodeModel = getNextNode(task);
		if(null == nodeModel) {
			LoggerUtils.error(logger, "该任务["+task.getDisplayName()+"]对应的任务模型为空.");
			return;
		}
		if(nodeModel instanceof EndModel) {
			facets.executeAndJump(task.getId(), SnakerEngine.ADMIN, null, nodeModel.getName());
		} else {
			ExtTaskModel nextTaskModel = (ExtTaskModel)nodeModel;
			//判断当前节点是否选人。如果要选人，则无法自动执行
			if(YesNoType.NO.getStrValue().equals(nextTaskModel.getIsExeAssigner())) {
			  //TODO 修复不会按部门过滤的问题
                processFacade.executeAndJump(flowForm.getProcessId(),flowForm.getOrderId(), SnakerEngine.ADMIN, null, null, nextTaskModel.getName());
			}//if
		}//if
		LoggerUtils.debug(logger, "["+task.getDisplayName()+"]任务的自动完成任务触发器执行结束.");
	}
	
	
	/**
	 * 获取下步节点名称
	 * @return
	 */
	private NodeModel getNextNode(Task task) {
		NodeModel nodeModel = null;
		TaskModel taskModel = task.getModel();
		List<TransitionModel> outputs = taskModel.getOutputs();
		if(CollectionUtils.isNotEmpty(outputs)) {
			if(outputs.size() == 1) {
				nodeModel = outputs.get(0).getTarget();
			} else {
				TransitionModel tm = getNextTransitionModel(outputs);
				nodeModel = tm.getTarget();
			}
		}
		return nodeModel;
	}
	
	/**
	 * 获取下一步Transition；获取方式：
	 * 如果有多条Transition，则按序号获取最近一条
	 * @param outputs
	 * @return
	 */
	private TransitionModel getNextTransitionModel(List<TransitionModel> outputs) {
		List<TransitionModel> lists = new ArrayList<TransitionModel>();
		for (TransitionModel tm : outputs) {
			if(!IFlowConstant.FLOW_PATH_TYPE_BACK.equals(tm.getType())) {
				if(CollectionUtils.isEmpty(lists)) {
					lists.add(tm);
				} else {
					TransitionModel prevTm = lists.get(0);
					if(null != prevTm 
							&& prevTm.getSortNum().intValue()>tm.getSortNum().intValue()) {
						lists.add(0, tm);
					} else {
						lists.add(tm);
					}
				}
			}//if
		}//for
		return lists.get(0);
	}
	
}
