/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;

import cn.com.smart.web.bean.entity.TNRoleMenu;

/**
 * 角色菜单接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IRoleMenuDao {

	/**
	 * 获取角色菜单关联数据
	 * @param roleId
	 * @return 返回角色菜单实体对象集合
	 */
	public List<TNRoleMenu> queryByRole(String roleId);
	
	/**
	 * 判断角色菜单是否已关联
	 * @param roleId
	 * @param menuId
	 * @return 返回true或false <br />
	 * 如已经关联则返回：true；否则返回：false
	 */
	public boolean isRoleMenuCombinExist(String roleId,String menuId);
	
}
