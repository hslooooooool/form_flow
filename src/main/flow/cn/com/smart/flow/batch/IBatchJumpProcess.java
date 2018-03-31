package cn.com.smart.flow.batch;

import java.util.List;

/**
 * 批量跳转流程
 * @author lmq <br />
 * 2016年11月8日
 * @version 1.0
 * @since 1.0
 */
public interface IBatchJumpProcess {

	/**
	 * 名称
	 * @return 返回处理者的名称
	 */
	public String getName();
	
	/**
	 * 根据提供的参数，返回内容，因为是批量处理；所以返回值为一个集合.
	 * <p>
	 * 如：批量内容中是流程实例名，实现类就通过流程实例名称获取流程实例ID；并返回流程实例ID集合.
	 * </p>
	 * @param batchContent 批量参数
	 * @return 返回处理后的列表
	 */
	public <E> List<E> build(String batchContent);
	
}
