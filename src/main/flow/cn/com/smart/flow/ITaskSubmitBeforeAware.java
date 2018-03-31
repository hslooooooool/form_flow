/**
 * 
 */
package cn.com.smart.flow;

import cn.com.smart.flow.bean.TaskBefore;

/**
 * 提交任务，任务未执行之前--拦截执行的类接口
 * @author lmq
 * @since 1.0
 */
public interface ITaskSubmitBeforeAware {

	/**
	 * 任务提交前执行的方法(前置方法)
	 * @param taskBefore 任务前置参数
	 * @return 返回值;成功返回true；失败返回:false <br />
	 * 当返回值为：false时，流程不在往下执行
	 */
	public boolean taskExeBefore(TaskBefore taskBefore);
	
}
