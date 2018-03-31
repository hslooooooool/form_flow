package cn.com.smart.flow.bean;

import java.util.Map;

/**
 * 提交流程表单数据bean
 * @author lmq
 * @create 2015年6月4日
 * @version 1.0 
 * @since 
 *
 */
public class SubmitFormData extends TaskInfo {

    private Map<String,Object> params;
	
	private Integer formState=0;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getFormState() {
		return formState;
	}

	public void setFormState(Integer formState) {
		this.formState = formState;
	}
	
	
}
