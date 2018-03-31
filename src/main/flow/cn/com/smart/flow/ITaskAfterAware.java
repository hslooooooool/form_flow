package cn.com.smart.flow;

import cn.com.smart.flow.bean.SubmitFormData;

/**
 * 任务处理结束后，需要后续处理的接口
 * @author lmq  2017年3月29日
 * @version 1.0
 * @since 1.0
 */
public interface ITaskAfterAware {

	/**
	 * 后续继续执行的方法;
	 * 注：该方法与流程任务处理的方法不在同一事物中
	 * @param submitFormData
	 */
	void execute(SubmitFormData submitFormData);
	
	/**
	 * 流程名称数组（如果设置了流程名称，则实现了该接口的实现类，则只处理指定流程）
	 * @return 返回流程名称
	 */
	String[] getProcessName();
	
	/**
	 * 节点名称数组（即：流程设计器上显示的节点名称）
	 * @return
	 */
	String[] getNodeName();
	
}
