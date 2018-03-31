package cn.com.smart.flow.bean;

import java.util.List;

/**
 * 数据分类
 * @author lmq
 * @create 2015年7月14日
 * @version 1.0 
 * @since 
 *
 */
public class DataClassify<T> {

	private String id;
	
	private String name;
	
	private List<T> datas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
}
