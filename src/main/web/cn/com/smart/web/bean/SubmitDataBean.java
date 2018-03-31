package cn.com.smart.web.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.smart.bean.TreeProp;

/**
 * 提交数据的对象;主要是用于提交树形属性的数据
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class SubmitDataBean {

	private List<TreeProp> treeProps;

	/**
	 * 获取树形属性对象集合
	 * @return 返回树形结构集合
	 */
	public List<TreeProp> getTreeProps() {
		return treeProps;
	}

	/**
	 * 设置树形属性的对象集合
	 * @param treeProps 设置树形集合
	 */
	public void setTreeProps(List<TreeProp> treeProps) {
		this.treeProps = treeProps;
	}
	
}
