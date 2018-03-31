/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;

import cn.com.smart.web.bean.entity.TNMenu;

/**
 * 菜单Dao接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IMenuDao {

	/**
	 * 获取所有菜单数据
	 * @return 返回数组对象集合
	 */
	public List<Object> queryObjAll();
	
	/**
	 * 获取所有有效菜单
	 * @return 返回菜单实体对象集合
	 */
	public List<TNMenu> getValidAll();
	
	/**
	 * 通过角色获取菜单
	 * @param roleIds 角色ID集合
	 * @return 返回菜单实体对象集合
	 */
	public List<TNMenu> queryMenus(List<String> roleIds);
	
	/**
	 * 通过资源ID获取菜单ID
	 * @param resourceIds 资源ID集合
	 * @return 返回数组对象集合
	 */
	public List<Object> queryMenuIdByResourceId(String[] resourceIds);
	
}
