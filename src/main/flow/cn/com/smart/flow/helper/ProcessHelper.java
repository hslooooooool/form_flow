package cn.com.smart.flow.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.smart.flow.SnakerEngineFacets;
import cn.com.smart.flow.bean.TaskInfo;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.NullArgumentException;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.model.EndModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.TaskModel;

import cn.com.smart.flow.bean.JumpNodeInfo;
import cn.com.smart.flow.ext.ExtProcess;
import cn.com.smart.flow.ext.ExtTaskModel;
import cn.com.smart.flow.service.ProcessFacade;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 流程处理助手
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
public class ProcessHelper {

	/**
	 * 节点名称分隔符
	 */
	public static final String NODE_NAME_SEPARATOR = ";";
	
	/**
	 * 处理请求参数
	 * @param params
	 * @return
	 */
	public static Map<String,Object> handleRequestParam(Map<String,Object> params) {
		if(null != params && params.size()>0) {
			List<String> removeKeys = null;
			for (String key : params.keySet()) {
				//处理流程变量
				int index = key.indexOf("_");
				if(index>-1) {
		            String paramValue = StringUtils.handleNull(params.get(key));
		            paramValue = StringUtils.repaceSpecialChar(paramValue);
		            if(StringUtils.isNotEmpty(paramValue)) {
		            	char type = key.charAt(0);
		            	String keyTmp = key.substring(index + 1);
		                Object value = null;
		                boolean is = false;
		                switch(type) {
	                    case 'S':
	                        value = paramValue;
	                        is = true;
	                        break;
	                    case 'I':
	                        value = ConvertUtils.convert(paramValue, Integer.class);
	                        is = true;
	                        break;
	                    case 'L':
	                        value = ConvertUtils.convert(paramValue, Long.class);
	                        is = true;
	                        break;
	                    case 'B':
	                        value = ConvertUtils.convert(paramValue, Boolean.class);
	                        is = true;
	                        break;
	                    case 'D':
	                        value = ConvertUtils.convert(paramValue, Date.class);
	                        is = true;
	                        break;
	                    case 'N':
	                        value = ConvertUtils.convert(paramValue, Double.class);
	                        is = true;
	                        break;
	                    default:
	                        value = paramValue;
	                        break;
	                    }
		                if(is) {
		                	params.put(keyTmp, value);
		                	removeKeys = (removeKeys==null)?new ArrayList<String>():removeKeys;
		                	removeKeys.add(key);
		                }
		            }
				}
			}//for
			if(null != removeKeys && removeKeys.size()>0) {
				for (String key:removeKeys) {
					params.remove(key);
				}
			}
		}//if
		return params;
	}
	
	/**
	 * 获取当前任务模式 <br />
	 * TaskModel中包含了设计流程节点时的所有数据信息
	 * @param models
	 * @param taskKey
	 * @return
	 */
	public static ExtTaskModel getCurrentTaskModel(List<TaskModel> models,String taskKey) {
		ExtTaskModel taskModel = null;
		if(null != models && models.size()>0) {
			if(StringUtils.isEmpty(taskKey)) {
				taskModel = (ExtTaskModel) models.get(0);
			} else {
				for (TaskModel tm : models) {
					if(tm.getName().equals(taskKey)) {
						taskModel = (ExtTaskModel) tm;
						break;
					}
				}
			}
		}
		return taskModel;
	}
	
	/**
	 * 获取当前任务模式 <br />
	 * TaskModel中包含了设计流程节点时的所有数据信息
	 * @param models
	 * @param taskKeys
	 * @return
	 */
	public static List<ExtTaskModel> getTaskModels(List<TaskModel> models,List<String> taskKeys) {
		List<ExtTaskModel> taskModels = null;
		if(CollectionUtils.isNotEmpty(models) && CollectionUtils.isNotEmpty(taskKeys)) {
			taskModels = new ArrayList<ExtTaskModel>();
			for (String taskKey : taskKeys) {
				for (TaskModel tm : models) {
					if(tm.getName().equals(taskKey)) {
						taskModels.add((ExtTaskModel) tm);
						break;
					}
				}
			}
		}
		return taskModels;
	}
	
	/**
	 * 流程实例ID和创建人转换为数组形式
	 * @param orders
	 * @return
	 */
	public static String[][] orders2Obj(List<Order> orders) {
		String[][] datas = new String[orders.size()][];
		int index = 0;
		for (Order order : orders) {
			datas[index][0] = order.getId();
			datas[index][1] = order.getCreator();
		}
		return datas;
	}
	
	/**
	 * 从表单提交的数据中获取流程出口线路名称
	 * @param params
	 * @return
	 */
	public static List<String> getOutputNames(Map<String,Object> params) {
		List<String> outputNames = null;
		if(null == params || params.size()<1) {
			return outputNames;
		}
		Object outputName = params.get("nextLineName");
		if(null != outputName && StringUtils.isNotEmpty(outputName.toString())) {
			outputNames = new ArrayList<String>();
			if(outputName.getClass().isArray()) {
			    String[] outputs = (String[])outputName;
				for (String output : outputs) {
					if(output.indexOf("_")>-1) {
						output = output.split("_")[0];
					}
					outputNames.add(output);
				}
			} else {
				String output = outputName.toString();
				if(output.indexOf("_")>-1) {
					output = output.split("_")[0];
				}
				outputNames.add(output);
			}
		}
		return outputNames;
	}
	
	
	/**
	 * 从参数中提取下一步参与者
	 * @param params
	 * @return
	 */
	public static Map<String,Object> getNextAssigner(Map<String,Object> params) {
		Map<String,Object> nextAssignerMap = new HashMap<String, Object>();
		if(null == params || params.size()<1) {
			return nextAssignerMap;
		}
		String nextAssigner = StringUtils.handleNull(params.get("nextAssigners"));
		if(StringUtils.isEmpty(nextAssigner)) {
			return nextAssignerMap;
		}
		String[] nextAssigners = nextAssigner.split(";");
		for (String content : nextAssigners) {
			String taskKey = content.replaceAll("(.*)\\((.*)\\)", "$1");
			String assigner = content.replaceAll("(.*)\\((.*)\\)", "$2");
			if(StringUtils.isNotEmpty(taskKey) && StringUtils.isNotEmpty(assigner)) {
				String[] assigners = ArrayUtils.stringToArray(assigner, ",");
				nextAssignerMap.put(taskKey, Arrays.asList(assigners));
			}
		}
		nextAssigners = null;
		return nextAssignerMap;
	}
	
	
	/**
	 * 从参数中提取下一步参与者
	 * @param params
	 * @return
	 */
	public static List<String> getSelectNextTaskKey(Map<String,Object> params) {
		List<String> selectNextTaskKeys = new ArrayList<String>();
		if(null == params || params.size()<1) {
			return selectNextTaskKeys;
		}
		Object nextLineName = params.get("nextLineName");
		if(null == nextLineName) {
			return selectNextTaskKeys;
		}
		Object[] nextLineNames = null;
		if(nextLineName.getClass().isArray()) {
			nextLineNames = (Object[]) nextLineName;
		} else {
			nextLineNames = new Object[]{nextLineName};
		}
		
		for (Object obj : nextLineNames) {
			String content = obj.toString();
			String taskKey = content.replaceAll("path\\d+_(.*)", "$1");
			if(StringUtils.isNotEmpty(taskKey)) {
				selectNextTaskKeys.add(taskKey);
			}
		}
		return selectNextTaskKeys.size()>0?selectNextTaskKeys:null;
	}
	
	/**
	 * 节点转换为字符串
	 * @param nodes 节点集合
	 * @return 返回节点字符串；多个节点直接用英文分号“;”隔开
	 */
	public static String nodesToStr(List<TaskModel> nodes) {
		StringBuilder strBuilder = new StringBuilder();
		if(CollectionUtils.isNotEmpty(nodes)) {
			for (NodeModel nodeModel : nodes) {
				strBuilder.append(nodeModel.getDisplayName()+NODE_NAME_SEPARATOR);
			}
			strBuilder.delete(strBuilder.length()-1, strBuilder.length());
		}
		return strBuilder.toString();
	}
	
	/**
	 * 节点字符串集合转换为数组
	 * @param nodeCollection 节点集合
	 * @return 返回节点字符串；多个节点直接用英文分号“;”隔开
	 */
	public static String[] nodeStrToArray(String nodeCollection) {
		String[] array = null;
		if(StringUtils.isNotEmpty(nodeCollection)) {
			array = ArrayUtils.stringToArray(nodeCollection, NODE_NAME_SEPARATOR);
		}
		return array;
	}
	
	/**
	 * 获取流程的所有任务节点
	 * @param list
	 * @return
	 */
	public static Map<String, String[]> getProcessNodes(List<ExtProcess> list) {
		Map<String, String[]> nodeMaps = new HashMap<String, String[]>();
		if(CollectionUtils.isNotEmpty(list)) {
			for (ExtProcess process : list) {
				List<TaskModel> taskModels = process.getModel().getTaskModels();
				if(CollectionUtils.isNotEmpty(taskModels)) {
					int taskSize = taskModels.size();
					String[] nodeKeys = new String[taskSize];
					for (int i = 0; i < taskSize; i++) {
						nodeKeys[i] = taskModels.get(i).getName();
					}
					nodeMaps.put(process.getId(), nodeKeys);
				}
			}//for
		}
		return nodeMaps;
	}
	
	/**
	 * 处理跳转节点
	 * @param pf
	 * @param models
	 * @param orderId
	 * @param currentTaskKey
	 * @return
	 */
	public static List<JumpNodeInfo> handleJumpNodes(ProcessFacade pf,List<NodeModel> models, 
			String orderId,String currentTaskKey) {
		List<JumpNodeInfo> jumpNodeInfos = null;
		if(CollectionUtils.isEmpty(models)) {
			throw new NullArgumentException("models参数为空");
		}
		jumpNodeInfos = new ArrayList<JumpNodeInfo>();
		 //是否需要选择处理者
		boolean isBefore = true;
		for (NodeModel nodeModel : models) {
			if(StringUtils.isEmpty(currentTaskKey)) {
				isBefore = false;
				combinJumpNodes(pf, nodeModel, null, isBefore, jumpNodeInfos);
			} else if(nodeModel.getName().equals(currentTaskKey)) {
				isBefore = false;
			} else if((nodeModel instanceof TaskModel || nodeModel instanceof EndModel)) {
				combinJumpNodes(pf, nodeModel, orderId, isBefore, jumpNodeInfos);
			}
		}//for
		return jumpNodeInfos;
	}
	
	/**
	 * 组合跳转节点
	 * @param pf
	 * @param nodeModel
	 * @param orderId
	 * @param isBefore
	 * @param jumpNodeInfos
	 */
	private static void combinJumpNodes(ProcessFacade pf, NodeModel nodeModel, 
			String orderId, boolean isBefore, List<JumpNodeInfo> jumpNodeInfos) {
		YesNoType isBack = YesNoType.NO;
		YesNoType isSelectAssigner = YesNoType.NO;
		String selectStyle = null;
		if(nodeModel instanceof ExtTaskModel) {
			ExtTaskModel taskModel = (ExtTaskModel)nodeModel;
			if(StringUtils.isNotEmpty(orderId)) {
				List<Object> lists = pf.getHisTasks(orderId, taskModel.getName());
				if(CollectionUtils.isNotEmpty(lists) && isBefore) {
					isBack = YesNoType.YES;
				}
			}
			isSelectAssigner = YesNoType.getObjByStrValue(taskModel.getIsExeAssigner());
			isSelectAssigner = (null == isSelectAssigner)?YesNoType.NO:isSelectAssigner;
			selectStyle = StringUtils.handleNull(taskModel.getSelectAssignerStyle());
		}
		JumpNodeInfo jumpNodeInfo = new JumpNodeInfo(nodeModel.getDisplayName(), nodeModel.getName(), isBack.getIndex());
		jumpNodeInfo.setIsSelectAssigner(isSelectAssigner.getIndex());
		jumpNodeInfo.setSelectStyle(selectStyle);
		jumpNodeInfos.add(jumpNodeInfo);
	}

	/**
	 * 根据参数初始化流程名称或ID；
	 * 当流程名称不为空时，通过流程名称获取流程Id（获取最新的流程）
	 * @param facets
	 * @param taskInfo
	 */
	public static void initProcessNameOrId(SnakerEngineFacets facets, TaskInfo taskInfo) {
		if(StringUtils.isNotEmpty(taskInfo.getProcessName()) && StringUtils.isEmpty(taskInfo.getProcessId())) {
			Process process = facets.getEngine().process().getProcessByName(taskInfo.getProcessName());
			taskInfo.setProcessId(process.getId());
		} else if(StringUtils.isNotEmpty(taskInfo.getProcessId()) && StringUtils.isEmpty(taskInfo.getProcessName())){
			Process process = facets.getEngine().process().getProcessById(taskInfo.getProcessId());
			taskInfo.setProcessName(process.getName());
		}
	}
}
