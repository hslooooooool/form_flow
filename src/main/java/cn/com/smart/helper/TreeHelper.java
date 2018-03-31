package cn.com.smart.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import cn.com.smart.bean.BaseTree;

/**
 * 树形结构帮助类 <br />
 * 实现了BaseTree接口的Bean集合;<br />
 * 通过该类，可以得到树形结构的Bean集合
 * 
 * @author lmq
 *
 * @param <T>
 */
public class TreeHelper<T extends BaseTree> {
	
	/**
	 * 输出一个树形结构的list
	 * @param ts
	 * @param t
	 * @param isSelf 输出的list中是否包含t
	 * @return List
	 * @throws Exception
	 */
	public List<T> outPutTree(List<T> ts, T t,boolean isSelf) throws Exception {
		List<T> trees = null; 
		if (null != ts && ts.size() > 0 && null != t) {
			trees = new ArrayList<T>();
			
			//获取子树
			Stack<T> childStack = new Stack<T>();
			int count = 0;
			if(isSelf) {
				for (T tt : ts) {
					if (tt.getId().toString().equals(t.getId().toString())) {
						t = tt;
						count = 1;
						break;
					}
				}
			}
			childStack.push(t);
			while (!childStack.isEmpty()) {
				T tt = childStack.pop();
				if (count>0) {
					trees.add(tt);
				}
				childStack.addAll(getChild(ts, tt));
				count++;
			}
		}
		return trees;
	}
	
	
	/**
	 * 输出一个树形结构的list
	 * @param ts
	 * @return List<T>
	 * @throws Exception
	 */
	public List<T> outPutTree(List<T> ts) throws Exception {
		List<T> trees = null; 
		if (null != ts && ts.size() > 0) {
			trees = new ArrayList<T>();
			Set<T> sets = getRoot(ts);
			//set转换为list
			List<T> lists = new ArrayList<T>(sets);
			//list倒序
			Collections.reverse(lists);
			Stack<T> childStack = new Stack<T>();
			childStack.addAll(lists);
			while (!childStack.isEmpty()) {
				T tt = childStack.pop();
				//判断是否存在
				if(!isExist(trees, tt)) {
					trees.add(tt);
				}
				childStack.addAll(getChild(ts, tt));
			}
		}
		return trees;
	}
	
	/**
	 * 判断是否存在
	 * @param lists
	 * @param t
	 * @return Boolean
	 */
	protected boolean isExist(List<T> lists,T t) {
		boolean is = false;
		for (T tt : lists) {
			if(tt.getId().equals(t.getId())) {
				is = true;
				break;
			}
		}
		return is;
	}
	
	/**
	 * 获取根节点
	 * @param ts
	 * @return TreeSet<T>
	 */
	protected TreeSet<T> getRoot(List<T> ts) {
		TreeSet<T> roots = null;
		if(null != ts && ts.size()>0) {
			roots = new TreeSet<T>(new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					int compare = o1.getSortOrder().compareTo(o2.getSortOrder());
					if(compare==0) {
						if(o1.getId().equals(o2.getId())) {
							compare = 0; 
						} else {
							compare = 1;
						}
					}
					return compare;
				}
			});
			for (T t : ts) {
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
	 * @return T
	 */
	protected T getParent(List<T> ts, T t) {
		T tmp = null;
		for (T tt : ts) {
			if (tt.getId().toString().equals(t.getParentId().toString())) {
				tmp = tt;
			}
		}
		return tmp;
	}
	
	/**
	 * 获取子节点
	 * @param ts
	 * @param t
	 * @return List<T>
	 */
	protected List<T> getChild(List<T> ts, T t) {
		List<T> childs = new ArrayList<T>();
		for (T tt : ts) {
			if (tt.getParentId().toString().equals(t.getId().toString())) {
				// 此处保证集合中最后一个元素是需要显示在当前层级中第一个展示的子节点（因为堆栈中是最后一个元素先出）
				if (!childs.isEmpty() && (tt.getSortOrder().intValue() >= childs.get(0).getSortOrder().intValue())) {
					childs.add(0, tt);
				} else {
					childs.add(tt);
				}
			}
		}
		return childs;
	}
	
}
