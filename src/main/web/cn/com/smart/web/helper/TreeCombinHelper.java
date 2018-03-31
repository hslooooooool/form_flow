package cn.com.smart.web.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.smart.bean.TreeProp;
import cn.com.smart.web.plugins.ZTreeData;

/**
 * 处理组合树形结构时可能会使用到的一些方法
 * @author lmq
 *
 */
@Component
public class TreeCombinHelper {

	/**
	 * 去掉多余的叶子节点(不是父节点并且叶子节点标记非flag的)
	 * @param orgTrees
	 * @param flag
	 * @return
	 */
	public List<TreeProp> trimLeaf(List<TreeProp> orgTrees,String flag) {
		try {
			if(null != orgTrees && orgTrees.size()>0) {
				deleteLeaf(orgTrees,flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orgTrees;
	}

	/**
	 * 删除叶子节点
	 * @param orgTrees
	 * @param flag
	 */
	private void deleteLeaf(List<TreeProp> orgTrees,String flag) {
		boolean is = false;
		for (TreeProp treeProp : orgTrees) {
			if(!treeProp.getFlag().equals(flag) && !isParent(orgTrees, treeProp)) {
				orgTrees.remove(treeProp);
				is = true;
				break;
			}
		}
		if(is) deleteLeaf(orgTrees,flag);
	}
	
	/**
	 * 判断是否为父节点
	 * @param orgUsers
	 * @param treeProp
	 * @return
	 */
	protected boolean isParent(List<TreeProp> treeProps,TreeProp treeProp) {
		boolean is = false;
		for (TreeProp treePropTmp : treeProps) {
			if(treePropTmp.getParentId().equals(treeProp.getId())) {
				is = true;
				break;
			}
		}
		return is;
	}
	
	
	
	/**
	 * 去掉多余的叶子节点
	 * @param orgTrees
	 * @param flag
	 * @return
	 */
	public List<?> trimLeaf(List<? extends ZTreeData> ztreeDatas) {
		try {
			if(null != ztreeDatas && ztreeDatas.size()>0) {
				deleteLeaf(ztreeDatas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ztreeDatas;
	}

	/**
	 * 删除叶子节点
	 * @param ztreeDatas
	 */
	private void deleteLeaf(List<? extends ZTreeData> ztreeDatas) {
		boolean is = false;
		for (ZTreeData treeData : ztreeDatas) {
			if(!treeData.getFlag().equals(treeData.getCheckFlag()) && !isParent(ztreeDatas, treeData)) {
				ztreeDatas.remove(treeData);
				is = true;
				break;
			}
		}
		if(is) deleteLeaf(ztreeDatas);
	}
	
	/**
	 * 判断是否为父节点
	 * @param treeDatas
	 * @param treeData
	 * @return
	 */
	protected boolean isParent(List<? extends ZTreeData> treeDatas,ZTreeData treeData) {
		boolean is = false;
		for (ZTreeData treeDataTmp : treeDatas) {
			if(treeDataTmp.getpId().equals(treeData.getId())) {
				is = true;
				break;
			}
		}
		return is;
	}
	
}
