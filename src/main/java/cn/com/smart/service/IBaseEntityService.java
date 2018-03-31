package cn.com.smart.service;

import java.util.Map;

import cn.com.smart.bean.BaseBean;
import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.filter.bean.FilterParam;

/**
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
public interface IBaseEntityService<T extends BaseBean> extends IBaseService {

	/**
	 * 获取所有数据
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findAll();
	
	/**
	 * 通过参数查询
	 * @param param 参数
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(Map<String,Object> param);
	
	/**
	 * 通过参数查询
	 * @param param 参数
	 * @param orderBy 排序  <br />
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(Map<String,Object> param, String orderBy);
	
	/**
	 * 通过参数查询（分页）
	 * @param param 参数
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @param orderBy 排序 <br />
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(Map<String,Object> param, int page, int pageSize);
	
	/**
	 * 通过参数查询（分页）
	 * @param param 参数
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(Map<String,Object> param, int page, int pageSize,String orderBy);
	
	/**
	 * 通过参数查询
	 * @param param 参数
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(FilterParam param);
	
	/**
	 * 通过参数查询
	 * @param param 参数
	 * @param orderBy 排序  <br />
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 返回SmartResponse对象
	 * 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(FilterParam param,String orderBy);
	
	/**
	 * 通过参数查询（分页）
	 * @param param 参数
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @return 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(FilterParam param, int page, int pageSize);
	
	/**
	 * 通过参数查询（分页）
	 * @param param 参数
	 * @param page 当前页
	 * @param pageSize 每页显示数
	 * @param orderBy 排序  <br />
	 * 如：按排序字段倒序( sortOrder asc)
	 * @return 通过调用getResult()判断是否成功 <br />
	 * 等于“1”表示成功；否则失败 <br />
	 * 如果成功通过调用getDatas()方法获取数据
	 */
	public SmartResponse<T> findByParam(FilterParam param, int page, int pageSize, String orderBy);
	
	/**
	 * 通过主键ID查询实体对象
	 * @param id　主键ID
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断是否执行成功 <br/>
	 * 等于“１”表示成功，否则表示失败　<br />
	 * 如果成功通过getData()方法获取数据
	 */
	public SmartResponse<T> find(String id);
	
	/**
	 * 通过主键查询数据
	 * @param id 主键ID数组
	 * @return 返回SmartResponse对象 <br />
	 * 通过getResult()方法判断方法是否执行成功 <br />
	 * “1”--表示成功，否则表示失败
	 * 如果成功，通过getDatas()方法获取数据
	 * @throws ServiceException
	 */
	public SmartResponse<T> finds(String[] id) throws ServiceException;
	
	
	/**
	 * 获取DAO
	 * @return 获取DAO实例
	 */
	public BaseDaoImpl<T> getDao();
	
}
