package cn.com.smart.web.tag.bean;

/**
 * 自定义单元格回调函数接口
 * @author lmq <br />
 * 2017年2月21日
 * @version 1.0
 * @since 1.0
 */
public interface ICustomCellCallback {

	/**
	 * 回调方法
	 * @param objArray 单元格数组数据
	 * @param row 行数
	 * @param colIndex 单元格下标
	 * @param value 单元格对应的值；如果没有则为null
	 * @return 返回处理后的结果
	 */
	public String callback(Object[] objArray, Integer row ,Integer colIndex, Object value);
	
}
