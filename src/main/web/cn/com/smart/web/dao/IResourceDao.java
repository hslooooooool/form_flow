/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;

import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.web.bean.entity.TNResource;

/**
 * 资源Dao接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IResourceDao {

	/**
	 * 关联查询（资源中含有操作权限）
	 * @param searchParam 搜索过滤对象
	 * @return 返回资源实体对象集合
	 */
	public List<TNResource> queryContainAuths(FilterParam searchParam);
	
}
