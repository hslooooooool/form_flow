package cn.com.smart.bean;

import java.util.List;

import cn.com.smart.constant.IConstant;
import cn.com.smart.init.config.InitSysConfig;
import cn.com.smart.web.constant.IActionConstant;

/**
 * 执行结果--统一的返回结果bean
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 * 
 * @param <T>
 */
public class SmartResponse<T> {
	
	private String result = IConstant.OP_FAIL;
	
	private String msg = IConstant.OP_FAIL_MSG;
	
	private List<T> datas;
	
	private T data;
	
	private int totalPage;
	
	private long totalNum;
	
	private int perPageSize;
	
	private int size;
	
	public SmartResponse() {}
	
	public SmartResponse(String msg) {
	    this.msg = msg;
	}

    /**
	 * 操作结果
	 * @see cn.com.smart.web.constant.IWebConstant
	 * @return <p>结果码 </p>
	 * 结果码，详情{@link cn.com.smart.web.constant.IWebConstant} 接口类
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置操作结果码
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 获取提示信息
	 * @return 提示信息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置提示信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 返回总页数（分页）
	 * @return 总页数 <br />
	 * 当结果集分页时，返回总页数，否则返回0
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置总页数，默认为0
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 获取本次返回的数据长度
	 * @return 长度，默认为0
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设置本次返回的数据长度
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 总数量(分页)
	 * @return 总数量(总记录数)
	 */
	public long getTotalNum() {
		return totalNum;
	}

	/**
	 * 设置总记录数
	 * @param totalNum
	 */
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * 返回T类型的数据集
	 * @return 返回数据集
	 */ 
	public List<T> getDatas() {
		return datas;
	}

	/**
	 * 设置数据集
	 * @param datas
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	/**
	 * 获取返回对象，当通过唯一条件查询时
	 * @return 返回一个T类的对象
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置一个T类型的对象
	 */
	public void setData(T data) {
		this.data = data;
	}

	public int getPerPageSize() {
    	try {
			perPageSize = Integer.parseInt(InitSysConfig.getInstance().getValue("page.per.size"));
		} catch (Exception e) {
			perPageSize = IActionConstant.PRE_PAGE_SIZE;
		}
		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

}
