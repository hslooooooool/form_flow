/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.smart.flow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.SnakerException;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.helper.ProcessHelper;

import com.mixsmart.enums.YesNoType;

/**
 * 封装snaker接口
 * @author lmq
 *
 */
@Component
public class SnakerEngineFacets {
	
	private static final Logger log = Logger.getLogger(SnakerEngineFacets.class);
	
	@Autowired
	private SnakerEngine engine;
	
	public SnakerEngine getEngine() {
		return engine;
	}
	
	/**
	 * 部署流程
	 * @param input
	 * @param creator
	 * @return
	 */
	public String deploy(InputStream input, String creator) {
		return getEngine().process().deploy(input, creator);
	}
	
	/**
	 * 重新部署流程
	 * @param id
	 * @param input
	 */
	public void redeploy(String id,InputStream input) {
		getEngine().process().redeploy(id, input);
	}
	
	/**
	 * 获取所有流程
	 * @return
	 */
	public List<String> getAllProcessNames() {
		List<Process> list = engine.process().getProcesss(new QueryFilter());
		List<String> names = new ArrayList<String>();
		for(Process entity : list) {
			if(names.contains(entity.getName())) {
				continue;
			} else {
				names.add(entity.getName());
			}
		}
		return names;
	}
	
	/**
	 * 启动流程事例
	 * @param processId
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
		return engine.startInstanceById(processId, operator, args);
	}
	
	/**
	 * 启动流程事例
	 * @param name
	 * @param version
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args) {
		return engine.startInstanceByName(name, version, operator, args);
	}
	
	/**
	 * 启动流程事例并执行
	 * @param name
	 * @param version
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args) {
		log.info("给["+name+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceByName(name, version, operator, args);
			List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
			List<Task> newTasks = new ArrayList<Task>();
			if(tasks != null && tasks.size() > 0) {
				Task task = tasks.get(0);
				newTasks.addAll(engine.executeTask(task.getId(), operator, args));
			}
			return order;
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processId
	 * @param operator
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecute(String processId, String operator, Map<String, Object> args) {
		log.info("给["+processId+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceById(processId, operator, args);
			return startInsAndExecuteResult(order, operator, null, null, args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processId
	 * @param operator
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecuteByName(String processName, String operator, Map<String, Object> args) {
		log.info("给["+processName+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceByName(processName, null,operator, args);
			return startInsAndExecuteResult(order, operator, null, null, args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processId
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
		log.info("给["+processId+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceById(processId, operator, args);
			if(null != order) {
				log.info("流程实例启动成功，启动的流程实例ID为：["+order.getId()+"]");
				List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
				List<Task> newTasks = new ArrayList<Task>();
				if(tasks != null && tasks.size() > 0) {
					Task task = tasks.get(0);
					newTasks.addAll(engine.executeTask(task.getId(), operator, args));
				}
			}
			return order;
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processName
	 * @param operator
	 * @param outputNames
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecuteByName(String processName, String operator,List<String> outputNames, Map<String, Object> args) {
		log.info("给["+processName+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceByName(processName, null,operator, args);
			return startInsAndExecuteResult(order, operator, outputNames, null, args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processId
	 * @param operator
	 * @param outputNames
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecute(String processId, String operator,List<String> outputNames, Map<String, Object> args) {
		log.info("给["+processId+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceById(processId, operator, args);
			return startInsAndExecuteResult(order, operator, outputNames, null,args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processName
	 * @param operator
	 * @param outputNames
	 * @param nextAssigners
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecuteByName(String processName, String operator,List<String> outputNames,Map<String,Object> nextAssigners, Map<String, Object> args) {
		log.info("给["+processName+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceByName(processName, null,operator, args);
			return startInsAndExecuteResult(order, operator, outputNames, nextAssigners, args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动流程事例并执行
	 * @param processId
	 * @param operator
	 * @param outputNames
	 * @param nextAssigners
	 * @param args
	 * @return 返回一个数组
	 * <ul>
	 * <li>第一个字段为流程实例ID</li>
	 * <li>第二个字段为任务ID</li>
	 * <li>第三个字段为任务名称(taskKey)</li>
	 * </ul>
	 */
	public String[] startInsAndExecute(String processId, String operator,List<String> outputNames,Map<String,Object> nextAssigners, Map<String, Object> args) {
		log.info("给["+processId+"]流程，启动一个流程实例");
		try {
			Order order = engine.startInstanceById(processId, operator, args);
			return startInsAndExecuteResult(order, operator, outputNames, nextAssigners, args);
		} catch(SnakerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 返回--启动流程事例并执行后的结果
	 * @param order
	 * @param operator
	 * @param outputNames
	 * @param args
	 * @return
	 */
	private String[] startInsAndExecuteResult(Order order, String operator,
			List<String> outputNames,Map<String,Object> nextAssigners, Map<String, Object> args) {
		String[] array = new String[3];
		if(null != order) {
			array[0] = order.getId();
			log.info("流程实例启动成功，启动的流程实例ID为：["+order.getId()+"]");
			List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
			List<Task> newTasks = new ArrayList<Task>();
			if(tasks != null && tasks.size() > 0) {
				Task task = tasks.get(0);
				array[1] = task.getId();
				array[2] = task.getTaskName();
				if(null == outputNames) {
					newTasks.addAll(engine.executeTask(task.getId(), operator, args));
				} else if (null == nextAssigners) {
					newTasks.addAll(engine.executeTask(task.getId(), operator,outputNames, args));
				} else {
					newTasks.addAll(engine.executeTask(task.getId(), operator,outputNames,nextAssigners, args));
				}
			}
		}
		return array;
	}
	
	
	/**
	 * 执行任务
	 * @param taskId
	 * @param operator
	 * @param args
	 * @return
	 */
	public List<Task> execute(String taskId, String operator, Map<String, Object> args) throws SnakerException {
		return engine.executeTask(taskId, operator, args);
	}
	
	/**
	 * 执行任务
	 * @param taskId
	 * @param operator
	 * @param outputNames
	 * @param args
	 * @return
	 */
	public List<Task> execute(String taskId, String operator,List<String> outputNames, Map<String, Object> args) throws SnakerException {
		return engine.executeTask(taskId, operator,outputNames, args);
	}
	
	/**
	 * 执行任务
	 * @param taskId
	 * @param operator
	 * @param outputNames
	 * @param nextAssigners
	 * @param args
	 * @return
	 */
	public List<Task> execute(String taskId, String operator,List<String> outputNames,Map<String,Object> nextAssigners, Map<String, Object> args) throws SnakerException {
		return engine.executeTask(taskId, operator, outputNames, nextAssigners, args);
	}
	
	/**
	 * 领取任务
	 * @param taskId
	 * @param operator
	 * @return
	 */
	public boolean takeTask(String taskId,String operator) {
		boolean is = false;
		if(StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(operator)) {
			try {
				engine.task().takeAssignTask(taskId, operator);
				is = true;
			} catch(SnakerException se) {
				se.printStackTrace();
			}
		}
		return is;
	}
	
	/**
	 * 执行跳转任务
	 * @param taskId
	 * @param operator
	 * @param args
	 * @param nodeName
	 * @return
	 */
	public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
		return engine.executeAndJumpTask(taskId, operator, args, nodeName);
	}
	
	/**
	 * 执行跳转任务
	 * @param taskId
	 * @param operator
	 * @param nextAssigners
	 * @param args
	 * @param nodeName
	 * @return
	 */
	public List<Task> executeAndJump(String taskId, String operator,Map<String, Object> nextAssigners, 
			Map<String, Object> args, String nodeName) {
		return engine.executeAndJumpTask(taskId, operator, nextAssigners, args, nodeName);
	}
	

	/**
	 * 主办任务
	 * @param taskId
	 * @param operator
	 * @param actors
	 * @return
	 */
    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    /**
     * 协办任务
     * @param taskId
     * @param operator
     * @param actors
     * @return
     */
    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }
    
    /**
     * 获取流程内容
     * @param orderId
     * @param taskName
     * @return
     */
    public Map<String, Object> flowData(String orderId, String taskName) {
    	Map<String, Object> data = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(taskName)) {
			List<HistoryTask> histTasks = engine.query().getHistoryRelateTasks(
							new QueryFilter().setOrderId(orderId).setName(taskName));
			List<Map<String, Object>> vars = new ArrayList<Map<String,Object>>();
			for(HistoryTask hist : histTasks) {
				vars.add(hist.getVariableMap());
			}
			data.put("vars", vars);
			data.put("histTasks", histTasks);
		}
		return data;
	}
	
    /**
     * 添加委托代理
     * @param entity
     */
	public void addSurrogate(Surrogate entity) {
		if(entity.getState() == null) {
			entity.setState(1);
		}
		engine.manager().saveOrUpdate(entity);
	}
	
	/**
	 * 删除委托代理
	 * @param id
	 */
	public void deleteSurrogate(String id) {
		engine.manager().deleteSurrogate(id);
	}
	
	/**
	 * 获取委托代理
	 * @param id
	 * @return
	 */
	public Surrogate getSurrogate(String id) {
		return engine.manager().getSurrogate(id);
	}
	
	/**
	 * 搜索委托代理信息
	 * @param page
	 * @param filter
	 * @return
	 */
	public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
		return engine.manager().getSurrogate(page, filter);
	}
	
	/**
	 * 删除部署流程(会删除所有流程数据，谨慎)
	 * @param processIds
	 * @return
	 */
	public boolean delelteProcess(String[] processIds) {
		boolean is = false;
		if(null != processIds && processIds.length>0) {
			for (int i = 0; i < processIds.length; i++) {
				engine.process().cascadeRemove(processIds[i]);
			}
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 删除流程实例
	 * @param orderId
	 * @return
	 */
	public boolean deleteOrder(String orderId) {
		boolean is = false;
		if(StringUtils.isNotEmpty(orderId)) {
			try {
			  engine.order().cascadeRemove(orderId);
			  is = true;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return is;
	}
	
	/**
	 * 获取流程定义信息
	 * @param processId
	 * @return
	 */
	public ExtProcess getProcess(String processId) {
		ExtProcess process = null;
		if(StringUtils.isEmpty(processId)) {
			return process;
		}
		return (ExtProcess) engine.process().getProcessById(processId);
	} 
	
	/**
	 * 获取任务元素
	 * @param processId
	 * @param taskKey
	 * @return
	 */
	public ExtTaskModel getTaskModel(String processId,String taskKey) {
		ExtTaskModel taskModel = null;
		if(StringUtils.isNotEmpty(processId)) {
		    List<TaskModel> models = getProcess(processId).getModel().getModels(TaskModel.class);
		    taskModel = ProcessHelper.getCurrentTaskModel(models, taskKey);
		}
		return taskModel;
	}
	
	/**
	 * 获取任务元素
	 * @param processId
	 * @param taskKeys
	 * @return
	 */
	public List<ExtTaskModel> getTaskModels(String processId,String[] taskKeys) {
		List<ExtTaskModel> taskModels = null;
		if(StringUtils.isNotEmpty(processId) && null != taskKeys && taskKeys.length>0) {
		    List<TaskModel> models = getProcess(processId).getModel().getModels(TaskModel.class);
		    taskModels = new ArrayList<ExtTaskModel>();
		    ExtTaskModel taskModel = null;
		    for (int i = 0; i < taskKeys.length; i++) {
		    	taskModel = ProcessHelper.getCurrentTaskModel(models, taskKeys[i]);
		    	if(null != taskModel) {
		    		taskModels.add(taskModel);
		    	}
			}//for
		}//if
		return taskModels;
	}
	
	/**
	 * 判断任务是否可以领取
	 * @param processId
	 * @param taskKey
	 * @return true--可以领取
	 */
	public boolean isTakeTask(String processId,String taskKey) {
		boolean is = false;
		ExtTaskModel taskModel = getTaskModel(processId, taskKey);
		if(null != taskModel && YesNoType.YES.getStrValue().equals(taskModel.getIsTakeTask())) {
			is = true;
		}
		taskModel = null;
		return is;
	}
	
	
	/**
	 * 添加变量到流程实例
	 * @param orderId
	 * @param flowVar
	 */
	public void addVar2Order(String orderId,Map<String,Object> flowVar) {
		engine.order().addVariable(orderId, flowVar);
	}
}
