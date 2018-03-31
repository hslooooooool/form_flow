package cn.com.smart.web.utils;

import java.util.ArrayList;
import java.util.List;

import cn.com.smart.bean.TreeProp;
import cn.com.smart.web.bean.entity.TNOrg;

/**
 * 
 * @author lmq
 * @version 1.0 2015年10月7日
 * @since 1.0
 *
 */
public class TreeUtil {

	/**
	 * 组织机构树转化为TreeProp对象树
	 * @param orgTrees 组织机构实体树列表
	 * @return　返回转换后的TreeProp列表
	 */
	public static List<TreeProp> Org2TreeProp(List<TNOrg> orgTrees) {
		List<TreeProp> treeProps = null;
		if(null != orgTrees && orgTrees.size()>0) {
			treeProps = new ArrayList<TreeProp>();
			TreeProp treeProp = null;
			for (TNOrg org : orgTrees) {
				treeProp = new TreeProp();
				treeProp.setId(org.getId());
				treeProp.setName(org.getName());
				treeProp.setParentId(org.getParentId());
				treeProp.setSortOrder(org.getSortOrder());
				treeProp.setFlag(org.getType());
				treeProps.add(treeProp);
			}
			treeProp = null;
		}
		orgTrees = null;
		return treeProps;
	}
	
}
