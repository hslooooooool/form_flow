package cn.com.smart.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.bean.TreeProp;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.bean.entity.TNRole;
import cn.com.smart.web.cache.impl.MenuMemoryCache;
import cn.com.smart.web.cache.impl.OPAuthMemoryCache;
import cn.com.smart.web.cache.impl.ResourceMemoryCache;
import cn.com.smart.web.cache.impl.RoleResourceMemoryCache;
import cn.com.smart.web.dao.impl.OPAuthDao;
import cn.com.smart.web.dao.impl.ResourceDao;
import cn.com.smart.web.dao.impl.RoleDao;
import cn.com.smart.web.dao.impl.RoleResourceDao;
import cn.com.smart.web.plugins.ZTreeData;
import cn.com.smart.web.plugins.ZTreeHelper;

/**
 * 
 * @author lmq
 *
 */
@Service
public class ResourceService extends MgrServiceImpl<TNResource> {
	
	public static final String  RES_FLAG = "res";
	public static final String  AUTH_FLAG = "auth";
	
	@Autowired
	private ResourceDao resDao;
	@Autowired
	private OPAuthDao opAuthDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleResourceDao roleResDao;
	
	@Autowired
	private ResourceMemoryCache resCache;
	@Autowired
	private OPAuthMemoryCache authCache;
	@Autowired
	private MenuMemoryCache menuCache;
	@Autowired
	private RoleResourceMemoryCache roleResCache;

	/**
	 * @param searchParam
	 * @param start
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<Object> findAll(FilterParam searchParam,int start,int rows) throws ServiceException {
		SmartResponse<Object> smartResp = new SmartResponse<Object>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			List<Object> lists = resDao.queryObjPage(searchParam, start, rows);
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				//操作权限值转化为操作名称
				List<TNOPAuth> opAuths = null;
				for (Object obj : lists) {
					Object[] objArray = (Object[])obj;
					if( null != objArray[4] && StringUtils.isNotEmpty(objArray[4].toString())) {
						String[] authValus = objArray[4].toString().split(",");
						
						opAuths = authCache.queryAuths(authValus);
						if(null == opAuths) 
							opAuths = opAuthDao.queryAuths(authValus);
						
						if(null != opAuths && opAuths.size()>0) {
							String opAuthStr = "";
							for (TNOPAuth opAuth : opAuths) {
								opAuthStr += opAuth.getName()+"，";
							}
							opAuthStr = opAuthStr.substring(0,opAuthStr.length()-1);
							objArray[4] = opAuthStr;
						}
					}
				}
				opAuths = null;
				long totalNum = resDao.queryObjCount(searchParam);
				smartResp.setTotalNum(totalNum);
				smartResp.setTotalPage(getTotalPage(totalNum, rows));
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	@Override
	public SmartResponse<String> save(TNResource resource) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != resource){
				if(null != resource.getAuths() && resource.getAuths().size()>0) {
					String opAuths = resource.authsToString();
					resource.setOpAuths(opAuths);
				}
				smartResp = super.save(resource);
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					//获取超级管理员角色(在超级管理员角色中添加资源信息)
					TNRole role = roleDao.adminRole();
					if(null != role) {
						List<TNResource> resList = new ArrayList<TNResource>(1);
						resList.add(resource);
						roleResDao.save(role.getId(),resList);
						resList = null;
					}
					reInitCache();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}

	
	@Override
	public SmartResponse<String> update(TNResource resource) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(null != resource) {
				if(null != resource.getAuths() && resource.getAuths().size()>0) {
					String opAuths = resource.authsToString();
					resource.setOpAuths(opAuths);
				}
				smartResp  = super.update(resource);
				if(OP_SUCCESS.equals(smartResp.getResult())) {
					//获取超级管理员角色(在超级管理员角色中添加资源信息)
					TNRole role = roleDao.adminRole();
					if(null != role) {
						List<TNResource> resList = new ArrayList<TNResource>(1);
						resList.add(resource);
						roleResDao.update(role.getId(),resList);
						resList = null;
					}
					reInitCache();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public SmartResponse<String> delete(String id) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("资源删除失败");
		if(StringUtils.isNotEmpty(id)) {
			try {
				if(resDao.delete(id)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("资源删除成功");
					reInitCache();
				}
			} catch(DaoException ex) {
				ex.printStackTrace();
			}
		}
		return smartResp;
	}
	
	
	private void reInitCache() {
		resCache.refreshCache();
		menuCache.refreshCache();
		roleResCache.refreshCache();
	}
	
	/**
	 * 选择资源权限列表
	 * @param searchParam
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<TNResource> selectResAuth(FilterParam searchParam) throws ServiceException {
		SmartResponse<TNResource> smartResp = new SmartResponse<TNResource>();
		try {
			List<TNResource> lists = resCache.queryContainAuths(searchParam);
			if(null == lists) 
				lists = resDao.queryContainAuths(searchParam);
			if(null != lists && lists.size()>0) {
				smartResp.setResult(OP_SUCCESS);
				smartResp.setMsg(OP_SUCCESS_MSG);
				smartResp.setDatas(lists);
				smartResp.setSize(lists.size());
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 选择资源权限列表
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<ZTreeData> selectResAuthTree(String roleId) throws ServiceException {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		try {
			List<TNResource> lists = resCache.queryContainAuths(null);
			if(null == lists)
				lists =	resDao.queryContainAuths(null);
			if(null != lists && lists.size()>0) {
				List<TreeProp> treeProps = resAuth2TreeProp(lists);
				Map<String,List<TNOPAuth>> selectedResAuthMaps = null;
				if(StringUtils.isNotEmpty(roleId))
					selectedResAuthMaps = roleResCache.queryByRole(roleId);
				if(null != treeProps && treeProps.size()>0) {
					ZTreeHelper<ZTreeData> zTreeHelper = new ZTreeHelper<ZTreeData>(ZTreeData.class, treeProps);
					List<ZTreeData> ztrees = zTreeHelper.convert(null);
					if(null != ztrees && ztrees.size()>0) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
						smartResp.setDatas(ztrees);
						//处理默认选择数据
						if(null != selectedResAuthMaps && selectedResAuthMaps.size()>0) {
							for (String key : selectedResAuthMaps.keySet()) {
								for (ZTreeData ztree : ztrees) {
									if(key.equals(ztree.getId())) {
										ztree.setChecked(true);
									} else if(key.equals(ztree.getpId())){
										List<TNOPAuth> selectedAuths = selectedResAuthMaps.get(key);
										if(null != selectedAuths && selectedAuths.size()>0) {
											for (TNOPAuth opAuth : selectedAuths) {
												if(opAuth.getValue().equals(ztree.getId())) {
													ztree.setChecked(true);
													break;
												}
											}
										}
									}
								}//for2
							}//for1
						}
					}
					selectedResAuthMaps = null;
					ztrees = null;
					zTreeHelper = null;
				}
			}
			lists = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 获取已选择的资源权限列表
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<ZTreeData> selectedResAuthTree(String roleId) throws ServiceException {
		SmartResponse<ZTreeData> smartResp = selectResAuthTree(roleId);
		try {
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				List<ZTreeData> ztrees = smartResp.getDatas();
				List<ZTreeData> selectedZtrees = new ArrayList<ZTreeData>();
				for (ZTreeData ztree : ztrees) {
					if(ztree.getChecked()) {
						selectedZtrees.add(ztree);
					}
				}
				if(selectedZtrees.size()>0) {
					smartResp.setDatas(selectedZtrees);
				} else {
					smartResp.setResult(OP_FAIL);
					smartResp.setMsg(OP_FAIL_MSG);
					smartResp.setDatas(null);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 资源权限列表转化为树形结构
	 * @param resList
	 * @return
	 * @throws ServiceException
	 */
	public List<TreeProp> resAuth2TreeProp(List<TNResource> resList) throws ServiceException {
		List<TreeProp> trees = null;
		try {
			if(null != resList && resList.size()>0) {
				trees = new ArrayList<TreeProp>();
				TreeProp treeProp = null;
				for (TNResource res: resList) {
					treeProp = new TreeProp();
					treeProp.setFlag(RES_FLAG);
					treeProp.setId(res.getId());
					treeProp.setName(res.getName());
					treeProp.setParentId("0");
					treeProp.setSortOrder(1);
					trees.add(treeProp);
					List<TNOPAuth> auths = res.getAuths();
					if(null != auths && auths.size()>0) {
						for (TNOPAuth auth : auths) {
							treeProp = new TreeProp();
							treeProp.setFlag(AUTH_FLAG);
							treeProp.setId(auth.getValue());
							treeProp.setName(auth.getName());
							treeProp.setParentId(res.getId());
							int sortOrder = 0;
							sortOrder = null == auth.getSortOrder()?0:auth.getSortOrder().intValue();
							treeProp.setSortOrder(sortOrder);
							trees.add(treeProp);
						}
					}//if
					auths = null;
				}//for
				treeProp = null;
			}//if
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return trees;
	}
	
	
	/**
	 * 树形结构转化为资源列表
	 * @param resList
	 * @return
	 */
	public List<TNResource> treeProp2Res(List<TreeProp> treeProps) throws ServiceException {
		List<TNResource> resources = null;
		try {
			if(null != treeProps && treeProps.size()>0) {
				resources = new ArrayList<TNResource>();
				TNResource res = null;
				TNOPAuth auth = null;
				List<TNOPAuth> auths = null;
				Map<String,List<TNOPAuth>> authMaps = new HashMap<String, List<TNOPAuth>>();
				for (TreeProp treeProp : treeProps) {
					if(RES_FLAG.equals(treeProp.getFlag())) {
						res = new TNResource();
						res.setId(treeProp.getId());
						res.setName(treeProp.getName());
						resources.add(res);
					} else {
						auth = new TNOPAuth();
						auth.setValue(treeProp.getId());
						auth.setName(treeProp.getName());
						auths = authMaps.get(treeProp.getParentId());
						if(null == auths) {
							auths = new ArrayList<TNOPAuth>();
							authMaps.put(treeProp.getParentId(), auths);
						}
						auths.add(auth);
					}
				}
				if(resources.size()>0 && authMaps.size()>0) {
					for (TNResource resTmp : resources) {
						auths = authMaps.get(resTmp.getId());
						if(null != auths && auths.size()>0) {
							resTmp.setAuths(auths);
						}
					}
				}
				authMaps = null;
				auths = null;
				auth = null;
				res = null;
			}
			treeProps = null;
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return resources;
	}
	
}
