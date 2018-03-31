/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;
import java.util.Map;

import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNRoleResource;

/**
 * 角色资源Dao接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IRoleResourceDao {

	/**
	 * 通过角色ID获取资源操作权限映射数据
	 * 返回Map中key的值标识资源ID
	 * @param roleId
	 * @return 返回Map对象
	 */
	public Map<String,List<TNOPAuth>> queryByRole(String roleId);
	
	/**
	 * 通过角色ID和资源uri获取角色资源
	 * @param uri
	 * @param roleIds
	 * @return 返回资源实体对象集合
	 */
	public List<TNRoleResource> queryByUriRoles(String uri,List<String> roleIds);
}
