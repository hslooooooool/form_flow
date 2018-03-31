package cn.com.smart.flow.sort;


/**
 * 分类排序
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年12月21日
 * @param <E>
 */
public class SortClassifyBean<E>{

	private String name;
	
	private int sort;
	
	private E entity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}
	
}
