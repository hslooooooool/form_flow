package cn.com.smart.service;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.OPDao;
import cn.com.smart.exception.ServiceException;

/**
 * 基础服务类接口
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
public interface IBaseService {
	
	
	
	/**
	 * 获取所有数据
	 * @param clazz 实体Bean类
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断方法是否执行成功 <br />
	 * “1”--表示成功，否则表示失败
	 * 如果成功，通过getDatas()方法获取数据
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findAll(Class<?> clazz) throws ServiceException;
	
	/**
	 * 查询数据
	 * @param clasz 对象实体Bean
	 * @param id 主键ID
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断方法是否执行成功 <br />
	 * “1”--表示成功，否则表示失败
	 * 如果成功，通过getData()方法获取数据
	 * @throws ServiceException
	 */
	public SmartResponse<Object> find(Class<?> clasz,String id) throws ServiceException;
	
	/**
	 * 计算总页数
     * @param total 总数据数
     * @param pageSize 每页显示数
     * @return 返回总的页数
	 */
	public int getTotalPage(long total,int pageSize);
	
	/**
	 * 通用保存方法
	 * @param bean 
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断方法是否执行成功 <br />
	 * “1”--表示成功，否则表示失败
	 * 如果成功，通过getData()方法获取数据
	 */
	public SmartResponse<String> save(BaseBeanImpl bean) throws ServiceException;
	
	/**
	 * 通用更新方法
	 * @param bean
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断方法是否执行成功 <br />
	 * “1”--表示成功，否则表示失败
	 * 如果成功，通过getData()方法获取数据
	 */
	public SmartResponse<String> update(BaseBeanImpl bean) throws ServiceException;
	
	/**
	 * 获取OPDao对象
	 * @return 返回对象
	 */
	public OPDao getOPDao();
	
}
