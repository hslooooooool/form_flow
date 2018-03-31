/**
 * 
 */
package cn.com.smart.flow;

import cn.com.smart.flow.bean.SubmitFormData;

/**
 * 流程执行接口
 * @author lmq
 * @since 1.0
 */
public interface IProcessExecuteAware {

	/**
	 * 任务提交完后执行的方法(后置方法)
	 * @param formData 表单数据
	 * @param userId 用户ID
	 * @param orgId 当前用户所在的组织机构ID
	 */
	public void taskExeAfter(SubmitFormData formData,String userId,String orgId);
	
}
