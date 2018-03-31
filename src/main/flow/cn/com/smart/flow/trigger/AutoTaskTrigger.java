package cn.com.smart.flow.trigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.mixsmart.utils.StringUtils;

import cn.com.smart.flow.IFlowConstant;
import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.service.FlowFormService;

/**
 * 自动执行任务触发器(可以修改为监听者模式)
 * @author lmq <br />
 * 2016年10月4日
 * @version 1.0
 * @since 1.0
 */
@Component
public class AutoTaskTrigger implements IFlowTrigger<Task> {

	private static final Logger logger = LoggerFactory.getLogger(AutoTaskTrigger.class);
	
	private SimpleDateFormat dateFormat;
	@Autowired
	private SnakerEngineFacets facets;
	@Autowired
	private FlowFormService flowFormServ;
	
	public AutoTaskTrigger() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public String getName() {
		return "autoTask";
	}

	@Override
	public void execute(Task task) {
		if(logger.isInfoEnabled()) {
			logger.info("正在执行自动完成任务触发器....");
		}
		if(null == task) {
			throw new IllegalArgumentException("task为空");
		}
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
		if(currentTime >= expireTime && expireTime > 0) {
			NodeModel nodeModel = getNextNode(task);
			if(null != nodeModel) {
				if(nodeModel instanceof EndModel) {
					facets.executeAndJump(task.getId(), SnakerEngine.ADMIN, null, nodeModel.getName());
				} else {
					ExtTaskModel nextTaskModel = (ExtTaskModel)nodeModel;
					//判断当前节点是否选人。如果要选人，则无法自动执行
					if(YesNoType.NO.getStrValue().equals(nextTaskModel.getIsExeAssigner())) {
						if(YesNoType.YES.getStrValue().equals(nextTaskModel.getIsDepartFilter())) {
							List<String> users = getNextNodeAssigners(task, nextTaskModel);
							Map<String, Object> nextAssigners = new HashMap<String, Object>();
							nextAssigners.put(nextTaskModel.getName(), users);
							facets.executeAndJump(task.getId(), SnakerEngine.ADMIN, nextAssigners, null, nextTaskModel.getName());
						} else {
							facets.executeAndJump(task.getId(), SnakerEngine.ADMIN, null, nextTaskModel.getName());
						}
					}//if
				}//if
			}//if
		}//if
		if(logger.isInfoEnabled()) {
			logger.info("自动完成任务触发器执行完成....");
		}
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
	 * 获取下一节点处理者
	 * @param task
	 * @param extTaskModel
	 * @return
	 */
	private List<String> getNextNodeAssigners(Task task, ExtTaskModel extTaskModel) {
		String[] actorIds = facets.getEngine().query().getTaskActorsByTaskId(task.getId());
		return flowFormServ.getNextNodeAssigners(extTaskModel.getAssignee(), task.getOrderId(), actorIds[0], true);
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
