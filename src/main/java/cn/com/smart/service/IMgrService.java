package cn.com.smart.service;

import java.util.List;
import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.SmartResponse;
import cn.com.smart.constant.IConstant;
import cn.com.smart.exception.ServiceException;

/**
 * 管理服务类接口
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
public interface IMgrService<T extends BaseBean> extends IBaseEntityService<T> {
	
	/**
	 * 保存数据
	 * @param t 实体bean
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getData()方法获取主键ID值
	 */
	public SmartResponse<String> save(T t) throws ServiceException;
	
	/**
	 * 保存数据
	 * @param bean 实体bean
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getData()方法获取主键ID值
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(BaseBeanImpl bean) throws ServiceException;
	
	/**
	 * 批量保存数据
	 * @param ts 实体bean集合
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取主键ID值
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(List<T> ts) throws ServiceException;
	
	/**
	 * 删除数据
	 * @param id 主键ID
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 */
	public SmartResponse<String> delete(String id) throws ServiceException;
	
	/**
	 * 更新数据
	 * @param t 实体bean
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 */
	public SmartResponse<String> update(T t) throws ServiceException;
	
	/**
	 * 更新数据
	 * @param bean 实体bean
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 */
	public SmartResponse<String> update(BaseBeanImpl bean) throws ServiceException;
	
	/**
	 * 批量更新数据
	 * @param ts 实体bean集合
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 * @throws ServiceException
	 */
	public SmartResponse<String> update(List<T> ts) throws ServiceException;
	
	/**
	 * 执行<code>resId</code>指定的SQL语句；
	 * 多个语句直接用 {@link IConstant#MULTI_STATEMENT_SPLIT} 常量指定的符号分隔
	 * @param resId 资源ID
	 * @param params 参数
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 * @throws ServiceException
	 */
	public SmartResponse<String> execute(String resId,Map<String,Object> params) throws ServiceException;
	
	/**
	 * 执行<code>resId</code>指定的SQL语句；
	 * 多个语句直接用 {@link IConstant#MULTI_STATEMENT_SPLIT} 常量定义的符号分隔
	 * @param resId 资源ID
	 * @param id 条件ID；多个ID直接用 {@link IConstant#MULTI_VALUE_SPLIT} 常量定义的符号分隔
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败
	 * @throws ServiceException
	 */
	public SmartResponse<String> execute(String resId, String id) throws ServiceException;
}
