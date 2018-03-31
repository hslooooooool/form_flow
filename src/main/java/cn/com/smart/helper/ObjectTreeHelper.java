package cn.com.smart.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 *
 */
@Component
public class ObjectTreeHelper {

	/**
	 * 输出树
	 * 
	 * @param lists
	 * @param obj
	 *            字段顺序必须为[id,parentId,seqNum,.......]
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> outPutTree(List<Object> lists, Object obj)
			throws Exception {
		List<Object> trees = null;
		if (null != lists && lists.size() > 0 && null != obj) {
			trees = new ArrayList<Object>();
			Stack<Object> s = new Stack<Object>();
			s.push(obj);
			int count = 0;
			while (!s.isEmpty()) {
				Object objTmp = s.pop();
				if (count > 0) {
					trees.add(objTmp);
				}
				s.addAll(getChild(lists, objTmp));
				count++;
			}
		}
		return trees;
	}
	
	/**
	 * 输出树
	 * 
	 * @param lists
	 * @param obj 字段顺序必须为[id,parentId,seqNum,.......]
	 * @param isSelf
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> outPutTree(List<Object> lists, Object obj,boolean isSelf)	throws Exception {
		List<Object> trees = null;
		if (null != lists && lists.size() > 0 && null != obj) {
			trees = new ArrayList<Object>();
			Stack<Object> s = new Stack<Object>();
			int count = 0;
			if(isSelf) {
				for (Object tt : lists) {
					if (((Object[])tt)[0].toString().equals(((Object[])obj)[0].toString())) {
						obj = tt;
						count = 1;
						break;
					}
				}
			}
			s.push(obj);
			while (!s.isEmpty()) {
				Object tt = s.pop();
				if (count>0) {
					trees.add(tt);
				}
				s.addAll(getChild(lists, tt));
				count++;
			}
		}
		return trees;
	}

	/**
	 * 获取子树
	 * @param lists
	 * @param obj
	 * @return List<Object>
	 */
	protected List<Object> getChild(List<Object> lists, Object obj) {
		List<Object> childs = new ArrayList<Object>();
		for (Object objTmp : lists) {
			if (StringUtils.handleNull(((Object[]) objTmp)[1]).equals(
					StringUtils.handleNull(((Object[]) obj)[0]))) {
				// 此处保证集合中最后一个元素是需要显示在当前层级中第一个展示的子节点（因为堆栈中是最后一个元素先出）
				if (childs != null
						&& childs.size() != 0
						&& Double.parseDouble(StringUtils.handleNumNull(((Object[]) objTmp)[2])) >= Double.parseDouble(StringUtils.handleNumNull(((Object[]) childs.get(0))[2]))) {
					childs.add(0, objTmp);
				} else {
					childs.add(objTmp);
				}
			}
		}
		return childs;
	}
	
	
	/**
	 * 输出树形结构
	 * @param lists
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> outPutTree(List<Object> lists) throws Exception {
		List<Object> trees = null;
		if (null != lists && lists.size() > 0) {
			trees = new ArrayList<Object>();
			Set<Object> sets = getRoot(lists);
			//set转换为list
			List<Object> roots = new ArrayList<Object>(sets);
			//list倒序
			Collections.reverse(roots);
			Stack<Object> s = new Stack<Object>();
			s.addAll(roots);
			while (!s.isEmpty()) {
				Object objTmp = s.pop();
				trees.add(objTmp);
				s.addAll(getChild(lists, objTmp));
			}
		}
		return trees;
	}
	
	/**
	 * 获取根节点
	 * @param ts
	 * @return TreeSet<Object>
	 */
	protected TreeSet<Object> getRoot(List<Object> ts) {
		TreeSet<Object> roots = null;
		if(null != ts && ts.size()>0) {
			roots = new TreeSet<Object>(new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					int compare = StringUtils.handObj2Integer(((Object[])o1)[2]).compareTo(StringUtils.handObj2Integer(((Object[])o2)[2]));
					if(compare==0) {
						if(StringUtils.handleNull(((Object[])o1)[0]).equals(StringUtils.handleNull(((Object[])o2)[0]))) {
							compare = 0; 
						} else {
							compare = 1;
						}
					}
					return compare;
				}
			});
			for (Object t : ts) {
				if(null == getParent(ts, t)) {
					roots.add(t);
				}
			}
		}
		return roots;
	}
	
	/**
	 * 获取父节点
	 * @param ts
	 * @param t
	 * @return Object
	 */
	protected Object getParent(List<Object> ts, Object t) {
		Object tmp = null;
		/*if("0".equals(t.getId()) || "0".equals(t.getParentId().toString())) {
			tmp = null;
		}*/
		for (Object tt : ts) {
			if (StringUtils.handleNull(((Object[])tt)[0]).equals(StringUtils.handleNull(((Object[])t)[1]))) {
				tmp = tt;
			}
		}
		return tmp;
	}

}
