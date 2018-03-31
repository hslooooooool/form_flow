package cn.com.smart.flow.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.mixsmart.utils.StringUtils;


/**
 * @author lmq
 * @create 2015年6月22日
 * @version 1.0 
 * @since 
 *
 */
public class TaskInfo {

	/**
	 * 流程ID
	 */
    protected String processId;
    
    /**
     * 流程名称
     */
    protected String processName;
	
    /**
     * 流程实例ID
     */
    protected String orderId;
	
    /**
     * 任务ID
     */
    protected String taskId;
    
    /**
     * 任务KEY
     */
    protected String taskKey;
    
    /**
     * 任务名称
     */
    protected String taskName;
    
    /**
     * 表单ID
     */
    protected String formId;
	
   /**
    * 表单数据ID
    */
    protected String formDataId;
    
    /**
     * 是否驳回
     */
    protected String isBack;
    
    protected String refreshUrl;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFormDataId() {
		return formDataId;
	}

	public void setFormDataId(String formDataId) {
		this.formDataId = formDataId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getRefreshUrl() {
		if(StringUtils.isNotEmpty(refreshUrl)) {
			try {
				refreshUrl = URLDecoder.decode(refreshUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return refreshUrl;
	}

	public void setRefreshUrl(String refreshUrl) {
		this.refreshUrl = refreshUrl;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
    
}
