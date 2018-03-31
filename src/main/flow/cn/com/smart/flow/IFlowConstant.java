package cn.com.smart.flow;

/**
 * 流程常量定义
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
public interface IFlowConstant {

	/**
	 * 流程变量字段标识符
	 */
	public static final String FLOW_VAR_FIELD_FLAG = "1";
	
	/**
	 * 流程线类型--正常
	 */
	public static final String FLOW_PATH_TYPE_NORMAL = "normal";
	
	/**
	 * 流程线类型--驳回
	 */
	public static final String FLOW_PATH_TYPE_BACK = "back";
	
	/**
	 * 起草人处理
	 */
	public static final String FLOW_CREATE_USER = "create_user";
	
	/**
	 * 处理意见
	 */
	public static final String FLOW_HANDLE_SUGGEST = "handleSuggest";
	
}
