package cn.com.smart.form;

/**
 * 表处理类型 <br />
 * 定义一些表处理时候用到的一些常量
 * @author lmq
 * @create 2015年6月29日
 * @version 1.0 
 * @since 
 *
 */
public interface TableHandleType {

	/**
	 * 表字段处理时--新字段的标识符
	 */
	public static final String TABLE_FIELD_NEW_FLAG = "newFieldFlag";
	
	/**
	 * 表字段处理时--删除字段的标识符
	 */
	public static final String TABLE_FIELD_DEL_FLAG = "delFieldFlag";
	
	/**
	 * 表字段处理时--更新字段的标识符
	 */
	public static final String TABLE_FIELD_UPDATE_FLAG = "updateFieldFlag";
	
}
