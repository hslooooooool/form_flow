package cn.com.smart.flow.ext;

import org.snaker.engine.model.TaskModel;

/**
 * 自定义任务模型
 * @author lmq
 */
public class ExtTaskModel extends TaskModel {

	private static final long serialVersionUID = -4449582254107329760L;
	
	private String assigneeDisplay;
	
	/**
	 * 是否有附件
	 * 1--有
	 * 0--无
	 */
	private String taskAttachment;
	
	private String formPropIds;
	
	/**
	 * 是否并发
	 */
	private String isConcurrent;

	private String isExeAssigner;
	
	private String selectAssignerStyle;
	
	private String isDepartFilter;
	
	private String isSug;
	
	/**
	 * 是否支持打印功能
	 */
	private String isPrint;
	
	/**
	 * 是否需要领取任务
	 * 1--需要领取；0--不领取
	 */
	private String isTakeTask;
	
    public String getAssigneeDisplay() {
        return assigneeDisplay;
    }

    public void setAssigneeDisplay(String assigneeDisplay) {
        this.assigneeDisplay = assigneeDisplay;
    }

	public String getTaskAttachment() {
		return taskAttachment;
	}

	public void setTaskAttachment(String taskAttachment) {
		this.taskAttachment = taskAttachment;
	}

	public String getFormPropIds() {
		return formPropIds;
	}

	public void setFormPropIds(String formPropIds) {
		this.formPropIds = formPropIds;
	}

	public String getIsSug() {
		return isSug;
	}

	public void setIsSug(String isSug) {
		this.isSug = isSug;
	}

	public String getIsTakeTask() {
		return isTakeTask;
	}

	public void setIsTakeTask(String isTakeTask) {
		this.isTakeTask = isTakeTask;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getIsExeAssigner() {
		return isExeAssigner;
	}

	public void setIsExeAssigner(String isExeAssigner) {
		this.isExeAssigner = isExeAssigner;
	}

	public String getSelectAssignerStyle() {
		return selectAssignerStyle;
	}

	public void setSelectAssignerStyle(String selectAssignerStyle) {
		this.selectAssignerStyle = selectAssignerStyle;
	}

	public String getIsDepartFilter() {
		return isDepartFilter;
	}

	public void setIsDepartFilter(String isDepartFilter) {
		this.isDepartFilter = isDepartFilter;
	}

	public String getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}

}
