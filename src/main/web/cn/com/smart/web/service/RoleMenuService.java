package cn.com.smart.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.helper.TreeHelper;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNMenu;
import cn.com.smart.web.bean.entity.TNRoleMenu;
import cn.com.smart.web.dao.impl.MenuDao;
import cn.com.smart.web.dao.impl.RoleMenuDao;
import cn.com.smart.web.plugins.ZTreeData;

/**
 * 
 * @author lmq
 *
 */
@Service
public class RoleMenuService extends MgrServiceImpl<TNRoleMenu> {

	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 保存
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<String> save(String roleId,String[] menuIds) throws ServiceException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		try {
			if(StringUtils.isNotEmpty(roleId)) {
				if(roleMenuDao.delete(roleId)) {
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg(OP_SUCCESS_MSG);
				}
				if(null != menuIds && menuIds.length>0) {
					List<TNRoleMenu> list = new ArrayList<TNRoleMenu>();
					TNRoleMenu roleMenu = null;
					for (int i = 0; i < menuIds.length; i++) {
						roleMenu = new TNRoleMenu();
						roleMenu.setMenuId(menuIds[i]);
						roleMenu.setRoleId(roleId);
						list.add(roleMenu);
					}
					if(null != roleMenuDao.save(list)) {
						smartResp.setResult(OP_SUCCESS);
						smartResp.setMsg(OP_SUCCESS_MSG);
					} else {
						smartResp.setResult(OP_FAIL);
						smartResp.setMsg(OP_FAIL_MSG);
					}
					list = null;
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
	 * 菜单及操作权限组成的树
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<ZTreeData> menuTree() throws ServiceException {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			List<TNMenu> menus = menuDao.findAll();
			if(null != menus && menus.size()>0) {
				try {
					TreeHelper<TNMenu> menuTreeHelper = new TreeHelper<TNMenu>();
					List<TNMenu> menuTrees = menuTreeHelper.outPutTree(menus);
					menuTreeHelper = null;
					if(null != menuTrees && menuTrees.size()>0) {
						List<ZTreeData> zTreeDatas = new ArrayList<ZTreeData>();
						ZTreeData zTreeData = null;
						for (TNMenu menu : menuTrees) {
							zTreeData = new ZTreeData();
							zTreeData.setId(menu.getId());
							zTreeData.setName(menu.getName());
							zTreeData.setpId(menu.getParentId());
							zTreeData.setFlag("menu");
							zTreeDatas.add(zTreeData);
						}
						if(zTreeDatas.size()>0) {
							smartResp.setResult(OP_SUCCESS);
							smartResp.setMsg(OP_SUCCESS_MSG);
							smartResp.setDatas(zTreeDatas);
							smartResp.setTotalNum(zTreeDatas.size());
						}
						zTreeDatas = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			menus = null;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 菜单及操作权限组成的树
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	public SmartResponse<ZTreeData> menuTree(String roleId) throws ServiceException {
		SmartResponse<ZTreeData> smartResp = new SmartResponse<ZTreeData>();
		smartResp.setResult(OP_NOT_DATA_SUCCESS);
		smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
		try {
			if(StringUtils.isNotEmpty(roleId)) {
				List<TNMenu> menus = menuDao.findAll();
				if(null != menus && menus.size()>0) {
					List<TNRoleMenu> roleMenus = roleMenuDao.queryByRole(roleId);
					try {
						TreeHelper<TNMenu> menuTreeHelper = new TreeHelper<TNMenu>();
						List<TNMenu> menuTrees = menuTreeHelper.outPutTree(menus);
						menuTreeHelper = null;
						if(null != menuTrees && menuTrees.size()>0) {
							List<ZTreeData> zTreeDatas = new ArrayList<ZTreeData>();
							ZTreeData zTreeData = null;
							for (TNMenu menu : menuTrees) {
								zTreeData = new ZTreeData();
								zTreeData.setId(menu.getId());
								zTreeData.setName(menu.getName());
								zTreeData.setpId(menu.getParentId());
								zTreeData.setFlag("menu");
								zTreeDatas.add(zTreeData);
								//操作权限
							}
							zTreeData = null;
							if(zTreeDatas.size()>0) {
								if(null != roleMenus && roleMenus.size()>0) {
									for (TNRoleMenu roleMenu : roleMenus) {
										for (ZTreeData zTreeDataTmp : zTreeDatas) {
											if(roleMenu.getMenuId().equals(zTreeDataTmp.getId()) && 
													"menu".equals(zTreeDataTmp.getFlag())) {
												zTreeDataTmp.setChecked(true);
											}
										}//for
									}//for
								}
								smartResp.setResult(OP_SUCCESS);
								smartResp.setMsg(OP_SUCCESS_MSG);
								smartResp.setDatas(zTreeDatas);
								smartResp.setTotalNum(zTreeDatas.size());
							}
							zTreeDatas = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						roleMenus  = null;
					}
					
				}
				menus = null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e.getCause());
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
	
	
	/**
	 * 角色菜单树（该角色拥有的菜单及操作权限）
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public SmartResponse<ZTreeData> roleMenuTree(String roleId) throws ServiceException {
		SmartResponse<ZTreeData> smartResp = menuTree(roleId);
		try {
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				List<ZTreeData> zTreeDatas = (List<ZTreeData>)smartResp.getData();
				List<ZTreeData> newZTreeDatas = new ArrayList<ZTreeData>();
				try {
					for (ZTreeData zTreeData : zTreeDatas) {
						if("true".equals(zTreeData.getChecked())) {
							newZTreeDatas.add(zTreeData);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(newZTreeDatas.size()>0) {
					smartResp.setDatas(newZTreeDatas);
				}
				newZTreeDatas = null;
				zTreeDatas = null;
			}
		} catch (Exception e) {
			throw new ServiceException(e.getCause());
		}
		return smartResp;
	}
}
